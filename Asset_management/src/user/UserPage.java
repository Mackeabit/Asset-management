package user;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import dao.UserDAO;
import vo.UserVO;

public class UserPage {

	public UserPage(UserVO vo) {
		
		//new를 통해 호출할 때, vo에 최신화되지 못한 정보가 들어가있는 것을 방지하기 위해
		//selecOne을 통한 최신화
		UserVO voUpdate = UserDAO.getInstance().selectOne(vo.getId());
		
		Frame mainFrame = new Frame(voUpdate.getId()+"님 환영합니다.");
		Font mainFont = new Font("", Font.BOLD, 40);
		Font subFont = new Font("", Font.PLAIN, 20);
		Label serviceL = new Label("나의 계좌번호 : "+voUpdate.getAccountNumber(),Label.LEFT);
		Label account = new Label("통장 잔고",Label.RIGHT);
		Label accountM = new Label(String.format("%,d", voUpdate.getMoney())+"원",Label.CENTER);
		Button sendM = new Button("이체");
		Button checkM = new Button("거래내역");
		Button deluser = new Button("회원탈퇴");
		Button logout = new Button("로그아웃");
		
		//mainFrame 세팅
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setVisible(true);
		
		//X버튼 활성
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//폰트, 색상설정
		account.setFont(subFont);
		sendM.setFont(subFont);
		checkM.setFont(subFont);
		accountM.setFont(mainFont);
		serviceL.setForeground(Color.DARK_GRAY);
		account.setForeground(Color.DARK_GRAY);
		accountM.setForeground(Color.DARK_GRAY);
		
		//사이즈 셋팅
		mainFrame.setBounds(480,200,450,550);
		serviceL.setBounds(9, 35, 200, 25);
		account.setBounds(50,150,220,30);
		accountM.setBounds(70,170,310,100);
		sendM.setBounds(115,290,100,50);
		checkM.setBounds(225, 290, 100, 50);
		logout.setBounds(370, 42, 60, 25);
		deluser.setBounds(370, 505, 60, 25);
		
		//add활성
		mainFrame.add(serviceL);
		mainFrame.add(account);
		mainFrame.add(accountM);
		mainFrame.add(checkM);
		mainFrame.add(sendM);
		mainFrame.add(logout);
		mainFrame.add(deluser);
		
		
		//버튼 동작
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"로그아웃":
					//new를 통한 로그인 페이지 호출
					mainFrame.dispose();
					new LoginPage();
					break;
					
				
				case"이체" :
					//new를 통한 이체 페이지 호출(현재 로그인한 유저의 vo 넘겨줌)
					mainFrame.dispose();
					new TransferPage(voUpdate);
					break;
				
				case"회원탈퇴" :
					
					//DAO.del에 현재아이디를 보내는 형태로 삭제 진행
					int res = UserDAO.getInstance().del(voUpdate.getId());
					
					//res값을 통해 삭제유무 확인,(0=삭제X,1=삭제O)
					if(res == 1) {
						//회원탈퇴 후 로그인페이지로 이동
						mainFrame.dispose();
						new LoginPage();
						break;
						
					}else {
						JOptionPane.showMessageDialog(mainFrame, "삭제에 실패하였습니다.");
					}
					break;
					
					
				case"거래내역":
					//거래내역 페이지 호출(현재 로그인한 유저의 vo 넘겨줌)
					mainFrame.dispose();
					new TransHistory(voUpdate);
					break;
				
				}//switch
				
			}
		};
		
		//버튼 동작 활성
		sendM.addActionListener(al);
		checkM.addActionListener(al);
		logout.addActionListener(al);
		deluser.addActionListener(al);
		
	
	}//public service()
		
}
	
