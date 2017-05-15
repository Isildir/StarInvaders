/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StarInvaders;

/**
 *
 * @author Grzanka Grzeogrz
 */

import javax.swing.*;

        
public class StarInvaders  extends JFrame{
    
    private GamePanel gamePanel;
    private final GameMenu menu;
    private final Score scoreClass = new Score();
            
    private boolean running=true;
    private static int width=1280,height=1024;
    private int frameCount = 0;

    public StarInvaders(){
        super("StarInvaders");
        
        gamePanel= new GamePanel(width,height);
        menu = new GameMenu(width,height,gamePanel,scoreClass);
        
        setBounds(0,0,width,height);
        setVisible(true);
        setResizable(false);
        setIgnoreRepaint(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public static void main(String[] args) {
        
        StarInvaders go = new StarInvaders();
        go.menu(false);
        
    }
    

    private void menu(boolean gameOver){
        
        if(!gameOver){
        add(menu);
        do{
            try{
                Thread.sleep(50);
            }catch(InterruptedException ex){}
        }while(!menu.start());
        menu.setStartNegative(false);
        add(gamePanel); 
        gamePanel.setVisible(true);
        gamePanel.grabFocus();
        InitiateThread();}
        else{
            gamePanel.setVisible(false);
            menu.setVisible(true);
            menu.grabFocus();
            menu.enterScore();
            do{
                try{
                    Thread.sleep(50);
                }catch(InterruptedException ex){}
            }while(!menu.start());
            menu.setStartNegative(false);
            menu.setVisible(false);
            gamePanel = null;
            gamePanel = new GamePanel(width,height);
            add(gamePanel);
            gamePanel.setVisible(true);
            gamePanel.grabFocus();
            running=true;
            InitiateThread();
        }
    }

    private void InitiateThread()
    {
        Thread loop = new Thread(){
            @Override
            public void run(){
                gameLoop();
            }
        };
        loop.start();
    }

    private void gameLoop(){
        
        final double GAME_HERTZ = 60.0;
        final double TIME_BETWEEN_UPDATES = 1000000000 / GAME_HERTZ;
        double lastUpdateTime = System.nanoTime();
      
        int lastSecondTime = (int) (lastUpdateTime / 1000000000);
        int doImpact=0;
        gamePanel.initiateWorld();
        do{
               
            if(gamePanel.goToMenu()){
                gamePanel.setGoToMenu();
                menu.setVisible(true);
                menu.grabFocus();
                gamePanel.setVisible(false);
                while(!menu.start()){
                    try {Thread.sleep(20);} catch(InterruptedException e) {} 
                }
                menu.setStartNegative(false);
                menu.setVisible(false);
                gamePanel.setVisible(true);
                gamePanel.grabFocus();
            }
            double now = System.nanoTime();
            
            while( now - lastUpdateTime > TIME_BETWEEN_UPDATES )
            {
               if(doImpact==0){entitiesImpact();doImpact=3;}else{doImpact--;}
               lastUpdateTime += TIME_BETWEEN_UPDATES;
            }
            
            if ( now - lastUpdateTime > TIME_BETWEEN_UPDATES)
            {
               lastUpdateTime = now - TIME_BETWEEN_UPDATES;
            }
            
            gameUpdate();
            gameRepaint();
            
            int thisSecond = (int) (lastUpdateTime / 1000000000);
            if (thisSecond > lastSecondTime)
            {
               System.out.println("NEW SECOND " + thisSecond + " " + frameCount);
               frameCount = 0;
               lastSecondTime = thisSecond;
            }
            
            while (now - lastUpdateTime < TIME_BETWEEN_UPDATES)
            {
               Thread.yield();
            
               //This stops the app from consuming all your CPU. It makes this slightly less accurate, but is worth it.
               //You can remove this line and it will still work (better), your CPU just climbs on certain OSes.
               //FYI on some OS's this can cause pretty bad stuttering. Scroll down and have a look at different peoples' solutions to this.
               try {Thread.sleep(1);} catch(InterruptedException e) {} 
            
               now = System.nanoTime();
            }

        }while(running);
        menu(true);
    }
    

    private void gameUpdate(){
        gamePanel.update();
    }
    
    private void entitiesImpact(){
        gamePanel.detectShips();
        try {Thread.sleep(1);} catch(InterruptedException e) {} 
        if(gamePanel.detectRest()){running=false;}
    }
    
    private void gameRepaint(){
        gamePanel.repaint();
        frameCount++;
    }

}