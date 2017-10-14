package drives.box;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;

import com.box.sdk.BoxAPIConnection;
import com.box.sdk.BoxFile;
import com.box.sdk.BoxFolder;
import com.box.sdk.BoxItem;
import com.box.sdk.BoxUser;

public class BoxDown extends Thread{
	
	private String access_Token ;
	private ArrayList filepath;
	private String[] fileID;
	
	//분할파일 저장될 경로
    String directory="11111111";
    
    // 파일이름 뽑아오기
    static String filename="";
    
    public static String getFileName(String file){		//파일 확장자 split 메소드
		String name[]=file.split("\\.");
		return name[0];
	}	
	
    public BoxDown(
		ArrayList path,
		String token, String dir) {
		this.filepath=path;
		//ßfilepath=path;
		//fileID= new String[filepath.length];
		this.access_Token=token;
		this.directory = dir;
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
        
        BoxFile downloadfile = new BoxFile(api, id);
        
        BoxItem.Info iteminfo = downloadfile.getInfo();
        String name = iteminfo.getName();
        filename = getFileName(name);
        System.out.println(iteminfo.getSize());
        try {
			OutputStream output = new FileOutputStream(directory+name);
			downloadfile.download(output);
			System.out.println("download : "+name);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        }
        System.out.println("download complete");
	}

}

