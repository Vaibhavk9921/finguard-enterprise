package com.finguard.loan.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EmiCalculator {
	public static BigDecimal calculateEmi(BigDecimal principal, Double annualRate, Integer months) {
		double p = principal.doubleValue();
		double r = annualRate / 12 / 100;
		double emi = (p * r * Math.pow(1 + r, months)) / (Math.pow(1 + r, months) - 1);
		return BigDecimal.valueOf(emi).setScale(2, RoundingMode.HALF_UP);
	}
}