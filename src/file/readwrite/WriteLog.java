package file.readwrite;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteLog {
	
	String name;
	String type;
	long size;
	String updatedtime;
	// 파일명, 타입, 용량 크기, 최근 수정날짜, 
	public WriteLog(String name, String type, long size, String updatedtime) {
		this.name = name;
		this.type = type;
		this.size = size;
		this.updatedtime = updatedtime;
		
	}
	
	public void write(String path) throws IOException {
		
		System.out.println(" test log saved : " + name +", "+ type +", "+ size+", "+updatedtime );
		BufferedWriter a = new BufferedWriter(new FileWriter("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/log/info/logfile_"+name+"_"+type+".txt",true)); 
    	

    	// Uncomment the following line to print the File ID.
	   System.out.println("log saved : " + name +", "+ type +", "+ size+", "+updatedtime );
	   a.write(name);
	   a.newLine();
	   a.write(type);
	   a.newLine();
	   a.write(Long.toString(size));
	   a.newLine();
	   a.write(updatedtime);
	   a.close();
	}


}
