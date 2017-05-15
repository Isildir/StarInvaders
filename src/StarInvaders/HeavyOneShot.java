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
public class HeavyOneShot extends BulletEntities{

    private double r;
    private final double dx;
    
    public HeavyOneShot(int x, int y, BufferedImage image,int targetX,int targetY) {
        super(x, y, image);
        r=x;
        double dy=(targetY-y)/3;
        dx=(targetX-x)/(dy);
    }
    
    @Override
    public void update(){
        r+=dx;
        y+=3;
    }
    
    public boolean checkRange(){
        return y>height;
    }

    @Override
    public void paint(Graphics2D g){
        g.drawImage(image, (int)r, y,ekran);
    }
    
    @Override
    public Rectangle entitySquare(){
        return new Rectangle((int)r,y,image.getWidth(),image.getHeight());
    }
    
}
