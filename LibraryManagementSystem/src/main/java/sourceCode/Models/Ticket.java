package sourceCode.Models;

import java.time.LocalDate;

public class Ticket {
    private String ticketID;
    private String documentID;
    private String userID;
    private LocalDate borrowedDate;
    private LocalDate returnedDate;
    private int quantity;

    public Ticket() {
        this.ticketID = "";
        this.documentID = "";
        this.userID = "";
        this.borrowedDate = null;
        this.returnedDate = null;
        this.quantity = 0;
    }
    public Ticket(String ticketID, String documentID, String userID, LocalDate borrowedDate, LocalDate returnedDate, int quantity) {
        this.ticketID = ticketID;
        this.documentID = documentID;
        this.userID = userID;
        this.borrowedDate = borrowedDate;
        this.returnedDate = returnedDate;
        this.quantity = quantity;
    }

    public String getTicketID() {
        return ticketID;
    }

    public void setTicketID(String ticketID) {
        this.ticketID = ticketID;
    }

    public String getDocumentID() {
        return documentID;
    }

    public void setDocumentID(String documentID) {
        this.documentID = documentID;
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
}
