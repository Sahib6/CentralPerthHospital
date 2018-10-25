import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import utils.Configuration;
import utils.ConnectionDB;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PatientListSearch implements Initializable
{
    @FXML private TableView<Patient> tableView;

    @FXML private TableColumn<Patient, String> patientNameList;

    @FXML private TableColumn<Patient, String> patientId;

    @FXML private TableColumn<Patient, String> patientSurnameList;

    @FXML private Button close;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        tableView.setStyle("-fx-background-color: " + Configuration.getColor() + "; -fx-font-size :" + Configuration.getFont());

        //data setup in Table view...

        List arrayList = new ArrayList<Patient>();
        try{
        ResultSet rs = ConnectionDB.dbExecuteQuery("Select * from patients;");
            while (rs.next())
            {
                arrayList.add(new Patient(rs.getString("patientid"), rs.getString("patientname"), rs.getString("patientsurname")));
            }
        }
        catch (Exception ex){ex.printStackTrace();
        }

        ObservableList<Patient> list = FXCollections.observableArrayList(arrayList);

        patientId.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientId"));
        patientNameList.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientName"));
        patientSurnameList.setCellValueFactory(new PropertyValueFactory<Patient, String>("patientSurname"));

        tableView.setItems(list);
    }

    public void closeClick(ActionEvent actionEvent)
    {
        Stage stage = (Stage)close.getScene().getWindow();
        stage.close();
    }
}
