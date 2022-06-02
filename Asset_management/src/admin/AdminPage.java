package admin;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dao.AdminDAO;
import vo.AdminVO;

public class AdminPage {
	
	public AdminPage(AdminVO adVO) {

		//dao 생성
		AdminDAO dao = AdminDAO.getInstance();
		
		// 프레임 생성
		Frame mainFrame = new Frame("관리자 모드");
		// Button 생성영역
		Button lookUpButt = new Button("조회");
		Button modiButt = new Button("수정");
		Button delButt = new Button("삭제");
		Button prevButt = new Button("이전페이지");
		Button allDelButt = new Button("BOOM!");
		// Label 생성영역
		Label lb_ad = new Label("Administrator");
		Label lb_ci = new Label("회원관리");
		//관리자 권한에 따른 라벨 변경(최고 관리자만 총자산 나오게 만듬)
		Label lb_allMoney;
		Label lb_money;
		if(adVO.getAuth() == 0) {
			lb_allMoney = new Label("총 자산");
			lb_money = new Label(dao.totalMoney()+"원");
		}else {
			lb_allMoney = new Label(adVO.getId()+"계정");
			lb_money = new Label("");
		}
		// Font 생성영역
		Font font_lb_ad = new Font("", Font.BOLD, 30);
		Font font_lb_su = new Font("", Font.BOLD, 15);
		Font font_Money = new Font("",Font.PLAIN,20);
		Font font_M = new Font("",Font.BOLD,40);
		ImageIcon icon = new  ImageIcon("src/images2/boom.jpg");
		JLabel bm = new JLabel(icon);
		
		//메인프레인 세팅
		mainFrame.setVisible(true);
		mainFrame.setLayout(null);
		mainFrame.setResizable(false);

		//X키 활성화
		mainFrame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		// Font지정 영역
		lb_ad.setFont(font_lb_ad);
		lb_ci.setFont(font_Money);
		allDelButt.setFont(font_lb_su);
		lb_allMoney.setFont(font_Money);
		lb_money.setFont(font_M);
		//색상 지정
		lb_ad.setForeground(Color.WHITE);
		lb_ci.setForeground(Color.WHITE);
		lb_allMoney.setForeground(Color.WHITE);
		lb_money.setForeground(Color.WHITE);
		mainFrame.setBackground(Color.DARK_GRAY);
		allDelButt.setBackground(Color.red);
		allDelButt.setForeground(Color.WHITE);
		
		// setBounds지정 영역
		mainFrame.setBounds(480, 200, 450, 550);
		lb_ad.setBounds(135, 50, 230, 70);//main
		lb_allMoney.setBounds(190,280,200,70);//총자산
		lb_money.setBounds(150,335,300,40);// 432432원
		lb_ci.setBounds(185, 150, 100, 40);//회원관리
		lookUpButt.setBounds(140, 200, 50, 30);//조회
		modiButt.setBounds(200, 200, 50, 30);//수정
		delButt.setBounds(260, 200, 50, 30);//삭제
		allDelButt.setBounds(370,500,60,30);//파괴
		bm.setBounds(315,490,50,50);//파괴아이콘
		
		// 프레임 추가 영역
		mainFrame.add(lb_money);
		mainFrame.add(lb_allMoney);
		mainFrame.add(lb_ad);
		mainFrame.add(lb_ci);
		mainFrame.add(lookUpButt);
		mainFrame.add(modiButt);
		mainFrame.add(delButt);
		mainFrame.add(allDelButt);
		mainFrame.add(bm);
		
		//버튼 동작 설정
		ActionListener al = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(e.getActionCommand()) {
				
				case"조회":
					mainFrame.dispose();
					new InfoLookUp(adVO);
					break;
					
				
				case"수정" :
					mainFrame.dispose();
					new InfoModiPage(adVO);
					break;
				
				case"삭제":
					mainFrame.dispose();
					new DelPage(adVO);
					break;
					
				case"이전페이지":
					mainFrame.dispose();
					new AdLoginPage();
					break;
					
				case"BOOM!":
					mainFrame.dispose();
					break;
					
				}//switch
				
			}
		};
		
		
		//버튼 활성화
		lookUpButt.addActionListener(al);
		modiButt.addActionListener(al);
		delButt.addActionListener(al);
		prevButt.addActionListener(al);
		allDelButt.addActionListener(al);
		
	}
}
