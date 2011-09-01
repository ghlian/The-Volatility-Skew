<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

	<jsp:include page="/_header.jsp" />

<title>The Latest Volatility Skew | FAQ</title>

</head>




<body>

	<jsp:include page="/_nav.jsp" />


	<div id="contract_wrap">
	
		<jsp:include page="/_contract_form.jsp" />
		
	</div>
	
	<div id="main">
		<div id="content">
		<h1>FAQ</h1>
	
			
			<p class="content_header">
			Where is the data from?
			</p>
			
			<p class="content_txt">
			Data is taken from Yahoo using the <a href="http://developer.yahoo.com/yql/">YQL Web service</a>.  
			Consequently, it's prone to inaccuracies, delays, and should not be relied upon.  Especially for real-time
			needs and/or when markets are closed.  This is a casual, educational and comparative snapshot tool.
			</p>
			
			<p class="content_header">
			Whoa, arb galore?!
			</p>
			
			<p class="content_txt">
			Unfortunately, no.  It's common for the last traded price of an option to not reflect
			current market conditions.  The underlier may have moved, making the volatility 
			calculated from the last traded option price appear under/over-priced.  
			One needs to be cognizant of the bid/ask spread among both options and underlier, 
			which can often suggest misleading or unrealistic volatilities.  
			</p>
			
			
			<p class="content_header">
			Why is the graph so jagged?
			</p>
			
			<p class="content_txt">
			The bid/ask spread typically widens after markets close, so implied volatilities 
			calculated from these prices relative to one another can exhibit jagged, shark-tooth graphs.  
			Similarly, teeny out-of-the-money options often have wide spreads that can lead to jagged graphs.
			</p>
			
			<p class="content_header">
			These calculations seem fishy.  More details, please.
			</p>
			
			<p class="content_txt">
			Implied volatilities are iteratively calculated based on the 
			<a href="http://old.nhh.no/for/dp/2002/0902.pdf">Bjerksund-Stensland</a>
			approximation, suitable for american-style equity options.  
			This has been tested to closely match the implied volatility calculations on the popular
			<a href="https://www.thinkorswim.com/tos/client/index.jsp">ThinkorSwim</a> 
			platform.  It is important to note that the valuation ignores dividend yields,
			and sets the cost of carry at .0025, which are defaults on the aforementioned platform.
			Feel free to look a closer look at the <a href="/contact">code</a>.
			</p>
		
			<p class="content_header">
			I'm comparing two different stocks, what is this x-axis?
			</p>
			
			<p class="content_txt">
			The same strike can mean different things depending on the 
			the underlier, so a strike by strike comparison isn't 
			very illuminating.  But viewing the strikes as relative
			distances from their respective at-the-money prices might be.  Hence,
			the strikes are translated into percentages, (puts being negative,
			calls being positive) relative to their at-the-money.  For example, the $90
			put for a stock trading around $100 would have an x-axis value of -0.1, 
			while the $150 call for the same stock would be located at +0.5. 
			</p>
		
		
		
			<p class="content_header">
			There is a hump between the put and call volatilities.  Explain.
			</p>
			
			<p class="content_txt">
			Deep in-the-money options typically have wide bid/ask spreads and are unreliable 
			indicators for implied volatility.  Consequently the skew is constructed from out-of-the-money options, 
			(puts to the left, calls to the right.)  
			The hump between puts and calls 
			demonstrates a 
			divergence from <a href="http://en.wikipedia.org/wiki/Put%E2%80%93call_parity"> put-call parity</a>.
			
			In short, prices of put and call options at the same strike should theoretically 
			have the same implied volatility.  When the graph switches from puts to calls, 
			the options are implying a different underlier price than that used to calculate 
			the implied volatility.
			This is particularly noticeable when bid/ask spreads are wide, or trade
			information is dated, and the fair value of 
			both options and underlier is difficult to ascertain.  Infrequently traded options with wide bid/ask spreads
			tend to exhibit this problem, while popular, frequently traded options with tight spreads 
			will exhibit a smooth curve, with little to no visible "vol hump."  
			Since there are no professionals here, you should probably only be looking at the latter.
			</p>
		
		</div>
	
	</div>
</body>

<jsp:include page="/_scripts.jsp" />

</html>