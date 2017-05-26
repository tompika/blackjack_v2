/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.unideb.beadando.kartyajatek.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import hu.unideb.beadando.kartyajatek.controller.Controller;
/**
 *
 * @author szilvacsku
 */
public class PlayerDAOImpl implements PlayerDAO{

    private static final Logger logger = LogManager.getLogger(PlayerDAOImpl.class);
    
    @Override
    public void registerPlayer(Player player) {
        
        
        String folder_path = System.getProperty("user.home") + File.separator;
        folder_path += File.separator + ".blackjack";
        File customDir = new File(folder_path);

        if (customDir.exists()) {
            logger.info(customDir + " mappa letezik.");
        } else if (customDir.mkdirs()) {
            logger.info(customDir + " mappa letrehozva.");
        } else {
            logger.info(customDir + " mappa letrehozasa nem sikerult.");
        }

        Path path = Paths.get(System.getProperty("user.home"), ".blackjack", Controller.getInstance().getPlayerDataFileName());
        File saveFile = path.toFile();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        if ( !saveFile.exists() ) {
            logger.info("A kimeneti fajl nem letezik. - Letrehozva!\n - " + saveFile.getPath());
            try {

                builder = factory.newDocumentBuilder();

                Document doc = builder.newDocument();

                Element rootElement = doc.createElement("players");
                doc.appendChild(rootElement);

                Element playerElement = doc.createElement("player");

                rootElement.appendChild(playerElement);

                Element name = doc.createElement("nickname");
                name.appendChild(doc.createTextNode(player.getNickname()));
                playerElement.appendChild(name);

                Element password = doc.createElement("password");
                password.appendChild(doc.createTextNode(player.getPassword()));
                playerElement.appendChild(password);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(saveFile);

                transformer.transform(source, result);

            } catch (ParserConfigurationException | TransformerException e) {
                logger.warn(e.getStackTrace());
            }

        } else {
            logger.info("Az adatokat tartalmazo fajl letezik!");
            try {
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                Document doc;

                doc = documentBuilder.parse(saveFile);

                Element root = doc.getDocumentElement();
                Element playerElement = doc.createElement("player");

                Element name = doc.createElement("nickname");
                name.appendChild(doc.createTextNode(player.getNickname()));
                playerElement.appendChild(name);

                Element playerCard = doc.createElement("password");
                playerCard.appendChild(doc.createTextNode(player.getPassword()));
                playerElement.appendChild(playerCard);

                root.appendChild(playerElement);

                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(saveFile);
                // new File("target/" + datee + ".xml"));

                transformer.transform(source, result);

            } catch (SAXException | IOException | ParserConfigurationException | TransformerException e) {
                logger.warn(e.getStackTrace());
            }

        }
        logger.info("Kimeneti fajl: " + saveFile.getPath());
                
    }

    @Override
    public void removePlayer(Player player) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Player getRegisteredPlayer(String nickName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
