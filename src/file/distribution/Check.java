package file.distribution;

import java.io.File;
import java.io.IOException;

public class Check {
	public Check(String filepath, String filename){
		filePath=filepath;
		fileName=filename;
	}
	static boolean isExist = false;		
	static String filePath = ""; 									//업로드 파일 경로(서버 저장 경로)
	static String fileName = "";									//업로드 파일 이름
	static String fullPath = filePath+fileName;						//경로+이름
	static long fileSize = 0;										//업로드 파일 크기
	
	public static void existenceCheck(String path,String name){		//파일이 존재하는지 체크
		try{
			File file = new File(path+fileName);					// test.pptx 파일이 있는지 확인
			if(file.isFile()){
				isExist=true;										// 파일이 존재하면, isExist = True
				fileSize = file.length();				
				System.out.println("파일이 존재합니다: "+isExist);
				System.out.println(file.getCanonicalPath().toString());
				System.out.println();				
			}else{
				System.out.println("파일이 없습니다: "+isExist);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	public static boolean showExist(){
		return isExist;
	}
	
	public static long showSize(){
		return fileSize;
	}
	
	public static String showpath(){
		return fullPath;
	}
	
	
	public static String getFileName(String name2){		//파일 확장자 split 메소드
		String name[]=name2.split("\\.");
		return name[0];
	}	

}
