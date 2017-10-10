package user.domain;
import java.io.File;

public class CreateDirectory {
	public void makeDir(String path) {
		// This path should be selected by the environment of Server ( CentOS)
		// right before the 'path' will be root directory for user's file system.
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path).mkdirs();
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Upload/spr").mkdirs();
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Download/spr").mkdirs();
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Upload/org").mkdirs();
		new File("/home/andrew/Desktop/Workspace/dirPractice/" + path + "/tmp/Download/org").mkdirs();
	}

}
