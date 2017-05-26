package hu.unideb.beadando.kartyajatek.controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import hu.unideb.beadando.kartyajatek.model.Data;
import hu.unideb.beadando.kartyajatek.model.PlayerRecord;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 * FXML Controller class
 *
 * @author szilvacsku
 */
public class AdminFXMLController implements Initializable {

    private static final Logger logger = LogManager.getLogger(GameFXMLController.class);
 
    
    
    @FXML
    private TableView<PlayerRecord> twAdmin;
    
    @FXML
    private TableColumn<PlayerRecord, String> nicknameCol;

    @FXML
    private TableColumn<PlayerRecord, String> passwordCol;
    
       
    
    public void AdminFxmlController(){
        Data data = new Data();
        
  
        try {
            twAdmin.setItems(data.getPlayerRecordData());
            
        } catch (ParserConfigurationException ex) {
            logger.info(ex.getStackTrace());
        } catch (SAXException ex) {
            logger.info(ex.getStackTrace());
        } catch (IOException ex) {
            logger.info(ex.getStackTrace());
        }
        logger.info("Player records loaded!");
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        AdminFxmlController();
        
        nicknameCol.setCellValueFactory(cellData -> cellData.getValue().nicknameProperty());
        passwordCol.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
       
        
    }    
    
}
