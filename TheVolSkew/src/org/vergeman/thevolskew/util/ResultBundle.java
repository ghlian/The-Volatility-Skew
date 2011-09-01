package org.vergeman.thevolskew.util;


import java.util.HashMap;
import java.util.TreeMap;

import org.vergeman.thevolskew.option.Stock_data;

import com.google.gson.annotations.Expose;

/*
 * a "container class" of final results
 * that houses data structures to be accessed 
 * by the server pages.
 * 
 */
public class ResultBundle {


	private TreeMap<String, double[][]> impvol_result_store;
	@Expose private HashMap<String, Stock_data> stock_map;
	@Expose private double[][][] vol_results;
	@Expose private String[] contract_order;
	@Expose private double[] scale;

	
	public ResultBundle(TreeMap<String, double[][]> impvol_result_store, 
			double[] scale,
			HashMap<String, Stock_data> stock_map,
			String[] contract_order ) {
	
		this.setImpvol_result_store(impvol_result_store);
		this.setScale(scale);
		this.setStock_map(stock_map);
		this.setContract_order(contract_order);
		
		updateResult();
		
	}

	public TreeMap<String, double[][]> getImpvol_result_store() {
		return impvol_result_store;
	}



	public void setImpvol_result_store(TreeMap<String, double[][]> impvolResultStore) {
		impvol_result_store = impvolResultStore;
	}



	private void updateResult() {
		int size = impvol_result_store.size();
		
		vol_results = new double[size][][];
		
		int i = 0;
		
		for (double[][] j : impvol_result_store.values()) {
				vol_results[i] = j;
				i++;
		}
		
	}



	public void setStock_map(HashMap<String, Stock_data> stock_map) {
		this.stock_map = stock_map;
	}



	public HashMap<String, Stock_data> getStock_map() {
		return stock_map;
	}



	public void setContract_order(String[] contract_order) {
		this.contract_order = contract_order;
	}



	public String[] getContract_order() {
		return contract_order;
	}



	public void setScale(double[] scale) {
		this.scale = scale;
	}



	public double[] getScale() {
		return scale;
	}

	
}
