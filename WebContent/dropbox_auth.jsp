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
		<!-- <a href="#" onclick="window.open('https://www.dropbox.com/oauth2/authorize?locale=ko-KR&response_type=code&client_id=b0ia3o6jv7sl6en','popup','width=800,height=500,left=0,top=0,scrollbars=no'); return false;" > -->
		<a href="https://www.dropbox.com/oauth2/authorize?locale=ko-KR&response_type=code&client_id=b0ia3o6jv7sl6en" target="_blank">
			<img alt="#" src="etc/driveimage/dropbox_image.png" style="height:70pt; width:70pt;"><br>
			아이디 연동하기
		</a>
	</div>
	<div class="col-md-6 col-sm-6">
		<form action="dropbox_token" method="post">
			<label class="input-label">토큰 엑세스 키 입력</label>
			<input type="text" name="dropbox_ac_code" class="form-control">
			<button type="submit" class="btn btn-default btn-drive" id="btn-full">토큰받기</button>
		</form>
	</div>
</div>



</body>
</html>