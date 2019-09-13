package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ModDoctorController {

    public TextField firstNameTF;
    public TextField lastNameTF;
    public ComboBox<String> departmentCB;
    public TextField licenceTF;
    public Button addDoctorBtn;

    public void addDoctor(ActionEvent actionEvent) {
        System.out.println("Uspio");
    }
}
