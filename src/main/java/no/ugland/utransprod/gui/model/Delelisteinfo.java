package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;

public class Delelisteinfo {

	private String ordrenummer;
	private BigDecimal antall;
	private Integer prodtp;
	private String txt;
	private Integer prodtp2;
	private String enhet;
	private String kundenavn;
	private String sted;
	private String garasjetype;
	private String beskrivelse;
	private String nummer;
	private String informasjon;
	private String prodno;
	private Integer prCatNo2;

	public Delelisteinfo(String ordrenummer, String kundenavn, String sted, String garasjetype, BigDecimal antall,
			Integer prodtp, String txt, Integer prodtp2, String enhet,String beskrivelse,String nummer,String informasjon,String prodno,Integer prCatNo2) {
		this.prCatNo2=prCatNo2;
		this.prodno=prodno;
		this.informasjon=informasjon;
		this.nummer=nummer;
		this.beskrivelse=beskrivelse;
		this.ordrenummer = ordrenummer;
		this.kundenavn = kundenavn;
		this.sted = sted;
		this.antall = antall;
		this.prodtp = prodtp;
		this.txt = txt;
		this.prodtp2 = prodtp2;
		this.enhet = enhet;
		this.garasjetype = garasjetype;
	}
	public Integer getPrCatNo2() {
		return prCatNo2;
	}
	public String getProdno() {
		return prodno;
	}
	public String getInformasjon() {
		return informasjon;
	}
	public String getNummer() {
		return nummer;
	}
	public String getBeskrivelse() {
		return beskrivelse;
	}

	public String getGarasjetype() {
		return garasjetype;
	}

	public String getSted() {
		return sted;
	}

	public String getKundenavn() {
		return kundenavn;
	}

	public String getTxtGruppe() {
		return "" + prodtp2 + " [" + txt + "]";
	}

	public String getTxt() {
		return txt;
	}

	public Integer getProdtp2() {
		return prodtp2;
	}

	public String getEnhet() {
		return enhet;
	}

	public String getOrdrenummer() {
		return ordrenummer;
	}

	public BigDecimal getAntall() {
		return antall;
	}

	public Integer getProdtp() {
		return prodtp;
	}

}
