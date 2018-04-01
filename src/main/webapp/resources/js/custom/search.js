$(document)
		.ready(
				function() {

					$('#dynaContent').hide();
					$("#clientName").select2();
					$('#downloadDiv').hide();
					// $('#mdmLobLinkButton').hide();
					$.ajax({
						type : 'GET',
						url : contextPath+'/clientName',
						success : function(data) {
							var select = $('#clientName');
							select.find('option').remove();
							$('<option>').val("-1").text("Select Client Name")
									.appendTo(select);
							$.each(data, function(index, value) {
								$('<option>').val(value).text(value).appendTo(
										select);
							});
						},
						error : function(data) {
							alert("No Client Found");
						}
					});
					$('#submitButton')
							.click(
									function() {
										$('#loadingmessage1').show();
										$('#dynaContent').show();
										$('#htmlError').hide();
										var fromDate = $('#fromDate').val();
										var toDate = $('#toDate').val();
										$('#toDate1').val(fromDate);
										$('#fromDate1').val(toDate);

										var clientName = $('#clientName').val();
										var tokenValue = $('#tokenValue').val();

										if (fromDate != '' && toDate != ''
												&& clientName !== ''
												&& clientName !== '-1') {
											$
													.ajax({
														type : 'POST',
														url : contextPath+'/badgePopulation',
														data : {
															tokenValue : $(
																	'#tokenValue')
																	.val(),
															clientName : $(
																	'#clientName')
																	.val(),
															fromDate : $(
																	'#fromDate')
																	.val(),
															toDate : $(
																	'#toDate')
																	.val()
														},
														success : function(data) {

															$(
																	'#totalActivitiesSearch')
																	.html(
																			data.totalActivities);
															$(
																	'#underProcessingSearch')
																	.html(
																			data.underProcessing);
															$(
																	'#successfulActivitiesSearch')
																	.html(
																			data.successfulActivities);
															$(
																	'#failedActivitiesSearch')
																	.html(
																			data.failedActivities);

															populateDatatableClientSpc();
															populatePiechart();
															pouplateGraphChart();
														},
														error : function(data) {
															if (data.status === 402) {
																$(
																		'#loginFailResponse')
																		.text(
																				"Username or Password is incorrect");
															}
														}
													});
										} else {

											$('#htmlError').show();
											$('#htmlError').text(
													'Please fill all values');
											$('#dynaContent').hide();
											return false;
										}
									});

					var populateDatatableClientSpc = function() {
						$('#auction_details_table_client_spc')
								.dataTable(
										{

											"bProcessing" : false,
											"bAutoWidth" : true,
											"bDestroy" : true,
											"serverSide" : true,
											"sAjaxSource" : contextPath+'/populateClientSpcParticularOverAllActivity',
											"iDisplayLength" : 10,
											"fixedHeader" : true,
											"bJQueryUI" : false,

											"columns" : [ {
												width : '10px'
											}, {
												width : '40px'
											}, {
												width : '30px'
											}, {
												width : '10px'
											}

											],
											"aoColumns" : [
													{
														"mData" : "client"
													},
													{
														"mData" : "activity"
													},
													{
														"mData" : "recordDate",

													},

													{
														"mData" : "status",
														"mRender" : function(
																data, type, row) {
															return '<img src="'
																	+ data
																	+ '" />';
														}
													}, ],
											"aaSorting" : [ [ 2, 'desc' ] ],

											"fnServerParams" : function(aoData) {
												aoData.push({
													"name" : "tokenValue",
													"value" : $('#tokenValue')
															.val()
												}, {
													"name" : "clientName",
													"value" : $('#clientName')
															.val()
												}, {
													"name" : "fromDate",

													"value" : $('#fromDate')
															.val()
												}, {
													"name" : "toDate",

													"value" : $('#toDate')
															.val()
												});
											},

										});

					};

					$('#totalActivitiesSearch')
							.click(
									function(data) {

										var populateDatatable = function() {
											$(
													'#auction_details_table_client_spc')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : true,
																"bDestroy" : true,
																"bJQueryUI" : false,
																"sAjaxSource" : contextPath+'/populateParticularClientSpcTotalActivity',
																"iDisplayLength" : 10,

																"columns" : [
																		{
																			width : '10px'
																		},
																		{
																			width : '40px'
																		},
																		{
																			width : '30px'
																		},
																		{
																			width : '10px'
																		}

																],
																"aoColumns" : [
																		{
																			"mData" : "client"
																		},
																		{
																			"mData" : "activity"
																		},
																		{
																			"mData" : "recordDate"
																		},

																		{
																			"mData" : "status",
																			"mRender" : function(
																					data,
																					type,
																					row) {
																				return '<img src="'
																						+ data
																						+ '" />';
																			}
																		}, ],
																"aaSorting" : [ [
																		2,
																		'desc' ] ],
																"fnServerParams" : function(
																		aoData) {
																	aoData
																			.push(
																					{
																						"name" : "tokenValue",
																						"value" : $(
																								'#tokenValue')
																								.val()
																					},
																					{
																						"name" : "clientName",
																						"value" : $(
																								'#clientName')
																								.val()
																					},
																					{
																						"name" : "fromDate",

																						"value" : $(
																								'#fromDate')
																								.val()
																					},
																					{
																						"name" : "toDate",

																						"value" : $(
																								'#toDate')
																								.val()
																					});
																}
															});
										};
										populateDatatable();

									});

					$('#underProcessingSearch')
							.click(
									function(data) {

										var populateDatatable = function() {
											$(
													'#auction_details_table_client_spc')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : true,
																"bDestroy" : true,
																"bJQueryUI" : false,
																"sAjaxSource" : contextPath+'/populateParticularClientSpcUnderActivity',
																"iDisplayLength" : 10,

																"columns" : [
																		{
																			width : '10px'
																		},
																		{
																			width : '40px'
																		},
																		{
																			width : '30px'
																		},
																		{
																			width : '10px'
																		}

																],
																"aoColumns" : [
																		{
																			"mData" : "client"
																		},
																		{
																			"mData" : "activity"
																		},
																		{
																			"mData" : "recordDate"
																		},

																		{
																			"mData" : "status",
																			"mRender" : function(
																					data,
																					type,
																					row) {
																				return '<img src="'
																						+ data
																						+ '" />';
																			}
																		}, ],
																"aaSorting" : [ [
																		2,
																		'desc' ] ],
																"fnServerParams" : function(
																		aoData) {
																	aoData
																			.push(
																					{
																						"name" : "tokenValue",
																						"value" : $(
																								'#tokenValue')
																								.val()
																					},
																					{
																						"name" : "clientName",
																						"value" : $(
																								'#clientName')
																								.val()
																					},
																					{
																						"name" : "fromDate",

																						"value" : $(
																								'#fromDate')
																								.val()
																					},
																					{
																						"name" : "toDate",

																						"value" : $(
																								'#toDate')
																								.val()
																					});
																}
															});
										};
										populateDatatable();
									});
					$('#successfulActivitiesSearch')
							.click(
									function(data) {

										var populateDatatable = function() {
											$(
													'#auction_details_table_client_spc')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : true,
																"bDestroy" : true,
																"bJQueryUI" : false,
																"sAjaxSource" : contextPath+'/populateParticularClientSpcSuccessfulActivity',
																"iDisplayLength" : 10,

																"columns" : [
																		{
																			width : '10px'
																		},
																		{
																			width : '40px'
																		},
																		{
																			width : '30px'
																		},
																		{
																			width : '10px'
																		}

																],
																"aoColumns" : [
																		{
																			"mData" : "client"
																		},
																		{
																			"mData" : "activity"
																		},
																		{
																			"mData" : "recordDate"
																		},

																		{
																			"mData" : "status",
																			"mRender" : function(
																					data,
																					type,
																					row) {
																				return '<img src="'
																						+ data
																						+ '" />';
																			}
																		}, ],
																"aaSorting" : [ [
																		2,
																		'desc' ] ],
																"fnServerParams" : function(
																		aoData) {
																	aoData
																			.push(
																					{
																						"name" : "tokenValue",
																						"value" : $(
																								'#tokenValue')
																								.val()
																					},
																					{
																						"name" : "clientName",
																						"value" : $(
																								'#clientName')
																								.val()
																					},
																					{
																						"name" : "fromDate",

																						"value" : $(
																								'#fromDate')
																								.val()
																					},
																					{
																						"name" : "toDate",

																						"value" : $(
																								'#toDate')
																								.val()
																					});
																}
															});
										};
										populateDatatable();
									});
					$('#failedActivitiesSearch')
							.click(
									function(data) {

										var populateDatatable = function() {
											$(
													'#auction_details_table_client_spc')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : true,
																"bDestroy" : true,
																"bJQueryUI" : false,
																"sAjaxSource" : contextPath+'/populateParticularClientSpcFailedActivity',
																"iDisplayLength" : 10,

																"columns" : [
																		{
																			width : '10px'
																		},
																		{
																			width : '40px'
																		},
																		{
																			width : '30px'
																		},
																		{
																			width : '10px'
																		}

																],
																"aoColumns" : [
																		{
																			"mData" : "client"
																		},
																		{
																			"mData" : "activity"
																		},
																		{
																			"mData" : "recordDate"
																		},

																		{
																			"mData" : "status",
																			"mRender" : function(
																					data,
																					type,
																					row) {
																				return '<img src="'
																						+ data
																						+ '" />';
																			}
																		}, ],
																"aaSorting" : [ [
																		2,
																		'desc' ] ],
																"fnServerParams" : function(
																		aoData) {
																	aoData
																			.push(
																					{
																						"name" : "tokenValue",
																						"value" : $(
																								'#tokenValue')
																								.val()
																					},
																					{
																						"name" : "clientName",
																						"value" : $(
																								'#clientName')
																								.val()
																					},
																					{
																						"name" : "fromDate",

																						"value" : $(
																								'#fromDate')
																								.val()
																					},
																					{
																						"name" : "toDate",

																						"value" : $(
																								'#toDate')
																								.val()
																					});
																}
															});
										};
										populateDatatable();
									});

					var oTable8 = $('#auction_details_table_client_spc')
							.dataTable();

					$('#auction_details_table_client_spc tbody').delegate("tr",
							"click", rowClick);

					var hlr8 = 0;

					var client = 0, activity, dateLoc = 0, status, values, catCode = 0, realActivity = 0, forModal, forModal1, dateLoc1 = 0;
					row8 = $("td:first", this).parent().children();

					function rowClick() {
						if (hlr8)
							$("td:first", hlr8).parent().children().each(
									function() {
										$(this).removeClass('markrow');
									});
						hlr8 = this;
						$("td:first", this).parent().children().each(
								function() {
									$(this).addClass('markrow');
								});

						client = $("td:first", this).text();
						activity = $("td:eq(1)", this).text();
						dateLoc = $("td:eq(2)", this).text();
						status = $("td:eq(3)", this).text();

						forModal1 = dateLoc.split('_');
						forModal = forModal1[1];
						dateLoc1 = forModal1[0];

						values = activity.split('~');
						catCode = values[0];
						realActivity = values[1];

						if (forModal === "Failure") {
							populateModalData();
						} else if (forModal === "Report") {
							populateModalDataUS();
						} else {
							populateModalDataUS();
						}

					}
					function populateModalDataUS() {

						$('#client1').val(client);
						$('#activity1').val(realActivity);
						$('#catCode1').val(catCode);
						$('#failedDate1').val(dateLoc1);
						$('#myModalUS').modal('show');

					}
					function populateModalData() {

						$
								.ajax({
									type : 'POST',
									url : contextPath+'/errorStack',
									data : {
										tokenValue : $('#tokenValue').val(),
										catCode : catCode,
										timeStamp : dateLoc1
									},
									success : function(data) {

										var obj1 = JSON.stringify(data);

										var obj = JSON.parse(obj1);
										$
												.each(
														obj,
														function(index, element) {

															$('#reason')
																	.append(
																			element.reason);
															$('#exceptionLog')
																	.append(
																			element.exceptionLog);
														});
										if (realActivity === "Business Exception- NO LOB_MDM present") {
											$('#clientMdmLob').val(client);
											$('#activityMdmLob').val(
													realActivity);
											$('#catCodeMdmLob').val(catCode);
											$('#failedDateMdmLob')
													.val(dateLoc1);
											var obj1 = JSON.stringify(data);

											var obj = JSON.parse(obj1);

											$
													.each(
															obj,
															function(index,
																	element) {

																$(
																		'#reasonMdmLob')
																		.append(
																				element.reason);
																$(
																		'#exceptionLogMdmLob')
																		.append(
																				element.exceptionLog);
															});
											$('#myModalMdmLob').modal('show');

											$
													.ajax({
														type : 'POST',
														url : contextPath+'/excelMdmLobRes',
														data : {
															tokenValue : $(
																	'#tokenValue')
																	.val(),
															client : $(
																	'#clientMdmLob')
																	.val(),
															activity : $(
																	'#activityMdmLob')
																	.val(),
															catCode : $(
																	'#catCodeMdmLob')
																	.val(),
															failedDate : $(
																	'#failedDateMdmLob')
																	.val(),
															reason : $(
																	'#reasonMdmLob')
																	.val(),
															exceptionLog : $(
																	'#exceptionLogMdmLob')
																	.val()
														},
														success : function(data) {
															if (data.nodata == 'No Data Found For MDM LOB') {
																$(
																		'#noMdmLobDiv')
																		.html(
																				'No Data Found For MDM LOB');
																$(
																		'#mdmLobLinkButton')
																		.hide();
															} else {
																$(
																		'#noMdmLobDiv')
																		.html(
																				'');
																$(
																		'#mdmLobLinkButton')
																		.show();
															}

														},
														error : function(data) {
															$(
																	'#loadingmessageLob')
																	.hide();
															$('#downloadDiv')
																	.hide();
														}
													});

										} else {
											$('#client').val(client);
											$('#activity').val(realActivity);
											$('#catCode').val(catCode);
											$('#failedDate').val(dateLoc1);
											var obj1 = JSON.stringify(data);

											var obj = JSON.parse(obj1);

											$
													.each(
															obj,
															function(index,
																	element) {

																$('#reason')
																		.append(
																				element.reason);
																$(
																		'#exceptionLog')
																		.append(
																				element.exceptionLog);
															});
											$('#myModal').modal('show');
										}
									},
									error : function(data) {
									}
								});
					}

					$('#modalExcelMdmLob').click(function(data) {
						$('#loadingmessageLob').show();
						$('#downloadDiv').hide();
						$.ajax({
							type : 'POST',
							url : contextPath+'/excelMdmLobRes',
							data : {
								tokenValue : $('#tokenValue').val(),
								client : $('#clientMdmLob').val(),
								activity : $('#activityMdmLob').val(),
								catCode : $('#catCodeMdmLob').val(),
								failedDate : $('#failedDateMdmLob').val(),
								reason : $('#reasonMdmLob').val(),
								exceptionLog : $('#exceptionLogMdmLob').val()
							},
							success : function(data) {
								$('#loadingmessageLob').hide();
								$('#myModalMdmLob').modal('hide');
								$('#downloadDiv').show();
							},
							error : function(data) {
								$('#loadingmessageLob').hide();
								$('#downloadDiv').hide();
							}
						});
					});

					$('#modalExcelLob').click(function(data) {
						$('#loadingmessageLob').show();
						$('#downloadDiv').hide();
						$.ajax({
							type : 'POST',
							url : contextPath+'/modalExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								client : $('#clientMdmLob').val(),
								activity : $('#activityMdmLob').val(),
								catCode : $('#catCodeMdmLob').val(),
								failedDate : $('#failedDateMdmLob').val(),
								reason : $('#reasonMdmLob').val(),
								exceptionLog : $('#exceptionLogMdmLob').val()
							},
							success : function(data) {
								$('#loadingmessageLob').hide();
								$('#myModalMdmLob').modal('hide');
								$('#downloadDiv').show();
							},
							error : function(data) {
								$('#loadingmessageLob').hide();
							}
						});
					});

					$('#reInitiateButton').click(function() {

						$('#loadingmessage').show();
						$.ajax({
							type : 'POST',
							url : contextPath+'/reinitiateActivity',
							data : {
								tokenValue : $('#tokenValue').val(),
								catCode : $('#catCode').val(),
								timeStamp : $('#failedDate').val(),
								client : $('#client').val(),
								activity : $('#activity').val(),
								failedDate : $('#failedDate').val()
							},
							success : function(data) {
								$('#myModal').modal('hide');
								$('#loadingmessage').hide();
							},
							error : function(data) {
								$('#myModal').modal('hide');
								$('#loadingmessage').hide();
							}
						});
					});

					$('#reInitiateButtonLob').click(function() {

						$('#loadingmessageLob').show();
						$.ajax({
							type : 'POST',
							url : contextPath+'/reinitiateActivity',
							data : {

								tokenValue : $('#tokenValue').val(),
								client : $('#clientMdmLob').val(),
								activity : $('#activityMdmLob').val(),
								catCode : $('#catCodeMdmLob').val(),
								timeStamp : $('#failedDateMdmLob').val(),
							},
							success : function(data) {
								$('#myModalMdmLob').modal('hide');
								$('#loadingmessageLob').hide();
							},
							error : function(data) {
								$('#myModalMdmLob').modal('hide');
								$('#loadingmessageLob').hide();
							}
						});
					});
					function pouplateGraphChart() {
						var fromDate = $('#fromDate').val();
						var toDate = $('#toDate').val();
						$('#toDate1').val(fromDate);
						$('#fromDate1').val(toDate);

						var clientName = $('#clientName').val();

						if (fromDate != '' && toDate != '' && clientName !== ''
								&& clientName !== '-1') {
							$
									.ajax({
										type : 'GET',
										url : contextPath+'/activityGraphServletClientSpc',
										data : {
											tokenValue : $('#tokenValue').val(),
											clientName : $('#clientName').val(),
											fromDate : $('#fromDate').val(),
											toDate : $('#toDate').val()
										},
										success : function(data) {
											$('#loadingmessage1').hide();
											arSeries = data;

											arJQ = [];

											for (var z = 0; z < arSeries.length; z++) {

												jqPrices = [];

												var prices = arSeries[z].PriceTicks;

												for (var i = 0; i < prices.length; i++) {

													jqPrices
															.push([
																	prices[i].allDates
																			.toString(),
																	prices[i].noOfActivities ]);
												}

												arJQ.push(jqPrices);

											}

											$
													.jqplot(
															'activity_chart_client_spc',
															arJQ,
															{
																title : 'Under Processing,Successful,Failed Activity Trends',
																fontFamily : '"Arial',
																fontSize : '10pt',
																textColor : '#000000',
																seriesDefaults : {
																	rendererOptions : {
																		smooth : true
																	}
																},
																series : [
																		{
																			lineWidth : 2,
																			color : '#80D855', // success
																			markerOptions : {
																				style : "circle",
																				size : 6
																			}
																		},
																		{
																			lineWidth : 2,
																			color : '#CC0000',
																			markerOptions : {
																				style : "x", // fail
																				size : 6
																			}
																		},
																		{
																			lineWidth : 2,
																			color : '#DD964A',
																			markerOptions : {
																				style : "filledSquare", // under
																				size : 6
																			}
																		} ],
																axes : {
																	xaxis : {

																		renderer : $.jqplot.DateAxisRenderer,
																		rendererOptions : {
																			tickRenderer : $.jqplot.CanvasAxisTickRenderer
																		},
																		tickOptions : {
																			formatString : '%d/%m'
																		},
																		tickInterval : '1 day',
																		label : "Dates Based on search",
																		fontSize : '3pt',
																	},
																	yaxis : {
																		label : "Activities",

																		min : 0
																	}
																}
															}).replot();
										}
									});
						} else {

							$('#htmlError').show();
							$('#htmlError').text('Please fill all values');
							$('#dynaContent').hide();
							return false;

						}
					}
					;

					function populatePiechart() {
						var fromDate = $('#fromDate').val();
						var toDate = $('#toDate').val();
						$('#toDate1').val(fromDate);
						$('#fromDate1').val(toDate);

						var clientName = $('#clientName').val();

						if (fromDate != '' && toDate != '' && clientName !== ''
								&& clientName !== '-1') {
							$
									.ajax({
										type : 'GET',
										url : contextPath+'/activityPieServletClientSpc',
										data : {
											tokenValue : $('#tokenValue').val(),
											clientName : $('#clientName').val(),
											fromDate : $('#fromDate').val(),
											toDate : $('#toDate').val()
										},
										success : function(data) {
											var plot = $
													.jqplot(
															'pieChartFailedSuccess_client_spc',
															[ data ],
															{
																title : {
																	text : 'Failed,Under Processing & Successful Activities',
																	fontFamily : '"Arial',
																	fontSize : '9pt',
																	textColor : '#000000'
																},

																seriesDefaults : {
																	renderer : $.jqplot.PieRenderer,
																	rendererOptions : {
																		showDataLabels : true,
																		padding : 2,
																		sliceMargin : 3,
																		shadow : false,
																		dataLabelFormatString : '%.2f%%',
																		dataLabelPositionFactor : 0.5
																	}
																},
																legend : {
																	show : true,
																	location : 'e'
																},
																highlighter : {
																	show : true,
																	useAxesFormatters : false,
																	tooltipFormatString : '%s'
																},
																seriesColors : [
																		'#1eed22',
																		'#ff0000',
																		'#fce676' ],
															}).replot();
										}
									});
						} else {
							$('#htmlError').show();
							$('#htmlError').text('Please fill all values');
							$('#dynaContent').hide();
							return false;
						}
					}
					// for excel
					$('#exportFailedExcel').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Failed Excel');
						$.ajax({
							type : 'POST',
							url : contextPath+'/failedExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportSuccessExcel').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Success Excel');
						$.ajax({
							type : 'POST',
							url : contextPath+'/successExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportUnderExcel').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Under Processing Excel');
						$.ajax({
							type : 'POST',
							url : contextPath+'/underExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportTotalPdf').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Overall Pdf');

						$.ajax({
							type : 'POST',
							url : contextPath+'/overAllPdfReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportFailedPdf').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Failed Pdf');
						$.ajax({
							type : 'POST',
							url : contextPath+'/failedPdfReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportSuccessPdf').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Success Pdf');
						$.ajax({
							type : 'POST',
							url : contextPath+'/successPdfReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportUnderPdf').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Under Processing Pdf');
						$.ajax({
							type : 'POST',
							url : contextPath+'/underPdfReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});
					$('#exportTotalExcel').click(function(data) {
						$('#downloadDiv').hide();
						$('#pdfButton').text('Export Overall Excel');
						$.ajax({
							type : 'POST',
							url : contextPath+'/overAllExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								clientName : $('#clientName').val(),
								fromDate : $('#fromDate').val(),
								toDate : $('#toDate').val()
							},
							success : function(data) {

								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});

					$('#downloadUrl')
							.click(
									function(data) {

										$
												.ajax({
													type : 'GET',
													url : contextPath+'/downloadPdfExcel',

													success : function(data) {

														var aopen = document
																.getElementById('downloadHref');

														aopen.href = "file:///"
																+ data;

														alert(data);

													},
													error : function(data) {
													}
												});
									});

				});