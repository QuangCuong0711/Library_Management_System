package sourceCode.Controllers;

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
import sourceCode.Models.Document;

public class DocumentController extends MenuController implements Initializable {

    @FXML
    private TableView<Document> table;

    @FXML
    private TableColumn<Document, String> id;

    @FXML
    private TableColumn<Document, String> title;

    @FXML
    private TableColumn<Document, String> authors;

    @FXML
    private TableColumn<Document, String> location;

    @FXML
    private TableColumn<Document, String> quantity;

    @FXML
    private TableColumn<Document, String> price;

    @FXML
    private TableColumn<Document, Date> publicationDate;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField titleTextField;

    @FXML
    private TextField authorTextField;

    @FXML
    private TextField locationTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private DatePicker publicationDateDatePicker;

    private ObservableList<Document> documentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        documentList = FXCollections.observableArrayList(
                new Document("V1", "Vật lí đại cương 1", 50, "A15", "Lương Duyên Bình", 25000, LocalDate.of(2005, 10, 20)), // 20/10/2005
                new Document("V2", "Vật lí đại cương 2", 50, "A16", "Lương Duyên Bình", 25000, LocalDate.of(2005, 10, 21)), // 21/10/2005
                new Document("OOP1", "Lập trình hướng đối tượng 1", 50, "B1", "Trần Thị Minh Châu", 50000, LocalDate.of(2010, 5, 10))  // 10/05/2010
        );
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        authors.setCellValueFactory(new PropertyValueFactory<>("authors"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        publicationDate.setCellValueFactory(new PropertyValueFactory<>("publicationDate"));
        table.setItems(documentList);
    }

    public void add (ActionEvent event) {
        if (idTextField.getText().isEmpty() || titleTextField.getText().isEmpty() || quantityTextField.getText().isEmpty()|| priceTextField.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fill all the fields");
            alert.showAndWait();
            return;
        } else {
            Document document = new Document();
            document.setId(idTextField.getText());
            document.setTitle(titleTextField.getText());
            document.setAuthors(authorTextField.getText());
            document.setLocation(locationTextField.getText());
            document.setQuantity(Integer.parseInt(quantityTextField.getText()));
            document.setPrice(Double.parseDouble(priceTextField.getText()));
            document.setPublicationDate(publicationDateDatePicker.getValue());
            documentList.add(document);
        }
    }

    public void delete(ActionEvent event) {
        Document document = table.getSelectionModel().getSelectedItem();
        if (document != null) {
            documentList.remove(document);
        }
    }

    public void edit(ActionEvent event) {
        Document document = table.getSelectionModel().getSelectedItem();
        if (document != null) {
            document.setId(idTextField.getText());
            document.setTitle(titleTextField.getText());
            document.setAuthors(authorTextField.getText());
            document.setLocation(locationTextField.getText());
            document.setQuantity(Integer.parseInt(quantityTextField.getText()));
            document.setPrice(Double.parseDouble(priceTextField.getText()));
            document.setPublicationDate(publicationDateDatePicker.getValue());
            table.refresh();
        }
    }

    public void display(ActionEvent event) {
        Document document = table.getSelectionModel().getSelectedItem();
        if (document != null) {
            idTextField.setText(document.getId());
            titleTextField.setText(document.getTitle());
            authorTextField.setText(document.getAuthors());
            locationTextField.setText(document.getLocation());
            quantityTextField.setText(String.valueOf(document.getQuantity()));
            priceTextField.setText(String.valueOf(document.getPrice()));
            publicationDateDatePicker.setValue(document.getPublicationDate());
        }
    }
}
