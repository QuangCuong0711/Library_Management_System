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

    private static final String API_KEY = "AIzaSyCcp7GuHwib1MlkCrRv0ez7aWXhaI3nJXE";
    // Đây là key api của bạn mọi người xem nếu không chạy được thì thay api key của mình vào - Quang Cuong
    private static final String BASE_URL = "https://www.googleapis.com/books/v1/volumes";

    // Hàm này để tìm kiếm sách theo API của Google Books
    public JsonArray searchBooks(String query) throws IOException, InterruptedException {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
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
            throw new IOException("Lỗi URL hoặc gửi yêu cầu", e);
        }
    }

}
