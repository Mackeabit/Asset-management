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

import javax.swing.JOptionPane;

import dao.UserDAO;
import vo.UserVO;

public class TransferPage {
	
	//계좌조회 버튼 눌렀는지 검사할 예정
	boolean checkA = false;
	
	public TransferPage(UserVO vo) {
		
		//프레임 생성
		Frame mainFrame = new Frame("계좌이체");
		//라벨 생성
		Label mainLabel = new Label("계좌이체");
		Label accountN = new Label("계좌번호");
		Label transMoney = new Label("이체금액");
		Label accountM = new Label("이체가능 금액  " + String.format("%,d", vo.getMoney())+"원", Label.RIGHT);
		//폰트 설정
		Font mainFont = new Font("맑은 고딕", Font.BOLD, 30);
		Font subFont = new Font("", Font.PLAIN, 18);
		Font smallFont = new Font("", Font.PLAIN, 12);
		//텍스트필드 생성
		TextField acNum = new TextField(10);
		TextField trNum = new TextField(10);
		//버튼 생성
		Button check = new Button("조회");
		Button transbutton = new Button("이체하기");
		Button back = new Button("취소하기");
		
		//프레임 설정
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setBackground(Color.WHITE);
		
		//X버튼 활성
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		//폰트 세팅
		mainLabel.setFont(mainFont);
		accountN.setFont(subFont);
		transMoney.setFont(subFont);
		accountM.setFont(smallFont);
		mainLabel.setForeground(Color.DARK_GRAY);
		accountN.setForeground(Color.DARK_GRAY);
		transMoney.setForeground(Color.DARK_GRAY);
		
		//위치 설정
		mainFrame.setBounds(480,200, 450, 550);
		mainLabel.setBounds(170, 140, 250, 60);// 이체
		accountN.setBounds(60, 205, 80, 50);// 계좌번호
		transMoney.setBounds(60, 255, 80, 50);// 이체금액
		accountM.setBounds(140, 295, 140, 30);// 이체금액
		acNum.setBounds(150, 220, 170, 25);// accountField
		trNum.setBounds(150, 268, 170, 25);// transField
		check.setBounds(330, 219, 55, 25);// 조회버튼
		back.setBounds(370, 40, 60, 25);// 취소하기버튼
		transbutton.setBounds(180, 350, 100, 50);// 이체하기버튼
		
		//프레임에 등록
		mainFrame.add(accountN);
		mainFrame.add(mainLabel);
		mainFrame.add(transMoney);
		mainFrame.add(accountM);
		mainFrame.add(transbutton);
		mainFrame.add(check);
		mainFrame.add(back);
		mainFrame.add(acNum);
		mainFrame.add(trNum);
		
		
		
		//버튼 동작 설정
		ActionListener al = new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				//내 통장잔고를 먼저 저장
				UserVO vo1 = vo;
				int myMoney = vo.getMoney();
				String account = acNum.getText().trim(); //받는사람 계좌
				String sendMoney = trNum.getText().trim(); //보내는 돈
				
				//vo값 초기화 계좌번호에 맞는 정보가 없으면 null값 저장
				UserVO vo = UserDAO.getInstance().selectOne(account);

				switch (e.getActionCommand()) {

				case "조회":
					
					
					if(!account.matches("[0-9]*")) {
						JOptionPane.showMessageDialog(mainFrame, "숫자만 입력 가능합니다.");
						break;
					}

					if(vo == null) {
						JOptionPane.showMessageDialog(mainFrame, "존재하지 않는 계좌입니다.");
						break;
					}
					
					
					if(account.equals(vo.getAccountNumber())) {
						JOptionPane.showMessageDialog(mainFrame, vo.getId() + "님의 계좌입니다.");
						//계좌조회가 성공했으면 true값으로 변경
						checkA = true;
					}
					
					break;


				case "취소하기":
					mainFrame.dispose();
					new UserPage(vo);
					break;

				case "이체하기":
					
					if(!checkA) {
						JOptionPane.showMessageDialog(mainFrame, "계좌 조회를 눌러주세요.");
						break;
					}
					
					if (account.equals("") || sendMoney.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "계좌번호와 금액 모두 입력하셔야 합니다.");
					} else if (!sendMoney.matches("[0-9]*")) {
						JOptionPane.showMessageDialog(mainFrame, "금액은 숫자만 입력 가능합니다.");
					} else if (account.equals(vo.getAccountNumber())) {
						
						//보내는 돈 int로 형변환
						int payMoney = Integer.parseInt(sendMoney);
						//계좌에 남은돈
						int money = myMoney - payMoney;

						if (payMoney > myMoney) {

							JOptionPane.showMessageDialog(mainFrame, "송금에 필요한 금액이 없습니다.");

						} else {

							JOptionPane.showMessageDialog(mainFrame, vo.getId() + "님의 계좌로 송금이 진행됩니다.");
							UserDAO.getInstance().transWrite(vo, vo1, payMoney, money);
							new UserPage(vo1);
							mainFrame.dispose();
							

						}
					}
					
					break;

				}
			}
		};

		transbutton.addActionListener(al);
		check.addActionListener(al);
		back.addActionListener(al);
		
	}//생성자
}
