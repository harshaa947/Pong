package pkg4.pong.network;
/*
Player data including name ip and id
*/
public class PlayerData{
	public String playerid;
	public String name;
	public String ip;
        
        public int pos;
	public String toString (){
		return playerid+";"+name+";"+ip+";"+pos;
	}
}