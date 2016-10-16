/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.game;

/**
 *
 * @author Vikas
 *this class aims for functioning of the computer Player
 * this creates a predefined object of Player class
 */
public class Computer extends Player{
    GamePanel game;
    
    public Computer(int pos){
        super(pos);
    }
    
    public void setgame(GamePanel g){
        game = g ;
    }
    
    public void update() {
        Ball ball = game.getBall();
        switch (position) {
            case POS_UP:
            case POS_DOWN:  if (ball.getX() < this.x) {
                                xVelocity = -5;
                            } 
                            else if (ball.getX() > this.x) {
                                xVelocity = 5;
                            }
                break;
            case POS_LEFT:  
            case POS_RIGHT:  if (ball.getY() < this.y) {
                                yVelocity = -5;
                            } 
                            else if (ball.getY() > this.y) {
                                yVelocity = 5;
                            }
                break;
            default:
                break;
        } 
        super.update();
    }
}
