package util.db;


public class MySqlConnection {
	
	
	private String mysqlurl;
	private String mysqlid;
	private String mysqlpw;

	
	public MySqlConnection(){

		// 관리자 Login
		// import시 수정해야할 부분
        mysqlurl = "jdbc:mysql://localhost:3306/moaa";
        mysqlid = "root";
        mysqlpw = "andrew12345";

	}	

	public String getDBurl() {
		return mysqlurl;
	}
	public String getDBid() {
		return mysqlid;
	}
	public String getDBpw() {
		return mysqlpw;
	}

}
