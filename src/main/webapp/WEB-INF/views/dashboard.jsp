<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<%-- <meta http-equiv="refresh"	content="<%=session.getMaxInactiveInterval()%>;url=login.jsp" /> --%>
<link rel="icon" href="../../../favicon.ico">
<title>Business Activity Monitoring</title>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">
<link href="resources/css/mjunction-styles.css" rel="stylesheet">
<link href="resources/css/menu.css" rel="stylesheet">
<link rel="stylesheet" href="resources/css/jquery.dataTables.css" />
<link rel="stylesheet" href="resources/css/select2.css" />
<link rel="stylesheet" href="resources/css/jquery-ui-1.9.2.custom.css" />


<style>
.markrow {
	background-color: #d9f6fc !important;
	opacity: 0.8;
}

.asd {
	background: rgba(0, 0, 0, 0);
	border: none;
}

a.wide {
	display: block;
}

#auction_details_table_client_spc tbody tr {
	cursor: pointer;
}

#auction_details_table tbody tr {
	cursor: pointer;
}

#bidderSync_dataTable tbody tr {
	cursor: pointer;
}

#logout {
	cursor: pointer;
}

#exportTotalPdf {
	cursor: pointer;
}

#exportFailedPdf {
	cursor: pointer;
}

#exportSuccessPdf {
	cursor: pointer;
}

#exportUnderPdf {
	cursor: pointer;
}

#exportTotalExcel {
	cursor: pointer;
}

#exportFailedExcel {
	cursor: pointer;
}

#exportSuccessExcel {
	cursor: pointer;
}

#exportUnderExcel {
	cursor: pointer;
}

table.dataTable tbody td {
	word-break: break-word;
	vertical-align: top;
}

.mdmLobButton {
	width: 210px;
	height: 35px;
	background: #2c6cbb;
	padding: 10px;
	text-align: center;
	border-radius: 5px;
	color: white;
	margin-left: 10%;
	
}

.mdmLobButton:hover {
	background-image: none !important;
	background-color: #52677f !important;
}
</style>

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
<link rel="stylesheet" href="resources/css/jquery.jqplot.min.css">

</head>

