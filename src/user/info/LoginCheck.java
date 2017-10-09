package user.info;

import java.sql.*;
import java.io.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginCheck
 */
//@WebServlet("/login")
public class LoginCheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
//	private static final long serialVersionUID = 102831973239L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginCheck() {
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
	        
	        // sql 구사
	         // 전 페이지인 LOGIN.jsp input에 입력한 값들을 변수에 담는다
	         String user_id= request.getParameter("input_id");
	         String user_pw= request.getParameter("input_pw");
	         
	        Statement st = conn.createStatement();
	        // 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
	        String sql = "SELECT * FROM users WHERE email='" + user_id + "' AND password='" + user_pw + "'";

	        ResultSet rs = st.executeQuery(sql);
	        
	        // isLogin 은 로그인 확인 유무를 위한 변수
	        Boolean isLogin = false;

	        
	        // 사용자 email과 부합하는 고유의 id값을 초기화
	        int key_id = 0;
	        String user_name ="";
	        while(rs.next()) {
	            // rs.next가 true 라면 = 정보가 있다
	            isLogin = true;
	            //key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
	            key_id = rs.getInt("id");
	            user_name = rs.getString("user_name");
	        }
	        
	        // Session 처리를 위한 객체 선언 및 세션 할당
	        HttpSession session = request.getSession();

	        // DB에 내가 적은 정보가 있다면
	        if(isLogin) {
	            // 지금 로그인할 id와 pw를 session에 저장하고
	            session.setAttribute("id", user_id);
	            session.setAttribute("pw", user_pw);
	            session.setAttribute("user_name", user_name);
	            session.setAttribute("key_id", key_id);
	            
	            System.out.println("session's user email : " + session.getAttribute("id"));
	            System.out.println("session's user id : " + session.getAttribute("key_id"));
	            System.out.println("session's user name : " + session.getAttribute("user_name"));

	            	// 첫 페이지로 돌려보낸다
	            response.sendRedirect("dashboard.jsp");    
	            
	        } else {
	        	
	        		// 응답하기 위한 출력 스트림 얻어오기
	        	response.setContentType("text/html;charset=utf-8"); 

	        	PrintWriter out = response.getWriter();
	        	
	        	// DB에 내가적은 정보가 없다면 경고창을 띄워준다     
	        	out.println("<script type='text/javascript'>");
	        	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/javascript/login_fail.js");
	        	out.println("</script>");
	        	
	              
	        }
	        
	        
	    } catch (Exception e) {       
	        e.printStackTrace();
	    }
		
		PrintWriter out = response.getWriter();
		String input_id = request.getParameter("input_id");

		out.println("hellworld from servlet");
		out.print(input_id);
		
	}

}
