package sourceCode.Models;

import java.time.LocalDate;

public class Document {

    private String id;
    private String imageUrl;
    private String title;
    private String authors;
    private int quantity;
    private String location;
    private LocalDate publicationDate;
    private double price;

    public Document(String id, String title, int quantity, double price) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.imageUrl = "";
        this.location = "";
        this.authors = "";
        this.price = price;
    }

    public Document() {
        this.id = "";
        this.imageUrl = "";
        this.title = "";
        this.quantity = 0;
        this.location = "";
        this.authors = "";
        this.price = 0;
    }

    public Document(String id, String title, int quantity, String location, String author,
            double price, LocalDate publicationDate) {
        this.id = id;
        this.title = title;
        this.quantity = quantity;
        this.location = location;
        this.authors = author;
        this.price = price;
        this.imageUrl = "";
        this.publicationDate = publicationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String author) {
        this.authors = author;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
