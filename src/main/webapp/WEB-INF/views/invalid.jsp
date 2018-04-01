<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="LoginBg">
		<div class="text-center FTop">
			<img src="resources/img/BamLogo.png" alt="">
		</div>
		<div class="LoginHolder">
			<div>
				<h3>Not a authorized user</h3>
			</div>
			<br>
			<div>
				<a href="login.jsp">Re Login</a>
			</div>
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

</body>
</html>