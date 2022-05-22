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
import java.util.List;

import javax.swing.JOptionPane;

import dao.UserDAO;
import vo.UserVO;

public class SignPage {
	
	boolean check1 = false;
	boolean check2 = false;

	
	public SignPage() {
		// 프레임 생성
		Frame mainFrame = new Frame("회원가입");
		// 라벨 생성
		Label mainLabel = new Label("Sign Up");
		Label account = new Label("계좌번호 :");
		Label acml = new Label("숫자만 입력 가능합니다.");
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
		Button checkAc = new Button("계좌조회");
		Button checkAc2 = new Button("계좌수정");
		Button checkID = new Button("중복체크");
		Button checkID2 = new Button("ID수정");
		Button sign = new Button("가입신청");
		Button goMain = new Button("메인으로");



		// 메인 프레임 설정
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		mainFrame.setBackground(Color.WHITE);

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
		acml.setBounds(148, 242, 290, 20);
		idl.setBounds(45, 272, 100, 20);
		idml.setBounds(115, 298, 180, 20);
		pwl.setBounds(45, 320, 100, 50);
		pwcheck.setBounds(40, 372, 110, 40);
		actf.setBounds(150, 215, 150, 25);
		idtf.setBounds(150, 270, 150, 25);
		pwtf.setBounds(150, 335, 150, 25);
		pwchecktf.setBounds(150, 380, 150, 25);
		checkAc.setBounds(310, 213, 70, 25);
		checkAc2.setBounds(310, 213, 70, 25);
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
		acml.setFont(smlFont);
		idtf.setFont(subFont);
		pwtf.setFont(subFont);
		actf.setFont(subFont);
		pwchecktf.setFont(subFont);
		mainLabel.setForeground(Color.DARK_GRAY);
		idl.setForeground(Color.DARK_GRAY);
		pwl.setForeground(Color.DARK_GRAY);
		pwcheck.setForeground(Color.DARK_GRAY);
		account.setForeground(Color.DARK_GRAY);

		// 프레임에 등록
		mainFrame.add(mainLabel);
		mainFrame.add(idl);
		mainFrame.add(pwl);
		mainFrame.add(idtf);
		mainFrame.add(pwtf);
		mainFrame.add(sign);
		mainFrame.add(pwcheck);
		mainFrame.add(pwchecktf);
		mainFrame.add(checkAc);
		mainFrame.add(checkAc2);
		mainFrame.add(account);
		mainFrame.add(checkID);
		mainFrame.add(checkID2);
		mainFrame.add(actf);
		mainFrame.add(goMain);

		// 중복체크 버튼 설정(true값 입력시 수정활성화)
		checkAc2.setVisible(check1);
		checkID2.setVisible(check2);
		
		// 버튼 동작 설정
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				String account = actf.getText(); // 계좌
				String id = idtf.getText(); // 아이디
				String pwd = pwtf.getText(); // 비밀번호
				String pwd2 = pwchecktf.getText(); // 비밀번호 확인
				
				
				//유효성체크
				if (!account.equals(actf.getText().replaceAll(" ", ""))||account.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "계좌번호에 공백이 포함되면 안됩니다.");
				} else if (!id.equals(idtf.getText().replaceAll(" ", ""))||id.equals("")) {
					JOptionPane.showMessageDialog(mainFrame, "아이디에 공백이 포함되면 안됩니다.");
				} else if (!pwd.equals(pwtf.getText().replaceAll(" ", ""))) {
					JOptionPane.showMessageDialog(mainFrame, "비밀번호에 공백이 포함되면 안됩니다.");
				} else {

					switch (e.getActionCommand()) {

					case "계좌조회":
						if(!account.matches("[0-9]{9}$")) {
							JOptionPane.showMessageDialog(mainFrame, "계좌번호는 9자리를 입력하셔야 합니다.");
							break;
						}
						//조회 및 중복체크를 위해 전체DB를 list에 저장
						List<UserVO> list = UserDAO.getInstance().selectList();
						//res에 1이 반환되면 DB에 데이터 없다는 뜻
						int res = UserDAO.getInstance().checkList(list, account);
						if(res==1) {
							JOptionPane.showMessageDialog(mainFrame, "사용 가능한 계좌번호 입니다.");
							//사용이 가능하면 텍스트필드를 잠그고, 버튼을 계좌조회 -> 계좌수정으로 바꿔준다.
							check1 = true;
							actf.setEnabled(!check1);
							checkAc.setVisible(!check1);
							checkAc2.setVisible(check1);
						}else {
							JOptionPane.showMessageDialog(mainFrame, "이미 존재하는 계좌번호 입니다.");
						}
						
						break;

					case "중복체크":
						//조회 및 중복체크를 위해 전체DB를 list에 저장
						list = UserDAO.getInstance().selectList();
						//res에 1이 반환되면 DB에 데이터 없다는 뜻
						res = UserDAO.getInstance().checkList(list, id);
						if(res == 1) {
							JOptionPane.showMessageDialog(mainFrame, "사용 가능한 아이디 입니다.");
							//사용이 가능하면 텍스트필드를 잠그고, 버튼을 중복체크 -> ID수정으로 바꿔준다.
							check2 = true;
							idtf.setEnabled(!check2);
							checkID.setVisible(!check2);
							checkID2.setVisible(check2);
						}else {
							JOptionPane.showMessageDialog(mainFrame, "이미 존재하는 아이디 입니다.");
						}
						break;

					case "계좌수정":
						check1 = false;
						actf.setEnabled(!check1);
						checkAc.setVisible(!check1);
						checkAc2.setVisible(check1);
						break;

					case "ID수정":
						check2 = false;
						idtf.setEnabled(!check2);
						checkID.setVisible(!check2);
						checkID2.setVisible(check2);
						break;

					case "가입신청":
						
						if(!pwd.equals(pwd2)||pwd.equals("")) {
							JOptionPane.showMessageDialog(mainFrame, "비밀번호가 다릅니다.");
							break;
						}
						
						if(!check1) {
							JOptionPane.showMessageDialog(mainFrame, "계좌조회를 해주세요.");
						}else if(!check2){
							JOptionPane.showMessageDialog(mainFrame, "아이디 중복체크를 해주세요.");
						}else {
							
							UserVO vo = new UserVO();
							vo.setId(id);
							vo.setPwd(pwd);
							vo.setAccountNumber(account);
							
							res = UserDAO.getInstance().userWrite(vo);
							
							if(res > 0) {
								mainFrame.dispose();
								new LoginPage();
								JOptionPane.showMessageDialog(mainFrame, "회원가입이 완료되었습니다.");
							}else {
								
							}
							
						}
						
						break;

					case "메인으로":

						mainFrame.dispose();
						new LoginPage();
						break;

					}
				}
			}
		};

		// 버튼 액션 등록
		checkAc.addActionListener(al);
		checkAc2.addActionListener(al);
		checkID.addActionListener(al);
		checkID2.addActionListener(al);
		sign.addActionListener(al);
		goMain.addActionListener(al);

	}// 생성자

}
