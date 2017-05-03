package hu.unideb.beadando.kartyajatek.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import hu.unideb.beadando.kartyajatek.Card;
import hu.unideb.beadando.kartyajatek.Controller;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class GameFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(GameFxmlController.class);

    Controller cont = Controller.getInstance();

    Card cardPlayer, cardPc;

    List<Card> cardsPlayer = new ArrayList<Card>();
    List<Card> cardsPc = new ArrayList<Card>();

    ImageView imView;
    ImageView imView2;

    public GameFxmlController() {
    }

    @FXML
    private BorderPane gameBorder;

    @FXML
    private HBox hboxPcCard;

    @FXML
    private HBox hboxPlayerCard;

    @FXML
    private Label playerPont;

    @FXML
    private Label pcPont;

    @FXML
    private Label name;

    @FXML
    private Button tart;

    @FXML
    private Button lap;

    @FXML
    private Button buttonTet;

    @FXML
    private TextField textFieldTet;

    @FXML
    private Button buttonUjKor;

    @FXML
    private Label labelEgyenleg;

    @FXML
    private Label labelTet;

    @FXML
    private Label labelName;

    @FXML
    private Label labelInfo;

    @FXML
    private Button btnAdmin;

    @FXML
    private MenuItem miHistory;

    @FXML
    void onActionAdmin(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/AdminFxml.fxml"));
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        stage.setTitle("Felhasznalok");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();

    }

    @FXML
    void mbHistoryOnAction(ActionEvent event) throws IOException {

        Parent parent = FXMLLoader.load(getClass().getResource("/HistoryFxml.fxml"));
        Scene scene = new Scene(parent);

        Stage stage = new Stage();

        stage.setTitle("Eredmények");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        try {

            btnAdmin.setDisable(true);
            btnAdmin.setVisible(false);

            if (cont.getPlayerName().equals("admin")) {
                btnAdmin.setDisable(false);
                btnAdmin.setVisible(true);
            }

            cont.loadCard();

            playerPont.setText("0");
            
            hboxPlayerCard.setAlignment(Pos.CENTER);
            hboxPcCard.setAlignment(Pos.CENTER);

            tart.setDisable(true);
            lap.setDisable(true);

            labelName.setText(cont.getPlayerName());
            labelEgyenleg.setText("10000");
            cont.setEgyenleg(10000);

            BackgroundImage bgimage = new BackgroundImage(new Image("tableBackground.png", 0, 0, false, true),
                    BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                    BackgroundSize.DEFAULT);

            gameBorder.setBackground(new Background(bgimage));

            labelInfo.setText("Sok szerencsét!");
                   

        } catch (IOException ex) {
            logger.error(ex.getStackTrace());
        }

    }

    @FXML
    void ujKor(ActionEvent event) {
        
        
        hboxPcCard.getChildren().clear();
        hboxPlayerCard.getChildren().clear();

        cardsPc.clear();
        cardsPlayer.clear();

        playerPont.setText("0");
        pcPont.setText("0");
        labelInfo.setText("Uj kör kezdődott!");
        
        buttonTet.setDisable(false);
        
    }

    
    @FXML
    private void buttonLapAction(ActionEvent event) throws IOException {

        moveLap(hboxPlayerCard, 0, cardsPlayer);
        playerPont.setText(calculatePoint(cardsPlayer));

        tart.setDisable(false);
        checkToMuch();

    }

    @FXML
    private void buttonTetAction(ActionEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Hiba");
        alert.setHeaderText(null);

        if (!textFieldTet.getText().isEmpty()) {
            if (Integer.parseInt(textFieldTet.getText()) < 0 && 
                    cont.getEgyenleg() >= Integer.valueOf(textFieldTet.getText())) {
                labelInfo.setText("Töltsd fel az egyenleged!");
                alert.setContentText("NO CASH!");
                alert.show();

            } else {

                labelTet.setText(textFieldTet.getText());
                cont.setTet(Integer.valueOf(labelTet.getText()));

                lap.setDisable(false);
                tart.setDisable(false);
                buttonTet.setDisable(true);
                buttonUjKor.setDisable(true);
                
                moveLap(hboxPcCard, 1, cardsPc);
                moveLap(hboxPcCard, 0, cardsPc);
                
                moveLap(hboxPlayerCard, 0, cardsPlayer);
                moveLap(hboxPlayerCard, 0, cardsPlayer);
                
                playerPont.setText(calculatePoint(cardsPlayer));
                pcPont.setText(calculatePoint(cardsPc));
            }
        } else {

            alert.setContentText("Kérem ellenőrizze a beírt adatot!\nA mező nem lehet üres!");
            alert.show();
        }

    }

    public void moveLap(HBox hbox, int back, List<Card> list) {

        Card card;
        if (back == 1) {
            card = cont.getBackCard();
        } else {
            card = cont.getRandomCard();
        }
        
     

        list.add(card);

        Image img = card.getImg();

        imView = new ImageView();
        imView.setImage(img);
        imView.setFitWidth(80);
        imView.setFitHeight(150);
        
        hbox.getChildren().add(imView);

    }

    private void checkToMuch() {

        int player = cardsPlayer.stream()
                .mapToInt(e -> e.getValue())
                .sum();

        int pc = cardsPc.stream()
                .mapToInt(e -> e.getValue())
                .sum();
       
        if(player > 21){
            
            labelInfo.setText("Vesztettél!\nLapjaid összege nagyobb mint 21!");
            cont.roundToFile(cardsPlayer, cardsPc);
            
            cont.calculateEgyenleg(-1);
            labelEgyenleg.setText(String.valueOf(cont.getEgyenleg()));
            
            tart.setDisable(true);
            lap.setDisable(true);
            buttonUjKor.setDisable(false);
            
        }

    }

    @FXML
    public void buttonTart() {

        hboxPcCard.getChildren().remove(0);
        cardsPc.remove(0);
              

        try {
           
            //amíg az oszto lapjaniak értéke nem éri el a 17-et, lapot húz
            while( cardsPc.stream().mapToInt(e -> e.getValue()).sum() < 17){
                Thread.sleep(300);
                moveLap(hboxPcCard, 0, cardsPc);
                
            }
            
                       
            pcPont.setText(calculatePoint(cardsPc));

        } catch (InterruptedException e) {
            logger.warn(e.getStackTrace());
        }
        checkWinner();

    }

    private void startState(){
        
        hboxPcCard.getChildren().clear();
        hboxPlayerCard.getChildren().clear();

        cardsPc.clear();
        cardsPlayer.clear();

        playerPont.setText("0");
        pcPont.setText("0");
        labelInfo.setText("Uj kör kezdődott!");

        tart.setDisable(false);
        lap.setDisable(false);
        
        moveLap(hboxPcCard, 1, cardsPc);
        moveLap(hboxPcCard, 0, cardsPc);
        
        pcPont.setText(calculatePoint(cardsPc));
        
        moveLap(hboxPlayerCard, 1, cardsPlayer);
        moveLap(hboxPlayerCard, 0, cardsPlayer);
        
        playerPont.setText(calculatePoint(cardsPlayer));
        
    }
    
    
    private String calculatePoint(List<Card> list) {

        int num = 0;
        boolean ace = false;

        for (Card card : list) {
            num += card.getValue();
            if (card.isAce()) {
                ace = true;
            }
        }

        return ace ? Integer.toString(num) + " / " + Integer.toString(num + 11) : Integer.toString(num);
    }

    private void checkWinner() {

        if (getWinner().endsWith("GAMEOVER")) {
            labelInfo.setText("Senki sem nyert!");
            
            labelEgyenleg.setText(String.valueOf(cont.getEgyenleg()));
            
            tart.setDisable(true);
            lap.setDisable(true);
            buttonTet.setDisable(true);
            buttonUjKor.setDisable(false);
            
            cont.roundToFile(cardsPlayer, cardsPc);

        } else if (!getWinner().isEmpty()) {
            labelInfo.setText("Nyertes: " + getWinner());
            
            labelEgyenleg.setText(String.valueOf(cont.getEgyenleg()));
                       
            tart.setDisable(true);
            lap.setDisable(true);
            buttonTet.setDisable(true);
            buttonUjKor.setDisable(false);
            
            cont.roundToFile(cardsPlayer, cardsPc);

        }

    }

    private String getWinner() {

        int player = cardsPlayer.stream()
                .mapToInt(e -> e.getValue())
                .sum();

        int pc = cardsPc.stream()
                .mapToInt(e -> e.getValue())
                .sum();

        int playerAce = player;
        int pcAce = pc;

        int ace = (int) cardsPlayer.stream()
                .filter(e -> e.isAce())
                .count();

        int acePc = (int) cardsPc.stream()
                .filter(e -> e.isAce())
                .count();

        if (ace > 0) {
            playerAce += 11;
        }
        if (acePc > 0) {
            pcAce += 11;
        }

        if (pc == player) {
            logger.info("Dontetlen!");
            cont.calculateEgyenleg(0);
            return "DONTETLEN";
        }
          
        else if (pc > 21 && player <= 21) {
            logger.info("Nyertes jatekos: " + cont.getPlayerName());
            cont.calculateEgyenleg(1);
            return cont.getPlayerName();
        }

      
        else if (pc <= 21 && player <= 21) {
            if ( (21 - player < 21 - pc ) || (21-playerAce < 21 - pcAce) ) {
                logger.info("Nyertes jatekos: " + cont.getPlayerName());
                cont.calculateEgyenleg(1);
                return cont.getPlayerName(); 
            }
        }

        else if (player > 21 && pc <= 21) {
            logger.info("Nyertes jatekos: OSZTO");
            cont.calculateEgyenleg(-1);
            return "Oszto";

        }

        return "";
    }
}
