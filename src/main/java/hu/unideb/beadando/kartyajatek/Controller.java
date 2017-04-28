package hu.unideb.beadando.kartyajatek;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
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

    private static List<Card> allCard = new ArrayList<Card>();
    private Data data;
    private String nickname;

    public static Controller getInstance() {

        if (instance == null) {
            instance = new Controller();
        }

        return instance;
    }

    private Controller() {
    }

    public void Register() throws IOException {

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

    public boolean RegisterPlayer(String name, String password) {
        EncryptDecrypt crypto = new EncryptDecrypt();

        data = new Data();
        if (data.findUserInData(name)) {
            data.addPlayer(name, crypto.encrypt(password));

            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Hiba");
            alert.setHeaderText("Hibás felhasználói név");
            alert.setContentText("Ezt a felhasználói név már használatban van!");
            alert.showAndWait();

            return false;
        }

    }

    public boolean Login(String userName, String password) {

        if ( getDecryptedPassword(userName).equals(password)){
            setPlayerName(userName);
        }
        
        return getDecryptedPassword(userName).equals(password);
    }

    private String getDecryptedPassword(String userName) {

        EncryptDecrypt crypto = new EncryptDecrypt();

        data = new Data();

        if (!data.getPlayerInformation(userName).isEmpty()) {

            String decrypted = crypto.decrypt(data.getPlayerInformation(userName).get(1));

            if (userName.equals(data.getPlayerInformation(userName).get(0))) {
                return decrypted;
            }

        }

        return "";
    }

    public String getPlayerName() {
        return nickname;
    }


    public void loadCard() throws IOException {
        File folder = new File(Card.class.getClassLoader().getResource("cards/").getFile());
        List<String> fileAll = new ArrayList<>();

        logger.info("Folder isDir: " + folder.isDirectory());
        logger.info("Folder exists: " + folder.exists());

        if (!folder.exists()) {

            CodeSource src = Card.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                URL jar = src.getLocation();
                ZipInputStream zip = new ZipInputStream(jar.openStream());
                while (true) {
                    ZipEntry e;
                    try {
                        e = zip.getNextEntry();

                        if (e == null) {
                            break;
                        }
                        String name = e.getName();

                        if (name.startsWith("cards/") && name.endsWith(".png")) {
                            name = name.replace("cards/", "");
                            fileAll.add(name);

                        }
                    } catch (IOException ex) {
                        java.util.logging.Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
            } else {

                logger.info("BDSRSTN!");
            }

        } else {

            for (File file : folder.listFiles()) {
                fileAll.add(file.getName());
            }

        }

        int val = 0;
        Image im;
        boolean ace;

        for (String filename : fileAll) {
            InputStream is;

            is = Card.class.getClassLoader().getResourceAsStream("cards/" + filename);

            ace = false;

            im = new Image(is);

            String[] str = filename.split("_");
            if (str[0].matches("\\d+")) {
                switch (Integer.valueOf(str[0])) {
                    case 2:
                        val = 2;
                        break;
                    case 3:
                        val = 3;
                        break;
                    case 4:
                        val = 4;
                        break;
                    case 5:
                        val = 5;
                        break;
                    case 6:
                        val = 6;
                        break;
                    case 7:
                        val = 7;
                        break;
                    case 8:
                        val = 8;
                        break;
                    case 9:
                        val = 9;
                        break;
                    case 10:
                        val = 10;
                        break;

                    default:
                        break;
                }

            } else {
                switch (str[0]) {
                    case "ace":
                        val = 1;
                        ace = true;
                        break;
                    case "jack":
                        val = 10;
                        break;
                    case "king":
                        val = 10;
                        break;
                    case "queen":
                        val = 10;
                        break;

                    default:
                        break;
                }
            }
            Card c = new Card(val, im, ace);
            addCard(c);
        }
        Collections.shuffle(allCard);
        logger.info("Cards loaded!");
        logger.info("Cards shuffle: OK");
        logger.info(allCard.size() + " db kartya beolvasva.");

    }

    public void clearCardList() {
        allCard.clear();
    }

    public List<Card> getAllCard() {
        return allCard;

    }

    public Card getRandomCard() {
        int index = new Random().nextInt(allCard.size());
        if (allCard.size() > 0) {
            logger.info("Lapok szama a pakliban: " + Integer.toString(allCard.size()-1));
            return allCard.remove(index);
            
        } else {
            logger.warn("Nincs beolvasott kartyalap!");
            return getBackCard();
        }
        
        

    }

    public static void addCard(Card c) {
        allCard.add(c);
    }

    public Card getBackCard() {
        Card back = new Card(0, new Image(Card.class.getClassLoader().getResourceAsStream("cards/0_back.png")), false);

        return back;
    }

    public void setPlayerName(String nickName){
        this.nickname = nickName;
        
    }
    public void toFile() {

        //data.addRoundToFile(gameModel.getPlayer().getCards(), gameModel.getOszto().getCards(), gameModel.getPlayerName());
        logger.info("Jelenlegi kor kiirasra kerult.");

    }

}
