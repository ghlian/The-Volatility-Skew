package org.vergeman.thevolskew.test;

import org.vergeman.thevolskew.option.Model_Bjerksund;
import org.vergeman.thevolskew.util.DateHelpers;
public class Bjerksund_test {

	public static void main(String args[]) {
		Model_Bjerksund model = new Model_Bjerksund();
		// 0 for put, 1 for call
		int callput = 0;
		double assetP = 523.29;
		double strike = 500;
		double vol = 1.0;
		double option_price = 1.20;
		double div = 0.00;
		
		double riskFree= .0025;

		double tmat = DateHelpers.getDaysfromToday("2011-08-25") / 365.0;
		double calc_vol = 1.0;
		int iterations  = 0;

		System.out.println("tmat: " + tmat);
		
		double price = model.price(callput, assetP, strike, riskFree, div, tmat, vol);
		
		System.out.println("price: " + price);
		
		double euro_price = model.European_Call(assetP, strike, riskFree, div, tmat, vol);
		System.out.println("euro_price: " + euro_price);
		
		double imp_vol = model.imp_vol(callput, assetP, strike, 
				riskFree, div, tmat, option_price, calc_vol, iterations);
		
		System.out.println("imp_vol: "+ imp_vol);
	}
}
