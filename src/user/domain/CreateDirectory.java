package user.domain;
import java.io.File;

public class CreateDirectory {
	public void makeDir(String path) {
		// This path should be selected by the environment of Server ( CentOS)
		// right before the 'path' will be root directory for user's file system
		// 유저 도메인의 루트
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path).mkdirs();
		// 업로드시 분할된 파일이 임시 저장되는 공간
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Upload/spr").mkdirs();
		// 다운로드시 분할된 파일이 임시 저장되는 공간
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Download/spr").mkdirs();
		// 업로드시 원본 파일이 임시 저장되는 공간
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Upload/org").mkdirs();
		// 다운로드시 원본 파일이 임시 저장되는 공간
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Download/org").mkdirs();
		// 업로드시 대쉬보드에 보이기 위한 파일 정보 생성, 다운로드시 로그파일 삭제
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/log/info").mkdirs();
		// 업로드시 다운로드를 위한 로그파일 생성, 업로드시 로그파일 삭제
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/log/load").mkdirs();
	}
	
	public void mkLogDir(String user_domain_path, String filename) {
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/log/load/" + filename).mkdirs();
	}

}
