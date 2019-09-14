package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ModDoctorController implements Initializable {

    public TextField firstNameTF;
    public TextField lastNameTF;
    public ComboBox<String> departmentCB;
    public TextField licenceTF;
    public Button addDoctorBtn;
    private Doctor doctor;
    private boolean add = false;
    private DoctorsOfficeDAO dao;
    private ObservableList<String> departmetns;


    public ModDoctorController(Doctor doctor) {
        this.doctor = doctor;
        if(doctor == null) add = true;
        dao = DoctorsOfficeDAO.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmetns = FXCollections.observableArrayList(dao.getDepartments());
        departmentCB.setItems(departmetns);
        if(add) {
            departmentCB.setValue("");
        } else {
            firstNameTF.setText(doctor.getFirstName());
            lastNameTF.setText(doctor.getLastName());
            licenceTF.setText(doctor.getLicenceNumber());
            departmentCB.setValue(doctor.getDepartment());
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

    private boolean correctLicence(String licence) {
        if(!add && licence.equals(doctor.getLicenceNumber())) return true;
        ArrayList<String> licences = dao.getLicences();

        if(licences.contains(licence)) return false;

        if(licence.equals("")) return false;
        try {
            int a = Integer.parseInt(licence);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;

    }

    public void addDoctor(ActionEvent actionEvent) {
        String name = firstNameTF.getText().trim(), lastName = lastNameTF.getText().trim(),
                department = departmentCB.getValue(), licence = licenceTF.getText().trim();

        boolean OK = true;

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

        if(department.equals("")) {
            OK = false;
        }

        if(correctLicence(licence)) {
            licenceTF.getStyleClass().removeAll("textFieldIncorrect");
            licenceTF.getStyleClass().add("textFieldCorrect");
        } else {
            licenceTF.getStyleClass().removeAll("textFieldCorrect");
            licenceTF.getStyleClass().add("textFieldIncorrect");
            OK = false;
        }

        if(OK) {
            if(add) {
                dao.addDoctor(name, lastName, department, licence);
                doctor = new SpecializedDoctor(name, lastName, "", "", licence, department);
            } else {
                dao.updateDoctor(doctor.getUsername(), name, lastName, department, licence);
            }

            Stage stage = (Stage) firstNameTF.getScene().getWindow();
            stage.close();
        }
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
