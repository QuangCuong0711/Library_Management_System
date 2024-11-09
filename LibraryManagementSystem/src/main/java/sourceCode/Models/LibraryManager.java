package sourceCode.Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LibraryManager {
    private GoogleBooksService booksService = new GoogleBooksService();
    private DatabaseService dbService = new DatabaseService();

    // Hàm lưu sách vào hệ thống
    public void addBooksToLibrary(String query) {
        try {
            JsonArray books = booksService.searchBooks(query);
            for (int i = 0; i < books.size(); i++) {
                JsonObject book = books.get(i).getAsJsonObject();
                JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");

                String id = book.get("id").getAsString();
                String title = volumeInfo.get("title").getAsString();
                String authors = volumeInfo.has("authors") ? volumeInfo.get("authors").toString() : "Unknown";
                String description = volumeInfo.has("description") ? volumeInfo.get("description").getAsString() : "No description";
                String imageUrl = volumeInfo.has("imageLinks") ? volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString() : "";

                // Thiết lập các giá trị mặc định cho những thuộc tính còn lại
                LocalDate publicationDate = LocalDate.of(2000, 1, 1);
                int quantity = 1;
                String location = "Unknown";
                double price = 0.0;

                Document newBook = new Document();
                newBook.setId(id);
                newBook.setImageUrl(imageUrl);
                newBook.setTitle(title);
                newBook.setAuthors(authors);
                newBook.setQuantity(quantity);
                newBook.setLocation(location);
                newBook.setPublicationDate(publicationDate);
                newBook.setPrice(price);

                // Lưu sách vào cơ sở dữ liệu
                dbService.saveBook(newBook);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
