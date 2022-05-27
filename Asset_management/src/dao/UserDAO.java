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
	
	//경로설정을 전역으로 해두고 getter을 통해 사용할 수 있게 만듬
	private String path = "C:/java_db_test/User/";
	private String admin_path = "C:/java_db_test/Admin/";

	public String getPath() {
		return path;
	}

	public String getAdmin_path() {
		return admin_path;
	}
	
	//DAO를 사용할 때 매번 new를 사용하기엔 메모리 낭비인듯 싶어 싱글톤 형태로 제작
	static UserDAO single = null;

	public static UserDAO getInstance() {
		// 생성되지 않았으면 생성
		if (single == null)
			single = new UserDAO();
		// 생성된 객체정보를 반환
		return single;
	}// getInstance();
	
	//거래내역창에 로드할 Acc.txt를 읽어오는 메서드
	public String acc(UserVO vo) {
		
		//읽어올 변수 line, 저장할 변수 content
		String line = "";
		String content = "";

		BufferedReader br;

		try {
			//경로에 위치한 txt파일을 content에 저장
			br = new BufferedReader(
				new InputStreamReader(
				new FileInputStream(path + vo.getId() + "/Acc.txt"), "euc-kr"));

			while ((line = br.readLine()) != null) {

				content += line+"\n";

			}

		} catch (Exception e) {
		}
		
		//저장한 content return
		return content;
	}//acc

	//유저 한명의 정보 조회하기 위한 메서드
	public UserVO selectOne(String find) {

		//현재 DB에 저장되어있는 전체 정보를 vo로 묶어 list에 저장
		List<UserVO> list = UserDAO.getInstance().selectList();
		
		//조회가 정상진행되었는지 확인하기 위한 null값 생성
		UserVO vo = null;
		
		//계좌번호가 넘어왔는지 아이디가 넘어왔는지 체크
		if (find.matches("[0-9]{9}$")) {

			for (int i = 0; i < list.size(); i++) {
				
				//전체 유저수만큼 for문을 돌리며 vo에 유저정보 넣기
				vo = list.get(i);
				
				//vo에 들어간 유저정보의 계좌번호와 파라미터로 보내진 계좌번호가 동일다면 break;
				//아니라면 null값으로 초기화 반복
				if (vo.getAccountNumber().equals(find)) {
					break;
				}else {
					vo = null;
				}

			}

		} else {

			for (int i = 0; i < list.size(); i++) {
				//전체 유저수만큼 for문을 돌리며 vo에 유저정보 넣기
				vo = list.get(i);
				
				//vo에 들어간 유저정보의 아이디와 파라미터로 보내진 아이디가 동일다면 break;
				//아니라면 null값으로 초기화 반복
				if (vo.getId().equals(find)) {
					break;
				}else {
					vo = null;
				}

			}
		}
		
		//아이디or계좌번호가 존재했다면 vo에 정보저장되어있고
		//없다면 null값 반환
		return vo;
	}//selectOne

	public List<UserVO> selectList() {

		List<UserVO> list = new ArrayList<UserVO>();

		// 경로내의 파일리스트를 배열로 저장
		// 각 폴더명을 ID로 만들 예정, 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File f = new File(path);
		String[] data = f.list();

		// ID폴더마다 들어있는 txt이름(개인DB를 모두 각 data.txt에 저장할 예정)
		// 저장 형식은 id/pwd/accountNumber/money로 구분자를 '/'로 설정
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

				// '/'구분자에 맞게 나눠주기
				String[] rs = content.split("/");

				// 나눠진 문자열을 vo에 순서대로 저장
				// list에 세팅된 vo를 저장
				UserVO vo = new UserVO();
				vo.setId(rs[0]);
				vo.setPwd(rs[1]);
				vo.setAccountNumber(rs[2]);
				//현재 money는 10,000 으로 저장되어있기 때문에
				//,를 ""로 바꾸고 int값으로 vo.Money에 저장 
				vo.setMoney(Integer.parseInt(rs[3].replaceAll(",", "")));
				list.add(vo);
				br.close();

			} // for

		} catch (Exception e) {

			e.printStackTrace();
		}

		// 저장이 모두 끝난 list반환
		return list;
	}//selectList

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

		// 두가지를 동시에 만들 예정이라 배열로 저장
		String[] pathArr = { admin_path, path };

		// for문을 통해 폴더가 없을 경우 생성(초기 실행시 동작)
		for (int i = 0; i < pathArr.length; i++) {
			File f = new File(pathArr[i]);
			if (!f.exists()) {
				f.mkdirs();
			}
		}

		// BufferedWriter을 통해 Admin.txt와 내용 작성
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(pathArr[0] + "Admin.txt"), "utf-8"));

			bw.write("Admin/Admin/"+AdminDAO.getInstance().totalMoney()+"/0");

			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}// User,Admin 폴더 생성 및 Admin/Admin.txt파일 생성

	public int userWrite(UserVO vo) {
		
		//정상으로 작동했는지 확인하기 위한 코드
		int res = 0;

		// DB생성 코드 작성
		String userPath = path + vo.getId();

		File f = new File(userPath);

		if (!f.exists()) {
			f.mkdirs();
		}

		BufferedWriter bw;

		try {

			// 회원가입 시 보너스 지급(10,000원)
			int money = 10000;
			
			//작성할 내용(id,pwd,account,money)을 배열로 저장
			String[] content = { vo.getId(), vo.getPwd(), vo.getAccountNumber(), String.format("%,d", money) };

			for (int i = 0; i < content.length; i++) {
				//true값을 줘서 배열안의 값이 모두 다 저장될 수 있게 만듬
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

			// 거래내역을 저장할 txt 생성
			bw = new BufferedWriter(new FileWriter(userPath + "/Acc.txt"));
			bw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//모두 정상적으로 완료되었다면 res=1
		return res;

	}// User db저장

	public int del(String id) {
		
		//삭제가 완료되었는지 판단하기위한 코드
		int res = 0;

		// 경로 설정
		String del = path + id;

		// 삭제할 유저의 폴더에 접근, 안에 내용을 배열로 저장
		File file = new File(del);
		File[] fileList = file.listFiles();
		
		// 폴더 안의 파일들의 length만큼 삭제 진행, 모두 다 지웠으면 마지막에 id폴더 삭제
		for (int i = 0; i < fileList.length; i++) {
			fileList[i].delete();
		}
		file.delete();

		if (!file.exists()) {
			//폴더가 삭제되었다면 res=1
			res = 1;
		}

		return res;
	}

	
	//이체 시 거래내역 작성해주는 코드
	public void transWrite(UserVO vo, UserVO vo1, int sendMoney, int nowMoney) {

		// vo1 = 보내는사람 정보
		// vo = 받는사람 정보
		// sendMoney = 보내는 돈
		// nowMoney = 보낸사람 계좌에 남은 돈
		// resMoney = 받는사람 계좌에 남은 돈

		int resMoney = vo.getMoney() + sendMoney;
		
		// 보낸사람, 받는 사람 아이디 배열로 저장
		String[] accArr = { vo1.getId(), vo.getId() };

		BufferedWriter bw;

		try {

			for (int i = 0; i < accArr.length; i++) {

				bw = new BufferedWriter(new FileWriter(path + accArr[i] + "/data.txt"));
				
				//i가 1일땐 받는 사람의 통장잔액을 수정
				//i가 0일땐 보낸 사람의 통장잔액 수정
				if (i > 0) {
					bw.write(vo.getId() + "/" + vo.getPwd() + "/" + vo.getAccountNumber() + "/"
							+ String.format("%,d", resMoney));
					bw.close();
				} else {
					bw.write(vo1.getId() + "/" + vo1.getPwd() + "/" + vo1.getAccountNumber() + "/"
							+ String.format("%,d", nowMoney));
					bw.close();
				}

			}

			for (int i = 0; i < accArr.length; i++) {

				//true값을 줘서 기존 내용 뒤에 써지게 만듬(거래내역은 삭제되면 안되기 때문)
				bw = new BufferedWriter(new FileWriter(path + accArr[i] + "/Acc.txt", true));
				
				//i가 1일땐 받는 사람의 거래내역을 수정
				//i가 0일땐 보낸 사람의 거래내역을 수정
				if (i > 0) {
					bw.write("보낸 사람 계좌번호 : " + vo1.getAccountNumber() + "\n받은 금액 : " + String.format("%,d", sendMoney)
							+ "\n통장 잔액 : " + String.format("%,d", resMoney) + "\n");
					bw.close();
				} else {
					bw.write("받은 사람 계좌번호 : " + vo.getAccountNumber() + "\n보낸 금액 : " + String.format("%,d", sendMoney)
							+ "\n통장 잔액 : " + String.format("%,d", nowMoney) + "\n\n");
					bw.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}// User db저장

}
