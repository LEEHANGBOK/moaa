package drives.siizecheck;


import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxUser;

public class BoxChecksize extends Thread{
	
	private String access_Token ;

	
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
	  	// txt 파일읽어오기  
		
		
		
		//인증으로 얻은 accesstoken넣으면
		String access_token = access_Token;

		
		BoxAPIConnection api = new BoxAPIConnection(access_token);
        BoxUser.Info userInfo = BoxUser.getCurrentUser(api).getInfo();
		
		
		System.out.format("Welcome, %s <%s>!\n\n", userInfo.getName(), userInfo.getLogin());
		
		
		String totalsize =  getFileSize(Long.toString(userInfo.getSpaceAmount()));
		String[] total = totalsize.split(" ");
		//total size 넘겨  줘야함   
		String totalsizenum = total[0];
		String totalexp = total[1];
		//총 드라이브 크
        System.out.println("BOX TOTAL SIZE : "+ totalsizenum + " " + totalexp);
        
        String usedsize =  getFileSize(Long.toString(userInfo.getSpaceUsed()));
		String[] used = usedsize.split(" ");
		//used size 넘겨  줘야함   
		String usedsizenum = used[0];
		String usedexp = used[1];
        
        //사용 중인 드라이브  양  
        System.out.println("BOX USED SIZE : "+usedsizenum + " " + usedexp );
        
        
		
        
        
	}
}

