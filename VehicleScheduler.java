import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

import org.json.*;

public class VehicleScheduler {

    public static void main(String[] args) throws Exception {

        HttpClient client = HttpClient.newHttpClient();

        // 🔥 STEP 1: ADD YOUR TOKEN HERE
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJNYXBDbGFpbXMiOnsiYXVkIjoiaHR0cDovLzIwLjI0NC41Ni4xNDQvZXZhbHVhdGlvbi1zZXJ2aWNlIiwiZW1haWwiOiJhdjE3NDlAZ21haWwuY29tIiwiZXhwIjoxNzc3NzAxMDA1LCJpYXQiOjE3Nzc3MDAxMDUsImlzcyI6IkFmZm9yZCBNZWRpY2FsIFRlY2hub2xvZ2llcyBQcml2YXRlIExpbWl0ZWQiLCJqdGkiOiJjM2JmZDRjNi04ZTVhLTQ5YzEtYjkwOS04MTRkOTk4OTA5NWMiLCJsb2NhbGUiOiJlbi1JTiIsIm5hbWUiOiJhYXl1c2ggdmVybWEiLCJzdWIiOiJlYWUxZmJkMS1hMTI0LTRjN2ItYjRkZS04YTg2YzZhM2M5NjIifSwiZW1haWwiOiJhdjE3NDlAZ21haWwuY29tIiwibmFtZSI6ImFheXVzaCB2ZXJtYSIsInJvbGxObyI6InJhMjMxMTA1NjAzMDA4MSIsImFjY2Vzc0NvZGUiOiJRa2JweEgiLCJjbGllbnRJRCI6ImVhZTFmYmQxLWExMjQtNGM3Yi1iNGRlLThhODZjNmEzYzk2MiIsImNsaWVudFNlY3JldCI6IkN5WkdRZGZYeXlHanhjUWEifQ.pOGMZgSBlj5JzE5Roct3FFXOehJjofGxVIyU1rBejMo";

        String depotUrl = "http://20.207.122.201/evaluation-service/depots";
        String vehicleUrl = "http://20.207.122.201/evaluation-service/vehicles";

        // 🔥 STEP 2: ADD AUTH HEADER
        HttpRequest depotRequest = HttpRequest.newBuilder()
                .uri(URI.create(depotUrl))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpRequest vehicleRequest = HttpRequest.newBuilder()
                .uri(URI.create(vehicleUrl))
                .header("Authorization", "Bearer " + token)
                .GET()
                .build();

        HttpResponse<String> depotResponse =
                client.send(depotRequest, HttpResponse.BodyHandlers.ofString());

        HttpResponse<String> vehicleResponse =
                client.send(vehicleRequest, HttpResponse.BodyHandlers.ofString());

        // 🔥 STEP 3: PARSE JSON
        JSONObject depotJson = new JSONObject(depotResponse.body());
        JSONArray depots = depotJson.getJSONArray("depots");

        JSONObject vehicleJson = new JSONObject(vehicleResponse.body());
        JSONArray vehicles = vehicleJson.getJSONArray("vehicles");

        // 🔥 STEP 4: CONVERT VEHICLES TO LIST
        List<Map<String, Object>> vehicleList = new ArrayList<>();

        

        for (int i = 0; i < vehicles.length(); i++) {
            JSONObject v = vehicles.getJSONObject(i);

            Map<String, Object> map = new HashMap<>();
            map.put("id", v.getString("TaskID"));
            map.put("duration", v.getInt("Duration"));
            map.put("impact", v.getInt("Impact"));

            vehicleList.add(map);
        }

        // 🔥 STEP 5: LOOP OVER DEPOTS

        System.out.println("Total Depots: " + depots.length());

        for (int d = 0; d < depots.length(); d++) {

            JSONObject depot = depots.getJSONObject(d);
            int capacity = depot.getInt("MechanicHours");

            System.out.println("\n=========================");
            System.out.println("Depot " + depot.getInt("ID"));
            System.out.println("Capacity: " + capacity);

            int n = vehicleList.size();
            int[][] dp = new int[n + 1][capacity + 1];

            // 🔥 STEP 6: KNAPSACK
            for (int i = 1; i <= n; i++) {

                int duration = (int) vehicleList.get(i - 1).get("duration");
                int impact = (int) vehicleList.get(i - 1).get("impact");

                for (int w = 0; w <= capacity; w++) {

                    if (duration <= w) {
                        dp[i][w] = Math.max(
                                dp[i - 1][w],
                                impact + dp[i - 1][w - duration]
                        );
                    } else {
                        dp[i][w] = dp[i - 1][w];
                    }
                }
            }

            // 🔥 STEP 7: BACKTRACK
            int w = capacity;
int totalDuration = 0;
int totalImpact = 0;

System.out.println("Selected Tasks:");

for (int i = n; i > 0; i--) {

    if (dp[i][w] != dp[i - 1][w]) {

        Map<String, Object> v = vehicleList.get(i - 1);

        int duration = (int) v.get("duration");
        int impact = (int) v.get("impact");

        System.out.println(v.get("id"));

        totalDuration += duration;
        totalImpact += impact;

        w -= duration;
    }
}

System.out.println("Total Duration: " + totalDuration);
System.out.println("Calculated Impact: " + totalImpact);
System.out.println("DP Max Impact: " + dp[n][capacity]);

            // 🔥 STEP 8: FINAL OUTPUT
            System.out.println("Total Duration: " + totalDuration);
            System.out.println("Max Impact: " + dp[n][capacity]);
        }
    }
}