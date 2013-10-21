package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public enum SalesGoal {
	GOAL_0(""), GOAL_50("yellow"), GOAL_75("orange"), GOAL_100("green");

	private String farge;
	
	private SalesGoal(String farge){
		this.farge=farge;
	}
	
	public static SalesGoal getSalesGoal(BigDecimal ownProduction,
			BigDecimal saleBudget) {
		if (saleBudget.intValue() != 0) {
			Integer procent = ownProduction.divide(saleBudget, 2,
					RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100)).intValue();
			return procent < 50 ? GOAL_0
					: procent >= 50 && procent < 75 ? GOAL_50 : procent >= 75
							&& procent < 100 ? GOAL_75 : GOAL_100;
		}
		return GOAL_0;
	}

	public String getFarge() {
		return farge;
	}
}
