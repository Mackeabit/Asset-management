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
	}
	
	public List<UserVO> selectList() {

		List<UserVO> list = new ArrayList<UserVO>();
		
		//경로 설정
		String path ="C:/web_14_java/user/";
		
		//경로내의 파일리스트를 배열로 저장
		//폴더명이 ID로 되어있게하여 저장되어있는 회원들의 아이디를 모두 불러오는 작업.
		File f = new File(path);
		String[] data = f.list();
		
		try {
			BufferedReader br = new BufferedReader
								(new FileReader(path));
			
			BufferedWriter bw = new BufferedWriter
								(new FileWriter(path));
			
			for(int i = 0; i<data.length; i++) {
				
				UserVO vo = new UserVO();
				//회원정보 자동저장하는 코드 넣어야함.
				vo.setIdx(i);
				
				
			}//for
			
		} catch (Exception e) {

			e.printStackTrace();
		}
		

		return list;
	}
	
	public void datawrite() {
		//DB생성 코드 작성
	}
	
	
	
	
}
