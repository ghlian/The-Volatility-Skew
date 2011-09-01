<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<head>

	<jsp:include page="/_header.jsp" />

<title>The Latest Volatility Skew | Contact</title>

</head>




<body style="min-height:1000px;">

	<jsp:include page="/_nav.jsp" />


	<div id="contract_wrap">
	
		<jsp:include page="/_contract_form.jsp" />
		
	</div>
	
	<div id="main">
	
		<div id="content">
		
			<h1>Comments</h1>
			
			<p class="content_header">
			Why?
			</p>
			
			<p class="content_txt">
			This was a random project born from curiosity, and the desire to play with
			Java Servlets, Server Pages, and the Google App Engine; in addition to a curiosity 
			regarding the implied volatilities of equity contracts.  Hope you find it useful.
			</p>
		
			<p class="content_header">
			Source
			</p>
			
			<p class="content_txt">
			I welcome any pulls, fixes, suggestions, improvements, etc.  Complete source available 
			via github.
			</p>
			
			<p class="content_header">
			Comments
			</p>
			
			<p class="content_txt">
			Thoughts and criticisms are most welcome below.
			</p>
		</div>
	
	</div>
	
	<div id="disqus_thread">
	</div>
</body>

<jsp:include page="/_scripts.jsp" />


	<script type="text/javascript">
	    /* * * CONFIGURATION VARIABLES: EDIT BEFORE PASTING INTO YOUR WEBPAGE * * */
	    var disqus_shortname = 'thevolatilityskew'; // required: replace example with your forum shortname
	
	    /* * * DON'T EDIT BELOW THIS LINE * * */
	    (function() {
	        var dsq = document.createElement('script'); dsq.type = 'text/javascript'; dsq.async = true;
	        dsq.src = 'http://' + disqus_shortname + '.disqus.com/embed.js';
	        (document.getElementsByTagName('head')[0] || document.getElementsByTagName('body')[0]).appendChild(dsq);
	    })();
	</script>
	
	<noscript>Please enable JavaScript to view the <a href="http://disqus.com/?ref_noscript">comments powered by Disqus.</a></noscript>
	<a href="http://disqus.com" class="dsq-brlink">blog comments powered by <span class="logo-disqus">Disqus</span></a>

</html>