package org.vergeman.thevolskew.servlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.gson.*;

import org.vergeman.thevolskew.http.YQLRequest;
import org.vergeman.thevolskew.http.YQLQueryString;
import org.vergeman.thevolskew.http.YQLResultParser;

import org.vergeman.thevolskew.option.ImpVolCalc;
import org.vergeman.thevolskew.option.Stock_data;
import org.vergeman.thevolskew.option.Option_data;

import org.vergeman.thevolskew.util.ResultBundle;



import java.util.ArrayList;
import java.util.HashMap;

import java.util.TreeMap;

@SuppressWarnings("serial")
public class VolSkewRequestServlet extends HttpServlet {

	private static final Logger log = Logger
			.getLogger(VolSkewRequestServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {

		log.info("doGet called");

		//receive and parse GET params
	
		//returns null if donesn't exist
		String request_values = req.getParameter("request_data");

		
		if (request_values != null) {
			System.out.println(request_values);
			//TODO: error with infinity values in double
			Gson gson = new GsonBuilder().serializeSpecialFloatingPointValues().excludeFieldsWithoutExposeAnnotation().create();
			String[] contracts;
			
			try {
			contracts = gson.fromJson(request_values, String[].class);
			}
			catch (Exception e) {
				return;	
			}
		// get option data & parse
			log.info("ASYNC REQUEST");

			HashMap<String, Boolean> request_queries = new HashMap<String, Boolean>();
			
			//build qry strings
			//we map to pool all queries into one asynchronous queue of requests
			for (String contract : contracts) {
				request_queries.put(YQLQueryString.BuildUnderlierQueryString(contract), true);
				request_queries.put(YQLQueryString.BuildOptionQueryString(contract), true);
			}
			
			//request data
			YQLRequest data = new YQLRequest(request_queries);
			
			//parse and subsequently calc data on successful request

			if (data.request()) {
				YQLResultParser ResultParser = new YQLResultParser();
				HashMap<String, Stock_data> stock_data = new HashMap<String, Stock_data>();
				//contract, option data list
				HashMap<String, ArrayList<Option_data> > option_data = new HashMap<String, ArrayList<Option_data> >(); 
				
				//==PARSE (populates hashes)==
				for (String result : data.getResults()) {
					System.out.println(result);
					ResultParser.parse(result, stock_data, option_data);
				}
				
				//==CALC==
				//determine if different underliers exist, then use relative strike
				//distance for x-axis
				boolean delta_toggle = false;
				if (stock_data.keySet().size() > 1) {
					delta_toggle = true;
				}
				//by strike (per row) storage of results
				TreeMap<String, double[][]> impvol_result_store = new TreeMap<String, double[][]>();
				
				//per contract
				String[] contract_order = new String[option_data.keySet().size()];
				int contract_index = 0;

	
				double[] scale = new double[4];
				for (String contract : option_data.keySet()) {
					//calculate
					ImpVolCalc option_vol = new ImpVolCalc(contract, stock_data, option_data);
					scale = option_vol.CalcImpVol(impvol_result_store, delta_toggle);
					
					contract_order[contract_index] = contract;
					contract_index++;
				}
				
				//System.out.println(Runtime.getRuntime().totalMemory());
				
				ResultBundle result = new ResultBundle(impvol_result_store, scale, stock_data, contract_order);
				String json_result = gson.toJson(result);
				
				req.setAttribute("result", result);
				
				//remember uses treemap so vols are 'ask/bid/last/mid' - ordered by keys
				req.setAttribute("json_result", json_result);
				
				String destination = "/view.jsp";
				RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
			
				try {
					System.out.println("Forwarding");
					rd.forward(req, resp);
				} catch (ServletException e) {
					e.printStackTrace();
				}
			}

			
		}
		//no args case, will forward to splash page
		else {
			String destination = "/home.jsp";
			RequestDispatcher rd = getServletContext().getRequestDispatcher(destination);
			
			try {
				System.out.println("Forwarding Home");
				rd.forward(req, resp);
			} catch (ServletException e) {
				e.printStackTrace();
			}
			
		}
		

		

		//System.out.println("DONE");
	}


	
}
