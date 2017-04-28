package hu.unideb.beadando.kartyajatek.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


import hu.unideb.beadando.kartyajatek.Controller;
import javafx.concurrent.Task;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Alert.AlertType;

import javafx.concurrent.Service;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


public class StartFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(StartFxmlController.class);
    
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

        cont.Register();

    }

    void showProgressDialog() {

        Service<Void> service = new Service<Void>() {
        @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call()
                            throws InterruptedException {
                        
                        for (int i = 0; i < 10; i++) {
                            Thread.sleep(300);
                            logger.info(1+i*10 + "%...");
                        }
                        logger.info("---->   Betöltve   <----");
                        return null;
                    }
                };
            }
        };

        service.start();
        

    }

    @FXML
    void buttonEredmeny(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/HistoryFxml.fxml"));
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        stage.setTitle("Eredmények");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    private void textFieldEnter(ActionEvent event) throws IOException {
        if (!tfName.getText().isEmpty() && !pfPassword.getText().isEmpty()) {

            if (cont.Login(tfName.getText(), pfPassword.getText())) {
                logger.info("Sikeres belepes!");
                         
                

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Hiba");
                alert.setHeaderText("Sikertelen bejelentkezés");
                alert.setContentText("Hibás felhasználónév és/vagy jelszó!");
                alert.show();

                return;
            }

            cont.setPlayerName(tfName.getText());
            logger.info("Jatekos neve beallitva: " + cont.getPlayerName());

            Parent login_parent = FXMLLoader.load(getClass().getResource("/GameFxml.fxml"));
            Scene gameScene = new Scene(login_parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
    }

}