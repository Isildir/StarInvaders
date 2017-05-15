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
public class BulletEntities extends EntitiesBase{
    
    public BulletEntities(int x,int y,BufferedImage image){
        super(x,y);
        this.image=image;
    }
    
    protected void update(){}
    protected int getDamage(){return 0;}
    protected boolean checkRange(){return false;}
    
}
