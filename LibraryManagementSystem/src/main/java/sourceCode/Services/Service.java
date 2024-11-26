package sourceCode.Services;

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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import sourceCode.Models.Book;

public class Service {

    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "root";
    private static final String PASSWORD = "Ncmh@0822"; // mng sửa theo máy cá nhân nhé
    private static final String API_URL = "https://www.googleapis.com/books/v1/volumes";
    private static final String API_KEY = "AIzaSyAyp5j4HRvk5dLirXVzyeZnWEEH3H2Cbhc";

    // Quang Cuong : AIzaSyCcp7GuHwib1MlkCrRv0ez7aWXhaI3nJXE
    // Thanh Hai : AIzaSyAfpySygIIfG6YtBgDT1x6xaYFkBkNjnDg
    // Manh Hung : AIzaSyAyp5j4HRvk5dLirXVzyeZnWEEH3H2Cbhc

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected");
            return connection;
        } catch (SQLException e) {
            System.out.println("Database connection failed");
            e.printStackTrace();
            return null;
        }
    }

    public static JsonArray getBook(String query) throws IOException, InterruptedException {
        try {
            String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
            String url = API_URL + "?q=" + encodedQuery + "&key=" + API_KEY;
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

    public static Book createBookFromJson(JsonObject book) {
        JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
        Book newBook = new Book();
        newBook.setISBN(book.get("id") != null ? book.get("id").getAsString() : null);
        newBook.setTitle(
                volumeInfo.get("title") != null ? volumeInfo.get("title").getAsString() : null);
        newBook.setAuthor(
                volumeInfo.get("authors") != null ? volumeInfo.get("authors").getAsJsonArray()
                        .get(0).getAsString() : null);
        newBook.setGenre(
                volumeInfo.get("categories") != null ? volumeInfo.get("categories").getAsJsonArray()
                        .get(0).getAsString() : null);
        newBook.setPublisher(
                volumeInfo.get("publisher") != null ? volumeInfo.get("publisher").getAsString()
                        : null);
        newBook.setPublicationDate(
                volumeInfo.get("publishedDate") != null ? volumeInfo.get("publishedDate")
                        .getAsString() : null);
        newBook.setLanguage(
                volumeInfo.get("language") != null ? volumeInfo.get("language").getAsString()
                        : null);
        newBook.setPageNumber(
                volumeInfo.get("pageCount") != null ? volumeInfo.get("pageCount").getAsInt() : 0);
        newBook.setImageUrl(volumeInfo.getAsJsonObject("imageLinks") != null
                && volumeInfo.getAsJsonObject("imageLinks").get("thumbnail") != null
                ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : null);
        newBook.setDescription(
                volumeInfo.get("description") != null ? volumeInfo.get("description").getAsString()
                        : null);
        return newBook;
    }
}
