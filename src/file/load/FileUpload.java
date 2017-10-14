package file.load;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import drives.box.BoxUp;
import drives.dropbox.DropboxUp;
import drives.google.GoogleUp;
import file.distribution.Check;
import file.distribution.Distribution;
import file.readwrite.WriteLog;
import user.domain.CreateDirectory;
import util.db.MySqlConnection;

/**
 * Servlet implementation class FileUpload
 */
@WebServlet("/FileUpload")
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FileUpload() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		request.setCharacterEncoding("utf-8");
		System.out.println("fileupload: doPost()");
		
		try{ 
		
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
			System.out.println("FileUpload : " + session_id);
			
			Statement st = conn.createStatement();
			// 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
			String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";
			
			// 활성화 되어있는 인증된 드라이브 갯수를 새기위한 정보
//			String sql_count = "SELECT DISTINCT drive FROM token WHERE (user='" + session_id + "') AND (is_active='1')";

			
			ResultSet rs = st.executeQuery(sql);
			
			// 토큰 디비에서 뽑아오기
			
			
			String user_domain_path="";
			String user_name = (String) session.getAttribute("user_name");
			
			while(rs.next()) {
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				user_domain_path = rs.getString("domain_path");
			}
			rs.close();
			
			
			// 각 드라이브 토큰 할당 => 디비 드라이버 사용
		    
		    String google_access_token="";
		    String Drop_access_token="";
			String Box_access_token="";
			
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
			
			// 사용자가 업로드 버튼을 눌렀을 때 원본 파일이 임시적으로 저장되는 경로
		    String dir = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
						+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
						+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload"
						+ System.getProperty("file.separator") + "org";
		    
//		    String dir = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/tmp/Upload/org";
		   
		    System.out.println("dir: " + dir);
		    
		    int max = 1024*2*1024*1024-1; // 업로드 파일의 최대 크기 지정: 2GB-1
		    String name=""; 			
		    String subject=""; 			
		    String filename1=""; 		// 업로드 파일 이름(name + subject)
		    
		    
		    
			
			// 분할된 파일들이 임시적으로 저장될 경로
			String cloudDirectory = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
					+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
					+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload" 
					+ System.getProperty("file.separator") + "spr";
			
			// String cloudDirectory = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/tmp/Upload/spr";
			
			
			System.out.println("cloudDirectory: " + cloudDirectory);
	     
	    	
	    		// 사용자가 업로드 버튼을 눌렀을 때 MultipartRequest => 파일을 업로드 시키는 method
			MultipartRequest m = new MultipartRequest(request, dir, max, "utf-8", new DefaultFileRenamePolicy());          
			
	    		
			Enumeration files=m.getFileNames();  
			System.out.println("files: " + files);
			
			String file1 =(String)files.nextElement(); 
			System.out.println("file1: " + file1);
			
			filename1=m.getFilesystemName(file1);
			System.out.println("file name: " + filename1);
			
			// '/'를 최종경로 뒤에 붙여줌
			String path = dir+System.getProperty("file.separator");
			
			
			// 분할 시작
			Distribution d = new Distribution();
			// 원본파일이 지정된 경로에 있는지 없는지 체크
			Check c = new Check(path, filename1);
			c.existenceCheck(path,filename1);
			
			
			// 위에 메소드가 true일 경우 분할작업 진행
			if(c.showExist()==true){
				
				System.out.println("in showExist");
				
				long size = c.showSize();
				
				// '3'부분이 나중에 활성화된 드라이브 수에 따라 바뀌도록 되어야 함
				// int isActiveDrive; //= DB drive
				int cloudNumber = drive_count;
				
				// 분할파일이 임시 저장되어있는 경로 할당
	 			/*String temp =d.showTemp();*/
	 			
				// 확장자를 제외한 오직 파일 이름만 할당
				String onlyName = c.getFileName(filename1);		
				String onlyExt = c.getExtension(filename1);
				
				
				String filename = onlyName + "_" + onlyExt;
				
				//경로+파일이름(확장자까지)
				String fullPath =path+filename1;
				
				// Console 확인
				System.out.println("filename: " + filename1);
				
				
				// 분할 최종 메소드 distribution(임시저장 경로에 저장할거고, 분할파일을 드라이브 수로 나누고, 
				//나중에 zip 결합을 위해 확장자 빼고 이름만, 임시저장하기 위해 확장자까지)
				d.distribution(cloudDirectory, size/cloudNumber, onlyName, fullPath, user_domain_path);
				
				File selectedOrgDir = new File(dir);
				File[] innerOrgFiles= selectedOrgDir.listFiles(); 
				int dirOrgListLen = innerOrgFiles.length;
				int targetOrgFileIndex=0;
				for(int i = 0 ; i < dirOrgListLen; i++) {
					if(innerOrgFiles[i].getName() == filename1) {
						targetOrgFileIndex = i;
						break;
					}
				}
				
				// 단일 업로드
				File dirforlog = new File(fullPath);
				String logname = onlyName;
				Path logsource = Paths.get(dir);
				String logtype = onlyExt;
				long logsize = dirforlog.length();
				Calendar cal = Calendar.getInstance();
				
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DAY_OF_MONTH);
				int hour = cal.get(Calendar.HOUR);
				int minute = cal.get(Calendar.MINUTE);
				
				String logupdate = Integer.toString(year)+"/"+Integer.toString(month)+"/"+Integer.toString(day)+","+Integer.toString(hour)+":"+Integer.toString(minute);
				
				
				if(innerOrgFiles[targetOrgFileIndex].exists()) {
					innerOrgFiles[targetOrgFileIndex].delete();
					System.out.println("[Knowing] After separating original file, the original file is successfully deleted!");
				} else {
					System.out.println("[Warning] There is no that such original file!");
				}
				
				System.out.println("file size: " + size );
				
				// 각각의 클라우드에 업로드 시작
				ReadDir readDir = new ReadDir();
				
				
				// 분할된 파일들이 임시 저장되어 있는 공간을 읽어드림
				readDir.read(cloudDirectory, drive_count);
				String[] temp2 = readDir.getBoxlist();
				System.out.println("min"+temp2[0]);
				
				CreateDirectory createLogDir = new CreateDirectory();
				createLogDir.mkLogDir(user_domain_path, filename);
				
				BoxUp box = new BoxUp(temp2, Box_access_token, user_domain_path, filename);		
				DropboxUp drop = new DropboxUp(readDir.getDropboxlist(), Drop_access_token, user_domain_path, filename);
				GoogleUp google = new GoogleUp(readDir.getGooglelist(), google_access_token, user_domain_path, filename);
				
				ArrayList<Thread> threadList = new ArrayList<Thread>();
				
				box.start();
				drop.start();
				google.start();
				
				threadList.add(box);
				threadList.add(drop);
				threadList.add(google);
				
				
				for(int i = 0 ; i < threadList.size() ; i++) {
					threadList.get(i).join();
				}
				
				
				// 분할된 파일 엽로드 완료후 삭제
				// 해당 파일의 이름을 가진 파일은 모두 삭제
				File selectedSprDir = new File(cloudDirectory);
				File[] innerSprFiles= selectedSprDir.listFiles(); 
				int dirListLen = innerSprFiles.length;
				for(int i = 0 ; i < dirListLen; i++) {
					String sprFileName = innerSprFiles[i].getName();
					if(sprFileName.contains(onlyName)) {
						innerSprFiles[i].delete();
						System.out.println("[Knowing] " + sprFileName + "is successfully deleted!");
					}
				}
				if(innerSprFiles.length == dirListLen) {
					System.out.println("[Warning] There were no that such separated files!");
				}
				
				
				
				System.out.println("------------ Finish ------------ ");
				
				//finish 이후 로그파일 생성 
				WriteLog write = new WriteLog(logname, logtype, logsize, logupdate);
				
				//사용자 도메인 = path
				write.write(user_domain_path);
				
				
				response.sendRedirect("dashboard.jsp");
				
			}else{
				System.err.println("error");
				response.sendRedirect("dashboard.jsp");    
			}
		    
	    }catch(Exception e){ 
	        e.printStackTrace(); 
	    }
		
//		response.sendRedirect("dashboard.jsp");  
	}

}
