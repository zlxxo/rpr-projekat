package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

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

                Stage stage = new Stage();
                Parent root = null;
                try {
                    root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
                    stage.setTitle("Pomoć");
                    stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
                    stage.setResizable(false);
                    stage.show();
                } catch (Exception e) {
                    System.out.println(e);
                }
                Stage stage1 = (Stage) bossUsernameTF.getScene().getWindow();
                stage1.close();
            } else {
                bossPasswordTF.getStyleClass().removeAll("textFieldCorrect");
                bossPasswordTF.getStyleClass().add("textFieldIncorrect");
            }
        }
    }
}
