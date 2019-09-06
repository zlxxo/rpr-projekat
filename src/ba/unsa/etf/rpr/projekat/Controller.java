package ba.unsa.etf.rpr.projekat;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
    }

    public void logInBoss(ActionEvent actionEvent) {
    }
}
