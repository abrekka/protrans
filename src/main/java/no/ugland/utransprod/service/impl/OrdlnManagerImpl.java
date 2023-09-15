package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.List;
import no.ugland.utransprod.dao.OrdlnDAO;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.OrdlnManager;

public class OrdlnManagerImpl implements OrdlnManager {
	private OrdlnDAO dao;

	public final void setOrdlnDAO(OrdlnDAO aDao) {
		this.dao = aDao;
	}

	public final List<Ordln> findByOrderNr(String orderNr) {
		return this.dao.findByOrderNr(orderNr);
	}

	public final List<Ordln> findForFakturagrunnlag(String orderNr) {
		return this.dao.findForFakturagrunnlag(orderNr);
	}

	public BigDecimal getSumCstpr(String orderNr, Integer prCatNo) {
		return this.dao.getSumCstpr(orderNr, prCatNo);
	}

	public Ordln findByOrderNrProdCatNo(String orderNr, Integer prodCatNo, Integer prodCatNo2) {
		return this.dao.findByOrderNrProdCatNo(orderNr, prodCatNo, prodCatNo2);
	}

	public BigDecimal getSumCcstpr(String orderNr, Integer prodCatNo) {
		return this.dao.getSumCcstpr(orderNr, prodCatNo);
	}

	public Integer isOrderLineInStorage(Integer ordNo, Integer lnNo) {
		return ordNo == null ? null : this.dao.isOrderLineInStorage(ordNo, lnNo);
	}

	public Ord findOrdByOrderNr(String orderNr) {
		return this.dao.findOrdByOrderNr(orderNr);
	}

	public Ordln findByOrdnoAndPrCatNo2(Integer ordno, Integer prodCatNo2) {
		return this.dao.findByOrdnoAndPrCatNo2(ordno, prodCatNo2);
	}

	public List<Ordln> findCostLines(String orderNr) {
		return this.dao.findCostLines(orderNr);
	}

	public Ordln findByOrdNoAndLnNo(Integer ordNo, Integer lnNo) {
		return this.dao.findByOrdNoAndLnNo(ordNo, lnNo);
	}

	public Ord findByOrdNo(Integer ordNo) {
		return this.dao.findByOrdNo(ordNo);
	}

	public List<Ordln> findTaksteinInfo(String orderNr) {
		return this.dao.findTaksteinInfo(orderNr);
	}

	public List<Ordln> findOrdLnByOrdNo(Integer ordno) {
		return this.dao.findOrdLnByOrdNo(ordno);
	}

	public List<Ord> findInvoicedOrdByOrdernr(List<String> ordernr) {
		return this.dao.findInvoicedOrdByOrdernr(ordernr);
	}
}
