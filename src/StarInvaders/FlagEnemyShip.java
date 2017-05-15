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
public class FlagEnemyShip extends EntitiesShips implements EnemyStuff{
    
    private ArrayList bonusList;
    private boolean floating=false;
    private int dx=0;
    
    public FlagEnemyShip(int x, int y,Map<Integer,BufferedImage> map) {
        super(x, y, map);
        fullLife=life=1250;shield=2500;
        this.image=map.get(2);
    }
    
    public void setData(ArrayList bonusList){
        this.bonusList=bonusList;
    }
    
    public int getScore(){
        return 300;
    }

    
    @Override
    public void update(){
        if(x<=width-120 && floating==false){
            dx+=1;
            x+=dx/6;
            if(dx>=7){dx=0;}
        }else{floating=true;}
        if(x>=120 && floating==true){
            dx+=1;
            x-=dx/6;
            if(dx>=7){dx=0;}
        }else{floating=false;}
    }
    
    protected boolean laserChance(){
        return Math.random()>=0.9995;
    }
    
    
    @Override
    public void tryToShot(){
        //not supported
    }
    
    @Override
    public void chanceToBonus(){
        if(Math.random()>=0.9){
            Bonus bonus = new Bonus(x,y,map.get(12));
            bonusList.add(bonus);
        }
    }
    
}
