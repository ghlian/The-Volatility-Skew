<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<div id="contract_select">
	
		<form id="contract_add" action="/">
		<fieldset>
		 <legend>Contract</legend>
			
			<p class = "contract_input">
				<label for="ticker_input">&nbsp;Enter Ticker / Company</label>
				<input id="ticker_input"/> 
			</p>
			
			<p class="contract_input">
				<label for="month_input">&nbsp;Contract</label>

				<select id="month_input">
					<option value = "Contract_Month">Contract Month</option>
				</select>
				
				
			</p>
			
			<p class="contract_input" style="vertical-align:middle">
				<input id="request_data" name="request_data" type="hidden"
					value='#' />
				
				<input id="contract_add_submit" type="submit" value="Add to Graph" />
			</p>
		</fieldset>
		</form>
	
	</div>