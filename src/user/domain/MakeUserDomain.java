package user.domain;

import java.io.IOException;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.db.MySqlConnection;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servlet implementation class MakeUserDomain
 */
//@WebServlet("/MakeUserDomain")
public class MakeUserDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeUserDomain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		// TODO Auto-generated method stub
		try {
			CreateDirectory obj_mkdir = new CreateDirectory();
			DevideDir obj_div = new DevideDir();
			SHA1 obj_sha1 = new SHA1();
			
			
//			String tmpString = obj_div.code();
			
			// JoinCheck로부터 받아온 새 유저의 id값을 받아와서 tmpString에 임시 저장한다.
			String tmpString = (String) request.getAttribute("user_id");
			int int_tmpString = Integer.parseInt(tmpString);

			String hashValue = obj_sha1.sha1(tmpString);
			
			String devidedDir = obj_div.divDir(hashValue);
			obj_mkdir.makeDir(devidedDir);
			
			// Console Output
			System.out.println("Got value from JoinCheck.java successfully! : " + hashValue);
			System.out.println("It was successful to get new user's domain");
			
			 // Driver loading
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            
            MySqlConnection mysqlConn = new MySqlConnection();
	        
	        	// 관리자 Login
	        String mysql_url = mysqlConn.getDBurl();
	        String mysql_id = mysqlConn.getDBid();
	        String mysql_pw = mysqlConn.getDBpw();
            
            	// 연결
            // users_domain에 user의 도메인 경로를 저장
            Connection conn = DriverManager.getConnection(mysql_url, mysql_id, mysql_pw);
            Statement st = conn.createStatement();
            String sql = "INSERT INTO users_domain (users_id, domain_path) VALUES ('" + int_tmpString + 
                    "','" + devidedDir + 
                    "')";
            st.executeUpdate(sql);
            System.out.println("it was successful to save new user's domain path into DB");
			
			// 회원가입에 성공하고 도메인 생성 성공시 로그인 페이지로 보낸다
            response.sendRedirect("sign_in.jsp");
			
		} catch (NoSuchAlgorithmException e) {
			e.getStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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

