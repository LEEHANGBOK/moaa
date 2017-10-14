package file.delete;

import java.io.File;

public class FileDelete {
	
	
	// filename => 확장자 포함
	public void deleteOrgDir(String oprTmpPath, String filename) {
		File selectedOrgDir = new File(oprTmpPath);
		File[] innerOrgFiles= selectedOrgDir.listFiles(); 
		int dirOrgListLen = innerOrgFiles.length;
		int targetOrgFileIndex=0;
		for(int i = 0 ; i < dirOrgListLen; i++) {
			if(innerOrgFiles[i].getName() == filename) {
				targetOrgFileIndex = i;
				break;
			}
		}
		
		if(innerOrgFiles[targetOrgFileIndex].exists()) {
			innerOrgFiles[targetOrgFileIndex].delete();
			System.out.println("[Knowing] After separating original file, the original file is successfully deleted!");
		} else {
			System.out.println("[Warning] There is no that such original file!");
		}
		
	}
	
	
	public void deleteSprDir(String sprTmpPath, String onlyName) {
	
		File selectedSprDir = new File(sprTmpPath);
		File[] innerSprFiles= selectedSprDir.listFiles(); 
		int dirListLen = innerSprFiles.length;
		for(int i = 0 ; i < dirListLen; i++) {
			String sprFileName = innerSprFiles[i].getName();
			if(sprFileName.contains(onlyName)) {
				innerSprFiles[i].delete();
				System.out.println("[Knowing] " + sprFileName + "is successfully deleted!");
			}
		}
		if(innerSprFiles.length == dirListLen) {
			System.out.println("[Warning] There were no that such separated files!");
		}
	}

}
