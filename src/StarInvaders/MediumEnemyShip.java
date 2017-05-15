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
 * @author Raith
 */
public class MediumEnemyShip extends EntitiesShips implements EnemyStuff{
    
    private ArrayList mediumShipFire;
    private ArrayList bonusList;
    
    private boolean doingSpecial=false,returning=false,leftRight=true;
    private int dy,dx=0;
    
    public MediumEnemyShip(int x, int y, Map<Integer,BufferedImage> map) {
        super(x, y, map);
        this.image=map.get(6);
        fullLife=life=200;shield=250;
    }
    
    protected void setData(ArrayList mediumShipFire,ArrayList bonusList){
        this.mediumShipFire=mediumShipFire;
        this.bonusList=bonusList;
    }
    
    public int getScore(){
        return 60;
    }
    
    @Override
    public int getSize(){
        return 2;
    }
    
    @Override
    public void update(){
        if(!doingSpecial && !returning){chanceToSpecial();}
        if(doingSpecial){specialMovement();}
        if(!doingSpecial && !returning){normalMovement();}
        if(returning){returnMovement();}
        tryToShot();
        
    }
    
    public void setDirection(boolean dir){
        leftRight=dir;
    }
    
    private void normalMovement(){
        if(x<=width-40 && leftRight==false){
            dx+=1;
            x+=dx/3;
            if(dx>=4){dx=0;}
        }else{leftRight=true;}
        if(x>=40 && leftRight==true){
            dx+=1;
            x-=dx/3;
            if(dx>=4){dx=0;}
        }else{leftRight=false;}
    }
    
    private void chanceToSpecial(){
        if(Math.random()>0.9998){
            doingSpecial=true;
            dy=y;
        }
    }
    
    private void specialMovement(){
        if(y<height-100){
            y+=4;
        }else{doingSpecial=false;returning=true;}
    }
    
    private void returnMovement(){
        if(y>dy){
            y-=3;
        }else{returning=false;}
    }
    
    @Override
    public void tryToShot(){
        if(Math.random()>=0.9998){
            LightShipFire shot1 = new LightShipFire(x+(image.getWidth()-map.get(4).getWidth())/2-3,y+image.getHeight()+map.get(4).getHeight(),map.get(4));
            mediumShipFire.add(shot1);
            LightShipFire shot2 = new LightShipFire(x+(image.getWidth()-map.get(4).getWidth())/2+3,y+image.getHeight()+map.get(4).getHeight(),map.get(4));
            mediumShipFire.add(shot2);
        }
    }
    
    @Override
    public void chanceToBonus(){
        if(Math.random()>=0.95){
            Bonus bonus = new Bonus(x,y,map.get(12));
            bonusList.add(bonus);
        }
    }
    
}
