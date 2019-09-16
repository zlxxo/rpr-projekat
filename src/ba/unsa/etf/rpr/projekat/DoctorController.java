package ba.unsa.etf.rpr.projekat;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class DoctorController implements Initializable {

    private SpecializedDoctor doctor;

    public DoctorController(SpecializedDoctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public SpecializedDoctor getDoctor() {
        return doctor;
    }

    public void setDoctor(SpecializedDoctor doctor) {
        this.doctor = doctor;
    }
}
