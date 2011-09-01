

function drawFlotChart(data) { 

	// attach data
	var dataResults = new Array();
	var contract = [];

	var XminPan = data.scale[0];
	var XmaxPan = data.scale[1];
	var YmaxPan = data.scale[2];
	var YminPan = data.scale[3];


	// build data
	for (num_results = 0; num_results < data.contract_order.length; num_results++) {

		var contract =  data.contract_order[num_results];
		var contract_id = contract.replace(" ", "_");


		if ($("#" + contract_id + "_last").is(":checked") ) {
			dataResults.push({label: contract + " last", 
				data: data.vol_results[num_results*4+2],
				color: num_results*4 +2} );
		}

		if ($("#" + contract_id + "_bid").is(":checked") ) {
			dataResults.push({label: contract + " bid", 
				data: data.vol_results[num_results*4+1],
				color: num_results*4 + 1} );
		}

		if ( $("#" + contract_id + "_ask").is(":checked") ) {
			dataResults.push({label: contract + " ask", 
				data: data.vol_results[num_results*4],
				color: num_results*4 } );
		}

		/*
		 * if ($("#" + contract_id + "_mid").is(":checked") ) {
		 * dataResults.push({label: contract + " mid", data:
		 * data.vol_results[num_results*4+3] } ); }
		 */

		// max/min ranges

	}





	// build chart
	var placeholder = $("#chart");
	var options = {
			xaxis: { zoomRange: [null, null], panRange: [null, null]},
			yaxis: { zoomRange: [null, null], panRange: [null, null]},
			zoom: {
				interactive: true,

			},
			pan: {
				interactive: true
			},
			series: {
				lines: { show: true, lineWidth: .5,},
				points: { show: true, radius: .5 }, 
			},
			grid: {
				backgroundColor: { colors: ["#fff", "#fff"]},
				hoverable: true, 
				clickable: true 

				// color: "#fff"
			},
			colors: ["blue", "red"],
			shadowSize: 0,
	};

	var plot = $.plot(placeholder, dataResults, options);

	placeholder.bind('plotpan', function(event, plot) {
		var axes = plot.getAxes();

	});
	placeholder.bind('plotzoom', function(event, plot) {
		var axes = plot.getAxes();

	});

	placeholder.resize(function() {});


	/* tooltips */
	var previousPoint = null;
	placeholder.bind("plothover", function (event, pos, item) {

		if (item) {
			if (previousPoint != item.dataIndex) {
				previousPoint = item.dataIndex;

				$("#tooltip").remove();
				var x = item.datapoint[0].toFixed(2),
				y = item.datapoint[1].toFixed(2);

				showTooltip(item.pageX, item.pageY,
						item.series.label + ": (" + x + ", " + y +")");
			}
		}
		else {
			$("#tooltip").remove();
			previousPoint = null;            
		}

	});

	// sticky click (funky error on resize)
	placeholder.bind("plotclick", function (event, pos, item) {
		if (item) {
			// $("#clickdata").text("You clicked point " + item.dataIndex +
			// " in " + item.series.label + ".");
			plot.highlight(item.series, item.datapoint);
		}
	});

}

//generate tooltip
function showTooltip(x, y, contents) {
	$('<div id="tooltip">' + contents + '</div>').css( {
		position: 'absolute',
		display: 'none',
		top: y + 5,
		left: x + 5,
		border: '1px solid #fdd',
		padding: '2px',
		'background-color': '#fee',
		opacity: 0.80
	}).appendTo("body").fadeIn(200);
}

