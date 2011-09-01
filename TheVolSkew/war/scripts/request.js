

/* external globals
 * var contract_count = ${fn:length(result.contract_order)};
 * var contract_request = '"${fn:join(result.contract_order,"\",\"")}"';
 * 
 */
var deleted = false;


function delete_row(obj) {

	var req = [];
	deleted = true;

	var num_results;
	for (num_results = 0; num_results < json_result.contract_order.length; num_results++) {		 

		if ($(obj).next().html() != json_result.contract_order[num_results] ) {
			req.push( json_result.contract_order[num_results] )

		}
	}
	//no contracts defined, redirect to starting point
	if (req.length < 1) {
		window.location = "/"
	}
	else {
		//console.log(req );
		$("#request_data").attr("value", build_form_request(req) ); 
		$("#contract_add").submit();
	}


}

//input array of contracts
function build_form_request(req) {
	"use strict";
	var request_string = "";

	for (var i = 0; i < req.length; i++) {
		request_string += "\"" + req[i] + "\"";
		//add comma..todo: refactor build form & add form
		if (i + 1 < req.length) {
			request_string += ",";
		}

	}
	return "[" + request_string + "]";
}



function add_form_request() {

	//contract_count defined from server-side
	if (contract_count == 0) {
		contract_request = "\"" + $("#ticker_input").val() + " "
		+ $("#month_input :selected").text() + "\"";
	} 
	else {
		contract_request += ",\""
			+ ($("#ticker_input").val() + " " 
					+ $("#month_input :selected").text()) + "\"";
	}

	return "[" + contract_request + "]";

}



/*MAIN*/

$(document).ready(function() {

	//load auto-complete
	autocomplete();

	//FORM SUBMIT: build request string
	$("#contract_add").submit(function(e) {

		//deleted toggle is called from form, otherwise is delete action
		if (!deleted) {
			//handle common error on submit
			if($("#month_input :selected").text() == "Contract Month") {
				e.preventDefault();	
				alert("Oops! Select a contract month.");
				return;
			}

			if ($("#month_input :selected").text() == "No Contracts Available") {
				e.preventDefault();	
				alert("Oops! No option contracts exist for this ticker.");
				return;
			}

			//build form request string in hidden form request_data
			$("#request_data").attr("value", add_form_request() );
		}
	});


	//checkmarks
	$("input[type=checkbox]").bind('click', function() {
		//console.log(json_result);
		drawFlotChart(json_result)
	});


	//Delete case
	$(".delete").click( function(e) {
		delete_row(this)
	});




});