<body>
	<header>

	<div class="col-sm-2 LogoHolder">
		<img src="resources/img/logo.png">
	</div>
	<div class="col-sm-6 MainHeading">
		<div class="HeadingHolder">Business Activity Monitoring
			Dashboard</div>
		<!--HeadingHolder-->
	</div>
	<!--col-sm-5-->
	<div class="col-sm-4 AccHoldr">
		<div class="AccountDetails">
			<!--<div class="col-sm-1 HomeIcon"> <a href="#"><img src="resources/img/homeicon.png" width="21" height="22"></a></div>
     
      <div class="col-sm-1 HomeIcon"> <a href="#"><img src="resources/img/helpicon.png" width="21" height="22"></a></div>-->

			<div class="col-sm-8 AcD">
				<div class="col-sm-10">
					<div class="AccountDetailsText" id="AccountDetailsText">
						Welcome
						<c:out value="${username}" />
						<!-- 						<a href="#" id="logout"><i class="fa fa-sign-out " -->
						<!-- 							aria-hidden="true"></i></a> -->
						<img alt="" src="resources/img/logout.png" id="logout">



					</div>

					<!--AccountDetailsText-->

				</div>
				<!--col-sm-8-->
				<div class="col-sm-2">
					<div class="UserImage "></div>
					<!--UserImage-->
				</div>
				<!--col-sm-4-->
			</div>
			<!--col-sm-10-->
		</div>
		<!--AccountDetails-->
	</div>
	<!--col-sm-5--> <!--container--> </header>

	<!--RedAlert-->

	<div class="MainContainer">
		<input type="hidden" id="tokenValue" name="tokenValue"
			value="${authtokenhash}">
		<div class="container">
			<div class="col-sm-6 pull-right">

				<div class="dropdown ExportButton pull-right">



					&nbsp;&nbsp;
					<div class="dropdown" style="float: right" id="downloadDiv">

						&nbsp;&nbsp; <a href="DownloadServlet" style="color: white;">Download
							the File</a>
					</div>

					<div class="dropdown" style="float: right">
						<button class="btn btn-primary dropdown-toggle" type="button"
							data-toggle="dropdown" id="pdfButton">
							Export PDF/EXCEL<span class="caret"></span>
						</button>
						<ul class="dropdown-menu">
							<li id="exportTotalPdf">Overall Data Pdf</li>&nbsp;
							<li id="exportFailedPdf">Failed Data Pdf</li>&nbsp;
							<li id="exportSuccessPdf">Successful Data Pdf</li>&nbsp;
							<li id="exportUnderPdf">Underprocessing Data Pdf</li>&nbsp;
							<li id="exportTotalExcel">Overall Data Excel</li>&nbsp;
							<li id="exportFailedExcel">Failed Data Excel</li>&nbsp;
							<li id="exportSuccessExcel">Successful Data Excel</li>&nbsp;
							<li id="exportUnderExcel">Underprocessing Data Excel</li>
						</ul>
					</div>


				</div>
			</div>
			<ul class="nav nav-tabs" id="myTab">
				<li class="active"><a href="#home" data-toggle="tab">Dashboard</a></li>
				<li><a href="#profile" data-toggle="tab">Search</a></li>
				<li><a href="#biderSync" data-toggle="tab">Failed
						BidderSync List</a></li>

			</ul>
			<div id='loadingmessage1' style='display: none; text-align: center;'>
				<img src='resources/img/loginLoader.gif' />
			</div>
			<div id="dialog" style="display: none;" title="Dialog Title">Your
				session is going to expire in 1 min</div>
			<div class="tab-content">
				<div class="tab-pane active" id="home">
					<div class="col-sm-7">
						<div class="upper">
							<div class="col-sm-5 NoPadL">
								<div class="AdminElementBox">

									<div class="col-sm-12 NoPad">
										<div class="col-sm-6 NoPad">
											<div class="AdminYellow AdimVio">
												<a href="#">
													<h2 class="FTop" id="totalActivities"></h2> <!-- 													<input type="text" id="totalActivities"> -->
													<p>Total Activities</p>

												</a>
											</div>
											<!--AdminYellow-->
										</div>
										<div class="col-sm-6 NoPad">
											<div class="AdminYellow AdminBlue">
												<a href="#">
													<h2 class="FTop" id="underProcessing"></h2> <!-- 													<input type="text" id="underProcessing"> -->
													<p>Under Processing</p>


												</a>
											</div>
											<!--AdminYellow-->
										</div>
									</div>
									<div class="col-sm-12 NoPad">
										<div class="col-sm-6 NoPad">
											<div class="AdminYellow AdminRed">
												<a href="#">
													<h2 class="FTop" id="successfulActivities"></h2> <!-- 													<input type="text"  id="successfulActivities"> -->
													<p>Successful Activities</p>
												</a>
											</div>
											<!--AdminYellow-->
										</div>
										<div class="col-sm-6 NoPad">
											<div class="AdminYellow ">
												<a href="#">
													<h2 class="FTop" id="failedActivities"></h2> <!-- 													<input type="text" id="failedActivities"> -->
													<p>Failed Activities</p>
												</a>
											</div>
											<!--AdminYellow-->
										</div>
									</div>
								</div>
							</div>

							<div class="col-sm-7 NoPadR">
								<div class="pieChartFailedSuccess" id="pieChartFailedSuccess"
									style="height: 270px; width: 400px;"></div>
								<!--PiechartHolder-->
							</div>
						</div>

						<div class="lower">
							<div class="PiechartHolder">
								<h3>Activity Graph</h3>
								<!-- 								<img src="resources/img/Graph1.jpg" width="515" height="290"> -->
								<div id="activity_chart"></div>
							</div>
						</div>

					</div>
					<div class="col-md-5">

						<div class="DtTable">
							<table width="100%" id="auction_details_table">

								<thead>
									<tr>
										<th style="text-align: center">Client</th>
										<th style="text-align: center">Activity</th>
										<th style="text-align: center">Date &nbsp;&nbsp;&nbsp;</th>
										<th style="text-align: center">Status</th>
									</tr>
								</thead>

							</table>




						</div>

					</div>

				</div>

				<div class="tab-pane" id="profile">

					<div class="col-sm-3 ">
						<div class="AdminElementBox">

							<div class="col-sm-12 NoPad">
								<div class="col-sm-6 NoPad">
									<div class="AdminYellow AdimVio">
										<a href="#">
											<h2 class="FTop" id="totalActivitiesSearch"></h2>
											<p>Total Activities</p>

										</a>
									</div>
									<!--AdminYellow-->
								</div>
								<div class="col-sm-6 NoPad">
									<div class="AdminYellow AdminBlue">
										<a href="#">
											<h2 class="FTop" id="underProcessingSearch"></h2>

											<p>Under Processing</p>


										</a>
									</div>
									<!--AdminYellow-->
								</div>
							</div>
							<div class="col-sm-12 NoPad">
								<div class="col-sm-6 NoPad">
									<div class="AdminYellow AdminRed">
										<a href="#">
											<h2 class="FTop" id="successfulActivitiesSearch"></h2>

											<p>Successful Activities</p>
										</a>
									</div>
									<!--AdminYellow-->
								</div>
								<div class="col-sm-6 NoPad">
									<div class="AdminYellow ">
										<a href="#">
											<h2 class="FTop" id="failedActivitiesSearch"></h2>
											<p>Failed Activities</p>
										</a>
									</div>
									<!--AdminYellow-->
								</div>
							</div>
						</div>
					</div>


					<div class="col-sm-9">
						<div class="PiechartHolder">
							<h3>Search Here</h3>
							<div class="col-sm-3 NoPad">
								<label>From Date</label>
								<div class="hero-unit">
									<input type="text" placeholder="From Date" id="fromDate"
										name="fromDate">
								</div>
							</div>

							<div class="col-sm-3 NoPad">
								<label>To Date</label>
								<div class="hero-unit">
									<input type="text" placeholder="To Date" id="toDate"
										name="toDate">
								</div>
							</div>


							<div class="col-sm-4 NoPad">
								<label>Client System</label>
								<div class="hero-unit">
									<select id="clientName" name="clientName" type="text"
										placeholder="" style="width: 70%">
										<option value="-1" style="text-align: center">Select
											Client</option>
									</select>
								</div>
							</div>
							<div class="col-sm-12 NoPad margTp35">
								<button type="button" class="btn btn-primary blueBtn"
									id="submitButton">Submit</button>
								<button type="button" class="btn btn-default blueBtn1"
									id="reset">Reset</button>
								<br> <br>
								<div id="htmlError" style="color: red; text-align: center;"></div>
							</div>

						</div>

					</div>
					<!-- Dynamic content starts -->

					<div id="dynaContent">



						<div class="col-sm-7">

							<div class="upper">
								<br> <br>

								<div class="col-sm-7 NoPadR">
									<div class="pieChartFailedSuccess_client_spc"
										id="pieChartFailedSuccess_client_spc"
										style="height: 270px; width: 400px;">

										<!-- 									<img src="resources/img/piechart.png"> -->
										<!-- 								<div id="pieChartFailedSuccess" class="pieChartFailedSuccess" style="height:200px;width:350px; "></div> -->
									</div>
									<!--PiechartHolder-->
								</div>
							</div>

							<div class="lower">
								<div class="PiechartHolder">
									<h3>Activity Graph</h3>
									<!-- 								<img src="resources/img/Graph1.jpg" width="515" height="290"> -->
									<div id="activity_chart_client_spc"
										style="height: 300px; width: 700px;"></div>
								</div>
							</div>

						</div>

						<div class="col-md-5">
							<br> <br>
							<div class="DtTable">
								<table width="100%" id="auction_details_table_client_spc">
									<thead>
										<tr>
											<th style="text-align: center">Client</th>
											<th style="text-align: center">Activity</th>
											<th style="text-align: center">Date &nbsp;&nbsp;&nbsp;</th>
											<th style="text-align: center">Status</th>

										</tr>
									</thead>

								</table>
							</div>

							<!-- /.modal -->



						</div>

					</div>
					<!-- Dynamic content ends -->


				</div>

				<!-- BIdder Sync -->

				<div class="tab-pane" id="biderSync">
					<div class="col-lg-6 col-md-offset-3">

						<div class="DtTable">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								id="bidderSync_dataTable">
								<thead>
									<tr>
										<th style="text-align: center">Client Name</th>
										<th style="text-align: center">File Name</th>
										<th style="text-align: center">Date</th>
										<th style="text-align: center">Time</th>

									</tr>
								</thead>

							</table>
						</div>
					</div>

				</div>

				<!-- End of bidder Sync -->
			</div>

		</div>





	</div>
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeTxt" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Error Stack Trace</h4>
					</div>
					<div class="modal-body">
						<div id='loadingmessage'
							style='display: none; text-align: center;'>
							<img src='resources/img/loginLoader.gif' />
						</div>
						<p>
							<strong>Client Name :</strong> <input type="text" class="asd"
								id="client" style="width: 80%" readonly="readonly">
						</p>
						<p>
							<strong>Activity :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="activity">
						</p>
						<p>
							<strong>Catalogue Code :</strong> <input type="text" class="asd"
								style="width: 70%" readonly="readonly" id="catCode">
						</p>
						<p>
							<strong>Date :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="failedDate">
						</p>
						<form class="form-inline WidthHundred">
							<div class="form-group WidthHundred margBot20px">
								<label for="Reason">Reason : </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea readonly="readonly" id="reason" name="reason"
									class="form-control textArea78"></textarea>
							</div>
						</form>
						<form class="form-inline WidthHundred">
							<div class="form-group WidthHundred margBot20px">
								<label for="Exception">Exception Log : </label>
								<textarea readonly="readonly" id="exceptionLog"
									name="exceptionLog" class="form-control textArea78"></textarea>

							</div>
						</form>



					</div>
					<div class="modal-footer">
						<button type="button" id="modalExcel" class="btn btn-primary">Export
							to Excel</button>
						<!-- <button type="button" class="btn btn-primary">Report
							Error</button> -->
						<button type="button" id="reInitiateButton"
							class="btn btn-primary">Re-Initiate Service</button>
					</div>


				</div>

				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</div>

	<!-- For lob mdm modal -->

	<div class="modal fade" id="myModalMdmLob" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeTxt" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Error Stack Trace</h4>
					</div>
					<div class="modal-body">
						<div id='loadingmessageLob'
							style='display: none; text-align: center;'>
							<img src='resources/img/loginLoader.gif' />
						</div>
						<p>
							<strong>Client Name :</strong> <input type="text" class="asd"
								id="clientMdmLob" style="width: 80%" readonly="readonly">
						</p>
						<p>
							<strong>Activity :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="activityMdmLob">
						</p>
						<p>
							<strong>Catalogue Code :</strong> <input type="text" class="asd"
								style="width: 70%" readonly="readonly" id="catCodeMdmLob">
						</p>
						<p>
							<strong>Date :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="failedDateMdmLob">
						</p>
						<form class="form-inline WidthHundred">
							<div class="form-group WidthHundred margBot20px">
								<label for="Reason">Reason : </label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea readonly="readonly" id="reasonMdmLob"
									name="reasonMdmLob" class="form-control textArea78"></textarea>
							</div>
						</form>
						<form class="form-inline WidthHundred">
							<div class="form-group WidthHundred margBot20px">
								<label for="Exception">Exception Log : </label>
								<textarea readonly="readonly" id="exceptionLogMdmLob"
									name="exceptionLogMdmLob" class="form-control textArea78"></textarea>

							</div>
						</form>



					</div>
					<div class="modal-footer">
						<button type="button" id="modalExcelMdmLob"
							class="btn btn-primary">Export MDM LOB Report</button>

