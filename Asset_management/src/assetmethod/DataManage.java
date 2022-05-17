package assetmethod;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import vo.UserVO;

public class DataManage {
	
	//유저 정보를 담을 ArrayList생성
	
	public List<UserVO> userList(){
		
		//경로 설정
		String path ="C:/web_14_java/user/";
		
		//경로내의 파일리스트를 배열로 저장
		File f = new File(path);
		String[] fileList = f.list();
		
		//파일리스트 자동저장하는 코드 넣어야함.
		
		List<UserVO> list = new ArrayList<UserVO>();
		UserVO vo = new UserVO();
		
		return list;
	}
	
	
	
	
	
	
	
}
