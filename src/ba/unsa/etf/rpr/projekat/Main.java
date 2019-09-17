package ba.unsa.etf.rpr.projekat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setTitle("Ljekarska ordinacija");
        primaryStage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
        primaryStage.setResizable(false);
        primaryStage.show();

        /*LocalDate d = LocalDate.now();
        System.out.println(d);
        LocalTime t = LocalTime.now();
        System.out.println(t);
        */
    }


    public static void main(String[] args) {
        launch(args);
    }
}
