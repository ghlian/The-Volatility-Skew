package org.vergeman.thevolskew.option;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;

public class Stock_data {
	@Expose String ticker;
	@Expose double bid = -1.0;
	@Expose double bid_realtime = -1.0;
	@Expose double ask = -1.0;
	@Expose double ask_realtime = -1.0;
	@Expose double last = -1.0;
	@Expose double div_yield = 0.0;
	
	//use real-time as basis for stock prices
	public Stock_data(JsonObject quote_obj, boolean use_div_yield) {
		this.ticker = quote_obj.get("symbol").getAsString();
		
		if (!quote_obj.get("Bid").isJsonNull() ) {
			this.bid = quote_obj.get("Bid").getAsDouble();
		}
		if (!quote_obj.get("BidRealtime").isJsonNull() ) {
			this.bid_realtime = quote_obj.get("BidRealtime").getAsDouble();
		}
		if (!quote_obj.get("Ask").isJsonNull() ) {
			this.ask = quote_obj.get("Ask").getAsDouble();
		}		
		if (!quote_obj.get("AskRealtime").isJsonNull() ) {
			this.ask_realtime = quote_obj.get("AskRealtime").getAsDouble();
		}
		if (!quote_obj.get("LastTradePriceOnly").isJsonNull() ) {
			this.last = quote_obj.get("LastTradePriceOnly").getAsDouble();
		}
		if (use_div_yield && !quote_obj.get("DividendYield").isJsonNull() ) {
			this.div_yield = quote_obj.get("DividendYield").getAsDouble() / 100;
		}
		
		
	}

	public String getTicker() {
		return this.ticker;
	}
	
	public double getLast() {
		return last;
	}

	public void setLast(double last) {
		this.last = last;
	}
	//mid point calculation?
}
