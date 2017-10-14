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
	
	public void deleteLogInfo(String logInfoPath, String loginfofile) {
		File selectedLogInfoDir = new File(logInfoPath);
		File[] innerLogInfoFiles= selectedLogInfoDir.listFiles(); 
		int dirListLen = innerLogInfoFiles.length;
		System.out.println("Searching for delete info file...");
		for(int i = 0 ; i < dirListLen; i++) {
			String logInfoFileName = innerLogInfoFiles[i].getName();
			if(logInfoFileName.contains(loginfofile)) {
				innerLogInfoFiles[i].delete();
				System.out.println("[Knowing] " + logInfoFileName + "is successfully deleted!");
				break;
			}
		}
	}
	
//	public void deleteLogLoad(String logLoadPath, String logloadfile) {
//		File selectedLogInfoDir = new File(logLoadPath);
//		File[] innerLogInfoFiles= selectedLogInfoDir.listFiles(); 
//		int dirListLen = innerLogInfoFiles.length;
//		System.out.println("Searching for delete load file...");
//		for(int i = 0 ; i < dirListLen; i++) {
//			String logInfoFileName = innerLogInfoFiles[i].getName();
//			if(logInfoFileName.contains(logloadfile)) {
//				innerLogInfoFiles[i].delete();
//				System.out.println("[Knowing] " + logInfoFileName + "is successfully deleted!");
//				break;
//			}
//		}
//	}
	
	public void deleteLogLoad (String logLoadPath) {
		File selectedLogInfoDir = new File(logLoadPath);
		File[] innerLogInfoFiles = selectedLogInfoDir.listFiles();
		
		if(innerLogInfoFiles.length > 0) {
			for (int i=0; i<innerLogInfoFiles.length; i++) {
				if(innerLogInfoFiles[i].isFile()) {
					innerLogInfoFiles[i].delete();
				} else {
					// 재귀
					deleteLogLoad(innerLogInfoFiles[i].getPath());
				}
				innerLogInfoFiles[i].delete();
			}
			selectedLogInfoDir.delete();
		}
	}

}
