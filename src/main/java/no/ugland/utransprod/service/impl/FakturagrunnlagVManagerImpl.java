package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.FakturagrunnlagVDAO;
import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.service.FakturagrunnlagVManager;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

public class FakturagrunnlagVManagerImpl implements FakturagrunnlagVManager {
    private FakturagrunnlagVDAO dao;

    public final void setFakturagrunnlagVDAO(final FakturagrunnlagVDAO aDao) {
	this.dao = aDao;
    }

    public List<FakturagrunnlagV> findFakturagrunnlag(Integer orderId) {
	return dao.finnFakturagrunnlag(orderId);
    }

    public Integer finnBestillingsnrFrakt(Integer orderId) {
	List<FakturagrunnlagV> fakturagrunnlag = dao.finnFakturagrunnlag(orderId);
	FakturagrunnlagV fakturagrunnlagFrakt = Iterables.find(fakturagrunnlag, fraktlinje());
	return fakturagrunnlagFrakt == null ? null : fakturagrunnlagFrakt.getPurcno();
    }

    private Predicate<FakturagrunnlagV> fraktlinje() {
	return new Predicate<FakturagrunnlagV>() {

	    public boolean apply(FakturagrunnlagV grunnlag) {
		return grunnlag.erFrakt();
	    }
	};
    }

}
