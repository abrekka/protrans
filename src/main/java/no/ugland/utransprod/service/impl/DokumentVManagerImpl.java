package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.DokumentVDAO;
import no.ugland.utransprod.model.DokumentV;
import no.ugland.utransprod.service.DokumentVManager;

public class DokumentVManagerImpl implements DokumentVManager {
	private DokumentVDAO dao;

	public DokumentVDAO getDao() {
		return dao;
	}

	public void setDao(DokumentVDAO dao) {
		this.dao = dao;
	}

	public List<DokumentV> finnDokumenter(String orderNr) {
		return dao.finnDokumenter(orderNr);
	}

	

}
