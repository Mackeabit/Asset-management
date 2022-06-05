package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vo.AdminVO;
import vo.UserVO;

public class AdminDAO {
	
	//'Admin'계정만 회사전체 자산 보이게하기
	//나머지 admin계정은 전체자산 null값으로 나오게 처리
	
	String path = UserDAO.getInstance().getPath();
	String admin_path = UserDAO.getInstance().getAdmin_path();
	
	//DAO를 사용할 때 매번 new를 사용하기엔 메모리 낭비인듯 싶어 싱글톤 형태로 제작
	static AdminDAO single = null;

	public static AdminDAO getInstance() {
		//생성되지 않았으면 생성
		if (single == null)
			single = new AdminDAO();
		//생성된 객체정보를 반환
		return single;
	}
	
	//User폴더에 저장된 모든 회원의 잔액을 합치는 메서드
	public String totalMoney() {
		
		int total=0;
		List<UserVO> list = UserDAO.getInstance().selectList();
		
		for(int i = 0; i<list.size(); i++) {
			UserVO vo = list.get(i);
			total += vo.getMoney();
		}
		
		return String.format("%,d", total);
	}
	
	
	public AdminVO selectOne(String find) {
		
		//현재 DB에 저장되어있는 관리자 정보를 vo로 묶어 list에 저장
		List<AdminVO> list = AdminDAO.getInstance().selectList();

		//조회가 정상진행되었는지 확인하기 위한 null값 생성
		AdminVO vo = null;
		
		for (int i = 0; i < list.size(); i++) {
			//전체 계정수만큼 for문을 돌리며 vo에 관리자정보 넣기
			vo = list.get(i);
			
			//vo에 들어간 관리자정보의 아이디와 파라미터로 보내진 아이디가 동일다면 break;
			//아니라면 null값으로 초기화 반복
			if (vo.getId().equals(find)) {
				break;
			}else {
				vo = null;
			}
		}
		
		return vo;
	}//selectOne
	
	
	//Admin계정 조회하는 메서드
	public List<AdminVO> selectList() {

		List<AdminVO> list = new ArrayList<AdminVO>();

		// 경로내의 파일리스트를 배열로 저장
		// 각 폴더명을 ID로 만들 예정, 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File f = new File(admin_path);
		String[] id = f.list();

		// vo에 모든 회원정보 저장
		try {

			// id갯수만큼 for문 실행
			for (int i = 0; i < id.length; i++) {

				// 경로는 기본경로 + 아이디 + data.txt
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(admin_path + id[i]), "utf-8"));
				// line은 읽어오는 용도, content는 line을 저장할 용도
				String line = "";
				String content = "";

				while ((line = br.readLine()) != null) {
					content += line;
				}

				// '/'구분자에 맞게 나눠주기
				String[] rs = content.split("/");

				// 나눠진 문자열을 vo에 순서대로 저장
				// list에 세팅된 vo를 저장
				AdminVO vo = new AdminVO();
				vo.setId(rs[0]);
				vo.setPwd(rs[1]);
				//,를 ""로 바꾸고 int값으로 vo.Money에 저장 
				vo.setMoney(Integer.parseInt(rs[2].replaceAll(",", "")));
				vo.setAuth(Integer.parseInt(rs[3]));
				list.add(vo);
				br.close();

			} // for

		} catch (Exception e) {

			e.printStackTrace();
		}

		// 저장이 모두 끝난 list반환
		return list;
	}//selectList
	
	
	public int checkList(String id) {
		
		List<AdminVO> list = AdminDAO.getInstance().selectList();

		// 중복값 없을 시 반환해줄 res값 설정
		int res = 1;
		
		for (int i = 0; i < list.size(); i++) {
			AdminVO vo = list.get(i);
			
			if(vo.getId().equals(id)) {
				res = 0;
				break;
			}
			
		}

		// res=0 --> 중복값 있음, 사용불가
		// res=1 --> 중복값 없음, 사용가능
		return res;
	}// checkLIst
	
	public int adminWrite(AdminVO vo) {
		
		//정상으로 작동했는지 확인하기 위한 코드
		int res = 0;

		// DB생성 코드 작성

		File f = new File(admin_path);

		if (!f.exists()) {
			f.mkdirs();
		}

		BufferedWriter bw;

		try {

			//작성할 내용(id,pwd,account,money)을 배열로 저장
			String[] content = { vo.getId(), vo.getPwd(), "0", "1"};

			for (int i = 0; i < content.length; i++) {
				//true값을 줘서 배열안의 값이 모두 다 저장될 수 있게 만듬
				bw = new BufferedWriter(new FileWriter(admin_path + vo.getId()+".txt", true));
				if (i == 3) {
					bw.write(content[i]);
					bw.close();
					res = 1;
					break;
				}

				bw.write(content[i] + "/");
				bw.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//모두 정상적으로 완료되었다면 res=1
		return res;

	}// Admin db저장
	
	
}
