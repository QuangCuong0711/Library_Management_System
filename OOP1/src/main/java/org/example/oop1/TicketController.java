package org.example.oop1;

import Document.Document;
import Ticket.Ticket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

public class TicketController extends MenuController implements Initializable {

    @FXML
    private TableView<Ticket> table;

    @FXML
    private TableColumn<Ticket, String> idticket;

    @FXML
    private TableColumn<Ticket, String> userId;

    @FXML
    private TableColumn<Ticket, String> documentId;

    @FXML
    private TableColumn<Ticket, Date> borrowedDate;

    @FXML
    private TableColumn<Ticket, Date> returnedDate;

    @FXML
    private TableColumn<Ticket, String> quantity;

    @FXML
    private TextField idTicketTextField;

    @FXML
    private TextField userIdTextField;

    @FXML
    private TextField documentIdTextField;

    @FXML
    private DatePicker borrowedDateTextField;

    @FXML
    private DatePicker returnedDateTextField;

    @FXML
    private TextField quantityTextField;


    private ObservableList<Ticket> ticketsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ticketsList = FXCollections.observableArrayList(
                new Ticket("A1", "B2", "C3", LocalDate.of(2005, 10, 21),LocalDate.of(2005, 10, 22),1), // 20/10/2005
                new Ticket("A1", "B2", "C3", LocalDate.of(2005, 10, 21),LocalDate.of(2005, 10, 22),3), // 21/10/2005
                new Ticket("A1", "B2", "C3", LocalDate.of(2005, 10, 21),LocalDate.of(2005, 10, 22),2)  // 10/05/2010
        );
        idticket.setCellValueFactory(new PropertyValueFactory<>("ticketID"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userID"));
        documentId.setCellValueFactory(new PropertyValueFactory<>("documentID"));
        borrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        returnedDate.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        table.setItems(ticketsList);
    }

    public void borrowed (ActionEvent event) {
        if (idTicketTextField.getText().isEmpty() || userIdTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()|| documentIdTextField.getText().isEmpty()|| borrowedDate.getText().isEmpty() || returnedDate.getText().isEmpty() || quantity.getText().isEmpty()  ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        } else {
            Ticket ticket = new Ticket();
            ticket.setTicketID(idTicketTextField.getText());
            ticket.setUserID(userIdTextField.getText());
            ticket.setDocumentID(documentIdTextField.getText());
            ticket.setBorrowedDate(borrowedDateTextField.getValue());
            ticket.setReturnedDate(returnedDateTextField.getValue());
            ticket.setQuantity(Integer.parseInt(quantityTextField.getText()));
            ticketsList.add(ticket);
        }
    }

    public void display(ActionEvent event) {
        Ticket ticket = table.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            idTicketTextField.setText(ticket.getTicketID());
            userIdTextField.setText(ticket.getUserID());
            documentIdTextField.setText(ticket.getDocumentID());
            borrowedDateTextField.setValue(ticket.getBorrowedDate());
            returnedDateTextField.setValue(ticket.getReturnedDate());
            quantityTextField.setText(String.valueOf(ticket.getQuantity()));
        }
    }

    public void returned(ActionEvent event) {
        Ticket ticket = table.getSelectionModel().getSelectedItem();
        if (ticket != null) {
            ticket.setReturnedDate(returnedDateTextField.getValue());
            table.refresh();
        }
    }

}
