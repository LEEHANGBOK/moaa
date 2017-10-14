package drives.delete;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.Metadata;

public class DropboxDelete extends Thread{
		private String access_Token;
		private ArrayList filepath;
		private String directory;
	
	public DropboxDelete(ArrayList path, String token, String directory) {
		
		filepath=path;
		access_Token=token;
		this.directory = directory;
	}
	
	

	public void run() {
		  
				
			    final String ACCESS_TOKEN = access_Token;
			    DbxRequestConfig config2= new DbxRequestConfig("USER_ID/1.0", Locale.getDefault().toString());
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
		               
		                
		                Metadata metadata = client.files().delete(folderName);
		                } finally
		                {
		                    downloadFile.close();
		                    System.out.println("Drop delete complete :" + filename);
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
		        System.out.println("delete complete");
		        
	}
	
}
