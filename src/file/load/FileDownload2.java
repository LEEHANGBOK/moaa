package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import drives.box.BoxDown;
import drives.dropbox.DropboxDown;
import drives.google.GoogleDown;
import file.load.Final;
import file.load.Unzip;
import file.load.ReadLogFile;
import drives.box.BoxDown;
import drives.dropbox.DropboxDown;
import drives.google.GoogleDown;



@WebServlet("/FileDownload")
public class FileDownload2 extends HttpServlet {
	
				// 클라우드별 액세스 토큰
				String google_access_token="ya29.GlzjBLlZ_xVH5f7krPC38DFLMd6fAQLLMxIonPcQGVnzUwYLQKFsbwSRTWPNQCYg3_6xvLkFwkRRAHChbQbfFs1YSW7ZUpq7hg26gSUE4xsq6tGb3nknzNu45ppjoQ";	
				String Box_access_token="DeKSpoY6P0hLOUy8KdyOuTe0hQMAWLDp";	
				String Drop_access_token="j2zjj8cBlkAAAAAAAAAAH4tp4Rf3cKaTik1TTLGapKdgE5c0540w79ZzRnJBU0Ae";
			
				// 클라우드별 로그파일 저장경로
				String google_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Google.txt";	
				String box_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Box.txt";		
				String dropbox_directory = System.getProperty("user.home")+System.getProperty("file.separator")+"mission_temp"+System.getProperty("file.separator")+"logfile_Drop.txt";
		
				// 다운로드된 분할파일들 저장경로 ____ tmp/download/spr/
				String sprDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
					"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")
					+"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"spr"+System.getProperty("file.separator");
			
				// 병합 후 원본파일 저장경로 ____ tmp/download/org/
				String orgDirectory = "C:"+System.getProperty("file.separator")+"Users"+System.getProperty("file.separator")+
					"조민재"+System.getProperty("file.separator")+"Desktop"+System.getProperty("file.separator")+
					"tmp"+System.getProperty("file.separator")+"Download"+System.getProperty("file.separator")+"org"+System.getProperty("file.separator");
		
				// 원본파일 이름
				static String orgFilename="test.pptx";
		
				Final f = new Final();
				ReadLogFile d = new ReadLogFile();
				Unzip unzip = new Unzip();
	
	private static final long serialVersionUID = 1L;
           
    public FileDownload2() {
        super();
        // TODO Auto-generated constructor stub
    }

    	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        		
		//로그파일 받아와서 클라우드별 리스트로 넣기
		ArrayList Google_list = d.readtext(google_directory);		
		ArrayList Box_list = d.readtext(box_directory);		
		ArrayList Dropbox_list = d.readtext(dropbox_directory);

		//다운로드 시작	&	sprDirectory에 다운로한 분할파일들 저장
		GoogleDown google = new GoogleDown(Google_list, google_access_token,sprDirectory);
		google.start();
		BoxDown box = new BoxDown(Box_list, Box_access_token,sprDirectory);	
		box.start();	
		DropboxDown drop = new DropboxDown(Dropbox_list, Drop_access_token,sprDirectory);
		drop.start();
			
			
		ArrayList<Thread> threadList = new ArrayList<Thread>();
		
		threadList.add(box);
        threadList.add(drop);
        threadList.add(google);
  
         // 모든 분할파일들이 spr폴더에 저장될 때까지 기다림.
		try{
			for(int i = 0 ; i < threadList.size() ; i++) {
		           threadList.get(i).join();         
		        }
			System.out.println("______Finish Download______");
			
			// 병합시작
			Unzip.unzip(sprDirectory,f.getOnlyFileName(orgFilename), orgDirectory);
			System.out.println("______Finish Unzip______");
		}catch(Exception e){
			e.printStackTrace();
		}


		// 서버상에 원본 파일이 저장되어있는 경로. (tmp/Download/org/)
		String orgDir = f.getOrgDirectory();
		
		// 원본파일의 이름+확장자
		String fileName = f.getOrgFilename();

		File file = new File(orgDir + System.getProperty("file.separator") + fileName);
		System.out.println("파일명 : " + fileName);

		// 다운로드 시작
		 String mimeType = getServletContext().getMimeType(file.toString());
            if(mimeType == null)
            {
               response.setContentType("application/octet-stream");
            }
            
            // 다운로드용 파일명을 설정
            String downName = null;
            if(request.getHeader("user-agent").indexOf("MSIE") == -1)
            {
               downName = new String(fileName.getBytes("UTF-8"), "8859_1");
            }
            else
            {
               downName = new String(fileName.getBytes("EUC-KR"), "8859_1");
            }
            
            
            response.setHeader("Content-Disposition","attachment;filename=\"" + downName + "\";");
            
            
            FileInputStream fileInputStream = new FileInputStream(file);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            
            byte b [] = new byte[1024];
            int data = 0;
            
            while((data=(fileInputStream.read(b, 0, b.length))) != -1)
            {
               servletOutputStream.write(b, 0, data);
            }
            
            servletOutputStream.flush();
            servletOutputStream.close();
            fileInputStream.close();
	}

}
