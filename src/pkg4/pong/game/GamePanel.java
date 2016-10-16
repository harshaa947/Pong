/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.ArrayList;
import pkg4.pong.network.Data;
import pkg4.pong.network.Streams;
import pkg4.pong.network.PlayerTh;
import pkg4.pong.network.PlayerData;

/**
 *
 * @author Vikas
 * This class creates a JPanel which will be used for the User Interface
 * of the game. This shows user the present State of the Game.
 * it takes Inputs from arrow keys of the KeyBoard which then directs the Player
 * position.
 */
public class GamePanel extends JPanel implements ActionListener, KeyListener {
    final public static int WINDOW_WIDTH = 500;
    final public static int WINDOW_HEIGHT = 500;
    final public static int WINDOW_WIDTH_Margin = 550;
    final public static int WINDOW_HEIGHT_Margin = 570;    
    final public static int margin = 20;
    final public static int corner = 40;
    final public static Color PrimaryDark = Color.decode("#1A237E");
    final public static Color PrimaryLight = Color.decode("#FFFFFF");
    final public static Color PrimaryAccent = Color.decode("#FFFF00");
    final public static Color PrimaryUP = Color.decode("#c62828");
    final public static Color PrimaryDOWN = Color.decode("#33691E");
    final public static Color PrimaryLEFT = Color.decode("#FF6F00");
    final public static Color PrimaryRIGHT = Color.decode("#3E2723");
    private boolean pause = false; 
    
    //PCUser user;
    Ball [] ball = new Ball[4];
    //Computer computer,computer1,computer2;
    Player [] players = new Player[4];
    int turn;

    public GamePanel(int n , int m) {
        Data data = Data.getInstance();
        players[0] = new PCUser(data.mypos);
        players[0].id = data.myid;
        players[0].name = data.myname;
        players[0].isPC = true;
        for(int i=0;i<data.pd.size();i++){
            PlayerData playerd = data.pd.get(i);
            players[i+1]= new NetworkUser(playerd.pos); 
            players[i+1].id = playerd.playerid;
            players[i+1].name = playerd.name;
            players[i+1].isNetwork = true;
        }
        int n12 = data.pd.size() +1;
        System.out.println(data.pd);
        for(int i=0;i<data.cd.size();i++){
            PlayerData playerd = data.cd.get(i);
            players[i+n12]= new Computer(playerd.pos); 
            players[i+n12].id = playerd.playerid;
            players[i+n12].name = playerd.name;
            players[i+n12].isComputer = true;
           ((Computer )players[i+n12]).setgame(this);
        }
        //user = new PCUser(Player.POS_LEFT);
        for(int i=0;i<3;i++)
            ball[i] = new Ball(i);
//        computer = new Computer(Player.POS_RIGHT);
//        computer.setgame(this);
//        computer1 = new Computer(Player.POS_UP);
//        computer1.setgame(this);
//        computer2 = new Computer(Player.POS_DOWN);
//        computer2.setgame(this);
        
        Timer time = new Timer(50, this);
        time.start();
        
        this.addKeyListener(this);
        this.setFocusable(true);
        turn =0;
    }
    
    public void Pause(){
        pause = true;
    }
            
    public void resume(){
        pause = false;
    }
    //for keeping account of the scores and act accordingly
    private void update() {
        for (int i=0 ;i<4;i++){
            if (players[i] != null)
            {    if(players[i].isNetwork){
                    ((NetworkUser) players[i]).update();
                }

                else if (players[i].isComputer)
                    ((Computer) players[i]).update();
                else players[i].update();
            }
        }
//        user.update();
//        computer.update();
//        computer1.update();
//        computer2.update();
        for(int j=0;j<3;j++){
            int temp_pos = ball[j].update();
            if(temp_pos != 0)
            for (int i=0 ;i<4;i++){
                if (players[i] != null)
                    if(players[i].getPosition() == temp_pos)
                    {
                        players[i].incrementScore();
                        break;
                    }
            } 
        }
        //checking collision with the wall
        for(int j=0;j<3;j++)
        for (int i=0 ;i<4;i++){
            if (players[i] != null)
                ball[j].checkCollisionWith(players[i]);
        } 
        
        for (int i=0 ;i<4;i++){
            if (players[i] != null)
                if(players[i].getScore() == 15)
                    players[i] = null;
        } 
//        ball.checkCollisionWith(user);
//        ball.checkCollisionWith(computer);
//        ball.checkCollisionWith(computer1);
//        ball.checkCollisionWith(computer2);

    }
    
    //For drawing the board of the Game
    //using the sets of rectangles and the filling them with the 
    //colours
    
