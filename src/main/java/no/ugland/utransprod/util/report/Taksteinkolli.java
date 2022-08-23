package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Taksteinkolli {
	public static final String SKARPNES_DOBBEL_EDEL_SORT = "SKARPNES DOBBEL EDEL SORT";
	private String beskrivelse;
	private BigDecimal antall;
	private String overordnetBeskrivelse;
	private String leveresFraLager;
	private boolean erTakstein;
	private Integer antallPrPall;
	private Integer antallPrPakke;

	public Taksteinkolli medBeskrivelse(String description) {
		this.beskrivelse = description;
		return this;
	}

	public Taksteinkolli medAntall(BigDecimal noinvoab) {
		this.antall = noinvoab;
		return this;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public BigDecimal getAntall() {
		return antall;
	}

	public String getAntallstreng() {
		String string = new DecimalFormat("###0").format(antall);
		return antall == null ? "" : string;
	}

	public Taksteinkolli medOverordnetBeskrivelse(String overordnetBeskrivelse) {
		this.overordnetBeskrivelse = overordnetBeskrivelse;
		return this;
	}

	public String getOverordnetBeskrivelse() {
		return overordnetBeskrivelse;
	}

	public Taksteinkolli medLeveresFraLager(String leveresFraLager) {
		this.leveresFraLager = leveresFraLager;
		return this;
	}

	public String getLeveresFraLager() {
		return leveresFraLager;
	}

	public String getPalleinfo() {
		if (erTakstein) {
			return antall == null ? "" : "(" + beregnAntallPaller() + "/" + beregnAntallPakker() + ")";
		}
		return "";
	}

	protected String beregnAntallPakker() {
		return antall == null ? null
				: String.valueOf(antall.remainder(BigDecimal.valueOf(antallPrPall))
						.divide(BigDecimal.valueOf(antallPrPakke), 0, RoundingMode.CEILING));
	}

	protected String beregnAntallPaller() {
		String valueOf = String.valueOf(antall.divide(BigDecimal.valueOf(antallPrPall), 0, RoundingMode.FLOOR));
		return antall == null ? null : valueOf;
	}

	public Taksteinkolli erTakstein(boolean erTakstein) {
		this.erTakstein = erTakstein;
		return this;
	}

	public Taksteinkolli antallPrPall(int antallPrPall) {
		this.antallPrPall = antallPrPall;
		return this;
	}

	public Taksteinkolli antallPrPakke(int antallPrPakke) {
		this.antallPrPakke = antallPrPakke;
		return this;
	}
}
