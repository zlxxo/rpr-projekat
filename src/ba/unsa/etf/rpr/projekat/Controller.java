package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class Controller implements Initializable {

    public ImageView logoIV;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            File file = new File("resources/images/lo.png");
            Image image = new Image(file.toURI().toString());
            logoIV.setImage(image);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void logInDoctor(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/doctorLogIn.fxml"));
            stage.setTitle("Log In");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void logInBoss(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;

        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/bossLogIn.fxml"));
            stage.setTitle("Log In");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
