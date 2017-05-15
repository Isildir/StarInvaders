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
public class HeavyEnemyShip extends EntitiesShips implements EnemyStuff{
    
    private ArrayList heavyShipFire;
    private ArrayList bonusList;
    private PlayerShip player;
    
    private short dx=0;
    private boolean leftRight=false;
    
    public HeavyEnemyShip(int x, int y, Map<Integer,BufferedImage> map) {
        super(x, y, map);
        this.image=map.get(7);
        fullLife=life=350;shield=450;
    }
    
    protected void setData(ArrayList heavyShipFire,ArrayList bonusList,PlayerShip player){
        this.heavyShipFire=heavyShipFire;
        this.bonusList=bonusList;
        this.player=player;
    }
    
    public int getScore(){
        return 120;
    }
    
    @Override
    public int getSize(){
        return 3;
    }
    
    @Override
    public void update(){
        movement();
        tryToShot();
    }
    
    public void setDirection(boolean dir){
        leftRight=dir;
    }
    
    private void movement(){
        if(x<=width-40 && leftRight==false){
            dx+=1;
            x+=dx/2;
            if(dx>=3){dx=0;}
        }else{leftRight=true;}
        if(x>=40 && leftRight==true){
            dx+=1;
            x-=dx/2;
            if(dx>=3){dx=0;}
        }else{leftRight=false;}
    }
    
    @Override
    public void tryToShot(){
        if(Math.random()>=0.999){
            HeavyOneShot shot = new HeavyOneShot(x+(image.getWidth()/2-map.get(14).getWidth()/2),y+image.getHeight()+map.get(14).getHeight()+1,map.get(14),player.getX()+map.get(1).getWidth()/2,player.getY());
            heavyShipFire.add(shot);
        }
    }
    
    @Override
    public void chanceToBonus(){
        if(Math.random()>=0.925){
            Bonus bonus = new Bonus(x,y,map.get(12));
            bonusList.add(bonus);
        }
    }
    
}
