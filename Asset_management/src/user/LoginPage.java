package user;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import admin.AdLoginPage;
import dao.UserDAO;
import vo.UserVO;

public class LoginPage {
	public LoginPage() {
		
		//최고관리자 계정의 기업총자산을 DB에 갱신하기 위해 호출
		UserDAO.getInstance().adminWrite();
		
		//프레임 생성
		Frame mainFrame = new Frame("로그인");
		//라벨 생성
		Label centerLabel = new Label("Bank LOG IN");
		Label idLabel = new Label("ID : ", Label.RIGHT);
		Label pwdLabel = new Label("PW : ", Label.RIGHT);
		//텍스트필드 생성
		TextField idt = new TextField(10);
		TextField pwt = new TextField(15);
		//버튼 생성
		Button loginButton = new Button("로그인");
		Button signButton = new Button("회원가입");
		Button adminButton = new Button("관리자");
		//폰트 생성
		Font centerFont = new Font("",Font.BOLD,30);
		Font subFont = new Font("",Font.BOLD,20);
		Font tfFont = new Font("", Font.PLAIN, 18);
		//이미지, JLabel 생성
		ImageIcon icon = new  ImageIcon("src/images2/bank.jpg");
		JLabel mainImage = new JLabel(icon);
		
		//메인프레임 설정
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setBackground(Color.WHITE);
		mainFrame.setResizable(false);
		
		//프레임,라벨,텍스트필드,버튼 등 배치
		mainFrame.setBounds(480,200,450,550);
		centerLabel.setBounds(130, 270, 400, 60);
		idLabel.setBounds(80,320,60,50);
		pwdLabel.setBounds(80,360,60,50);
		idt.setBounds(140, 335, 150, 25);
		pwt.setBounds(140, 375, 150, 25);
		loginButton.setBounds(300, 335, 60, 65);
		signButton.setBounds(190, 420, 70, 25);
		adminButton.setBounds(19, 40, 60, 25);
		mainImage.setBounds(0, 50, 450, 197);
		
		//X키 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//라벨 폰트 설정
		centerLabel.setFont(centerFont);
		idLabel.setFont(subFont);
		pwdLabel.setFont(subFont);
		//텍스트필드 폰트 설정
		idt.setFont(tfFont);
		pwt.setFont(tfFont);
		//라벨 글자색 설정
		centerLabel.setForeground(Color.DARK_GRAY);
		idLabel.setForeground(Color.DARK_GRAY);
		pwdLabel.setForeground(Color.DARK_GRAY);
		
		// pw 입력시 '*' 마스킹처리
		pwt.setEchoChar('*');

		//프레임에 등록
		mainFrame.add(centerLabel);
		mainFrame.add(idLabel);
		mainFrame.add(pwdLabel);
		mainFrame.add(idt);
		mainFrame.add(pwt);
		mainFrame.add(loginButton);
		mainFrame.add(signButton);
		mainFrame.add(adminButton);
		mainFrame.add(mainImage);
		
		//버튼 동작 설정
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"회원가입":
					
					mainFrame.dispose();
					new SignPage();
					break;
					
				
				case"관리자" :
					
					mainFrame.dispose();
					new AdLoginPage();
					break;
				
				case"로그인":
					
					String id = idt.getText().trim();
					String pwd = pwt.getText().trim();
					
					if(id.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "아이디를 입력해주세요.");
						break;
					}else if(pwd.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호를 입력해주세요.");
						break;
					}
					
					int res = UserDAO.getInstance().checkList(id);
					
					if(res == 1) {
						JOptionPane.showMessageDialog(mainFrame, "존재하지 않는 아이디 입니다.");
						break;
					}
					
					UserVO vo = UserDAO.getInstance().selectOne(id);
					
					if(pwd.equals(vo.getPwd())) {
						mainFrame.dispose();
						new UserPage(vo);
						break;
					}else {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호가 일치하지 않습니다.");
					}
					
					break;
					
				}//switch
				
			}
		};
		
		
		//버튼 등록
		signButton.addActionListener(al);
		adminButton.addActionListener(al);
		loginButton.addActionListener(al);
		
		
	}//생성자
}
