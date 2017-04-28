package hu.unideb.beadando.kartyajatek;

import javafx.scene.image.Image;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * A kártyalapokat reprezentáló osztály, mely tartalmazza a lap tulajdonságait.
 *
 * @author Szilvácsku Péter
 *
 */
public class Card {

    private static final Logger logger = LogManager.getLogger(Card.class);

    private int value;
    private boolean ace = false;
    private Image img;

    public Card(int value, Image img, boolean ace) {
        this.value = value;
        this.img = img;
        this.ace = ace;
    }

    public boolean isAce() {
        return ace;
    }

    public int getValue() {
        return value;
    }

    public Image getImg() {
        return img;
    }

    
    @Override
    public String toString() {
        return Integer.toString(value);
    }

}
