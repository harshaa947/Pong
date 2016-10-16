package pkg4.pong.network;
/*
* Sent when ypu recieve connect request 
* and you are not master peer 
* need to be sent with masterip and masterid
*/
public class NoMpPacket {
	String message = "Can't join the game";
	int errorcode;
	String masterip;
	public String toString(){
		return Integer.toString(errorcode) + " "  + masterip ; 
		}
}