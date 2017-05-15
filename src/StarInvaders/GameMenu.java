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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Raith
 */
public class GameMenu extends JPanel implements ActionListener{
    
    private final JButton startButton = new JButton("Start");
    private final JButton upgradeButton = new JButton("Upgrade");
    private final JButton controlsButton = new JButton("Controls)");
    private final JButton scoreViewButton = new JButton("View Score");
    private final JButton quitButton = new JButton("Quit");
    
    private final JButton returnButton = new JButton("Back To Menu");
    private final JButton laserDamageButton= new JButton("Laser Damage : 1 point");
    private final JButton laserChargeButton = new JButton("Laser Charge : 3 points");
    private final JButton missleSpeedButton = new JButton("Missle Speed : 2 points");
    
    private final JButton missleDamageButton = new JButton("Missle Damage : 4 points");
    private final JButton moreHealthButton = new JButton("Maximum Health + 50 : 2 points");
    private final JButton moreShieldButton = new JButton("Maximum Shield +50 : 2 points");
    private final JButton lessDamageButton = new JButton("Damage Reduction 5% : 6 points");
    private final JButton shipSpeedButton = new JButton("Ship Speed +1 : 8 points");
    private final JButton shipTeleportationButton = new JButton("Ship Teleportation : 12 points");
    
    private JTextField textField;
    private JButton button;
    
    private final int width,height;
    private boolean gameStart=false,showManual=false,showScore=false,upgradePoints=false;
    
    private List<String> lines;
    
    private GamePanel gamePanel;
    private Score scoreClass;
    
    public GameMenu(int width,int height,GamePanel gamePanel,Score scoreClass){
        super();
        this.gamePanel=gamePanel;
        this.width=width;
        this.height=height;
        this.scoreClass=scoreClass;
        setBorder(BorderFactory.createLineBorder(Color.black));
        setPreferredSize(new Dimension(width,height));
        setLayout(null);
        
        setFocusable(true);
        
        addActionListeners();
        mainMenu();
    }
    
    private void addActionListeners(){
        
        startButton.addActionListener(this);
        upgradeButton.addActionListener(this);
        scoreViewButton.addActionListener(this);
        quitButton.addActionListener(this);
        controlsButton.addActionListener(this);        
        laserDamageButton.addActionListener(this);
        laserChargeButton.addActionListener(this);
        missleSpeedButton.addActionListener(this);
        missleDamageButton.addActionListener(this);
        moreHealthButton.addActionListener(this);
        moreShieldButton.addActionListener(this);
        lessDamageButton.addActionListener(this);
        shipSpeedButton.addActionListener(this);
        shipTeleportationButton.addActionListener(this);
        returnButton.addActionListener(this);
      
    }
    private void mainMenu(){
        
        removeAll();
        
        startButton.setBounds(width/2-100,height/2,200,50);
        upgradeButton.setBounds(width/2-100,height/2+100,200,50);
        scoreViewButton.setBounds(width/2-100,height/2+300,200,50);
        quitButton.setBounds(width/2-100,height/2+400,200,50);
        controlsButton.setBounds(width/2-100,height/2+200,200,50);
        
        startButton.setBackground(Color.gray);
        startButton.setForeground(Color.green);
        
        upgradeButton.setBackground(Color.gray);
        upgradeButton.setForeground(Color.green); 
        
        scoreViewButton.setBackground(Color.gray);
        scoreViewButton.setForeground(Color.green);
        
        quitButton.setBackground(Color.gray);
        quitButton.setForeground(Color.green);
            
        controlsButton.setBackground(Color.gray);
        controlsButton.setForeground(Color.green);
                
        add(startButton);
        add(upgradeButton);
        add(controlsButton);
        add(scoreViewButton);
        add(quitButton);
        
        updateUI();
        
    }
    
    private void upgradeMenu(){
        removeAll();
        
        upgradePoints=true;
        
        laserDamageButton.setBounds(width/2+150,height/2-400,300,80);
        laserChargeButton.setBounds(width/2-450,height/2-400,300,80);
        missleSpeedButton.setBounds(width/2+150,height/2-250,300,80);
        missleDamageButton.setBounds(width/2-450,height/2-250,300,80);
        moreHealthButton.setBounds(width/2+150,height/2-100,300,80);
        moreShieldButton.setBounds(width/2-450,height/2-100,300,80);
        lessDamageButton.setBounds(width/2-550,height/2+50,300,80);
        shipSpeedButton.setBounds(width/2-150,height/2+50,300,80);
        shipTeleportationButton.setBounds(width/2+250,height/2+50,300,80);
        returnButton.setBounds(width/2-100,height/2+300,200,80);  
        
        add(laserDamageButton);
        add(laserChargeButton);
        add(missleSpeedButton);
        add(missleDamageButton);
        add(moreHealthButton);
        add(moreShieldButton);
        add(lessDamageButton);
        add(shipSpeedButton);
        add(shipTeleportationButton);
        add(returnButton);
        
        updateUI();
        
    }
    
