package drives.sizecheck;
import java.util.Locale;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class DropChecksize extends Thread {

	private String access_Token ;
	String testsize;
String testexp;
	
	public DropChecksize(String token) {
		
		access_Token=token;
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
		  
				
			    final String ACCESS_TOKEN = access_Token;
			    DbxRequestConfig config2= new DbxRequestConfig("USER_ID/1.0", Locale.getDefault().toString());
			    DbxClientV2 client = null;
			    
			    config2 = new DbxRequestConfig("dropbox/java-tutorial");
		        client = new DbxClientV2(config2, ACCESS_TOKEN);
		        
		        try {
		        	//사용중인 양  
		        	
		        	String usedsize =  getFileSize(Long.toString(client.users().getSpaceUsage().getUsed()));
		    		
		    		//used size 넘겨  줘야함   
		    		testsize = usedsize;
		    		
		    		
		    		String usedexp = "GB";
		        	testexp = usedexp;
		        	
					System.out.println("DROP BOX used : "+testsize + " " + usedexp);
						
					} catch (DbxApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DbxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		       
	}
}
