package drives.box;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import drives.box.GetBoxToken;
/**
 * Servlet implementation class BoxToken
 */
@WebServlet("/BoxToken")
public class BoxToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoxToken() {
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
			GetBoxToken p = new GetBoxToken();
			
			String box_ac_code= request.getParameter("box_ac_code");
			System.out.println("code in BoxToken.java:"+ box_ac_code);
	        Map params = new HashMap();
	        params.put("grant_type", "authorization_code");
	        params.put("code", box_ac_code);  // 코드받아서 넣기
	        params.put("client_id", "inzgx27u8pggvqq6fvih3l1x99qnlwcp");
	        params.put("client_secret","F6tZUdCcPmt7fgozSYgYHi2OkwAPashW");
	        String response_box = p.post("https://api.box.com/oauth2/token", params);
	        JSONParser parser = new JSONParser();
	        Object obj;
			
			obj = parser.parse(response_box);
			
	        JSONObject jsonObj = (JSONObject) obj;
	        
	        String box_token = (String) jsonObj.get("access_token");
	        String box_refresh_token = (String) jsonObj.get("refresh_token");
	        
	        System.out.println("access_token : " + box_token);
	        System.out.println("refresh_token : " + box_refresh_token);
	        
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
	        
	        int session_id = (int) session.getAttribute("key_id");
	        
	        Statement st = conn.createStatement();
	        // DB에 데이터 삽입
	        String sql = "INSERT INTO token (user_id, drive, token) VALUES ('" + session_id + 
	               "', 'box', '" 
	               + box_token + 
	               "')";
	
	        st.executeUpdate(sql);
	
	
	       response.sendRedirect("box_auth.jsp");    
        
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {       
			e.printStackTrace();
		}

	    


		
	}

}
