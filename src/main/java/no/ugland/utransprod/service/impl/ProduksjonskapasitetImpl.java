package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.ProductAreaDAO;
import no.ugland.utransprod.dao.ProduksjonskapasitetDAO;
import no.ugland.utransprod.model.Produksjonskapasitet;
import no.ugland.utransprod.service.ProduksjonskapasitetManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;

public class ProduksjonskapasitetImpl implements ProduksjonskapasitetManager{
	private ProduksjonskapasitetDAO dao;
	
	public void setDao(ProduksjonskapasitetDAO dao) {
		this.dao = dao;
	}

	public List<Produksjonskapasitet> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Produksjonskapasitet> findByObject(Produksjonskapasitet var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void removeObject(Produksjonskapasitet var1) {
		// TODO Auto-generated method stub
		
	}

	public void saveObject(Produksjonskapasitet produksjonskapasitet) throws ProTransException {
		dao.saveObject(produksjonskapasitet);
		
	}

	public void refreshObject(Produksjonskapasitet var1) {
		// TODO Auto-generated method stub
		
	}

	public Produksjonskapasitet merge(Produksjonskapasitet var1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void lazyLoad(Produksjonskapasitet var1, LazyLoadEnum[][] var2) {
		// TODO Auto-generated method stub
		
	}

}
