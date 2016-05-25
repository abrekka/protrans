package no.ugland.utransprod.gui.model;

public class Ordrelinjeinfo{


	private String trInf3;
	private Integer prodTp2;
	private String articleName;
	private Integer numberOfItems;
	
	public Ordrelinjeinfo(String trInf3,Integer prodTp2,String articleName){
		this.trInf3=trInf3;
		this.prodTp2=prodTp2;
		this.articleName=articleName;
	}

	public String getTrInf3() {
		return trInf3;
	}

	public Integer getProdTp2() {
		return prodTp2;
	}

	public String getArticleName() {
		return articleName;
	}

	public Integer getNumberOfItems() {
		return numberOfItems;
	}

}
