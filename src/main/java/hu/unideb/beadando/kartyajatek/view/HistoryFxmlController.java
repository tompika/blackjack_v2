package hu.unideb.beadando.kartyajatek.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import hu.unideb.beadando.kartyajatek.Data;
import hu.unideb.beadando.kartyajatek.Round;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class HistoryFxmlController implements Initializable {

    private static final Logger logger = LogManager.getLogger(HistoryFxmlController.class);

    @FXML
    private TableView<Round> roundView;

    @FXML
    private TableColumn<Round, String> nevCol;

    @FXML
    private TableColumn<Round, String> jatekosCol;

    @FXML
    private TableColumn<Round, String> osztoCol;

    @FXML
    private TableColumn<Round, String> datumCol;

    public void HistoryFxmlController() {

        Data data = new Data();
        //data.setFileName("rounds.xml");
        try {
            data.load("rounds.xml");
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        logger.info("History loaded!");
  
        roundView.setItems(data.getRoundData());

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        HistoryFxmlController();

        nevCol.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        jatekosCol.setCellValueFactory(cellData -> cellData.getValue().playerProperty());
        osztoCol.setCellValueFactory(cellData -> cellData.getValue().osztoProperty());
        datumCol.setCellValueFactory(cellData -> cellData.getValue().datumProperty());

    }

}
