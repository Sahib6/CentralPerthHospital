import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import utils.Configuration;
import utils.ConnectionDB;

import java.net.URL;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class DeletePatient implements Initializable
{
    @FXML private Button cancel;

    @FXML private GridPane deletePatientBack;

    @FXML private TextField patientId;

    @FXML private Button confirm;

    public void cancelClick(ActionEvent actionEvent)
    {
        Stage stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
       String setColor = Configuration.getColor();
       String setFont = Configuration.getFont();

        if(deletePatientBack != null)
            deletePatientBack.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);

    }

    public void confirmClick(ActionEvent actionEvent)
    {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("INFORMATION");
            alert.setHeaderText("");
            if(patientId.getText().isEmpty())
            {
                alert.setContentText("Patient ID is empty...!!");
                alert.showAndWait();
            }

            else {
                ResultSet rs = ConnectionDB.dbExecuteQuery("Select * from patients where patientid = '" + patientId.getText() + "';");
                if(!rs.next())
                {
                    alert.setContentText("Patient ID does not exist..!!");
                    alert.showAndWait();
                    patientId.setText("");
                }

                else {
                    ConnectionDB.dbExecuteQuery("Delete from patients where patientid = '" + patientId.getText() + "';");
                    alert.setContentText(patientId.getText() + "deleted!");
                    alert.showAndWait();
                    patientId.setText("");
                }
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
}
