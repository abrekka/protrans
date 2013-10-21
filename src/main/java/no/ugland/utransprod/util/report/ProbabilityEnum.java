package no.ugland.utransprod.util.report;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.util.Periode;

public enum ProbabilityEnum {
	PROBABILITY_OFFER(30, new Integer[] { 30 }, new Integer[] { 0 }, "Tilbud",
			"registered") {
		@Override
		public void addToSalesReportMap(
				Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
				SaleReportData sale, Periode periode) {
			addSaleToMap(reportMap, sale);

		}

	},
	PROBABILITY_ORDER(90, new Integer[] { 90 }, new Integer[] { 30 }, "Ordre",
			"orderDate") {
		@Override
		public void addToSalesReportMap(
				Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
				SaleReportData sale, Periode periode) {
			if (sale.getRegistered().after(periode.getStartDate())
					&& sale.getRegistered().before(periode.getEndDate())) {// tilbud
				// har
				// skjedd
				// samme
				// uke
				SaleReportData tmpSale = sale.cloneSaleData();
				tmpSale.setProbabilityEnum(ProbabilityEnum.PROBABILITY_OFFER);
				addSaleToMap(reportMap, tmpSale);
			}
			addSaleToMap(reportMap, sale);

		}
	},
	PROBABILITY_CONFRIM_ORDER(100, new Integer[] { 100 }, new Integer[] { 30 },
			"Avrop", "saledate") {
		@Override
		public void addToSalesReportMap(
				Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
				SaleReportData sale, Periode periode) {
			if (sale.getRegistered().after(periode.getStartDate())
					&& sale.getRegistered().before(periode.getEndDate())) {// tilbud
				// har
				// skjedd
				// samme
				// uke
				SaleReportData tmpSale = sale.cloneSaleData();
				tmpSale.setProbabilityEnum(ProbabilityEnum.PROBABILITY_OFFER);
				addSaleToMap(reportMap, tmpSale);
			}

			if (sale.getOrderDate() != null
					&& sale.getOrderDate().after(periode.getStartDate())
					&& sale.getOrderDate().before(periode.getEndDate())) {// ordre
				// har
				// skjedd
				// samme
				// uke
				SaleReportData tmpSale = sale.cloneSaleData();
				tmpSale.setProbabilityEnum(ProbabilityEnum.PROBABILITY_ORDER);
				addSaleToMap(reportMap, tmpSale);
			}

		}
	},
	PROBABILITY_UNKNOWN(-1, new Integer[] { 0 }, new Integer[] { 0 }, "Ukjent",
			null) {
		@Override
		public void addToSalesReportMap(
				Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
				SaleReportData sale, Periode periode) {
			// TODO Auto-generated method stub

		}
	};

	private int probability;
	private String typeName;
	private String dateString;
	private List<Integer> probabilityList;
	private List<Integer> notProbabilityList;

	private static void addSaleToMap(
			Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
			SaleReportData sale) {
		Set<SaleReportData> salesData = reportMap
				.get(sale.getProbabilityEnum());
		salesData = salesData == null ? new HashSet<SaleReportData>()
				: salesData;
		salesData.add(sale);
		reportMap.put(sale.getProbabilityEnum(), salesData);
	}

	private ProbabilityEnum(final int mainProbability,
			final Integer[] aProbabilityArray,
			final Integer[] aNotProbabilityArray, final String aTypeName,
			final String aDateString) {
		probability = mainProbability;
		probabilityList = Arrays.asList(aProbabilityArray);
		notProbabilityList = Arrays.asList(aNotProbabilityArray);
		typeName = aTypeName;
		dateString = aDateString;
	}

	public Integer getProbability() {
		return probability;
	}

	public List<Integer> getProbabilityList() {
		return probabilityList;
	}

	public String getTypeName() {
		return typeName;
	}

	public String getDateString() {
		return dateString;
	}

	public static String getTypeNameByProbability(int aProbability) {
		switch (aProbability) {
		case 30:
			return "Tilbud";
		case 90:
			return "Ordre";
		case 100:
			return "Avrop";
		default:
			return "";
		}
	}

	public static ProbabilityEnum getProbabilityEnum(int aProbability) {
		switch (aProbability) {
		case 30:
			return PROBABILITY_OFFER;
		case 90:
			return PROBABILITY_ORDER;
		case 100:
			return PROBABILITY_CONFRIM_ORDER;
		default:
			return ProbabilityEnum.PROBABILITY_UNKNOWN;
		}
	}

	public abstract void addToSalesReportMap(
			Map<ProbabilityEnum, Set<SaleReportData>> reportMap,
			SaleReportData sale, Periode periode);

	public static List<ProbabilityEnum> getAllSalesProbabilityEnums() {
		ProbabilityEnum[] array = new ProbabilityEnum[] { PROBABILITY_OFFER,
				PROBABILITY_ORDER };// ,ProbabilityEnum.PROBABILITY_CONFRIM_ORDER};
		return Arrays.asList(array);
	}

	public List<Integer> getNotProbabilityList() {
		return notProbabilityList;
	}
}
