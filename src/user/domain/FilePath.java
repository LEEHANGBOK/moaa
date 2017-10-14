package user.domain;

public class FilePath {
	
	String downOrgPath;
	
	String downSprPath;
	
	String upOrgPath;
	
	String upSprPath;
	
	String infoPath;
	
	String loadPath;
	
	
	public FilePath(String user_domain_path){
		
		this.upOrgPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path +"/tmp/Upload/org";
		
		this.upSprPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/tmp/Upload/spr";
		
		this.downOrgPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path +"/tmp/Download/org";
		
		this.downSprPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/tmp/Download/spr";
		
		this.infoPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/log/info";
		
		this.loadPath = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/log/load";
		
	}
	
	
	public String getDownOrgPath() {
		return downOrgPath;
	}
	
	public String getDownSprPath() {
		return downSprPath;
	}
	
	public String getUpOrgPath() {
		return upOrgPath;
	}
	
	public String getUpSprPath() {
		return upSprPath;
	}
	
	public String getInfoPath() {
		return infoPath;
	}
	
	public String getLoadPath() {
		return loadPath;
	}

}
