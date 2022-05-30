package admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import dao.UserDAO;
import vo.AdminVO;
import vo.UserVO;

public class DetailsInfo {
	public DetailsInfo(UserVO vo, AdminVO adVO) {
		
		//프레임 생성
		Frame mainFrame = new Frame("상세 조회");
		//Label 생성
		Label mainLabel = new Label("User Profile");
		Label id = new Label("아이디: " + vo.getId());
		Label pw = new Label("비밀번호: " + vo.getPwd());
		Label money = new Label("잔액: " + String.format("%,d", vo.getMoney()));
		Label account = new Label("계좌번호: " + vo.getAccountNumber());
		Label dealLabel = new Label("거 래 내 역");
		//텍스트에어리어 생성
		TextArea deal = new TextArea(UserDAO.getInstance().acc(vo));
		//폰트 생성
		Font Lfont = new Font("", Font.BOLD, 30);
		Font mfont = new Font("", Font.PLAIN, 15);
		Font sfont = new Font("", Font.PLAIN, 12);
		
		//메인프레임 세팅
		mainFrame.setBackground(Color.DARK_GRAY);
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);
		
		//거래내역 Label 세팅
		deal.setEditable(false);
		
		//위치 지정
		mainFrame.setBounds(480, 200, 450, 550);
		mainLabel.setBounds(150, 50, 200, 40);
		id.setBounds(50, 90, 200, 30);
		pw.setBounds(50, 120, 200, 30);
		money.setBounds(50, 150, 200, 30);
		account.setBounds(50, 180, 300, 30);
		deal.setBounds(35, 240, 380, 280);
		dealLabel.setBounds(200, 210, 100, 30);
		
		//폰트 설정
		id.setFont(mfont);
		mainLabel.setFont(Lfont);
		pw.setFont(mfont);
		money.setFont(mfont);
		account.setFont(mfont);
		deal.setFont(sfont);
		dealLabel.setFont(sfont);
		
		//색상 설정
		mainLabel.setForeground(Color.WHITE);
		id.setForeground(Color.WHITE);
		pw.setForeground(Color.WHITE);
		money.setForeground(Color.WHITE);
		account.setForeground(Color.WHITE);
		dealLabel.setForeground(Color.WHITE);
		
		//메인프레임에 등록
		mainFrame.add(dealLabel);
		mainFrame.add(mainLabel);
		mainFrame.add(id);
		mainFrame.add(money);
		mainFrame.add(account);
		mainFrame.add(deal);
		mainFrame.add(pw);

		//X키 설정 (이전페이지)
		mainFrame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				mainFrame.dispose();
				new InfoLookUp(adVO);
			}
		});

	}
}
