package drives.dropbox;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.dropbox.core.DbxException;

import drives.dropbox.GetDropboxToken;

/**
 * Servlet implementation class DropboxToken
 */
@WebServlet("/DropboxToken")
public class DropboxToken extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DropboxToken() {
        super();
        // TODO Auto-generated constructor stub
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
		System.out.println("post");
		
		
		
		final String DROP_BOX_APP_KEY = "b0ia3o6jv7sl6en";
		final String DROP_BOX_APP_SECRET = "i2znlitfmke8ugz";
		
		
		String dropbox_ac_code= request.getParameter("dropbox_ac_code");
		System.out.println("Dropbox's access key is : " + dropbox_ac_code);
		
		
		try {
			GetDropboxToken test = new GetDropboxToken();
			String dropbox_token = test.authDropbox(DROP_BOX_APP_KEY, DROP_BOX_APP_SECRET, dropbox_ac_code);
			System.out.println("Dropbox's access token is : " + dropbox_token);
			
			// Token을 DB에 저장
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
	               "', 'dropbox', '" 
	               + dropbox_token + 
	               "')";

	        st.executeUpdate(sql);
			
			response.sendRedirect("dropbox_auth.jsp");    
			
		} catch (DbxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		
		
		
		
		
	}

}
