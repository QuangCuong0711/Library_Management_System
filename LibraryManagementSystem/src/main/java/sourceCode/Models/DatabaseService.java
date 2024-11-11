package sourceCode.Models;

import java.sql.*;

public class DatabaseService {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/library_db";
    // Điền tên và mật khẩu của database của máy
    private static final String USER = "";
    private static final String PASS = "";

    // Kết nối cơ sở dữ liệu
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    // Lưu sách vào cơ sở dữ liệu
    public void saveBook(Document newBook) {
        String sql = "INSERT INTO books (id, imageUrl, title, authors, quantity, location, publicationDate, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = this.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newBook.getId());
            pstmt.setString(2, newBook.getImageUrl());
            pstmt.setString(3, newBook.getTitle());
            pstmt.setString(4, newBook.getAuthors());
            pstmt.setInt(5, newBook.getQuantity());
            pstmt.setString(6, newBook.getLocation());
            if (newBook.getPublicationDate() != null) {
                pstmt.setDate(7, Date.valueOf(newBook.getPublicationDate()));
            } else {
                pstmt.setNull(7, java.sql.Types.DATE);
            }
            pstmt.setDouble(8, newBook.getPrice());
            pstmt.executeUpdate();

            System.out.println("Sách đã được lưu thành công");
        } catch (SQLException e) {
            System.out.println("Lưu sách thất bại");
            e.printStackTrace();
        }
    }
}
