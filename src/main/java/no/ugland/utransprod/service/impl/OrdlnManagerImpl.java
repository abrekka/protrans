package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.OrdlnDAO;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.OrdlnManager;

public class OrdlnManagerImpl implements OrdlnManager {
	private OrdlnDAO dao;

	/**
	 * @param aDao
	 */
	public final void setOrdlnDAO(final OrdlnDAO aDao) {
		this.dao = aDao;
	}

	public final List<Ordln> findByOrderNr(final String orderNr) {
		return dao.findByOrderNr(orderNr);
	}

	public final List<Ordln> findForFakturagrunnlag(final String orderNr) {
		return dao.findForFakturagrunnlag(orderNr);
	}

	public BigDecimal getSumCstpr(String orderNr, Integer prCatNo) {
		return dao.getSumCstpr(orderNr, prCatNo);
	}

	public Ordln findByOrderNrProdCatNo(String orderNr, Integer prodCatNo, Integer prodCatNo2) {
		return dao.findByOrderNrProdCatNo(orderNr, prodCatNo, prodCatNo2);
	}

	public BigDecimal getSumCcstpr(String orderNr, Integer prodCatNo) {
		return dao.getSumCcstpr(orderNr, prodCatNo);
	}

	public Integer isOrderLineInStorage(Integer ordNo, Integer lnNo) {
		return ordNo == null ? null : dao.isOrderLineInStorage(ordNo, lnNo);
	}

	public Ord findOrdByOrderNr(String orderNr) {
		return dao.findOrdByOrderNr(orderNr);
	}

	public Ordln findByOrdnoAndPrCatNo2(Integer ordno, Integer prodCatNo2) {
		return dao.findByOrdnoAndPrCatNo2(ordno, prodCatNo2);
	}

	public List<Ordln> findCostLines(String orderNr) {
		return dao.findCostLines(orderNr);
	}

	public Ordln findByOrdNoAndLnNo(Integer ordNo, Integer lnNo) {
		return dao.findByOrdNoAndLnNo(ordNo, lnNo);
	}

	public Ord findByOrdNo(Integer ordNo) {
		return dao.findByOrdNo(ordNo);
	}

	public List<Ordln> findTaksteinInfo(String orderNr) {
		return dao.findTaksteinInfo(orderNr);
	}

	public List<Ordln> findOrdLnByOrdNo(Integer ordno) {
		return dao.findOrdLnByOrdNo(ordno);
	}

}
