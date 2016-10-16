///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package pkg4.pong.network;
//
//import java.util.ArrayList;
//import pkg4.pong.Pong;
//
///**
// *
// * @author Harsh
// */
//public class sample {
//     private void startButton(){
//        String name = userField.getText();
//         int port = Integer.parseInt(Port_NumberField.getText()) ;
//         System.out.println(name);
//         final Networkop network = new Networkop(name,port);
//                  System.out.println(name);
//
//                    network.Start();
//                  System.out.println(name);
//                   boolean check = true; 
//            wait = new Thread(){
//            public void run(){
//                 network.waityo();
//            }
//                };
//            //boolean check = network.waityo();
//                  System.out.println(name);
//           
//    }
//     
//    private void forcegamestart(){
//       wait.stop();
//        Data data = Data.getInstance();
//          int na = data.maxpos;
//          int nb = 4 - na+1;
//          System.out.println(""+na+" "+nb);
//          for(int i=0;i<nb;i++){
//              PlayerData playerc = sampleplayers.get(i);
//              playerc.pos = na +i ;
//              data.maxpos++;
//              data.cd.add(playerc);
//          }
//          StartPacket start = new StartPacket();
//          start.pl = data.cd;
//          String startdata = "Start " +start.toString() ;
//            ArrayList<Thread> streamsd =Streams.getInstance().streams; 
//            for (int i=0;i<streamsd.size();i++){
//			 PlayerTh player = (PlayerTh) streamsd.get(i);
//			 player.writeBack(startdata);
//		 }
//          Pong pong = new Pong("");
//    }
//    
//    private void joinButton(){
//        String name = userField.getText();
//         int port = Integer.parseInt(Port_NumberField.getText()) ;
//         System.out.println(name);
//         String ip = IP_addressField.getText();
//         Networkop network = new Networkop(name,ip,port);
//         network.Start();
//         boolean check = network.waityo();
//         if(check){
//             Pong pong = new Pong("");
//         }
//    }
//
//
//
//        Thread wait ;
//    ArrayList<PlayerData> sampleplayers = new ArrayList<PlayerData>();
//    public TextInputDemo() {
//        PlayerData computer = new PlayerData();
//          computer.playerid ="COmp123";
//          computer.name = "Smarty-Johny";
//            PlayerData computer1 = new PlayerData();
//          computer1.playerid ="Comp456";
//          computer1.name = "Clever-Jane";
//            PlayerData computer2 = new PlayerData();
//          computer2.playerid ="Comp789";
//          computer2.name = "Fast-jolly";
//          
//          sampleplayers.add(computer);
//          sampleplayers.add(computer1);
//           sampleplayers.add(computer2);
//    }
//    
//
//
