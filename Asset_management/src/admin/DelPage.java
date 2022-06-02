package admin;

import java.awt.Button;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JOptionPane;

import dao.UserDAO;
import vo.AdminVO;
import vo.UserVO;

public class DelPage {

	public DelPage(AdminVO adVO) {
		
		//프레임 생성
		Frame mainFrame = new Frame("고객정보삭제");
		//라벨 생성
		Label mainLabel = new Label("Delete Account");
		//폰트 생성
		Font mainFont = new Font("", Font.BOLD, 30);
		Font subfont = new Font("",Font.BOLD,20);
		//버튼 생성
		Button btnId = new Button("전체 아이디 조회");
		Button btnSel = new Button("아이디 조회");
		Button btnDel = new Button("삭제");
		Button btnAdm = new Button("돌아가기");
		//텍스트 필드, 텍스트에어리어 생성
		TextField tfSel = new TextField();
		TextArea ta = new TextArea("", 0, 0, TextArea.SCROLLBARS_BOTH);
		//초이스 생성
		Choice ch = new Choice(); 
		
		//메인프레임 세팅
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		mainFrame.setVisible(true);
		
		//위치 지정
		mainFrame.setBounds(480, 200, 450, 550);
		mainLabel.setBounds(120, 70, 220, 50); // 메인라벨
		btnSel.setBounds(280, 180, 80, 30); // 아이디 조회 버튼
		btnDel.setBounds(290, 460, 70, 26); // 삭제 버튼
		btnAdm.setBounds(20, 40, 70, 30); // 돌아가기
		btnId.setBounds(90,415,100,30);
		tfSel.setBounds(90, 180, 180, 30);//검색창
		ta.setBounds(90, 230, 270, 180);//TextArea
		ch.setBounds(90, 460, 180, 40); //선택창
		
		//폰트 설정
		ta.setFont(subfont);
		
		//색상 설정
		mainFrame.setBackground(Color.DARK_GRAY);
		mainLabel.setForeground(Color.WHITE);

		//프레임 추가
		mainLabel.setFont(mainFont);
		mainFrame.add(btnSel);
		mainFrame.add(btnDel);
		mainFrame.add(btnAdm);
		mainFrame.add(tfSel);
		mainFrame.add(ta);
		mainFrame.add(mainLabel);
		mainFrame.add(ch);
		mainFrame.add(btnId);
		
		//TextArea 잠금
		ta.setEditable(false);


		//X키 설정(되돌아가기)
		mainFrame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
				new AdminPage(adVO);
			};
		});
		
		
		//유저 목록 조회
		ta.setText("");
		List<UserVO> list = UserDAO.getInstance().selectList();
		
		if(list.size() != 0) {
			ch.add("선택");
			for(int i = 0; i<list.size(); i++) {
				UserVO vo = list.get(i);
				ch.add(vo.getId());
				ta.append(vo.getId()+"\n");
			}
		}else {
			JOptionPane.showMessageDialog(mainFrame, "사용자가 없습니다.");
		}
		
		
		btnAdm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				mainFrame.dispose();
				new AdminPage(adVO);
				
			}
		});
		
		
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"전체 아이디 조회":
					ta.setText("");
					List<UserVO> list = UserDAO.getInstance().selectList();
					
					if(list.size() != 0) {
						for(int i = 0; i<list.size(); i++) {
							UserVO vo = list.get(i);
							ta.append(vo.getId()+"\n");
						}
					}else {
						JOptionPane.showMessageDialog(mainFrame, "사용자가 없습니다.");
					}
					
					break;
					
				case"아이디 조회":
					
					String id = tfSel.getText().trim();
					
					int resId = UserDAO.getInstance().checkList(id);
					
					if(id.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "아이디를 입력해주세요.");
					}else if(resId != 0) {
						JOptionPane.showMessageDialog(mainFrame, "존재하지 않는 아이디 입니다.");
					}else {
						ta.setText("");
						UserVO vo = UserDAO.getInstance().selectOne(id);
						String accCon = UserDAO.getInstance().acc(vo);
						
						ta.append("아이디 : "+vo.getId()+"\n");
						ta.append("비밀번호 : "+vo.getPwd()+"\n");
						ta.append("계좌번호 : "+vo.getAccountNumber()+"\n");
						ta.append("통장잔액 : "+String.format("%,d", vo.getMoney())+"\n");
						ta.append("거래내역 : \n"+accCon);
					}
					
					
					break;
					
				case"삭제":
					
					id = ch.getSelectedItem();
					
					if(id.equals("선택")) {
						JOptionPane.showMessageDialog(mainFrame, "아이디를 선택해주세요");
					}else {
						int res = UserDAO.getInstance().del(id);
						
						if(res == 1) {
							ch.remove(id);
							JOptionPane.showMessageDialog(mainFrame, "삭제가 완료되었습니다.");
							mainFrame.dispose();
							new DelPage(adVO);
						}else {
							JOptionPane.showMessageDialog(mainFrame, "삭제 실패, 관리자에게 문의하세요.");
						}
					}
					break;
				}
				
			}
		};
		
		btnDel.addActionListener(al);
		btnSel.addActionListener(al);
		btnId.addActionListener(al);
		
	}
	
}
