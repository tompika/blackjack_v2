package hu.unideb.beadando.kartyajatek.view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import hu.unideb.beadando.kartyajatek.Data;
import hu.unideb.beadando.kartyajatek.Round;
import java.util.logging.Level;

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
        
        try {
            roundView.setItems(data.getRoundData());
        } catch (IOException ex) {
            logger.info(ex.getStackTrace());
        } catch (ParserConfigurationException ex) {
            logger.info(ex.getStackTrace());
        } catch (SAXException ex) {
            logger.info(ex.getStackTrace());
        }
        logger.info("History loaded!");

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
