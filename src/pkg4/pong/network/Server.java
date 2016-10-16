package pkg4.pong.network;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.UUID;
/*
* Server of peer recieve connect and intr request 
* start playert for further communication between peers.
*/
public class Server extends Thread
{
   private ServerSocket serverSocket;
   private boolean isGameRunning=true;	
   private boolean isMaster = false;
   public Server(int port ,boolean isMaster) throws IOException
   {
      serverSocket = new ServerSocket(port);
      //serverSocket.setSoTimeout(10000);
	  this.isMaster = isMaster;
   }
   
   
   public void run()
   {
       int pos = 2;
       Data.getInstance().maxpos=2;
      while(isGameRunning)
      {
         try
         {
           // System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
			//System.out.println("Just connected to " + server.getRemoteSocketAddress());
			DataInputStream in =
                  new DataInputStream(server.getInputStream());
				  			System.out.println("reached2");
            
			String signal = in.readUTF();
			//System.out.println("reached3");
			System.out.println(signal);
			String[] strs = signal.split("\\s+");
				if(strs[0].equals("Connect") ){
					if(isMaster)
					{	if(Data.getInstance().pd.size() < 3){
						Thread pit = new PlayerTh(server,strs[2],strs[3],"",pos,true);
						pit.start();
                                                pos++;
                                                Data.getInstance().maxpos=pos;
						Streams.getInstance().streams.add(pit);
						System.out.println(Streams.getInstance().streams);
					} else {
						DataOutputStream out = new DataOutputStream(server.getOutputStream());
							out.writeUTF("NoJn 0 ");
					}
						
					}
					else {
						 DataOutputStream out = new DataOutputStream(server.getOutputStream());
							out.writeUTF("NoMP 1 "+Data.getInstance().masterid);
					}
				}
				else if (strs[0].equals("Intr") && !isMaster) {
					Thread pit = new PlayerTh(server,strs[2],strs[3],strs[1],Integer.parseInt(strs[4]),false);     // myid ,myname , myip , mypos
						pit.start();
						Streams.getInstance().streams.add(pit);
						System.out.println(Streams.getInstance().streams);
				}
            
         }catch(SocketTimeoutException s)
         {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e)
         {
            e.printStackTrace();
            break;
         }
      }
   }
   
}