package org.vergeman.thevolskew.option;

public class Model_Bjerksund {

	// define sensitivities
	final double TICK = 100.0;
	final double ZERO = 0.0000000001;

	final double SENS_DELTA = .1;
	final double SENS_VEGA = .01;
	final double SENS_GAMMA = .1;
	final double SENS_THETA = (double) 1 / 365;
	final double SENS_RHO = 0.01;
	final double SENS_OMEGA = 0.01;

	final double IMP_VOL_TOL = .0001;
	final int VOL_ITERATION_LIMIT = 10;

	/*
	 *  callput, stock, strike, riskfree rate, dividend yield, time to maturity
	 *  (years), stock volatility %
	 */


	public double price(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return BjerksundStensland(callput, assetP, strike, riskFree, zero(div),
				tmat, vol);
	}

	public double delta(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return (BjerksundStensland(callput, assetP + SENS_DELTA, strike,
						riskFree, zero(div), tmat, vol) - BjerksundStensland(
						callput, assetP - SENS_DELTA, strike, riskFree,
						zero(div), tmat, vol)) / (2 * SENS_DELTA);
	}

	public double gamma(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return (delta(callput, assetP + SENS_GAMMA, strike, riskFree,
				zero(div), tmat, vol) - delta(callput, assetP - SENS_GAMMA,
				strike, riskFree, zero(div), tmat, vol))
				/ (2 * SENS_GAMMA);
	}

	public double omega(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return (BjerksundStensland(callput, assetP, strike, riskFree, zero(div
				+ SENS_OMEGA), tmat, vol) - BjerksundStensland(callput, assetP,
				strike, riskFree, zero(div - SENS_OMEGA), tmat, vol)) / (2);
	}

	public double rho(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return (BjerksundStensland(callput, assetP, strike,
				riskFree + SENS_RHO, zero(div), tmat, vol) - BjerksundStensland(
				callput, assetP, strike, riskFree - SENS_RHO, zero(div), tmat,
				vol))
				/ (2 * SENS_RHO);
	}

	public double theta(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return TICK
				* (BjerksundStensland(callput, assetP, strike, riskFree,
						zero(div), tmat, vol) - BjerksundStensland(callput,
						assetP, strike, riskFree, zero(div), tmat + SENS_THETA,
						vol));
	}

	public double vega(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		
		return TICK
				* (BjerksundStensland(callput, assetP, strike, riskFree,
						zero(div), tmat, vol + SENS_VEGA) - BjerksundStensland(
						callput, assetP, strike, riskFree, zero(div), tmat, vol
								- SENS_VEGA)) / (2);

	}

	public double imp_vol(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double price,
			double calc_vol, int iterations) {
		
		double new_price = BjerksundStensland(callput, assetP, strike,
				riskFree, zero(div), tmat, calc_vol)
				- price;

		if ((Math.abs(new_price) > IMP_VOL_TOL)
				&& (iterations < VOL_ITERATION_LIMIT)) {
			// x1 = x - f(x1)/f'(x)
			return imp_vol(callput, assetP, strike, riskFree, zero(div), tmat,
					price, calc_vol
							- new_price
							/ vega(callput, assetP, strike, riskFree,
									zero(div), tmat, calc_vol), iterations + 1);
		}

		return calc_vol;
	}

	/* 
	 * 0 for put, 1 for call callput, stock, strike, riskfree rate, 
	 * dividend yield, time to maturity (years), stock volatility %
	 */

