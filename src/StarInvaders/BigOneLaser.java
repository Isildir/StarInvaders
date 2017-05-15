/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author Raith
 */
public class BigOneLaser extends BulletEntities{
    
    private int timer=-200;
    
    public BigOneLaser(int x, int y, BufferedImage image) {
        super(x, y, image);
    }
    
    @Override
    public void update(){
        timer++;
    }
    
    public void setPosition(int x,int y){
        this.x=x;
        this.y=y;
    }

    @Override
    public boolean checkRange(){
        return timer>500;
    }
    
    @Override
    public int getDamage(){
        return 80;
    }
   
    public void changeTimer(){
        timer=550;
    }
    
    @Override
    public void paint(Graphics2D g){
        g.setColor(Color.red);
        if(timer<=0 && timer%4<=-1){for(int i=y;i<=height+100;i+=20){g.fillRect(x-1, i-10, 1, 15);}}
        else if(timer>0 && timer<=200){g.drawImage(image, x-(image.getWidth()+timer/10)/2, y,image.getWidth()+timer/10,y+height-y+50,ekran);}
        else if(timer>200 && timer<=300){g.drawImage(image, x-(image.getWidth()+200/10)/2, y,image.getWidth()+200/10,y+height-y+50,ekran);}
        else if(timer>300 && timer<=500){g.drawImage(image, x-(image.getWidth()+(400/10-(timer-100)/10))/2, y,image.getWidth()+(400/10-(timer-100)/10),y+height-y+50,ekran);}
    }
    
    @Override
    public Rectangle entitySquare(){
        if(timer>0 && timer<=200){return new Rectangle( x-(image.getWidth()+timer/10)/2, y,image.getWidth()+timer/10,y+height-y+50);}
        else if(timer>200 && timer<=300){return new Rectangle( x-(image.getWidth()+200/10)/2, y,image.getWidth()+200/10,y+height-y+50);}
        else if(timer>300 && timer<=500){return new Rectangle( x-(image.getWidth()+(400/10-(timer-100)/10))/2, y,image.getWidth()+(400/10-(timer-100)/10),y+height-y+50);}
        else{return new Rectangle(x,y,1,1);}
    }
}
