package no.ugland.utransprod.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.TakstolAllVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.service.TakstolAllVManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.TakstolInterface;
import no.ugland.utransprod.util.TakstolUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.report.JiggReportData;

public class TakstolAllVManagerImpl implements TakstolAllVManager {
	private TakstolAllVDAO dao;

	public void setTakstolAllVDAO(TakstolAllVDAO aDao) {
		dao = aDao;
	}

	public Map<String, Map<String, Set<JiggReportData>>> findJiggReportDataByPeriode(
			Periode periode) {
		List<TakstolInterface> takstoler = dao.findProductionByPeriode(periode);

		takstoler = TakstolUtil.setRelated(takstoler, null, false);
		return generateReportData(takstoler, periode);
	}

	private Map<String, Map<String, Set<JiggReportData>>> generateReportData(
			List<TakstolInterface> takstoler, Periode periode) {
		Map<String, Map<String, Set<JiggReportData>>> reportMap = new Hashtable<String, Map<String, Set<JiggReportData>>>();

		for (TakstolInterface takstol : takstoler) {
			TakstolAllV takstolAllV = (TakstolAllV) takstol;
			addMainLine(takstolAllV, reportMap, periode);
			addRelatedLines(takstolAllV.getRelatedArticles(), periode,
					reportMap);

		}
		return reportMap;
	}

	private void addRelatedLines(List<TakstolInterface> relatedArticles,
			Periode periode,
			Map<String, Map<String, Set<JiggReportData>>> reportMap) {
		if (relatedArticles != null) {
			for (TakstolInterface takstol : relatedArticles) {
				TakstolAllV takstolAllV = (TakstolAllV) takstol;
				if (isProducedOnUnitInPeriode(takstolAllV, periode)) {
					buildJiggReportData(takstolAllV, reportMap, false);
				}
			}
		}

	}

	private void addMainLine(TakstolAllV takstolAllV,
			Map<String, Map<String, Set<JiggReportData>>> reportMap,
			Periode periode) {
		if (takstolAllV.getOrderId() != null) {// ikke dummy
			if (isProducedOnUnitInPeriode(takstolAllV, periode)) {
				buildJiggReportData(takstolAllV, reportMap, true);
			}
		}

	}

	private void buildJiggReportData(TakstolAllV takstolAllV,
			Map<String, Map<String, Set<JiggReportData>>> reportMap,
			boolean isMainLine) {
		Map<String, Set<JiggReportData>> jiggElements = getJiggElements(
				takstolAllV, reportMap);
		Set<JiggReportData> orders = getJiggOrders(takstolAllV, jiggElements);

		addJiggReportData(takstolAllV, orders, isMainLine);
		jiggElements.put(takstolAllV.getProductAreaGroupName()
				.equalsIgnoreCase("Takstol") ? "Ordre" : "Intern", orders);
		reportMap.put(takstolAllV.getProductionUnitName(), jiggElements);
	}

	private void addJiggReportData(TakstolAllV takstolAllV,
			Set<JiggReportData> orders, boolean isMainLine) {
		JiggReportData jiggReportData = new JiggReportData();
		jiggReportData.setOrderInfo(takstolAllV.getOrderNr() + " "
				+ takstolAllV.getCustomerDetails());
		jiggReportData.setStartDate(takstolAllV.getActionStarted());
		jiggReportData.setProduced(takstolAllV.getProduced());
		jiggReportData.setArticleName(takstolAllV.getArticleName()
				+ (takstolAllV.getNumberOfItems() != null ? "("
						+ takstolAllV.getNumberOfItems() + ")" : ""));
		if (isMainLine) {
			jiggReportData.setOwnProduction(takstolAllV.getOwnProduction());
		} else {
			jiggReportData.setOwnProductionInfo(takstolAllV.getOwnProduction());
		}
		if (isMainLine) {
			jiggReportData.setOwnInternalProduction(takstolAllV
					.getOwnInternalProduction());
		} else {
			jiggReportData.setOwnInternalProductionInfo(takstolAllV
					.getOwnInternalProduction());
		}
		jiggReportData.setPrice(takstolAllV.getCalculatedPrice());
		if (isMainLine || !orders.contains(jiggReportData)) {
			orders.add(jiggReportData);
		}
	}

	private Set<JiggReportData> getJiggOrders(TakstolAllV takstolAllV,
			Map<String, Set<JiggReportData>> jiggElements) {
		Set<JiggReportData> orders = jiggElements
				.get(takstolAllV.getProductAreaGroupName().equalsIgnoreCase(
						"Takstol") ? "Ordre" : "Intern");
		orders = orders == null ? new HashSet<JiggReportData>() : orders;
		return orders;
	}

	private Map<String, Set<JiggReportData>> getJiggElements(
			TakstolAllV takstolAllV,
			Map<String, Map<String, Set<JiggReportData>>> reportMap) {
		Map<String, Set<JiggReportData>> jiggElements = reportMap
				.get(takstolAllV.getProductionUnitName());
		jiggElements = jiggElements == null ? new Hashtable<String, Set<JiggReportData>>()
				: jiggElements;
		return jiggElements;
	}

	private boolean isProducedOnUnitInPeriode(TakstolAllV takstolAllV,
			Periode periode) {
		return takstolAllV.getProductionUnitName() != null
				&& takstolAllV.getProduced() != null
				&& Util.isDateInPeriode(takstolAllV.getProduced(), periode);
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

	public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
		Map<Object, Object> map = new Hashtable<Object, Object>();
		map.put("Rapport", findJiggReportDataByPeriode(params.getPeriode()));
		return map;

	}

	public List<TakstolAllV> findAllNotProduced() {
		return dao.findAllNotProduced();
	}

}
