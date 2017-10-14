package drives.delete;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;
import com.google.api.services.drive.model.File;

public class GoogleDelete extends Thread {
	private String access_Token ;
	private Drive service;
	private ArrayList filepath;
	
	public GoogleDelete(
		ArrayList idlist, 
		String token) {
		filepath=idlist;
		access_Token=token;
		
		Credential credential= new GoogleCredential().setAccessToken(access_Token);
		service= new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
			    .setApplicationName("Google-PlusSample/1.0")
			    .build();
	}
	
	public void run() {

	    	// txt 파일읽어오
	    	

	        Iterator iterator = filepath.iterator();
	        
	        while(iterator.hasNext()) {
	        
	        String fileId = (String) iterator.next();
	    	

	    	
	
	
	    File file;
		try {
			file = service.files().get(fileId).execute();

			 String name = file.getTitle();
			 
			 
			service.files().delete(fileId).execute();
			
			System.out.println("Delete Google file name : " + name);
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	   
	    
	  
	    
	   
			
		}
		}
	
}
