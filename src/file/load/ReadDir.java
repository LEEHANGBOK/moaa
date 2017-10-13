package file.load;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import drives.box.BoxUp;
import drives.dropbox.DropboxUp;
import drives.google.GoogleUp;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class ReadDir {
	
	public static String[] Googlelist;
	public static String[] Boxlist;
	public static String[] Droplist;
	public static String[] totallist;
	
	public void read(String dir, int drive_count) {
		String path=dir;
		File dirFile=new File(path);
		File []fileList=dirFile.listFiles();
		int size = fileList.length;
		
		//totallist = new String[size];
		
		int drivesize = size/drive_count+1;
		int flag =0;
		int fluence = 0;
		Googlelist = new String[drivesize];
		Boxlist = new String[drivesize];
		Droplist = new String[drivesize];
		for(File tempFile : fileList) {
		  if(tempFile.isFile()) {
		    String tempPath=tempFile.getParent();
		    String tempFileName=tempFile.getName();
		    System.out.println("Path="+tempPath);
		    System.out.println("FileName="+tempFileName);

		    switch(flag) {
		    case 0  :System.out.println("G: " + flag +", fluence :" + fluence + ", size : "+ Googlelist.length); 
		    	Googlelist[fluence] = tempPath+System.getProperty("file.separator")+tempFileName;
		    			flag = 1;
		    			break;
		    case 1  :System.out.println("B: " + flag +", fluence :" + fluence + ", size : "+ Boxlist.length); 
		    	Boxlist[fluence] = tempPath+System.getProperty("file.separator")+tempFileName;
						flag = 2;
						break;
					
		    case 2  : System.out.println("D: " + flag +", fluence :" + fluence + ", size : "+ Droplist.length);   
		    	Droplist[fluence] = tempPath+System.getProperty("file.separator")+tempFileName;
		    			flag = 0;
		    			fluence++;
		    			break;
		    	default : System.out.println("finish");
		    			break;
		    }
		    
		  }
		}
	}
		
	public String[] getBoxlist(){
		return Boxlist;
		
	}
	
	public String[] getGooglelist(){
		return Googlelist;
	}
	
	public String[] getDropboxlist(){
		return Droplist;
	}
	
	
	public static void main(String[] args) throws IOException {
		
//		// 분할파일 저장소 경로
//		String directory=System.getProperty("user.home")+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+"UserDomain"+System.getProperty("file.separator")+"UserDomain_01"+System.getProperty("file.separator")+"Temp"+System.getProperty("file.separator")+"Upload";
//		
//		ReadDir dir = new ReadDir();	
//		dir.read(directory);
//		
//		// db에서 token 받아와야함
//		String Drop_access_token="";
//		String Box_access_token="";
//		String google_access_token="";
//
//		BoxUp box = new BoxUp(Boxlist, Box_access_token);		
//		DropboxUp drop = new DropboxUp(Droplist, Drop_access_token);
//		GoogleUp google = new GoogleUp(Googlelist, google_access_token);
//		box.start();
//		drop.start();
//		google.start();
//		System.out.println("-------Finish -------");
	}

}
