package user.session;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

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
 * Servlet implementation class SeeUserDomain
 */
//@WebServlet("/SeeUserDomain")
public class SeeUserDomain extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SeeUserDomain() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			
			HttpSession session = request.getSession();
			int session_id = 0;
			
			String driver = "com.mysql.jdbc.Driver";
	        Class.forName(driver);
	        
	        MySqlConnection mysqlConn = new MySqlConnection();
	        
	        	// 관리자 Login
	        String url = mysqlConn.getDBurl();
	        String id = mysqlConn.getDBid();
	        String pw = mysqlConn.getDBpw();
	        
	        // 연결
	        Connection conn = DriverManager.getConnection(url, id, pw);
	        
			session_id = (int) session.getAttribute("key_id");
			System.out.println(session_id);
			
			Statement st = conn.createStatement();
	        // 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
	        String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";

	        ResultSet rs = st.executeQuery(sql);
	        String user_domain_path="";
	        while(rs.next()) {
	        	
	            //key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
	        	user_domain_path = rs.getString("domain_path");
	        }
	        
	        System.out.println("user's domain path: " + user_domain_path);
	        
	        request.setAttribute("user_domain", user_domain_path);
	        ServletContext context = getServletContext();
	        RequestDispatcher dispatcher = context.getRequestDispatcher("/dashboard.jsp");
	        dispatcher.forward(request, response);
			
		} catch(Exception e) {
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
