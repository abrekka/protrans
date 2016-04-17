package no.ugland.utransprod.gui.model;

public class Ordreinfo {

	private String ordrenr;
	private Integer linjenr;
	private String beskrivelse;

	public Ordreinfo(String ordrenr,Integer linjenr,String beskrivelse){
		this.ordrenr=ordrenr;
		this.linjenr=linjenr;
		this.beskrivelse=beskrivelse;
	}
	
	public Integer getLinjenr() {
		return linjenr;
	}
	public String getBeskrivelse() {
		return beskrivelse;
	}
}
