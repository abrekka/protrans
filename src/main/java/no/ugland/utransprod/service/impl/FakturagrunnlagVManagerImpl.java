package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.FakturagrunnlagVDAO;
import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.service.FakturagrunnlagVManager;

public class FakturagrunnlagVManagerImpl implements FakturagrunnlagVManager {
    private FakturagrunnlagVDAO dao;

    public final void setFakturagrunnlagVDAO(final FakturagrunnlagVDAO aDao) {
	this.dao = aDao;
    }

    public List<FakturagrunnlagV> findFakturagrunnlag(Integer orderId) {
	return dao.finnFakturagrunnlag(orderId);
    }

}
