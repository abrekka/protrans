package no.ugland.utransprod.util.report;

import java.awt.Dimension;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.TransportLetterObject;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.DelAlt;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.DelAltManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

public abstract class AbstractTransportLetter implements TransportLetter {
	private static final Object POST_SHIPMNET_TEXT = " Etterlevering";

	private static final String STONE_COLLI_NAME = "Takstein";

	private static final String DIALOG_HEADING = "Fraktbrev";

	private static final int PAGES = 1;

	private static final String ORDER_TYPE_TEXT_PARAMETER = "order_type_text";

	private static final String SHOW_PLACEMENT_PARAMETER = "show_placement";
	private static final String UGLAND_LOGO_PARAMETER = "ugland_logo";

	private static final String IS_TROSS_PARAMETER = "is_tross";

	private ManagerRepository managerRepository;

	@Inject
	public AbstractTransportLetter(final ManagerRepository aManagerRepository) {
		managerRepository = aManagerRepository;
	}

	protected abstract void addSketchImageToParameters(Transportable transportable, Map<String, Object> parameters);

	protected abstract void addAssemblyInfoToHeading(Transportable transportable, StringBuilder heading);

	protected abstract String getOrderTypeText();

	protected abstract boolean shouldShowPlacement(Transportable transportable);

	protected abstract boolean isTross(Transportable transportable);

	public abstract IconEnum getLogoIcon();

	public void generateTransportLetter(final Transportable transportable, final WindowInterface owner) {
		StringBuilder heading = getHeading(transportable);

		Map<String, Object> parameters = getParameters(transportable, heading);

		printTransportLetterForTransportable(owner, transportable, parameters);

	}

	private void printTransportLetterForTransportable(final WindowInterface owner, Transportable transportable,
			Map<String, Object> parameters) {
		ReportViewer reportViewer = new ReportViewer(DIALOG_HEADING);

		WindowInterface window = getWindow(owner, reportViewer);

		generateAndShowReport(owner, transportable, parameters, reportViewer, window);
	}

	private void generateAndShowReport(final WindowInterface owner, Transportable transportable,
			Map<String, Object> parameters, ReportViewer reportViewer, WindowInterface window) {
		try {

			List<OrderLine> missing = transportable.getMissingCollies();
			ArrayListModel list = new ArrayListModel();

			String tilleggsordreOgEttersendinger = sjekkOmKundeHartilleggsordreEllerEttersendinger(transportable);

			Integer colliCount = prepareOrderLinesAndColliesAndGetNumberOfCollies(transportable, missing, list,
					tilleggsordreOgEttersendinger);

			reportViewer.generateProtransReport(new TransportLetterTableModel(list, colliCount), DIALOG_HEADING,
					ReportEnum.TRANSPORT_LETTER, parameters);
			window.pack();
			window.setSize(new Dimension(850, 700));
			Util.locateOnScreenCenter(window);
			window.setVisible(true);
		} catch (ProTransException e) {
			e.printStackTrace();
			Util.showErrorDialog(owner, "Feil", e.getMessage());
		}
	}

	private String sjekkOmKundeHartilleggsordreEllerEttersendinger(Transportable transportable) {
		List<Order> ikkeSendtForKunde = managerRepository.getOrderManager()
				.findNotSentByCustomerNr(transportable.getCustomer().getCustomerNr());
		ikkeSendtForKunde = Lists
				.newArrayList(Iterables.filter(ikkeSendtForKunde, utenGjeldendeOrdre(transportable.getOrderNr())));
		String ordreNr = "";
		if (!ikkeSendtForKunde.isEmpty()) {
			ordreNr = Joiner.on(",").join(Iterables.transform(ikkeSendtForKunde, tilOrdreNr()));
		}

		List<PostShipment> ettersendingerForKunde = managerRepository.getPostShipmentManager()
				.findByCustomerNr(transportable.getCustomer().getCustomerNr());

		ettersendingerForKunde = Lists.newArrayList(
				Iterables.filter(ettersendingerForKunde, ikkeSendtUtenGjeldende(transportable.getPostShipment())));
		if (!ettersendingerForKunde.isEmpty()) {
			if (!ordreNr.isEmpty()) {
				ordreNr = ordreNr + ",";
			}
			ordreNr = ordreNr
					+ Joiner.on(",").join(Iterables.transform(ettersendingerForKunde, tilOrdreNrFraEttersending()));
		}
		if (!ordreNr.isEmpty()) {
			ordreNr = "Kunde har tillegsordre eller ettersendinger: " + ordreNr;
		}
		return ordreNr;
	}

