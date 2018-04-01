<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../../favicon.ico">
<title>Business Activity Monitoring</title>


<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/mjunction-styles.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="resources/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- Custom styles for this template -->

<link href="resources/css/font-awesome.min.css" rel="stylesheet">
<link href="resources/css/footable.metro.css" rel="stylesheet">
<link href="resources/css/footable-demos.css" rel="stylesheet">
<link href="resources/css/footable.core.css" rel="stylesheet">
<link href="resources/css/remodal-default-theme.css" rel="stylesheet">
<link href="resources/css/remodal.css" rel="stylesheet">

</head>
<body>
	<div class="LoginBg">
		<div class="text-center FTop">
			<img src="resources/img/BamLogo.png" alt="">
		</div>
		<div class="LoginHolder">
			<div class="LoginHolderInner">
				<div class="LoginLogoHolder">
					<img src="resources/img/logoLogin.png">
				</div>
				<!--LoginLogoHolder-->
				<form method="post" id="loginForm" class="loginForm">
					<input type="hidden" id="tokenValueLog" name="tokenValueLog"
						value="">

					<div class="LoginFields">
						<div class="LoginFieldsBlock">
							<div class="col-sm-2">
								<img src="resources/img/user12.png" width="19" height="19">
							</div>
							<div class="col-sm-10">
								<input name="userName" id="userName" type="text"
									placeholder="Username">
							</div>
						</div>

						<div class="LoginFieldsBlock">
							<div class="col-sm-2">
								<img src="resources/img/lock.png" width="21" height="21">
							</div>
							<div class="col-sm-10">
								<input name="password" id="password" type="password"
									placeholder="Password">
							</div>
						</div>
						<div id='loadingmessage'
							style='display: none; text-align: center;'>
							<img src='resources/img/loginLoader.gif' />
						</div>
						<br>
						<div style="color: red" id="loginFailResponse"></div>
						<!-- Login modal -->



						<div class="modal fade" id="myModal" role="dialog">
							<div class="modal-dialog modal-sm">
								<div class="modal-content">
									<div class="modal-header">
										<button type="button" class="close" data-dismiss="modal">&times;</button>
										<h4 class="modal-title">Confirm Re Login</h4>
									</div>
									<div class="modal-body">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<button type="button" id="reLogin" class="btn btn-success">Yes</button>
										&nbsp;&nbsp;
										<button type="button" class="btn btn-danger" id="doNothing">No</button>
									</div>

								</div>
							</div>
						</div>

						<!-- end of modal -->

					</div>
				</form>
				<div class="LoginFieldsButton">
					<div class="col-sm-12">
						<input name="loginButton" id="loginButton" type="submit"
							value="Login">
					</div>

				</div>
				<!--LoginFields-->
				<!-- 				<div class="CancelFieldsButton"> -->
				<!-- 					<div class="col-sm-12"> -->
				<!-- 						<input name="" type="button" value="Cancel"> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
			</div>
			<!--LoginHolderInner-->
		</div>
		<!--LoginHolder-->

	</div>
	<!--LoginBg-->




	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- start : To get the context path -->
	<script>var contextPath = "${pageContext.request.contextPath}"</script>
	<!-- end : to get the context path -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="resources/js/ie10-viewport-bug-workaround.js"></script>
	<script src="resources/js/custom/login.js"></script>
	<script src="resources/js/remodal.js"></script>

<script>

$(document).ready(function(){
	
	contextPath = '${pageContext.request.contextPath}';
	
	console.log('[login.jsp] : contextPath: ' + contextPath);
});


</script>
</body>
</html>
