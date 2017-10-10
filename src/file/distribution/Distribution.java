package file.distribution;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Distribution {

	/*static String userDomain;*/
	/*static String tempDirectory;*/
	
	//"C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+"UserDomain"+System.getProperty("file.separator")+"UserDomain_01"+System.getProperty("file.separator")+"Temp"+System.getProperty("file.separator")+"Upload"
	
	// FileUpload tempDirectory는 CloudDirectory 데이터 받아옴.
	public void distribution(String tempDirectory, long size, String name, String fullpath, String user_domain_path){
		try{
			
			/*userDomain = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
					+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
					+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload"
					+ System.getProperty("file.separator") + "org" + System.getProperty("file.separator");*/
			
			/*this.tempDirectory = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
					+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
					+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload"
					+ System.getProperty("file.separator") + "spr";*/
			
			System.out.println(tempDirectory);
			
			ZipFile zipFile = new ZipFile(tempDirectory + System.getProperty("file.separator") + name + ".zip");
			
			ArrayList filesToAdd = new ArrayList();//압출할 파일 대상들.
			System.out.println("fullpath:"+fullpath);
			
			filesToAdd.add(new File(fullpath));
			
			ZipParameters parameters = new ZipParameters();
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			zipFile.createZipFile(filesToAdd, parameters, true, size);//파일 사이즈 byte단위
			System.out.println(zipFile.getSplitZipFiles());
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	// 필요없는 부분
	/*public static String showTemp(){
		return tempDirectory;
	}*/
	
	


}
