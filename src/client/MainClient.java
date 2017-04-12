package client;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class MainClient extends JFrame{
	JPanel p_center;
	JPanel[] page=new JPanel[1];
	Client_chat chat;
	
	public MainClient(){
		chat=new Client_chat(this);
		p_center=new JPanel();
		page[0]=new Client_login(this);
		
		p_center.add(page[0]);
		add(p_center);
		
		setSize(360,590);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String[] args) {
		new MainClient();
	}
}