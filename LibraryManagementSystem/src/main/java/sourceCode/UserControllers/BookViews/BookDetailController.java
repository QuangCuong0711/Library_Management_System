package sourceCode.UserControllers.BookViews;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import sourceCode.LoginController;
import sourceCode.Models.Book;
import sourceCode.Services.DatabaseConnection;

import static sourceCode.LoginController.imageCache;
import static sourceCode.LoginController.imagedefault;

public class BookDetailController {

    @FXML
    private ImageView image;
    @FXML
    private Label title;
    @FXML
    private Label ISBN;
    @FXML
    private Label author;
    @FXML
    private Label publisher;
    @FXML
    private Label date;
    @FXML
    private Label genre;
    @FXML
    private Label language;
    @FXML
    private Label pageNumber;
    @FXML
    private TextArea description;
    private Book book;

    public void setBook(Book book) {
        this.book = book;
        title.setText(book.getTitle());
        ISBN.setText(book.getISBN());
        author.setText(book.getAuthor());
        genre.setText(book.getGenre());
        publisher.setText(book.getPublisher());
        date.setText(book.getPublicationDate());
        language.setText(book.getLanguage());
        pageNumber.setText(String.valueOf(book.getPageNumber()));
//        if (book.getImageUrl() != null && !book.getImageUrl().isEmpty()) {
//            try {
//                Image img = new Image(book.getImageUrl(), true);
//                if (img.isError()) {
//                    System.out.println("Image URL is invalid or image cannot be loaded: "
//                            + book.getImageUrl());
//                } else {
//                    image.setImage(img);
//                }
//            } catch (IllegalArgumentException e) {
//                System.out.println("Invalid image URL: " + book.getImageUrl());
//                image.setImage(null);
//            }
//        }
        if (book.getImageUrl() != null) {
//            try{
//                Image image = new Image(book.getImageUrl());
//                if (image.isError()) {
//                    bookCover.setImage(imagedefault); // Gán ảnh mặc định
//                } else {
//                    bookCover.setImage(image); // Gán ảnh nếu tải thành công
//                }
//            } catch (Exception e) {
//                bookCover.setImage(imagedefault);
//
//            }
            if (imageCache.containsKey(book.getImageUrl())) {
                // Nếu ảnh đã có trong cache, sử dụng ảnh đó
                image.setImage(imageCache.get(book.getImageUrl()));
            } else {
                try {
                    // Tải ảnh trực tiếp trong luồng chính
                    Image img = new Image(book.getImageUrl());
                    if (!img.isError()) {
                        // Nếu tải thành công, lưu vào cache và hiển thị ảnh
                        imageCache.put(book.getImageUrl(), img);
                        image.setImage(img);
                        System.out.println("Image loaded and cached: " + book.getImageUrl());
                    } else {
                        // Nếu ảnh có lỗi, sử dụng ảnh mặc định
                        System.out.println("Image error, using default for URL: " + book.getImageUrl());
                        image.setImage(imagedefault);
                    }
                } catch (Exception e) {
                    // Xử lý ngoại lệ và sử dụng ảnh mặc định
                    System.out.println("Exception while loading image: " + book.getImageUrl());
                    e.printStackTrace();
                    image.setImage(imagedefault);
                }
            }
        }
        description.setText(book.getDescription());
    }

    public void borrowBook() {
        String checkQuery = "SELECT quantity FROM library.book WHERE ISBN = ?";
        String returnLateQuery = "SELECT COUNT(*) FROM library.ticket t WHERE userId = ? AND returnedDate IS NOT NULL AND DATEDIFF(returnedDate, borrowedDate) > 30";
        String lateQuery = "SELECT COUNT(*) FROM library.ticket t WHERE userId = ? AND returnedDate IS NULL AND DATEDIFF(CURDATE(), borrowedDate) > 30";
        String borrowQuery = "SELECT COUNT(*) FROM library.ticket t WHERE userId = ? AND returnedDate IS NULL AND DATEDIFF(CURDATE(), borrowedDate) <= 30";
        String insertQuery = "INSERT INTO library.ticket (ISBN, userID, borrowedDate, returnedDate, quantity) VALUES (?, ?, CURDATE(), null, 1)";
        String updateTicketQuery = "UPDATE library.ticket SET quantity = ? WHERE ISBN = ? AND userID = ?";
        String updateBookQuery = "UPDATE library.book SET quantity = quantity - 1 WHERE ISBN = ?";

        ExecutorService executor = Executors.newFixedThreadPool(4);

        try (Connection conn = DatabaseConnection.getInstance().getConnection()) {
            assert conn != null;

            // Future để kiểm tra số lượng sách
            Future<Integer> checkFuture = executor.submit(() -> {
                try (PreparedStatement stmt = conn.prepareStatement(checkQuery)) {
                    stmt.setString(1, book.getISBN());
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt("quantity");
                        }
                    }
                }
                return 0;
            });

            Future<Integer> returnLateFuture = executor.submit(() -> {
                try (PreparedStatement stmt = conn.prepareStatement(returnLateQuery)) {
                    stmt.setString(1, sourceCode.LoginController.currentUserId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
                return 0;
            });

            Future<Integer> lateFuture = executor.submit(() -> {
                try (PreparedStatement stmt = conn.prepareStatement(lateQuery)) {
                    stmt.setString(1, sourceCode.LoginController.currentUserId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
                return 0;
            });

            Future<Integer> borrowFuture = executor.submit(() -> {
                try (PreparedStatement stmt = conn.prepareStatement(borrowQuery)) {
                    stmt.setString(1, sourceCode.LoginController.currentUserId);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            return rs.getInt(1);
                        }
                    }
                }
                return 0;
            });

            int bookQuantity = checkFuture.get();
            int returnLateCount = returnLateFuture.get();
            int lateCount = lateFuture.get();
            int borrowCount = borrowFuture.get();

            if (lateCount >= 1) {
                System.out.println("Người dùng không được phép mượn sách do có quá nhiều sách trả muộn hoặc quá hạn.");
                return;
            }

            if (returnLateCount >= 3) {
                System.out.println("Người dùng không được phép mượn sách do có quá nhiều sách trả muộn hoặc quá hạn.");
                return;
            }

            if (borrowCount >= 5) {
                System.out.println("Người dùng đã mượn quá số lượng sách cho phép.");
                return;
            }

            if (bookQuantity <= 0) {
                System.out.println("Sách không có sẵn để mượn.");
                return;
            }

            try (PreparedStatement updateTicketStmt = conn.prepareStatement(updateTicketQuery)) {
                updateTicketStmt.setInt(1, 1);
                updateTicketStmt.setString(2, book.getISBN());
                updateTicketStmt.setString(3, sourceCode.LoginController.currentUserId);
                int rowsAffected = updateTicketStmt.executeUpdate();

                if (rowsAffected == 0) {
                    try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                        insertStmt.setString(1, book.getISBN());
                        insertStmt.setString(2, sourceCode.LoginController.currentUserId);
                        insertStmt.executeUpdate();
                    }
                }
            }

            try (PreparedStatement updateBookStmt = conn.prepareStatement(updateBookQuery)) {
                updateBookStmt.setString(1, book.getISBN());
                updateBookStmt.executeUpdate();
                System.out.println("Book quantity updated successfully");
            }

        } catch (SQLException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
            System.out.println("Book borrowing failed");
        } finally {
            executor.shutdown();
        }
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
