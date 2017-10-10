<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<html>
 <head>
 <title>mission99</title>
 
 </head>
 <body>
	<form action="fileupload.jsp" method="post"  enctype="multipart/form-data">
		<input type="file" multiple id="file" name="userfile" style="display:inline" >
		<script>
			var curStyle=document.getElementById("file").style.display;
				document.getElementById("file").style.display="none";
		</script>
		<button id="newFile" onclick="check()" class="btn btn-default">올리기</button>
		<script>
	 	function check(){
	 		eventOccur(document.getElementById('file'),'click');
	 	}			 	
	 	function eventOccur(evEle, evType){
 		  if (evEle.fireEvent) {
 		    evEle.fireEvent('on' + evType);
 		  } else {
 		    var mouseEvent = document.createEvent('MouseEvents');
 		    mouseEvent.initEvent(evType, true, false);
 		    var transCheck = evEle.dispatchEvent(mouseEvent);
 		    if (!transCheck) {
 		      console.log("클릭 이벤트 발생 실패!");
 		    }
 		  }
 		}
	 	</script>
 	</form>
 </body>
 </html> 