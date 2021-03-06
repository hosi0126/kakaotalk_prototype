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
	
	Client_chat chat;//채널 새창*채팅목록에서 새로열기 가능하게 바꾸기
	
	public KakaoMain(){
		DBConn();
		panel=new JPanel[2];
		panel[0]=new LoginPanel(this);
		
		add(panel[0]);

		chat=new Client_chat(this);/////////채팅 이건 연결을 할때 스레드에서 호출하니까 seemain에서 하면 널값들어옴
		
		setUndecorated(true); //타이틀바 제거
		setBounds(100,100,360,590);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	public void seeMain(String loginEmail,Vector<MemberList> memberList){
		
		this.loginEmail=loginEmail;
		this.memberList=memberList;

		System.out.println("로그인 성공한 아이디는?? " + loginEmail);
		System.out.println("멤버안에들어있는 사람의 주소는??" + memberList);
		System.out.println("두번째 안에 있는 사람의 e_mail은? "+ memberList.get(1).getE_mail());
		
		
		p_center=new JPanel();

		menuPanel=new MenuPanel(this);
		friendsListPanel=new FriendsListPanel(this);
		chattingListPanel=new ChattingListPanel(this);
		settingPanel=new SettingPanel();
		
		
		panel[1]=new JPanel();	
		panel[1].setLayout(new BorderLayout()); //panel[1]의 북쪽에 메뉴바, 센터에 패널3개
	
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

	
	
	//윈도우안에 어떤 패널이 올지를 결정해주는 메서드 정의
	public void setPage(int index){
		for(int i=0; i<panel.length; i++){
			if(i==index){
				panel[i].setVisible(true);
			}else{
				panel[i].setVisible(false);
			}
			//pack(); //내용물의 크기만큼 윈도우의 크기 설정
			//setLocationRelativeTo(null); //화면 중앙
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
		//DB연결 객체 생성.	
	}
	
	public static void main(String[] args) {
		new KakaoMain();
	}
	
}
