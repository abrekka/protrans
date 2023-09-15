/*     */ package no.ugland.utransprod.gui.model;

/*     */
/*     */ import java.math.BigDecimal;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.model.Colli;
/*     */ import no.ugland.utransprod.model.Order;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.PackableListItem;
/*     */ import no.ugland.utransprod.model.PostShipment;
/*     */ import no.ugland.utransprod.model.Transport;
/*     */ import no.ugland.utransprod.service.ColliManager;
/*     */ import no.ugland.utransprod.service.IApplyListManager;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ import no.ugland.utransprod.service.OrderLineManager;
/*     */ import no.ugland.utransprod.service.OrderManager;
/*     */ import no.ugland.utransprod.service.PostShipmentManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadEnum;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
/*     */ import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.UserUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import org.hibernate.Hibernate;

/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */
/*     */ public class PackageApplyList extends AbstractApplyList<PackableListItem> {
	/*     */ protected String colliName;
	/*     */ private String windowAccessName;
	/*     */ private ReportEnum reportEnum;
	/*     */ protected ManagerRepository managerRepository;

	/*     */
	/*     */ public PackageApplyList(Login login, IApplyListManager<PackableListItem> manager, String aColliName,
			String aWindowAccessName, ReportEnum aReportEnum, VismaFileCreator vismaFileCreator,
			ManagerRepository aManagerRepository) {
		/* 49 */ super(login, manager, vismaFileCreator);
		/* 50 */ this.managerRepository = aManagerRepository;
		/* 51 */ this.colliName = aColliName;
		/* 52 */ this.windowAccessName = aWindowAccessName;
		/* 53 */ this.reportEnum = aReportEnum;
		/* 54 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void setApplied(PackableListItem object, boolean applied, WindowInterface window) {
		/* 64 */ if (object != null) {
			/* 65 */ OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
			/* 66 */ OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
			/* 67 */ if (orderLine != null) {
				/*     */
				/* 69 */ this.handleApply(applied, window, orderLineManager, orderLine, (String) null, true);
				/*     */
				/* 71 */ this.checkAndSavePostShipment(orderLine);
				/*     */
				/* 73 */ this.saveOrder(window, orderLineManager, orderLine);
				/* 74 */ this.applyListManager.refresh(object);
				/*     */ }
			/*     */ }
		/*     */
		/* 78 */ }

	/*     */
	/*     */
	/*     */
	/*     */ protected OrderLine handleApply(boolean applied, WindowInterface window,
			OrderLineManager orderLineManager, OrderLine orderLine, String aColliName, boolean isMainArticle) {
		/* 83 */ OrderLine appliedOrderLine = null;
		/*     */ try {
			/* 85 */ if (applied) {
				/* 86 */ appliedOrderLine = orderLine.getColli() != null ? null
						: this.setObjectApplied(orderLineManager, orderLine, aColliName);
				/*     */
				/*     */
				/*     */
				/* 90 */ } else if (this.ignoreVismaFile(orderLine, window, isMainArticle)) {
				/* 91 */ this.setObjectUnapplied(orderLineManager, orderLine);
				/*     */
				/*     */
				/*     */ }
			/* 95 */ } catch (ProTransException var9) {
			/* 96 */ Util.showErrorDialog(window, "Feil", var9.getMessage());
			/* 97 */ var9.printStackTrace();
			/*     */ }
		/* 99 */ return appliedOrderLine;
		/*     */ }

	/*     */
	/*     */
	/*     */ private void setObjectUnapplied(OrderLineManager orderLineManager, OrderLine orderLine)
			throws ProTransException {
		/* 104 */ orderLine.setColli((Colli) null);
		/* 105 */ orderLineManager.saveOrderLine(orderLine);
		/* 106 */ this.checkCompleteness(orderLine, false);
		/* 107 */ }

	/*     */
	/*     */
	/*     */ private OrderLine setObjectApplied(OrderLineManager orderLineManager, OrderLine orderLine,
			String aColliName) throws ProTransException {
		/* 111 */ ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
		/*     */
		/* 113 */ String currentColliName = aColliName != null ? aColliName : this.colliName;
		Colli colli;
		/* 114 */ if (orderLine.getPostShipment() != null) {
			/* 115 */ colli = colliManager.findByNameAndPostShipment(currentColliName, orderLine.getPostShipment());
			/*     */ } else {
			/* 117 */ colli = colliManager.findByNameAndOrder(currentColliName, orderLine.getOrder());
			/*     */ }
		/* 119 */ if (colli == null) {
			/* 120 */ if (orderLine.getPostShipment() != null) {
				/* 121 */ colli = new Colli((Integer) null, (Order) null, currentColliName, (Transport) null,
						(Date) null, (Set) null, orderLine.getPostShipment(), (Integer) null, Util.getCurrentDate(),
						"PackageApplyList");
				/*     */
				/*     */ } else {
				/* 124 */ colli = new Colli((Integer) null, orderLine.getOrder(), currentColliName, (Transport) null,
						(Date) null, (Set) null, (PostShipment) null, (Integer) null, Util.getCurrentDate(),
						"PackageApplyList");
				/*     */
				/*     */
				/*     */ }
			/* 128 */ } else if (!Hibernate.isInitialized(colli.getOrderLines())) {
			/* 129 */ colliManager.lazyLoad(colli,
					new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
			/*     */ }
		/*     */
		/* 132 */ colli.addOrderLine(orderLine);
		/* 133 */ colli.setPackageDate(Util.getCurrentDate());
		/* 134 */ colliManager.saveColli(colli);
		/*     */
		/* 136 */ orderLine.setColli(colli);
		/*     */
		/* 138 */ orderLineManager.saveOrderLine(orderLine);
		/* 139 */ this.checkCompleteness(orderLine, true);
		/* 140 */ return orderLine;
		/*     */ }

	/*     */
	/*     */ protected void saveOrder(WindowInterface window, OrderLineManager orderLineManager, OrderLine orderLine) {
		/* 144 */ OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		/* 145 */ Order order = orderLine.getOrder();
		/*     */
		/* 147 */ orderManager.settStatus(order.getOrderId(), (String) null);
		/*     */
		/*     */ try {
			/* 150 */ orderLineManager.saveOrderLine(orderLine);
			/* 151 */ } catch (ProTransException var7) {
			/* 152 */ Util.showErrorDialog(window, "Feil", var7.getMessage());
			/* 153 */ var7.printStackTrace();
			/*     */ }
		/* 155 */ }

	/*     */
	/*     */ protected void checkAndSavePostShipment(OrderLine orderLine) {
		/* 158 */ PostShipment postShipment = orderLine.getPostShipment();
		/* 159 */ if (postShipment != null) {
			/* 160 */ postShipment.setStatus((String) null);
			/* 161 */ PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
					.getBean("postShipmentManager");
			/* 162 */ postShipmentManager.savePostShipment(postShipment);
			/*     */ }
		/* 164 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ protected void checkCompleteness(OrderLine orderLine, boolean applied) throws ProTransException {
		/* 174 */ if (orderLine != null) {
			/* 175 */ Order order = orderLine.getOrder();
			/* 176 */ if (order != null) {
				/* 177 */ OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
				/* 178 */ if (applied) {
					/* 179 */ if (!Hibernate.isInitialized(order.getOrderLines())) {
						/* 180 */ orderManager.lazyLoadOrder(order,
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
						/*     */ }
					/*     */
					/* 183 */ if (order.isDonePackage()) {
						/* 184 */ orderManager.settOrdreKomplett(order.getOrderNr(), Util.getCurrentDate());
						/*     */
						/*     */
						/*     */ }
					/*     */ } else {
					/* 189 */ orderManager.fjernOrdreKomplett(order.getOrderNr());
					/*     */
					/*     */
					/*     */ }
				/*     */ } else {
				/* 194 */ PostShipment postShipment = orderLine.getPostShipment();
				/* 195 */ if (postShipment != null) {
					/* 196 */ PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
							.getBean("postShipmentManager");
					/*     */
					/* 198 */ if (applied) {
						/* 199 */ postShipmentManager.lazyLoad(postShipment,
								new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES });
						/*     */
						/* 201 */ if (postShipment.isDonePackage()) {
							/* 202 */ postShipment.setPostShipmentComplete(Util.getCurrentDate());
							/* 203 */ postShipmentManager.savePostShipment(postShipment);
							/*     */ }
						/*     */ } else {
						/* 206 */ postShipment.setPostShipmentComplete((Date) null);
						/* 207 */ postShipmentManager.savePostShipment(postShipment);
						/*     */ }
				}
				/*     */ }
			/*     */ }
		/*     */
		/* 212 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public boolean hasWriteAccess() {
		/* 218 */ return UserUtil.hasWriteAccess(this.login.getUserType(), this.windowAccessName);
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public ReportEnum getReportEnum() {
		/* 226 */ return this.reportEnum;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public PackableListItem getApplyObject(Transportable transportable, WindowInterface window) {
		/* 233 */ List<PackableListItem> list = this.applyListManager
				.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		/* 234 */ return list != null && list.size() == 1 ? (PackableListItem) list.get(0) : null;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void setStarted(PackableListItem object, boolean started) {
		/* 241 */ if (object != null) {
			/* 242 */ List<Applyable> relatedObjects = object.getRelatedArticles();
			/* 243 */ List<Applyable> objects = new ArrayList();
			/* 244 */ if (object.getOrderLineId() != null) {
				/* 245 */ objects.add(object);
				/*     */ }
			/* 247 */ if (relatedObjects != null && relatedObjects.size() != 0) {
				/* 248 */ objects.addAll(relatedObjects);
				/*     */ }
			/* 250 */ Date startedDate = started ? Util.getCurrentDate() : null;
			/* 251 */ this.setStarted(objects, startedDate);
			/*     */
			/*     */ }
		/*     */
		/* 255 */ }

	public void setPause(PackableListItem object, boolean started) {
		
	}

	private void setStarted(List<Applyable> objects, Date startedDate) {
		Iterator var3 = objects.iterator();
		while (var3.hasNext()) {
			Applyable object = (Applyable) var3.next();
			OrderLine orderLine = this.managerRepository.getOrderLineManager()
					.findByOrderLineId(object.getOrderLineId());
			if (orderLine != null) {
				orderLine.setActionStarted(startedDate);

				this.managerRepository.getOrderLineManager().saveOrderLine(orderLine);
				this.applyListManager.refresh((PackableListItem) object);
			}
		}
	}

	private void setPause(List<Applyable> objects, Date startedDate) {
	}

	public void setRealProductionHours(PackableListItem object, BigDecimal overstyrtTidsforbruk) {
	}
}
