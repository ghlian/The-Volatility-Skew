
var result = [];

var YAHOO = {
		util: { 
	ScriptNodeDataSource: {
	callbacks: function(data) { 
	result = $.map( data.ResultSet.Result, function( item ) {
		//jquery requires these to be label,value pair designation
		return {
			label: item.symbol + " | " + item.name,
			value: item.symbol
		}
	});
}
}
}};

function autocomplete() {

	$("#ticker_input" ).autocomplete({
		source: function( request, response ) {

		//store response function to be called back
		//within our pseudo 'jsonp request callback' obj
		//ajax_response = response;

		$.ajax({
			// url: "http://www.google.com/finance/match",
			url: "http://d.yimg.com/aq/autoc",
			dataType: "jsonp",
			//jsonpCallback: "success2",
			jsonpCallback: "YAHOO.util.ScriptNodeDataSource.callbacks",
			data: {
			//matchtype: "matchall",
			//q: request.term,
			query: request.term,
			region : "US",
			lang: "en-US",
		},
		success: function() {},
		complete: function() {
			response(result);
		},
		});


	},
	minLength: 1,
	select: function( event, ui ) {
		//console.log(ui.item.value);
		getContracts(ui.item.value);

	}
	,
	open: function() {
		$( this ).removeClass( "ui-corner-all" ).addClass( "ui-corner-top" );
	},
	close: function() {
		$( this ).removeClass( "ui-corner-top" ).addClass( "ui-corner-all" );
	}

	});


}


function getContracts(ticker) {
	//console.log(ticker);
	var ticker_months = [];

	$("#month_input").html("");
	$("#month_input").addClass("contract-loading");
	$("#month_input").html( $("<option></option").attr("value", "").text("Loading. . .") );

	$.ajax({
		type: "GET",
		url: "/contractmonth",
		dataType: "json",
		data: $.param({ticker: ticker}),
		error: {

	},
	success: function(data, textStatus) {
		ticker_months = data;
	},
	complete: function(xhr) {

		if (ticker_months < 1) {
			$("#month_input").html( $("<option></option").attr("value", "").text("No Contracts Available") );
		}
		else  {
			$("#month_input").html("Contract Month");
			$.each(ticker_months, function(key, value) {
				var monthyr = value.split("-");
				$("#month_input").append( $("<option></option").attr("value", key).text(monthyr[1] + "-" +monthyr[0]));
			});
		}

		$("#month_input").removeClass("contract-loading");
	}
	});
}