    public void paintComponent(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;
        g.setColor(PrimaryLight);
        g.fillRect(0, 0, WINDOW_WIDTH_Margin, WINDOW_HEIGHT_Margin);
        g.setColor(PrimaryDark);
        g.drawRect(0,margin,margin,WINDOW_HEIGHT);
        g.drawRect(0,0,WINDOW_WIDTH + 2*margin,margin);
        g.drawRect(WINDOW_WIDTH+ margin, 0 + margin, margin, WINDOW_HEIGHT);
        g.drawRect(0 ,WINDOW_HEIGHT + margin , WINDOW_WIDTH+2*margin, margin);

        //g.drawLine(0, 30 + margin, WINDOW_WIDTH +margin,margin + 30);
        g.fillOval(margin+(WINDOW_WIDTH / 2) - 30, WINDOW_HEIGHT / 2 - 30+margin, 60, 60);
        g.setColor(PrimaryLight);
        g.fillOval(margin+(WINDOW_WIDTH / 2) - 25, WINDOW_HEIGHT / 2 - 25+margin, 50, 50);
  
        g.setColor(PrimaryLEFT);
        g.fillRect(margin, margin, corner, corner);
        g.setColor(PrimaryDOWN);
        g.fillRect(margin, margin+WINDOW_HEIGHT - corner,corner, corner);
        g.setColor(PrimaryRIGHT);
        g.fillRect(margin+WINDOW_WIDTH - corner, margin+WINDOW_HEIGHT- corner,corner, corner);
        g.setColor(PrimaryUP);
        g.fillRect(margin+WINDOW_WIDTH - corner, margin,corner, corner);
        for(int i=0;i<3;i++)
         ball[i].paint(g);
        for (int i=0 ;i<4;i++){
            if (players[i] != null)
                players[i].paint(g);
        } 
//        user.paint(g);
//       
//        computer.paint(g);
//        computer1.paint(g);
//        computer2.paint(g);
        
       /* g.setColor(Color.red);
        g.drawRect(WINDOW_WIDTH - corner, 0,corner, corner);
        g.fillRect(WINDOW_WIDTH - corner, 0,corner, corner);
        g.setColor(Color.red);
        g.drawRect(WINDOW_HEIGHT-corner, 0,corner, corner);
        g.fillRect(WINDOW_HEIGHT-corner, 0,corner, corner);
        */
       String name = Data.getInstance().masterip;
       g.setColor(GamePanel.PrimaryDark);
       g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
       g.drawString(name, 10, 15);
       if(pause){
            g.setFont(new Font("TimesRoman", Font.PLAIN, 50));
            g.setColor(Color.red);
            g.drawString("PAUSE", 200, margin + WINDOW_HEIGHT/2);
       }
    }

    public Ball getBall() {
        return ball[0];
    }
    
    public Player getPCuser(){
        String myid = Data.getInstance().myid;
        for(int i =0;i<4;i++){
            if (players[i] != null)
                if(players[i].id.equals(myid))
                    return players[i];
        }
        return null;
    }
    
    // Every second turn should sent data
    public void actionPerformed(ActionEvent e) {
        if (!pause)
        {   update();
            turn++;
        }
        repaint();
        
        
        PCUser pcv = (PCUser) getPCuser();
        if(pcv !=null){
            if(turn %2 ==0 && Data.getInstance().masterid.equals(Data.getInstance().myid))
        {
            
            String turndata = "TurnData " + turn+" "+ pcv.getX()+" "+ pcv.getY()+" "+ball[0].getData();
            ArrayList<Thread> streamsd =Streams.getInstance().streams; 
            for (int i=0;i<streamsd.size();i++){
			 PlayerTh player = (PlayerTh) streamsd.get(i);
			 player.writeBack(turndata);
		 }
        }
            else{
                String turndata = "TurnData " + turn+" "+ pcv.getX()+" "+ pcv.getY();
            ArrayList<Thread> streamsd =Streams.getInstance().streams; 
            for (int i=0;i<streamsd.size();i++){
			 PlayerTh player = (PlayerTh) streamsd.get(i);
			 player.writeBack(turndata);
		 }
            }
        if(turn%30==0){
            
            String turndata = "ScoreData " +pcv.id+";"+ pcv.getScore() ;
            if(Data.getInstance().masterid.equals(Data.getInstance().myid)){
                ArrayList<Player> playercp = getcomputerplayers();
            for (int i=0;i<playercp.size();i++){
                turndata +=","+ playercp.get(i).id+";"+ playercp.get(i).getScore();
            }
            }
            
            ArrayList<Thread> streamsd =Streams.getInstance().streams; 
            for (int i=0;i<streamsd.size();i++){
			 PlayerTh player = (PlayerTh) streamsd.get(i);
			 player.writeBack(turndata);
		 }
        }
    }
        if(pcv.getScore()> 100){
            removeplayer(pcv.id);
        }
        
        }
        
