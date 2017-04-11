package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;

public class MainServer extends JFrame implements Runnable{
	int port=7777;
	ServerSocket server;
	Thread thread;//accept 胶饭靛
	Socket socket;
	
	public MainServer() {
		thread=new Thread(this);//accept
		thread.start();
		
		setSize(360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void accept(){
		try {
			server=new ServerSocket(port);
			System.out.println("server 积己");
			
			while(true){
				socket=server.accept();
				String ip=socket.getInetAddress().getHostAddress();
				System.out.println(ip+" 立加\n");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void run() {
		accept();
	}
	public static void main(String[] args) {
		new MainServer();
	}


}
