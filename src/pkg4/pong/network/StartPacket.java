/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.network;
import java.util.ArrayList;
/**
 *
 * @author Harsh
 * A packet indicator for starting game
 * 
 */
public class StartPacket {
    public ArrayList<PlayerData> pl = new ArrayList<PlayerData>();
    public String toString (){
	 String temp = "";
	 for (int i =0 ; i< pl.size() ; i++)
		 if (i ==0)
		 temp += pl.get(i).toString()  ;
		else temp += ","+pl.get(i).toString()   ;
	 if(pl.size() == 0) {
		 temp+="[]";
	 }
	 temp+=" ";
	 return temp;
 }
}
