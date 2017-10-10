package drives.dropbox;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.dropbox.core.v1.DbxAccountInfo;
import com.dropbox.core.DbxAppInfo;
import com.dropbox.core.DbxAuthFinish;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.DbxWebAuthNoRedirect;
import com.dropbox.core.json.JsonReader;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.CreateFolderErrorException;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

public class Test {

	private static final String DROP_BOX_APP_KEY = "b0ia3o6jv7sl6en";
	private static final String DROP_BOX_APP_SECRET = "i2znlitfmke8ugz";
	//private static final String ACCESS_TOKEN = "j2zjj8cBlkAAAAAAAAAAD7Pkg01pVvoCcyVBMaPb6pYToow4OJ6m5wXLZ5GMIAKj";
	private static final String USER_ID = "blahblah";
	//DbxClient client = new DbxClient(ACCESS_TOKEN,null,null);
	
	DbxClientV1 dbxClient;

	public DbxClientV1 authDropbox(String dropBoxAppKey, String dropBoxAppSecret)
			throws IOException, DbxException {
		DbxAppInfo dbxAppInfo = new DbxAppInfo(dropBoxAppKey, dropBoxAppSecret);
		DbxRequestConfig dbxRequestConfig = new DbxRequestConfig("USER_ID/1.0", Locale.getDefault().toString());
		DbxWebAuthNoRedirect dbxWebAuthNoRedirect = new DbxWebAuthNoRedirect(dbxRequestConfig, dbxAppInfo);
		//String authorizeUrl = dbxWebAuthNoRedirect.start();
		//https://www.dropbox.com/oauth2/authorize?locale=ko-KR&response_type=code&client_id=b0ia3o6jv7sl6en
//		System.out.println("1. Authorize: Go to URL and click Allow : " + authorizeUrl);
//		System.out.println("2. Auth Code: Copy authorization code and input here ");
		String dropboxAuthCode = "kFb_ENWtmyUAAAAAAAAAlGJVnjrPLsIRq_2z_2zXrmQ";
//				new BufferedReader(new InputStreamReader(System.in)).readLine().trim();
		DbxAuthFinish authFinish = dbxWebAuthNoRedirect.finish(dropboxAuthCode);
		
		//수정필요
		String authAccessToken = authFinish.getAccessToken();
		JsonReader<DbxAuthFinish> refreshToken = authFinish.Reader;
		
		 System.out.println(refreshToken);
		System.out.println(authAccessToken);
		dbxClient = new DbxClientV1(dbxRequestConfig, authAccessToken);
		

		return dbxClient;
	}

	/* returns Dropbox size in GB */
	public long getDropboxSize() throws DbxException {
		long dropboxSize = 0;
		DbxAccountInfo dbxAccountInfo = dbxClient.getAccountInfo();
		// in GB :)
		dropboxSize = dbxAccountInfo.quota.total / 1024 / 1024 / 1024;
		return dropboxSize;
	}


	public void createFolder(String folderName) throws DbxException {
		dbxClient.createFolder("/" + folderName);
	}

	
	public static void main(String[] args) throws IOException, DbxException {
		Test test = new Test();
		test.authDropbox(DROP_BOX_APP_KEY, DROP_BOX_APP_SECRET);
		//System.out.println("Dropbox Size: " + test.getDropboxSize()+ " GB");
		//test.uploadToDropbox("untitled1.py");
		//javaDropbox.createFolder("tutorial");
		//test.listDropboxFolders("/");
		//test.downloadFromDropbox("America.png");

	}

}
