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
 * @author tak
 */
public class PlayerLaser extends BulletEntities{
    
    private int charge,damage,dy,width,height;
    public PlayerLaser(int x, int y, BufferedImage image) {
        super(x, y, image);
    }
    
    public boolean checkCharge(){
        charge--;
        return charge>0;
    }
    
    public int getDamage(){
        return damage;
    }
    
    public void setData(int charge,int damage,int dy,int width,int height){
        this.charge=charge;
        this.damage=damage;
        this.dy=dy;
        this.width=width;
        this.height=height;
    }
    
    @Override
    public void update(){
        y-=dy;
    }
    public boolean checkRange(){
        return y<-100;
    }
    
    @Override
    public void paint(Graphics2D g){
        g.drawImage(image, x, y,width,height,ekran);
    }
    
    @Override
    public Rectangle entitySquare(){
        return new Rectangle(x,y,width,height*2);
    }
}
