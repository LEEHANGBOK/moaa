package drives.google;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

public class GoogleDown extends Thread {

	private String access_Token ;
	private ArrayList filepath;
	private Drive service;
	
	//분할될 파일 저장 경로
	String directory= "";
	
	public GoogleDown(
			ArrayList idlist, 
			String token, String dir) {
		this.filepath=idlist;
		this.access_Token=token;
		this.directory=dir;
		
		Credential credential= new GoogleCredential().setAccessToken(access_Token);
		service= new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
			    .setApplicationName("Google-PlusSample/1.0")
			    .build();
	}
	
	private static InputStream downloadFile(Drive service, File file) {
	    if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
	      try {
	        HttpResponse resp =
	            service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
	                .execute();
	        return resp.getContent();
	      } catch (IOException e) {
	        // An error occurred.
	        e.printStackTrace();
	        return null;
	      }
	    } else {
	      // The file doesn't have any content stored on Drive.
	      return null;
	    }
	  }
	
	public void run() {
	
	    try {
	    	// txt 파일읽어오
	    	

	        Iterator iterator = filepath.iterator();
	        
	        while(iterator.hasNext()) {
	        
	        String fileId = (String) iterator.next();
	    	

	    	
	
	
	    File file = service.files().get(fileId).execute();
	    String name = file.getTitle();
	    String mime = file.getMimeType();
	    
	    
	    
	    InputStream input = downloadFile(service, file);
	    OutputStream output = new FileOutputStream(directory+name);
	    
	    while(true) {
	    	int data = input.read();
	    	if(data == -1) {
	    		System.out.println("파일 end : "+ name);
	    		break;
	    	}
	    	output.write(data);
	    }
	    input.close();
	    output.close();
	        }
	        System.out.println("download complete");

	       
	    } catch (IOException e) {
	      System.out.println("An error occurred: " + e);
	      
	    }
	//}
		
	//}
	
	}
}

