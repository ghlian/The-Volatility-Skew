package org.vergeman.thevolskew.http;

public class YQLQueryString {

	/*
	 * ex: 
	 * http://query.yahooapis.com/v1/public/yql?q=SELECT%20*%20FROM%20yahoo.finance.options%20WHERE%20symbol%3D%22GS%22%20AND%20expiration%3D%222011-09%22&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys
	 * testing:
	 * http://query.yahooapis.com/v1/public/yql?q=use%20%22http%3A%2F%2Fthevolskew.appspot.com%2Fyahoo.finance.options.xml%22%20SELECT%20*%20FROM%20yahoo.finance.options%20WHERE%20symbol%3D%22GOOG%22%20AND%20expiration%3D%222012-03%22&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&format=json
	 */
	public static String BuildOptionQueryString (String contract) {
		String[] request = contract.split("\\s+");
		String ticker = request[0];
		String date = request[1];
		
		StringBuilder strBld = new StringBuilder();
		strBld.append("http://query.yahooapis.com/v1/public/yql?q=");
		strBld.append("use%20%22http%3A%2F%2Fthevolskew.appspot.com%2Fyahoo.finance.options.xml%22");
		strBld.append("SELECT%20*%20FROM%20yahoo.finance.options%20WHERE%20symbol%3D%22");
		//strBld.append("SELECT%20*%20FROM%20yahoo.finance.options%20WHERE%20symbol%3D%22");
		strBld.append(ticker);
		strBld.append("%22%20AND%20expiration%3D%22");
		strBld.append(format_date(date));
		strBld.append("%22&diagnostics=false&env=store%3A%2F%2Fdatatables.org%2" +
				"Falltableswithkeys&format=json&");

		return strBld.toString();
	}
	
	/*
	 * ex:
	 * http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22YHOO%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=cbfunc
	 */
	public static String BuildUnderlierQueryString (String contract) {
		String[] request = contract.split("\\s+");
		String ticker = request[0];
		StringBuilder strBld = new StringBuilder();
		
		strBld.append("http://query.yahooapis.com/v1/public/yql?q=");
		strBld.append("select%20*%20from%20yahoo.finance.quotes%20where%20symbol%20in%20(%22");
		strBld.append(ticker);
		strBld.append("%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
		
		return strBld.toString();
	}
	
	
	public static String BuildContractMonthQueryString (String ticker, String ip) {

		StringBuilder strBld = new StringBuilder();
		
		strBld.append("http://query.yahooapis.com/v1/public/yql?q=");
		//strBld.append("use%20%22http%3A%2F%2Fthevolskew.appspot.com%2Fyahoo.finance.option_contracts.xml%22%20as%20mytable%3B%20");
		//strBld.append("SELECT%20*%20FROM%20mytable%20WHERE%20symbol%20%3D%20'");
		strBld.append("SELECT%20*%20FROM%20yahoo.finance.option_contracts%20WHERE%20symbol%3D'");
		strBld.append(ticker);
		strBld.append("'&format=json");
		strBld.append("&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=cbfunc");
		return strBld.toString();
	}
	
	
	/*
	 * return MM-YYYY formatting
	 * input: mm-yy
	 * output: mm-yyyy
	 */
	private static String format_date(String date) {
		String[] date_split = date.split("-");
		String proper_date = date_split[1] + "-" + date_split[0];
		return proper_date;
	}
		
	
	
}
