package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class BossController implements Initializable {

    public TableView<Doctor> doctorsTV;
    public TableView<Patient> patientsTV;
    public TableColumn colDFirstName;
    public TableColumn colDLastName;
    public TableColumn colSpetialization;
    public TableColumn colLicenceNumber;
    public TableColumn colPFirstName;
    public  TableColumn colPLastName;
    public TableColumn colPIN;
    public TableColumn<Patient, String> colMHNumber;
    private DoctorsOfficeDAO dao;
    private ObservableList<Patient> patients;
    private ObservableList<Doctor> doctors;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = DoctorsOfficeDAO.getInstance();
        doctors = FXCollections.observableArrayList(dao.getDoctors());
        patients = FXCollections.observableArrayList(dao.getPatients());

        doctorsTV.setItems(doctors);
        patientsTV.setItems(patients);

        colDFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colDLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colSpetialization.setCellValueFactory(new PropertyValueFactory("department"));
        colLicenceNumber.setCellValueFactory(new PropertyValueFactory("licenceNumber"));

        colPFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colPLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colMHNumber.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMedicalHistory().getNumber())));
        colPIN.setCellValueFactory(new PropertyValueFactory("personalIdentificationNumber"));
    }

    public void addDoctor(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/modDoctor.fxml"));
            stage.setTitle("Doktor");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
