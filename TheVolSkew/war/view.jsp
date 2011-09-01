<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>


<!DOCTYPE html>
<html>

<head>

<jsp:include page="/_header.jsp" />
<title>The Latest Volatility Skew | Graph</title>
</head>

<body>


<jsp:include page="/_nav.jsp" />

<div id="contract_wrap">

	<jsp:include page="/_contract_form.jsp" />
	
</div>



<div id="main">


	
	<div id="market_info">
	<h2>Quotes</h2>
	
		<table id="market_data_tbl">
			<thead>
				<tr>
					<!-- 
							<th></th>
							<th>Ticker</th>
							<th>Last</th>
						 -->
				</tr>
			</thead>
			<tbody id="market_data_tbl_body">
				<c:forEach var="stock" items="${result.stock_map}">
					<tr>
					<!-- 
						<td class='delete' title='remove'>x</td>
					 -->
						<td id="${stock.key}" class='ticker'>${stock.key}</td>
						<td class='last'>${stock.value.last}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	
	
		<div id='toggle'>
		<h2>Options</h2>
		<table id="options_data_tbl">
			<thead>
				<tr>
					<th></th>
					<th>Contract</th>
					<th>Last</th>
				 	<th>Bid</th>
				 	<th>Ask</th>
				</tr>
		
			</thead>
			<tbody id="options_data_tbl_body">
		
				<c:forEach var="contract" items="${result.contract_order}">
					<tr id = "${fn:replace(contract, " ", "_")}" >
						<td class='delete' title='remove'>x</td>
						<td class='contract'>${contract}</td>
						<td class='last'><input id="${fn:replace(contract, " ", "_")}_last" type="checkbox" checked /></td>
						<td class='bid'><input id="${fn:replace(contract, " ", "_")}_bid" type="checkbox" /></td>
						<td class='ask'><input id="${fn:replace(contract, " ", "_")}_ask" type="checkbox" /></td>
					</tr>
					
					<!-- old way of a/b/m/last contract separately
					<tr>
						<td class='delete' title='remove'>x</td>
						<td class='contract'>${contract} ask</td>
						<td></td>
					</tr>
					<tr>
						<td class='delete' title='remove'>x</td>
						<td class='contract'>${contract} bid</td>
						<td></td>
					</tr>
		
					<tr>
						<td class='delete' title='remove'>x</td>
						<td class='contract'>${contract} mid</td>
						<td></td>
					</tr>
					
					
					<tr>
						<td class='delete' title='remove'>x</td>
						<td class='contract'>${contract} last</td>
						<td></td>
					</tr>
					 -->
				</c:forEach>
		
			</tbody>
		</table>
		</div>



	</div>

	
	<div id='chart'>
	</div>
	


<!--  second phase -->
	<div id="save_graph"></div>
	
	<div id="data"></div>
	
</div> <!--  end main -->




</body>



<jsp:include page="/_scripts.jsp" />



<script type="text/javascript">
	var json_result = ${json_result};
	drawFlotChart(json_result);
</script>





</html>
