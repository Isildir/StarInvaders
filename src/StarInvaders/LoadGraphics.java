/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Raith
 */
public class LoadGraphics {
    
    ImageLoader load;
    
    public LoadGraphics(){
        load = new ImageLoader();
    }
    
    public void loadGraphics(Map<Integer,BufferedImage> map) {
        map.put(1, load.loadImage("img/playerShip.gif"));
        map.put(2, load.loadImage("img/flagShip.png"));
        map.put(3, load.loadImage("img/strike.png"));
        map.put(4, load.loadImage("img/enemyLaser.jpg"));
        map.put(5, load.loadImage("img/lightShip.png"));
        map.put(6, load.loadImage("img/mediumShip.png"));
        map.put(7, load.loadImage("img/heavyShip.png"));
        map.put(8, load.loadImage("img/bum.gif"));
        map.put(9, load.loadImage("img/gameBackground.jpg"));
        map.put(10,load.loadImage("img/shield.png"));
        map.put(11,load.loadImage("img/rocketBum.png"));
        map.put(12,load.loadImage("img/bonus.png"));
        map.put(13,load.loadImage("img/rocket.png"));
        map.put(14,load.loadImage("img/roundBullet.png"));
        map.put(15,load.loadImage("img/playerLaser2.png"));
        map.put(16,load.loadImage("img/playerLaser3.png"));
        map.put(17,load.loadImage("img/menuBackground.jpg"));
        map.put(18,load.loadImage("img/engine.png"));
        map.put(19,load.loadImage("img/playerLaser1.jpg"));
    }
    
}
