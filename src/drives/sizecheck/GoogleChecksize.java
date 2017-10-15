package drives.sizecheck;

import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.About;

public class GoogleChecksize extends Thread {

	private String access_Token ;
	private Drive service;
	String testsize;
	String testexp;
	
	public GoogleChecksize(
			String token) {
		
		access_Token=token;
		
		Credential credential= new GoogleCredential().setAccessToken(access_Token);
		service= new Drive.Builder(new NetHttpTransport(), JacksonFactory.getDefaultInstance(), credential)
			    .setApplicationName("Google-PlusSample/1.0")
			    .build();
	}
	
	
	public static String getFileSize(String size) {
		String gubn[] = {"Byte", "KB", "MB", "GB"};
		   String returnSize = new String();
		   int gubnKey = 0 ; 
		   double changeSize = 0;
		   long filesize = 0 ;
		   
		      filesize = Long.parseLong(size);
		      double testsize =0;
		      double testsize2 = 0;
		      testsize =  (filesize/(double) 1024);
		      testsize = Double.parseDouble(String.format("%.4f",testsize));


		      testsize/=(double) 1024;
		      testsize = Double.parseDouble(String.format("%.4f",testsize));
		      testsize/=(double) 1024;
		      testsize = Double.parseDouble(String.format("%.3f",testsize));
		      
		    		  returnSize = Double.toString(testsize);
		   return returnSize;
		}
	
	
	public void run() {

		About about;
		try {
			about = service.about().get().execute();
			
			String usedsize =  getFileSize(Long.toString(about.getQuotaBytesUsed()));
    		
    			
    			//used size 넘겨  줘야함   
    			
    			
    		
    			testsize = usedsize;
    			testexp = "GB";
    			
    			Integer.parseInt(usedsize);
    			
			//사용중인 드라이브 양   
			System.out.println("GOOGLE USED SIZE : " + usedsize + " " + "GB");
			
			

			String totalsize =  getFileSize(Long.toString(about.getQuotaBytesTotal()));
			String[] total = totalsize.split(" ");
			
			
			//total size 넘겨  줘야함 
			String totalsizenum = total[0];
			String totalexp = total[1];
			//총 드라이브 양  
			System.out.println("GOOGLE TOTAL SIZE : " + totalsizenum + " " + totalexp);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    	
	
	}
}

