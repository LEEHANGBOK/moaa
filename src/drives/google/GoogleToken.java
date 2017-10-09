package drives.google;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class GoogleToken
 */
//@WebServlet("/GoogleToken")
public class GoogleToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GoogleToken() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		try {
	        // 드라이버 로딩
	        String driver = "com.mysql.jdbc.Driver";
	        Class.forName(driver);
	        
	        // 관리자 Login
	        String url = "jdbc:mysql://localhost:3306/moaa";
	        String id = "root";
	        String pw = "andrew12345";
	        
	        // 연결
	        Connection conn = DriverManager.getConnection(url, id, pw);
	        
	        // 세션받아오기
	        HttpSession session = request.getSession();
	        
	        // sql 구사
	         // 전 페이지인 LOGIN.jsp input에 입력한 값들을 변수에 담는다
	        String google_token= request.getParameter("google_token");
	        System.out.println(google_token);
	        
	        int session_id = (int) session.getAttribute("key_id");
	        
	        Statement st = conn.createStatement();
	        // 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
	        String sql = "INSERT INTO token (user_id, drive, token) VALUES ('" + session_id + 
                    "', 'google', '" 
                    + google_token + 
                    "')";

	        st.executeUpdate(sql);


            response.sendRedirect("google_auth.jsp");    
	            
	        
	        
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
