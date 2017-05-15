/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.image.BufferedImage;

/**
 *
 * @author Raith
 */
public class PlayerMissle extends BulletEntities{
    
    private int dy=2;
    
    public PlayerMissle(int x, int y, BufferedImage image) {
        super(x, y, image);
    }
    
    public void setData(int dy){
        this.dy=dy;
    }

    @Override
    public void update(){
        y-=dy;
    }
    
    public boolean checkRange(){
        return y<-100;
    }
    
}
