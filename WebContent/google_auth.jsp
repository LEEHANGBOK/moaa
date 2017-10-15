<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<html>
  <head>
	<title>Google Auth</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
   
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>

<div class="container middle grid">
	<!--Add buttons to initiate auth sequence and sign out-->
    <div class="col-md-6 col-sm-6">
		<a id="authorize-button" style="display: none;" target="_blank"> 
			<img alt="#" src="etc/driveimage/google_image.png" style="height:70pt; width:70pt;"><br>
			인증하기
		</a>
	
		<a id="signout-button" style="display: none;" target="_blank"> 
			<img alt="#" src="etc/driveimage/google_image.png" style="height:70pt; width:70pt;"><br>
			sign out
		</a>
	</div>
	<div class="col-md-6 col-sm-6">
		<text id="content2"></text>
		<a class="ui-shadow ui-btn ui-corner-all btn btn-default btn-drive" id="btn-full" href="javascript:uploadFile2();" style="margin-bottom:10px">토큰받아오기</a>
		<!-- <a class="ui-shadow ui-btn ui-corner-all btn btn-default btn-drive" id="btn-full" href="javascript:checkToken();">토큰확인</a> -->
		 <!-- <form name="google_form" action="google_token" method="post" >
			<input type="hidden" value="" name="google-token">
			<button type="submit" class="btn btn-default btn-drive" id="btn-full">토큰저장</button>
		</form>  -->
	</div>
</div>

    <script type="text/javascript">
      // Client ID and API key from the Developer Console
    var CLIENT_ID = '1020724062873-0nbbfhahaslasmg3nio1gh7hp8kk3pqq.apps.googleusercontent.com';
    
      var CLIENT_SECRET ='ki8Bn9NqJgSSDD4Hg8nb2ZLu';
      // Array of API discovery doc URLs for APIs used by the quickstart
      var DISCOVERY_DOCS = ["https://www.googleapis.com/discovery/v1/apis/drive/v3/rest"];

      // Authorization scopes required by the API; multiple scopes can be
      // included, separated by spaces.
      var SCOPES = 'https://www.googleapis.com/auth/drive';

      var authorizeButton = document.getElementById('authorize-button');
      var signoutButton = document.getElementById('signout-button');
	  var google_token;
	  
	  //추가
	  /* function submit(){
		  document.google_form.google-token.value = google_token;
	  } */
	  
	  function uploadFile2(){
          /* alert("access token : "+gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().access_token+"refresh token : "+gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().refresh_token); */
          /* appendPre2(gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().access_token); */
           //추가
          google_token = gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().access_token
          if(google_token != null){
        	  location.href="google_token?google_token="+google_token;
        	  window.opener.location.relaod(true);
        	  alert("토큰 : " + google_token + "\n토큰을 성공적으로 받아왔습니다!");  
        	  window.close();
          } else {
        	  alert("먼저 인증을 해주세요");
          }
          
      }
	  
	  function checkToken(){
		  if(google_token != null) {
			  alert(google_token);
		  } else {
			  alert("인증을 위한 토큰이 없습니다.");
		  }
		 
	  }
	  
	  

      function appendPre2(message) {
        var pre = document.getElementById('content2');
        var textContent = document.createTextNode(message + '\n');

        pre.appendChild(textContent);
      }

      /**
       *  On load, called to load the auth2 library and API client library.
       */
      function handleClientLoad() {
        gapi.load('client:auth2', initClient);
       
      }

      /**
       *  Initializes the API client library and sets up sign-in state
       *  listeners.
       */
       var GoogleAuth;
       function initClient() {

        gapi.client.init({
          discoveryDocs: DISCOVERY_DOCS,
          clientId: CLIENT_ID,
          scope: SCOPES
        }).then(function () {
          // Listen for sign-in state changes.
          GoogleAuth = gapi.auth2.getAuthInstance();

          gapi.auth2.getAuthInstance().isSignedIn.listen(updateSigninStatus);

          // Handle the initial sign-in state.
          updateSigninStatus(gapi.auth2.getAuthInstance().isSignedIn.get());
          authorizeButton.onclick = handleAuthClick;
          signoutButton.onclick = handleSignoutClick;

        });
      }

      /**
       *  Called when the signed in status changes, to update the UI
       *  appropriately. After a sign-in, the API is called.
       */
      function updateSigninStatus(isSignedIn) {
        if (isSignedIn) {
          authorizeButton.style.display = 'none';
          signoutButton.style.display = 'block';
          //추가
          /* google_token = gapi.auth2.getAuthInstance().currentUser.get().getAuthResponse().access_token; */
          /* alert(google_token);	
 */
        } else {
          authorizeButton.style.display = 'block';
          signoutButton.style.display = 'none';

        }
      }

      /**
       *  Sign in the user upon button click.
       */
      function handleAuthClick(event) {
        gapi.auth2.getAuthInstance().signIn();
      }

      /**
       *  Sign out the user upon button click.
       */
      function handleSignoutClick(event) {
        gapi.auth2.getAuthInstance().signOut();
      }


    </script>

    <script async defer src="https://apis.google.com/js/api.js"
      onload="this.onload=function(){};handleClientLoad()"
      onreadystatechange="if (this.readyState === 'complete') this.onload()">
    </script>
  
  </body>
</html>
