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
    public TableColumn<Doctor, String> colSpetialization;
    public TableColumn colLicenceNumber;
    public TableColumn colPFirstName;
    public  TableColumn colPLastName;
    public TableColumn colPIN;
    public TableColumn<Patient, String> colMHNumber;
    private DoctorsOfficeDAO dao;
    private ObservableList<Patient> patients;
    private ObservableList<Doctor> doctors;
    public TableView<CheckUp> checkUpsTV;
    public TableColumn<CheckUp, String> colFirstName;
    public TableColumn<CheckUp, String> colLastName;
    public TableColumn<CheckUp, String> colDate;
    public TableColumn<CheckUp, String> colTime;
    private ObservableList<CheckUp> checkUps;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = DoctorsOfficeDAO.getInstance();
        doctors = FXCollections.observableArrayList(dao.getDoctors());
        patients = FXCollections.observableArrayList(dao.getPatients());
        checkUps = FXCollections.observableArrayList(dao.getCheckUps());
        if(checkUps.size() == 0) System.out.println("Greška");
        doctorsTV.setItems(doctors);
        patientsTV.setItems(patients);
        checkUpsTV.setItems(checkUps);

        colDFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colDLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colSpetialization.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDepartment()));
        colLicenceNumber.setCellValueFactory(new PropertyValueFactory("licenceNumber"));

        colPFirstName.setCellValueFactory(new PropertyValueFactory("firstName"));
        colPLastName.setCellValueFactory(new PropertyValueFactory("lastName"));
        colMHNumber.setCellValueFactory(data -> new SimpleStringProperty(Integer.toString(data.getValue().getMedicalHistory().getNumber())));
        colPIN.setCellValueFactory(new PropertyValueFactory("personalIdentificationNumber"));

        colFirstName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatient().getFirstName()));
        colLastName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatient().getLastName()));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        colTime.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().toString()));
    }

    public void addDoctor(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modDoctor.fxml"));
            ModDoctorController controller = new ModDoctorController(null);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Doktor");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                Doctor doctor = controller.getDoctor();
                if (doctor != null) {
                    doctors.setAll(dao.getDoctors());
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateDoctor(ActionEvent actionEvent) {
        Doctor doctor = doctorsTV.getSelectionModel().getSelectedItem();
        if (doctor == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/modDoctor.fxml"));
            ModDoctorController controller = new ModDoctorController(doctor);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Doktor");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                Doctor doctor1 = controller.getDoctor();
                if (doctor1 != null) {
                    doctors.setAll(dao.getDoctors());
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addPatient(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addPatient.fxml"));
            AddPatientController controller = new AddPatientController(null, null);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Pacijent");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                Patient patient = controller.getPatient();
                if (patient != null) {
                    patients.setAll(dao.getPatients());
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void checkUp(ActionEvent actionEvent) {
        CheckUp checkUp = checkUpsTV.getSelectionModel().getSelectedItem();
        if (checkUp == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkUp.fxml"));
            CheckUpController controller = new CheckUpController(checkUp);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Pregled");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                CheckUp checkUp1 = controller.getCheckUp();
                if (checkUp != null) {
                    checkUps.setAll(dao.getCheckUps());
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
