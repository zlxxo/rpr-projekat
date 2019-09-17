package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddCheckUpController implements Initializable {
    private CheckUp checkUp = null;
    private Doctor doctor;
    private ArrayList<Patient> patients;
    private DoctorsOfficeDAO dao;
    private ObservableList<Patient> patientsO;

    public ChoiceBox<Patient> patientCB;
    public ChoiceBox<Doctor> doctorCB;
    public Button addCheckUpBtn;


    public AddCheckUpController(Doctor doctor) {
        this.doctor = doctor;
        dao = DoctorsOfficeDAO.getInstance();
        if(doctor instanceof GeneralPractitioner) patients = ((GeneralPractitioner) doctor).getPatients();
        else patients = dao.getPatients();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        patientsO = FXCollections.observableArrayList(patients);
        patientCB.setItems(patientsO);
        patientCB.setValue(patientsO.get(0));
    }

    public void addCheckUp(ActionEvent actionEvent) {
        Patient patient = patientCB.getValue();
        dao.addCheckUp(doctor.getUsername(), patient.getMedicalHistory().getNumber());
        checkUp = new CheckUp(doctor, patient, LocalDate.now(), LocalTime.now(), "");
        Stage stage = (Stage) patientCB.getScene().getWindow();
        stage.close();
    }

    public CheckUp getCheckUp() {
        return checkUp;
    }

    public void setCheckUp(CheckUp checkUp) {
        this.checkUp = checkUp;
    }
}
