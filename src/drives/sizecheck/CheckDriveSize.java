package drives.sizecheck;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import util.db.MySqlConnection;

/**
 * Servlet implementation class CheckDriveSize
 */
//@WebServlet("/CheckDriveSize")
public class CheckDriveSize extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDriveSize() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String google_access_token="";
		
		String Box_access_token="";
		
		String Drop_access_token="";
		
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
			
			// 토큰 디비에서 뽑아오기
			
			// 각 드라이브 토큰 할당 => 디비 드라이버 사용
			
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
		
			GoogleChecksize google = new GoogleChecksize(google_access_token);
			google.start();
			
			DropChecksize drop = new DropChecksize(Drop_access_token);
			drop.start();
			
			BoxChecksize box = new BoxChecksize(Box_access_token);
			box.start();
	
			
//			int googleSize = Integer.parseInt(google.getFileSize("GB"));
//			int boxSize = Integer.parseInt(box.getFileSize("GB"));
//			int dropboxSize = Integer.parseInt(drop.getFileSize("GB"));
			
			
			
			ArrayList<Thread> threadList = new ArrayList<Thread>();
			
			threadList.add(box);
			threadList.add(drop);
			threadList.add(google);
			
			
			for(int i = 0 ; i < threadList.size() ; i++) {
				threadList.get(i).join();
			}
			String googleSize = google.testsize;
			String dropboxSize = drop.testsize;
			String boxSize = box.testsize;
			
			String googleexp = google.testexp;
			String dropexp = drop.testexp;
			String boxexp = box.testexp;
			
			Map<String, String> google_s = new HashMap<String, String>();
			google_s.put("drive", "google");
			google_s.put("size", googleSize);
			google_s.put("exp", googleexp);
			
			Map<String, String> dropbpx_s = new HashMap<String, String>();
			dropbpx_s.put("drive", "dropbox");
			dropbpx_s.put("size", dropboxSize);
			dropbpx_s.put("exp", dropexp);
			
			Map<String, String> box_s = new HashMap<String, String>();
			box_s.put("drive", "box");
			box_s.put("size", boxSize);
			box_s.put("exp", boxexp);
			
			ArrayList<Map> drives = new ArrayList<Map>();
			
			drives.add(google_s);
			drives.add(dropbpx_s);
			drives.add(box_s);
			
			request.setAttribute("driveSizes", drives);
			
			ServletContext context = getServletContext();
			
			RequestDispatcher dispatcher = context.getRequestDispatcher("/dashboard.jsp");
			
			dispatcher.forward(request, response);
			
//			response.sendRedirect("dashboard.jsp");
			
			
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
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
	}

}
