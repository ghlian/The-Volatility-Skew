<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>

<html>

	<head>

		<jsp:include page="/_header.jsp" />
		<title>The Latest Volatility Skew</title>

	</head>

	<body>

		<jsp:include page="/_nav.jsp" />

		<div id="contract_wrap">
		
			<jsp:include page="/_contract_form.jsp" />
			
		</div>

<!-- splash -->
		<div id="main">
		
			<div id="splash">
				
				<p class="splash_header_txt">Your Implied Volatility Snapshot</p>
				
				<p class="splash_txt">Visualize the implied volatility skew of equity options 
				across maturities</p>
				
			</div>
		

			<div id="boxes">

				<div class="box">
				
					<div class="box_content">
						
						<p class="box_graphic">
							<img src ="/images/enter_ticker.PNG" alt="Enter Ticker" />
						</p>
						<div class="box_txt">
						<p class="box_header">
							Enter Ticker</p>
							
						<p class="box_caption">
							Choose an underlying stock
						</p>
						</div>
					</div>
				</div>
	
				<div class="box">
					<div class="box_content">
					
						
					
							<p class="box_graphic">
							<img src ="/images/contract.PNG" alt="Choose Contract" />
							</p>	
						
						<div class="box_txt">			
							<p class="box_header">
								Choose Contract
							</p>
	
							<p class="box_caption">
								Select a contract month
							</p>
						</div>
						
					</div>
				</div>
			
				<div class="box">
					<div class="box_content">
						<p class="box_graphic">
							<img src ="/images/add2.PNG" alt="Add to Graph" />
						</p>
						
						<div class="box_txt">
						<p class="box_header">
							Graph
						</p>
							
						<p class="box_caption">
							Add it to your graph and compare
						</p>
						</div>
					</div>
				</div>
			
			</div>
		
		</div>


	</body>



	<jsp:include page="/_scripts.jsp" />


</html>
