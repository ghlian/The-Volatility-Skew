<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

	<jsp:include page="/_header.jsp" />

<title>The Latest Volatility Skew | About</title>

</head>




<body>

	<jsp:include page="/_nav.jsp" />


	<div id="contract_wrap">
	
		<jsp:include page="/_contract_form.jsp" />
		
	</div>
	
	<div id="main">
	
		<div id="content">
			<h1>What is this?</h1>

			<p class="content_txt">
			"The Volatility Skew" 
			visualizes market implied volatilities for 
			option contracts across strikes and maturities.

			Construct graphs for actively traded option contracts and
			combine them to suit your needs:
			<ul class="content_list">
				<li>Varying maturities</li>

				<p class="content_list_sub">
				Compare an underlier's term structure by 
				selecting different option maturities for the same stock.  
				For example, compare AAPL volatility in December against the April contract.
				</p>
				
				<li>Different underliers</li>
				<p class="content_list_sub">
				Visualize the volatility skew between two different stocks of the
				same maturity; for example, compare Dec. AAPL vs. Dec. MSFT.  
				Strikes (x-axis) are mapped to percentage distance from 
				the at-the-money when comparing among different underliers. 
				</p>
				
				<li>Bid / ask / last implied volatility curves</li>
				<p class="content_list_sub"> 
				Toggle between bid, offer, and last-traded volatility curves.
				Identify relationships among strikes and even hone in on 
				areas to watch for possible mis-pricings.
				</p>
				
			</ul>
			
			</p>
		
			<p class="content_txt">
			This project was created out of curiosity and for fun, given the absence of 
			a similarly quick/free resource on the interwebs.  
			</p>
			

			<h1>
		     . . . I don't get it.
			</h1>


			<p class="content_txt">
			Volatility is one of the primary inputs that determine the price of an option.  
			Traders typically maintain a volatility surface that maps volatilities across
			strikes (skew / smile) and maturities (term structure.)  
			These relationships help identify potential option mis-pricings and  
			arbitrage opportunities.  In essence, buying volatility low, selling it high, 
			while trading <a href="http://www.schwab.com/public/schwab/research_strategies/market_insight/investing_strategies/options/gamma_scalping.html">
			gamma</a> are approaches used to lock in "edge" and (hopefully) profit.
			</p>

			<h1>
		     . . . Tell me more.
			</h1>

			<p class="content_txt">
			The Volatility Skew is just a casual tool.  The resources below are good starting 
			points on volatility trading and general option theory.  Nattenberg, in particular, 
			is worthwhile, accessible, and essential reading.
			</p>
			
			<ul>
				<li>
				 Nattenberg, Sheldon.
				<a href="http://www.amazon.com/Option-Volatility-Pricing-Strategies-Techniques/dp/155738486X/ref=sr_1_1?ie=UTF8&qid=1314833634&sr=8-1">
				Option Volatility & Pricing.</a>
				</li>
				
				<li>
				Hull, John.
				<a href="http://www.amazon.com/Options-Futures-Other-Derivatives-6th/dp/0131499084/ref=sr_1_1?s=books&ie=UTF8&qid=1314833677&sr=1-1">
				Options, Futures and Other Derivatives.</a>
				</li>
				
				<li>Wikipedia. 
				<a href="http://en.wikipedia.org/wiki/Volatility_smile">
				Volatility Smile.</a>
				</li>
				
				<li>Wikipedia. 
				<a href="http://en.wikipedia.org/wiki/Volatility_arbitrage">
				Volatility Arbitrage.</a>
				
				<li>Wikipedia. 
				<a href="http://en.wikipedia.org/wiki/Greeks_(finance)">
				Greeks.</a>
				</li>
			</ul>
			
		</div>
		
	</div>

</body>

<jsp:include page="/_scripts.jsp" />

</html>