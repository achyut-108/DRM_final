$(document).ready(function() {

					//var contextPath = '${pageContext.request.contextPath}';
					console.log('context________*****path ... ' + contextPath);
					
					$("#userName").keyup(function(event) {
						if (event.keyCode == '13') { // 9 is the tab key
							$("#password").focus();
						}
					});
					$("#password").keyup(function(event) {
						if (event.keyCode == '13') { // 9 is the tab key
							$("#loginButton").focus();
						}
					});

					var el = $("input:text").get(0);
					var elemLen = el.value.length;
					el.selectionStart = elemLen;
					el.selectionEnd = elemLen;
					el.focus();
 
					$('#loginButton')
							.click(
									function(data) {
										$('#loginFailResponse').html('');
										var userName = $('#userName').val();
										var password = $('#password').val();
										// alert(userName+password);
										if (userName === "" || password === "") {
											$('#loginFailResponse')
													.text(
															"Username or Password cannot be blank");
										} else {
											$('#loadingmessage').show();
											$.ajax({
														type : 'POST',
														//url : 'login',
														url: contextPath+"/login",
														//url:  "/bam/login",
														data : $('#loginForm')
																.serialize(),

														success : function(data) {
															//var url = "dashboard.jsp";
															var url = contextPath+"/dashboard";
															url.slice(0,url.indexOf('?'));
															
															console.log('inside success!! url is : ' + url);
															window.location.href = url;
															$('#loadingmessage')
																	.hide();

														},
														error : function(data) {

															if (data.status === 401) {
																$(
																		'#loginFailResponse')
																		.text(
																				"Username or Password cannot be blank");
															} else if (data.status === 403) {
																$(
																		'#loginFailResponse')
																		.text(
																				"Cannot login to server.!Please try later");
															}
															//else if (data.status === 404) {
//																$(
//																		'#loginFailResponse')
//																		.text(
//																				"User Id or Password is incorrect");
//															} 
																else if (data.status === 402) {
																$(
																		'#loginFailResponse')
																		.text(
																				"User Already Logged In..!!");
																$('#myModal')
																		.modal(
																				{
																					show : 'true'
																				});
															}
															$('#loadingmessage')
																	.hide();
														}

													});
										}
										// $('#loginFailResponse').html('');

									});

					$('#reLogin')
							.click(
									function(data) {
										$('#loginFailResponse').html('');
										$('#loadingmessage').show();

										document.getElementById('reLogin').disabled = 'disabled';
										document.getElementById('doNothing').disabled = 'disabled';

										$
												.ajax({
													type : 'POST',
													url : contextPath+'/relogin',

													data : $('#loginForm')
															.serialize(),

													success : function(data) {
//														var url = "dashboard.jsp";
                                                        var url = contextPath+"/dashboard";

														url.slice(0, url
																.indexOf('?'));

														window.location.href = url;
														$('#loadingmessage')
																.hide();
													},
													error : function(data) {

														if (data.status === 402) {
															$(
																	'#loginFailResponse')
																	.text(
																			"Username or Password cannot be blank");
															$('#myModal')
																	.modal(
																			'hide');
															$('#loadingmessage')
																	.hide();
														} else if (data.status === 403) {
															$(
																	'#loginFailResponse')
																	.text(
																			"Cannot login to server.!Please try later");
															$('#myModal')
																	.modal(
																			'hide');
															$('#loadingmessage')
																	.hide();
														} 
														//else if (data.status === 404) {
//															$(
//																	'#loginFailResponse')
//																	.text(
//																			"User Id or Password is incorrect");
//															$('#myModal')
//																	.modal(
//																			'hide');
//															$('#loadingmessage')
//																	.hide();
//														}
                                                        else if (data.status === 401) {
															$(
																	'#loginFailResponse')
																	.text(
																			"Database Has Gone Down.!Please Try Later");
															$('#myModal')
																	.modal(
																			'hide');
															$('#loadingmessage')
																	.hide();
														} else if (data.status === 402) {
															$(
																	'#loginFailResponse')
																	.text(
																			"User Already Logged In..!!");

															$('#myModal')
																	.modal(
																			'hide');
															$('#loadingmessage')
																	.hide();
														}
														$('#loadingmessage')
																.hide();
													}

												});
									});
					$('#doNothing').click(function(data) {

						$('#myModal').modal('hide');

					})
				});