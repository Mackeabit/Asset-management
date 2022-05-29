package admin;

import java.awt.Button;
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

public class InfoLookUp {
	public InfoLookUp(AdminVO adVO) {
		
		//프레임 생성
		Frame mainFrame = new Frame("고객 조회");
		//라벨 생성
		Label mainLabel = new Label("User List");
		Label cusLabel = new Label("고객리스트");
		//폰트 설정
		Font mainFont = new Font("", Font.BOLD, 30);
		Font subFont = new Font("", Font.PLAIN, 18);
		Font smFont = new Font("", Font.PLAIN, 12);
		Font tfFont = new Font("", Font.PLAIN, 17);
		//버튼 생성
		Button lookUpButt = new Button("상세조회");
		//텍스트 필드 생성
		TextField idSearch = new TextField();
		//텍스트 에어리어 설정
		TextArea infoArea = new TextArea("아이디          잔액\n");
		
		//메인프레임 세팅
		mainFrame.setLayout(null);
		mainFrame.setBounds(480, 200, 450, 550);
		mainFrame.setResizable(false);
		mainFrame.setVisible(true);
		
		//텍스트 에어리어 세팅
		infoArea.setEditable(false);

		//색상 지정
		mainFrame.setBackground(Color.DARK_GRAY);
		cusLabel.setForeground(Color.WHITE);
		mainLabel.setForeground(Color.WHITE);
		
		//Font 지정
		cusLabel.setFont(subFont);
		mainLabel.setFont(mainFont);
		idSearch.setFont(tfFont);
		lookUpButt.setFont(smFont);
		infoArea.setFont(tfFont);
		
		//setBounds 설정
		mainLabel.setBounds(160, 60, 250, 40);
		cusLabel.setBounds(165, 200, 110, 50);
		idSearch.setBounds(120, 145, 150, 30);
		lookUpButt.setBounds(280, 145, 60, 29);
		infoArea.setBounds(75, 245, 300, 220);
		
		lookUpButt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String getId = idSearch.getText().trim();
				UserVO vo = UserDAO.getInstance().selectOne(getId);
				
				if(vo == null) {
					JOptionPane.showMessageDialog(mainFrame, "존재하지 않는 아이디입니다.");
				}else {
					
				}
			}
		}); 
		
		//텍스트 에어리어에 불러올 코드
		List<UserVO> list = UserDAO.getInstance().selectList();
		for(int i = 0 ; i<list.size(); i++) {
			
			UserVO vo = list.get(i);
			StringBuffer id = new StringBuffer(vo.getId());
			StringBuffer money = new StringBuffer(String.format("%,d", vo.getMoney())+"원");
			while(true) {
				
				if(id.length()== 15)break;

				id.append(" ");
			}
			infoArea.append(id+" "+money+"\n");
		}
		
		//메인프레임에 등록
		mainFrame.add(infoArea);
		mainFrame.add(lookUpButt);
		mainFrame.add(cusLabel);
		mainFrame.add(idSearch);
		mainFrame.add(mainLabel);

		//X키 이전화면으로 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
				new AdminPage(adVO);
			}
		});
		
	}//생성자
}
