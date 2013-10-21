package no.ugland.utransprod.model;

import java.math.BigDecimal;

/**
 * Brukes til å aggregere informasjon om ordre sin status
 * 
 * @author atle.brekka
 * @see no.ugland.utransprod.dao.hibernate.OrderStatusNotSentVDAOHibernate#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
 * 
 */
public class StatusOrdersNotSent {
	/**
	 * 
	 */
	private Integer countNoPacklist;

	/**
	 * 
	 */
	private BigDecimal garageValueNoPacklist;

	/**
	 * 
	 */
	private Integer countNotReady;

	/**
	 * 
	 */
	private BigDecimal garageValueNotReady;

	/**
	 * 
	 */
	private Integer countNotSent;

	/**
	 * 
	 */
	private BigDecimal garageValueNotSent;

	/**
	 * 
	 */
	public StatusOrdersNotSent() {
		super();
	}

	/**
	 * @param countNoPacklist
	 * @param garageValueNoPacklist
	 * @param countNotReady
	 * @param garageValueNotReady
	 * @param countNotSent
	 * @param garageValueNotSent
	 */
	public StatusOrdersNotSent(Integer countNoPacklist,
			BigDecimal garageValueNoPacklist, Integer countNotReady,
			BigDecimal garageValueNotReady, Integer countNotSent,
			BigDecimal garageValueNotSent) {
		super();
		this.countNoPacklist = countNoPacklist;
		this.garageValueNoPacklist = garageValueNoPacklist;
		this.countNotReady = countNotReady;
		this.garageValueNotReady = garageValueNotReady;
		this.countNotSent = countNotSent;
		this.garageValueNotSent = garageValueNotSent;
	}

	/**
	 * @return antall uten pakkliste
	 */
	public Integer getCountNoPacklist() {
		return countNoPacklist;
	}

	/**
	 * @param countNoPacklist
	 */
	public void setCountNoPacklist(Integer countNoPacklist) {
		this.countNoPacklist = countNoPacklist;
	}

	/**
	 * @return garasjeverdi for ordre uten pakkliste
	 */
	public BigDecimal getGarageValueNoPacklist() {
		return garageValueNoPacklist;
	}

	/**
	 * @param garageValueNoPacklist
	 */
	public void setGarageValueNoPacklist(BigDecimal garageValueNoPacklist) {
		this.garageValueNoPacklist = garageValueNoPacklist;
	}

	/**
	 * @return antall ikke klare
	 */
	public Integer getCountNotReady() {
		return countNotReady;
	}

	/**
	 * @param countNotReady
	 */
	public void setCountNotReady(Integer countNotReady) {
		this.countNotReady = countNotReady;
	}

	/**
	 * @return garasjeverdi for ordre som ikke er klare
	 */
	public BigDecimal getGarageValueNotReady() {
		return garageValueNotReady;
	}

	/**
	 * @param garageValueNotReady
	 */
	public void setGarageValueNotReady(BigDecimal garageValueNotReady) {
		this.garageValueNotReady = garageValueNotReady;
	}

	/**
	 * @return antall ikke sendt
	 */
	public Integer getCountNotSent() {
		return countNotSent;
	}

	/**
	 * @param countNotSent
	 */
	public void setCountNotSent(Integer countNotSent) {
		this.countNotSent = countNotSent;
	}

	/**
	 * @return garasjeverdi for ordre som ikke er sendt
	 */
	public BigDecimal getGarageValueNotSent() {
		return garageValueNotSent;
	}

	/**
	 * @param garageValueNotSent
	 */
	public void setGarageValueNotSent(BigDecimal garageValueNotSent) {
		this.garageValueNotSent = garageValueNotSent;
	}
}
