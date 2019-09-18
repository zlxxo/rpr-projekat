package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    public DatePicker dateDP;
    public TextField hourTF;
    public TextField minuteTF;


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

        LocalDate min = LocalDate.now();

        dateDP.setValue(min);

        dateDP.setDayCellFactory(d -> new DateCell() {
            @Override public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                setDisable(item.isBefore(min));
            }});

    }

    public void addCheckUp(ActionEvent actionEvent) {
        boolean OK = true;

        if(correctHour(hourTF.getText())){
            hourTF.getStyleClass().removeAll("textFieldIncorrect");
            hourTF.getStyleClass().add("textFieldCorrect");
        } else {
            hourTF.getStyleClass().removeAll("textFieldCorrect");
            hourTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        if(correctMinute(minuteTF.getText())){
            minuteTF.getStyleClass().removeAll("textFieldIncorrect");
            minuteTF.getStyleClass().add("textFieldCorrect");
        } else {
            minuteTF.getStyleClass().removeAll("textFieldCorrect");
            minuteTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        if(OK) {
            LocalDate date = dateDP.getValue();
            LocalTime time = LocalTime.of(Integer.parseInt(hourTF.getText()), Integer.parseInt(minuteTF.getText()), 0);
            if(correctTime(time, date)) {
                Patient patient = patientCB.getValue();
                dao.addCheckUp(doctor.getUsername(), patient.getMedicalHistory().getNumber(), date, time);
                checkUp = new CheckUp(doctor, patient, date, time, "");
                Stage stage = (Stage) patientCB.getScene().getWindow();
                stage.close();
            } else {
                hourTF.getStyleClass().removeAll("textFieldCorrect");
                hourTF.getStyleClass().add("textFieldIncorrect");
                minuteTF.getStyleClass().removeAll("textFieldCorrect");
                minuteTF.getStyleClass().add("textFieldIncorrect");
            }
        }
    }

    private boolean correctHour(String hour) {

        if(hour.length() == 0) return false;
        try{
            int d = Integer.parseInt(hour);
            if(d < 8 || d > 19) return false;
        }catch (Exception e){
            return false;
        }

        return true;
    }

    private boolean correctMinute(String minute) {

        if(minute.length() == 0) return false;
        try{
            int d = Integer.parseInt(minute);

            if(d < 0 || d > 59) return false;
        }catch (Exception e){
            return false;
        }

        return true;
    }

    public boolean correctTime(LocalTime time, LocalDate date) {
        if(date.equals(LocalDate.now())) return time.compareTo(LocalTime.now()) > 0;
        return true;
    }

    public CheckUp getCheckUp() {
        return checkUp;
    }

    public void setCheckUp(CheckUp checkUp) {
        this.checkUp = checkUp;
    }
}
