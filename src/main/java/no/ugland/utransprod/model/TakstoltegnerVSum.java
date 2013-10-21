package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Collection;

public class TakstoltegnerVSum {

	private BigDecimal sumCostAmount;
	private Integer numberOf;
	private String trossDrawer;
	private String productAreaGroupName;

	public void generateSum(Collection<TakstoltegnerV> tegninger) {
		sumCostAmount=BigDecimal.ZERO;
		numberOf=tegninger.size();
		for(TakstoltegnerV tegner:tegninger){
			sumCostAmount=sumCostAmount.add(tegner.getCostAmount());
			trossDrawer=tegner.getTrossDrawer();
			productAreaGroupName=tegner.getProductAreaGroupName();
		}
		
	}

	public String getTrossDrawer() {
		return trossDrawer;
	}

	public BigDecimal getSumCostAmount() {
		return sumCostAmount!=null?sumCostAmount:BigDecimal.ZERO;
	}

	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}

	public Integer getNumberOf() {
		return numberOf!=null?numberOf:0;
	}

}
