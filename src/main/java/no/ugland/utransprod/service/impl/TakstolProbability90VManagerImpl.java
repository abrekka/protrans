package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.TakstolProbability90VDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.TakstolProbability90V;
import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.service.ImportOrderProb90VManager;
import no.ugland.utransprod.service.TakstolAllVManager;
import no.ugland.utransprod.service.enums.ProductAreaGroupEnum;
import no.ugland.utransprod.util.excel.AntallSum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.OrdreReserveTakstol;

public class TakstolProbability90VManagerImpl implements
		ImportOrderProb90VManager {

	public TakstolProbability90VDAO dao;
	private TakstolAllVManager takstolAllVManager;

	public List<TakstolProbability90V> findAllTakstoler() {
		return dao.findAllTakstoler();
	}

	public void setTakstolProbability90VDAO(
			TakstolProbability90VDAO takstolProbability90VDAO) {
		this.dao = takstolProbability90VDAO;
	}

	public CheckObject checkExcel(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> findByParams(ExcelReportSetting params)
			throws ProTransException {

		return getReportBasis();
	}

	private List<OrdreReserveTakstol> getReportBasis() {
		Set<OrdreReserveTakstol> ordreReserveList = new HashSet<OrdreReserveTakstol>();
		List<TakstolProbability90V> takstoler90 = findAllTakstoler();
		ordreReserveList.addAll(convertTakstoler(takstoler90));
		List<TakstolAllV> takstolProduksjonsliste = takstolAllVManager
				.findAllNotProduced();
		ordreReserveList
				.addAll(convertTakstolProduksjonsListe(takstolProduksjonsliste));
		List<OrdreReserveTakstol> list = new ArrayList(ordreReserveList);
		Collections.sort(list);
		return list;
	}

	private Collection<? extends OrdreReserveTakstol> convertTakstolProduksjonsListe(
			List<TakstolAllV> takstolProduksjonsliste) {
		List<OrdreReserveTakstol> ordreReserveList = new ArrayList<OrdreReserveTakstol>();
		if (takstolProduksjonsliste != null) {
			for (TakstolAllV order : takstolProduksjonsliste) {
				if (order.getProbability() == 100) {
					ordreReserveList.add(createOrdreReserveTakstol(order));
				}
			}
		}
		return ordreReserveList;
	}

	private OrdreReserveTakstol createOrdreReserveTakstol(TakstolAllV order) {
		OrdreReserveTakstol ordreReserveTakstol = new OrdreReserveTakstol();
		ordreReserveTakstol.setType("Avrop");
		ordreReserveTakstol
				.setProductAreaGroup(order.getProductAreaGroupName());
		ordreReserveTakstol
				.setCustomerNr(String.valueOf(order.getCustomerNr()));
		ordreReserveTakstol.setCustomerName(order.getCustomerName());
		ordreReserveTakstol.setOrderNr(order.getOrderNr());
		ordreReserveTakstol.setOwnProduction(getOwnProduction(order));
		ordreReserveTakstol.setDeliveryCost(order.getDeliveryCost());
		ordreReserveTakstol.setProductionDate(order.getProductionDate());
		ordreReserveTakstol.setTransportYear(order.getTransportYear());
		ordreReserveTakstol.setTransportWeek(order.getTransportWeek());
		return ordreReserveTakstol;
	}

	private BigDecimal getOwnProduction(TakstolAllV order) {
		return order.getProductAreaGroupName().equalsIgnoreCase("Takstol") ? order
				.getOwnProduction()
				: order.getOwnInternalProduction();
	}

	private List<OrdreReserveTakstol> convertTakstoler(
			List<TakstolProbability90V> takstoler) {
		List<OrdreReserveTakstol> ordreReserveList = new ArrayList<OrdreReserveTakstol>();
		if (takstoler != null) {
			for (TakstolProbability90V order : takstoler) {
				ordreReserveList.add(createOrdreReserveTakstol(order));
			}
		}
		return ordreReserveList;
	}

	private OrdreReserveTakstol createOrdreReserveTakstol(
			TakstolProbability90V order) {
		OrdreReserveTakstol ordreReserveTakstol = new OrdreReserveTakstol();
		ordreReserveTakstol.setType("Ordre");
		ordreReserveTakstol.setProductAreaGroup(ProductAreaGroupEnum
				.getProductAreGroupByProdno(order.getProductAreaNr())
				.getProductAreaGroupName());
		ordreReserveTakstol.setCustomerNr(order.getCustomerNr());
		ordreReserveTakstol.setCustomerName(order.getCustomerName());
		ordreReserveTakstol.setOrderNr(order.getNumber1());
		ordreReserveTakstol.setOwnProduction(order.getOwnProduction());
		ordreReserveTakstol.setDeliveryCost(order.getDeliveryCost());
		return ordreReserveTakstol;
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
		Map<Object, Object> reportMap = new Hashtable<Object, Object>();
		List<OrdreReserveTakstol> reportBasis = getReportBasis();
		reportMap.put("Basis", reportBasis);
		Map<String, AntallSum> reportSum = getReportSum(reportBasis);
		reportMap.put("Sum", reportSum);

		return reportMap;
	}

	private Map<String, AntallSum> getReportSum(
			List<OrdreReserveTakstol> reportBasis) {
		Map<String, AntallSum> basisMap = new Hashtable<String, AntallSum>();
		if (reportBasis != null) {
			for (OrdreReserveTakstol order : reportBasis) {
				String key = getKeyValue(order);
				AntallSum antallSum = basisMap.get(key);
				antallSum = antallSum == null ? new AntallSum() : antallSum;
				antallSum.incrementNumberOf();
				antallSum.add(order.getOwnProduction());
				basisMap.put(key, antallSum);
			}
		}
		return basisMap;
	}

	private String getKeyValue(OrdreReserveTakstol order) {
		if (order.getType().equalsIgnoreCase("Ordre")) {
			return "IkkeProsjektertEkstern";
		} else {// avrop
			if (!order.getProductAreaGroup().equalsIgnoreCase("Takstol")) {
				if (order.getOwnProduction().equals(BigDecimal.ZERO)) {
					return "IkkeProsjektertIntern";
				} else {
					return "ProsjektertIntern";
				}
			} else {
				return "ProsjektertEkstern";
			}
		}

	}

	public void setTakstolAllVManager(TakstolAllVManager takstolAllVManager) {
		this.takstolAllVManager = takstolAllVManager;
	}

}