    private void scoreMenu(){
        lines = scoreClass.readScore();
        
        removeAll();
        showScore=true;
        
        returnButton.setBounds(width/2-100,height/2+300,200,80);  
        returnButton.addActionListener(this);
        add(returnButton);

        updateUI();
    }
    
    public void enterScore(){
        
        removeAll();
        
        textField = new JTextField();
        textField.setBounds(width/2-50, height/2-200, 100, 30);
        add(textField);
        
        button = new JButton("Submit");
        button.setBounds(width/2-50, height/2, 100, 50);
        button.addActionListener(this);
        add(button);
        
        returnButton.setBounds(width/2-100,height/2+300,200,80);  
        returnButton.addActionListener(this);
        add(returnButton);
        
        updateUI();
    }
    
    private void manual(){
        removeAll();
        
        showManual=true;
        
        returnButton.setBounds(width/2-100,height/2+300,200,80);  
        returnButton.addActionListener(this);
        add(returnButton);
        
        updateUI();
    }
    
    private int pointsToSpend(){
        return gamePanel.getPointsTotal()-gamePanel.getPointsSpend();
    }
    
    public void setStartNegative(boolean start){
        this.gameStart=start;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object e = ae.getSource();
        if(e == startButton){
            setBackground(Color.RED);
            gameStart=true;
            setVisible(false);
            setFocusable(false);
        }
        if(e == upgradeButton){
            upgradeMenu(); 
        }
        if(e == controlsButton){
            manual();
        }
        if(e == scoreViewButton){
            scoreMenu();
        }
        if(e == quitButton){
            System.exit(0);
        }
        if(e == laserDamageButton){
            if(pointsToSpend()>=1){
                gamePanel.setPointsToSpend(1);
                gamePanel.upgradeLaserDamage();
                updateUI();
            }
        }
        if(e == laserChargeButton){
            if(pointsToSpend()>=3){
                gamePanel.setPointsToSpend(3);
                gamePanel.upgradeLaserCharge();
                updateUI();
            }
        }
        if(e == missleDamageButton){
            if(pointsToSpend()>=4){
                gamePanel.setPointsToSpend(4);
                gamePanel.upgradeMissleDamage();
                updateUI();
            }
        }
        if(e == missleSpeedButton){
            if(pointsToSpend()>=2){
                gamePanel.setPointsToSpend(2);
                gamePanel.upgradeMissleSpeed();
                updateUI();
            }
        }
        if(e == moreHealthButton){
            if(pointsToSpend()>=2){
                gamePanel.setPointsToSpend(2);
                gamePanel.upgradePlayerLife();
                updateUI();
            }
        }
        if(e == moreShieldButton){
            if(pointsToSpend()>=2){
                gamePanel.setPointsToSpend(2);
                gamePanel.upgradePlayerShield();
                updateUI();
            }
        }
        if(e == lessDamageButton){
            if(pointsToSpend()>=6 && gamePanel.getDamageReduction()<50){
                gamePanel.setPointsToSpend(6);
                gamePanel.upgradePlayerDamageReduction();
                updateUI();
            }
        }
        if(e == shipSpeedButton){
            if(pointsToSpend()>=8 && gamePanel.getPlayerSpeed()<5){
                gamePanel.setPointsToSpend(8);
                gamePanel.upgradePlayerSpeed();
                updateUI();
            }
        }
        if(e == shipTeleportationButton){
            if(pointsToSpend()>=8){
                gamePanel.setPointsToSpend(8);
                gamePanel.upgradePlayersDash();
                updateUI();
            }
        }
        if(e == button){
            scoreClass.writeScore(textField.getText(),gamePanel.getScore());
            scoreMenu();
        }
        if(e == returnButton){
            mainMenu();
            showManual=false;
            showScore=false;
            upgradePoints=false;
        }
    }
    
      
    public boolean start(){
        return gameStart;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(gamePanel.map.get(17), 0, 0,width,height, null);
        if(showManual){
            g.setColor(Color.yellow);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            g.drawString("<- ship  movement ->", width/2-140, 100);
            g.drawString(" space : shot laser ", width/2-140, 150);
            g.drawString("  e : shot  missle  ", width/2-140, 200);
            g.drawString("  q : planet strike ", width/2-140, 250);
            g.drawString("  esc : pause/menu  ", width/2-140, 300);
            g.drawString("ctrl + <- | -> : dash left,right", width/2-150, 350);
            g.drawString("ctrl + 1 | 2 | 3 : laser type change", width/2-210, 400);
        }
        if(showScore){
            g.setColor(Color.yellow);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
            int i=0;
            for (String line : lines) {
                if(i<10){g.drawString(line, width/2-120, 100 + 50*i);i++;}
            }
        }
        if(upgradePoints){
            g.setColor(Color.yellow);
            g.setFont(new Font("TimesRoman", Font.PLAIN, 40));
            g.drawString("Points " + pointsToSpend(), width/2-50, 100);
        }
        
    }
}
