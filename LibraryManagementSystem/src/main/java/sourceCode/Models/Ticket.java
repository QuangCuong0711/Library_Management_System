package sourceCode.Models;

import java.time.LocalDate;

public class Ticket {

    private int ticketID;
    private String bookID;
    private String userID;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private int quantity;
    private String status;

    public Ticket() {
        this.ticketID = 0;
        this.bookID = "";
        this.userID = "";
        this.borrowedDate = null;
        this.returnedDate = null;
        this.quantity = 0;
        this.status = "";
    }

    public Ticket(int ticketID, String bookID, String userID, LocalDate borrowedDate,
            LocalDate returnedDate, int quantity, String status) {
        this.ticketID = ticketID;
        this.bookID = bookID;
        this.userID = userID;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.quantity = quantity;
        this.status = status;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public LocalDate getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(LocalDate borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public LocalDate getReturnedDate() {
        return returnedDate;
    }

    public void setReturnedDate(LocalDate returnedDate) {
        this.returnedDate = returnedDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
