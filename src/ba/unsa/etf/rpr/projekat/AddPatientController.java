package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable {

    public TextField firstNameTF;
    public TextField lastNameTF;
    public ChoiceBox<Doctor> doctorCB;
    public TextField PINTF;
    private DoctorsOfficeDAO dao;
    private ObservableList<Doctor> doctors;
    private ArrayList<Doctor> d = new ArrayList<>();
    private Patient patient;

    public AddPatientController(Patient patient) {
        this.patient = patient;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = DoctorsOfficeDAO.getInstance();
        ArrayList<Doctor> d1 = dao.getDoctors();

        for(Doctor d2 : d1) {
            if(d2 instanceof GeneralPractitioner) {
                d.add(d2);
            }
        }

        doctors = FXCollections.observableArrayList(d);
        doctorCB.setItems(doctors);
        doctorCB.setValue(d.get(0));
    }

    public void addPatient(ActionEvent actionEvent) {
        boolean OK = true;

        String name = firstNameTF.getText().trim(), lastName = lastNameTF.getText().trim(), pin = PINTF.getText().trim();

        if(correctName(name)) {
            firstNameTF.getStyleClass().removeAll("textFieldIncorrect");
            firstNameTF.getStyleClass().add("textFieldCorrect");
        } else {
            firstNameTF.getStyleClass().removeAll("textFieldCorrect");
            firstNameTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        if(correctName(lastName)) {
            lastNameTF.getStyleClass().removeAll("textFieldIncorrect");
            lastNameTF.getStyleClass().add("textFieldCorrect");
        } else {
            lastNameTF.getStyleClass().removeAll("textFieldCorrect");
            lastNameTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        if(correctPIN(pin)) {
            PINTF.getStyleClass().removeAll("textFieldIncorrect");
            PINTF.getStyleClass().add("textFieldCorrect");
        } else {
            PINTF.getStyleClass().removeAll("textFieldCorrect");
            PINTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        Doctor d = doctorCB.getValue();

        if(d == null) {
            OK = false;
        }

        if(OK) {
            System.out.println("Sve ok");
            dao.addPatient(pin, name, lastName, d.getUsername());
            patient = new Patient(name, lastName, pin, new MedicalHistory());
            Stage stage = (Stage) firstNameTF.getScene().getWindow();
            stage.close();
        }

    }

    private boolean correctPIN(String pin) {
        if(pin.length()!=13) return false;
        for(int i = 0; i < pin.length(); i++)
            if(!Character.isDigit(pin.charAt(i))) return false;
        try{
            String d = pin.substring(0,2);
            String m = pin.substring(2,4);
            String g = pin.substring(4,7);
            int d1 = Integer.parseInt(d);
            int m1 = Integer.parseInt(m);
            int g1 = Integer.parseInt(g);
            try {
                LocalDate t = LocalDate.of(g1, m1, d1);
            } catch (Exception e) {
                return false;
            }
            return true;
        }catch (Exception e){
            e.getCause();
            return false;
        }
    }

    private boolean correctName(String name) {
        if(name.equals("")) return false;
        boolean previousIsSpace = true;

        for(int i = 0; i < name.length(); i++) {
            char a = name.charAt(i);
            if(!(Character.isLetter(a) || Character.isSpaceChar(a))) {
                return false;
            }
            if(previousIsSpace) {
                if(!Character.isUpperCase(a)) return false;
            }

            if(Character.isSpaceChar(a)) previousIsSpace = true;
            else if(Character.isLetter(a)) previousIsSpace = false;
        }

        return true;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }
}
