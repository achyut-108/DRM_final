$(document)
		.ready(
				function() {

					var timeoutID;
					resetTimeout();

					function resetTimeout() {
						if (timeoutID)
							clearTimeout(timeoutID);
						timeoutID = setTimeout(ShowTimeoutWarning, 600000);
					}

					function ShowTimeoutWarning() {
						$("#dialog").dialog("open");
						return false;
					}

					$("#dialog").dialog({
						autoOpen : false,
						dialogClass : "no-close",
						position : 'center',
						title : 'Session Details',
						draggable : false,
						width : 300,
						height : 200,
						resizable : false,
						modal : true,
						buttons : [ {
							text : "OK",
							click : function() {
								ShowTimeoutWarning();
								$(this).dialog("close");
							}
						} ]
					});

					document.onkeyup = resetTimeout;
					document.onkeydown = resetTimeout;
					document.onclick = resetTimeout;

					$
							.ajax({
								type : 'POST',
								url : contextPath+'/prepopulation',
								data : {
									tokenValue : $('#tokenValue').val()
								},
								success : function(data) {

									$('#totalActivities').html(
											data.totalActivities);
									$('#underProcessing').html(
											data.underProcessing);
									$('#successfulActivities').html(
											data.successfulActivities);
									$('#failedActivities').html(
											data.failedActivities);
									var populateDatatable = function() {
										$('#auction_details_table')
												.dataTable(
														{

															"bProcessing" : false,
															"bAutoWidth" : false,
															"bDestroy" : true,
															"sAjaxSource" : contextPath+'/populateParticularOverAllActivity',
															"iDisplayLength" : 10,
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
																	2, 'desc' ] ],
															"fnServerParams" : function(
																	aoData) {
																aoData
																		.push({
																			"name" : "tokenValue",
																			"value" : $(
																					'#tokenValue')
																					.val()
																		});
															}
														});
									};
									populateDatatable();
								},
								error : function(data) {
									if (data.status === 402) {
										var url = "404.html";

										url.slice(0, url.indexOf('?'));

										window.location.href = url;

									}
								}
							});
					$('#totalActivities')
							.click(
									function(data) {
										// alert($('#tokenValue').val());
										var populateDatatable = function() {
											$('#auction_details_table')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : false,
																"bDestroy" : true,
																"sAjaxSource" : contextPath+'/populateParticularTotalActivity',
																"bJQueryUI" : false,
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
																			.push({
																				"name" : "tokenValue",
																				"value" : $(
																						'#tokenValue')
																						.val()
																			});
																}
															});
										};
										populateDatatable();
									});
					$('#underProcessing')
							.click(
									function(data) {
										// alert($('#tokenValue').val());
										var populateDatatable = function() {
											$('#auction_details_table')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : false,
																"bDestroy" : true,
																"sAjaxSource" : contextPath+'/populateParticularUnderActivity',
																"bJQueryUI" : false,
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
																			.push({
																				"name" : "tokenValue",
																				"value" : $(
																						'#tokenValue')
																						.val()
																			});
																}
															});
										};
										populateDatatable();
									});
					$('#successfulActivities')
							.click(
									function(data) {
										// alert($('#tokenValue').val());
										var populateDatatable = function() {
											$('#auction_details_table')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : false,
																"bDestroy" : true,
																"sAjaxSource" : contextPath+'/populateParticularSuccessfulActivity',
																"bJQueryUI" : false,
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
																		} ],
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
																			.push({
																				"name" : "tokenValue",
																				"value" : $(
																						'#tokenValue')
																						.val()
																			});
																}
															});
										};
										populateDatatable();
									});
					$('#failedActivities')
							.click(
									function(data) {
										
										var populateDatatable = function() {
											$('#auction_details_table')
													.dataTable(
															{
																"bProcessing" : false,
																"bAutoWidth" : false,
																"sAjaxSource" : contextPath+'/populateParticularFailedActivity',
																"bJQueryUI" : false,
																"iDisplayLength" : 10,
																"bDestroy" : true,
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
																			.push({
																				"name" : "tokenValue",
																				"value" : $(
																						'#tokenValue')
																						.val()
																			});
																}
															});
										};
										populateDatatable();
									});
					$('#logout').click(function(data) {
						$.ajax({
							type : 'POST',
							url : contextPath+'/logout',
							success : function(data) {
//								var url = "login.jsp";
								var url = contextPath;
								console.log('logout click function : ' + contextPath);
								url.slice(0, url.indexOf('?'));
								window.location.href = url;
							},
							error : function(data) {
							}
						});
					});
					var oTable8;
					oTable8 = $('#auction_details_table').dataTable();
					$('#auction_details_table tbody').delegate("tr", "click",
							rowClick);
					var hlr8 = 0; // Reference to the currently highlighted
					// row
					var client = 0, activity, dateLoc = 0, status, values, catCode = 0, realActivity = 0, forModal1, forModal, dateLoc1 = 0
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
						// You can pull the values out of the row here if
						// required
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

//					$('#modalExcelMdmLob').click(function(data) {
//						$('#downloadDiv').hide();
//						$.ajax({
//							type : 'POST',
//							url : 'excelMdmLobRes',
//							data : {
//								tokenValue : $('#tokenValue').val(),
//								client : $('#clientMdmLob').val(),
//								activity : $('#activityMdmLob').val(),
//								catCode : $('#catCodeMdmLob').val(),
//								failedDate : $('#failedDateMdmLob').val(),
//								reason : $('#reasonMdmLob').val(),
//								exceptionLog : $('#exceptionLogMdmLob').val()
//							},
//							success : function(data) {
//
//								$('#myModalMdmLob').modal('hide');
//								$('#downloadDiv').show();
//							},
//							error : function(data) {
//							}
//						});
//					});
					$('#modalExcel').click(function(data) {
						$('#downloadDiv').hide();
						$.ajax({
							type : 'POST',
							url : contextPath+'/modalExcelReport',
							data : {
								tokenValue : $('#tokenValue').val(),
								client : $('#client').val(),
								activity : $('#activity').val(),
								catCode : $('#catCode').val(),
								failedDate : $('#failedDate').val(),
								reason : $('#reason').val(),
								exceptionLog : $('#exceptionLog').val()
							},
							success : function(data) {

								$('#myModal').modal('hide');
								$('#downloadDiv').show();
							},
							error : function(data) {
							}
						});
					});

				});