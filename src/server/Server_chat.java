package server;

import java.net.ServerSocket;


public class Server_chat extends Thread{
	ServerSocket server;
	
	public Server_chat(ServerSocket server) {
		this.server=server;
		
	}
}