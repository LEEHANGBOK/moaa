package drives.delete;

import java.util.ArrayList;
import java.util.Iterator;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxUser;

public class BoxDelete extends Thread{
	
	
	private String access_Token ;
	
	private ArrayList filepath;
	
	
	public BoxDelete(
			ArrayList path,
			String token) {
		filepath=path;
		access_Token=token;
	}
	

	
	public void run() {
	  	// txt 파일읽어오기  
		
		
		
		//인증으로 얻은 accesstoken넣으면
		String access_token = access_Token;

		Iterator iterator = filepath.iterator();
        
        while(iterator.hasNext()) {
        
        String id = (String) iterator.next();	
		BoxAPIConnection api = new BoxAPIConnection(access_token);
        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
		
		
		System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());
		 BoxFolder rootFolder = BoxFolder.getRootFolder(api);
	        
	        
	        
	        
	       
	        BoxFile deletefile = new BoxFile(api, id);
	        
	        
	        String name = deletefile.getInfo().getName();
	      
	        deletefile.delete();
	        
	        System.out.println("Box  Delete File : " + name);
	        
	        
	        
        }
	}
}
