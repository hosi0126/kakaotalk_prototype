package merge_sh_yk;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//ȸ������
public class Register extends JDialog {
	   JLabel la_title, la_checkId, la_checkPw, la_info, l_cancle;
	   JPanel p_main, p_put, p_pw_bt;
	   HintTextField_FIRST t_email; 
	   HintTextField t_name, t_pw, t_pw_check;
	   JButton bt;
	   boolean flag=true;
	   
	   Member me;
	   KakaoMain kakaoMain;
	   private static final String EMAIL_PATTERN ="^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
	   
	   public Register(KakaoMain kakaoMain) {
	      this.kakaoMain=kakaoMain;
	      
	      la_title=new JLabel("Kakao ȸ������");
	      la_checkId=new JLabel();
	      la_checkPw=new JLabel();
	      la_info=new JLabel("���� ��ư�� ������ ������ �Ϸ� �˴ϴ�. ");
	      p_main=new JPanel();
	      p_put=new JPanel();
	      t_email = new HintTextField_FIRST("�̸����� �Է��ϼ���"); 
	      t_name=new HintTextField("�̸��� �Է��ϼ���");
	      t_pw= new HintTextField("��й�ȣ�� �Է��ϼ���"); 
	      t_pw_check= new HintTextField("��й�ȣ�� �ѹ� �� �Է��ϼ���");
	      bt=new JButton("����");
	      
	      p_main.setLayout(new FlowLayout());
	      p_main.setPreferredSize(new Dimension(360, 590));
	      p_main.setBackground(new Color(255, 235, 051));
	      
	      la_title.setPreferredSize(new Dimension(360, 50));
	      la_title.setHorizontalAlignment(JLabel.CENTER);
	      p_put.setPreferredSize(new Dimension(250, 300));
	      p_put.setBackground(new Color(255, 235, 051));
	      p_put.setLayout(new GridLayout(8, 1));

	      bt.setBackground(new Color(113, 92, 94));
	     
	      p_put.add(t_email);
	      p_put.add(la_checkId);
	      p_put.add(t_pw);
	      p_put.add(t_pw_check);
	      p_put.add(la_checkPw);
	      p_put.add(t_name);
	      p_put.add(la_info);
	      p_put.add(bt);
	      
	      p_main.add(la_title);
	      p_main.add(p_put);

	      add(p_main);
	      
	      t_email.addKeyListener(new KeyAdapter() {
		    	public void keyPressed(KeyEvent e) {
		    		if(e.getKeyCode()==KeyEvent.VK_ENTER){
		    			redundancyChecky();
		    		}
		    	} 
	      });
	      t_email.addFocusListener(new FocusListener() {
				public void focusLost(FocusEvent e) {
					//redundancyChecky();
				}
				
				public void focusGained(FocusEvent e) {
	
				}
	      });
	      
	      bt.addActionListener(new ActionListener() {
	         public void actionPerformed(ActionEvent e) {
	            join();
	         }
	      });   

	      setSize(380, 650);
	      setVisible(flag);
	   }

	   public boolean redundancyChecky(){
		   String input=t_email.getText();
		    
		   //�̸��� ��ȿ�� �˻�
		   boolean b = Pattern.matches(EMAIL_PATTERN, t_email.getText());
		   if(b==false){
			   JOptionPane.showMessageDialog(kakaoMain, "�̸��� ������ Ȯ�����ּ���");
		   }
		 
		 //�̸��� �ߺ��˻�
		   PreparedStatement pstmt=null;
		   ResultSet rs=null;
		   String sql="select e_mail from member where e_mail=?";
		   
		   try {
				pstmt=kakaoMain.con.prepareStatement(sql);
				pstmt.setString(1, input);
				ResultSet result=pstmt.executeQuery();
				
				if(rs.next()==false){
					System.out.println("�ߺ��� ����");
				} else {
					System.out.println("�ߺ��� ����");
				}
		   } catch (SQLException e) {
			   e.printStackTrace();
		   }
		   
		   return true;
	   }
	   
	   public void join(){   
	        /*
		   me=new Member();//�ν��Ͻ� �Ѱ� ����
		   me.setEmail(t_email.getText());
	     
	      
	      member.setE_mail(t_email.getText());
	      member.setNik_id(t_name.getText());
	      member.setPassword(t_pw.getText());
		   
		   me.setName(t_name.getText());
		   me.setPw(t_pw.getText());
	      PreparedStatement pstmt=null;
	      ResultSet rs=null;
	      String sql="insert into member(e_mail, nik_id, password) values(?, ?, ?)";
	      
	      try {
	         pstmt=kakaoMain.con.prepareStatement(sql);
	         System.out.println("��� ���̵� = " + member.getE_mail());
	         System.out.println("��� ���̵� = " + member.getNik_id());
	         System.out.println("��� ���̵� = " + member.getPassword());
	         
	         pstmt.setString(1, member.getE_mail());
	         pstmt.setString(2, member.getNik_id());
	         pstmt.setString(3, member.getPassword());
	         System.out.println("��� ���̵� = " + me.getEmail());
	         System.out.println("��� ���̵� = " + me.getName());
	         System.out.println("��� ���̵� = " + me.getPw());
	         
	         pstmt.setString(1, me.getEmail());
	         pstmt.setString(2, me.getName());
	         pstmt.setString(3, me.getPw());
	         rs=pstmt.executeQuery();
	      
	         JOptionPane.showMessageDialog(this, "ȸ�����Լ���!");
	         
	      } catch (SQLException e) {
	         e.printStackTrace();
	      } finally {
	         if(kakaoMain.con!=null){
	            try {
	            	kakaoMain.con.close();
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         }
	         if(rs!=null){
	            try {
	               rs.close();
	            } catch (SQLException e) {
	               e.printStackTrace();
	            }
	         }
	      }//finally
	        
	         */
	      
	   }

}
