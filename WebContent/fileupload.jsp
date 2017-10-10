<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.io.PrintWriter"%> 
<%@page import="java.util.Enumeration"%> 
<%@page import="com.oreilly.servlet.*"%> 
<%@page import="com.oreilly.servlet.multipart.*"%> 
<%@page import="file.distribution.Check" %>
<%@page import="file.distribution.Distribution" %>
<%@page import="drives.box.BoxUp" %>
<%@page import="drives.dropbox.DropboxUp" %>
<%@page import="drives.google.GoogleUp" %>
<%@page import="file.load.ReadDir" %>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.Statement" %>
<%@page import="java.io.File"%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
</head>
<body>
<% 
	
String driver = "com.mysql.jdbc.Driver";
Class.forName(driver);

// 관리자 Login
String url = "jdbc:mysql://localhost:3306/moaa";
String id = "root";
String pw = "andrew12345";

// 연결
	Connection conn = DriverManager.getConnection(url, id, pw);
	
	int session_id = (int) session.getAttribute("key_id");
	System.out.println(session_id);
	
	Statement st = conn.createStatement();
	// 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
	String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";
	
	ResultSet rs = st.executeQuery(sql);
	String user_domain_path="";
	String user_name = (String) session.getAttribute("user_name");
	
	while(rs.next()) {
	
		//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
		user_domain_path = rs.getString("domain_path");
	}
	
	
	//절대경로 입력 시 해당 디렉토리에 있는 파일들을 페이지 상에 출력, 클릭 시 다운로드 가능
	String saveDir = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path;
	/* String saveDir = "/home/andrew/Desktop/jpg"; */
	File dir = new File(saveDir);
	File[] fileList = dir.listFiles(); 
	
 	request.setCharacterEncoding("utf-8");
	
	// 사용자가 업로드 버튼을 눌렀을 때 원본 파일이 임시적으로 저장되는 경로
    String dir_org = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
			+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
			+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload"
			+ System.getProperty("file.separator") + "org";
   
    int max = 1024*2*1024*1024-1; // 업로드 파일의 최대 크기 지정: 2GB-1
    String name=""; 			
    String subject=""; 			
    String filename1=""; 		// 업로드 파일 이름(name + subject)
    
    
    // 각 드라이브 토큰 할당 => 디비 드라이버 사용
    String Drop_access_token="";
	String Box_access_token="";
	String google_access_token="";
	
	// 분할된 파일들이 임시적으로 저장될 경로
	String cloudDirectory = System.getProperty("file.separator") + "home"+System.getProperty("file.separator")+"andrew"+System.getProperty("file.separator") +"Desktop"
			+ System.getProperty("file.separator")+"Workspace"+System.getProperty("file.separator")+"dirPractice"+System.getProperty("file.separator")
			+ user_domain_path +System.getProperty("file.separator")+"tmp" + System.getProperty("file.separator") + "Upload" 
			+ System.getProperty("file.separator") + "spr";
     
    try{ 
    	
    		// 사용자가 업로드 버튼을 눌렀을 때 MultipartRequest => 파일을 업로드 시키는 method
		MultipartRequest m = new MultipartRequest(request, dir_org, max, "utf-8", new DefaultFileRenamePolicy());          
		
    		
		Enumeration files=m.getFileNames();  
		String file1 =(String)files.nextElement(); 
		filename1=m.getFilesystemName(file1);
		System.out.println(filename1);
		// '/'를 최종경로 뒤에 붙여줌
		String path = dir+System.getProperty("file.separator");
		
		// 분할 시작
		Distribution d = new Distribution();
		// 원본파일이 지정된 경로에 있는지 없는지 체크
		Check c = new Check(path, filename1);
		c.existenceCheck(path,filename1);
		
		// 위에 메소드가 true일 경우 분할작업 진행
		if(c.showExist()==true){
			long size = c.showSize();
			
			// '3'부분이 나중에 활성화된 드라이브 수에 따라 바뀌도록 되어야 함
			// int isActiveDrive; //= DB drive
			int cloudNumber = 3;
			
			// 분할파일이 임시 저장되어있는 경로 할당
 			String temp =d.showTemp();
			// 확장자를 제외한 오직 파일 이름만 할당
			String onlyName = c.getFileName(filename1);			
			//경로+파일이름(확장자까지)
			String fullPath =path+filename1;					
			
			// Console 확인
			System.out.println(filename1);
			
			// 분할 최종 메소드 distribution(임시저장 경로에 저장할거고, 분할파일을 드라이브 수로 나누고, 
			//나중에 zip 결합을 위해 확장자 빼고 이름만, 임시저장하기 위해 확장자까지)
			d.distribution(temp,size/cloudNumber, onlyName, fullPath, user_domain_path);
			
			
			// 각각의 클라우드에 업로드 시작
			ReadDir readDir = new ReadDir();
			
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
		}
	      	System.out.println(path);            
	    }catch(Exception e){ 
	        e.printStackTrace(new PrintWriter(out)); 
	    }
	%> 
	</body>
</html>