<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="false"%>


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<!--TO DO : To make it configurable to give the name of the corresponding user using spring message or cache  -->
<title>Sirish</title>
<!-- Tell the browser to be responsive to screen width -->
<meta
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"
	name="viewport">
<!-- Bootstrap 3.3.6 -->
<link href="<c:url value="resources/bootstrap/css/bootstrap.min.css" />"
	rel="stylesheet">
<!-- Font Awesome -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">
<!-- Ionicons -->
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">
<!-- Theme style -->
<link href="<c:url value="resources/dist/css/AdminLTE.min.css" />"
	rel="stylesheet">
<!-- AdminLTE Skins. Choose a skin from the css/skins
       folder instead of downloading all of them to reduce the load. -->
<link
	href="<c:url value="resources/dist/css/skins/_all-skins.min.css" />"
	rel="stylesheet">
	
<!-- Bootstrap 3.3.6 -->
<link rel="stylesheet" href="resources/bootstrap/css/bootstrap.min.css">
    <!-- DataTables -->
<link rel="stylesheet" href="resources/plugins/datatables/dataTables.bootstrap.css">	

<!-- iCheck for checkboxes and radio inputs -->
  <link rel="stylesheet" href="resources/plugins/iCheck/all.css">
	

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->

<!-- jQuery 2.2.3 -->
	<script src="resources/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- DataTables -->
<script src="resources/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="resources/plugins/datatables/dataTables.bootstrap.min.js"></script>
<!-- iCheck 1.0.1 -->
<script src="resources/plugins/iCheck/icheck.min.js"></script>

<style>
.boxStyle {
	width: 22% !important;
	margin-top: 26%;
}

/* .profile-user-img {
    margin: 0 auto;
    width: 100px;
    padding: 3px;
    border: 3px solid #d2d6de;
    margin-left: 0%;
	}
.profile-username {
    font-size: 21px;
    margin-top: 5px;
    margin-left: -89%;
	}
.text-center {
    text-align: center;
    margin-left: -89%;
} */
/* .box-body {
    background-image: url(user40-128x128.jpg);
    background-repeat: no-repeat;
} */
</style>
</head>
<!-- Left menu starts -->

<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">

		<!-- Top menu remove from here -->

		<jsp:include page="/WEB-INF/views/topMenu.jsp" />

		<!-- Left side column. contains the logo and sidebar -->

		<!-- Removed left bar from here -->

		<jsp:include page="/WEB-INF/views/leftMenu.jsp" />

		<!-- Content Wrapper. Contains page content -->
		<div class="content-wrapper">

			<%-- <jsp:include page="/WEB-INF/views/userProfile.jsp" /> --%>

			<jsp:include page="/WEB-INF/views/templeSadhnaManagement.jsp" />

		</div>



		<!-- User Profile removved from here -->
		<!-- /.content-wrapper -->
		<footer class="main-footer">
			<div class="pull-right hidden-xs">
				<b>Version</b> 2.3.8
			</div>
			<strong>Copyright &copy; 2014-2016 <a
				href="http://almsaeedstudio.com">Almsaeed Studio</a>.
			</strong> All rights reserved.
		</footer>

		<!-- removed from heress -->
		<!-- /.control-sidebar -->
		<!-- Add the sidebar's background. This div must be placed
       immediately after the control sidebar -->
		<div class="control-sidebar-bg"></div>
	</div>
	<!-- ./wrapper -->

	<!-- <script type="text/javascript"> -->
	<script>
	  $(function () {
	   // $("#example1").DataTable();
	    $('#example2').DataTable({
	      "paging": true,
	      "lengthChange": false,
	      "searching": false,
	      "ordering": true,
	      "info": true,
	      "autoWidth": false
	    });
	    
	  //Flat red color scheme for iCheck
	    $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
	      checkboxClass: 'icheckbox_flat-green',
	     // radioClass: 'iradio_flat-green'
	    });
	  
	  });
	</script>
	<!-- </script> -->


	
	<!-- Bootstrap 3.3.6 -->
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>
	<!-- FastClick -->
	<script src="resources/plugins/fastclick/fastclick.js"></script>
	<!-- AdminLTE App -->
	<script src="resources/dist/js/app.min.js"></script>
	<!-- AdminLTE for demo purposes -->
	<script src="resources/dist/js/demo.js"></script>
</body>
</html>
