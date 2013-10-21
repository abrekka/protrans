package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.TakstolInfoVDAO;
import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.TakstolInfoV;
import no.ugland.utransprod.service.TakstolInfoVManager;

public class TakstolInfoVManagerImpl implements TakstolInfoVManager{
	private TakstolInfoVDAO dao;
	public void setTakstolInfoVDAO(TakstolInfoVDAO aDao){
		dao=aDao;
	}
	public List<TakstolInfoV> findByOrderNr(String orderNr) {
		return dao.findByOrderNr(orderNr);
	}
	public List<Object[]> summerArbeidsinnsats(String fraAarUke,
			String tilAarUke,TransportConstraintEnum transportConstraintEnum,String productArea) {
		return dao.summerArbeidsinnsats(fraAarUke,tilAarUke,transportConstraintEnum,productArea);
	}
	public List<Object[]> getBeregnetTidForOrdre(String fraAarUke,
			String tilAarUke, TransportConstraintEnum transportConstraintEnum,
			String productArea) {
		return dao.getBeregnetTidForOrdre(fraAarUke,
				tilAarUke, transportConstraintEnum,
				productArea);
	}

}
