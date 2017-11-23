<%
    // 현재 로그인된 아이디가 없다면 (= session에 저장된 id가 없다면) 
    if(session.getAttribute("id") == null) {
%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Document</title>

    <meta name="description" content="sign_in page">
    <meta name="author" content="mission99">
    <link href="assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="assets/css/font-awesome.min.css" rel="stylesheet">
    <link href="assets/css/style.css" rel="stylesheet">
    <link href="assets/css/sign-in-up.css" rel="stylesheet">
    <link rel="shortcut icon" href="assets/ico/favicon.png">
    
	<script type="text/javascript">
    
        // 필수 입력정보인 아이디, 비밀번호가 입력되었는지 확인하는 함수
        function checkValue()
        {
            if(!document.userInfo.join_email.value){
                alert("아이디를 입력하세요.");
                return false;
            }
            
            if(!document.userInfo.join_pw.value){
                alert("비밀번호를 입력하세요.");
                return false;
            }
            
            if(!document.userInfo.join_username.value){
            	alert("사용자 이름을 입력하세요.")
            	return false;
            }
            
            // 비밀번호와 비밀번호 확인에 입력된 값이 동일한지 확인
            if(document.userInfo.join_pw.value != document.userInfo.join_cnpw.value ){
                alert("비밀번호를 동일하게 입력하세요.");
                return false;
            }
            
            if(document.userInfo.join_pw.value == document.userInfo.join_cnpw.value 
            		&& document.userInfo.join_email.value != null && document.userInfo.join_username.value != null){
            	alert("mission99에 가입하신 것을 환영합니다!")
            	return true;
            }
        }
    </script>
</head>

<body class="sign-color">
    <header class="sign-header">
        <div class="col-xs-12 col-sm-2 col-md-2 col-lg-3">
            <div id="header-logo">
                <a href="index.jsp">
                    <h2>mission99</h2>
                </a>
            </div>
        </div>
        <div class="col-sm-offset-10 col-md-offset-10 col-lg-offset-9"></div>
    </header>
    <!-- Content -->
    <div id="body-content-up">
        <div class="flex-wrapper">
            <div class="container" style="width:500px">
                <!--<div class="col-xs-12 col-md-8 col-md-offset-2 col-lg-6 col-lg-offset-3 btm-24">-->
                <div class="col-xs-12 card-wrapper panel">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 form-container">
                        <div class="title-wrapper btm-30">
                            <h3 class="title btm-0">Sign up</h3>
                            <p class="signup btm-0">If you have already account<a class="signup-link text-a" href="sign_in.jsp">Sign in</a></p>
                        </div>
                        <p style="margin-top:1em; text-align: center">By creating account, you can enjoy our services by free!</p>
                        <hr/>
                        <form class="form-horizontal" name="userInfo" action="join_check" method="post" onSubmit="return checkValue()">
                            <div class="form-group">
                                <div class="col-sm-12">
                                   <label>Email</label>
                                    <input type="email" name="join_email" class="form-control" id="inputEmail3" placeholder="ex) example@example.com">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                   <label>User Name</label>
                                    <input type="text" name="join_username" class="form-control" id="inputUserName3" placeholder="ex) Hong Gildong">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                   <label>Password</label>
                                    <input type="password" name="join_pw" class="form-control" id="inputPassword3" placeholder="Please enter your password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                   <label>Confirm Password</label>
                                    <input type="password" name="join_cnpw" class="form-control" id="inputPassword3" placeholder="Confirm your password">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-12">
                                    <button type="submit" class="btn btn-default" id="btn-full">Sign up</button>
                                </div>
                            </div>
                        </form>
                        <div class="space"></div>
                        <hr>
                        <div class="social-container">
                            <div class="col-xs-12 col-sm-6 lpad-zero">
                                <a id="facebook-login" class="social-btn fb-blue" href="/login/facebook/?csrfmiddlewaretoken=E6ozhsaqmJXgCx0mHmWcbVkqmKYWAiJy">
                                    <div class="social-icon"><img src="//prezi-a.akamaihd.net/login-versioned/292-f4e2ba409da434ef697fe50a67f5f60b241a0b37//login/img/facebook-icon.svg"></div>
                                    <div class="social-cta">페이스북 계정으로 회원가입</div>
                                </a>
                            </div>
                            <div class="col-xs-12 col-sm-6 rpad-zero btm-12">
                                <a id="linkedin-login" class="social-btn linkedin-blue" href="/login/linkedin/?csrfmiddlewaretoken=E6ozhsaqmJXgCx0mHmWcbVkqmKYWAiJy">
                                    <div class="social-icon"><img src="//prezi-a.akamaihd.net/login-versioned/292-f4e2ba409da434ef697fe50a67f5f60b241a0b37//login/img/linkedin-icon.svg"></div>
                                    <div class="social-cta">링크드인 계정으로 회원가입</div>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>

</html>


<%
    } else {
    	response.sendRedirect("dashboard.jsp");
    }
%>