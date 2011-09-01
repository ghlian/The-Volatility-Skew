package org.vergeman.thevolskew.option;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;

import org.vergeman.thevolskew.servlet.VolSkewRequestServlet;
import org.vergeman.thevolskew.util.DateHelpers;
import org.vergeman.thevolskew.util.TupleComparator;



public class ImpVolCalc {
	
	String contract_month;
	String ticker;
	
	double price_ask;
	double price_bid;
	double price_mid;
	double price_last;
	double price;
	
	int dte;
	double div_yield;
	
	double ir = 0.005;
	
	HashMap<String, ArrayList<Option_data>> option_month_map;
	
	int num_contracts;
	
	private static final Logger log = Logger
				.getLogger(VolSkewRequestServlet.class.getName());
	//contract: ticker + " " + yyyy-mm
	
	public ImpVolCalc(String contract_month, HashMap<String, Stock_data> stock_map, HashMap<String, ArrayList<Option_data>> option_month_map) {
		
		this.option_month_map = option_month_map;

		
		//'monthly 'data
		this.contract_month = contract_month;
		this.ticker = contract_month.split("\\s+")[0];
		
		//filter, generate appropriate underlier data
		this.price_bid = stock_map.get(ticker).bid_realtime;
		this.price_ask = stock_map.get(ticker).ask_realtime;
		this.price_mid = (this.price_ask + this.price_bid) /2;
		this.price_last = stock_map.get(ticker).last;
		
		//use last price on market close, lack of data
		if (this.price_mid == 0.0 || this.price_mid < 0) {
			this.price_mid = this.price_last;
		}
		
		this.price = choose_price(this.price_bid, this.price_ask, this.price_mid, this.price_last);
		
		this.div_yield = stock_map.get(ticker).div_yield;
		this.dte = DateHelpers.getDaysfromToday(option_month_map.get(contract_month).get(0).expiration);

		this.num_contracts = option_month_map.size();
	
	}
	
	//last seems to generally be the most "usable" price 
	private double choose_price(double price_bid, double price_ask, double price_mid, double price_last) {
		
		return price_last;
		/*
		if (price_last > 0 && price_last > price_bid && price_last < price_ask) {
			return price_last;
		}
		//if ask is closer to last traded (down move since last)
		if ( Math.abs(price_ask - price_last) < Math.abs(price_bid - price_last) ) {
			return price_ask;
		}
		//if bid is closer to last traded (up move since last)
		if ( Math.abs(price_bid - price_last) < Math.abs(price_ask - price_last) ) {
			return price_bid;
		}
		//otherwise use midpoint, or last traded
		return price_mid;
		*/
		
	}
	
