/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.game;

/**
 *
 * @author Vikas
 */
public class NetworkUser extends Player{
    //public String id ;
    int networkx;
    int networky;
    int turn;
    public NetworkUser(int pos){
        super(pos);
    }
    public void  updatePosition(int pos,int x,int y){
        this.x = x;
        this.y = y;
    }
    
    public void update(){
        
       this.x = this.networkx;
       this.y = this.networky;
    }
    
    public void setnetwork(int x , int y){
       this.networkx = x;
       this.networky = y;
    }
    public void setturn(int n){
        this.turn = n;
    }
    public int getturn (){
       return turn; 
    }
    public void setscore(int n){
        score = n;
    }
    public int getscore(){
        return score;
    }
}