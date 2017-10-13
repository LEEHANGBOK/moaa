<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.*"%>
<%@page import="java.sql.Connection" %>
<%@page import="java.sql.DriverManager" %>
<%@page import="java.sql.ResultSet" %>
<%@page import="java.sql.Statement" %>
<%@page import="util.db.MySqlConnection" %>


<%
	String driver = "com.mysql.jdbc.Driver";
	Class.forName(driver);
	
	MySqlConnection mysqlConn = new MySqlConnection();
    
	// 관리자 Login
	String url = mysqlConn.getDBurl();
	String id = mysqlConn.getDBid();
	String pw = mysqlConn.getDBpw();
	
	// 연결
	Connection conn = DriverManager.getConnection(url, id, pw);
	
	int session_id = (int) session.getAttribute("key_id");
	System.out.println("session id : " + session_id);
	Statement st = conn.createStatement();
	
	String sql_token = "SELECT * FROM token WHERE user_id='" + session_id + "'";
	
	ResultSet rs = st.executeQuery(sql_token);

	ArrayList<String> driArrList = new ArrayList<String>();
	
	if(rs.next()){
		do{
			System.out.println("[Knowing] " + rs.getString("drive") + " is authorized");
			driArrList.add(rs.getString("drive"));
		}while(rs.next());

	}else{
	}
	
	Object[] objectList = driArrList.toArray();
	String[] driArr =  Arrays.copyOf(objectList,objectList.length,String[].class);
	
	boolean google_con = Arrays.asList(driArr).contains("google");
	boolean dropbox_con = Arrays.asList(driArr).contains("dropbox");
	boolean box_con = Arrays.asList(driArr).contains("box");
	
	/* System.out.println(google_con + " " + dropbox_con + " " +  box_con); */
	
	/* for(int i=0; i<2; i++){
		System.out.println(driArr[i]);
	} */

	//사용자 이름 세션에 저장
	String user_name = (String) session.getAttribute("user_name");
	// 사용자 아이디 세션에 저장
	String user_email = (String) session.getAttribute("id");
%>


<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <title>MOAA | Dashboard</title>
  <!-- Tell the browser to be responsive to screen width -->
  <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
  <!-- Bootstrap 3.3.7 -->
  <link rel="stylesheet" href="bower_components/bootstrap/dist/css/bootstrap.min.css">
  <!-- Font Awesome -->
  <link rel="stylesheet" href="bower_components/font-awesome/css/font-awesome.min.css">
  <!-- Ionicons -->
  <link rel="stylesheet" href="bower_components/Ionicons/css/ionicons.min.css">
  <!-- Theme style -->
  <link rel="stylesheet" href="dist/css/AdminLTE.min.css">
  <!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
  <link rel="stylesheet" href="dist/css/skins/_all-skins.min.css">

  <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

  <!-- Google Font -->
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">
</head>

