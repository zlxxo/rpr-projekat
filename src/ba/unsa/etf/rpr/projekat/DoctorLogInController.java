package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

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
            } else {
                doctorPasswordTF.getStyleClass().removeAll("textFieldCorrect");
                doctorPasswordTF.getStyleClass().add("textFieldIncorrect");
            }
        }
    }
}
