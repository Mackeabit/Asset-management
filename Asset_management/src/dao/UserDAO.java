package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;

public class UserDAO {

	static UserDAO single = null;

	public static UserDAO getInstance() {
		// 생성되지 않았으면 생성
		if (single == null)
			single = new UserDAO();
		// 생성된 객체정보를 반환
		return single;
	}// getInstance();

	public UserVO selectOne(String find) {

		// 저장되어있는 전체 정보 조회
		List<UserVO> list = UserDAO.getInstance().selectList();

		int i = 0;

		UserVO vo = null;

		if (find.matches("[0-9]{9}$")) {
			// 계좌번호 중복 체크
			for (i = 0; i < list.size(); i++) {
				vo = list.get(i);
				// 계좌번호가 존재하는 구간의 i값 얻기
				if (vo.getAccountNumber().equals(find))
					break;
				
			}

		} else {
			// 아이디 중복 체크
			for (i = 0; i < list.size(); i++) {
				vo = list.get(i);
				// 아이디가 존재하는 구간의 i값 얻기
				if (vo.getId().equals(find))
					break;
				
			}
		}
		vo = list.get(i);

		return vo;
	}

	public List<UserVO> selectList() {

		List<UserVO> list = new ArrayList<UserVO>();

		// 경로 설정
		String path = "C:/java_db_test/user/";

		// 경로내의 파일리스트를 배열로 저장
		// 각 폴더명을 ID로 만들 예정, 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File f = new File(path);
		String[] data = f.list();

		// ID폴더마다 들어있는 txt이름(개인DB를 모두 각 data.txt에 저장할 예정)
		// 저장 형식은 idx,name,id,pwd,accountNumber으로 구분자를 ','로 설정할 것.
		String sql = "/data.txt";

		// vo에 모든 회원정보 저장
		try {

			// id갯수만큼 for문 실행
			for (int i = 0; i < data.length; i++) {

				// 경로는 기본경로 + 아이디 + data.txt
				BufferedReader br = new BufferedReader(
						new InputStreamReader(new FileInputStream(path + data[i] + sql), "utf-8"));
				// line은 읽어오는 용도, content는 line을 저장할 용도
				String line = "";
				String content = "";

				while ((line = br.readLine()) != null) {
					content += line;
				}

				// '/'를 구분자로 저장하기에 각 구분자에 맞게 나눠주기
				String[] rs = content.split("/");

				// 나눠진 문자열을 vo에 순서대로 저장
				// list에 세팅된 vo를 저장
				UserVO vo = new UserVO();
				vo.setId(rs[0]);
				vo.setPwd(rs[1]);
				vo.setAccountNumber(rs[2]);
				vo.setMoney(Integer.parseInt(rs[3].replaceAll(",", "")));
				list.add(vo);
				br.close();

			} // for

		} catch (Exception e) {

			e.printStackTrace();
		}

		// 저장이 모두 끝난 list반환
		return list;
	}

	public int checkList(String find) {

		List<UserVO> list = UserDAO.getInstance().selectList();

		// 중복값 없을 시 반환해줄 res값 설정
		int res = 1;

		// 계좌번호는 편의상 9자리 고정이라 하고 진행
		// 계좌번호가 넘어왔다면 if문 안으로 진행
		if (find.matches("[0-9]{9}$")) {
			// 계좌번호 중복 체크
			for (int i = 0; i < list.size(); i++) {
				UserVO vo = list.get(i);
				// 계좌번호 중복이라면 res=0 반환
				if (vo.getAccountNumber().equals(find)) {
					res = 0;
					break;
				}

			}

		} else {
			// 아이디 중복 체크
			for (int i = 0; i < list.size(); i++) {
				UserVO vo = list.get(i);
				// 중복되는 아이디가 존재한다면 res=0 반환
				if (vo.getId().equals(find)) {
					res = 0;
					break;
				}

			}
		}

		// res=0 --> 중복값 있음, 사용불가
		// res=1 --> 중복값 없음, 사용가능
		return res;
	}// checkLIst

