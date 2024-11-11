package sourceCode.Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class GoogleBooksService {

    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String API_KEY = "AIzaSyAfpySygIIfG6YtBgDT1x6xaYFkBkNjnDg";
    // Quang Cuong : AIzaSyCcp7GuHwib1MlkCrRv0ez7aWXhaI3nJXE
    // Thanh Hai : AIzaSyAfpySygIIfG6YtBgDT1x6xaYFkBkNjnDg
    // Manh Hung :

    /**
     * Search books by query and return a JsonArray with Google Books API.
     */
    public JsonArray searchBooks(String query) throws IOException, InterruptedException {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = BASE_URL + "?q=" + encodedQuery + "&key=" + API_KEY;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject jsonObject = JsonParser.parseString(response.body()).getAsJsonObject();
                return jsonObject.getAsJsonArray("items");
            } else {
                throw new IOException("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("URL errors or can't send request", e);
        }
    }
}
