/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Raith
 */
public class LightShipFire extends BulletEntities{
    
    public LightShipFire(int x, int y, BufferedImage image) {
        super(x, y, image);
    }
    @Override
    public void update(){
        y+=4;
    }
    public boolean checkRange(){
        return y>height;
    }
    
    @Override
    public void paint(Graphics2D g){
        g.drawImage(image, x, y,image.getWidth()/2,image.getHeight()/2,ekran);
    }
}
