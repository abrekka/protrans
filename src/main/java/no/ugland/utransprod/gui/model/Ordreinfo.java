package no.ugland.utransprod.gui.model;

public class Ordreinfo {

	private String ordrenr;
	private Integer linjenr;
	private String beskrivelse;
	private Integer vegghoyde;
	private Integer murhoyde;
	private String prodno;
	private String type;
	private Integer purcno;
	private Integer prcatno;
	private Integer free4;
	private Integer prCatNo2;
	private String inf;

	public Ordreinfo(String ordrenr, Integer linjenr, String beskrivelse, Integer vegghoyde, Integer murhoyde,
			String prodno, String type, Integer purcno, Integer prcatno, Integer free4, Integer prCatNo2,String inf) {
		this.ordrenr = ordrenr;
		this.linjenr = linjenr;
		this.beskrivelse = beskrivelse;
		this.vegghoyde = vegghoyde;
		this.murhoyde = murhoyde;
		this.prodno = prodno;
		this.type = type;
		this.purcno = purcno;
		this.prcatno = prcatno;
		this.free4 = free4;
		this.prCatNo2 = prCatNo2;
		this.inf=inf;
	}
	public String getInf() {
		return inf;
	}

	public Integer getPrCatNo2() {
		return prCatNo2;
	}

	public Integer getLinjenr() {
		return linjenr;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public Integer getVegghoyde() {
		return vegghoyde;
	}

	public Integer getMurhoyde() {
		return murhoyde;
	}

	public String getProdno() {
		return prodno;
	}

	public String getType() {
		return type;
	}

	public Integer getPurcno() {
		return purcno;
	}

	public Integer getPrcatno() {
		return prcatno;
	}

	public Integer getFree4() {
		return free4;
	}
}
