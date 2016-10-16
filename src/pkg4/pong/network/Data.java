package pkg4.pong.network;
import pkg4.pong.game.GamePanel;

import java.util.ArrayList;
/*
* This is staic instance of dta that can be used to share data among
* different classes without passing refernce
*/
 public class Data {
	 public enum GameState { PAUSE, PLAY ,DELAY }  

	 public ArrayList<PlayerData> pd = new ArrayList<PlayerData>();
         public ArrayList<PlayerData> cd = new ArrayList<PlayerData>();
	 public ArrayList<TurnData>[] turndata =(ArrayList<TurnData>[]) new ArrayList[10000];
         public GamePanel Game;
	 public int mTurnNumber ;
	 public String masterid;
	 public String myip;
	 public String masterip ;
	 public String myid;
	 public String myname;
         public String[] networkusers;
         public String[] computerusers;
         public int mypos;
         public int maxpos;
	 public GameState mGameState ;
	 private static Data Datains ;
	 private Data(){
		 for(int i=0;i<10000;i++){
			 turndata[i]= new ArrayList<TurnData>();
		 }
                 //Game = new GamePanel(0,0);
                 networkusers = new String[3];
                 computerusers = new String[3];
	 }
	 
         public static Data getInstance(){
		 if (Datains == null){
			 Datains = new Data();
			 
		 }
		 return Datains ; 
	 }
         
 }