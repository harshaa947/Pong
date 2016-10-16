package pkg4.pong.network;
/*
* This packet will be sent when initializing connections. It will contain
hisport and will help to know other .
*/
public class ConPacket {
 public int myport ;
 public String toString(){
	 return Integer.toString(myport);
 }
 }