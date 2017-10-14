package drives.siizecheck;
import java.util.Locale;

import com.dropbox.core.DbxApiException;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;

public class Drop_checksize extends Thread {

	private String access_Token ;
	
	public Drop_checksize(
			String token) {
		
		access_Token=token;
	}
	
	public static String getFileSize(String size) {
		   String gubn[] = {"Byte", "KB", "MB", "GB"};
		   String returnSize = new String();
		   int gubnKey = 0 ; 
		   double changeSize = 0;
		   long filesize = 0 ;
		   
		   try {
		      filesize = Long.parseLong(size);
		      for( int x = 0 ; (filesize / (double) 1024) > 0 ; x++, filesize/=(double) 1024){
		         gubnKey = x;
		         changeSize = filesize;
		      }
		      returnSize = changeSize +" " +  gubn[gubnKey];
		   }catch(Exception ex) {
		      System.out.println("wrong size");
		   }
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
		    		String[] used = usedsize.split(" ");
		    		
		    		//used size 넘겨  줘야함   
		    		String usedsizenum = used[0];
		    		String usedexp = used[1];
		        	
		        	
					System.out.println("DROP BOX used : "+usedsizenum + " " + usedexp);
						
					} catch (DbxApiException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (DbxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        
		       
	}
}
