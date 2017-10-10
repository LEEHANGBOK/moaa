<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Dropbox Auth</title>
	<meta name="description" content="sign_in page">
	<meta name="author" content="mission99">
	<link href="assets/css/bootstrap.min.css" rel="stylesheet">
	<link href="assets/css/font-awesome.min.css" rel="stylesheet">
	<link href="assets/css/style.css" rel="stylesheet">

  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
  <link href="assets/css/auth.css" rel="stylesheet">
  <link href="assets/css/sign-in-up.css" rel="stylesheet">
</head>

<body>

<div class="container middle grid">
	<div class="col-md-6 col-sm-6">
		<a href="#" onclick="window.open('https://account.box.com/api/oauth2/authorize?response_type=code&client_id=inzgx27u8pggvqq6fvih3l1x99qnlwcp&redirect_url=https://localhost:8080/box_auth.jsp','popup','width=800,height=500,left=0,top=0,scrollbars=no'); return false;">
			<img alt="#" src="etc/driveimage/box_image.png" style="height:70pt; width:70pt;"><br>
			아이디 연동하기
		</a>
	</div>
	<div class="col-md-6 col-sm-6">
		<form action="box_token" method="post" name="form">
			<label class="input-label">토큰 엑세스 키 입력</label>
			<input type="text" name="dropbox_ac_code" value="" class="form-control">
			<a href="javascript:set();" class="btn btn-default btn-drive" id="btn-full" style="margin-bottom: 10px">1. 코드받기</a>
			<button type="submit" class="btn btn-default btn-drive" id="btn-full">2. 토큰받기</button>
			
		</form>
	</div>
</div>

<script type="text/javascript">
      var Client_ID = "inzgx27u8pggvqq6fvih3l1x99qnlwcp";
      var Client_SECRET="F6tZUdCcPmt7fgozSYgYHi2OkwAPashW";
      var access_token;
      var refresh_token;

      function getParams() {
      // 파라미터가 담길 배열
      var param = new Array();

      // 현재 페이지의 url
      var url = decodeURIComponent(location.href);
      // url이 encodeURIComponent 로 인코딩 되었을때는 다시 디코딩 해준다.
      url = decodeURIComponent(url);

      var params;
      // url에서 '?' 문자 이후의 파라미터 문자열까지 자르기
      params = url.substring( url.indexOf('?')+1, url.length );
      // 파라미터 구분자("&") 로 분리
      params = params.split("&");

      // params 배열을 다시 "=" 구분자로 분리하여 param 배열에 key = value 로 담는다.
      var size = params.length;
      var key, value;
      for(var i=0 ; i < size ; i++) {
          key = params[i].split("=")[0];
          value = params[i].split("=")[1];

          param[key] = value;
      }

      return param;
      }
      function set(){
      var p = getParams();
      var Code = p["code"];
      var f = document.form;
      f.dropbox_ac_code.value = Code;
      
      alert(Code);
      history.replaceState({}, null, location.pathname);
      
      }

</script>




</body>
</html>