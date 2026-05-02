import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import org.json.*;

class Notification {
    String id;
    String type;
    String message;
    long timestamp;

    public Notification(String id, String type, String message, long timestamp) {
        this.id = id;
        this.type = type;
        this.message = message;
        this.timestamp = timestamp;
    }
}

public class NotificationPriority {

    public static void main(String[] args) throws Exception {

        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJhdjE3NDlAZ21haWwuY29tIiwiZXhwIjoxNzc3NzA1MzQ3LCJpYXQiOjE3Nzc3MDQ0NDcsImlzcyI6IkFmZm9yZCBNZWRpY2FsIFRlY2hub2xvZ2llcyBQcml2YXRlIExpbWl0ZWQiLCJqdGkiOiJjNDRhNzFiZi05YTdmLTRmMzMtOGZjYS02M2RlMjY3YTQzMTYiLCJsb2NhbGUiOiJlbi1JTiIsIm5hbWUiOiJhYXl1c2ggdmVybWEiLCJzdWIiOiJlYWUxZmJkMS1hMTI0LTRjN2ItYjRkZS04YTg2YzZhM2M5NjIifSwiZW1haWwiOiJhdjE3NDlAZ21haWwuY29tIiwibmFtZSI6ImFheXVzaCB2ZXJtYSIsInJvbGxObyI6InJhMjMxMTA1NjAzMDA4MSIsImFjY2Vzc0NvZGUiOiJRa2JweEgiLCJjbGllbnRJRCI6ImVhZTFmYmQxLWExMjQtNGM3Yi1iNGRlLThhODZjNmEzYzk2MiIsImNsaWVudFNlY3JldCI6IkN5WkdRZGZYeXlHanhjUWEifQ.krNtsfzXzFnuHq-oqsqhmOhbOYd-NVQcO6oyPqCe1Fw";

        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://20.207.122.201/evaluation-service/notifications"))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // 🔥 DEBUG (optional but useful)
System.out.println("API RESPONSE:");
System.out.println(response.body());

// 🔥 FIXED PARSING
JSONObject json = new JSONObject(response.body());
JSONArray arr = json.getJSONArray("notifications");
        List<Notification> list = new ArrayList<>();

        for (int i = 0; i < arr.length(); i++) {
            JSONObject obj = arr.getJSONObject(i);

            list.add(new Notification(
                    obj.getString("ID"),
                    obj.getString("Type"),
                    obj.getString("Message"),
                    System.currentTimeMillis() // simple recency
            ));
        }

        // SORT BY PRIORITY
        list.sort((a, b) -> getScore(b) - getScore(a));

        System.out.println("Top 10 Notifications:\n");

        for (int i = 0; i < Math.min(10, list.size()); i++) {
            Notification n = list.get(i);
            System.out.println(n.type + " - " + n.message);
        }
    }

    static int getScore(Notification n) {
        int weight;

        switch (n.type) {
            case "Placement":
                weight = 3;
                break;
            case "Result":
                weight = 2;
                break;
            default:
                weight = 1;
        }

        return weight * 100 + (int)(n.timestamp % 1000);
    }
}