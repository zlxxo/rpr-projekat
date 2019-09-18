package ba.unsa.etf.rpr.projekat;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import static javafx.scene.layout.Region.USE_PREF_SIZE;

public class DoctorController implements Initializable {

    private SpecializedDoctor doctor;

    public TableView<CheckUp> checkUpsTV;
    public TableColumn<CheckUp, String> colFirstName;
    public TableColumn<CheckUp, String> colLastName;
    public TableColumn<CheckUp, String> colDate;
    public TableColumn<CheckUp, String> colTime;

    private DoctorsOfficeDAO dao;
    private ObservableList<CheckUp> checkUps;


    public DoctorController(SpecializedDoctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dao = DoctorsOfficeDAO.getInstance();

        checkUps = FXCollections.observableArrayList(dao.getCheckUps(doctor.getUsername()));
        checkUpsTV.setItems(checkUps);

        colFirstName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatient().getFirstName()));
        colLastName.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPatient().getLastName()));
        colDate.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDate().toString()));
        colTime.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTime().toString()));
    }


    public void addCheckUp(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/addCheckUp.fxml"));
            AddCheckUpController controller = new AddCheckUpController(doctor);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Pregled");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                CheckUp checkUp = controller.getCheckUp();
                if (checkUp != null) {
                    checkUps.setAll(dao.getCheckUps(doctor.getUsername()));
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void checkUp(ActionEvent actionEvent) {
        CheckUp checkUp = checkUpsTV.getSelectionModel().getSelectedItem();
        if (checkUp == null) return;

        Stage stage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/checkUp.fxml"));
            CheckUpController controller = new CheckUpController(checkUp);
            loader.setController(controller);
            root = loader.load();
            stage.setTitle("Pregled");
            stage.setScene(new Scene(root, USE_PREF_SIZE, USE_PREF_SIZE));
            stage.setResizable(false);
            stage.show();


            stage.setOnHiding( event -> {
                CheckUp checkUp1 = controller.getCheckUp();
                if (checkUp != null) {
                    checkUps.setAll(dao.getCheckUps(doctor.getUsername()));
                }
            } );
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public SpecializedDoctor getDoctor() {
        return doctor;
    }

    public void setDoctor(SpecializedDoctor doctor) {
        this.doctor = doctor;
    }
}
