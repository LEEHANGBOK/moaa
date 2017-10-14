package file.load;
/**
 * 
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import drives.box.BoxDown;
import drives.dropbox.DropboxDown;
import drives.google.GoogleDown;
import file.load.Final;
import file.load.Unzip;
import file.load.ReadLogFile;
import util.db.MySqlConnection;
import user.domain.FilePath;

/**
 * Servlet implementation class FileDownload
 */
//@WebServlet("/FileDownload")
public class FileDownload extends HttpServlet {
   private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileDownload() {
        super();
        // TODO Auto-generated constructor stub
    }

   /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      // TODO Auto-generated method stub
      doPost(request, response);
   }

   /**
    * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
    */
   
   public String arrayJoin(String glue1, String glue2, String array[]) {
	    String result = "";

	    for (int i = 0; i < array.length; i++) {
	      result += array[i];
	      if (i < array.length - 2) result += glue1;
	      if (i == array.length - 2) result+= glue2;
	    }
	    return result;
   }
   
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	   // TODO Auto-generated method stub
	   
	   request.setCharacterEncoding("utf-8");
	   
	   // log파일 읽어오기 위한 파일 이름
	   String fnForReadLog [] = request.getParameterValues("file_name");
	   for(int i=0; i<fnForReadLog.length; i++)
		   System.out.println("[knowing] Filename for download: " + fnForReadLog[i]);
	   
	   String loadFileDir = fnForReadLog[0];
	   
	   System.out.println(fnForReadLog[0]);
	   
	   String[] reName = loadFileDir.split("_");
	   
	   for(int i=0; i<reName.length; i++)
		   System.out.println(reName[i]);
	   
	   // 확장자 포함 원래 파일 이름
	   String filename = arrayJoin("_", ".", reName);
	   
	   System.out.println("[Knowing] Original file's name : " + filename);
	   
	   
	   
	   
	   String user_domain_path="";
	   
	   String google_access_token="";
	   String Drop_access_token="";
	   String Box_access_token="";
	   
	   try {
		   String driver = "com.mysql.jdbc.Driver";
		   Class.forName(driver);
			
		   MySqlConnection mysqlConn = new MySqlConnection();
	       
	        // 관리자 Login
	       String url = mysqlConn.getDBurl();
	       String id = mysqlConn.getDBid();
	       String pw = mysqlConn.getDBpw();
			
			// 연결
			Connection conn = DriverManager.getConnection(url, id, pw);
			
			// Session 받아오기
			HttpSession session = request.getSession();
			
			int session_id = (int) session.getAttribute("key_id");
			System.out.println("FileDownload : " + session_id);
			
			Statement st = conn.createStatement();
			
			String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";
			
			ResultSet rs = st.executeQuery(sql);
			
			// 토큰 디비에서 뽑아오기
			
			
			
			String user_name = (String) session.getAttribute("user_name");
			
			while(rs.next()) {
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				user_domain_path = rs.getString("domain_path");
			}
			rs.close();
			
			// 활성화된 드라이브 갯수
			int drive_count=0;
			
			// 토큰저장
			String sql_google_token = "SELECT token FROM token where (user_id='" + session_id +"') AND (drive='google') AND (is_active='1')";
			ResultSet rs_google = st.executeQuery(sql_google_token);
			while(rs_google.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				google_access_token = rs_google.getString("token");
				drive_count++;
			}
			rs_google.close();
			
			String sql_dropbox_token = "SELECT token FROM token where (user_id='" + session_id +"') AND (drive='dropbox') AND (is_active='1')";
			ResultSet rs_dropbox = st.executeQuery(sql_dropbox_token);
			while(rs_dropbox.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				Drop_access_token = rs_dropbox.getString("token");
				drive_count++;
			}
			rs_dropbox.close();
			
			String sql_box_token = "SELECT token FROM token where (user_id='" + session_id +"') AND (drive='box') AND (is_active='1')";
			ResultSet rs_box = st.executeQuery(sql_box_token);
			while(rs_box.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				Box_access_token = rs_box.getString("token");
				drive_count++;
			}
			rs_box.close();
			
			System.out.println("The number of activated dirves is : " + drive_count);
			System.out.println("User's Google Token : " + google_access_token);
			System.out.println("User's Dropbox Token : " + Drop_access_token);
			System.out.println("User's Box Token : " + Box_access_token);
		
	   } catch(Exception e) {
		   e.printStackTrace();
	   }
	   
	   FilePath filePath = new FilePath(user_domain_path);
	   filePath.getDownOrgPath();
	   filePath.getDownSprPath();
	   filePath.getLoadPath();
	   
	
		// 클라우드별 로그파일 저장경로
		String google_directory = filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Google.txt";	
		String box_directory = filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Box.txt";		
		String dropbox_directory =  filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Drop.txt";

		// 다운로드된 분할파일들 저장경로 ____ tmp/download/spr/
		String sprDirectory = filePath.getDownSprPath() + System.getProperty("file.separator");
	
		// 병합 후 원본파일 저장경로 ____ tmp/download/org/
		String orgDirectory = filePath.getDownOrgPath() + System.getProperty("file.separator");

		// 원본파일 이름 (압축풀때 필요)
		String orgFilename= filename;
		
		System.out.println(orgFilename);

		ReadLogFile readLogFile = new ReadLogFile();
		Unzip unzip = new Unzip();
		Final finalFile = new Final();
	   
	   
	   
	   
	   
	   
	   
	   
      
	   	//로그파일 받아와서 클라우드별 리스트로 넣기
		ArrayList Google_list = readLogFile.readtext(google_directory);		
		ArrayList Box_list = readLogFile.readtext(box_directory);		
		ArrayList Dropbox_list = readLogFile.readtext(dropbox_directory);
	
		//다운로드 시작	&	sprDirectory에 다운로한 분할파일들 저장
 		GoogleDown google = new GoogleDown(Google_list, google_access_token,sprDirectory);
 		google.start();
 		BoxDown box = new BoxDown(Box_list, Box_access_token,sprDirectory);	
 		box.start();	
 		DropboxDown drop = new DropboxDown(Dropbox_list, Drop_access_token,sprDirectory);
 		drop.start();
	 			
	 			
 		ArrayList<Thread> threadList = new ArrayList<Thread>();
	 		
 		threadList.add(box);
        threadList.add(drop);
        threadList.add(google);
	   
          // 모든 분할파일들이 spr폴더에 저장될 때까지 기다림.
		try{
			
			// 쓰레드 모두 끝날 때 까지 기다림
			for(int i = 0 ; i < threadList.size() ; i++) {
		           threadList.get(i).join();         
		        }
			System.out.println("______Finish Download______");
			
			// 병합시작
			
			System.out.println("unzip sprDirectory: " + sprDirectory);
			System.out.println("unzip orgFilename: " + orgFilename);
			System.out.println("unzip orgDirectory: " + orgDirectory);
			
			Unzip.unzip(sprDirectory,finalFile.getOnlyFileName(orgFilename), orgDirectory);
			System.out.println("______Finish Unzip______");
		}catch(Exception e){
			e.printStackTrace();
		}
	
	
