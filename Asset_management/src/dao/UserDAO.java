package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;

public class UserDAO {
	
	static UserDAO single = null;

	public static UserDAO getInstance() {
		//생성되지 않았으면 생성
		if (single == null)
			single = new UserDAO();
		//생성된 객체정보를 반환
		return single;
	}// getInstance();
	
	public List<UserVO> selectList() {

		List<UserVO> list = new ArrayList<UserVO>();
		
		//경로 설정
		String path ="C:/java_db_test/user/";
		
		//경로내의 파일리스트를 배열로 저장
		//각 폴더명을 ID로 만들 예정, 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File f = new File(path);
		String[] data = f.list();
		
		//ID폴더마다 들어있는 txt이름(개인DB를 모두 각 data.txt에 저장할 예정)
		//저장 형식은 idx,name,id,pwd,accountNumber으로 구분자를 ','로 설정할 것.
		String sql = "/data.txt";
		
		//vo에 모든 회원정보 저장
		try {
			
			//id갯수만큼 for문 실행
			for(int i = 0; i<data.length; i++) {
				
				//경로는 기본경로 + 아이디 + data.txt
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(path+data[i]+sql),"utf-8"));
				//line은 읽어오는 용도, content는 line을 저장할 용도
				String line="";
				String content = "";
				
				while((line = br.readLine())!=null) {
					content += line;
				}
				
				//','를 구분자로 저장하기에 각 구분자에 맞게 나눠주기
				String[] rs = content.split(",");
				
				
				//나눠진 문자열을 vo에 순서대로 저장
				//list에 세팅된 vo를 저장
				UserVO vo = new UserVO();
				vo.setName(rs[0]);
				vo.setId(rs[1]);
				vo.setPwd(rs[2]);
				vo.setAccountNumber(rs[3]);
				list.add(vo);
				br.close();
				
			}//for
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		
		//저장이 모두 끝난 list반환
		return list;
	}
	
	public int checkList(List<UserVO> list, String find) {
		
		//중복값 없을 시 반환해줄 res값 설정
		int res = 1;
		
		//계좌번호는 편의상 9자리 고정이라 하고 진행
		//계좌번호가 넘어왔다면 if문 안으로 진행
		if(find.length() == 9) {
			//계좌번호 중복 체크
			for(int i = 0; i<list.size(); i++) {
				UserVO vo = list.get(i);
				//계좌번호 중복이라면 res=0 반환
				if(vo.getAccountNumber().equals(find)) {
					res = 0;
					break;
				}
				
			}
			
		}else {
			//아이디 중복 체크
			for(int i = 0; i<list.size(); i++) {
				UserVO vo = list.get(i);
				//중복되는 아이디가 존재한다면 res=0 반환
				if(vo.getId().equals(find)) {
					res = 0;
					break;
				}
				
			}
		}
		
		
		//res=0 --> 중복값 있음, 사용불가
		//res=1 --> 중복값 없음, 사용가능
		return res;
	}//checkLIst
	
	
	
	
	public void adminWrite() {
		
		//만들 폴더의 경로 설정(Admin,User)
		String adminPath ="C:/java_db_test/Admin/";
		String userPath ="C:/java_db_test/User/";
		
		//두가지를 동시에 만들 예정이라 배열로 저장
		String[] path = {adminPath,userPath};
		
		//for문을 통해 폴더가 없을 경우 생성(초기 실행시 동작)
		for(int i = 0; i<path.length; i++) {
			File f = new File(path[i]);
			if(!f.exists()) {
				f.mkdirs();
			}
		}
		
		//BufferedWriter을 통해 Admin.txt와 내용 작성
		BufferedWriter bw;
		
		try {
			bw = new BufferedWriter(new FileWriter(path[0]+"Admin.txt"));
			
			bw.write("Admin,Admin,Admin,Admin");
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}//User,Admin 폴더 생성 및 Admin.txt파일 생성
	
	
	public void userWrite(UserVO vo) {
		//DB생성 코드 작성
		String userPath ="C:/java_db_test/User/"+vo.getId();
		
		BufferedWriter bw;
		
		try {
			
			String[] content = {vo.getName(),vo.getId(),vo.getPwd(),vo.getAccountNumber()};
			
			for(int i = 0; i<content.length; i++) {
				bw = new BufferedWriter(new FileWriter(userPath+"/data.txt"));
				
				if(i==4) {
					bw.write(content[i]);
					bw.close();
					break;
				}
				
				bw.write(content[i]+",");
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}//User db저장
	
	
	
	
}
