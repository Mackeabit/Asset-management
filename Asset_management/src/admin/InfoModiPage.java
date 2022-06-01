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

public class InfoModiPage {
	
	public InfoModiPage(AdminVO adVO) {
		
		List<UserVO> list = UserDAO.getInstance().selectList();
		
		//프레임 생성
		Frame mainFrame = new Frame("고객정보 수정");
		//라벨 생성
		Label mainLabel = new Label("Manage User Profiles");
		Label listLabel = new Label("고객 리스트");
		Label idLabel = new Label("ID");
		//버튼 생성
		Button searchButt = new Button("조회");
		Button preButt = new Button("이전페이지");
		//폰트 생성
		Font mainFont = new Font("", Font.BOLD, 30);
		Font subFont = new Font("", Font.BOLD, 20);
		Font sub2Font = new Font("", Font.PLAIN, 15);
		Font sub3Font = new Font("", Font.PLAIN, 20);
		//텍스트필드 생성
		TextField tf_ID = new TextField();
		//텍스트에어리어 생성
		TextArea infoTF = new TextArea("",list.size(),0,TextArea.SCROLLBARS_VERTICAL_ONLY);
		
		for(int i = 0; i<list.size(); i++) {
			UserVO vo = list.get(i);
			infoTF.append(vo.getId()+"\n");
		}
		
		//텍스트에어리어 세팅
		infoTF.setEditable(false);
		
		//메인프레임 세팅
		mainFrame.setVisible(true);
		mainFrame.setResizable(false);
		mainFrame.setLayout(null);
		
		//폰트 설정
		mainLabel.setFont(mainFont);
		listLabel.setFont(sub2Font);
		idLabel.setFont(subFont);
		tf_ID.setFont(sub3Font);
		
		//색상 설정
		mainFrame.setBackground(Color.DARK_GRAY);
		mainLabel.setForeground(Color.WHITE);
		listLabel.setForeground(Color.WHITE);
		idLabel.setForeground(Color.WHITE);
		
		//위치 지정
		mainFrame.setBounds(480, 200, 450, 550);
		mainLabel.setBounds(85, 90, 290, 40);
		listLabel.setBounds(170, 240, 80, 30);
		idLabel.setBounds(110, 180, 30, 30);//ID
		tf_ID.setBounds(140, 180, 150, 30);//검색창
		searchButt.setBounds(300, 180, 40, 30);//조회버튼
		infoTF.setBounds(35, 270, 380, 250);//textArea
		preButt.setBounds(20, 50, 60, 30);//main
		
		//메인프레임에 등록
		mainFrame.add(mainLabel);
		mainFrame.add(listLabel);
		mainFrame.add(idLabel);
		mainFrame.add(tf_ID);
		mainFrame.add(searchButt);
		mainFrame.add(infoTF);
		mainFrame.add(preButt);
		
		//X키 이전화면으로 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				mainFrame.dispose();
				new AdminPage(adVO);
			}
		});
		
		//버튼 등록
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"조회":
					String id = tf_ID.getText();
					UserVO vo = UserDAO.getInstance().selectOne(id);
					
					if(id.equals("")) {
						JOptionPane.showMessageDialog(mainFrame, "수정할 ID 입력하세요");
						break;
					}
					
					if(vo == null) {
						JOptionPane.showMessageDialog(mainFrame, "유저 정보가 존재하지 않습니다.");
					}else {
						mainFrame.dispose();
						new InfoUpdate(vo,adVO);
					}
					break;
					
				case"이전페이지":
					mainFrame.dispose();
					new AdminPage(adVO);
					break;
					
				}//switch
				
			}
		};
		
		searchButt.addActionListener(al);
		preButt.addActionListener(al);
		
	}
}
