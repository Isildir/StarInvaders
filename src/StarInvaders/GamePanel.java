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
    ImageLoader load = new ImageLoader();
    LoadSound loadSound = new LoadSound();
    PlayerShip player;
    PlanetStrike strike;
    
    private final int width,height;
    
    private final ArrayList<LightShipFire> lightFire = new ArrayList<>();
    private final ArrayList<LightShipFire> mediumFire = new ArrayList<>();
    private final ArrayList<HeavyOneShot> heavyFire = new ArrayList<>();
    private final ArrayList<BigOneLaser> flagFire = new ArrayList<>();
    
    private final ArrayList<PlayerLaser> playerLaser = new ArrayList<>();
    private final ArrayList<PlayerMissle> playerMissles = new ArrayList<>();
    
    private final ArrayList<EntitiesShips> enemyList = new ArrayList<>();

    private final ArrayList<Bonus> bonusList = new ArrayList<>();
    private final ArrayList<Detonation> detonationList = new ArrayList<>();
    private final ArrayList<MissleDetonation> missleDetonationList = new ArrayList<>();
    
    private final ArrayList<EntitiesBase> toRemove = new ArrayList<>();
    
    private boolean playerMoveLeft=false,playerMoveRight=false,playerDoShot=false,cheatLvl=false,specialAction=false,isDashing=false,playerSendMissle=false,onePressed=false,twoPressed=false,threePressed=false,goMenu=false,planetStrike=false;
    private int score=0,gameLevel=1,BkPos=0,pointsTotal=0,pointsSpend=0,damageReduction=0,stageTimer=0,planetStrikeBreak=0,freePos=50,cheatStop=20;
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
        
        loadGraphics();
        
        loadSound.load("sound/music.wav").loop(Clip.LOOP_CONTINUOUSLY);
    }

    private void loadGraphics() {
        map.put(1, load.loadImage("img/playerShip.gif"));//gracz stoi
        map.put(2, load.loadImage("img/flagShip.png"));//gracz skreca w lewo
        map.put(3, load.loadImage("img/strike.png"));//okragly pocisk
        map.put(4, load.loadImage("img/enemyLaser.jpg"));//podstawowy pocisk
        map.put(5, load.loadImage("img/lightShip.png"));//obcy lekki stoi
        map.put(6, load.loadImage("img/mediumShip.png"));//obcy lekki w lewo
        map.put(7, load.loadImage("img/heavyShip.png"));//obcy lekki w prawo
        map.put(8, load.loadImage("img/bum.gif"));//wybuch po zniszczeniu
        map.put(9, load.loadImage("img/gameBackground.jpg"));//tlo
        map.put(10,load.loadImage("img/shield.png"));//tarcza kinetyczna
        map.put(11,load.loadImage("img/rocketBum.png"));//obrazenia
        map.put(12,load.loadImage("img/bonus.png"));//bonus z wroga
        map.put(13,load.loadImage("img/rocket.png"));//rakieta
        map.put(14,load.loadImage("img/roundBullet.png"));//okragly pocisk
        map.put(15,load.loadImage("img/playerLaser2.png"));//okragly pocisk
        map.put(16,load.loadImage("img/playerLaser3.png"));//okragly pocisk
        map.put(17,load.loadImage("img/menuBackground.jpg"));//okragly pocisk
        map.put(18,load.loadImage("img/engine.png"));//okragly pocisk
        map.put(19,load.loadImage("img/playerLaser1.jpg"));//okragly pocisk
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
                bonusList.forEach((e) -> {e.paint(g);});
                detonationList.forEach((e) -> {e.paint(g);});
                enemyList.forEach((e) -> {e.paint(g);});
                lightFire.forEach((s) -> {s.paint(g);});
                mediumFire.forEach((s) -> {s.paint(g);});
                playerLaser.forEach((e) -> {e.paint(g);});
                playerMissles.forEach((e) -> {e.paint(g);});
                missleDetonationList.forEach((e) -> {e.paint(g);});
                flagFire.forEach((e) -> {e.paint(g);});
                heavyFire.forEach((e) -> {e.paint(g);});
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
    
    private void playDetonation(){loadSound.load("sound/bum.wav").start();}
    private void playLaser(){loadSound.load("sound/laser.wav").start();}
    private void playShipDestroyed(){loadSound.load("sound/shipDestroyed.wav").start();}
    
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
                    ((HeavyEnemyShip)enemy).setData(heavyFire, bonusList, player);
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
                    ((MediumEnemyShip)enemy).setData(mediumFire, bonusList);
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
                    ((LightEnemyShip)enemy).setData(lightFire, bonusList);
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
        //enemyList.forEach((e) -> {e.update();});
        for(EntitiesShips e : enemyList){
            switch(e.getSize()){
                case 1:
                    ((LightEnemyShip)e).update();
                    break;
                case 2:
                    ((MediumEnemyShip)e).update();
                    break;
                case 3:
                    ((HeavyEnemyShip)e).update();
                    break;
                case 4:
                    ((FlagEnemyShip)e).update();
                    if(((FlagEnemyShip)e).laserChance() && !laserFlagMap.containsKey(e)){
                    BigOneLaser shot = new BigOneLaser(e.getX()+e.image.getWidth()/2,e.getY()+e.image.getHeight()+map.get(4).getHeight(),map.get(4));
                    flagFire.add(shot);
                    laserFlagMap.put(((FlagEnemyShip)e), shot);
                    }
                    if(laserFlagMap.containsKey(e)){
                        laserFlagMap.get(e).setPosition(e.getX()+e.image.getWidth()/2,e.getY()+e.image.getHeight()+map.get(4).getHeight());
                        if(laserFlagMap.get(e).checkTimer()){laserFlagMap.remove(e);}
                    }
                    break;
            }
        }
        
        if(strike != null){
            strike.update();
            if(strike.checkRange()){
                strike = null;
            }
        }
        
        for(HeavyOneShot e : heavyFire){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        flagFire.removeAll(toRemove); 
        
        for(BigOneLaser e : flagFire){
            e.update();
            if(e.checkTimer()){
                toRemove.add(e);
            }
        }
        flagFire.removeAll(toRemove);        
                
        for(LightShipFire e : lightFire){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        lightFire.removeAll(toRemove);
        
        for(LightShipFire e : mediumFire){
            e.update();
            if(e.checkRange()){
                toRemove.add(e);
            }
        }
        mediumFire.removeAll(toRemove);

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
            for(PlayerMissle s : playerMissles){
                if(e.intersects(s.entitySquare())){
                    playDetonation();
                    MissleDetonation miss = new MissleDetonation(T.getX(),T.getY(),map.get(8));
                    missleDetonationList.add(miss);
                    toRemove.add(s);
                }
            }
            for(MissleDetonation s : missleDetonationList){
                if(e.intersects(s.entitySquare())){
                    T.calculateDamage(player.getMissleDamage());
                }
            }
            playerLaser.removeAll(toRemove);
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
                
                switch(e.getSize()){
                    case 1:
                        ((LightEnemyShip)e).chanceToBonus();
                        score+=((LightEnemyShip)e).getScore();
                        toRemove.add(e);
                        break;
                    case 2:
                        ((MediumEnemyShip)e).chanceToBonus();
                        score+=((MediumEnemyShip)e).getScore();
                        toRemove.add(e);
                        break;
                    case 3:
                        ((HeavyEnemyShip)e).chanceToBonus();
                        score+=((HeavyEnemyShip)e).getScore();
                        toRemove.add(e);
                        break;
                    case 4:  
                        ((HeavyEnemyShip)e).chanceToBonus();
                        score+=((HeavyEnemyShip)e).getScore();
                        toRemove.add(e);
                        break;
                }
            }
        }
        enemyList.removeAll(toRemove);
    }
    
    public boolean detectRest(){
        Rectangle a=player.entitySquare();
        
        for(LightShipFire e : lightFire){
            if(a.intersects(e.entitySquare()) && !player.recovering()){
                player.calculateDamage(10-((10*damageReduction)/100));
                toRemove.add(e);
            }
        }
        lightFire.removeAll(toRemove);
        
        for(LightShipFire e : mediumFire){
            if(a.intersects(e.entitySquare()) && !player.recovering()){
                player.calculateDamage(12-((12*damageReduction)/100));
                toRemove.add(e);
            }
        }
        mediumFire.removeAll(toRemove);
        
        for(HeavyOneShot e : heavyFire){
            if(a.intersects(e.entitySquare()) && !player.recovering()){
                player.calculateDamage(25-((25*damageReduction)/100));
                toRemove.add(e);
            }
        }
        heavyFire.removeAll(toRemove);
        
        for(BigOneLaser e : flagFire){
            if(a.intersects(e.entitySquare()) && !player.recovering()){
                player.calculateDamage(80-((80*damageReduction)/100));
            }
        }
        
        for(EntitiesShips e : enemyList){
            if(e.getSize()==2){
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
