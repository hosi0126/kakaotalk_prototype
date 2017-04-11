package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MainClient extends JFrame implements ActionListener{
	String ip;
	JButton bt;
	Socket socket;
	int port=7777;
	public MainClient(){
		loadIp();
		bt=new JButton("connect");
		add(bt);
		bt.addActionListener(this);
		setSize(360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void loadIp(){
			ip="211.238.142.121";//서버 아이피 주소 입력 txt 파일로 입력받아 세팅
			System.out.println(ip);
	}
	
	public void connect(){
		try {
			socket=new Socket(ip, port);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	public void actionPerformed(ActionEvent e) {
		connect();
	}
	
	public static void main(String[] args) {
		new MainClient();
	}

}