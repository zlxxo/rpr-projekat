package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class BossLogInController {

    public TextField bossUsernameTF;
    public TextField bossPasswordTF;


    public void logIn(ActionEvent actionEvent) {
        DoctorsOfficeDAO.removeInstance();
        DoctorsOfficeDAO d = DoctorsOfficeDAO.getInstance();
        Boss boss = d.getBoss(bossUsernameTF.getText());
        if(boss == null) {
            bossUsernameTF.getStyleClass().removeAll("textFieldCorrect");
            bossUsernameTF.getStyleClass().add("textFieldIncorrect");
            bossPasswordTF.getStyleClass().removeAll("textFieldCorrect");
            bossPasswordTF.getStyleClass().add("textFieldIncorrect");
        } else {
            bossUsernameTF.getStyleClass().removeAll("textFieldIncorrect");
            bossUsernameTF.getStyleClass().add("textFieldCorrect");
            if(boss.correctPassword(bossPasswordTF.getText())) {
                bossPasswordTF.getStyleClass().removeAll("textFieldIncorrect");
                bossPasswordTF.getStyleClass().add("textFieldCorrect");
            } else {
                bossPasswordTF.getStyleClass().removeAll("textFieldCorrect");
                bossPasswordTF.getStyleClass().add("textFieldIncorrect");
            }
        }
    }
}
