package pkg4.pong.network;
/**
 * Welcome Packet contains details of other players
 * already connected
 */
import java.util.ArrayList;
 public class WelcomePacket {
 
 String newid ;
 String masterid;
 ArrayList<PlayerData> players = new ArrayList<PlayerData>();
 int playerno ,pos ; 
 public String toString (){
	 String temp = newid + " " + masterid + " " + Integer.toString(playerno) + " " ;
	 for (int i =0 ; i< players.size() ; i++)
		 if (i ==0)
		 temp += players.get(i).toString()  ;
		else temp += ","+players.get(i).toString()   ;
	 if(players.size() == 0) {
		 temp+="[]";
	 }
	 temp+=" "+pos;
	 return temp;
 }
 }