//		// 서버상에 원본 파일이 저장되어있는 경로. (tmp/Download/org/)
//		String orgDir = f.getOrgDirectory();
//		
//		// 원본파일의 이름+확장자
//		String fileName = f.getOrgFilename();
	
		File file = new File(orgDirectory + System.getProperty("file.separator") + filename);
		System.out.println("파일명 : " + filename);
		
		// 다운로드 시작
		 String mimeType = getServletContext().getMimeType(file.toString());
		 
         if(mimeType == null)
         {
            response.setContentType("application/octet-stream");
         }
         
         // 다운로드용 파일명을 설정
         String downName = null;
         if(request.getHeader("user-agent").indexOf("MSIE") == -1)
         {
            downName = new String(filename.getBytes("UTF-8"), "8859_1");
         }
         else
         {
            downName = new String(filename.getBytes("EUC-KR"), "8859_1");
         }
         
         
         response.setHeader("Content-Disposition","attachment;filename=\"" + downName + "\";");
	         
	         
         FileInputStream fileInputStream = new FileInputStream(file);
         ServletOutputStream servletOutputStream = response.getOutputStream();
         
         byte b [] = new byte[1024];
         int data = 0;
         
         while((data=(fileInputStream.read(b, 0, b.length))) != -1)
         {
            servletOutputStream.write(b, 0, data);
         }
         
         servletOutputStream.flush();
         servletOutputStream.close();
         fileInputStream.close();
         
         response.sendRedirect("dashboard.jsp");
   }

}