<!-- ADD THE CLASS layout-top-nav TO REMOVE THE SIDEBAR. -->
<body class="hold-transition skin-blue layout-top-nav">
<div class="wrapper">

  <header class="main-header">
    <nav class="navbar navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a href="dashboard.jsp" class="navbar-brand"><b>MOAA</b></a>
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar-collapse">
            <i class="fa fa-bars"></i>
          </button>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse pull-left" id="navbar-collapse">
          <ul class="nav navbar-nav">
            <li><a href="dashboard.jsp">Dashboard <span class="sr-only">(current)</span></a></li>
            <li><a href="setting_v2.jsp">Setting</a></li>
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">My Drive <span class="caret"></span></a>
              <ul class="dropdown-menu" role="menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li class="divider"></li>
                <li><a href="#">Separated link</a></li>
                <li class="divider"></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li>
          </ul>
          <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
              <input type="text" class="form-control" id="navbar-search-input" placeholder="Search">
            </div>
          </form>
        </div>
        <!-- /.navbar-collapse -->
        <!-- Navbar Right Menu -->
        <div class="navbar-custom-menu">
          <ul class="nav navbar-nav">
            <li class="dropdown user user-menu">
              <!-- Menu Toggle Button -->
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                <!-- The user image in the navbar-->
                <img src="dist/img/user2-160x160.jpg" class="user-image" alt="User Image">
                <!-- hidden-xs hides the username on small devices so only the image appears. -->
                <span class="hidden-xs"><%= user_name %></span>
              </a>
              <ul class="dropdown-menu">
                <!-- The user image in the menu -->
                <li class="user-header">
                  <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">

                  <p>
                    <%= user_name %>
                    <small>Member since Nov. 2012</small>
                  </p>
                </li>
                <!-- Menu Body -->
                <li class="user-body">
                  <div class="row">
                    <div class="col-xs-4 text-center">
                      <a href="#">Followers</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Sales</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Friends</a>
                    </div>
                  </div>
                  <!-- /.row -->
                </li>
                <!-- Menu Footer-->
                <li class="user-footer">
                  <div class="pull-left">
                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                  </div>
                  <div class="pull-right">
                    <a href="logout_check" class="btn btn-default btn-flat">Sign out</a>
                  </div>
                </li>
              </ul>
            </li>
          </ul>
        </div>
        <!-- /.navbar-custom-menu -->
      </div>
      <!-- /.container-fluid -->
    </nav>
  </header>
  <!-- Full Width Column -->
  <div class="content-wrapper">
    <div class="container">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>
          Setting
        </h1>
        <ol class="breadcrumb">
          <li><a href="dashboard.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
          <li class="active">Setting</li>
        </ol>
      </section>

      <!-- Main content -->
      <section class="content">
       <div class="row">
        <div class="col-md-12" >
          <!-- Custom Tabs -->
          <div class="nav-tabs-custom">
            <ul class="nav nav-tabs">
              <li class="active"><a href="#tab_1" data-toggle="tab"><b>회원 정보</b></a></li>
              <li><a href="#tab_2" data-toggle="tab"><b>드라이브 설정</b></a></li>
            </ul>
            <p>
            <div class="tab-content" style="height:500pt; background-color: #ecf0f5; ">
              <div class="tab-pane active" id="tab_1" style="padding-top:30px;" >
                   <div class="col-md-6">
                   <div class="box box-default" style="height:220pt;">
			          <div class="box-header with-border">
			            <h3 class="box-title">프로필 설정</h3>
			          </div>
			          <div class="box-body">
			          <form>
				          <img src="dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" style="height:100pt; width:100pt;">
				          &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				          <b>이름</b>&nbsp;
				          <%= user_name %>
				          <div style="padding-top:30px; padding-left: 30px;">
					          <p><p>
							  <input type="button" name="modify_profile" onclick="location.href='dashboard.jsp'"class="btn btn-block btn-default" value ="수정" style="width:80pt; height:30pt;">
						  </div>	
			          </form>
			          </div>
			          
			          <!-- /.box-body -->
			          </div>
			        </div>
			        <!-- /.box -->
			        
			        <div class="col-md-6">
                   <div class="box box-default" style="height:220pt;">

			          <div class="box-header with-border">
			            <h3 class="box-title">보안 설정</h3>
			          </div>
			          <div class="box-body">
			            <div class="table-responsive">
			            <p>
			            	<form>
				            	<table class="table no-margin">
					            	<tbody style="padding-top: 100px;">
						            	<tr>
							            	<td width="30%" ><b>이메일</b></td>
							            	<td width="70%"><%= user_email %></td>
						            	</tr>
						            	<tr>
							            	<td width="30%"><b>비밀번호</b></td>
							            	<td width="70%">*********</td>
						            	</tr>
					            	</tbody>
				            	</table>
				            	<div style="padding-top: 80px; padding-left: 30px;">
							        <p><p>
									<input type="button" name="modify_profile" onclick="location.href='dashboard.jsp'"class="btn btn-block btn-default" value ="비밀번호 변경" style="width:90pt; height:30pt;">
				            	</div>
			                </form>
			            </div>
			          </div>
			          <!-- /.box-body -->
			          </div>
			        </div>
			        <!-- /.box -->
			        <div style="color:#A4A4A4; padding-left:30px;">
			          <b>moaa</b>를 더이상 이용하시지 않는다면 <a href="">회원탈퇴 바로가기</a>
			        </div>

              </div>
              <!-- /.tab-pane -->
              <div class="tab-pane" id="tab_2" style="padding-top:30px;">
                <div class="col-md-6">
                   <div class="box box-default" style="height:220pt;">
			          <div class="box-header with-border">
			            <h3 class="box-title">사용 가능한 드라이브 목록</h3>
			          </div>
			          <div class="box-body">
				          <form>
				           	 <small><b>&nbsp;&nbsp;클릭 후 인증</b></small><p>
				           	 <ul id="checkboxTbl" style="list-style-type: none; margin:0; padding:0;">
					           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
						           	 <a href="#" onclick="window.open('google_auth.jsp','popup','width=800,height=500,left=0,top=0,scrollbars=no'); return false;">
						           	 <img alt="#" src="etc/driveimage/google_image.png" style="height:70pt; width:70pt;"></a><p>
						           	 <div align="center" style="color:  #808080">
						           	 	Google Drive
						           	 </div>
					           	 </li>
					           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
					           	 	<a href="#" onclick="window.open('dropbox_auth.jsp','popup','width=800,height=500,left=0,top=0,scrollbars=no'); return false;">
						           	 	<img alt="#" src="etc/driveimage/dropbox_image.png" style="height:70pt; width:70pt;">
						           	</a><p>
						           	<div align="center" style="color:  #808080">
						           	 	Dropbox
						           	</div>
					           	 </li>
					           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
						           	 <a href="#" onclick="window.open('box_auth.jsp','popup','width=800,height=500,left=0,top=0,scrollbars=no'); return false;">
						           	 	<img alt="#" src="etc/driveimage/box_image.png" style="height:70pt; width:70pt;">
						           	 </a><p>
						           	 <div align="center" style="color:  #808080">
						           	 	box
						           	 </div>
					           	 </li>
				           	 </ul>
				           	 <div class="btn-group pull-right" style="padding-top:20px; padding-right:20px;">
			                      <input type="button" class="btn btn-default" value="추가">
			                      <input type="button" class="btn btn-default" onclick="location.href='adddrive.jsp'" value="더 보기">  
		                     </div> 
			           	 </form>
			          </div>
			          
			          <!-- /.box-body -->
			          </div>
			        </div>
			        
			        
			        
			        <!-- /.box -->
			        <div class="col-md-6">
                   		<div class="box box-default" style="height:220pt;">
			          		<div class="box-header with-border">
			            		<h3 class="box-title">인증된 드라이브 목록</h3>
			          		</div>
			          		<div class="box-body">
			          		<%-- <% while(rs_sql.next()) { %> --%>
				          		<form>
						           	 <small><b>&nbsp;&nbsp;클릭하여 활성, 비활성화</b></small><p>
						           	 <ul id="checkboxTbl" style="list-style-type: none; margin:0; padding:0;">
							           	 <%
								        	if(google_con) {
								         %>
							           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
								           	 <a href="#">
								           	 	<img alt="#" src="etc/driveimage/google_image.png" style="height:70pt; width:70pt;">
								           	 </a><p>
								           	 <div align="center" style="color:  #808080">
								           	 	Google Drive
								           	 </div>
							           	 </li>
							           	 <%
							        		 }
								        	 if(dropbox_con) {
							         	 %>
							           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
								           	 <a href="#">
									           	 <img alt="#" src="etc/driveimage/dropbox_image.png" style="height:70pt; width:70pt;">
								           	 </a><p>
								           	 <div align="center" style="color:  #808080">
								           	 	Dropbox
								           	 </div>
							           	 </li>
							           	 <%
							        		 }
								        	 if(box_con) {
							         	 %>
							           	 <li style = "padding-top: 10px; padding-left:30px; padding-right:40px; float:left;">
								           	 <a href="#">
								           	 	<img alt="#" src="etc/driveimage/box_image.png" style="height:70pt; width:70pt;">
								           	 </a><p>
								           	 <div align="center" style="color:  #808080">
								           	 	box
								           	 </div>
							           	 </li>
							           	 <%
							           	 	} 
							           	 %>
						           	 </ul>
				           		</form>
				           		<%-- <% } %> --%>
		          			</div>
			         		<!-- /.box-body -->
			          	</div>
			        </div>
			        <!-- /.box -->
			        <div class="col-md-6">
                   		<div class="box box-default" style="height:220pt;">
			          		<div class="box-header with-border">
			            		<h3 class="box-title">Bandwidth 확인</h3>
			          		</div>
			          		<div class="box-body">
		          			</div>
			         		<!-- /.box-body -->
			          	</div>
			        </div>
			        <!-- /.box -->
			        <div class="col-md-6">
                   <div class="box box-default" style="height:220pt;">
			          <div class="box-header with-border">
			            <h3 class="box-title">추가 옵션 설정</h3>
			          </div>
			          <div class="box-body">
			          
			          </div>
			          <!-- /.box-body -->
			          </div>
			        </div>
			        <!-- /.box -->
              </div>
              <!-- /.tab-pane -->
            </div>
            <!-- /.tab-content -->
          </div>
          <!-- nav-tabs-custom -->
        </div>
        <!-- /.col -->
      </div>
      <!-- /.row -->
      </section>
      <!-- /.content -->
    </div>
    <!-- /.container -->
  </div>
  <!-- /.content-wrapper -->
  <footer class="main-footer">
    <div class="container">
      <div class="pull-right hidden-xs">
        <b>Version</b> 2.4.0
      </div>
      <strong>Copyright &copy; 2014-2016 <a href="https://adminlte.io">Almsaeed Studio</a>.</strong> All rights
      reserved.
    </div>
    <!-- /.container -->
  </footer>
</div>
<!-- ./wrapper -->

<!-- jQuery 3 -->
<script src="bower_components/jquery/dist/jquery.min.js"></script>
<!-- Bootstrap 3.3.7 -->
<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
<!-- SlimScroll -->
<script src="bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
<!-- FastClick -->
<script src="bower_components/fastclick/lib/fastclick.js"></script>
<!-- AdminLTE App -->
<script src="dist/js/adminlte.min.js"></script>
<!-- AdminLTE for demo purposes -->
<script src="dist/js/demo.js"></script>
</body>
</html>
