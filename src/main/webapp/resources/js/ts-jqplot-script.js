$(document)
		.ready(
				function() {

					$('#loadingmessage1').show();
					$('#loadingmessagePieLarger').hide();

					$
							.ajax({
								type : 'GET',
								url : 'activityGraphServlet',
								success : function(data) {
									$('#loadingmessage1').hide();
									arSeries = data;

									arJQ = [];

									for (var z = 0; z < arSeries.length; z++) {

										jqPrices = [];

										var prices = arSeries[z].PriceTicks;

										for (var i = 0; i < prices.length; i++) {

											jqPrices.push([
													prices[i].allDates
															.toString(),
													prices[i].noOfActivities ]);

										}

										arJQ.push(jqPrices);

									}

									$
											.jqplot(
													'activity_chart',
													arJQ,
													{
														title : 'Activity Trends',
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
																	color : '#80D855',// success
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
																		style : "filledSquare",// under
																		size : 6
																	}
																}

														],
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
																label : "Last 5 Dates",
																fontSize : '3pt',

															},
															yaxis : {
																label : "Activities",
																fontSize : '3pt'

															}

														}

													}).replot();
								}
							});

					$
							.ajax({
								type : 'GET',
								url : 'activityPieServlet',

								success : function(data) {

									var plot = $
											.jqplot(
													'pieChartFailedSuccess',
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

				});