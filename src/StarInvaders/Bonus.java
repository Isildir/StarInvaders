/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.image.BufferedImage;

/**
 *
 * @author tak
 */
public class Bonus extends BulletEntities{
    
    public Bonus(int x, int y, BufferedImage image) {
        super(x, y, image);
    }
    
    public boolean checkRange(){
        return y>height;
    }
    
    @Override
    public void update(){
        y+=2;
    }

}
