package sourceCode.AdminControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ShowTicket {

    @FXML
    private Label ticketID;
    @FXML
    private Label userID;
    @FXML
    private Label bookID;
    @FXML
    private Label borrowedDate;
    @FXML
    private Label returnedDate;
    @FXML
    private Label quantity;
    @FXML
    private Label status;


    public void setTicket(sourceCode.Models.Ticket ticket) {
        ticketID.setText(String.valueOf(ticket.getTicketID()));
        userID.setText(ticket.getUserID());
        bookID.setText(ticket.getBookID());
        borrowedDate.setText(String.valueOf(ticket.getBorrowedDate()));
        returnedDate.setText(String.valueOf(ticket.getReturnedDate()));
        quantity.setText(String.valueOf(ticket.getQuantity()));
        status.setText(ticket.getStatus());
    }

    public void confirmButtonOnAction(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
