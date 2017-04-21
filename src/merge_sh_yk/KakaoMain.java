package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;

import db.DBManager;

public class KakaoMain extends JFrame{
	Point mouseDownCompCoords = null;
	JPanel[] panel;
	JPanel menuPanel, friendsListPanel, chattingListPanel, settingPanel, p_center;
	DBManager manager;
	Connection con;
	String loginEmail;
	public Vector<MemberList> memberList;
	
	Client_chat chat;//ä�� ��â*ä�ø�Ͽ��� ���ο��� �����ϰ� �ٲٱ�
	
	public KakaoMain(){
		DBConn();
		panel=new JPanel[2];
		panel[0]=new LoginPanel(this);
		
		add(panel[0]);

		chat=new Client_chat(this);/////////ä�� �̰� ������ �Ҷ� �����忡�� ȣ���ϴϱ� seemain���� �ϸ� �ΰ�����
		
		setUndecorated(true); //Ÿ��Ʋ�� ����
		setBounds(100,100,360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void seeMain(String loginEmail,Vector<MemberList> memberList){
		
		this.loginEmail=loginEmail;
		this.memberList=memberList;

		System.out.println("�α��� ������ ���̵��?? " + loginEmail);
		System.out.println("����ȿ�����ִ� ����� �ּҴ�??" + memberList);
		System.out.println("�ι�° �ȿ� �ִ� ����� e_mail��? "+ memberList.get(1).getE_mail());
		
		
		p_center=new JPanel();

		menuPanel=new MenuPanel(this);
		friendsListPanel=new FriendsListPanel(this);
		chattingListPanel=new ChattingListPanel(this);
		settingPanel=new SettingPanel();
		
		
		panel[1]=new JPanel();	
		panel[1].setLayout(new BorderLayout()); //panel[1]�� ���ʿ� �޴���, ���Ϳ� �г�3��
	
		panel[1].add(menuPanel, BorderLayout.NORTH);
		p_center.add(friendsListPanel);
		p_center.add(chattingListPanel);
		p_center.add(settingPanel);
		panel[1].add(p_center);
		
		
		add(panel[1]);

		panel[0].setBackground(new Color(255, 235, 051));
		panel[0].setSize(360,590);
		
		panel[0].setVisible(false);
		panel[1].setVisible(true);

		dragMouse(panel[0]);
		

	}

	
	
	//������ȿ� � �г��� ������ �������ִ� �޼��� ����
	public void setPage(int index){
		for(int i=0; i<panel.length; i++){
			if(i==index){
				panel[i].setVisible(true);
			}else{
				panel[i].setVisible(false);
			}
			//pack(); //���빰�� ũ�⸸ŭ �������� ũ�� ����
			//setLocationRelativeTo(null); //ȭ�� �߾�
		}
	}
	
	public void dragMouse(JPanel panel){
		panel.addMouseListener(new MouseAdapter() {		
			@Override
			public void mouseReleased(MouseEvent e) {
				mouseDownCompCoords = null;
			}
			@Override
			public void mousePressed(MouseEvent e) {
				mouseDownCompCoords = e.getPoint();
			}
		});

		panel.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				Point currCoords = e.getLocationOnScreen();
		        setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
			}
		});
	}
	
	public void DBConn(){
		manager = DBManager.getInstance();
		con=manager.getConnection();
		//DB���� ��ü ����.	
	}
	
	public static void main(String[] args) {
		new KakaoMain();
	}
	
}
