package user.info;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.db.MySqlConnection;

/**
 * Servlet implementation class JoinCheck
 */
//@WebServlet("/JoinCheck")
public class JoinCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JoinCheck() {
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
		
		PrintWriter out = response.getWriter();
		
		// JOIN.jsp input 에서 입력한 회원가입에 필요한 값들을 변수에 담아준다
        String email = request.getParameter("join_email");
        String username = request.getParameter("join_username");
        String pw = request.getParameter("join_pw");
        
        try {
            // 드라이버 로딩
            String driver = "com.mysql.jdbc.Driver";
            Class.forName(driver);
            
            MySqlConnection mysqlConn = new MySqlConnection();
	        
	        	// 관리자 Login
	        String mysql_url = mysqlConn.getDBurl();
	        String mysql_id = mysqlConn.getDBid();
	        String mysql_pw = mysqlConn.getDBpw();
            
            // 연결
            // INSERT 해달라 USERTABLE에 
            // id, pw, hobby 라는 필드를 가진
            Connection conn = DriverManager.getConnection(mysql_url, mysql_id, mysql_pw);
            Statement st = conn.createStatement();
            String sql = "INSERT INTO users (email, user_name, password) VALUES ('" + email + 
                    "','" + username + 
                    "','" + pw + 
                    "')";
            st.executeUpdate(sql);
            
            // Query를 실행했으면 해당 레코드의 아이디값을 불러온다.
            String sqlForDomain = "SELECT id FROM users WHERE email='" + email + "'";
            ResultSet rs = st.executeQuery(sqlForDomain);
            
            String user_id = null;
            
            while(rs.next()) {
            	user_id = rs.getString("id");
            }
            
            System.out.println("New joined user's id value is " + user_id);
            request.setAttribute("user_id", user_id);
            	// 포워드 방식으로 user_id 값을 MakeUserDomain.java로 데이터 전달
            RequestDispatcher dis = request.getRequestDispatcher("user_domain");
            // URL 변경 안됨.
            dis.forward(request, response);
                
            
        } catch (Exception e) {       
            out.println(e);
        }    
	}

}
