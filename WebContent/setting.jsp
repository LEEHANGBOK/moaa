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
  <title>MOAA | Setting</title>
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
  <link rel="shortcut icon" href="assets/ico/favicon.png">

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
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

  <header class="main-header">

			<!-- Logo -->
			<a href="index.jsp" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
				<span class="logo-mini"><b>M</b>oaa</span> <!-- logo for regular state and mobile devices -->
				<span class="logo-lg"><b>MOAA</b></span>
			</a>

			<!-- Header Navbar: style can be found in header.less -->
			<nav class="navbar navbar-static-top">
				<!-- Sidebar toggle button-->
				<a href="#" class="sidebar-toggle" data-toggle="push-menu"
					role="button"> <span class="sr-only">Toggle navigation</span>
				</a>
				<!-- Navbar Right Menu -->
				<div class="navbar-custom-menu">
					<ul class="nav navbar-nav">
						<!-- Messages: style can be found in dropdown.less-->

						<!-- Notifications: style can be found in dropdown.less -->
						<li class="dropdown notifications-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-bell-o"></i> <span class="label label-warning">10</span>
						</a>
							<ul class="dropdown-menu">
								<li class="header">You have 10 notifications</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li><a href="#"> <i class="fa fa-users text-aqua"></i>
												5 new members joined today
										</a></li>
										<li><a href="#"> <i class="fa fa-warning text-yellow"></i>
												Very long description here that may not fit into the page
												and may cause design problems
										</a></li>
										<li><a href="#"> <i class="fa fa-users text-red"></i>
												5 new members joined
										</a></li>
										<li><a href="#"> <i
												class="fa fa-shopping-cart text-green"></i> 25 sales made
										</a></li>
										<li><a href="#"> <i class="fa fa-user text-red"></i>
												You changed your username
										</a></li>
									</ul>
								</li>
								<li class="footer"><a href="#">View all</a></li>
							</ul></li>
						<!-- Tasks: style can be found in dropdown.less -->
						<li class="dropdown tasks-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <i
								class="fa fa-flag-o"></i> <span class="label label-danger">9</span>
						</a>
							<ul class="dropdown-menu">
								<li class="header">You have 9 tasks</li>
								<li>
									<!-- inner menu: contains the actual data -->
									<ul class="menu">
										<li>
											<!-- Task item --> <a href="#">
												<h3>
													Design some buttons <small class="pull-right">20%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-aqua"
														style="width: 20%" role="progressbar" aria-valuenow="20"
														aria-valuemin="0" aria-valuemax="100">
														<span class="sr-only">20% Complete</span>
													</div>
												</div>
										</a>
										</li>
										<!-- end task item -->
										<li>
											<!-- Task item --> <a href="#">
												<h3>
													Create a nice theme <small class="pull-right">40%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-green"
														style="width: 40%" role="progressbar" aria-valuenow="20"
														aria-valuemin="0" aria-valuemax="100">
														<span class="sr-only">40% Complete</span>
													</div>
												</div>
										</a>
										</li>
										<!-- end task item -->
										<li>
											<!-- Task item --> <a href="#">
												<h3>
													Some task I need to do <small class="pull-right">60%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-red"
														style="width: 60%" role="progressbar" aria-valuenow="20"
														aria-valuemin="0" aria-valuemax="100">
														<span class="sr-only">60% Complete</span>
													</div>
												</div>
										</a>
										</li>
										<!-- end task item -->
										<li>
											<!-- Task item --> <a href="#">
												<h3>
													Make beautiful transitions <small class="pull-right">80%</small>
												</h3>
												<div class="progress xs">
													<div class="progress-bar progress-bar-yellow"
														style="width: 80%" role="progressbar" aria-valuenow="20"
														aria-valuemin="0" aria-valuemax="100">
														<span class="sr-only">80% Complete</span>
													</div>
												</div>
										</a>
										</li>
										<!-- end task item -->
									</ul>
								</li>
								<li class="footer"><a href="#">View all tasks</a></li>
							</ul></li>
						<!-- User Account: style can be found in dropdown.less -->
						<li class="dropdown user user-menu"><a href="#"
							class="dropdown-toggle" data-toggle="dropdown"> <img
								src="dist/img/User_IkHwan.jpg" class="user-image"
								alt="User Image"> <span class="hidden-xs"> <%=user_name%>
							</span>
						</a>
							<ul class="dropdown-menu">
								<!-- User image -->
								<li class="user-header"><img
									src="dist/img/User_IkHwan.jpg" class="img-circle"
									alt="User Image">

									<p>
										<%=user_name%>
										<!-- 디비에서 등록 시간 가져오기 -->
										<small>Member since Nov. 2012</small>
									</p></li>
								<!-- Menu Body -->
								<li class="user-body">
									<div class="row">
										<div class="col-xs-4 text-center">
											<a href="setting.jsp">Drives</a>
										</div>
										<div class="col-xs-4 text-center">
											<a href="#">Sales</a>
										</div>
										<div class="col-xs-4 text-center">
											<a href="#">Friends</a>
										</div>
									</div> <!-- /.row -->
								</li>
								<!-- Menu Footer-->
								<li class="user-footer">
									<div class="pull-left">
										<a href="settig.jsp" class="btn btn-default btn-flat">Profile</a>
									</div>
									<div class="pull-right">
										<a href="logout_check" class="btn btn-default btn-flat">Sign
											out</a>
									</div>
								</li>
							</ul></li>
						<!-- Control Sidebar Toggle Button -->
						<li><a href="#" data-toggle="control-sidebar"><i
								class="fa fa-gears"></i></a></li>
					</ul>
				</div>

			</nav>
		</header>
				<!-- Left side column. contains the logo and sidebar -->
		<aside class="main-sidebar">
			<!-- sidebar: style can be found in sidebar.less -->
			<section class="sidebar">
				<!-- Sidebar user panel -->
				<div class="user-panel">
					<div class="pull-left image">
						<img src="dist/img/User_IkHwan.jpg" class="img-circle"
							alt="User Image">
					</div>
					<div class="pull-left info">
						<p><%=user_name%></p>
						<a href="#"><i class="fa fa-circle text-success"></i> Online</a>
					</div>
				</div>
				<!-- search form -->
				<form action="#" method="get" class="sidebar-form">
					<div class="input-group">
						<input type="text" name="q" class="form-control"
							placeholder="Search..."> <span class="input-group-btn">
							<button type="submit" name="search" id="search-btn"
								class="btn btn-flat">
								<i class="fa fa-search"></i>
							</button>
						</span>
					</div>
				</form>
				<!-- /.search form -->
				<!-- sidebar menu: : style can be found in sidebar.less -->
				<ul class="sidebar-menu" data-widget="tree">
					<li class="header">MAIN NAVIGATION</li>
					<li><a href="dashboard.jsp"><i class="fa fa-dashboard"></i><span>
								대쉬보드 </span></a></li>
					<li><a href="setting.jsp"><i class="fa fa-cogs"></i><span>
								설정 </span></a></li>


					<li class="treeview"><a href="#"> <i class="fa fa-cloud"></i>
							<span>내 드라이브</span> <span class="pull-right-container">
								<i class="fa fa-angle-left pull-right"></i>
						</span>
					</a>
						<ul class="treeview-menu">
							<li><a href="pages/tables/simple.html"><i
									class="fa fa-google"></i> Google Drive</a></li>
							<li><a href="pages/tables/data.html"><i
									class="fa fa-dropbox"></i> Dropbox</a></li>
						</ul></li>
				</ul>
			</section>
			<!-- /.sidebar -->
		</aside>
  <!-- Full Width Column -->
  <div class="content-wrapper">
    <div class="container" style="width: 100%">
      <!-- Content Header (Page header) -->
      <section class="content-header">
        <h1>
          환경설정
        </h1>
        <ol class="breadcrumb">
          <li><a href="dashboard.jsp"><i class="fa fa-dashboard"></i> Home</a></li>
          <li class="active">환경설정</li>
        </ol>
      </section>

      <!-- Main content -->
      <section class="content">
       <div class="row">
          <!-- Custom Tabs -->
                   <div class="col-md-6">
                   <div class="box box-default" style="height:220pt;">
			          <div class="box-header with-border">
			            <h3 class="box-title">프로필 설정</h3>
			          </div>
			          <div class="box-body">
			          <form>
				          <img src="dist/img/User_IkHwan.jpg" class="img-circle" alt="User Image" style="height:100pt; width:100pt;">
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
			           	 </form>
			          </div>
			          
			          <!-- /.box-body -->
			          </div>
			        </div>
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
			        <div style="color:#A4A4A4; padding-left:30px;">
			          <b>moaa</b>를 더이상 이용하시지 않는다면 <a href="">회원탈퇴 바로가기</a>
			        </div>                
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
  <aside class="control-sidebar control-sidebar-dark">
			<!-- Create the tabs -->
			<ul class="nav nav-tabs nav-justified control-sidebar-tabs">
				<li><a href="#control-sidebar-home-tab" data-toggle="tab"><i
						class="fa fa-home"></i></a></li>
				<li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i
						class="fa fa-gears"></i></a></li>
			</ul>
			<!-- Tab panes -->
			<div class="tab-content">
				<!-- Home tab content -->
				<div class="tab-pane" id="control-sidebar-home-tab">
					<h3 class="control-sidebar-heading">Recent Activity</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-birthday-cake bg-red"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Langdon's Birthday</h4>

									<p>Will be 23 on April 24th</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-user bg-yellow"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Frodo Updated His
										Profile</h4>

									<p>New phone +1(800)555-1234</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-envelope-o bg-light-blue"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Nora Joined Mailing
										List</h4>

									<p>nora@example.com</p>
								</div>
						</a></li>
						<li><a href="javascript:void(0)"> <i
								class="menu-icon fa fa-file-code-o bg-green"></i>

								<div class="menu-info">
									<h4 class="control-sidebar-subheading">Cron Job 254
										Executed</h4>

									<p>Execution time 5 seconds</p>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

					<h3 class="control-sidebar-heading">Tasks Progress</h3>
					<ul class="control-sidebar-menu">
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Custom Template Design <span
										class="label label-danger pull-right">70%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-danger"
										style="width: 70%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Update Resume <span class="label label-success pull-right">95%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-success"
										style="width: 95%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Laravel Integration <span
										class="label label-warning pull-right">50%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-warning"
										style="width: 50%"></div>
								</div>
						</a></li>
						<li><a href="javascript:void(0)">
								<h4 class="control-sidebar-subheading">
									Back End Framework <span class="label label-primary pull-right">68%</span>
								</h4>

								<div class="progress progress-xxs">
									<div class="progress-bar progress-bar-primary"
										style="width: 68%"></div>
								</div>
						</a></li>
					</ul>
					<!-- /.control-sidebar-menu -->

				</div>
				<!-- /.tab-pane -->

				<!-- Settings tab content -->
				<div class="tab-pane" id="control-sidebar-settings-tab">
					<form method="post">
						<h3 class="control-sidebar-heading">General Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading"> Report panel
								usage <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Some information about this general settings option</p>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Allow mail
								redirect <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Other sets of options are available</p>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Expose author
								name in posts <input type="checkbox" class="pull-right" checked>
							</label>

							<p>Allow the user to show his name in blog posts</p>
						</div>
						<!-- /.form-group -->

						<h3 class="control-sidebar-heading">Chat Settings</h3>

						<div class="form-group">
							<label class="control-sidebar-subheading"> Show me as
								online <input type="checkbox" class="pull-right" checked>
							</label>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Turn off
								notifications <input type="checkbox" class="pull-right">
							</label>
						</div>
						<!-- /.form-group -->

						<div class="form-group">
							<label class="control-sidebar-subheading"> Delete chat
								history <a href="javascript:void(0)" class="text-red pull-right"><i
									class="fa fa-trash-o"></i></a>
							</label>
						</div>
						<!-- /.form-group -->
					</form>
				</div>
				<!-- /.tab-pane -->
			</div>
		</aside>
		<!-- /.control-sidebar -->
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
