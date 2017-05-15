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
public class Detonation extends EntitiesBase{

    private int timeToExpire=0;
    BufferedImage target;
    
    public Detonation(int x,int y,BufferedImage image,BufferedImage target){
        super(x,y);
        this.image=image;
        this.target=target;
    }
    
    public boolean check(){
        if(timeToExpire<=40){
            timeToExpire+=1;
            return true;
        }
        else{return false;}
    }
    
    @Override
    public void paint(Graphics2D g){
        g.drawImage(image,x-timeToExpire/8,y-timeToExpire/8,target.getWidth()+timeToExpire/4,target.getHeight()+timeToExpire/4,ekran);
    }
}
