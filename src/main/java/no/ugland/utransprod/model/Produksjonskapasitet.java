package no.ugland.utransprod.model;

import java.math.BigDecimal;

public class Produksjonskapasitet {
	private Integer id;
	private Integer aar;
	private Integer uke;
	private String avdeling;
	private String navn;
	private BigDecimal timer;	
	private BigDecimal service;
	private BigDecimal innarbeiding;
	private BigDecimal avspasering;
	private BigDecimal lager;
	private BigDecimal annet;
	private BigDecimal hms;
	private BigDecimal estimert;
	private BigDecimal prodinternt;
	private BigDecimal prodestimert;
	private String kommentar;

	

	public Produksjonskapasitet(Integer aar,Integer uke, String avdeling, String navn, BigDecimal timer, BigDecimal service,
			BigDecimal innarbeiding, BigDecimal avspasering, BigDecimal lager, BigDecimal annet, BigDecimal hms,
			BigDecimal estimert, BigDecimal prodinternt, BigDecimal prodestimert, String kommentar) {
		super();
		this.aar = aar;
		this.uke = uke;
		this.avdeling = avdeling;
		this.navn = navn;
		this.timer = timer;
		this.service = service;
		this.innarbeiding = innarbeiding;
		this.avspasering = avspasering;
		this.lager = lager;
		this.annet = annet;
		this.hms = hms;
		this.estimert = estimert;
		this.prodinternt = prodinternt;
		this.prodestimert = prodestimert;
		this.kommentar = kommentar;
	}

	public Integer getAar() {
		return aar;
	}
	
	public void setAar(Integer aar) {
		this.aar = aar;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUke() {
		return uke;
	}

	public void setUke(Integer uke) {
		this.uke = uke;
	}

	public String getAvdeling() {
		return avdeling;
	}

	public void setAvdeling(String avdeling) {
		this.avdeling = avdeling;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public BigDecimal getTimer() {
		return timer;
	}

	public void setTimer(BigDecimal timer) {
		this.timer = timer;
	}

	public BigDecimal getService() {
		return service;
	}

	public void setService(BigDecimal service) {
		this.service = service;
	}

	public BigDecimal getInnarbeiding() {
		return innarbeiding;
	}

	public void setInnarbeiding(BigDecimal innarbeiding) {
		this.innarbeiding = innarbeiding;
	}

	public BigDecimal getAvspasering() {
		return avspasering;
	}

	public void setAvspasering(BigDecimal avspasering) {
		this.avspasering = avspasering;
	}

	public BigDecimal getLager() {
		return lager;
	}

	public void setLager(BigDecimal lager) {
		this.lager = lager;
	}

	public BigDecimal getAnnet() {
		return annet;
	}

	public void setAnnet(BigDecimal annet) {
		this.annet = annet;
	}

	public BigDecimal getHms() {
		return hms;
	}

	public void setHms(BigDecimal hms) {
		this.hms = hms;
	}

	public BigDecimal getEstimert() {
		return estimert;
	}

	public void setEstimert(BigDecimal estimert) {
		this.estimert = estimert;
	}

	public BigDecimal getProdinternt() {
		return prodinternt;
	}

	public void setProdinternt(BigDecimal prodinternt) {
		this.prodinternt = prodinternt;
	}

	public BigDecimal getProdestimert() {
		return prodestimert;
	}

	public void setProdestimert(BigDecimal prodestimert) {
		this.prodestimert = prodestimert;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}
	

}
