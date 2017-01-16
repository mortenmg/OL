package test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class javatests {

	public static void main(String[] args) {
		double A = 1.344444;
		BigDecimal bd = new BigDecimal (A);
		bd = bd.setScale(2, RoundingMode.UP);
		System.out.println("Big decimal: " +bd);

	}

}