	public double BjerksundStensland(int callput, double assetP, double strike,
			double riskFree, double div, double tmat, double vol) {
		double Binfinity, BB, dt;
		double ht, I;

		double Alpha, Beta, b1;
		double rr, dd = 0, dd2, assetnew, drift, v2;
		double z;

		if (callput == 1) {
			z = 1;
			rr = riskFree;
			dd2 = div;
		} else {
			z = -1;
			rr = div;
			dd = rr;
			dd2 = 2 * dd - rr;
			assetnew = assetP;
			assetP = strike;
			strike = assetnew;

		}

		dt = vol * Math.sqrt(tmat);
		drift = riskFree - div;
		v2 = Math.pow(vol, 2);

		if (z * (riskFree - div) >= rr) {
			if (callput != 1) {
				return European_Call(assetP, strike, rr, div, tmat, vol);
			} else {
				return European_Call(assetP, strike, rr, dd, tmat, vol);
			}

		} else {
			b1 = Math.sqrt(Math.pow((z * drift / v2 - 0.5), 2) + 2 * rr / v2);
			Beta = ((double) 1 / 2 - (z * (drift / v2))) + b1;
			Binfinity = Beta / (Beta - 1) * strike;
			BB = Math.max(strike, rr / dd2 * strike);
			ht = -(z * drift * tmat + 2 * dt) * BB / (Binfinity - BB);
			I = BB + (Binfinity - BB) * (1 - Math.exp(ht));
			Alpha = (I - strike) * Math.pow(I, -Beta);

			if (assetP >= I) {
				return assetP - strike;
			} else {
				return Alpha * Math.pow(assetP, Beta) - Alpha
						* phi(assetP, tmat, Beta, I, I, rr, z * drift, vol)
						+ phi(assetP, tmat, 1, I, I, rr, z * drift, vol)
						- phi(assetP, tmat, 1, strike, I, rr, z * drift, vol)
						- strike
						* phi(assetP, tmat, 0, I, I, rr, z * drift, vol)
						+ strike
						* phi(assetP, tmat, 0, strike, I, rr, z * drift, vol);
			}

		}

	}

	public double phi(double S, double T, double gamma, double H, double I,
			double r, double A, double V) {

		double lambda, k, dd, dt;
		dt = V * Math.sqrt(T);

		lambda = (-1.0 * r + (gamma * A) + (0.5 * gamma * (gamma - 1.0) * Math
				.pow(V, 2.0)))
				* T;

		dd = -(Math.log(S / H) + (A + (gamma - 0.5) * Math.pow(V, 2)) * T) / dt;
		k = 2 * A / Math.pow(V, 2) + (2 * gamma - 1);
		return Math.exp(lambda)
				* Math.pow(S, gamma)
				* (cumnormsDist(dd) - Math.pow((I / S), k)
						* cumnormsDist(dd - 2.0 * Math.log(I / S) / dt));

	}

	public double European_Call(double S, double X, double r, double D,
			double T, double V) {

		double dt;
		double d1, d2;
		double Nd1, Nd2;

		dt = V * Math.sqrt(T);

		d1 = (Math.log(S / X) + (r - D + 0.5 * Math.pow(V, 2)) * T) / dt;
		
		d2 = d1 - dt;

		Nd1 = cumnormsDist(d1);
		Nd2 = cumnormsDist(d2);

		return (Math.exp(-D * T) * S * Nd1) - (X * Math.exp(-r * T) * Nd2);
	}

	public double cumnormsDist(double z) {
		// Standard Normal *Cumulative* Distribution Approximation
		// overflow handling
		if (z > 6.0) {
			return 1.0;
		}
		if (z < -6.0) {
			return 0.0;
		}

		// via Abramowitz & Stegun
		double b1 = 0.31938153;
		double b2 = -0.356563782;
		double b3 = 1.781477937;
		double b4 = -1.821255978;
		double b5 = 1.330274429;
		double p = 0.2316419;
		double c2 = 0.3989423;

		double a = Math.abs(z);
		double t = 1.0 / (1.0 + a * p);
		double b = c2 * Math.exp((-z) * (z / 2.0));
		double n = ((((b5 * t + b4) * t + b3) * t + b2) * t + b1) * t;
		n = 1.0 - b * n;
		if (z < 0.0) {
			n = 1.0 - n;
		}
		return n;
	}

	public double Max(double x1, double x2) {
		if (x1 > x2)
			return x1;
		else
			return x2;
	}

	public double zero(double value) {
		if (value <= 0) {
			return ZERO;
		}
		return value;
	}
}
