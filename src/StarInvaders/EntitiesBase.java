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
 * @author Lama
 */
public class EntitiesBase {
    
    protected int x,y;
    protected final int width=1280,height=1024;
    protected BufferedImage image;
    protected GamePanel ekran;
    
    public EntitiesBase(int x,int y){
        this.x=x;
        this.y=y;
    }

    public void paint(Graphics2D g){
        g.drawImage(image, x, y,ekran);
    }
    
    public Rectangle entitySquare(){
        return new Rectangle(x,y,image.getWidth(),image.getHeight());
    }

    public void setX(int x){ this.x = x; }
    public int getX(){ return x; }
    
    public void setY(int y){ this.y = y; }
    public int getY(){ return y; }
}
