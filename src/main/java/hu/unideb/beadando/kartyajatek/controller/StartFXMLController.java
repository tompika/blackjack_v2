package hu.unideb.beadando.kartyajatek.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.ChoiceDialog;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class StartFXMLController implements Initializable {

    private static final Logger logger = LogManager.getLogger(StartFXMLController.class);
    
    Controller cont = Controller.getInstance();

    @FXML
    private TextField tfName;

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnHistory;

    @FXML
    private PasswordField pfPassword;

    @FXML
    private Button btnRegistration;

    @FXML
    void registrationExecute(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/Registration.fxml"));
        Scene scene = new Scene(parent);
        scene.getStylesheets().add("styles/Styles.css");

        Stage stage = new Stage();
        stage.setTitle("Regisztráció");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    private void textFieldEnter(ActionEvent event) throws IOException {
        if (!tfName.getText().isEmpty() && !pfPassword.getText().isEmpty()) {

         /*   if (cont.Login(tfName.getText(), pfPassword.getText())) {
                logger.info("Sikeres belepes!");
                         
                

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Hiba");
                alert.setHeaderText("Sikertelen bejelentkezés");
                alert.setContentText("Hibás felhasználónév és/vagy jelszó!");
                alert.show();

                return;
            }*/

         
            List<String> choices = new ArrayList<>();
            choices.add("1");
            choices.add("2");
            choices.add("3");

            ChoiceDialog<String> dialog = new ChoiceDialog<>("1", choices);
            dialog.setTitle("Paklik száma");
            dialog.setHeaderText(null);
            dialog.setContentText("Válassza ki, hogy hány paklival szeretne játszani:");

            // Traditional way to get the response value.
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()){
                
            }

            
            
            
            
           // cont.setPlayerName(tfName.getText());
           // logger.info("Jatekos neve beallitva: " + cont.getPlayerName());
            
            tfName.setText("");
            pfPassword.setText("");
            
            Parent login_parent = FXMLLoader.load(getClass().getResource("/GameFxml.fxml"));
            Scene gameScene = new Scene(login_parent);
            
            gameScene.getStylesheets().add("styles/Styles.css");
            //Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Stage stage = new Stage();

            stage.setTitle("BlackJack");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(gameScene);
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.show();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Hiba");
            alert.setHeaderText(null);
            alert.setContentText("Kérem ellenőrizze a beírt adatot!\nA mező nem lehet üres!");
            alert.show();
        }

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        //Data data = new Data();
        //data.deleteUser("wasd");
        
        btnHistory.setVisible(false);
    }
    
    public void setNickNameField(String value){
        tfName.setText(value);
    }
    
    public void setPasswordField(String value){
        pfPassword.setText(value);
    }
    
    public String getNickNameTextFieldValue(){
        return tfName.getText();
    }
    
    public String getPasswordFieldValue(){
        return pfPassword.getText();
    }

}
