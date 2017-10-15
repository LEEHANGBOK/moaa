
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.io.File"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="util.db.MySqlConnection"%>
<%@page import="file.readwrite.ReadLog"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.*"%>


<%
	request.setCharacterEncoding("UTF-8");
%>

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
	System.out.println("In dashboard, user's session's id : " + session_id);

	Statement st = conn.createStatement();
	// 내가 입려한 id와 pw 값이 DB안에 있는지 확인한다
	String sql = "SELECT domain_path FROM users_domain WHERE users_id='" + session_id + "'";

	ResultSet rs = st.executeQuery(sql);
	String user_domain_path = "";
	String user_name = (String) session.getAttribute("user_name");

	while (rs.next()) {

		//key_id에 디비 값을 저장 : (주의)while문 안에서만 실행된다.
		user_domain_path = rs.getString("domain_path");
	}
	rs.close();
	
	String sql_token = "SELECT * FROM token WHERE user_id='" + session_id + "'";
	
	ResultSet rs_token = st.executeQuery(sql_token);

	ArrayList<String> driArrList = new ArrayList<String>();
	
	if(rs_token.next()){
		do{
			System.out.println("[Knowing] " + rs_token.getString("drive") + " is authorized");
			driArrList.add(rs_token.getString("drive"));
		}while(rs_token.next());

	}else{
	}
	
	Object[] objectList = driArrList.toArray();
	String[] driArr =  Arrays.copyOf(objectList,objectList.length,String[].class);
	
	boolean google_con = Arrays.asList(driArr).contains("google");
	boolean dropbox_con = Arrays.asList(driArr).contains("dropbox");
	boolean box_con = Arrays.asList(driArr).contains("box");

	//절대경로 입력 시 해당 디렉토리에 있는 파일들을 페이지 상에 출력, 클릭 시 다운로드 가능
	String saveDir = "/home/andrew/Desktop/Workspace/dirPractice/" + user_domain_path + "/log/info";
	/* String saveDir = "/home/andrew/Desktop/jpg"; */
	File dir = new File(saveDir);
	File[] fileList = dir.listFiles();
%>

<%
	// 현재 로그인된 아이디가 있다면 (= session에 저장된 id가 있다면)
	if (session.getAttribute("id") != null) {
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>MOAA | Dashboard</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.7 -->
<link rel="stylesheet"
	href="bower_components/bootstrap/dist/css/bootstrap.min.css">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="bower_components/font-awesome/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="bower_components/Ionicons/css/ionicons.min.css">
<!-- jvectormap -->
<link rel="stylesheet"
	href="bower_components/jvectormap/jquery-jvectormap.css">
<!-- Theme style -->
<link rel="stylesheet" href="dist/css/AdminLTE.min.css">
<link rel="stylesheet" href="dist/css/AdminLTE.css">
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
<script
	src="http://cdnjs.cloudflare.com/ajax/libs/raphael/2.1.0/raphael-min.js"></script>
<script src="http://code.jquery.com/jquery-1.8.2.min.js"></script>
<script src="http://cdn.oesmith.co.uk/morris-0.4.1.min.js"></script>

<!-- Morris charts -->
<link rel="stylesheet" href="bower_components/morris.js/morris.css">
<!-- Morris charts -->
<link rel="stylesheet" href="bower_components/morris.js/morris.css">

<!-- Google Font -->
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700,300italic,400italic,600italic">

<style>
#checkboxTbl tr.selected {
	font-weight: bold;
}
</style>
<script src="https://code.jquery.com/jquery-1.10.2.js"></script>
<script>
	$(document)
			.ready(
					function() {
						var tbl = $("#checkboxTbl");
						// 테이블 헤더에 있는 checkbox 클릭시
						$(":checkbox:first", tbl).click(function() {
							// 클릭한 체크박스가 체크상태인지 체크해제상태인지 판단
							if ($(this).is(":checked")) {
								$(":checkbox", tbl).attr("checked", "checked");
							} else {
								$(":checkbox", tbl).removeAttr("checked");
							}

							// 모든 체크박스에 change 이벤트 발생시키기                
							$(":checkbox", tbl).trigger("change");
						});
						// 헤더에 있는 체크박스외 다른 체크박스 클릭시

						$(":checkbox:not(:first)", tbl)
								.click(
										function() {
											var allCnt = $(
													":checkbox:not(:first)",
													tbl).length;
											var checkedCnt = $(
													":checkbox:not(:first)",
													tbl).filter(":checked").length;
											// 전체 체크박스 갯수와 현재 체크된 체크박스 갯수를 비교해서 헤더에 있는 체크박스 체크할지 말지 판단

											if (allCnt == checkedCnt) {
												$(":checkbox:first", tbl).attr(
														"checked", "checked");
											} else {
												$(":checkbox:first", tbl)
														.removeAttr("checked");
											}

										})
								.change(
										function() {
											if ($(this).is(":checked")) {
												// 체크박스의 부모 > 부모 니까 tr 이 되고 tr 에 selected 라는 class 를 추가한다.
												$(this).parent().parent()
														.addClass("selected");
											} else {
												$(this)
														.parent()
														.parent()
														.removeClass("selected");
											}
										});
					});
