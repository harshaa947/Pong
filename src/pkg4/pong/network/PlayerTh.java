package pkg4.pong.network;
/*
A blocking thread useful for reading and writing to live connections
*/
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
import pkg4.pong.Pong;
import java.util.Random;

 public class PlayerTh extends Thread {

     private Socket socket;
	 public String id ;
	 public String name ;
	 public String ip ;
	 private DataInputStream in ;
	 private DataOutputStream out ;	
	 boolean isGame = true;
     public PlayerTh(Socket socket ,String name , String ip , String id, int pos,boolean isMaster) throws IOException{
		 //this.id = id ;
         this.socket = socket;
		 if(id == "")
		 this.id = Integer.toString(Streams.getInstance().streams.size() + 1 ) + UUID.randomUUID().toString();
			else this.id = id;
		 try{
			 this.in = new DataInputStream(socket.getInputStream());
		 this.out = new DataOutputStream(socket.getOutputStream());
		 }catch (IOException e){
			 e.printStackTrace();
				
		 }
		 this.name = name ;
		 this.ip = ip;
		 PlayerData temp = new PlayerData();
			temp.name= name;
			temp.ip = ip;
			temp.playerid = this.id;
                        temp.pos = pos;
                        //Data.getInstance().pd.add(temp);
                        if(isMaster){
                            WelcomePacket welcome = new WelcomePacket();
			welcome.newid = this.id;
			welcome.masterid = Data.getInstance().masterid;
			welcome.players = Data.getInstance().pd;
			welcome.playerno= welcome.players.size();
                        welcome.pos = pos;
			out.writeUTF("Welcome " +welcome.toString()+" "+ Data.getInstance().myname);
						
                        }
			Data.getInstance().pd.add(temp);

     }
	 
	 public PlayerTh(Socket socket,String name , String ip , String id , boolean temp) {
		 this.socket = socket;
		  this.id = id;
		 try{
			 this.in = new DataInputStream(socket.getInputStream());
		 this.out = new DataOutputStream(socket.getOutputStream());
		 }catch (IOException e){
			 e.printStackTrace();
				
		 }
		 this.name = name ;
		 this.ip = ip;
		 
	 }
	
	 public boolean getStatus(){
		 try {
			 this.in= new DataInputStream(socket.getInputStream());
			 this.out= new DataOutputStream(socket.getOutputStream());
		 }
		 catch (IOException e){
			 e.printStackTrace();
			 return false;
		 }
		 return true;
	 }	
	
         // A looped run for reading data
     public void run() {
		 
		while(isGame){
			try{
                            
				String todo = in.readUTF();
                                //System.out.println(todo);
				String[] strs = todo.split("\\s+");
				if (strs[0].equals("TurnData")){
					 System.out.println(todo);
                                        int turn = Integer.parseInt(strs[1]);
                                        int x1 = Integer.parseInt(strs[2]);
                                        int x2 = Integer.parseInt(strs[3]);
                                        if(strs.length >5){
                                            int a = Integer.parseInt(strs[4]);
                                         int b = Integer.parseInt(strs[5]);
                                         int c = Integer.parseInt(strs[6]);
                                            int d = Integer.parseInt(strs[7]);
                                            Data.getInstance().Game.updateBall(a,b,c,d);
                                        }
                                         
                                      Data.getInstance().Game.updatenetworkplayers(this.id, turn,x1, x2);
                                      
				}
                                else if (strs[0].equals("Start")){
                                    
                                    if (!strs[1].equals("[]")){
                                        String[] pdstrs = strs[1].split(",");
                                    Data mydata = Data.getInstance();
					for(int i=0 ; i< pdstrs.length;i++){
						String[] parts = pdstrs[i].split(";"); 
						PlayerData player = new PlayerData();
						player.playerid = parts[0];
						player.name = parts[1];
						player.ip = parts[2];
                                                player.pos = Integer.parseInt(parts[3]);
						mydata.cd.add(player);
                                                }
                                          }
                                        Thread t = new Thread(){
                                            public void run(){
                                               Pong pong = new Pong(""); 
                                            }
                                        } ;
                                        t.start();
                                     }
                                else if (strs[0].equals("BallData")){
                                    int a = Integer.parseInt(strs[2]);
                                    int b = Integer.parseInt(strs[3]);
                                    int c = Integer.parseInt(strs[4]);
                                    int d = Integer.parseInt(strs[5]);
                                    Data.getInstance().Game.updateBall(a,b,c,d);
                                }
                                else if (strs[0].equals("ScoreData")){
                                    System.out.println(todo);
                                    if (strs[1].contains(",")){
                                        String[] pdstrs = strs[1].split(",");
					for(int i=0 ; i< pdstrs.length;i++){
						String[] parts = pdstrs[i].split(";"); 
                                                String yuo = parts[0];
                                                int score = Integer.parseInt(parts[1]);
                                    Data.getInstance().Game.setScore(yuo,score);
                                    }
                                        
                                    
                                }else{
                                                String[] parts = strs[1].split(";"); 
                                                String yuo = parts[0];
                                                int score = Integer.parseInt(parts[1]);
                                    Data.getInstance().Game.setScore(yuo,score);
                                                }
						}
                                else if(strs[0].equals("Replace")){
                                    Data data = Data.getInstance();
                                    PlayerData computer = new PlayerData();
                                    String replaceid = strs[1];
                                    String[] parts = strs[2].split(";");
                                computer.playerid =parts[0];
                                computer.name = parts[1];
                                computer.ip = parts[2];
                                data.cd.add(computer);
                                data.Game.replaceplayerwithcomp(replaceid, computer);
                                 Data.getInstance().Game.resume();
                                }
                        
                        } catch (IOException e) {
			 //System.out.println(e.toString());
			  isGame = false;
                          Data.getInstance().Game.Pause();
                        Streams.getInstance().streams.remove(this);

                          if(Data.getInstance().masterid.equals(Data.getInstance().myid))
                          {  Data data = Data.getInstance();
                              int n ;
                              PlayerData computer = new PlayerData();
                              Random rand = new Random();
                              n = rand.nextInt(900)+100;
                                computer.playerid ="COmp"+ n ;
                                computer.name = "Smarty-Johny";
                                computer.ip = "192.168.456.123:8000";
                                data.cd.add(computer);
                                data.Game.replaceplayerwithcomp(this.id, computer);
                                String sentdata="Replace "+this.id+" "+computer.toString();
                                ArrayList<Thread> streamsd =Streams.getInstance().streams; 
                                     for (int i=0;i<streamsd.size();i++){
                                    PlayerTh player = (PlayerTh) streamsd.get(i);
                                        player.writeBack(sentdata);
                                     }
                                Data.getInstance().Game.resume();
				}
                          
			}
		 		  
     }}
	 // A run for writing data . Non-blocking function
	 public void writeBack(String str){
		 try{
			 out.writeUTF(str);
		 } catch (IOException e){
			 //e.printStackTrace();
			 System.out.println(e.toString());
		 }
		 
	 }
	 
}
