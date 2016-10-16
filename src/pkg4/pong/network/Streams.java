package pkg4.pong.network;

import java.util.ArrayList;
/**
 * Streams for input and output writing
 * Static instance can be called without instantaniation
*/
 public class Streams {
	     private static Streams instance;
		 public ArrayList<Thread> streams = new ArrayList<Thread>();
		 private Streams(){
			 
		 }
		 public static Streams getInstance() {
			 if (instance == null){
				 instance = new Streams();
			 }
			 return instance;
		 }	
 }