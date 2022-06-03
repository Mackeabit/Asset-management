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

import javax.swing.JOptionPane;

import dao.AdminDAO;
import vo.AdminVO;

public class AddAdminPage {
	boolean check = false;
	public AddAdminPage(AdminVO adVO) {

		// 프레임 생성
		Frame mainFrame = new Frame("관리자 추가");
		// 라벨 생성
		Label mainLabel = new Label("Create Ac");
		Label account = new Label("계정권한 :");
		Label idl = new Label("아이디 :", Label.RIGHT);
		Label idml = new Label("영문,숫자만 입력 가능합니다.", Label.RIGHT);
		Label pwl = new Label("비밀번호 :", Label.RIGHT);
		Label pwcheck = new Label("PW 확인 : ", Label.RIGHT);
		// 사용할 폰트 설정
		Font mainFont = new Font("", Font.BOLD, 40);
		Font subFont = new Font("", Font.BOLD, 17);
		Font smlFont = new Font("", Font.PLAIN, 11);
		// 텍스트필드 생성
		TextField actf = new TextField(20);
		TextField idtf = new TextField(20);
		TextField pwtf = new TextField(15);
		TextField pwchecktf = new TextField(15);
		// 버튼 성생
		Button checkID = new Button("중복체크");
		Button checkID2 = new Button("ID수정");
		Button sign = new Button("관리자 등록");
		Button goMain = new Button("뒤로가기");

		// 메인 프레임 설정
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setBackground(Color.DARK_GRAY);

		// 비밀번호 별표처리
		pwtf.setEchoChar('*');
		pwchecktf.setEchoChar('*');

		// X키 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// 프레임, 라벨, 텍스트필드, 버튼 배치
		mainFrame.setBounds(480, 200, 450, 550);
		mainLabel.setBounds(145, 100, 200, 90);
		account.setBounds(57, 202, 90, 50);
		idl.setBounds(45, 272, 100, 20);
		idml.setBounds(115, 298, 180, 20);
		pwl.setBounds(45, 320, 100, 50);
		pwcheck.setBounds(40, 372, 110, 40);
		actf.setBounds(150, 215, 150, 25);
		idtf.setBounds(150, 270, 150, 25);
		pwtf.setBounds(150, 335, 150, 25);
		pwchecktf.setBounds(150, 380, 150, 25);
		checkID.setBounds(310, 269, 70, 25);
		checkID2.setBounds(310, 269, 70, 25);
		sign.setBounds(155, 445, 140, 45);
		goMain.setBounds(19, 42, 60, 25);

		// 폰트 및 색상 설정
		mainLabel.setFont(mainFont);
		idl.setFont(subFont);
		idml.setFont(smlFont);
		pwl.setFont(subFont);
		pwcheck.setFont(subFont);
		account.setFont(subFont);
		idtf.setFont(subFont);
		pwtf.setFont(subFont);
		actf.setFont(subFont);
		pwchecktf.setFont(subFont);
		idml.setForeground(Color.WHITE);
		mainLabel.setForeground(Color.WHITE);
		idl.setForeground(Color.WHITE);
		pwl.setForeground(Color.WHITE);
		pwcheck.setForeground(Color.WHITE);
		account.setForeground(Color.WHITE);

		// 프레임에 등록
		mainFrame.add(mainLabel);
		mainFrame.add(idl);
		mainFrame.add(pwl);
		mainFrame.add(idtf);
		mainFrame.add(pwtf);
		mainFrame.add(sign);
		mainFrame.add(pwcheck);
		mainFrame.add(pwchecktf);
		mainFrame.add(account);
		mainFrame.add(checkID);
		mainFrame.add(checkID2);
		mainFrame.add(actf);
		mainFrame.add(idml);
		mainFrame.add(goMain);

		actf.setText("Admin");
		actf.setEnabled(false);
		// 중복체크 버튼 설정(true값 입력시 수정활성화)
		checkID2.setVisible(check);

		// 버튼 동작 설정
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				String id = idtf.getText(); // 아이디
				String pwd = pwtf.getText(); // 비밀번호
				String pwd2 = pwchecktf.getText(); // 비밀번호 확인

				switch (e.getActionCommand()) {

				case "중복체크":

					if (!id.equals(idtf.getText().replaceAll(" ", "")) || id.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "아이디에 공백이 포함되면 안됩니다.");
					}
					// res에 1이 반환되면 DB에 데이터 없다는 뜻
					int res = AdminDAO.getInstance().checkList(id);
					if (res == 1) {
						JOptionPane.showMessageDialog(mainFrame, "사용 가능한 아이디 입니다.");
						// 사용이 가능하면 텍스트필드를 잠그고, 버튼을 중복체크 -> ID수정으로 바꿔준다.
						check = true;
						idtf.setEnabled(!check);
						checkID.setVisible(!check);
						checkID2.setVisible(check);
					} else {
						JOptionPane.showMessageDialog(mainFrame, "이미 존재하는 아이디 입니다.");
					}
					break;

				case "ID수정":
					check = false;
					idtf.setEnabled(!check);
					checkID.setVisible(!check);
					checkID2.setVisible(check);
					break;

				case "관리자 등록":

					if (!pwd.equals(pwtf.getText().replaceAll(" ", ""))) {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호에 공백이 포함되면 안됩니다.");
					}

					if (!pwd.equals(pwd2) || pwd.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "비밀번호가 다릅니다.");
						break;
					}

					if (!check) {
						JOptionPane.showMessageDialog(mainFrame, "아이디 중복체크를 해주세요.");
					} else {
						
						AdminVO vo = new AdminVO();
						vo.setId(id);
						vo.setPwd(pwd);

						res = AdminDAO.getInstance().adminWrite(vo);
						if (res > 0) {
							JOptionPane.showMessageDialog(mainFrame, "정상적으로 등록되었습니다.");
							mainFrame.dispose();
							new AddAdminPage(adVO);
						} else {

						}

					}

					break;

				case "뒤로가기":
					mainFrame.dispose();
					new AdminPage(adVO);
					break;

				}
			}
		};

		// 버튼 액션 등록
		checkID.addActionListener(al);
		checkID2.addActionListener(al);
		sign.addActionListener(al);
		goMain.addActionListener(al);

	}
}