<!-- 						<a href="DownloadservletMdmLob" id="mdmLobLinkButton" -->
<!-- 							class="mdmLobButton" style="color: white; text-decoration: none;">Download -->
<!-- 							MDM LOB Report</a> -->
						<button type="button" id="modalExcelLob" class="btn btn-primary">Export
							to Excel</button>
						<!-- <button type="button" class="btn btn-primary">Report
							Error</button> -->

						<button type="button" id="reInitiateButtonLob"
							class="btn btn-primary">Re-Initiate Service</button>
						<h4 style="float: left" id="noMdmLobDiv"></h4>


					</div>


				</div>

				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</div>

	<!-- under and success modal -->

	<div class="modal fade" id="myModalUS" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeTxt" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Stack Trace</h4>
					</div>
					<div class="modal-body">
						<!-- 											<p> -->
						<!-- 												<strong>Activity :</strong> Catalogue Data to Auction System -->
						<!-- 											</p> -->

						<!-- 						<span class="info">Client -->
						<!-- 							</h4> <input id="client" name="client" type="text" -->
						<!-- 							class="form-control" placeholder="Client" readonly=""> -->
						<p>
							<strong>Client Name :</strong> <input type="text" class="asd"
								id="client1" style="width: 80%" readonly="readonly">
						</p>
						<p>
							<strong>Activity :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="activity1">
						</p>
						<p>
							<strong>Catalogue Code:</strong> <input type="text" class="asd"
								style="width: 70%" readonly="readonly" id="catCode1">
						</p>
						<p>
							<strong>Date :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="failedDate1">
						</p>

					</div>
				</div>

				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</div>


	<div class="modal fade" id="myModalBidder" tabindex="-1" role="dialog"
		aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close closeTxt" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">Bidder Sync Failure</h4>
					</div>
					<div class="modal-body">
						<div id='loadingmessageBidder'
							style='display: none; text-align: center;'>
							<img src='resources/img/loginLoader.gif' />
						</div>
						<p>
							<strong>Client Name :</strong> <input type="text" class="asd"
								id="clientNameBidder" style="width: 80%" readonly="readonly">
						</p>
						<p>
							<strong>File Name :</strong> <input type="text" class="asd"
								style="width: 80%" readonly="readonly" id="fileNameBidder">
						</p>
						<p>
							<strong>Date :</strong> <input type="text" class="asd"
								style="width: 70%" readonly="readonly" id="dateTimeBidder">
						</p>
						<p id="onlyTimeStamp">
							<strong>Time Stamp :</strong> <input type="text" class="asd"
								style="width: 70%" readonly="readonly" id="onlyDateTimeBidder">
						</p>

						<form class="form-inline WidthHundred">
							<button type="button" id="finalXmlBidderSync"
								class="btn btn-primary btn-xs" style="float: left;">Preview
								XML</button>
							<br> <br>

							<div id="xmlViewBidderDiv">
								<p>
									<strong>XML Content </strong>
								</p>

								<div class="form-group WidthHundred margBot20px">


									<textarea readonly="readonly" id="bidderSyncXmlView"
										name="bidderSyncXmlView" class="form-control textArea85"></textarea>
									<br> <br>
									<button type="button" id="hideXmlBidderSync"
										class="btn btn-primary btn-xs" style="float: right;">Hide
										XML</button>
									<br> <br>
								</div>
							</div>

						</form>



					</div>
					<div class="modal-footer">
						<!-- 						<button type="button" id="viewXmlBidderSync" -->
						<!-- 							class="btn btn-primary">Preview XML</button> -->
						<button type="button" id="reInitiateButtonBidder"
							class="btn btn-primary">Re-Initiate Bidder Service</button>


					</div>


				</div>

				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
	</div>


	<!--MainContainer-->

	<footer> Powered by TCG-Digital </footer>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/docs.min.js"></script>

	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->



	<!-----------------------------menu------------------------------->
	<!-- 	<script src="resources/js/bootstrap-datepicker.js"></script> -->

    <!-- start : To get the context path -->
	<script>var contextPath = "${pageContext.request.contextPath}"</script>
	<!-- end : to get the contextr path -->
	<script src="resources/js/custom/dashboard.js"></script>
	<script src="resources/js/jquery.dataTables.js"></script>

	<!-- for graph ploting -->

	<script type="text/javascript" src="resources/js/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="resources/js/jqplot.bubbleRenderer.min.js"></script>
	<script type="text/javascript" src="resources/js/jqplot.json2.min.js"></script>
	<script type="text/javascript" src="resources/js/ts-jqplot-script.js"></script>
	<script type="text/javascript"
		src="resources/js/jqplot.canvasAxisLabelRenderer.min.js"></script>
	<script type="text/javascript"
		src="resources/js/jqplot.categoryAxisRenderer.min.js"></script>
	<script type="text/javascript" src="resources/js/jqplot.pieRenderer.js"></script>
	<script type="text/javascript"
		src="resources/js/jqplot.canvasTextRenderer.min.js"></script>
	<script type="text/javascript" src="resources/js/jqplot.dateAxisRenderer.js"></script>
	<script type="text/javascript" src="resources/js/jqplot.donutRenderer.js"></script>
	<script type="text/javascript"
		src="resources/js/jqplot.enhancedLegendRenderer.js"></script>
	<script type="text/javascript" src="resources/js/select2.js"></script>
	<script type="text/javascript" src="resources/js/custom/search.js"></script>
	<script type="text/javascript" src="resources/js/custom/bidderSync.js"></script>
	<script type="text/javascript" src="resources/js/jquery-ui-1.9.2.custom.js"></script>




	<script type="text/javascript">
		// When the document is ready
		$(document)
				.ready(
						function() {
				
							contextPath = '${pageContext.request.contextPath}';
							
							console.log('[dashboard.jsp] : contextPath: ' + contextPath);
							var d = new Date();
							var monthNames = [ "January", "February", "March",
									"April", "May", "June", "July", "August",
									"September", "October", "November",
									"December" ];
							today = monthNames[d.getMonth()] + ' '
									+ d.getDate() + ' ' + d.getFullYear();

							$('#toDate').attr('disabled', 'disabled');
							$('#fromDate').datepicker({

								showOtherMonths : true,
								selectOtherMonths : true
							});

							$('#fromDate')
									.change(
											function() {
												var from = $('#fromDate')
														.datepicker('getDate');
												var date_diff = Math
														.ceil((from.getTime() - Date
																.parse(today)) / 86400000);
												var maxDate_d = date_diff + 14
														+ 'd';
												date_diff = date_diff + 'd';
												$('#toDate')
														.val('')
														.removeAttr('disabled')
														.removeClass(
																'hasDatepicker')
														.datepicker(
																{

																	minDate : date_diff,
																	maxDate : maxDate_d
																});

											});

							$('#toDate').keyup(function() {
								$(this).val('');
								alert('Please select date from Calendar');
							});
							$('#fromDate').keyup(function() {
								$('#fromDate,#toDate').val('');
								$('#toDate').attr('disabled', 'disabled');
								alert('Please select date from Calendar');
							});

							$('#reset').click(function(e) {

								$('#dynaContent').hide();
								$('#fromDate').val('');
								$('#toDate').val('');
								$('#toDate').val('');

								$("#clientName").select2("val", "-1");
								$('#underProcessingSearch').html('');
								$('#totalActivitiesSearch').html('');
								$('#successfulActivitiesSearch').html('');
								$('#failedActivitiesSearch').html('');

							});

							var href = $('a[data-toggle="tab"]').attr("href");
							//href = href.substring(href.indexOf("#") + 1);
							//alert(href);
							$.ajax({
								type : 'POST',
								url : contextPath+'/tabSession',
								data : {
									tabValue : href

								},

							});
							$('a[data-toggle="tab"]').on('shown.bs.tab',
									function(e) {
										var target = $(e.target).attr("href") // activated tab
										// alert(target);
										$.ajax({
											type : 'POST',
											url : 'tabSession',
											data : {
												tabValue : target

											},

										});
									});

						});
	</script>
	<script>
		function myFunction() {
			$(window).bind('beforeunload', function() {
				return 'Are you sure you want to leave?';
			});
		}
	</script>



	<!-----------------------------menu------------------------------->


</body>


</body>
</html>