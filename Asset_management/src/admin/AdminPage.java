package admin;

import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Label;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import dao.AdminDAO;
import vo.AdminVO;

public class AdminPage {
	public AdminPage(AdminVO vo) {
		
		//dao 생성
		AdminDAO dao = AdminDAO.getInstance();
		
		// 프레임 생성
		Frame mainFrame = new Frame("관리자 모드");
		// Button 생성영역
		Button btn_cis = new Button("조회");
		Button btn_cic = new Button("수정");
		Button btn_cid = new Button("삭제");
		Button btn_before = new Button("이전페이지");
		Button btn_reset = new Button("BOOM!");
		// Lable 생성영역
		Label lb_ad = new Label("Administrator");
		Label lb_ci = new Label("회원관리");
		Label lb_allMoney = new Label("총 자산");
		Label lb_money = new Label(dao.totalMoney()+"원");
		// Font 생성영역
		Font font_lb_ad = new Font("", Font.BOLD, 30);
		Font font_lb_su = new Font("", Font.BOLD, 15);
		Font font_Money = new Font("",Font.PLAIN,20);
		Font font_M = new Font("",Font.BOLD,40);
		ImageIcon icon = new  ImageIcon("src/images2/boom.jpg");
		JLabel bm = new JLabel(icon);
		
		mainFrame.setResizable(false);
		// 레이아웃 자동배치 OFF
		mainFrame.setLayout(null);
		// 프레임 사이즈 조절 불가
		mainFrame.setResizable(false);

		// Font지정 영역
		lb_ad.setFont(font_lb_ad);
		lb_ci.setFont(font_Money);
		btn_reset.setFont(font_lb_su);
		lb_allMoney.setFont(font_Money);
		lb_money.setFont(font_M);
		
		lb_ad.setForeground(Color.WHITE);
		lb_ci.setForeground(Color.WHITE);
		lb_allMoney.setForeground(Color.WHITE);
		lb_money.setForeground(Color.WHITE);
		mainFrame.setBackground(Color.DARK_GRAY);
		btn_reset.setBackground(Color.red);
		btn_reset.setForeground(Color.WHITE);
		
		// setBounds지정 영역
		mainFrame.setBounds(480, 200, 450, 550);
		lb_ad.setBounds(135, 50, 230, 70);//main
		lb_allMoney.setBounds(190,280,200,70);//총자산
		lb_money.setBounds(150,335,300,40);// 432432원
		lb_ci.setBounds(185, 150, 100, 40);//회원관리
		btn_cis.setBounds(140, 200, 50, 30);//조회
		btn_cic.setBounds(200, 200, 50, 30);//수정
		btn_cid.setBounds(260, 200, 50, 30);//삭제
		btn_reset.setBounds(370,500,60,30);//파괴
		bm.setBounds(315,490,50,50);//파괴아이콘
		
		// 프레임 추가 영역
		mainFrame.add(lb_money);
		mainFrame.add(lb_allMoney);
		mainFrame.add(lb_ad);
		mainFrame.add(lb_ci);
		mainFrame.add(btn_cis);
		mainFrame.add(btn_cic);
		mainFrame.add(btn_cid);
		mainFrame.add(btn_reset);
		mainFrame.add(bm);
		
	}
}
