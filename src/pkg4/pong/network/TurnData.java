package pkg4.pong.network;
/**
 * TurnData representing his turn
 * @author Harsh
 */
public class TurnData{
	 int x1=0, l=0,v=0;
	 boolean power1;
	 boolean power2;
	 int turn;
	 String id ;
	 public String toString(){
		 return Integer.toString(x1) + " "+id;
	 }
 }