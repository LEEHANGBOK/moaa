package file.readwrite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadLog {String name;
String type;
long size;
long updatedtime;
// 파일명, 타입, 용량 크기, 최근 수정날짜, 

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
		returnSize = changeSize + gubn[gubnKey];
	}catch(Exception ex) {
		System.out.println("wrong size");
	}
	return returnSize;
}

public Map write(File file) throws IOException {
	BufferedReader in = new BufferedReader(new FileReader(file));
	String name;
	String type;
	String size;
	
	
	//변환된 사이즈
	String aftersize;
	
	String time;
	Map<String, String> list = new HashMap<String, String>();
    if((name = in.readLine()) != null) {
      list.put("name", name);
    }
    if((type = in.readLine()) != null) {
        list.put("type", type);
      }
    if(( size = in.readLine()) != null) {
    		aftersize = getFileSize(size);
        list.put("size", aftersize);
      }
    if((time = in.readLine()) != null) {
        list.put("time", time);
      }
    in.close();

    return list;

   
}




}