/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author Raith
 */
public class GamePanel extends JPanel implements KeyListener{

    Map<Integer,BufferedImage> map = new HashMap<>();
    Map<FlagEnemyShip,BigOneLaser> laserFlagMap = new HashMap<>();
    LoadSound loadSound = new LoadSound();
    PlayerShip player;
    PlanetStrike strike;
    private final ArrayList<Clip> clipList = new ArrayList<>();
    
    private final int width,height;
    
    private final ArrayList<BulletEntities> lasersList = new ArrayList<>();
    private final ArrayList<EntitiesShips> enemyList = new ArrayList<>();
    
    private final ArrayList<PlayerLaser> playerLaser = new ArrayList<>();
    private final ArrayList<PlayerMissle> playerMissles = new ArrayList<>();
    

    private final ArrayList<Bonus> bonusList = new ArrayList<>();
    private final ArrayList<Detonation> detonationList = new ArrayList<>();
    private final ArrayList<MissleDetonation> missleDetonationList = new ArrayList<>();
    
    private final ArrayList<EntitiesBase> toRemove = new ArrayList<>();
    
    private boolean playerMoveLeft=false,playerMoveRight=false,playerDoShot=false,cheatLvl=false,specialAction=false,isDashing=false,playerSendMissle=false,onePressed=false,twoPressed=false,threePressed=false,goMenu=false,planetStrike=false;
    private int score=0,gameLevel=111,BkPos=0,pointsTotal=0,pointsSpend=0,damageReduction=0,stageTimer=0,planetStrikeBreak=0,freePos=50,cheatStop=20;
    private short playerDashTimer=8,canDash=80,playerSpeed=2,dashSpeed=10;

    public GamePanel(int width,int height){
        super();
        this.width=width;
        this.height=height;
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(width,height));
        this.setBounds(0, 0, width,height);
        setBackground(Color.yellow);
        addKeyListener(this);
        setFocusable(true);
        
        LoadGraphics load = new LoadGraphics();
        load.loadGraphics(map);
        
