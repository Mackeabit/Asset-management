package user;

import dao.UserDAO;

public class AssetMain {
	public static void main(String[] args) {
		
		//로그인 페이지 객체 생성
		new LoginPage();
		
		//실행 시 DAO를 통해 유저, 관리자 폴더 생성
		UserDAO.getInstance().adminWrite();
		
		
	}
}
