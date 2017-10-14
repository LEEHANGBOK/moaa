package drives.delete;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import file.delete.FileDelete;
import user.domain.FilePath;
import util.db.MySqlConnection;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class DeleteDriveFile
 */
@WebServlet("/DeleteDriveFile")
public class DeleteDriveFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteDriveFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
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
		   
	    // log파일 읽어오기 위한 파일 이름
	    String fnForReadLog [] = request.getParameterValues("file_name");
	    for(int i=0; i<fnForReadLog.length; i++)
	 	   System.out.println("[knowing] Filename for delete: " + fnForReadLog[i]);
	   
	    String loadFileDir = fnForReadLog[0];
		
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
			
		
		
				
		
			FilePath filePath = new FilePath(user_domain_path);
			
			// 클라우드별 로그파일 저장경로
			String google_directory = filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Google.txt";	
			String box_directory = filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Box.txt";		
			String dropbox_directory =  filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator") + "logfile_Drop.txt";
			
			String box_load_path = filePath.getLoadPath() + System.getProperty("file.separator") + loadFileDir + System.getProperty("file.separator");
			
			Deletemain down = new Deletemain();
			
			
			//로그파일 읽어오기
			ArrayList Google_list = down.readtext(google_directory);
			
			ArrayList Box_list = down.readtext(box_directory);
			
			ArrayList Dropbox_list = down.readtext(dropbox_directory);
			
			
			
			GoogleDelete google = new GoogleDelete(Google_list, google_access_token);
			google.start();
			
			
			
			BoxDelete box = new BoxDelete(Box_list, Box_access_token);
			box.start();
			
			
			DropboxDelete drop = new DropboxDelete(Dropbox_list, Drop_access_token, box_load_path);
			drop.start();
			
			ArrayList<Thread> threadList = new ArrayList<Thread>();
			
	
			threadList.add(box);
			threadList.add(drop);
			threadList.add(google);
			
			
			for(int i = 0 ; i < threadList.size() ; i++) {
				threadList.get(i).join();
			}
			
			
			
			// /log/info (대쉬보드) 파일 이름 가져오기
			String logInfoFileName = "logfile_" + loadFileDir + ".txt";
			System.out.println("[Knowing] logInfoFileName : " + logInfoFileName);
			
			// /log/load 에 로그 디렉토리 가져오기
			String logLoadFilePath = filePath.getLoadPath() + "/" + loadFileDir;
			
			FileDelete fileDelete = new FileDelete();
			// log/info (대쉬보드) 파일 삭제
			fileDelete.deleteLogInfo(filePath.getInfoPath(), logInfoFileName);
			// log/load (업로드 기록) 파일 삭제
			fileDelete.deleteLogLoad(logLoadFilePath);
			
			System.out.println("[Kowing] All related files are deleted!");
			
			response.sendRedirect("dashboard.jsp");
		
		} catch(Exception e) {
			   e.printStackTrace();
		}
		
	}

}
