/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Raith
 */
public class ImageLoader {

/**
 * Jedyna metoda klasy przyjmujaca jako parametr sciezke do pliku i wczytujaca go.
 * @param sciezka sciezka do pliku w formacie String
     * @return nie zwraca nic jedynie wczytuje obrazek do systemu
 */
    public BufferedImage loadImage(String sciezka) {
        URL url=null;
        try {
            url = getClass().getClassLoader().getResource(sciezka);
            return ImageIO.read(url);
        }catch (Exception e) {
            System.out.println("Przy otwieraniu " + sciezka +" jako " + url);
            System.out.println("Wystapil blad : "+e.getClass().getName()+""+e.getMessage());
            System.exit(0);
            return null;
        }
    }
}