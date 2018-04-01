$(document).ready(function() {

	$('#xmlViewBidderDiv').hide();
	$('#onlyTimeStamp').hide();

	var oTable = 0;
	var populateDatatable = function() {
		oTable = $('#bidderSync_dataTable').dataTable({
			"bProcessing" : false,
			"bAutoWidth" : true,
			"bDestroy" : true,
			"sAjaxSource" : contextPath+'/bidderSyncPop',
			"bJQueryUI" : false,
			"iDisplayLength" : 10,
			"aoColumns" : [ {
				"mData" : "clientName"
			}, {
				"mData" : "fileName"
			}, {
				"mData" : "dateTimeStamp"
			}, {
				"mData" : "onlyTimeStamp"

			}, ],

			"aaSorting" : [ [ 2, 'desc' ] ],
			"fnServerParams" : function(aoData) {
				aoData.push({
					"name" : "tokenValue",
					"value" : $('#tokenValue').val()
				});
			},

		});

	};

	populateDatatable();

	$('#bidderSync_dataTable tbody').delegate("tr", "click", rowClick);

	var clientName = 0, fileName = 0, dateTimeStamp = 0, onlyTimeStamp = 0;

	function rowClick() {

		clientName = $("td:first", this).text();
		fileName = $("td:eq(1)", this).text();
		dateTimeStamp = $("td:eq(2)", this).text();
		onlyTimeStamp = $("td:eq(3)", this).text();

		populateModalBidder();

	}

	function populateModalBidder() {

		$('#clientNameBidder').val(clientName);
		$('#fileNameBidder').val(fileName);
		$('#dateTimeBidder').val(dateTimeStamp);
		$('#onlyDateTimeBidder').val(onlyTimeStamp);

		$('#myModalBidder').modal('show');

	}

	$('#reInitiateButtonBidder').click(function(data) {
		$('#loadingmessageBidder').show();
		$.ajax({
			type : 'POST',
			url : contextPath+'/reinitiateBidderSync',
			data : {
				clientNameBidder : $('#clientNameBidder').val(),
				fileNameBidder : $('#fileNameBidder').val(),
				dateTimeBidder : $('#dateTimeBidder').val(),
				onlyTimeStamp : $('#onlyDateTimeBidder').val()
			},
			success : function(data) {

				$('#myModalBidder').modal('hide');
				$('#loadingmessageBidder').hide();
				populateDatatable();

			},
			error : function(data) {
				$('#loadingmessageBidder').hide();
			}
		});
	});

	$('#finalXmlBidderSync').click(function(data) {
		
		$.ajax({
			type : 'POST',
			url : contextPath+'/viewXmlBidderSync',
			data : {
				clientNameBidder : $('#clientNameBidder').val(),
				fileNameBidder : $('#fileNameBidder').val(),
				dateTimeBidder : $('#dateTimeBidder').val(),
				onlyTimeStamp : $('#onlyDateTimeBidder').val()
			},
			success : function(data) {
				
				$('#bidderSyncXmlView').html(data);
				$('#xmlViewBidderDiv').show();
			},
			error : function(data) {
			}
		});
	});

	$('#hideXmlBidderSync').click(function(data) {

		$('#xmlViewBidderDiv').hide();
	

	});

	

});