package drives.sizecheck;


import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;

public class BoxChecksize extends Thread{
	
	private String access_Token ;
	String testsize ; 
	String testexp;
	
	public BoxChecksize(
			String token) {
		
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
	  	// txt 파일읽어오기  
		
		
		
		//인증으로 얻은 accesstoken넣으면
		String access_token = access_Token;

		
		BoxAPIConnection api = new BoxAPIConnection(access_token);
        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
		
		
		System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());
		
		
		String totalsize =  getFileSize(Long.toString(userInfo.getSpaceAmount()));
		//total size 넘겨  줘야함   
		String totalsizenum = totalsize;
		
		String totalexp = "GB";
		//총 드라이브 크
        System.out.println("BOX TOTAL SIZE : "+ totalsizenum + " " + totalexp);
        
        String usedsize =  getFileSize(Long.toString(userInfo.getSpaceUsed()));
        
      
        
		
		
		//used size 넘겨  줘야함   
		String usedsizenum = usedsize;
		
		  testsize = usedsizenum;
		
		String usedexp = "GB";
		testexp = usedexp;
        //사용 중인 드라이브  양  
        System.out.println("BOX USED SIZE : "+usedsizenum + " " + usedexp );
        
        
		
        
        
	}
}

