/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Raith
 */
public class PlayerShip extends EntitiesShips {
    
    private ArrayList playerLaser;
    private ArrayList playerMissle;
    private BufferedImage laserColor;
    private int gunLvl=1,missleCount=10,lifes=5,laserDamage,laserSpeed,recoverTimer=0,laserTechTree,laserWidth,laserHeight,charge,shotDelay,shotBreak,missleBreak=50,damageBonus=0,chargeBonus=0,fullShield=400,missleDamage=12,missleSpeed=2;
    private boolean recovering=false;
    
    public PlayerShip(int x, int y, Map<Integer,BufferedImage> map) {
        super(x, y,map);
        this.image=map.get(1);
        fullLife=life=400;shield=400;
    }
    
    public void setData(ArrayList laser,ArrayList missle){
        this.playerLaser=laser;
        this.playerMissle=missle;
        this.laserColor=map.get(4);
    }
    
    public void upgradeMissleStats(int damage,int speed){
        missleDamage+=damage;
        missleSpeed+=speed;
    }
    
    public void upgradeLaserStats(int damage,int charge){
        damageBonus+=damage;
        chargeBonus+=charge;
    }
    
    public void upgradeLifeStats(int health,int shield){
        fullLife+=health;
        fullShield+=shield;
    }

    public int getMissleDamage(){
        return missleDamage;
    }

    public void setLaserType(int type){
        this.laserTechTree=type;
        switch (laserTechTree){
            case 1: shotBreak=30;charge=2+chargeBonus/2;laserDamage=30+damageBonus;laserSpeed=7;laserWidth=4;laserHeight=16;laserColor=map.get(19);break;
            case 2: shotBreak=20;charge=4+chargeBonus;laserDamage=15+damageBonus/2;laserSpeed=8;laserWidth=3;laserHeight=14;laserColor=map.get(15);break;
            case 3: shotBreak=40;charge=1+chargeBonus/4;laserDamage=70+damageBonus*2;laserSpeed=6;laserWidth=5;laserHeight=22;laserColor=map.get(16);break;
        }
    }

    public boolean fireLaser(){
        int w=4;//odleglosc miedzy pociskami razy 2
        if(shotDelay<=0){
            shotDelay=shotBreak;
            if(gunLvl<10){
                    for(int i=0;i<1;i++){
                        PlayerLaser shot = new PlayerLaser(x+(image.getWidth()-laserColor.getWidth())/2,y-laserColor.getHeight(),laserColor);
                        shot.setData(charge,laserDamage,laserSpeed,laserWidth,laserHeight);
                        playerLaser.add(shot);
                    }
            }else{
                    for(int i=-1;i<=1;i+=2){
                        PlayerLaser shot = new PlayerLaser(x+(image.getWidth()-laserColor.getWidth())/2-i*w,y-laserColor.getHeight(),laserColor);
                        shot.setData(charge,laserDamage,laserSpeed,laserWidth,laserHeight);
                        playerLaser.add(shot);
                    }
            } 
            return true;
        }
        return false;
    }
    
    public void fireMissle(){
        if(missleCount>0 && missleBreak<=0){
            PlayerMissle shot = new PlayerMissle(x+(image.getWidth()-map.get(13).getWidth())/2,y,map.get(13));
            shot.setData(missleSpeed);
            playerMissle.add(shot);
            missleCount--;
            missleBreak=50;
        }
    }

    @Override
    public void paint(Graphics2D g){
        if(recoverTimer<=0){g.drawImage(image, x, y,ekran);if(((int)(Math.random()*10))>5){g.drawImage(map.get(18), x+3, y+image.getHeight(),ekran);}}
        else if(recoverTimer%10==0){g.drawImage(image, x, y,ekran);}
        if(showShield()){
            g.drawImage(map.get(10),x-12, y-10,image.getWidth()+24,image.getHeight()+25,ekran);
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
        g.setColor(Color.red);
        if(life>0){g.fillRect(width-500, height-60, 200*life/fullLife,20);}
        g.setColor(Color.blue);
        if(shield>0){g.fillRect(width-250, height-60, 200*shield/fullShield,20);}
        for(int i=100;i<missleCount*map.get(13).getWidth()/2+100 + missleCount*8;i+=map.get(13).getWidth()/2+8){
            g.drawImage(map.get(13), i, height-60, map.get(13).getWidth()/2+5, map.get(13).getHeight()/2+5, ekran);
        }
        for(int i=1;i<lifes;i++){
            g.drawImage(map.get(1), i*(map.get(1).getWidth()/3+6), 20, map.get(1).getWidth()/3, map.get(1).getHeight()/3, ekran);
        }
        if(damageTimer<20 && damageTimer>=0){g.setColor(Color.red);damageTimer++;g.drawString(""+takenDamage, x+image.getWidth()/2-12, y-damageTimer/2-4);} 
    }

    
    public void update(){
        
        if(life<=0 && lifes>=1){recovering=true;recoverTimer=150;life=fullLife;shield=fullShield;lifes--;}
        if(recoverTimer>=-3){recoverTimer--;}
        if(recoverTimer<=0){recovering=false;}
        if(shotDelay>=0){shotDelay--;}
        if(missleBreak>=0){missleBreak--;}
    }
    
    public boolean haveLifes(){return lifes>=1;}
        
    public boolean recovering(){return recovering;}

    public void gunUpgrade(){
        if(gunLvl<18){gunLvl++;}
        if(missleCount<10){missleCount++;}
        if(life<=fullLife){life+=50;}
        if(life>fullLife){fullLife=life;}
        if(life==fullLife){shield+=50;}
        if(shield>fullShield){shield=fullShield;}
    }
}
