package file.load;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadLogFile {
		
	public ArrayList readtext(String directory) {
		// 여기서 directory는 파일명+생성자를 포함한 디렉토리 
		// 로그파일 저장 위치(파일명+확장자까지)
		ArrayList filelist = new ArrayList();
		try {
			BufferedReader in = new BufferedReader(new FileReader(directory));
			
			//파일을 읽어온다.
			
			String s;
			
			while((s=in.readLine()) !=null) {
				filelist.add(s);
			}
			in.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println(e);
		}
		
		return filelist;
	}	
	


}
