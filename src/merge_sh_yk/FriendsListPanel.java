package merge_sh_yk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import db.DBManager;

public class FriendsListPanel extends JPanel{
	JPanel p_search; //�˻� �г�
	JPanel p_list; //p_search�κ� ������ �Ʒ��κ� ��ü �г�-�׸���
	JPanel p_myProfile;
	JTextField t_search;
	JLabel la_myProfile, la_friends;
	JScrollPane scroll;
	int friends_count=0; //ģ�� ��
	
	PersonPanel[] people=new PersonPanel[10];
	KakaoMain kakaoMain;
	
	
	String myPhotoPath, myName, myStatusMsg;
	int j=0; //�α����ѻ���� ������ �������Ʈ���� ã������ ����.

	
	public FriendsListPanel(KakaoMain kakaoMain){
		this.kakaoMain= kakaoMain;
		
		p_search=new JPanel();
		t_search=new JTextField("�̸��˻�", 30);
		p_list=new JPanel();
		p_myProfile=new JPanel();
		la_myProfile=new JLabel("    �� ������ ");
		
		scroll=new JScrollPane(p_list, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		//p_list.setPreferredSize(new Dimension(360, 100));
		p_list.setLayout(new GridLayout(20,1));
		p_list.setBackground(Color.WHITE);
		
		t_search.setPreferredSize(new Dimension(350, 30));
		p_search.setPreferredSize(new Dimension(360, 43));
		//p_search.setBackground(Color.BLUE);
		
		p_search.add(t_search);	
		
		
		friends_count=kakaoMain.memberList.size()-1;
		la_friends=new JLabel("    ģ��   "+friends_count);
		
		p_list.add(la_myProfile);
		
		//������ ����� ��ã��..
		for(int i=0;i<kakaoMain.memberList.size();i++){

			if(i==0){
				
			while( !(kakaoMain.loginEmail.equals(kakaoMain.memberList.get(j).getE_mail())) ){
				j++;
			}
			people[0]=new PersonPanel(kakaoMain,kakaoMain.memberList.get(j).getProfile_img(), kakaoMain.memberList.get(j).getNik_id(),  kakaoMain.memberList.get(j).getStatus_msg() );
			p_list.add(people[0]);
			p_list.add(la_friends);
			
			}
			
		}
		
		//������ �������� ģ���� ����ϱ�
		for(int i=0;i<kakaoMain.memberList.size();i++){
			if(kakaoMain.memberList.get(i).getE_mail().equals(kakaoMain.memberList.get(j).getE_mail()     )){
				continue;
				
			}else{
				people[i+1]=new PersonPanel(kakaoMain,kakaoMain.memberList.get(i).getProfile_img(), kakaoMain.memberList.get(i).getNik_id(),  kakaoMain.memberList.get(i).getStatus_msg() );
				p_list.add(people[i+1]);
			}
		}

		add(p_search, BorderLayout.NORTH);
		add(scroll);
		
		setBackground(Color.WHITE);
		setPreferredSize(new Dimension(360, 497));
		//setPreferredSize(new Dimension(360, 450));
		
	}
	
}

