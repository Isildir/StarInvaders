/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Raith
 */
public class MissleDetonation extends EntitiesBase{
    
    private int timeToExpire=0;
    
    public MissleDetonation(int x,int y,BufferedImage image){
        super(x,y);
        this.image=image;
    }
    
    public boolean check(){
        if(timeToExpire<=50){
            timeToExpire+=1;
            return true;
        }
        else{return false;}
    }
    
    @Override
    public void paint(Graphics2D g){
        g.drawImage(image,x-timeToExpire*3/2,y-timeToExpire*3/2,image.getWidth()+timeToExpire*3,image.getHeight()+timeToExpire*3,ekran);
    }
    
    @Override
    public Rectangle entitySquare(){
        return new Rectangle(x-timeToExpire*3/2,y-timeToExpire*3/2,image.getWidth()+timeToExpire*3,image.getHeight()+timeToExpire*3);
    }
    
}
