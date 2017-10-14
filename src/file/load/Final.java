package file.load;

import java.io.File;
import java.util.ArrayList;

import drives.box.BoxDown;
import drives.dropbox.DropboxDown;
import drives.google.GoogleDown;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Final {
	
//	// 클라우드별 액세스 토큰
//	String google_access_token="ya29.GlzjBBaCgzPC2znI6HJKYIFpUagsEMrlOeQpRQRj5kjXXmW2wntRtetAwhWylnApMbX67MbbL53dlv8PkB05XEi78y8FYSiJXy-sZfMCqDAxmo9ZT4ZUKVid3xDp_g";	
//	String Box_access_token="hbEXe0qb5uXg0ballVfIihg6AotUtBd0";	
//	String Drop_access_token="j2zjj8cBlkAAAAAAAAAAH4tp4Rf3cKaTik1TTLGapKdgE5c0540w79ZzRnJBU0Ae";
//	
//	// 클라우드별 로그파일 저장경로
//	String google_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Google.txt";	
//	String box_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Box.txt";		
//	String dropbox_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Drop.txt";
//	
//	// 다운로드된 분할파일들 저장경로 ____ tmp/download/spr/
//	String sprDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
//			"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")
//			+"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"spr"+System.getProperty("file.separator");
//	
//	// 병합 후 원본파일 저장경로 ____ tmp/download/org/
//	String orgDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
//			"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+
//			"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"org"+System.getProperty("file.separator");
//	
//	// 원본파일 이름
//	static String orgFilename="test.pptx";
	
	public static String getOnlyFileName(String filename){		//파일 이름만 뽑아내는 메소드
		String name[]=filename.split("\\.");
		return name[0];
	}	
	
	public static String getOnlyFileExtension(String filename){		//파일 확장자만 뽑아내는 메소드
		String name[]=filename.split("\\.");
		return name[1];
	}	
	
//	public String getOrgDirectory(){
//		return orgDirectory;
//	}
//	public String getOrgFilename(){
//		return orgFilename;
//	}
	
	
	
}
