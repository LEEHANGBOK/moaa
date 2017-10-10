package file.load;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
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
			
			// 관리자 Login
			String url = "jdbc:mysql://localhost:3306/moaa";
			String id = "root";
			String pw = "andrew12345";
			
			// 연결
			Connection conn = DriverManager.getConnection(url, id, pw);
			
			// Session 받아오기
			HttpSession session = request.getSession();
			
			int session_id = (int) session.getAttribute("key_id");
			System.out.println("FileUpload : " + session_id);
			
			Statement st = conn.createStatement();
			// 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
			String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";
			
			ResultSet rs = st.executeQuery(sql);
			
			// 토큰 디비에서 뽑아오기
			/*String sql_google_token = "SELECT token FROM token where (users_id='" + session_id +"') AND (drive='google'";
			String sql_dropbox_token = "SELECT token FROM token where (users_id='" + session_id +"') AND (drive='dropbox'";
			String sql_box_token = "SELECT token FROM token where (users_id='" + session_id +"') AND (drive='box'";
			
			ResultSet rs_google = st.executeQuery(sql_google_token);
			ResultSet rs_dropbox = st.executeQuery(sql_dropbox_token);
			ResultSet rs_box = st.executeQuery(sql_box_token);*/
			
			String user_domain_path="";
			String user_name = (String) session.getAttribute("user_name");
			
			while(rs.next()) {
			
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				user_domain_path = rs.getString("domain_path");
			}
			
			// 각 드라이브 토큰 할당 => 디비 드라이버 사용
		    
		    String google_access_token="";
		    String Drop_access_token="";
			String Box_access_token="";
			
			
			// 토큰저장
			/*while(rs_google.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				google_access_token = rs_google.getString("token");
			}
			
			while(rs_dropbox.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				Drop_access_token = rs_dropbox.getString("token");
			}
			
			while(rs_box.next()) {
				
				//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
				Box_access_token = rs_box.getString("token");
			}*/
			
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
			
//			String cloudDirectory = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/tmp/Upload/spr";
			
			
			System.out.println("cloudDirectory: " + cloudDirectory);
	     
//			System.out.println("aaaaaaaaaaaaaaaaaaaaaa");
	    	
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
			
//			System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbb");
			
			// 분할 시작
			Distribution d = new Distribution();
			// 원본파일이 지정된 경로에 있는지 없는지 체크
			Check c = new Check(path, filename1);
			c.existenceCheck(path,filename1);
			
//			System.out.println("ccccccccccccccccccccccccccc");
			
			// 위에 메소드가 true일 경우 분할작업 진행
			if(c.showExist()==true){
				
				System.out.println("in showExist");
				
				long size = c.showSize();
				
				// '3'부분이 나중에 활성화된 드라이브 수에 따라 바뀌도록 되어야 함
				// int isActiveDrive; //= DB drive
				int cloudNumber = 3;
				
				// 분할파일이 임시 저장되어있는 경로 할당
	 			/*String temp =d.showTemp();*/
	 			
				// 확장자를 제외한 오직 파일 이름만 할당
				String onlyName = c.getFileName(filename1);		
				
				//경로+파일이름(확장자까지)
				String fullPath =path+filename1;					
				
				// Console 확인
				System.out.println("filename: " + filename1);
				
//				System.out.println("ddddddddddddddddddddddddddddddddddd");
				
				// 분할 최종 메소드 distribution(임시저장 경로에 저장할거고, 분할파일을 드라이브 수로 나누고, 
				//나중에 zip 결합을 위해 확장자 빼고 이름만, 임시저장하기 위해 확장자까지)
				d.distribution(cloudDirectory, size/cloudNumber, onlyName, fullPath, user_domain_path);
				
				System.out.println("file size: " + size );
//				System.out.println("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");
				
				// 각각의 클라우드에 업로드 시작
				ReadDir readDir = new ReadDir();
				
//				System.out.println("fffffffffffffffffffffffffffffffff");
				
				// 분할된 파일들이 임시 저장되어 있는 공간을 읽어드림
				readDir.read(cloudDirectory);
				String[] temp2 = readDir.getBoxlist();
				System.out.println("min"+temp2[0]);
				BoxUp box = new BoxUp(temp2, Box_access_token);		
				DropboxUp drop = new DropboxUp(readDir.getDropboxlist(), Drop_access_token);
				GoogleUp google = new GoogleUp(readDir.getGooglelist(), google_access_token);
				box.start();
				drop.start();
				google.start();
				System.out.println("-------Finish -------");
				
				
			}else{
				System.err.println("error");
				response.sendRedirect("dashboard.jsp");    
			}
		    System.out.println("after if : " + path);            
	    }catch(Exception e){ 
	        e.printStackTrace(); 
	    }
		
//		response.sendRedirect("dashboard.jsp");  
	}

}