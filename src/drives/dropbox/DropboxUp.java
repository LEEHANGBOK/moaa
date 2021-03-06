package drives.dropbox;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;

public class DropboxUp extends Thread {

	private String access_Token ;
	private String[] filepath;
	private String user_domain_path;
	private String filename;
	
	public DropboxUp(String[] path, String token, String user_domain_path, String filename) {
		//filepath=path;
		filepath=path;
		access_Token=token;
		this.user_domain_path = user_domain_path;
		this.filename = filename;
	}
	
	public void run() {
				final String USER_NAME="";
			    final String ACCESS_TOKEN = access_Token;
			    DbxRequestConfig config2 = null;
			    DbxClientV2 client = null;
			    FullAccount account2 = null;
			    config2 = new DbxRequestConfig("dropbox/java-tutorial",null);
		        client = new DbxClientV2(config2, ACCESS_TOKEN);// input change => 서버 도메인으로 로그파일 자동생성
		        try {
		        	// input change => 서버 도메인으로 로그파일 자동생성
//					BufferedWriter a = new BufferedWriter(new FileWriter(System.getProperty("user.home")+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
//							+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
//							+ user_domain_path +System.getProperty("file.separator")+"log" + System.getProperty("file.separator") + "load" 
//							+ System.getProperty("file.separator") + filename + System.getProperty("file.separator")+"logfile_Drop.txt",true));
		        	
		        	BufferedWriter a = new BufferedWriter(new FileWriter("/home/andrew/Desktop/Workspace/dirPractice/"
		                	+ user_domain_path + "/log/load/" + filename + "/logfile_Drop.txt",true)); 
				
					
		        try {
		            account2 = client.users().getCurrentAccount();
		            System.out.println("[Knowing] Dropbox's authorized user's name : " + account2.getName().getDisplayName());
		        }
		        catch (DbxException dbxe)
		        {
		            dbxe.printStackTrace();
		        }
				   
		        String folderName = "/";// + System.currentTimeMillis();
		        
		       
		        	for(String path : filepath) {
		        		if(path!=null) {
		        	int n = path.lastIndexOf("/");
		        	int size = path.length();
		        	
		        		//저장된 파일경
		        	String foldername = folderName+path.substring(n+1, size);
		        	
		        	
		        try {
		            InputStream in = new FileInputStream(path);
		            FileMetadata metadata = client.files().uploadBuilder(foldername).uploadAndFinish(in);
		           
		            System.out.println("Drop box finished at : "+metadata.getName());
		            a.write(metadata.getName());
		            a.newLine();
		        }
		        catch (FileNotFoundException fne)
		        {
		            fne.printStackTrace();
		        }
		        catch (IOException ioe)
		        {
		            ioe.printStackTrace();
		        }
		        catch (DbxException dbxe)
		        {
		            dbxe.printStackTrace();
		        }
		        		}}
		        a.close();	
		        } catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	}
		

}