        loadSound.load("sound/music.wav").loop(Clip.LOOP_CONTINUOUSLY);
        loadWavs();
    }
    
    @Override
    public void paintComponent(Graphics gl)
        {
            Graphics2D g = (Graphics2D) gl;
            g.setPaint(new TexturePaint(map.get(9), new Rectangle(0,BkPos,map.get(9).getWidth(),map.get(9).getHeight()))); 
            g.fillRect(0,0,getWidth(),getHeight());
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.setColor(Color.red);
            try{
                enemyList.forEach((e) -> {e.paint(g);});
                lasersList.forEach((e) -> {e.paint(g);});
                bonusList.forEach((e) -> {e.paint(g);});
                detonationList.forEach((e) -> {e.paint(g);});
                playerLaser.forEach((e) -> {e.paint(g);});
                playerMissles.forEach((e) -> {e.paint(g);});
                missleDetonationList.forEach((e) -> {e.paint(g);});
                if(strike != null){strike.paint(g);}
            }catch(ConcurrentModificationException e){}
            try{
                player.paint(g);
            }catch(NullPointerException e){}
            if(stageTimer<300){
                g.setColor(Color.red);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 40)); 
                g.drawString("Stage : "+gameLevel,width/2-100,height/2-50);
            }
            g.setColor(Color.white);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
            g.drawString("Wynik : "+score,width-150,40);
            g.setColor(Color.blue);
            g.fillRect(250,height-60,7200/30,20);
            g.setColor(Color.red);
            g.fillRect(250,height-60,planetStrikeBreak/30,20);
        }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {playerMoveLeft = true;}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {playerMoveRight = true;}
        if (e.getKeyCode() == KeyEvent.VK_SPACE){playerDoShot = true;}
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){specialAction=true;}
        if (e.getKeyCode() == KeyEvent.VK_E){playerSendMissle=true;}
        if (e.getKeyCode() == KeyEvent.VK_1){onePressed=true;}
        if (e.getKeyCode() == KeyEvent.VK_2){twoPressed=true;}
        if (e.getKeyCode() == KeyEvent.VK_3){threePressed=true;}
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){goMenu=true;}
        if (e.getKeyCode() == KeyEvent.VK_Q){planetStrike=true;}
        if (e.getKeyCode() == KeyEvent.VK_U){cheatLvl=true;}
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {playerMoveLeft = false;}
	if (e.getKeyCode() == KeyEvent.VK_RIGHT) {playerMoveRight = false;}
        if (e.getKeyCode() == KeyEvent.VK_SPACE){playerDoShot = false;}
        if (e.getKeyCode() == KeyEvent.VK_CONTROL){specialAction=false;}
        if (e.getKeyCode() == KeyEvent.VK_E){playerSendMissle=false;}
        if (e.getKeyCode() == KeyEvent.VK_1){onePressed=false;}
        if (e.getKeyCode() == KeyEvent.VK_2){twoPressed=false;}
        if (e.getKeyCode() == KeyEvent.VK_3){threePressed=false;}
        if (e.getKeyCode() == KeyEvent.VK_Q){planetStrike=false;}
        if (e.getKeyCode() == KeyEvent.VK_U){cheatLvl=false;}
    }
    
    private void playDetonation(){if(!clipList.get(0).isRunning()){clipList.get(0).setFramePosition(0);clipList.get(0).start();}}
    private void playLaser(){if(!clipList.get(1).isRunning()){clipList.get(1).setFramePosition(0);clipList.get(1).start();}}
    private void playShipDestroyed(){if(!clipList.get(2).isRunning()){clipList.get(2).setFramePosition(0);clipList.get(2).start();}}
    private void loadWavs(){
        clipList.add(loadSound.load("sound/bum.wav"));
        clipList.add(loadSound.load("sound/laser.wav"));
        clipList.add(loadSound.load("sound/shipDestroyed.wav"));
    }
    
    
    private void playerWeaponChange(){
        if(specialAction && onePressed){player.setLaserType(1);}
        if(specialAction && twoPressed){player.setLaserType(2);}
        if(specialAction && threePressed){player.setLaserType(3);}
    }
    
    private void playerDash(){
        if(specialAction && canDash>=100){isDashing=true;canDash=0;}
        if(isDashing && playerDashTimer>=0){
            if(playerMoveLeft){
                player.setX(player.getX()-dashSpeed);
            }else if(playerMoveRight){
                player.setX(player.getX()+dashSpeed);
            }
            playerDashTimer--;
            canDash=0;
        }else{canDash++;playerDashTimer=8;isDashing=false;}
    }
    
    public void initiateWorld(){
        player = new PlayerShip(width/2-map.get(1).getWidth()/2,height-120,map);
        player.setData(playerLaser,playerMissles);
        player.setLaserType(1);
    }
    
    public void initiateLevel(){
        
        int EC,ECperLayer,ECinterspace;
        
        if(gameLevel>=10){
            EC=gameLevel/4-2;
            if(EC>12){EC=12;}
            ECinterspace=1180/EC;
            for(int i=0;i<=EC;i++){
                EntitiesShips enemy = new FlagEnemyShip(i*ECinterspace+40,freePos,map);
                ((FlagEnemyShip)enemy).setData(bonusList);
                enemyList.add(enemy);
            }
            freePos+=90;
        }
        if(gameLevel>=7){
            EC=gameLevel*2-6;
            if(EC>71){EC=71;}
            if(gameLevel<10){EC*=2;}
            ECperLayer=(EC/18)+1;
            ECinterspace=1180/(EC/ECperLayer);
            for(int r=1;r<=ECperLayer;r++){
                for(int i=0;i<=EC/ECperLayer;i++){
                    EntitiesShips enemy = new HeavyEnemyShip(i*ECinterspace+40,freePos,map);
                    ((HeavyEnemyShip)enemy).setData(lasersList, bonusList, player);
                    boolean direction=true;
                    if(enemyList.size()%2>0){direction=false;}
                    ((HeavyEnemyShip)enemy).setDirection(direction);
                    enemyList.add(enemy);
                }
                freePos+=42;
            }
        }
        if(gameLevel>=4){
            EC=gameLevel*6-10;
            if(EC>199){EC=199;}
            if(gameLevel<10){EC*=2;}
            ECperLayer=(EC/40)+1;
            ECinterspace=1180/(EC/ECperLayer);
            for(int r=1;r<=ECperLayer;r++){
                for(int i=0;i<=EC/ECperLayer;i++){
                    EntitiesShips enemy = new MediumEnemyShip(i*ECinterspace+40,freePos,map);
                    ((MediumEnemyShip)enemy).setData(lasersList, bonusList);
                    if(enemyList.size()%2>0){((MediumEnemyShip)enemy).setDirection(false);}
                    enemyList.add(enemy);
                }
                freePos+=40;   
            }
        }
        EC=gameLevel*24;
        if(gameLevel<10){EC*=2;}
        if(EC>399){EC=399;}
        ECperLayer=(EC/46)+1;
        ECinterspace=1270/(EC/ECperLayer);
        for(int r=1;r<=ECperLayer;r++){
            if(freePos<height/2+100){
                for(int i=0;i<=EC/ECperLayer-1;i++){
                    EntitiesShips enemy = new LightEnemyShip(i*ECinterspace+20,freePos,map);
                    ((LightEnemyShip)enemy).setData(lasersList, bonusList);
                    enemyList.add(enemy);
                }
            }
            freePos+=35;
        }
    }


    public void update(){

        if(stageTimer<300){stageTimer++;}
        if(cheatStop>=0){cheatStop--;}
        if(specialAction && cheatLvl && cheatStop<=0){gameLevel+=5;cheatStop=50;}
        if(planetStrikeBreak>=0){planetStrikeBreak--;}
        pointsTotal=score/2000;
        playerDash();
        playerWeaponChange();
        if(enemyList.isEmpty() && enemyList.isEmpty() && enemyList.isEmpty() && enemyList.isEmpty()){initiateLevel();}
        if(playerMoveLeft && player.getX()>20){player.setX(player.getX()-playerSpeed);}
        if(playerMoveRight && player.getX()<width-20-player.image.getWidth()){player.setX(player.getX()+playerSpeed);}
        if(playerDoShot){if(player.fireLaser()){playLaser();}}
        if(playerSendMissle){player.fireMissle();}
        if(planetStrike && planetStrikeBreak<=0){strike = new PlanetStrike(30,height+20,map.get(3));planetStrikeBreak=7200;score-=3000;}
        player.update();
        for(EntitiesShips e : enemyList){
            e.update();
            if(e.getImage()==map.get(2)){
                if(((FlagEnemyShip)e).laserChance() && !laserFlagMap.containsKey(((FlagEnemyShip)e))){
                    BigOneLaser shot = new BigOneLaser(e.getX()+e.image.getWidth()/2,e.getY()+e.image.getHeight()+map.get(4).getHeight(),map.get(4));
                    lasersList.add(shot);
                    laserFlagMap.put(((FlagEnemyShip)e), shot);
                }
                if(laserFlagMap.containsKey(((FlagEnemyShip)e))){
                    laserFlagMap.get(((FlagEnemyShip)e)).setPosition(e.getX()+e.image.getWidth()/2,e.getY()+e.image.getHeight()+map.get(4).getHeight());
                    if(laserFlagMap.get(((FlagEnemyShip)e)).checkRange()){laserFlagMap.remove(((FlagEnemyShip)e));}
                }
            }
        }
        
        
        if(strike != null){
            strike.update();
            if(strike.checkRange()){
                strike = null;
            }
        }
        
        for(BulletEntities e : lasersList){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        lasersList.removeAll(toRemove); 

        for(PlayerLaser e : playerLaser){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        playerLaser.removeAll(toRemove);
        
        for(PlayerMissle e : playerMissles){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        playerMissles.removeAll(toRemove);
        
        for(Bonus e : bonusList){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        bonusList.removeAll(toRemove);
        
        for(Detonation e : detonationList){
            if(!e.check()){
                toRemove.add(e);
            }
        }
        detonationList.removeAll(toRemove);
        
        for(MissleDetonation e : missleDetonationList){
            if(!e.check()){
                toRemove.add(e);
            }
        }
        missleDetonationList.removeAll(toRemove);
        
        BkPos++;
        if(BkPos==height){BkPos=0;} 
    }
    
    private boolean detectGeneric(EntitiesShips T){
        boolean remove=false;
        Rectangle e = T.entitySquare();
        if(strike != null){if(e.intersects(strike.entitySquare())){T.calculateDamage(strike.getDamage());}}
            for(PlayerLaser s : playerLaser){
                if(e.intersects(s.entitySquare())){
                    T.calculateDamage(s.getDamage());
                    if(!s.checkCharge()){toRemove.add(s);}
                    }
                }
            playerLaser.removeAll(toRemove);
            for(PlayerMissle s : playerMissles){
                if(e.intersects(s.entitySquare())){
                    playDetonation();
                    MissleDetonation miss = new MissleDetonation(T.getX(),T.getY(),map.get(8));
                    missleDetonationList.add(miss);
                    toRemove.add(s);
                }
            }
            playerMissles.removeAll(toRemove);
            for(MissleDetonation s : missleDetonationList){
                if(e.intersects(s.entitySquare())){
                    T.calculateDamage(player.getMissleDamage());
                }
            }
            if(T.getLife()<=0){
                remove=true;
                playShipDestroyed();
                Detonation det = new Detonation(T.getX(),T.getY(),map.get(8),T.getImage());
                detonationList.add(det);
            }
        return remove;
    }


    public void detectShips(){
                
        for(EntitiesShips e : enemyList){
            
            if(detectGeneric(e)){
                if(e.getImage()==map.get(2) && laserFlagMap.containsKey((FlagEnemyShip)e)){
                    laserFlagMap.get((FlagEnemyShip)e).changeTimer();
                }
                e.chanceToBonus();
                score+=e.getScore();
                toRemove.add(e);
                
            }
        }
        enemyList.removeAll(toRemove);
    }
    
    private boolean checkHit(BulletEntities e){
        boolean remove=false;
        Rectangle a=player.entitySquare();
        if(a.intersects(e.entitySquare()) && !player.recovering()){
            player.calculateDamage(e.getDamage()-((e.getDamage()*damageReduction)/100));
            remove=true;
        }
        return remove;
    }
    public boolean detectRest(){
        Rectangle a=player.entitySquare();
        
        for(BulletEntities e : lasersList){
            if(checkHit(e) && e.getDamage()<70){
                toRemove.add(e);
            }
        }
        lasersList.removeAll(toRemove);
        
        for(EntitiesShips e : enemyList){
            if(e.getImage()==map.get(6)){
                if(a.intersects(e.entitySquare()) && !player.recovering()){
                    player.calculateDamage(300-((300*damageReduction)/100));
                    playShipDestroyed();
                    Detonation det = new Detonation(e.getX(),e.getY(),map.get(8),map.get(6));
                    detonationList.add(det);
                    toRemove.add(e);
                }
            }
        }
        enemyList.removeAll(toRemove);
        
        for(Bonus e : bonusList){
            if(a.intersects(e.entitySquare())){
                player.gunUpgrade();
                toRemove.add(e);
            }
        }
        bonusList.removeAll(toRemove);
        
        toRemove.clear();
        
        System.out.println(lasersList.size() +"   "+ enemyList.size()+"      "+playerLaser.size()+"      "+playerMissles.size()+"      "+toRemove.size()+"     "+bonusList.size()+"     "+detonationList.size()+"      "+missleDetonationList.size());
        if(!player.haveLifes()){return true;}
        if(enemyList.isEmpty()){gameLevel+=1;stageTimer=0;freePos=50;}
        return false;
    }    

    public String getScore(){return " "+score;}
    public boolean goToMenu(){return goMenu;}
    public void setGoToMenu(){goMenu=false;}
    public int getPointsTotal(){return pointsTotal;}
    public int getPointsSpend(){return pointsSpend;}
    public void setPointsToSpend(int points){pointsSpend+=points;}
    protected void upgradeLaserDamage(){player.upgradeLaserStats(1,0);}
    protected void upgradeLaserCharge(){player.upgradeLaserStats(0,1);}
    protected void upgradeMissleDamage(){player.upgradeMissleStats(1,0);}
    protected void upgradeMissleSpeed(){player.upgradeMissleStats(0,1);}
    protected void upgradePlayerLife(){player.upgradeLifeStats(50,0);}
    protected void upgradePlayerShield(){player.upgradeLifeStats(0,50);}
    protected void upgradePlayerSpeed(){playerSpeed++;}
    protected void upgradePlayerDamageReduction(){damageReduction+=5;}
    protected void upgradePlayersDash(){dashSpeed+=2;}
    protected int getPlayerSpeed(){return playerSpeed;}
    protected int getDamageReduction(){return damageReduction;}
    
}
