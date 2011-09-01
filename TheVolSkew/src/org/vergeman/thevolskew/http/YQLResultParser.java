package org.vergeman.thevolskew.http;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.vergeman.thevolskew.option.Stock_data;
import org.vergeman.thevolskew.option.Option_data;

public class YQLResultParser {
	Gson gson;
	JsonParser parser;
	JsonObject result_obj;
	JsonObject temp_obj;
	
	public YQLResultParser() {
		gson = new Gson();
		parser = new JsonParser();
	}
	
	public void parse(String result, HashMap<String, Stock_data> stock_map,
			HashMap<String, ArrayList<Option_data>> option_month_map) {

		result_obj = parser.parse(result).getAsJsonObject().getAsJsonObject(
				"query").getAsJsonObject("results");

		//STOCK RESULT
		if (identify(result_obj).equals("stock")) {
			JsonObject quote_obj = result_obj.getAsJsonObject("quote");

			Stock_data stock = new Stock_data(quote_obj, false);
			stock_map.put(stock.getTicker(), stock);

			//System.out.println("stock");
			
		//OPTION RESULT
		} else if (identify(result_obj).equals("options")) {
			JsonObject optionsChain_obj = result_obj
					.getAsJsonObject("optionsChain");

				// loop through each obj
				// get options obj
				Option_data option = null;
				ArrayList<Option_data> option_list = new ArrayList<Option_data>();

				String pattern = "\\D+\\d{6}(C|P)\\d{8}";
				Pattern p = Pattern.compile(pattern);

				for (Iterator<JsonElement> it = optionsChain_obj.get("option")
						.getAsJsonArray().iterator(); it.hasNext();) {

					option = new Option_data(optionsChain_obj, it.next().getAsJsonObject());

					// filter for 'same day / teenies'
					if (p.matcher(option.getContract()).matches()) {
						option_list.add(option);
					}
				}

				option_month_map.put(option.getContractMonth(), option_list);
				//System.out.println("option");
		} 
		//OPTION CONTRACT DOESN'T EXIST
		else {
			//System.out.println("no contract");
		}


	}
	
	private String identify(JsonObject result_obj) {
		temp_obj = result_obj.getAsJsonObject("quote");
		//is stock
		if (temp_obj != null) {
			//System.out.println( obj.get("symbol").getAsString() );
			return "stock";
		}
		else if (result_obj.getAsJsonObject("optionsChain").has("option") ) {
			return "options";
		}
		else {
			return "";
		}
	}
	
	
	
}
