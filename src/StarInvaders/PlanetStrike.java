/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Raith
 */
public class PlanetStrike extends BulletEntities{
    
    private final ArrayList<Integer> listX = new ArrayList<>();
    private final ArrayList<Integer> listY = new ArrayList<>();
    
    public PlanetStrike(int x, int y, BufferedImage image) {
        super(x, y, image);
        setPos();
    }
    
    private void setPos(){
        for(int i=0;i<400;i++){
                listX.add((int) (Math.random()*(width-60)+30));
                listY.add((int) (Math.random()*112));
            }
    }
    @Override
    public void update(){
        y-=3;
    }
    
    public boolean checkRange(){
        return y<-200;
    }
    
    public int getDamage(){
        return 30;
    }
    
    @Override
    public void paint(Graphics2D g){
            for(int i=0;i<400;i++){
                g.drawImage(image, listX.get(i), y + listY.get(i), ekran);
            }
    }
    
    @Override
    public Rectangle entitySquare(){
        return new Rectangle(30,y,width-60,120);
    }
    
}
