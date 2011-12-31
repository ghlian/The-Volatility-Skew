package org.vergeman.thevolskew.servlet;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import javax.servlet.http.*;

import org.vergeman.thevolskew.http.YQLQueryString;
import org.vergeman.thevolskew.http.YQLRequest;

import com.google.gson.*;

/*
 * This servlet is used to query the available contract months
 * given a valid ticker (an ajax request will hit it)
 */
@SuppressWarnings("serial")
public class ContractMonthRequestServlet extends HttpServlet {
	private static final Logger log = Logger
	.getLogger(VolSkewRequestServlet.class.getName());
	
	private HashMap<String, Boolean> request_queries;
	
	String contract;
	String response;
	String result;
	String my_ip;
	String request_values;
	
	JsonParser parser;
	JsonArray result_array;
	
	YQLRequest data;
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		
		log.info("doGet called");
		Gson gson = new Gson();
		parser = new JsonParser();
		
		my_ip = req.getLocalAddr();
		request_values = req.getParameter("ticker");
		
		request_queries = new HashMap<String, Boolean>();
		
		if (request_values != null) {
			contract = gson.fromJson(request_values, String.class);
			request_queries.put(YQLQueryString.BuildContractMonthQueryString(contract, my_ip), true);
		}
		//request data
		YQLRequest data = new YQLRequest(request_queries);
		
		//parse
		if (data.request()) {

			result = data.getResults().get(0);
			result = result.substring(7, result.length()-2);
			System.out.println(result);
			try {
				result_array = parser.parse(result).getAsJsonObject()
						.getAsJsonObject("query").getAsJsonObject("results")
						.getAsJsonObject("option").getAsJsonArray("contract");
			} catch (Exception e) {
				System.out.println(e);
			}
			

			resp.setContentType("application/json");
			resp.getWriter().print(gson.toJson(result_array));
		}
		
		
		
	}
}
