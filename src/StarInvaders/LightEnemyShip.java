/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author tak
 */
public class LightEnemyShip extends EntitiesShips implements EnemyStuff{
    
    private boolean arriving=true,descending=true,backing=true;
    private int timer=(int)(Math.random()*200),phase2Time=116,dx;
    private final int originalX,originalY;
    
    private ArrayList bonusList;
    private ArrayList lightFire;
    
    public LightEnemyShip(int x,int y,Map<Integer,BufferedImage> map) {
        super(x,y,map);
        originalX=x;originalY=y;
        this.y=-200;
        fullLife=life=50;shield=75;
        this.image=map.get(5);
        whereToGo();
    }
    
    public void setData(ArrayList lightFire,ArrayList bonusList){
        this.lightFire=lightFire;
        this.bonusList=bonusList;
    }
    
    @Override
    public int getScore(){
        return 30;
    }

    @Override
    public void update(){
        if(timer<=0){
            if(arriving==true){movementOne();}
            else if(descending==true){movementTwo();}
            else if(backing==true){movementThree();}
            else if(descending==false){movementFour();}
            
        }else{timer-=1;}
        if(!arriving){tryToShot();}
    }
    
    private void whereToGo(){
        if(x<width/2){
            dx=x+width/2-50;
        }else{
            dx=x-width/2+50;
        }
    }
    
    private void movementOne(){
        if(y<originalY){
            y+=2;
        }else{arriving=false;timer=280;}
    }
    
    private void movementTwo(){
        if(phase2Time>=0){
            x+=(dx-originalX)/100;
            phase2Time-=1;
            if(y<height/4+originalY){
                y+=1;
            }
        }else{descending=false;phase2Time=116;timer=260;}
    }
    
    private void movementThree(){
        if(y>originalY){
            y-=2;
        }else{backing=false;timer=40;}
    }
    
    private void movementFour(){
        if(x!=originalX){
            x+=(originalX-dx)/100;
        }else{descending=true;backing=true;timer=240;}
    }
    
    @Override
    public void tryToShot(){
        if(Math.random()>=0.9992){
            BulletEntities shot = new LightShipFire(x+(image.getWidth()-map.get(4).getWidth())/2,y+image.getHeight()+map.get(4).getHeight(),map.get(4));
            lightFire.add(shot);
        }
    }
    
    @Override
    public void chanceToBonus(){
        if(Math.random()>=0.975){
            Bonus bonus = new Bonus(x,y,map.get(12));
            bonusList.add(bonus);
        }
    }

}
