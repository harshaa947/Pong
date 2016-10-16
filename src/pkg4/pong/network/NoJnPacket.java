package pkg4.pong.network;
/*
* Sent when ypu recieve connect request 
* and you are  master peer but oppurtunity is full or game has started

*/
public class NoJnPacket {
	String message = "Can't join the game";
	int errorcode;
	public String toString(){
		return Integer.toString(errorcode); 
	}
}