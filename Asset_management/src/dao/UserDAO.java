package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
				BufferedReader br = new BufferedReader(new FileReader(path+data[i]+sql));
				
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
				vo.setIdx(rs[0]);
				vo.setName(rs[1]);
				vo.setId(rs[2]);
				vo.setPwd(rs[3]);
				vo.setAccountNumber(rs[4]);
				list.add(vo);
				br.close();
				
			}//for
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		
		//저장이 모두 끝난 list반환
		return list;
	}
	
	
	
	public void datawrite() {
		//DB생성 코드 작성
	}
	
	
	
	
}