	private Function<PostShipment, String> tilOrdreNrFraEttersending() {
		return new Function<PostShipment, String>() {

			public String apply(PostShipment ettersending) {
				return ettersending.getOrderNr() + "(ettersending)";
			}
		};
	}

	private Predicate<PostShipment> ikkeSendtUtenGjeldende(final PostShipment postShipment) {
		return new Predicate<PostShipment>() {

			public boolean apply(PostShipment ettersending) {
				return postShipment != null
						? !postShipment.getPostShipmentId().equals(ettersending.getPostShipmentId())
								&& !ettersending.getSentBool()
						: !ettersending.getSentBool();
			}
		};
	}

	private Function<Order, String> tilOrdreNr() {
		return new Function<Order, String>() {

			public String apply(Order ordre) {
				return ordre.getOrderNr();
			}
		};
	}

	private Predicate<Order> utenGjeldendeOrdre(final String ordrenr) {
		return new Predicate<Order>() {

			public boolean apply(Order order) {
				return !ordrenr.equals(order.getOrderNr())
						&& order.getConstructionType().getName().contains("Tilleggsordre");
			}
		};
	}

	private int prepareOrderLinesAndColliesAndGetNumberOfCollies(Transportable transportable, List<OrderLine> missing,
			ArrayListModel list, String tilleggsordre) {
		int colliCount = 0;
		for (int i = 1; i <= PAGES; i++) {
			colliCount = prepareOrdersAndCollies(transportable, missing, list, colliCount, i, tilleggsordre);
		}
		return colliCount;
	}

	private int prepareOrdersAndCollies(Transportable transportable, List<OrderLine> missing, ArrayListModel list,
			int colliCount, int i, String tilleggsordre) {
		Ord ord = managerRepository.getOrdlnManager().findOrdByOrderNr(transportable.getOrderNr());
		Integer bestillingsnrFrakt = managerRepository.getFakturagrunnlagVManager()
				.finnBestillingsnrFrakt(transportable.getOrder().getOrderId());
		String customerRef = ord != null ? ord.getYrRef() : null;

		List<Taksteinkolli> taksteinkolliInfo = genererTaksteinInfo(transportable);

//		if (!Hibernate.isInitialized(transportable.getCollies()) && Order.class.isInstance(transportable)) {
//			managerRepository.getOrderManager().lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] {
//					LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.COMMENTS });
//		} else if (!Hibernate.isInitialized(transportable.getCollies())
//				&& PostShipment.class.isInstance(transportable)) {
//			managerRepository.getPostShipmentManager().lazyLoad((PostShipment) transportable,
//					new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
//							LazyLoadPostShipmentEnum.COLLIES, LazyLoadPostShipmentEnum.ORDER_COMMENTS });
//		}

		list.addAll(prepareCollies(transportable.getCollies(), i, transportable, customerRef, bestillingsnrFrakt,
				taksteinkolliInfo, tilleggsordre));
		if (i == 1) {
			colliCount = list.size();
		}
		list.addAll(prepareOrderLines(missing, i, transportable, customerRef, bestillingsnrFrakt, taksteinkolliInfo,
				tilleggsordre));
		return colliCount;
	}

