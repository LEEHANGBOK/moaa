package drives.dropbox;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.users.FullAccount;

public class DropboxDown extends Thread {

	private String access_Token ;
	private ArrayList filepath;
	
	//분할될 파일 저장 경로
	String directory= "";
	
	public DropboxDown(
			ArrayList path, 
			String token, String dir) {
		
		this.filepath=path;
		this.access_Token=token;
		this.directory = dir;
	}
	
	
	
	public void run() {
		  
				
			    final String ACCESS_TOKEN = access_Token;
			    DbxRequestConfig config2 = null;
			    DbxClientV2 client = null;
			    
			    config2 = new DbxRequestConfig("dropbox/java-tutorial");
		        client = new DbxClientV2(config2, ACCESS_TOKEN);
		        
		        
		        
		        Iterator iterator = filepath.iterator();
		        
		        while(iterator.hasNext()) {
		        
		        String filename = (String) iterator.next();
		        
		        String folderName = "/"+filename;
		        
		        
		        try
		        {
		            //output file for download --> storage location on local system to download file
		            FileOutputStream downloadFile = new FileOutputStream(directory + filename);
		            try {
		                FileMetadata metadata = client.files().downloadBuilder(folderName).download(downloadFile);
		                } finally
		                {
		                    downloadFile.close();
		                    System.out.println("download complete :" + filename);
		                }
		        }
		        //exception handled
		        catch (DbxException e)
		        {
		            e.printStackTrace();
		        }
		        catch (IOException e)
		        {
		            e.printStackTrace();
		        }
		        }
		        System.out.println("download complete");
	}
}