</script>
<script type="text/javascript"
	src="http://code.jquery.com/jquery-3.2.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		$('.btn_show').click(function() {
			$('.btn_list').show();
			$('.btn_icon').hide();
		});
		$('.btn_hide').click(function() {
			$('.btn_list').hide();
			$('.btn_icon').show();
		});
	});
</script>
</head>
<body class="hold-transition skin-blue sidebar-mini" >
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

		<!-- Content Wrapper. Contains page content -->
		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">
			<!-- Content Header (Page header) -->
			<section class="content-header">
				<h1>
					Dashboard <small>Version 2.0</small>
				</h1>
				<ol class="breadcrumb">
					<li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
					<li class="active">Dashboard</li>
				</ol>
			</section>

			<!-- Main content -->


			<section class="content">
				<!-- Info boxes -->
				<%
		        	if(driArr.length != 0) {
		        %>
				<a href="check_drive_size" class="btn button fa fa-refresh"> 차트 새로고침</a>
				<%
					} else {
				%>
				<div class="col-md-12" style="width:100%; padding-left:0px; padding-right:0px">
					<div class="box box-primary">
						<div class="box-header with-border">
							<div><p><b>인증 드라이브 사용 현황 : </b>아직 인증된 드라이브가 없습니다</p></div>
						</div>
					</div>
				</div>
				<%		
					}
				%>
										
					 <%
						Map<String, String> Gdrives = null;
						Map<String, String> Ddrives = null;
						Map<String, String> Bdrives = null;
							
						
						ArrayList<Object> receivemap = (ArrayList<Object>)request.getAttribute("driveSizes");
						double Gusedsize = 0;
						double Dusedsize = 0;
						double Busedsize = 0;
						
						int igooglesize = 0;
						int idropboxsize = 0;
						int iboxsize = 0;
						
						if(receivemap!=null)	{
						
							Gdrives = (Map<String,String>)receivemap.get(0);
							Ddrives = (Map<String,String>)receivemap.get(1);
							Bdrives = (Map<String,String>)receivemap.get(2);
							
							Gusedsize = Double.parseDouble(Gdrives.get("size"));
							igooglesize = (int) Gusedsize;
							
							Dusedsize = Double.parseDouble(Ddrives.get("size"));
							idropboxsize = (int) Dusedsize;
							
							Busedsize = Double.parseDouble(Bdrives.get("size"));
							iboxsize = (int) Busedsize;
							System.out.println("google used siez : " + Gdrives.get("size")+ " "+Gdrives.get("exp"));
							
							/* googlesize = Gdrives.get("size"); */
							System.out.println("Drop used siez : " + Ddrives.get("size")+ " "+Ddrives.get("exp"));
							System.out.println("Box used siez : " + Bdrives.get("size")+ " "+Bdrives.get("exp"));
							
						} 
						
						if(receivemap != null) {
							/* System.out.println(drives.get("google")); */
							System.out.println("Gusedsize" + Gusedsize);
						} else {
							System.out.println("drives is null!!");
						}
					%>
			
				<div class="row">
				
					<%
			        	if(google_con) {
			         %>
					<div class="col-md-4">
						<div class="box box-primary" style="border-top-color: #f44336">
							<div class="box-header with-border">
								<!-- <h3 class="box-title">Google Drive</h3> -->
								<img alt="#" src="etc/driveimage/google_image.png" style="height:30pt; width:30pt;">
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body chart-responsive">
								<div class="chart" id="donut-GoogleDrive"
									style="height: 250px; position: relative;"></div>

								<script type="text/javascript">
										Morris.Donut({
											
											element : 'donut-GoogleDrive',
											resize : true,
											colors : [ "#4285f4", "#ea4335",
													"#4285f4" ],
											data : [ {
												label : "남은 사용공간 (GB)",
												
												value : <%= 15- Gusedsize %>
											}, {
												label : "사용중인 공간 (GB)",
												value : <%= Gusedsize %>
											} ],
											hideHover : 'auto'
										});
									</script>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<%
		        		}
			        	if(dropbox_con) {
		         	%>
					<div class="col-md-4">
						<div class="box box-primary" style="border-top-color: #9a9a9a">
							<div class="box-header with-border">
								<!-- <h3 class="box-title">Dropbox</h3> -->
								<img alt="#" src="etc/driveimage/dropbox_image.png" style="height:30pt; width:30pt;">
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body chart-responsive">
								<div class="chart" id="donut-Dropbox"
									style="height: 250px; position: relative;"></div>

								<script type="text/javascript">
									Morris.Donut({
										element : 'donut-Dropbox',
										resize : true,
										colors : [ "#4285f4", "#ea4335",
												"#4285f4" ],
										data : [ {
											label : "남은 사용공간 (GB)",
											value : 2-<%= Dusedsize %>
										}, {
											label : "사용중인 공간 (GB)",
											value : <%= Dusedsize %>
										} ],
										hideHover : 'auto'
									});
								</script>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<%
		        		}
			        	if(box_con) {
		         	%>
					<div class="col-md-4">
						<div class="box box-primary" style="border-top-color: #54a0dc">
							<div class="box-header with-border">
								<!-- <h3 class="box-title">Box</h3> -->
								<img alt="#" src="etc/driveimage/box_image.png" style="height:30pt; width:30pt;">
								<div class="box-tools pull-right">
									<button type="button" class="btn btn-box-tool"
										data-widget="collapse">
										<i class="fa fa-minus"></i>
									</button>
									<button type="button" class="btn btn-box-tool"
										data-widget="remove">
										<i class="fa fa-times"></i>
									</button>
								</div>
							</div>
							<div class="box-body chart-responsive">
								<div class="chart" id="donut-Box"
									style="height: 250px; position: relative;"></div>

								<script type="text/javascript">
									Morris.Donut({
										element : 'donut-Box',
										resize : true,
										colors : [ "#4285f4", "#ea4335",
											"#4285f4" ],
										data : [ {
											label : "남은 사용공간 (GB)",
											value : 10-<%= Busedsize %>
										}, {
											label : "사용중인 공간 (GB)",
											value : <%= Busedsize %>
										} ],
										hideHover : 'auto'
									});
								</script>
							</div>
							<!-- /.box-body -->
						</div>
						<!-- /.box -->
					</div>
					<%
		           		} 
		           	%>
				</div>
				<!-- /.row -->
				
				<div class="row">
				<%
		        	if(driArr.length != 0) {
		        %>
				   <div class="col-md-12">
				   <div class="box box-primary" style="border-top-color: #343d44">
				   <div class="box-header with-border">
				      <h3 class="box-title">Total Drive</h3>

		              <div class="progress">
		                <div class="progress-bar progress-bar-primary progress-bar-striped" role="progressbar" aria-valuenow="90" aria-valuemin="0" aria-valuemax="100" 
		                style="width: <%=(Gusedsize + Dusedsize + Busedsize)/27*100%>%">
		                <!-- width 부분 용량 퍼센트 변수 -->
		                  <span class="sr-only"><%=(Gusedsize + Dusedsize + Busedsize)%> Complete (success)</span>
		                  <% System.out.println("Total size percentage: " + (Gusedsize + Dusedsize + Busedsize)/27*100); %>
		                </div>
		              </div>
                     	<div align="right" style="color:  #808080">
                     		<!-- 용량 변수 -->
		                <%= Gusedsize + Dusedsize + Busedsize %> GB / 27 GB
	                	</div>
                    </div>
            <!-- /.box-body -->
          </div>
          <!-- /.box -->
            </div>
            <%
				} else {
			%>
			<div class="col-md-12">
				<div class="box box-primary">
					<div class="box-header with-border">
						<div><p><b>등록된 드라이브 전체용량 : </b>아직 인증된 드라이브가 없습니다</p></div>
					</div>
				</div>
			</div>
			<%		
				}
			%>
            </div>
            <!-- /.row -->
				

				<div class="row">
					<div class="col-md-12">
						<div class="box">
							<div class="box-header with-border">
								<h3 class="box-title"></h3>
								<div class="btn-group" style="float:left; padding-left:3px">
									<h3 class="box-title">파일 목록</h3>
								</div>

								<div class="btn-group pull-right">
									<button type="button" class="btn btn-default btn_show">
										<i class="fa fa-list"></i>
									</button>
									<button type="button" class="btn btn-default btn_hide">
										<i class="fa fa-th"></i>
									</button>
								</div>


							</div>
							<!-- /.box-header -->
							<div class="box-body">
								<div class="btn_list">
									<div class="table-responsive">
										<form action="file_upload" method="post"
											enctype="multipart/form-data">
												<input type="file" multiple id="file" name="userfile"
													style="display: inline" placeholder="파일 선택" />
												<button id="newFile" type="submit" class="btn btn-default">업로드</button>
										</form>

										<form method="post" name="dashForm">
											<div class="button-group" style="position: absolute;left: 325px;top: 65px;">
												<button onClick='mySubmit(1)' class="btn btn-default">다운로드</button>
												<button onClick='mySubmit(2)' class="btn btn-default">파일삭제</button>
											</div><hr>
		
											<script>
												function mySubmit(index) {
													if (index == 1) {
														document.dashForm.action = 'file_download';
													}
													if (index == 2) {
														document.dashForm.action = 'delete_drive_file'
													}

												}
											</script>

											<table id="checkboxTbl" class="table no-margin">
												<thead>
													<tr>
														<th></th>
														<th>종류</th>
														<th>파일명</th>
														<th>타입</th>
														<th>크기</th>
														<th>최근 수정 날짜</th>
													</tr>
												</thead>
												<tbody>

													<%
														ReadLog readLog = new ReadLog();
															//for(File tempFile : fileList   )
															for (int k = 0; k < fileList.length; k++) {
																File tempFile = fileList[k];
																if (tempFile.isFile()) {
																	Map<String, String> fileData = readLog.write(tempFile);
																	String[] dataToSend = new String[fileList.length];
																	dataToSend[k] = fileData.get("name") + "_" + fileData.get("type");
													%>
													<!-- <form action="file_download" method="post" name="form1"> -->
													<tr id="<%=k%>">
														<%
															String tempFileName = tempFile.getName();
																		int j = tempFileName.indexOf(".");
														%>
														<%-- <%= dataToSend[k] %> --%>
														<td width="3%"><input type="checkbox"
															name="file_name"
															value="<%=fileData.get("name")%>_<%=fileData.get("type")%>"
															onclick="check_only(this)" /> <script
																type="text/javascript">
																function check_only(
																		chk) {
																	var obj = document
																			.getElementsByName("file_name");
																	for (var i = 0; i < obj.length; i++) {
																		if (obj[i] != chk) {
																			obj[i].checked = false;
																		}
																	}
																}
															</script></td>
														<td width="5%">
															<%
																String extenstion_1st = fileData.get("type");
																			switch (extenstion_1st) {
																			case "pdf":
															%> <img src="etc/iconimage/icon_pdf.png"
															width="23" height="23"> <%
															 	break;
															 				case "jpg":
															 %> <img src="etc/iconimage/icon_jpg.png" width="23" height="23"> <%
															 	break;
															 				case "png":
															 %> <img src="etc/iconimage/icon_png.png" width="23" height="23"> <%
															 	break;
															 				case "PNG":
															 %> <img src="etc/iconimage/icon_png.png" width="23" height="23"> <%
															 	break;
															 				case "html":
															 %> <img src="etc/iconimage/icon_html.png" width="23" height="23"> <%
															 	break;
															 				case "css":
															 %> <img src="etc/iconimage/icon_css.png" width="23" height="23"> <%
															 	break;
															 				case "xlsx":
															 %> <img src="etc/iconimage/icon_excel.png" width="23" height="23"> <%
															 	break;
															 				case "exe":
															 %> <img src="etc/iconimage/icon_exe.png" width="23" height="23"> <%
															 	break;
															 				case "mp3":
															 %> <img src="etc/iconimage/icon_mp3.png" width="23" height="23"> <%
															 	break;
															 				case "txt":
															 %> <img src="etc/iconimage/icon_txt.png" width="23" height="23"> <%
															 	break;
															 				case "pptx":
															 %> <img src="etc/iconimage/icon_powerpoint.png" width="23" height="23"> <%
															 	break;
															 				case "avi":
															 %> <img src="etc/iconimage/icon_avi.png" width="23" height="23"> <%
															 	break;
															 				case "wmv":
															 %> <img src="etc/iconimage/icon_video.png" width="23" height="23"> <%
															 	break;
															 				case "mp4":
															 %> <img src="etc/iconimage/icon_mp4.png" width="23" height="23"> <%
															 	break;
															 				case "docx":
															 %> <img src="etc/iconimage/icon_word.png" width="23" height="23"> <%
															 	break;
															 				case "war":
															 %> <img src="etc/iconimage/icon_zip.png" width="23" height="23"> <%
															 	break;
															 				case "zip":
															 %> <img src="etc/iconimage/icon_zip.png" width="23" height="23"> <%
															 	break;
															 				default:
															 %> <img src="etc/iconimage/icon_default.png" width="23" height="23"> <%
															 	}
															 %>
														</td>
														<td width="68%">
															<%
																// 이부분 파일에서 파일 이름 가져오기
																			// 파일 이름
																			String filename = fileData.get("name");
																			out.write(filename);
																			/* out.write("<a href=\"" + request.getContextPath() + "/FileDownload?filename=" + java.net.URLEncoder.encode(tempFileName, "UTF-8") + "\">" + tempFileName.substring(0,j) + "</a><br>"); */
															%>
														</td>

														<td width="7%">
															<%
																// 확장자

																			String extenstion = fileData.get("type");
																			out.write(extenstion);

																			/* out.println(tempFileName.substring(j+1)); */
															%>
														</td>
														<td width="7%">
															<%
																/* // 용량
																			// 파일에서 용량 가져오기
																			long length = tempFile.length();
																			double LengthbyUnit = (double)length;
																			int Unit=0;
																			while(LengthbyUnit >1024 && Unit<5){
																			LengthbyUnit = LengthbyUnit/1024;
																			Unit++;
																			}
																			DecimalFormat df = new DecimalFormat("#,##0.00");
																			out.println(df.format(LengthbyUnit));
																			switch(Unit){
																			case 0:
																			out.println("Bytes");
																			break;
																			case 1:
																			out.println("KB");
																			break;
																			case 2:
																			out.println("MB");
																			break;
																			case 3:
																			out.println("GB");
																			break;
																			case 4:
																			out.println("TB");
																			break;
																			} */

																			String fileLength = fileData.get("size");
																			out.write(fileLength);
															%>
														</td>


														<td width="10%">
															<%
																// 업로드 시간
																			/* long lastModified = tempFile.lastModified();
																			Date date = new Date(lastModified);
																			SimpleDateFormat sfd = new SimpleDateFormat("yyyy/MM/dd hh:mm");
																			out.println(sfd.format(date)); */

																			String uploadedTime = fileData.get("time");
																			out.write(uploadedTime);

																		}
																	}
															%>
														</td>
													</tr>
													<!-- </form> -->
												</tbody>
											</table>
										</form>
										<!-- </form> -->
									</div>
								</div>
								<div class="btn_icon" style="display: none;">
								<input type="file" multiple id="file" name="userfile" style="display: inline" placeholder="파일 선택" />
								<button class="btn btn-default">업로드</button>
								<div class="button-group" style="position: absolute;left: 325px;top: 65px;">
									<button class="btn btn-default">다운로드</button>
									<button class="btn btn-default">파일삭제</button>
								</div><hr>
									<form>
										<ul id="checkboxTbl"
											style="list-style-type: none; margin: 0; padding: 0;">
											<%
												for (File tempFile : fileList) {
														if (tempFile.isFile()) {
															Map<String, String> fileData = readLog.write(tempFile);
											%>
											<li
												style="padding-top: 30px; padding-left: 30px; padding-right: 50px; float: left;">
												<input type="checkbox" name="selectfile" value="1" />
											<p>
													<%
														String extenstion_1st = fileData.get("type");
																	switch (extenstion_1st) {
																	case "pdf":
													%>
													<img src="etc/iconimage/icon_pdf.png" width="120"
														height="120">
													<%
														break;
																	case "jpg":
													%>
													<img src="etc/iconimage/icon_jpg.png" width="120"
														height="120">
													<%
														break;
																	case "png":
													%>
													<img src="etc/iconimage/icon_png.png" width="120"
														height="120">
													<%
														break;
																	case "PNG":
													%>
													<img src="etc/iconimage/icon_png.png" width="120"
														height="120">
													<%
														break;
																	case "html":
													%>
													<img src="etc/iconimage/icon_html.png" width="120"
														height="120">
													<%
														break;
																	case "css":
													%>
													<img src="etc/iconimage/icon_css.png" width="120"
														height="120">
													<%
														break;
																	case "xlsx":
													%>
													<img src="etc/iconimage/icon_excel.png" width="120"
														height="120">
													<%
														break;
																	case "exe":
													%>
													<img src="etc/iconimage/icon_exe.png" width="120"
														height="120">
													<%
														break;
																	case "mp3":
													%>
													<img src="etc/iconimage/icon_mp3.png" width="120"
														height="120">
													<%
														break;
																	case "txt":
													%>
													<img src="etc/iconimage/icon_txt.png" width="120"
														height="120">
													<%
														break;
																	case "pptx":
													%>
													<img src="etc/iconimage/icon_powerpoint.png" width="120"
														height="120">
													<%
														break;
																	case "avi":
													%>
													<img src="etc/iconimage/icon_avi.png" width="120"
														height="120">
													<%
														break;
																	case "wmv":
													%>
													<img src="etc/iconimage/icon_video.png" width="120"
														height="120">
													<%
														break;
																	case "mp4":
													%>
													<img src="etc/iconimage/icon_mp4.png" width="120"
														height="120">
													<%
														break;
																	case "docx":
													%>
													<img src="etc/iconimage/icon_word.png" width="120"
														height="120">
													<%
														break;
																	case "war":
													%>
													<img src="etc/iconimage/icon_zip.png" width="120"
														height="120">
													<%
														break;
																	case "zip":
													%>
													<img src="etc/iconimage/icon_zip.png" width="120"
														height="120">
													<%
														break;
																	default:
													%>
													<img src="etc/iconimage/icon_default.png" width="120"
														height="120">
													<%
														}
													%>
												
												<p>
												<div align="center">
													<%
														String filename = fileData.get("name");
																	out.write(filename);
													%>
												</div>
												<p>
												<div align="center" style="color: #808080">
													<%
														String fileLength = fileData.get("size");
																	out.write(fileLength);
													%>
												</div>
											</li>
											<%
												}
													}
											%>
										</ul>
									</form>
								</div>
							</div>
							<!-- ./box-body -->
						</div>
						<!-- /.box -->
					</div>
					<!-- /.col -->
				</div>
				<!-- /.row -->
			</section>
			<!-- /.content -->
		</div>
		<!-- /.content-wrapper -->

		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 2.4.0
			</div>
			<strong>Copyright &copy; 2014-2016 <a
				href="https://adminlte.io">Almsaeed Studio</a>.
			</strong> All rights reserved.
		</footer>

		<!-- Control Sidebar -->
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
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>

	</div>
	<!-- ./wrapper -->
	
	

	<!-- jQuery 3 -->
	<script src="bower_components/jquery/dist/jquery.min.js"></script>
	<!-- Bootstrap 3.3.7 -->
	<script src="bower_components/bootstrap/dist/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="bower_components/fastclick/lib/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="dist/js/adminlte.min.js"></script>
	<!-- Sparkline -->
	<script
		src="bower_components/jquery-sparkline/dist/jquery.sparkline.min.js"></script>
	<!-- jvectormap  -->
	<script src="plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
	<script src="plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
	<!-- SlimScroll -->
	<script
		src="bower_components/jquery-slimscroll/jquery.slimscroll.min.js"></script>
	<!-- ChartJS -->
	<script src="bower_components/Chart.js/Chart.js"></script>
	<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
	<script src="dist/js/pages/dashboard2.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="dist/js/demo.js"></script>
	
	<script >
	/* 
	$(document).ready(function(){
		location.href='check_drive_size'
	});s */
	

	</script>
</body>
</html>

<%
	}
	// 현재 로그인된 아이디가 없다면 (= session에 저장된 id가 없다면)
	else {
		response.sendRedirect("sign_in.jsp");
	}
%>

