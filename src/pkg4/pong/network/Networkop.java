/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg4.pong.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.UUID;
import pkg4.pong.Pong;

/**
 *
 * @author Harsh
 */
/*
* Class used for initializing connections .
* Creates Thread for server 
* If you join game help to create connection with all players
*/
public class Networkop {
        public String name,myip,masterip;
        boolean isMaster;
        boolean validateConnection = false;
        
        // constructor called when it is master peer
    public Networkop(String name , int port){
        this.name = name;
        this.myip = getip() +":"+port;
        isMaster = true;
        String temp = "0" + UUID.randomUUID().toString();
        Data data = Data.getInstance();
		  data.masterid = temp;
                  data.myid = temp;
                  data.myip = this.myip;
                  data.myname = this.name;
                  data.masterip =  this.myip;
                  data.mypos = 1;
    }
    
    // constructer called for joining game
    public Networkop(String name , String connectip , int port){
        this.name = name;
        this.myip = getip() +":"+port;
        this.masterip = connectip;
        isMaster = false;
        Data data = Data.getInstance();
		  
                  data.myip = this.myip;
                  data.myname = this.name;
                  data.masterip =  this.masterip;
    }
    
    // starts server and connects to other payers
    public void Start(){
        // Starting thread for server
        String host="";
            int port=0;
            try{
           URI uri = new URI("http://" + myip);
	   host = uri.getHost();
	   port = uri.getPort();
	  } catch(URISyntaxException e ){
		  System.out.println(e.toString());
	} 
            try
      {
         Thread t = new Server(port , isMaster);
         t.start();
      }catch(IOException e)
      {
         e.printStackTrace();
      }
	
             
        if(!isMaster){
                        try{
                                String host1="";
                                int port1=0;
                                try{
                                        URI uri = new URI("http://" + masterip);
                                        host1 = uri.getHost();
                                        port1 = uri.getPort();
                                       } 
                                catch(URISyntaxException e ){
                                               System.out.println(e.toString());
                                     } 
                                // connection with master peer 
			  Socket client = new Socket(host1, port1);
                          OutputStream outToServer = client.getOutputStream();
                          DataOutputStream out = new DataOutputStream(outToServer);
                            out.writeUTF("Connect "+ client.getLocalSocketAddress() + " " + name +" "+ myip );
                            InputStream inFromServer = client.getInputStream();
                            DataInputStream in =new DataInputStream(inFromServer);
                            String 	todo = in.readUTF();
                            System.out.println(todo);
			String[] strs = todo.split("\\s+");
                        // welcome data 
			if (strs[0].equals("Welcome")){
				Data mydata = Data.getInstance();
				mydata.myid = strs[1];
				mydata.masterid = strs[2];
                                mydata.mypos = Integer.parseInt(strs[5]);
				String temp = strs[4];
				Thread pit = new PlayerTh(client,strs[6],masterip , strs[2] ,true );
				pit.start();
				Streams.getInstance().streams.add(pit);
				//System.out.println(Streams.getInstance().streams);
				if(!strs[3].equals("0")){String[] pdstrs = temp.split(",");
					for(int i=0 ; i< pdstrs.length;i++){
						String[] parts = pdstrs[i].split(";"); 
						PlayerData player = new PlayerData();
						player.playerid = parts[0];
						player.name = parts[1];
						player.ip = parts[2];
                                                player.pos = Integer.parseInt(parts[3]);
						mydata.pd.add(player);
					}
				
				//System.out.println(mydata.pd);
				 for(int i=0 ; i< mydata.pd.size();i++){
					 PlayerData player = mydata.pd.get(i);
					 
					 int port2=0;
					  host1="";
					 try{
							//System.out.println(player.ip);
							URI uri = new URI("http://" + player.ip);
							host1 = uri.getHost();
							port2 = uri.getPort();
	   	
								  } catch(URISyntaxException e ){
									  System.out.println(e.toString());
									  
								  } 
					if (port1 == port2)
							continue;
                                        // connection with other players
					 Socket client1 = new Socket(host1, port2);
					Thread pit1 = new PlayerTh(client1,player.name,player.ip , player.playerid,true);
				pit1.start();
				Streams.getInstance().streams.add(pit1);
					OutputStream outToServer1 = client1.getOutputStream();
					DataOutputStream out1 = new DataOutputStream(outToServer1);
						IntrPacket intro = new IntrPacket();
						intro.myid = mydata.myid;
						intro.myip= mydata.myip;
						intro.myname = mydata.myname;
                                                intro.mypos = mydata.mypos;
						out1.writeUTF("Intr "+intro.toString());
						 }}
				
				PlayerData master = new PlayerData();
				master.name = strs[6];
				master.ip = this.masterip;
				master.playerid = strs[2];
                                master.pos = 1;
				mydata.pd.add(master);
			} else if (strs[0].equals("NoMp")){
                            this.masterip = strs[2];
                            Start();
                            Data.getInstance().masterip = this.masterip;
			}
		  } catch (IOException e){
			  e.printStackTrace();
		  } 
	  }}
    
    // A functional wait until 4 players come .need to run in separate thread due to blocking nature
        public boolean waityo(){
                        while(!validateConnection){
                               try{
					Thread.sleep(10);
				}
				catch (InterruptedException ie){
					System.out.println(ie.toString());
					}
//		if( Streams.getInstance().streams.size() == 3){
//                      Pong pong = new Pong("");
//			return true;  
	  
        
                           
        }
        return false;
        }
	
        // matches whether 192.168. is prefix or not
    public static boolean match192(String s){
              boolean temp ;
              temp = s.startsWith("192.168.43.");
              return temp;
          }
    
    /*
    * Get host ip but take care of not taking local ip and
    *only private one of series 19.168.
    */
    
    public static String getip(){
        InetAddress IP = null;
        String localip ="";
         try {
		  IP=InetAddress.getLocalHost();
                  localip = IP.getHostAddress();
	  }
	  catch(UnknownHostException ue){
		  ue.printStackTrace();
	  }

	  
	  String ipAddress = null;
          
	try {
                for (Enumeration en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                    NetworkInterface intf = (NetworkInterface)en.nextElement();
                    for (Enumeration enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = (InetAddress) enumIpAddr.nextElement();
                        if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                             ipAddress=inetAddress.getHostAddress();
                             if( match192(ipAddress) )
                                return ipAddress;
                        }
                    }
                }
                 return null;
            } catch (SocketException ex) {
                return null;
                 }
        
    }
}
11111111111111111111111111111111
11111111111111111111111
00000000000000000001000000000000
10000000000000000001000000000000
11000000000000000001000000000000
11100000000000000001000000000000
11100000010000000001000000000000