	//Cacluates all impl vols per month (contract request)
	public double[] CalcImpVol(TreeMap<String,double[][]> impvol_result_store, boolean delta_toggle) {
		
		double imp_vol_ask = -1;
		double imp_vol_bid = -1;
		double imp_vol_mid = -1;
		double imp_vol_last = -1;
		
		TupleComparator comparator = new TupleComparator();
		List <double[]> ask_vols = new ArrayList<double[]>();
		List<double[]> bid_vols = new ArrayList<double[]>();
		List<double[]> mid_vols = new ArrayList<double[]>();
		List<double[]> last_vols = new ArrayList<double[]>();
		
		double final_strike;
		
		double max_strike = 0;
		double min_strike = Double.POSITIVE_INFINITY;
		double max_vol = 0;
		double min_vol = Double.POSITIVE_INFINITY;
		
		//for each contract (ie. APPL 09-2011), lets calc those options (each strike)
		for (Option_data option : option_month_map.get(contract_month) ) {
			
			/*
			 * deal only with OTM Strikes
			 * exclude weekly options
			 * iterations start at 0, count up to max
			 */

			if ( ((option.strike < price && option.type.equals("P")) ||
				  (option.strike > price && option.type.equals("C"))) && 
				  (!option.isWeekly() )
			) {
				
				
				if (option.ask != -1) {
					
					imp_vol_ask = option.calcImpVol(option.type, price, option.strike, ir, div_yield, 
												dte / 365.0, option.ask, 1, 0);
					
					if (clean(imp_vol_ask) != -1)  {
						final_strike = strike(price, option.strike, delta_toggle);
						double[] tuple = new double[]{final_strike, imp_vol_ask};
						ask_vols.add(tuple);
						
						max_strike = Math.max(final_strike, max_strike);
						min_strike = Math.min(final_strike, min_strike);
						max_vol = Math.max(imp_vol_ask, max_vol);
						min_vol = Math.min(imp_vol_ask, min_vol);
						
					}
					else {
						log.info("imp vol fail ask: " + option.ticker + 
								" " + String.valueOf(option.strike) + 
								" " + option.type);
					}
				
				}
				if(option.bid != -1) {
					
					imp_vol_bid = option.calcImpVol(option.type, price, option.strike, ir, div_yield, 
												dte / 365.0, option.bid, 1, 0);
				
					if (clean(imp_vol_bid) != -1)  {
						final_strike = strike(price, option.strike, delta_toggle);
						double[] tuple = new double[]{final_strike, imp_vol_bid};
						bid_vols.add(tuple);
						
						max_strike = Math.max(final_strike, max_strike);
						min_strike = Math.min(final_strike, min_strike);
						max_vol = Math.max(imp_vol_bid, max_vol);
						min_vol = Math.min(imp_vol_bid, min_vol);
					}
					else {
						log.info("imp vol fail bid: " + option.ticker + 
								" " + String.valueOf(option.strike) + 
								" " + option.type);
					}
				}
				if (option.mid != -1) {
				
					imp_vol_mid = option.calcImpVol(option.type, price, option.strike, ir, div_yield, 
												dte / 365.0, option.mid, 1, 0);
				
					if (clean(imp_vol_mid) != -1)  {
						final_strike = strike(price, option.strike, delta_toggle);
						double[] tuple = new double[]{final_strike, imp_vol_mid};
						mid_vols.add(tuple);
						
						//since mid is derived, we can skip max/min since it'll  be from 
						//other data
					}
					else {
						log.info("imp vol fail mid: " + option.ticker + 
								" " + String.valueOf(option.strike) + 
								" " + option.type);
					}
				}
				if (option.last != -1) {
				
					imp_vol_last = option.calcImpVol(option.type, price, option.strike, ir, div_yield, 
						dte / 365.0, option.last, 1, 0);
				
					if (clean(imp_vol_last) != -1)  {
						final_strike = strike(price, option.strike, delta_toggle);
						double[] tuple = new double[]{final_strike, imp_vol_last};
						last_vols.add(tuple);
						
						max_strike = Math.max(final_strike, max_strike);
						min_strike = Math.min(final_strike, min_strike);
						max_vol = Math.max(imp_vol_last, max_vol);
						min_vol = Math.min(imp_vol_last, min_vol);
					}
					else {
						log.info("imp vol fail last: " + option.ticker + 
								" " + String.valueOf(option.strike) + 
								" " + option.type);
					}
				}
				
				
			} //end valid strike check
		}//end loop
		
		Collections.sort(ask_vols, comparator);
		Collections.sort(bid_vols, comparator);
		Collections.sort(mid_vols, comparator);
		Collections.sort(last_vols, comparator);
		
		impvol_result_store.put(contract_month + " " + "ask", ask_vols.toArray(new double[ask_vols.size()][]));
		impvol_result_store.put(contract_month + " " + "bid", bid_vols.toArray(new double[bid_vols.size()][]));
		impvol_result_store.put(contract_month + " " + "mid", mid_vols.toArray(new double[mid_vols.size()][]));
		impvol_result_store.put(contract_month + " " + "last", last_vols.toArray(new double[last_vols.size()][]));
		
		return new double[]{min_strike, max_strike, min_vol, max_vol};
	}

	private double strike(double price, double strike, boolean delta_toggle) {
		if (delta_toggle) {
			return (strike - price)/price;
		}
		else {
			return strike;
		}
	}
	
	
	//TODO: refine error cases in model...
	private double clean(double v) {
		if (Double.isInfinite(v) || Double.isNaN(v) ||
				 v == -1 || v == 1 || v == 0 ) {
			
		} else {
			return v;
		}
		return -1;
	}
}
