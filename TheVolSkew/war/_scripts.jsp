<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.6.2/jquery.min.js"></script>

<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.16/jquery-ui.min.js"></script>

<!--[if IE]>
<script type="text/javascript" src="/scripts/excanvas/excanvas.min.js"></script>
<![endif]-->

<script type="text/javascript" src="/scripts/flot/jquery.flot.min.js"></script>

<script type="text/javascript" src="/scripts/flot/jquery.flot.navigate.min.js"></script>
	
<script type="text/javascript" src="/scripts/flot/jquery.flot.resize.min.js"></script>
	
<!-- start Mixpanel -->
<script type="text/javascript">
  var mpq=[];mpq.push(["init","c9e9176ec00d35a07e9b1a3a54b9bc71"]);(function(){var b,a,e,d,c;b=document.createElement("script");b.type="text/javascript";b.async=true;b.src=(document.location.protocol==="https:"?"https:":"http:")+"//api.mixpanel.com/site_media/js/api/mixpanel.js";a=document.getElementsByTagName("script")[0];a.parentNode.insertBefore(b,a);e=function(f){return function(){mpq.push([f].concat(Array.prototype.slice.call(arguments,0)))}};d=["init","track","track_links","track_forms","register","register_once","identify","name_tag","set_config"];for(c=0;c<d.length;c++){mpq[d[c]]=e(d[c])}})();
</script>
<!-- end Mixpanel -->


<!--  google a-->
<script type="text/javascript">

  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-25364891-1']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();

</script>




<!--  UDF scripts -->
<script type="text/javascript" src="/scripts/chart_flot.js"></script>
<script type="text/javascript" src="/scripts/request.js"></script>
<script  type="text/javascript" src="/scripts/autocomplete.js"></script>


<script type="text/javascript">
	var contract_count = ${fn:length(result.contract_order)};
	var contract_request = '"${fn:join(result.contract_order,"\",\"")}"';
</script>