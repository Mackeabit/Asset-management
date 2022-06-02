package user;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dao.UserDAO;
import vo.UserVO;

public class TransHistory {

	public TransHistory(UserVO vo) {
		
		String accCon = UserDAO.getInstance().acc(vo);
		
		Frame mainFrame = new Frame(vo.getId()+"님의 거래내역");
		Font mainFont = new Font("", Font.BOLD, 40);
		Font subFont = new Font("", Font.PLAIN, 20);
		Label inquryL = new Label("거래 내역",Label.RIGHT);
		Label accountL = new Label("나의 계좌번호 : "+vo.getAccountNumber(),Label.LEFT);
		Label accountM = new Label(vo.getMoney()+"원",Label.RIGHT); 
		TextArea ta = new TextArea(accCon, 0, 0, TextArea.SCROLLBARS_VERTICAL_ONLY);
		Button sendM = new Button("이체");
		Button back = new Button("뒤로가기");
		Button logout = new Button("로그아웃");
		
		ta.setEditable(false);
		
		mainFrame.setLayout(null);
		
		mainFrame.setVisible(true);
		
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		
		mainFrame.setResizable(false);
		
		mainFrame.setBackground(Color.WHITE);
		
		inquryL.setFont(subFont);
		accountM.setFont(mainFont);
		inquryL.setForeground(Color.DARK_GRAY);
		accountL.setForeground(Color.DARK_GRAY);
		accountM.setForeground(Color.DARK_GRAY);
		
		mainFrame.setBounds(480,200,450,550);//전체 프레임
		accountL.setBounds(9, 32, 200, 25);//계좌번호 Label
		inquryL.setBounds(130,90,130,40);//거래내역 Label
		accountM.setBounds(70,110,250,70);//~원 Label
		sendM.setBounds(110, 380, 100, 50);//이체버튼
		back.setBounds(220, 380, 100, 50);//뒤로가기버튼
		ta.setBounds(80, 180, 290, 180);//텍스트에어리어
		logout.setBounds(370, 40, 60, 25);//로그아웃버튼
		
		mainFrame.add(inquryL);
		mainFrame.add(accountL);
		mainFrame.add(accountM);
		mainFrame.add(sendM);
		mainFrame.add(logout);
		mainFrame.add(back);
		mainFrame.add(ta);
		
		
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"로그아웃":
					
					mainFrame.dispose();
					new LoginPage();
					break;
					
				
				case"이체" :
					mainFrame.dispose();
					new TransferPage(vo);
					break;
				
					
				case"뒤로가기":
					mainFrame.dispose();
					new UserPage(vo);
					break;
				
				}//switch
				
			}
		};
		
		sendM.addActionListener(al);
		logout.addActionListener(al);
		back.addActionListener(al);
		
	}//생성자
	
}