	private List<Taksteinkolli> genererTaksteinInfo(Transportable transportable) {
		List<Taksteinkolli> taksteinkolliList = Lists.newArrayList();
		if (Order.class.isInstance(transportable)) {
			Order order = (Order) transportable;
			OrderLine takstein = order.getOrderLine("Takstein");
			if (takstein != null) {
				managerRepository.getOrderLineManager().lazyLoad(takstein,
						new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				Ordln ordlnTakstein = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(takstein.getOrdNo(),
						takstein.getLnNo());
				if (ordlnTakstein != null) {
					takstein.setOrdln(ordlnTakstein);
				}
				List<Ordln> taksteinInfo = managerRepository.getOrdlnManager()
						.findTaksteinInfo(transportable.getOrderNr());
				String leveresFraLager = takstein.getAttributeValue("Sendes fra GG");
				for (Ordln ordln : taksteinInfo) {
					taksteinkolliList.add(new Taksteinkolli().medLeveresFraLager(leveresFraLager)
							.medOverordnetBeskrivelse(takstein.getDetailsWithoutNoAttributes())
							.medBeskrivelse(ordln.getDescription()).medAntall(ordln.getNoinvoab().add(ordln.getNofin()))
							.antallPrPall(hentAntallPrPall(ordln)).antallPrPakke(hentAntallPrPakke(ordln))
							.erTakstein(ordln.getProd() != null && ordln.getProd().getPrCatNo3() == 1));
				}
			}
		}
		return taksteinkolliList;
	}

	private int hentAntallPrPakke(Ordln ordln) {
		if (ordln.getProd() != null) {
			DelAltManager delAltManager = (DelAltManager) ModelUtil.getBean(DelAltManager.MANAGER_NAME);
			List<DelAlt> delaltlist = delAltManager.finnForProdno(ordln.getProd().getProdNo());
			if (delaltlist.size() == 1) {
				return delaltlist.get(0).getFree2();
			}
		}
		return 6;
	}

	private int hentAntallPrPall(Ordln ordln) {
		if (ordln.getProd() != null) {
			DelAltManager delAltManager = (DelAltManager) ModelUtil.getBean(DelAltManager.MANAGER_NAME);
			List<DelAlt> delaltlist = delAltManager.finnForProdno(ordln.getProd().getProdNo());
			if (delaltlist.size() == 1) {
				return delaltlist.get(0).getFree1();
			}
		}
		return 180;
	}

	private List<ReportObject> prepareOrderLines(Collection<OrderLine> orderLineList, Integer pageNumber,
			Transportable transportable, String customerRef, Integer bestilingsnrFrakt,
			List<Taksteinkolli> taksteinkolliInfo, String tilleggsordre) {
		ArrayList<ReportObject> orderLines = new ArrayList<ReportObject>();

		if (orderLineList != null) {
			for (OrderLine orderLine : orderLineList) {
				addOrderLine(pageNumber, transportable, orderLines, orderLine, customerRef, bestilingsnrFrakt,
						taksteinkolliInfo, tilleggsordre);
			}
		}
		return orderLines;
	}

	private void addOrderLine(Integer pageNumber, Transportable transportable, ArrayList<ReportObject> orderLines,
			OrderLine orderLine, String customerRef, Integer bestilingsnrFrakt, List<Taksteinkolli> taksteinkolliInfo,
			String tilleggsordre) {
		if (isStone(orderLine)) {
			Ordln ordln = this.managerRepository.getOrdlnManager().findByOrdNoAndLnNo(orderLine.getOrdNo(),
					orderLine.getLnNo());
			if (ordln != null) {
				orderLine.setOrdln(ordln);
			}
			lazyLoadAttributes(orderLine);
		}
		orderLines.add(new ReportObject(pageNumber, orderLine, transportable, customerRef, bestilingsnrFrakt,
				taksteinkolliInfo, tilleggsordre));
	}

	private boolean isStone(OrderLine orderLine) {
		return orderLine.getArticleName().equalsIgnoreCase(STONE_COLLI_NAME);
	}

	private void lazyLoadAttributes(OrderLine orderLine) {
		managerRepository.getOrderLineManager().lazyLoad(orderLine,
				new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
	}

	private List<ReportObject> prepareCollies(Collection<Colli> colliList, Integer pageNumber,
			Transportable transportable, String customerRef, Integer bestillingsnrFrakt,
			List<Taksteinkolli> taksteinkolliInfo, String tilleggsordre) {
		ArrayList<ReportObject> collies = new ArrayList<ReportObject>();
		if (colliList != null) {
			for (Colli colli : colliList) {
				if (!"Takstein".equalsIgnoreCase(colli.getColliName())) {
					if ("Gulvspon".equalsIgnoreCase(colli.getColliName())
							&& Hibernate.isInitialized(colli.getOrderLines())) {
						managerRepository.getColliManager().lazyLoadAll(colli);
					}
					addColli(pageNumber, transportable, collies, colli, customerRef, bestillingsnrFrakt,
							taksteinkolliInfo, tilleggsordre);
				}
			}
		}
		return collies;
	}

	private void addColli(Integer pageNumber, Transportable transportable, ArrayList<ReportObject> collies, Colli colli,
			String customerRef, Integer bestillingsnrFrakt, List<Taksteinkolli> taksteinkolliInfo,
			String tilleggsordre) {
		if (checkAndLazyLoadColli(colli)) {
			lazyLoadCollies(colli);

			collies.add(new ReportObject(pageNumber, colli, transportable, customerRef, bestillingsnrFrakt,
					taksteinkolliInfo, tilleggsordre));
		}
	}

	private void lazyLoadCollies(Colli colli) {
		// managerRepository.getOrderManager().lazyLoadOrder(colli.getOrder(),
		// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES });

		if (colli.getColliName().equalsIgnoreCase("Takstein")) {
			for (OrderLine orderLine : colli.getOrderLines()) {
				Ordln ordln = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(orderLine.getOrdNo(),
						orderLine.getLnNo());
				if (ordln != null) {
					orderLine.setOrdln(ordln);
				}
			}
		}
	}

	private boolean checkAndLazyLoadColli(Colli colli) {
		boolean addColli = true;
		// lazyLoadOrderLinesAndAttributes(colli);
		// dersom takstein ikke sendes fra GG
		if (colli.getColliDetails() == null) {
			addColli = false;
		}
		return addColli;
	}

	private void lazyLoadOrderLinesAndAttributes(Colli colli) {
		// managerRepository.getColliManager().lazyLoadAll(colli);

		// for (OrderLine orderLine : colli.getOrderLines()) {
		// managerRepository.getOrderLineManager().lazyLoad(
		// orderLine,
		// new LazyLoadOrderLineEnum[] {
		// LazyLoadOrderLineEnum.ORDER_LINES_ORDER_LINES,
		// LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
		// LoadOrder(
		// orderLine.getOrder(),
		// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
		// LazyLoadOrderEnum.ORDER_LINES,
		// LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
		// LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
		// }

	}

	private WindowInterface getWindow(final WindowInterface owner, ReportViewer reportViewer) {
		WindowInterface window = new JDialogAdapter(Util.getDialog(owner, "Fraktbrev", false));

		window.add(reportViewer.buildPanel(window));
		return window;
	}

	private Map<String, Object> getParameters(Transportable transportable, StringBuilder heading) {
		Map<String, Object> parameters = new Hashtable<String, Object>();

		addSketchImageToParameters(transportable, parameters);
		addHeadingToParameters(heading, parameters);
		addOrderTypeText(parameters);
		addShowPlacementToParameters(parameters, transportable);
		addLogoToParameters(parameters);
		addIsGarageToParameters(parameters, transportable);
		return parameters;
	}

	private void addLogoToParameters(Map<String, Object> parameters) {
		IconEnum iconEnum = getLogoIcon();
		InputStream iconStream = getClass().getClassLoader().getResourceAsStream(iconEnum.getIconPath());

		parameters.put(UGLAND_LOGO_PARAMETER, iconStream);

	}

	private void addShowPlacementToParameters(Map<String, Object> parameters, Transportable transportable) {
		parameters.put(SHOW_PLACEMENT_PARAMETER, shouldShowPlacement(transportable));

	}

	private void addIsGarageToParameters(Map<String, Object> parameters, Transportable transportable) {
		parameters.put(IS_TROSS_PARAMETER, isTross(transportable));

	}

	private void addOrderTypeText(Map<String, Object> parameters) {
		parameters.put(ORDER_TYPE_TEXT_PARAMETER, getOrderTypeText());

	}

	private void addHeadingToParameters(StringBuilder heading, Map<String, Object> parameters) {
		parameters.put("construction_type", heading.toString());
	}

	private StringBuilder getHeading(Transportable transportable) {
		StringBuilder heading = addOrderInfoToHeading(transportable);

		if (transportable instanceof Order) {
			// managerRepository.getOrderManager().lazyLoadTree((Order)
			// transportable);
			addAssemblyInfoToHeading(transportable, heading);

		} else {
			addPostShipmentInfoToHeading(transportable, heading);
		}
		return heading;
	}

	private void addPostShipmentInfoToHeading(Transportable transportable, StringBuilder heading) {
		// PostShipmentManager postShipmentManager = (PostShipmentManager)
		// ModelUtil.getBean("postShipmentManager");
		// postShipmentManager.lazyLoadTree((PostShipment) transportable);

		heading.append(POST_SHIPMNET_TEXT);
	}

	private StringBuilder addOrderInfoToHeading(Transportable transportable) {
		StringBuilder stringBuilder = new StringBuilder(
				transportable.getConstructionType().getName() + " " + transportable.getOrder().getInfo());
		return stringBuilder;
	}

	private class ReportObject {
		private Integer pageNumber;

		TransportLetterObject letterObject;

		private Transportable transportable;
		private String customerRef;

		private Integer bestillingsnrFrakt;
		private List<Taksteinkolli> taksteinkolliInfo;
		private String tilleggsordre;

		/**
		 * @param aPageNumber
		 * @param transportLetterObject
		 * @param aTransportable
		 */
		public ReportObject(Integer aPageNumber, TransportLetterObject transportLetterObject,
				Transportable aTransportable, String aCustomerRef, Integer bestillingsnrFrakt,
				List<Taksteinkolli> taksteinkolliInfo, String tilleggsordre) {
			this.tilleggsordre = tilleggsordre;
			this.taksteinkolliInfo = taksteinkolliInfo;
			customerRef = aCustomerRef;
			pageNumber = aPageNumber;
			letterObject = transportLetterObject;

			transportable = aTransportable;
			this.bestillingsnrFrakt = bestillingsnrFrakt;

		}

		public List<Taksteinkolli> getTaksteinkolliInfo() {
			return taksteinkolliInfo;
		}

		public String getCustomerRef() {
			return customerRef;
		}

		/**
		 * @return sidenummer
		 */
		public Integer getPageNumber() {
			return pageNumber;
		}

		/**
		 * @return order
		 */
		public Order getOrder() {
			return transportable.getOrder();
		}

		/**
		 * @return transportobjekt
		 */
		public Transportable getTransportable() {
			return transportable;
		}

		/**
		 * @return fraktbrevobjekt
		 */
		public TransportLetterObject getTransportLetterObject() {
			return letterObject;
		}

		public Integer getBestillingsnrFrakt() {
			return bestillingsnrFrakt;
		}

		public String getTilleggsordre() {
			return tilleggsordre;
		}
	}

	private final class TransportLetterTableModel extends AbstractTableAdapter {
		private static final long serialVersionUID = 1L;

		private Integer colliCount;

		/**
		 * @param listModel
		 * @param aColliCount
		 */
		public TransportLetterTableModel(ListModel listModel, Integer aColliCount) {
			super(listModel, TransportLetterColumn.getColumnNames());
			colliCount = aColliCount;
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int column) {
			ReportObject reportObject = (ReportObject) getRow(row);

			String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
			return TransportLetterColumn.valueOf(columnName).getValue(reportObject, colliCount);

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
			String columnName = StringUtils.upperCase(getColumnName(column)).replaceAll(" ", "_");
			return TransportLetterColumn.valueOf(columnName).getColumnClass();
		}

	}

	private enum TransportLetterColumn {
		TAKSTEINKOLLIINFO("taksteinkolliinfo") {
			@Override
			public Class<?> getColumnClass() {
				return List.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getTaksteinkolliInfo();
			}
		},
		ADDRESS("address") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getDeliveryAddress();
			}
		},
		FIRST_NAME("first_name") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getCustomer().getFirstName();
			}
		},
		LAST_NAME("last_name") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getCustomer().getLastName();
			}
		},
		POSTAL_CODE("postal_code") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getPostalCode();
			}
		},
		POST_OFFICE("post_office") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getPostOffice();
			}
		},
		ORDER_NR("order_nr") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getOrderNr();
			}
		},
		CUSTOMER_NR("customer_nr") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getCustomer().getCustomerNr();
			}
		},
		PHONE_NR("phone_nr") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getTelephoneNr();
			}
		},
		NUMBER_OF_COLLI("number_of_colli") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return colliCount;
			}
		},
		COLLI_NAME("colli_name") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				if ("Gulvspon".equalsIgnoreCase(letterObject.getName()) && Colli.class.isInstance(letterObject)) {
					if (!((Colli) letterObject).getOrderLines().isEmpty()) {
						return Util.upperFirstLetter(letterObject.getName() + " - "
								+ ((Colli) letterObject).getOrderLines().iterator().next().getNumberOfItems());// +
					}
				}
				return Util.upperFirstLetter(letterObject.getName());// +
				// (letterObject.getNumberOf()
				// != null
				// ? " - "
				// +
				// letterObject.getNumberOf()
				// : ""));
			}
		},
		NUMBER_OF("number_of") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getNumberOf();
			}
		},
		LENGHT("lenght") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getLenght();
			}
		},
		WIDTH("width") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getWidht();
			}
		},
		HEIGHT("height") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getHeight();
			}
		},
		COLLI_DETAILS("colli_details") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getDetails();
			}
		},
		ELEMENT_NAME("element_name") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.getTypeName();
			}
		},
		IS_NOT_POST_SHIPMENT("is_not_post_shipment") {
			@Override
			public Class<?> getColumnClass() {
				return Boolean.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				TransportLetterObject letterObject = reportObject.getTransportLetterObject();
				return letterObject.isNotPostShipment();
			}
		},
		REPORT_NUMBER("report_number") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getPageNumber();
			}
		},
		DRIVER_COMMENT("driver_comment") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Transportable transportable = reportObject.getTransportable();
				if (transportable.getDeviation() != null
						&& !Hibernate.isInitialized(transportable.getDeviation().getOrderComments())) {
					DeviationManager deviationManager = (DeviationManager) ModelUtil
							.getBean(DeviationManager.MANAGER_NAME);
					deviationManager.lazyLoad(transportable.getDeviation(),
							new LazyLoadDeviationEnum[] { LazyLoadDeviationEnum.COMMENTS });
				}

				if (!Hibernate.isInitialized(transportable.getOrderComments())) {
					// if (transportable instanceof PostShipment) {
					// PostShipmentManager postShipmentManager =
					// (PostShipmentManager) ModelUtil
					// .getBean("postShipmentManager");
					// postShipmentManager.lazyLoad((PostShipment)
					// transportable,
					// new LazyLoadPostShipmentEnum[] {
					// LazyLoadPostShipmentEnum.ORDER_COMMENTS });
					// } else {
					if (transportable instanceof Order) {
						OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");

						orderManager.lazyLoadOrder((Order) transportable,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });

					}
				}

				String comments = transportable.getTransportComments();
				return comments == null ? reportObject.getTilleggsordre()
						: comments + " " + reportObject.getTilleggsordre();
			}
		},
		TELEPHONE_NR_SITE("telephone_nr_site") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				Order order = reportObject.getOrder();
				return order.getTelephoneNrSite();
			}
		},
		CUSTOMER_REF("customer_ref") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getCustomerRef();
			}
		},
		BESTILLING_NR_FRAKT("bestilling_nr_frakt") {
			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getBestillingsnrFrakt();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		},
		TRANSPORT_FIRMA("transport_firma") {
			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getTransportable().getTransport().getSupplier() == null ? null
						: reportObject.getTransportable().getTransport().getSupplier().getSupplierName();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		TRANSPORT_DRIVER("transport_driver") {
			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getTransportable().getTransport().getDriver();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		TRANSPORT_WEEK("transport_week") {
			@Override
			public Object getValue(ReportObject reportObject, Integer colliCount) {
				return reportObject.getTransportable().getTransport().getTransportWeek() + " - "
						+ reportObject.getTransportable().getTransport().getTransportYear();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		};
		private String columnName;

		private TransportLetterColumn(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public abstract Object getValue(ReportObject reportObject, Integer colliCount);

		public abstract Class<?> getColumnClass();

		public static String[] getColumnNames() {
			TransportLetterColumn[] transportLetterColumns = TransportLetterColumn.values();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 0; i < transportLetterColumns.length; i++) {
				columnNameList.add(transportLetterColumns[i].getColumnName());
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}
	}

}
