/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.game;
import java.awt.Color;
import java.awt.Graphics2D;
/**
 *
 * @author Vikas
 
 Ball is created in this class for the game 
 Position of the Ball is decided by this class
here x and y are respective coordinates of the ball
* xV and yV are respective velocity of the ball
*/
public class Ball {
    private int x = GamePanel.WINDOW_WIDTH / 2;      // 
    private int y = GamePanel.WINDOW_HEIGHT / 2;
    private final int xV = 5;
    private final int yV = 6;
    private int xVelocity = -xV;
    private int yVelocity = yV;
    private int size = 7;
    
    public Ball(int i){
        xVelocity = xVelocity + 2*i;
        yVelocity = yV + -2*i -1;
    }

    //this function checks if ball hits the Wall 
    //if it hits then it reflects the ball perfectly ellastic collision
   //margin refers to the margin given in the sides of the game
    public int update() {
        int pos = 0;
        x = x + xVelocity;
        y = y + yVelocity;
        int margin = GamePanel.margin;
        if (x - size - margin< 0) {
            xVelocity = xV;
            if (y - size > margin + GamePanel.corner && y + size < margin + GamePanel.WINDOW_HEIGHT - GamePanel.corner)
                pos = Player.POS_LEFT;
        } 
        else if (x + size - margin> GamePanel.WINDOW_WIDTH) {
            xVelocity = -xV;
            if (y - size > margin + GamePanel.corner && y + size < margin + GamePanel.WINDOW_HEIGHT - GamePanel.corner)
                pos = Player.POS_RIGHT;
        }

        if (y - size - margin < 0) {
            yVelocity = yV;
            if (x - size > margin + GamePanel.corner && x + size < margin + GamePanel.WINDOW_WIDTH - GamePanel.corner)
                pos = Player.POS_UP;
        } 
        else if (y + size - margin> GamePanel.WINDOW_HEIGHT) {
            yVelocity = -yV;
            if (x - size > margin + GamePanel.corner && x + size < margin + GamePanel.WINDOW_WIDTH - GamePanel.corner)
                pos = Player.POS_DOWN;
        }
        return pos;
    }

    public void paint(Graphics2D g) {
        g.setColor(GamePanel.PrimaryAccent);
        g.fillOval(x - size, y - size, 2*size, 2*size);
        g.setColor(Color.BLACK);
        g.drawOval(x - size, y - size, 2*size, 2*size);
    }
    
    
    //this fuction checks if the Ball is being hit by the player or not
    //if it hit then it reflects the ball appropriately
    public void checkCollisionWith(Player player) {
        switch (player.getPosition()) {
            case Player.POS_UP:    if (this.y - size < player.getY() + player.getHeight() && this.x > player.getX() && this.x < player.getX() + player.getWidth())
                                        yVelocity = yV;
                break;
            case Player.POS_DOWN:  if (this.y + size > player.getY() && this.x > player.getX() && this.x < player.getX() + player.getWidth())
                                        yVelocity = -yV;
                break;
            case Player.POS_LEFT:  if (this.x - size < player.getX() + player.getWidth() && this.y > player.getY() && this.y < player.getY() + player.getHeight())
                                        xVelocity = xV;
                break;
            case Player.POS_RIGHT: if (this.x + size > player.getX() && this.y > player.getY() && this.y < player.getY() + player.getHeight())
                                        xVelocity = -xV;
                break;
            default:
                break;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    public void setBall(int px , int py , int vx , int vy){
        this.x = px;
        this.y = py;
        this.xVelocity = vx;
        this.yVelocity = vy;
    }
    
    public String getData(){
        String temp = "" + x +" "+y+" "+xVelocity+" "+yVelocity;
        return temp;
    }
}
