package no.ugland.utransprod.util.report;

import java.math.BigDecimal;

public class Taksteinkolli {
    private String beskrivelse;
    private BigDecimal antall;
    private String overordnetBeskrivelse;
    private String leveresFraLager;

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
}
