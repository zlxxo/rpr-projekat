package ba.unsa.etf.rpr.projekat;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class GeneralPractitionerController implements Initializable {

    private GeneralPractitioner dcctor;

    public GeneralPractitionerController(GeneralPractitioner doctor) {
        this.dcctor = doctor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public GeneralPractitioner getDcctor() {
        return dcctor;
    }

    public void setDcctor(GeneralPractitioner dcctor) {
        this.dcctor = dcctor;
    }
}
