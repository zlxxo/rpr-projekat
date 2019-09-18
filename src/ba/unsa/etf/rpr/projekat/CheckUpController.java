package ba.unsa.etf.rpr.projekat;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CheckUpController implements Initializable {

    private CheckUp checkUp;

    public Label nameL;
    public ComboBox<String> allergiesCB;
    public ComboBox<String> diseasesCB;
    public TextArea diagnosisTA;
    private ObservableList<String> allergies;
    private ObservableList<String> diseases;
    private DoctorsOfficeDAO dao;


    public CheckUpController(CheckUp checkUp) {
        this.checkUp = checkUp;
        dao = DoctorsOfficeDAO.getInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameL.setText(checkUp.getPatient().toString());
        diagnosisTA.setText(checkUp.getDiagnosis());
        allergies = FXCollections.observableArrayList(checkUp.getPatient().getMedicalHistory().getAllergies());
        diseases = FXCollections.observableArrayList(checkUp.getPatient().getMedicalHistory().getDiseases());

        allergiesCB.setItems(allergies);
        diseasesCB.setItems(diseases);
    }

    public void finish(ActionEvent actionEvent) {
        dao.updateCheckUp(checkUp.getDoctor().getUsername(), checkUp.getPatient().getMedicalHistory().getNumber(), diagnosisTA.getText(),
                checkUp.getDate(), checkUp.getTime());
        Stage stage = (Stage) nameL.getScene().getWindow();
        stage.close();
    }

    public CheckUp getCheckUp() {
        return checkUp;
    }

    public void setCheckUp(CheckUp checkUp) {
        this.checkUp = checkUp;
    }
}
