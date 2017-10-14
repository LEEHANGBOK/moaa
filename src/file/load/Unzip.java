package file.load;

import java.io.File;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;



public class Unzip {

	
//	static String sprDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
//			"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")
//			+"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"spr"+System.getProperty("file.separator");
//	
//	// 병합 후 원본파일 저장경로 ____ tmp/download/org/
//	static String orgDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
//			"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+
//			"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"org"+System.getProperty("file.separator");
	
	
	
	public static void unzip(String spr, String filename,String dst){
		try {  
		     ZipFile zipFile = new ZipFile(spr+filename+".zip");  
		     System.out.println(zipFile.getFile());    
		     
		     System.out.println( );
		     zipFile.extractAll(dst);  //병합 메소드
		    

		   } catch (ZipException e) {  
		     e.printStackTrace();  
		   } 
		//System.out.println("________finish________");
	}
	

}
