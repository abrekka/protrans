package no.ugland.utransprod.service.enums;

import java.util.Hashtable;
import java.util.Map;

public enum ProductAreaGroupEnum {
	TAKSTOL(300,"Takstol");
	private Integer deptNo;
	private String productAreaGroupName;
	private static Map<Integer,ProductAreaGroupEnum> productAreaGroups=new Hashtable<Integer, ProductAreaGroupEnum>();
	
	static{
		for(ProductAreaGroupEnum productArea:ProductAreaGroupEnum.values()){
			productAreaGroups.put(productArea.getDeptNo(), productArea);
		}
	}

	private ProductAreaGroupEnum(Integer deptno,String aProductAreaGroupName) {
		deptNo = deptno;
		productAreaGroupName=aProductAreaGroupName;
	}

	public Integer getDeptNo() {
		return deptNo;
	}

	public static ProductAreaGroupEnum getProductAreGroupByProdno(Integer productAreaNr) {
		return productAreaGroups.get(productAreaNr);
	}

	public String getProductAreaGroupName() {
		return productAreaGroupName;
	}
}
