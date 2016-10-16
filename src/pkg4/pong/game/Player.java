/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
/**
 *
 * @author Vikas
 * This class is for the functioning of the Player handle 
 * It sets the length position and other attributes of the Player handle.
 */
public class Player {
    final public static int POS_UP = 1;
    final public static int POS_DOWN = 2;
    final public static int POS_LEFT = 3;
    final public static int POS_RIGHT = 4;
    protected int position;
    protected int x;
    protected int y;
    protected int yVelocity;
    protected int xVelocity;
    protected int width;
    protected int height;
    protected int score;
    public boolean isPC = false;
    public boolean isNetwork = false;
    public boolean isComputer = false;
    public String id;
    public String name;
    //Placing the handles in the mid of their respective side 
    public Player(int pos) {
        position = pos;
        yVelocity = 0;
        xVelocity = 0;
        score = 0;
        switch (position) {
            case POS_UP:    x = GamePanel.WINDOW_WIDTH/2 - 20 +GamePanel.margin;
                            y = 0+GamePanel.margin;
                            width = 40;
                            height = 10;
                break;
            case POS_DOWN:  x = GamePanel.WINDOW_WIDTH/2 - 20+GamePanel.margin;
                            y = GamePanel.WINDOW_HEIGHT - 10+GamePanel.margin;
                            width = 40;
                            height = 10;
                break;
            case POS_LEFT:  y = GamePanel.WINDOW_HEIGHT/2 - 20+GamePanel.margin;
                            x = 0+GamePanel.margin;
                            width = 10;
                            height = 40;
                break;
            case POS_RIGHT: y = GamePanel.WINDOW_HEIGHT/2 - 20+GamePanel.margin;
                            x = GamePanel.WINDOW_WIDTH - 10+GamePanel.margin;
                            width = 10;
                            height = 40;
                break;
            default:
                break;
        }
    }

    // this function changes the position of the Player handle
    //depending upon the velocity of the velocity of the Player
    public void update() {
        int margin = GamePanel.margin + GamePanel.corner;
        int margin1 = GamePanel.margin - GamePanel.corner;
        switch (position) {
            case POS_UP:
            case POS_DOWN:  x = x + xVelocity;
                            if (x - margin < 0 && xVelocity < 0) {
                                x = x - xVelocity;
                            } 
                            else if (x + width - margin1 > GamePanel.WINDOW_WIDTH && xVelocity > 0) {
                                x = x - xVelocity;
                            }
                break;
            case POS_LEFT:  
            case POS_RIGHT: y = y + yVelocity;
                            if (y - margin < 0 && yVelocity < 0) {
                                y = y - yVelocity;
                            } 
                            else if (y + height - margin1 > GamePanel.WINDOW_HEIGHT && yVelocity > 0) {
                                y = y - yVelocity;
                            }
                break;
            default:
                break;
        }
    }

    // for setting colour of the player handle
    public void paint(Graphics2D g) {
        AffineTransform defaultAt = g.getTransform();
        AffineTransform at = new AffineTransform();
        switch (position) {
            case POS_UP:    g.setColor(GamePanel.PrimaryUP);
                            g.fillRect(x, y, width, height);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                            g.drawString(name, 200, 15);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                            g.setColor(Color.white);
                            g.drawString("" + score, GamePanel.margin+GamePanel.WINDOW_WIDTH - GamePanel.corner + 5, GamePanel.margin + 30);
                break;
            case POS_DOWN:  g.setColor(GamePanel.PrimaryDOWN);
                            g.fillRect(x, y, width, height);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                            g.drawString(name, 200, 15 + GamePanel.margin+GamePanel.WINDOW_HEIGHT);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                            g.setColor(Color.white);
                            g.drawString("" + score, GamePanel.margin + 5, GamePanel.margin+GamePanel.WINDOW_HEIGHT - GamePanel.corner + 30);
                break;
            case POS_LEFT:  g.setColor(GamePanel.PrimaryLEFT);
                            g.fillRect(x, y, width, height);
                            at.rotate(Math.PI / 2);
                            g.setTransform(at);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                            g.drawString(name, 200, -5);
                            g.setTransform(defaultAt);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                            g.setColor(Color.white);
                            g.drawString("" + score, GamePanel.margin + 5, GamePanel.margin + 30);
                break;
            case POS_RIGHT: g.setColor(GamePanel.PrimaryRIGHT);
                            g.fillRect(x, y, width, height);
                            at.rotate(Math.PI / 2);
                            g.setTransform(at);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                            g.drawString(name, 200, -(5+GamePanel.margin+GamePanel.WINDOW_HEIGHT));
                            g.setTransform(defaultAt);
                            g.setFont(new Font("TimesRoman", Font.PLAIN, 25));
                            g.setColor(Color.white);
                            g.drawString("" + score, GamePanel.margin+GamePanel.WINDOW_WIDTH - GamePanel.corner + 5, GamePanel.margin+GamePanel.WINDOW_HEIGHT- GamePanel.corner + 30);
                break;
            default:
                break;
        }
        
    }

    public void setVelocity(int speed) {
        switch (position) {
            case POS_UP:
            case POS_DOWN:  xVelocity = speed;
                break;
            case POS_LEFT:  
            case POS_RIGHT: yVelocity = speed;
                break;
            default:
                break;
        }    
    }
    
    public int getPosition(){
        return position;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public void incrementScore(){
        score++;
    }
    
    public int getScore(){
        return score;
    }
     public void setscore(int n){
        score = n;
    }
}