	public void adminWrite() {

		// 만들 폴더의 경로 설정(Admin,User)
		String adminPath = "C:/java_db_test/Admin/";
		String userPath = "C:/java_db_test/User/";

		// 두가지를 동시에 만들 예정이라 배열로 저장
		String[] path = { adminPath, userPath };

		// for문을 통해 폴더가 없을 경우 생성(초기 실행시 동작)
		for (int i = 0; i < path.length; i++) {
			File f = new File(path[i]);
			if (!f.exists()) {
				f.mkdirs();
			}
		}

		// BufferedWriter을 통해 Admin.txt와 내용 작성
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path[0] + "Admin.txt"), "utf-8"));

			bw.write("Admin/Admin/Admin");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// User,Admin 폴더 생성 및 Admin.txt파일 생성

	public int userWrite(UserVO vo) {

		int res = 0;

		// DB생성 코드 작성
		String userPath = "C:/java_db_test/User/" + vo.getId();

		File f = new File(userPath);

		if (!f.exists()) {
			f.mkdirs();
		}

		BufferedWriter bw;

		try {

			// 회원가입 시 보너스 지급(10,000원)
			int money = 10000;

			String[] content = { vo.getId(), vo.getPwd(), vo.getAccountNumber(), String.format("%,d", money) };

			for (int i = 0; i < content.length; i++) {
				bw = new BufferedWriter(new FileWriter(userPath + "/data.txt", true));
				if (i == 3) {
					bw.write(content[i]);
					bw.close();
					res = 1;
					break;
				}

				bw.write(content[i] + "/");
				bw.close();
			}
			
			//거래내역 txt 생성
			bw = new BufferedWriter(new FileWriter(userPath + "/Acc.txt"));
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;

	}// User db저장

	public int del(String id) {

		int res = 0;

		// 경로 설정
		String path = "C:/java_db_test/user/" + id;

		// 경로내의 파일리스트를 배열로 저장
		// 각 폴더명을 ID로 만들 예정, 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File file = new File(path);
		File[] fileList = file.listFiles();

		for (int i = 0; i < fileList.length; i++) {
			fileList[i].delete();
		}
		file.delete();

		if (!file.exists()) {

			res = 1;
		}

		return res;
	}

	
	public int transWrite(UserVO vo, UserVO vo1, int sendMoney, int nowMoney) {
		
		//1.보내는 사람 잔액을 바꿔주기
		//2.보내는 사람 거래내역 생성하여, 받는사람 : 계좌번호, 보낸금액 : 보낸금액, 잔액 : 잔액 형식으로 작성하기
		//3.받는 사람 잔액 바꿔주기
		//4.받는 사람 거래내역 생성하여, 보낸사람 : 계좌번호, 받은금액 : 받은금액,  잔액 : 잔액 형식으로 작성하기
		
		//vo1 = 보내는사람 정보
		//vo = 받는사람 정보
		//sendMoney = 보내는 돈
		//nowMoney = 보낸사람 계좌에 남은 돈
		//resMoney = 받는사람 계좌에 남은 돈
		
		int res = 0;
		int resMoney = vo.getMoney()+sendMoney;
		
		String path = "C:/java_db_test/user/";
		String[][] content = {{ vo1.getId(), vo1.getPwd(), vo1.getAccountNumber(), String.format("%,d", nowMoney)},
								{ vo.getId(), vo.getPwd(), vo.getAccountNumber(), String.format("%,d", resMoney) } };
//		String[] contentVO = { vo.getId(), vo.getPwd(), vo.getAccountNumber(), String.format("%,d", resMoney) };
		
		String[][] accArr = {{vo1.getId(),vo1.getAccountNumber(),String.format("%,d", sendMoney),String.format("%,d", nowMoney)},
							{vo.getId(),vo.getAccountNumber(),String.format("%,d", sendMoney),String.format("%,d", resMoney)}};
		

		BufferedWriter bw;

		try {

			for (int i = 0; i < content.length; i++) {
				
				for (int j = 0; j < content[i].length; j++) {
					if(j == 0) {
						bw = new BufferedWriter(new FileWriter(path + content[i][0] + "/data.txt"));
					}else {
						bw = new BufferedWriter(new FileWriter(path + content[i][0] + "/data.txt",true));
					}
					if (j == 3) {
						bw.write(content[i][j]);
						bw.close();
					}
					
					bw.write(content[i][j] + "/");
					bw.close();
				}
			}
			
			//거래내역 코드 작성해야함

		} catch (IOException e) {
			e.printStackTrace();
		}

		return res;

	}// User db저장
	
	
	
	
	
	
	
	
	
	
	
}
