import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import utils.Configuration;
import utils.ConnectionDB;
import utils.ScreenSize;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class PatientRegistration implements Initializable
{
    //    Patient Registration...

    Stage stage;

    String setColor, setFont;

    @FXML
    private GridPane patientRegistrationPage;

    @FXML
    private TextField patientName;

    @FXML
    private TextField patientSurname;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField roomNumber;

    @FXML
    private TextField bedNumber;

    @FXML
    private Button submit;

    @FXML
    private Button cancel;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        System.out.println(Configuration.getColor() + " " + Configuration.getFont());
        setColor = Configuration.getColor();
        setFont = Configuration.getFont();

        if(patientRegistrationPage != null)
            patientRegistrationPage.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);

        UnaryOperator<TextFormatter.Change> filter = change -> {
          String text = change.getText();

          if(text.matches("^[0-9]+$") | change.isDeleted())
              return change;

          else return null;
        };

        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        try{
            phoneNumber.setTextFormatter(textFormatter);}catch (Exception ex){}
    }

        public void newPatientClick(ActionEvent actionEvent)
    {
        patientName.setText("");
        patientSurname.setText("");
        phoneNumber.setText("");
        roomNumber.setText("");
        bedNumber.setText("");
        patientName.requestFocus();
    }


    public void onSubmitClick(ActionEvent actionEvent)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setTitle("INFORMATION");

        if(patientName.getText().equals(""))
        {
            alert.setContentText("Patient name cannot be empty");
            alert.showAndWait();
        }

        else if(patientSurname.getText().equals(""))
        {
            alert.setContentText("Patient surname cannot be empty");
            alert.showAndWait();
        }

        else if(phoneNumber.getText().equals(""))
        {
            alert.setContentText("Patient phone number cannot be empty");
            alert.showAndWait();
        }

        else if(roomNumber.getText().equals(""))
        {
            alert.setContentText("Patient room number cannot be empty");
            alert.showAndWait();
        }

        else if(bedNumber.getText().equals(""))
        {
            alert.setContentText("Patient bed number cannot be empty");
            alert.showAndWait();
        }

        else{

            try {
                ConnectionDB.dbExecuteUpdate("insert into patients values ('" + patientName.getText() + "','" + patientSurname.getText()
                        + "'," + phoneNumber.getText() + ",'" + roomNumber.getText() + bedNumber.getText());
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

//Search patients Screen...

    @FXML
    private GridPane patientListSearch;


    public void searchPatient(ActionEvent actionEvent)
    {
        try {
            Stage stage = (Stage) submit.getScene().getWindow();
            Parent searchPatient = FXMLLoader.load(getClass().getResource("views/PatientListSearch.fxml"));
            Stage searchPatientDialog = new Stage();
            searchPatientDialog.setTitle("SEARCH PATIENT");
            searchPatientDialog.initOwner(stage);
            searchPatientDialog.initModality(Modality.APPLICATION_MODAL);


            patientListSearch = (GridPane)searchPatient.lookup("#patientListSearch");

            if(patientListSearch != null)
                patientListSearch.setStyle("-fx-background-color: " + setColor + "; -fx-font-size :" + setFont);


            Scene scene = new Scene(searchPatient, 500, 500);
            searchPatientDialog.setScene(scene);

            searchPatientDialog.show();
        }

        catch (Exception ex){ ex.printStackTrace();}
    }


    //Delete Patient pop up...

    @FXML private GridPane deletePatientBack;

    public void deletePatient(ActionEvent actionEvent)
    {
        try {
            Stage stage = (Stage) submit.getScene().getWindow();
            Parent deletePatient = FXMLLoader.load(getClass().getResource("views/DeletePatient.fxml"));
            Stage searchPatientDialog = new Stage();
            searchPatientDialog.setTitle("DELETE PATIENT");
            searchPatientDialog.initOwner(stage);
            searchPatientDialog.initModality(Modality.APPLICATION_MODAL);

            deletePatientBack = (GridPane)deletePatient.lookup("#deletePatientBack");

            if(deletePatientBack != null)
                deletePatientBack.setStyle("-fx-background-color: " + setColor + "; -fx-font-size :" + setFont);

            Scene scene = new Scene(deletePatient, 500, 500);
            searchPatientDialog.setScene(scene);

            searchPatientDialog.show();
        }
        catch (Exception ex){}

    }

    public void regCancelClick(ActionEvent actionEvent)
    {
        try {
            stage = (Stage) cancel.getScene().getWindow();
            Parent mainMenu = FXMLLoader.load(getClass().getResource("views/MainMenu.fxml"));

            GridPane mainMenuPage = (GridPane) mainMenu.lookup("#mainMenuPage");


            if(mainMenuPage != null)
                mainMenuPage.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);
            Scene scene = new Scene(mainMenu, ScreenSize.getScreenWidth(), ScreenSize.getScreenHeight() - 60);
            stage.setMaximized(true);
            stage.setScene(scene);
        }
        catch (Exception ex){}
    }
}
