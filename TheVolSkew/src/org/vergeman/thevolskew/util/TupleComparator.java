package org.vergeman.thevolskew.util;

import java.util.Comparator;

public class TupleComparator implements Comparator<double[]> {

	@Override
	public int compare(double[] o1, double[] o2) {
		if (o1[0] > o2[0]) return 1;
		if (o1[0] < o2[0]) return -1;
		return 0;
	}

	
}
