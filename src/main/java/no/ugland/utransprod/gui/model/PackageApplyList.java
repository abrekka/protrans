package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.hibernate.Hibernate;

/**
 * Håndterer pakking av ordre
 * 
 * @author atle.brekka
 */
public class PackageApplyList extends AbstractApplyList<PackableListItem> {

	protected String colliName;

	private String windowAccessName;

	private ReportEnum reportEnum;
	protected ManagerRepository managerRepository;

	public PackageApplyList(Login login, IApplyListManager<PackableListItem> manager, String aColliName,
			String aWindowAccessName, ReportEnum aReportEnum, VismaFileCreator vismaFileCreator,
			ManagerRepository aManagerRepository) {
		super(login, manager, vismaFileCreator);
		managerRepository = aManagerRepository;
		colliName = aColliName;
		windowAccessName = aWindowAccessName;
		reportEnum = aReportEnum;
	}

	/**
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(final PackableListItem object, final boolean applied, final WindowInterface window) {
		if (object != null) {
			OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
			OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
			if (orderLine != null) {

				handleApply(applied, window, orderLineManager, orderLine, null, true);

				checkAndSavePostShipment(orderLine);

				saveOrder(window, orderLineManager, orderLine);
				applyListManager.refresh(object);
			}
		}

	}

	protected OrderLine handleApply(final boolean applied, final WindowInterface window,
			final OrderLineManager orderLineManager, final OrderLine orderLine, final String aColliName,
			final boolean isMainArticle) {
		OrderLine appliedOrderLine = null;
		try {
			if (applied) {
				appliedOrderLine = orderLine.getColli() != null ? null
						: setObjectApplied(orderLineManager, orderLine, aColliName);
				// createVismaFile(orderLine);
			} else {
				if (ignoreVismaFile(orderLine, window, isMainArticle)) {
					setObjectUnapplied(orderLineManager, orderLine);
				}

			}
		} catch (ProTransException e1) {
			Util.showErrorDialog(window, "Feil", e1.getMessage());
			e1.printStackTrace();
		}
		return appliedOrderLine;
	}

	private void setObjectUnapplied(final OrderLineManager orderLineManager, final OrderLine orderLine)
			throws ProTransException {
		orderLine.setColli(null);
		orderLineManager.saveOrderLine(orderLine);
		checkCompleteness(orderLine, false);
	}

	private OrderLine setObjectApplied(final OrderLineManager orderLineManager, final OrderLine orderLine,
			final String aColliName) throws ProTransException {
		ColliManager colliManager = (ColliManager) ModelUtil.getBean("colliManager");
		Colli colli;
		String currentColliName = aColliName != null ? aColliName : colliName;
		if (orderLine.getPostShipment() != null) {
			colli = colliManager.findByNameAndPostShipment(currentColliName, orderLine.getPostShipment());
		} else {
			colli = colliManager.findByNameAndOrder(currentColliName, orderLine.getOrder());
		}
		if (colli == null) {
			if (orderLine.getPostShipment() != null) {
				colli = new Colli(null, null, currentColliName, null, null, null, orderLine.getPostShipment(), null,
						Util.getCurrentDate());
			} else {
				colli = new Colli(null, orderLine.getOrder(), currentColliName, null, null, null, null, null,
						Util.getCurrentDate());
			}
		} else {
			if (!Hibernate.isInitialized(colli.getOrderLines())) {
				colliManager.lazyLoad(colli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
			}
		}
		colli.addOrderLine(orderLine);
		colli.setPackageDate(Util.getCurrentDate());
		colliManager.saveColli(colli);

		orderLine.setColli(colli);

		orderLineManager.saveOrderLine(orderLine);
		checkCompleteness(orderLine, true);
		return orderLine;
	}

	protected void saveOrder(WindowInterface window, OrderLineManager orderLineManager, OrderLine orderLine) {
		OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		Order order = orderLine.getOrder();
//		order.setStatus(null);
		orderManager.settStatus(order.getOrderId(), null);
		try {
//			orderManager.saveOrder(order);
			orderLineManager.saveOrderLine(orderLine);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	protected void checkAndSavePostShipment(OrderLine orderLine) {
		PostShipment postShipment = orderLine.getPostShipment();
		if (postShipment != null) {
			postShipment.setStatus(null);
			PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
			postShipmentManager.savePostShipment(postShipment);
		}
	}

	/**
	 * Sjekker om ordre er komplett
	 * 
	 * @param orderLine
	 * @param applied
	 * @throws ProTransException
	 */
	protected void checkCompleteness(OrderLine orderLine, boolean applied) throws ProTransException {
		if (orderLine != null) {
			Order order = orderLine.getOrder();
			if (order != null) {
				OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
				if (applied) {
					if (!Hibernate.isInitialized(order.getOrderLines())) {
						orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });// ,LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES
																														// });
					}
					if (order.isDonePackage()) {
						orderManager.settOrdreKomplett(order.getOrderNr(), Util.getCurrentDate());
//						order.setOrderComplete(Util.getCurrentDate());
//						orderManager.saveOrder(order);
					}
				} else {
					orderManager.fjernOrdreKomplett(order.getOrderNr());
//					order.setOrderComplete(null);
//					orderManager.saveOrder(order);
				}
			} else {
				PostShipment postShipment = orderLine.getPostShipment();
				if (postShipment != null) {
					PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
							.getBean("postShipmentManager");
					if (applied) {
						postShipmentManager.lazyLoad(postShipment,
								new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES });
						if (postShipment.isDonePackage()) {
							postShipment.setPostShipmentComplete(Util.getCurrentDate());
							postShipmentManager.savePostShipment(postShipment);
						}
					} else {
						postShipment.setPostShipmentComplete(null);
						postShipmentManager.savePostShipment(postShipment);
					}
				}
			}
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#hasWriteAccess()
	 */
	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), windowAccessName);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getReportEnum()
	 */
	@Override
	public ReportEnum getReportEnum() {
		return reportEnum;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public PackableListItem getApplyObject(final Transportable transportable, final WindowInterface window) {
		List<PackableListItem> list = applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public void setStarted(PackableListItem object, boolean started) {
		if (object != null) {
			List<Applyable> relatedObjects = object.getRelatedArticles();
			List<Applyable> objects = new ArrayList<Applyable>();
			if (object.getOrderLineId() != null) {
				objects.add(object);
			}
			if (relatedObjects != null && relatedObjects.size() != 0) {
				objects.addAll(relatedObjects);
			}
			Date startedDate = started ? Util.getCurrentDate() : null;
			setStarted(objects, startedDate);

		}

	}

	private void setStarted(List<Applyable> objects, Date startedDate) {
		for (Applyable object : objects) {

			OrderLine orderLine = managerRepository.getOrderLineManager().findByOrderLineId(object.getOrderLineId());
			if (orderLine != null) {
				orderLine.setActionStarted(startedDate);

				managerRepository.getOrderLineManager().saveOrderLine(orderLine);
				applyListManager.refresh((PackableListItem) object);
			}
		}
	}

	public void setRealProductionHours(PackableListItem object, BigDecimal overstyrtTidsforbruk) {
		// TODO Auto-generated method stub

	}

}
