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
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class Controller implements Initializable
{

    private Stage stage;

    @FXML
    private GridPane loginScreenPage;

    @FXML
    private GridPane mainMenuPage;

    @FXML
    private GridPane patientRegistrationPage;

    @FXML
    private GridPane configScreen;

    private String pass = "";
    String setColor, setFont;


    //when page loads
    @Override
        public void initialize(URL location, ResourceBundle resources)
        {
            System.out.println(Configuration.getColor() + " " + Configuration.getFont());
            setColor = Configuration.getColor();
            setFont = Configuration.getFont();

        if(loginScreenPage != null)
            loginScreenPage.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);


        //code snippet to convert password text to *...
        UnaryOperator<TextFormatter.Change> filter = change -> {
          String text = change.getText();

          if(text.matches("^[a-zA-Z0-9]+$"))
          {
              pass = pass + change.getText();
              change.setText("*");
              return change;
          }

          else if(change.isDeleted())
          {
              pass = pass.substring(0, pass.length() - 1);
              return change;
          }
          return null;
        };

        //applying rule on password field...
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        try{
        password.setTextFormatter(textFormatter);}catch (Exception ex){}

    }
    //Login Screen Handling..!!
    @FXML
    private Button cancel;

    @FXML
    private TextField username;

    @FXML
    private TextField password;

    //closing the application...

    @FXML
    public void cancelClick(ActionEvent ex)
    {
        stage = (Stage) cancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void loginClick(ActionEvent e)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFORMATION");
        alert.setHeaderText("");

        if(!pass.equals(""))
            System.out.println(pass);
        else
            System.out.println("empty");

        if(!username.equals("") && !pass.equals("")) {

            ResultSet rs = null;
            try {
                rs = ConnectionDB.dbExecuteQuery("select * from login where username = '" + username.getText() + "';");
                if (!rs.next()) {
                    alert.setContentText("username does not exist..!!");
                    alert.showAndWait();
                    username.setText("");
                    password.setText("");
                }

                else if(rs.getString(1).equals(username.getText()) && !rs.getString(2).equals(pass))
                {
                    alert.setContentText("Password is incorrect..!!");
                    alert.showAndWait();
                    username.setText("");
                    password.setText("");
                }
                if (username.getText().equals(rs.getString(1)) && pass.equals(rs.getString(2))) {
                    try {
                        stage = (Stage) cancel.getScene().getWindow();
                        Parent mainMenu = FXMLLoader.load(getClass().getResource("views/MainMenu.fxml"));
                        mainMenuPage = (GridPane) mainMenu.lookup("#mainMenuPage");
                        if (mainMenuPage != null)
                            mainMenuPage.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);
                        Scene scene = new Scene(mainMenu, ScreenSize.getScreenWidth(), ScreenSize.getScreenHeight() - 60);
                        stage.setMaximized(true);
                        stage.setScene(scene);
                    }
                    catch (Exception ex) {
                }
            }
        }
            catch (Exception ex){ex.printStackTrace();}

        }
        else if(username.getText().equals(""))
        {
            alert.setContentText("Username cannot be empty..!!");
            alert.showAndWait();
            username.setText("");
            password.setText("");

        }

        else if(password.getText().equals(""))
        {
            alert.setContentText("Password cannot be empty..!!");
            alert.showAndWait();
            username.setText("");
            password.setText("");

        }

        else {
            alert.setContentText("Please enter correct username and password..!!");
            alert.showAndWait();
            username.setText("");
            password.setText("");
        }
    }

//    public void passwordKeyPressed(javafx.scene.input.KeyEvent keyEvent) {
//        pass = pass + keyEvent.getText();
//        System.out.println(pass);
//        int pos = pass.length();
//
//        enter = "";
//        for(int i = 0; i < pos; i++)
//        {
//            enter = enter + "*";
//        }
//
//        password.deleteNextChar();
//        password.appendText("*");
//    }



//    Main Menu....

    @FXML
    private Button patientRegistration;

    public void patientRegistrationClick(ActionEvent e)
    {
        try{
        stage = (Stage) patientRegistration.getScene().getWindow();
        Parent patientReg = FXMLLoader.load(getClass().getResource("views/PatientRegistration.fxml"));
        patientRegistrationPage = (GridPane) patientReg.lookup("#patientRegistrationPage");
        if(patientRegistrationPage != null)
            patientRegistrationPage.setStyle("-fx-background-color : " + setColor + "; -fx-font-size :" + setFont);
        Scene scene = new Scene(patientReg, ScreenSize.getScreenWidth(), ScreenSize.getScreenHeight() - 60);
        stage.setMaximized(true);
        stage.setScene(scene);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private Button configuration;

    public void configurationClick(ActionEvent actionEvent)
    {
        try{
            stage = (Stage) configuration.getScene().getWindow();
            Stage configDialog = new Stage();
            configDialog.initModality(Modality.APPLICATION_MODAL);
            configDialog.initOwner(stage);
            configDialog.setTitle("CONFIGURATION");
            Parent config = FXMLLoader.load(getClass().getResource("views/Configuration.fxml"));
            configScreen = (GridPane) config.lookup("#configScreen");
            if(configScreen != null)
                configScreen.setStyle("-fx-background-color: " + setColor + "; -fx-font-size :" + setFont);
            Scene scene = new Scene(config, 600, 600);
            configDialog.setScene(scene);
            configDialog.show();
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @FXML
    private Button close;

    public void closeClick(ActionEvent actionEvent)
    {
        stage = (Stage)  close.getScene().getWindow();
        stage.close();
    }


    //Configuration....

    @FXML
    private Button configCancel;

    @FXML
    private Button configSave;


    public void colorWhiteClick(ActionEvent e)
    {
        setColor = "#ffffff";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }

    public void colorGreyClick(ActionEvent e)
    {
        setColor = "#696969";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }


    public void colorBlackClick(ActionEvent actionEvent)
    {
        setColor = "#000000";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }


    public void font8Click(ActionEvent actionEvent)
    {
        setFont = "8";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }

    public void font10Click(ActionEvent actionEvent)
    {
        setFont = "10";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }

    public void font12Click(ActionEvent actionEvent)
    {
        setFont = "12";
        configScreen.setStyle("-fx-font-size :"+ setFont + ";" + "-fx-background-color : " + setColor);
    }

    public void configSaveClick(ActionEvent actionEvent)
    {
       Configuration configuration =  new Configuration(setColor, setFont);
       configuration.writeToFile();
        stage = (Stage) configSave.getScene().getWindow();
        stage.close();
    }

    public void configCancelClick(ActionEvent actionEvent)
    {
        stage = (Stage)configCancel.getScene().getWindow();
        stage.close();
    }


}
