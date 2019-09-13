package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class DoctorLogInController {

    public TextField doctorUsernameTF;
    public TextField doctorPasswordTF;

    public void logIn(ActionEvent actionEvent) {
        DoctorsOfficeDAO.removeInstance();
        DoctorsOfficeDAO d = DoctorsOfficeDAO.getInstance();
        Doctor doctor = d.getDoctor(doctorUsernameTF.getText());
        if(doctor == null) {
            doctorUsernameTF.getStyleClass().removeAll("textFieldCorrect");
            doctorUsernameTF.getStyleClass().add("textFieldIncorrect");
            doctorPasswordTF.getStyleClass().removeAll("textFieldCorrect");
            doctorPasswordTF.getStyleClass().add("textFieldIncorrect");
        } else {
            doctorUsernameTF.getStyleClass().removeAll("textFieldIncorrect");
            doctorUsernameTF.getStyleClass().add("textFieldCorrect");
            if(doctor.correctPassword(doctorPasswordTF.getText())) {
                doctorPasswordTF.getStyleClass().removeAll("textFieldIncorrect");
                doctorPasswordTF.getStyleClass().add("textFieldCorrect");

                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/doctor.fxml"));
                    stage.setTitle(doctor.toString());
                    stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
                    stage.setResizable(true);
                    stage.show();
                } catch (Exception e) {
                    System.out.println(e);
                }
                Stage stage1 = (Stage) doctorUsernameTF.getScene().getWindow();
                stage1.close();
            } else {
                doctorPasswordTF.getStyleClass().removeAll("textFieldCorrect");
                doctorPasswordTF.getStyleClass().add("textFieldIncorrect");
            }
        }
    }
}
