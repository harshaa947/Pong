package pkg4.pong.network;
/*
*This is the packet sent by user as an introduction to other players when entered into game as a token of his information required for game
*/
public class IntrPacket {
 
 String myid ;
 String myname;
 String myip ; 
 int mypos;
 public String toString (){
	 return myid + " " + myname + " " + myip + " "+mypos ;
 }
 }