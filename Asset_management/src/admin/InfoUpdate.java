package admin;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

import dao.UserDAO;
import vo.AdminVO;
import vo.UserVO;

public class InfoUpdate {

	String check = "";
	boolean pw_check = false;
	boolean acc_check = false;

	public InfoUpdate(UserVO vo, AdminVO adVO) {

		// 프레임 생성
		Frame mainFrame = new Frame("고객정보 수정");
		// 버튼 생성
		Button btn_pwchange = new Button("암호변경");
		Button btn_acchange = new Button("계좌변경");
		Button btn_save = new Button("저장하기");
		Button btn_before = new Button("이전페이지");
		// Label 생성
		Label infoLabel = new Label("User Profiles");
		Label noticeLabel = new Label("※ 아이디 변경 불가능 ※");
		Label idLabel = new Label("ID");
		Label pwdLabel = new Label("PW");
		Label accLabel = new Label("계좌번호");
		// 폰트 생성
		Font font_infoLabel = new Font("", Font.BOLD, 35);
		Font font_noticeLabel = new Font("", Font.BOLD, 20);
		Font font_lb_etc = new Font("", Font.PLAIN, 16);
		// 텍스트필드 생성
		TextField tf_id = new TextField();
		TextField tf_pw = new TextField();
		TextField tf_acc = new TextField();

		// 텍스트필드 값 채우기
		tf_id.setText(vo.getId());
		tf_pw.setText(vo.getPwd());
		tf_acc.setText(vo.getAccountNumber());

		// 텍스트필드 수정불가
		tf_id.setEnabled(false);
		tf_pw.setEnabled(false);
		tf_acc.setEnabled(false);

		// 비밀번호 별표
		tf_pw.setEchoChar('*');

		// 메인프레임 세팅
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);

		// X키 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// 폰트 설정
		infoLabel.setFont(font_infoLabel);
		noticeLabel.setFont(font_noticeLabel);
		idLabel.setFont(font_noticeLabel);
		pwdLabel.setFont(font_noticeLabel);
		accLabel.setFont(font_lb_etc);

		// 색상 설정
		mainFrame.setBackground(Color.DARK_GRAY);
		infoLabel.setForeground(Color.WHITE);
		noticeLabel.setForeground(Color.WHITE);
		idLabel.setForeground(Color.WHITE);
		pwdLabel.setForeground(Color.WHITE);
		accLabel.setForeground(Color.WHITE);

		// 위치 설정
		mainFrame.setBounds(480, 200, 450, 550);
		infoLabel.setBounds(130, 50, 250, 70);
		noticeLabel.setBounds(100, 440, 400, 30);
		idLabel.setBounds(130, 170, 30, 25);
		tf_id.setBounds(180, 170, 150, 25);
		pwdLabel.setBounds(130, 220, 35, 25);
		tf_pw.setBounds(180, 220, 150, 25);
		accLabel.setBounds(100, 257, 80, 50);
		tf_acc.setBounds(180, 270, 150, 25);
		btn_pwchange.setBounds(130, 320, 60, 30);
		btn_acchange.setBounds(200, 320, 60, 30);
		btn_save.setBounds(270, 320, 60, 30);
		btn_before.setBounds(20, 50, 70, 30);

		// 프레임에 등록
		mainFrame.add(infoLabel);
		mainFrame.add(noticeLabel);
		mainFrame.add(idLabel);
		mainFrame.add(pwdLabel);
		mainFrame.add(accLabel);
		mainFrame.add(tf_id);
		mainFrame.add(tf_pw);
		mainFrame.add(tf_acc);
		mainFrame.add(btn_pwchange);
		mainFrame.add(btn_acchange);
		mainFrame.add(btn_save);
		mainFrame.add(btn_before);

		Choice acc_choice = new Choice();

		acc_choice.add(vo.getAccountNumber());

		// 뒤로가기 버튼 동작설정
		btn_before.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.dispose();
				new InfoModiPage(adVO);
			}
		});

		// 암호변경 버튼 동작설정
		btn_pwchange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				tf_pw.setEnabled(true);
				tf_pw.setText("");
				pw_check = true;

				if (check.equals("계좌")) {
					check = "둘다변경";
				} else {
					check = "암호";
				}
			}
		});

		// 계좌변경 버튼 동작설정
		btn_acchange.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tf_acc.setEnabled(true);
				tf_acc.setText("");
				acc_check = true;

				if (check.equals("암호")) {
					check = "둘다변경";
				} else {
					check = "계좌";
				}
			}
		});

		// 저장하기 버튼 동작설정
		btn_save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String id = tf_id.getText();
				String pwd = tf_pw.getText().trim();
				String acc = tf_acc.getText().trim();
				int res;

				if (pw_check || acc_check) {

					if (id.equals("") || acc.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "모든 정보를 작성해주세요.");
					} else if (acc.length() != 9 || !acc.matches("[0-9]{9}$")) {
						JOptionPane.showMessageDialog(mainFrame, "계좌번호는 9자리 숫자로 설정해주세요.");
					} else {

						switch (check) {

						case "계좌":

							res = UserDAO.getInstance().checkList(acc);

							if (res == 0) {
								JOptionPane.showMessageDialog(mainFrame, "이미 존재하는 계좌번호입니다.");
								break;
							}

							vo.setAccountNumber(acc);
							UserDAO.getInstance().modiInfo(vo);
							mainFrame.dispose();
							JOptionPane.showMessageDialog(mainFrame,
									"계좌번호 : " + vo.getAccountNumber() + "\n 으로 변경되었습니다.");
							new InfoModiPage(adVO);
							break;

						case "암호":

							vo.setPwd(pwd);
							UserDAO.getInstance().modiInfo(vo);
							mainFrame.dispose();
							JOptionPane.showMessageDialog(mainFrame, "암호 : " + vo.getPwd() + "\n 으로 변경되었습니다.");
							new InfoModiPage(adVO);
							break;

						case "둘다변경":

							res = UserDAO.getInstance().checkList(acc);

							if (res == 0) {
								JOptionPane.showMessageDialog(mainFrame, "이미 존재하는 계좌번호입니다.");
								break;
							}

							vo.setPwd(pwd);
							vo.setAccountNumber(acc);
							UserDAO.getInstance().modiInfo(vo);
							mainFrame.dispose();
							JOptionPane.showMessageDialog(mainFrame, "암호 : " + vo.getPwd() + "\n" + "계좌번호 : "
									+ vo.getAccountNumber() + "\n 으로 변경되었습니다.");
							new InfoModiPage(adVO);
							break;

						}
					}
				} else {
					JOptionPane.showMessageDialog(mainFrame, "수정하신 내용이 없습니다.");
				}

			}
		});

	}
}
