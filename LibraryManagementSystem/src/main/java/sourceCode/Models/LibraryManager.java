package sourceCode.Models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.time.LocalDate;

public class LibraryManager {

    private final GoogleBooksService googleBooksService = new GoogleBooksService();
    private final DatabaseService databaseService = new DatabaseService();

    /**
     * Add books to library by query.
     */
    public void addBooksToLibrary(String query) {
        try {
            JsonArray books = googleBooksService.searchBooks(query);
            for (int i = 0; i < books.size(); i++) {
                JsonObject book = books.get(i).getAsJsonObject();
                JsonObject volumeInfo = book.getAsJsonObject("volumeInfo");
                Document newBook = new Document();
                newBook.setISBN(book.get("id").getAsString());
                newBook.setTitle(volumeInfo.get("title").getAsString());
                newBook.setAuthor(volumeInfo.get("authors").getAsJsonArray().get(0).getAsString());
                newBook.setGenre(
                        volumeInfo.get("categories").getAsJsonArray().get(0).getAsString());
                newBook.setPublisher(volumeInfo.get("publisher").getAsString());
                newBook.setPublicationDate(
                        LocalDate.parse(volumeInfo.get("publishedDate").getAsString()));
                newBook.setLanguage(volumeInfo.get("language").getAsString());
                newBook.setPageNumber(volumeInfo.get("pageCount").getAsInt());
                newBook.setImageUrl(
                        volumeInfo.getAsJsonObject("imageLinks").get("thumbnail").getAsString());
                newBook.setDescription(volumeInfo.get("description").getAsString());
                databaseService.addBook(newBook);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
