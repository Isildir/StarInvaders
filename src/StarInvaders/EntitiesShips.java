/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Raith
 */
public class EntitiesShips extends EntitiesBase{

    protected int life,fullLife,shield;
    protected short shieldTime=0,lifeHitTime=0,damageTimer=20;
    protected int takenDamage=0;
    protected Map<Integer,BufferedImage> map;
    
    public EntitiesShips(int x,int y,Map<Integer,BufferedImage> map){
        super(x,y);
        this.map=map;
        fullLife=life;
    }
    
    public BufferedImage getImage(){
        return image;
    }
  
    protected int getScore(){
        return 0;
    }
    
    protected void chanceToBonus(){
        //to use in subclass
    }
    
    protected void update(){
        //to use in subclass
    }

    protected void calculateDamage(int damage){
        int whatsLeft=0;
        damageTimer=0;
        this.takenDamage=damage;
        if(shield>0){
            activateShield();
            if(shield-damage>=0){shield-=damage;}else{whatsLeft=Math.abs(shield-damage);shield=0;}
        }else{whatsLeft=damage;}
        if(whatsLeft>0){
            showDamage();
            life-=whatsLeft;
        }
    }
    
    protected boolean showShield(){
        if(shieldTime>0){
            shieldTime-=1;
            return true;
        }else{
            return false;
        }
    }
    
    protected boolean lifeHit(){
        if(lifeHitTime>0){
            lifeHitTime-=1;
            return true;
        }else{
            return false;
        }
    }
    
    protected void activateShield(){
        shieldTime=12;
    }
    
    protected void showDamage(){
        lifeHitTime=12;
    }
    
    @Override
    public void paint(Graphics2D g){
        g.drawImage(image, x, y,ekran);            
        if(showShield()){
            g.drawImage(map.get(10),x-10, y-10,image.getWidth()+20,image.getHeight()+20,ekran);
        }
        if(lifeHit()){
            if(shield<=0){
                g.drawImage(map.get(11),x+image.getWidth()/6, y+image.getHeight()/6,image.getWidth()/2,image.getHeight()/2,ekran);
            }
            if(life<(fullLife/2)){
                g.drawImage(map.get(11),x+image.getWidth()/6, y+image.getHeight()/6,image.getWidth()/2,image.getHeight()/2,ekran);
                g.drawImage(map.get(11),x+image.getWidth()/2, y+image.getHeight()/2,image.getWidth()/2,image.getHeight()/2,ekran);
            }
        }
        if(damageTimer<20 && damageTimer>=0){
            damageTimer++;g.drawString(""+takenDamage, x+image.getWidth()/2-12, y-damageTimer/2-4);
        }
    }
    
    public int getLife(){return life;}
    public void setLife(int life){this.life=life;}
    
    public int getShield(){return shield;}
    public void setShield(int shield){this.shield=shield;}
    
}