    // this fuction is for the movement of the player
    // it uses Arrow keys for this action
   
    public void keyPressed(KeyEvent e) {
        PCUser user = (PCUser)getPCuser();
        switch (user.getPosition()) {
            case Player.POS_UP:
            case Player.POS_DOWN:  if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                                        user.setVelocity(-5);
                                        if (user.getX() < 0) {
                                            user.setVelocity(0);
                                        }
                                    } 
                                    else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                                        user.setVelocity(5);
                                        if (user.getX() + user.getWidth() > WINDOW_WIDTH) {
                                            user.setVelocity(0);
                                        }
                                    }
                break;
            case Player.POS_LEFT:  
            case Player.POS_RIGHT:  if (e.getKeyCode() == KeyEvent.VK_UP) {
                                        user.setVelocity(-5);
                                        if (user.getY() < 0) {
                                            user.setVelocity(0);
                                        }
                                    } 
                                    else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                                        user.setVelocity(5);
                                        if (user.getY() + user.getHeight() > WINDOW_HEIGHT) {
                                            user.setVelocity(0);
                                        }
                                    }
                break;
            default:
                break;
        }   
    }
    // as the key is released the Player stop moving
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        PCUser user = (PCUser)getPCuser();
        switch (user.getPosition()) {
            case Player.POS_UP:
            case Player.POS_DOWN:   if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_RIGHT) {
                                        user.setVelocity(0);
                                    }
                break;
            case Player.POS_LEFT:  
            case Player.POS_RIGHT:  if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
                                        user.setVelocity(0);
                                    }
                break;
            default:
                break;
        }   
        if (keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) {
            user.setVelocity(0);
        }
    }
    public ArrayList<Player> getnetworkplayers(){
        ArrayList<Player> networkp = new ArrayList();
        for(int i=0;i<3;i++){
                        if(players[i]!=null)
                 if(players[i].isNetwork)
                networkp.add(players[i]);
        }
        return networkp;
    }
    
    public ArrayList<Player> getcomputerplayers(){
        ArrayList<Player> computerp = new ArrayList();
        for(int i=0;i<4;i++){
            if(players[i]!=null)
            if(players[i].isComputer)
                computerp.add(players[i]);
        }
        System.out.println(computerp);
        return computerp;
    }
    
    public void keyTyped(KeyEvent e) {
    }
    public void updatenetworkplayers(String id ,int turn,int x ,int y){
        ArrayList<Player> networkp = getnetworkplayers();
        //System.out.println(id+" "+turn+ " "+x+" "+y);
        for(int i=0;i<networkp.size();i++)
        {
            NetworkUser net =(NetworkUser) networkp.get(i);
            if(net!=null)
            if(id.equals(net.id))
            {
                if(turn > net.getturn())
                {
                    net.setnetwork( x, y);
                     net.setturn(turn);
                }
               
                
                //System.out.println(net.id+" "+net.getturn()+" "+net.networkx+" "+net.networky);
                break;
            }
        }
        
    }
    
    public void updateBall(int px , int py , int vx , int vy){
        ball[0].setBall(px, py, vx, vy);
    }
    public void setScore(String id ,int score){
       
        for(int i=0;i<4;i++)
        {
           Player cp = players[i];
           if (cp != null)
                if(id.equals(cp.id))
                {

                    cp.setscore( score);
                    if (cp.score >100 )
                        players[i] =null ;
                    break;
                }
        }
        
    }
    
    
    public void replaceplayerwithcomp(String playerid,PlayerData compid){
        for (int i=0;i<4;i++)
        {
            Player joker = players[i];
            if(joker !=null)
            if(joker.id.equals(playerid)){
                players[i] = new Computer(players[i].getPosition());
                 players[i].id = compid.playerid;
                 players[i].name = compid.name;
                 players[i].isComputer = true;
                 players[i].setscore(joker.getScore());
                 ((Computer )players[i]).setgame(this);
                break;
            }
        }
        
    }
    
    public int removeplayer(String playerid){
        int n=0;
        for (int i=0;i<4;i++)
        {
            Player joker = players[i];
            if (joker !=null)
            if(joker.id.equals(playerid)){
                 players[i]=null;
                break;
            }
        }
        return n;
        
    }
    
}
