package org.vergeman.thevolskew.option;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

public class Option_data {
	
	String ticker;
	String contract;
	String expiration;
	String contract_month; //ticker + " " + yyyy-mm
	
	double strike;
	String type; //p / c
	
	double bid = -1.0;
	double ask = -1.0;
	double mid = -1.0;
	double last = -1.0;

	String pattern = "\\d{6}(C|P)";
	Pattern p = Pattern.compile(pattern);
	Matcher m;
	
	//TODO: extend a model class for varying models
	/*
	 * TODO: make pattern static external to class?
	 * can be expensive to compile same regEx every time
	 */

	Model_Bjerksund model;
	
	public Option_data(JsonObject optionsChain_obj, JsonObject options_obj) {

		//optionsChain info
		if (!optionsChain_obj.get("symbol").isJsonNull() ) {
			this.ticker = optionsChain_obj.get("symbol").getAsString();
		}

		//options info
		if(!options_obj.get("symbol").isJsonNull()) {
			this.contract = options_obj.get("symbol").getAsString();
		}
		
		m = p.matcher(this.contract);
		if ( m.find() ) {
			//TODO: Fixing expiration
			String temp_date = m.group(0);	
			this.expiration = "20" + temp_date.substring(0, 2) + "-" 
				+ temp_date.substring(2, 4) + "-"
				+ temp_date.substring(4, 6);
				
			String[] temp = this.expiration.split("-");
			this.contract_month = this.ticker + " " + temp[1] + "-" + temp[0];
		}
		else { //if (!optionsChain_obj.get("expiration").isJsonNull() ) {
			this.expiration = optionsChain_obj.get("expiration").getAsString();
			String[] temp = this.expiration.split("-");
			this.contract_month = this.ticker + " " + temp[1] + "-" + temp[0];
		}
		
		
		
		if(!options_obj.get("strikePrice").isJsonNull()) {
			this.strike = options_obj.get("strikePrice").getAsDouble();
		}		
		if(!options_obj.get("type").isJsonNull()) {
			this.type = options_obj.get("type").getAsString();
		}
		if(!options_obj.get("bid").isJsonNull()) {
			Double temp = options_obj.get("bid").getAsDouble();
			if (temp.isNaN() || temp.isInfinite() ) {
				this.bid = -1;
			}
			else {
				this.bid = options_obj.get("bid").getAsDouble();
			}
		}
		if(!options_obj.get("ask").isJsonNull() ) {
			Double temp =options_obj.get("ask").getAsDouble();
			if (temp.isNaN() || temp.isInfinite() ) {
				this.ask = -1; 
			}
			else {
				this.ask = temp;
			}
		}
		if(!options_obj.get("lastPrice").isJsonNull()) {
			Double temp =options_obj.get("lastPrice").getAsDouble();
			if (temp.isNaN() || temp.isInfinite() ) {
				this.last = -1;
			}
			else {
				this.last = options_obj.get("lastPrice").getAsDouble();
			}
		}
		
		if (this.bid > -1.0 && this.ask > -1.0) {
			this.mid = (this.bid + this.ask) /2;
		}
		
		model = new Model_Bjerksund();
		
	}
	
	
	public boolean isWeekly() {
		/*options expire on 3rd Friday of each month - no weeklys 
		 * during the week of canonical option expiration.
		 * Thus, if day is expiration than 2 weeks out in month, must be same week
		 * NB: Display the last weekly of the month for 'spot' month representation
		 */
		if (Integer.parseInt(this.expiration.substring(8, 10)) < 14 )
				//|| Integer.parseInt(this.expiration.substring(8, 10)) > 21)
		{
			return true;
		}
		return false;
	}
	
	
	public double calcDelta(String callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		// translate call put
		int _callput = 0;
		if (callput.equals("C")) {
			_callput = 1;
		}
		return model.delta(_callput, assetP, strike, riskFree, div, tmat, vol);
	}
	
	public double calcImpVol(String callput, double price, double strike, 
						double ir,  double div_yield, double tmaturity, 
						double option_price, double start_vol, int iterations) {
	//handle bad inputs	
	if (price == -1.0 || price == 0.0) {
		return -1.0;
	}
	if (Double.isNaN(option_price)) {
		return -1.0;
	}
	//translate call put
	int _callput = 0;
	if (callput.equals("C")) {
		_callput = 1;
	}
	
	double vol =  Math.max( model.imp_vol(_callput, price, strike, ir, div_yield, tmaturity, 
					 option_price, start_vol, iterations), 0);
		
	if (Double.isInfinite(vol) || Double.isNaN(vol)) {
		return -1.0;
	}
	return vol;
	}

	
	public String getTicker() {
		return ticker;
	}
	public String getContract() {
		return contract;
	}
	public String getContractMonth() {
		return contract_month;
	}

	public double getStrike() {
		return strike;
	}
}
