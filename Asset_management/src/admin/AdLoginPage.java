package admin;

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

import dao.AdminDAO;
import user.LoginPage;
import vo.AdminVO;

public class AdLoginPage {
	public AdLoginPage() {

		// 프레임 생성
		Frame mainFrame = new Frame("관리자 로그인");
		// 라벨 생성
		Label mainL = new Label("Admin LOGIN");
		Label idLabel = new Label("ID :");
		Label pwdLabel = new Label("PW :");
		// 버튼 생성
		Button userButton = new Button("일반고객");
		Button loginButton = new Button("로그인"); // 로그인 버튼
		// 폰트 생성
		Font mainFont = new Font("", Font.BOLD, 30);
		Font font = new Font("", Font.PLAIN, 13);
		Font font2 = new Font("", Font.BOLD, 20);
		// 텍스트필드 생성
		TextField idt = new TextField(); // id 창
		TextField pwt = new TextField(); // pwd 창
		// 이미지 및 JLabel 생성
		ImageIcon icon = new ImageIcon("src/images2/admin.jpg");
		JLabel bg = new JLabel(icon);

		// 메인프레임 설정
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setBackground(Color.DARK_GRAY);
		mainFrame.setResizable(false);

		// 프레임,라벨,텍스트필드,버튼 등 배치
		mainFrame.setBounds(480, 200, 450, 550);
		userButton.setBounds(20, 40, 60, 25);
		loginButton.setBounds(180, 435, 100, 30);
		mainL.setBounds(135, 300, 200, 30);
		bg.setBounds(0, 70, 450, 200);
		idLabel.setBounds(132, 347, 40, 30);
		pwdLabel.setBounds(120, 388, 50, 30);
		idt.setBounds(180, 350, 150, 25);
		pwt.setBounds(180, 390, 150, 25);

		// X키 활성화
		mainFrame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// pw 입력시 '*' 마스킹처리
		pwt.setEchoChar('*');

		// 폰트 설정
		loginButton.setFont(font);
		mainL.setFont(mainFont);
		idLabel.setFont(font2);
		pwdLabel.setFont(font2);
		idt.setFont(font2);
		pwt.setFont(font2);

		// 색상 설정
		mainL.setForeground(Color.WHITE);
		idLabel.setForeground(Color.WHITE);
		pwdLabel.setForeground(Color.WHITE);

		// 프레임에 등록
		mainFrame.add(bg);
		mainFrame.add(mainL);
		mainFrame.add(idLabel);
		mainFrame.add(pwdLabel);
		mainFrame.add(userButton);
		mainFrame.add(idt);
		mainFrame.add(pwt);
		mainFrame.add(loginButton);

		// 버튼동작 설정
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				switch (e.getActionCommand()) {

				case "일반고객":
					mainFrame.dispose();
					new LoginPage();
					break;
					
				case "로그인":

					String id = idt.getText().trim();
					String pwd = pwt.getText().trim();

					if (id.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "아이디를 입력해주세요.");
						break;
					} else if (pwd.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호를 입력해주세요.");
						break;
					}

					int res = AdminDAO.getInstance().checkList(id);

					if (res == 1) {
						JOptionPane.showMessageDialog(mainFrame, "존재하지 않는 아이디 입니다.");
						break;
					}
					
					AdminVO vo = AdminDAO.getInstance().selectOne(id);

					if (pwd.equals(vo.getPwd())) {
						mainFrame.dispose();
						new AdminPage(vo);
						break;
					} else {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호가 일치하지 않습니다.");
					}

					break;

				}
			}
		};

		// 버튼 등록
		userButton.addActionListener(al);
		loginButton.addActionListener(al);

	}
}
