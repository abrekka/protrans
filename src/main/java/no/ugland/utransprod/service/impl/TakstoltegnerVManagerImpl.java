package no.ugland.utransprod.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.TakstoltegnerVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.service.TakstoltegnerVManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

public class TakstoltegnerVManagerImpl implements TakstoltegnerVManager {

	private TakstoltegnerVDAO dao;

	public Map<TakstoltegnerVSum,Collection<TakstoltegnerV>> findByPeriode(Periode periode) {
		Collection<TakstoltegnerV> tegnere= dao.findByPeriode(periode);
		Multimap<String,TakstoltegnerV> tegnerMap=ArrayListMultimap.create();
		
		for(TakstoltegnerV tegner:tegnere){
			tegnerMap.put(tegner.getTrossDrawer()+tegner.getProductAreaGroupName(), tegner);
		}
		
		Map<TakstoltegnerVSum,Collection<TakstoltegnerV>> sumMap=Maps.newHashMap();
		
		for(String tegnerNavn:tegnerMap.keySet()){
			TakstoltegnerVSum takstoltegnerVSum= new TakstoltegnerVSum();
			takstoltegnerVSum.generateSum(tegnerMap.get(tegnerNavn));
			sumMap.put(takstoltegnerVSum, tegnerMap.get(tegnerNavn));
		}
		
		return sumMap;
	}

	public void setTakstoltegnerVDAO(TakstoltegnerVDAO dao) {
		this.dao = dao;
	}

	public CheckObject checkExcel(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> findByParams(ExcelReportSetting params)
			throws ProTransException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfoButtom(ExcelReportSetting params)
			throws ProTransException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfoTop(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Object, Object> getReportDataMap(ExcelReportSetting params)
			throws ProTransException {
		Map<TakstoltegnerVSum,Collection<TakstoltegnerV>> dataList=findByPeriode(params.getPeriode());
		Map<Object,Object> dataMap=Maps.newHashMap();
		dataMap.put("Reportdata", dataList);
		return dataMap;
	}

}
