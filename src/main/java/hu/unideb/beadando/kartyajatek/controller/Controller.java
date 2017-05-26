package hu.unideb.beadando.kartyajatek.controller;

import hu.unideb.beadando.kartyajatek.controller.GameFXMLController;
import hu.unideb.beadando.kartyajatek.controller.StartFXMLController;
import hu.unideb.beadando.kartyajatek.model.Card;
import hu.unideb.beadando.kartyajatek.model.GameModel;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A projekthez tartozó controller osztály melynek feladata a különböző
 * események kezelése.
 *
 * @author Szilvácsku Péter
 *
 */
public class Controller {

    private static Controller instance = null;
    private static final Logger logger = LogManager.getLogger(Controller.class);

    private GameModel gameModel;
    private GameFXMLController gameView;
    private StartFXMLController startView;

    private final String playerDataFile = "data.xml";
    private final String roundsFileName = "rounds.xml";
    
    
    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    private Controller() {
    }

    public void init() {
        this.gameModel = new GameModel();
    }

    public String getPlayerDataFileName(){
        return playerDataFile;
    }
    
    public String getRoundsDataFileName(){
        return roundsFileName;
    }
    
    public GameModel getGameModel(){
        return gameModel;
    }
            
    public void viewRegister() throws IOException {

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

    public void viewHistory() throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/HistoryFxml.fxml"));
        Scene scene = new Scene(parent);

        Stage stage = new Stage();
        stage.setTitle("Eredmények");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    public void viewGame() throws IOException {

        if (gameModel.Login(startView.getNickNameTextFieldValue(), startView.getPasswordFieldValue()) ) {
            logger.info("Sikeres belepes!");

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Hiba");
            alert.setHeaderText("Sikertelen bejelentkezés");
            alert.setContentText("Hibás felhasználónév és/vagy jelszó!");
            alert.show();

            return;
        }

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
        if (result.isPresent()) {
            gameModel.setPaklikSzama(Integer.valueOf(result.get()));
        }

        Parent login_parent = FXMLLoader.load(getClass().getResource("/GameFxml.fxml"));
        Scene gameScene = new Scene(login_parent);
        gameScene.getStylesheets().add("styles/Styles.css");

        setNickName(startView.getNickNameTextFieldValue());
        logger.info("Jatekos neve beallitva: " + gameModel.getPlayerName());

        Stage stage = new Stage();
        stage.setTitle("BlackJack");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(gameScene);
        stage.centerOnScreen();
        stage.setResizable(false);
        stage.show();
    }

  
    public void loadCard() {
        try {
            gameModel.loadCard();
        } catch (IOException ex) {
            logger.info(ex.getStackTrace());
        }
    }

    int checkLoginName(String nickName) {
        return 0;
    }

 
    public void calculatePoint() {

    }

    public void Login() {

    }

    public Card getBackCard() {
        return gameModel.getBackCard();
    }

    public Card getRandomCard() {
        return gameModel.getRandomCard();
    }

    public void setPaklikSzama(int value) {
        gameModel.setPaklikSzama(value);
    }

    public void setNickName(String value) {
        gameModel.setPlayerName(value);
    }
    
    public void setViewController(StartFXMLController controller){
        this.startView = controller;
        
        logger.info("ViewController beallitva!");
    }

}
