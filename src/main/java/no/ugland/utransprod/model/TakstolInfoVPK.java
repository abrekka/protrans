package no.ugland.utransprod.model;

import java.io.Serializable;

public class TakstolInfoVPK implements Serializable{
	
	private String ordernr;
	private Integer lnno;
	
	
	public TakstolInfoVPK(){
		
	}


	public TakstolInfoVPK(String ordernr, Integer lnno) {
		super();
		this.ordernr = ordernr;
		this.lnno = lnno;
	}

	public Integer getLnno() {
		return lnno;
	}

	public void setLnno(Integer lnno) {
		this.lnno = lnno;
	}

	public String getOrdernr() {
		return ordernr;
	}

	public void setOrdernr(String ordernr) {
		this.ordernr = ordernr;
	}
}
