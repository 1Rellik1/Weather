import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import org.json.*;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        String text = scanner.nextLine();
        String url ="http://api.openweathermap.org/data/2.5/weather?q="+text
                +"&lang=ru&appid=5663b81727130dedc1cbb06159d13ffd";
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(
                URI.create(url))
                .header("accept", "application/json")
                .build();
        var resp = client.send(request, HttpResponse.BodyHandlers.ofString());
        if(resp.statusCode()==200){
            var resp_body = resp.body();
            var Jobj = new JSONObject(resp_body);
            String name = Jobj.getString("name");
            int temp= Jobj.getJSONObject("main").getInt("temp") - 273;
            String state = Jobj.getJSONArray("weather").getJSONObject(0).getString("description");
            int wind = Jobj.getJSONObject("wind").getInt("speed");
            System.out.println("Погода в городе "+name+'\n'+"Температура: "+temp+ "°C\n"+"Состояние:"+ state+'\n'
                    + "Ветер: "+ wind+"м/с\n");
        }
        else{
            System.out.println("Город не найден");
        }
    }
}
