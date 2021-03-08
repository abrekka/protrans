package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductionUnit;
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
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer produksjon
 * 
 * @author atle.brekka
 */

public class ProductionApplyList extends AbstractApplyList<Produceable> {
	private static Logger LOGGER = Logger.getLogger(ProductionApplyList.class);
	private String colliName;

	private String windowName;

	private Integer[] invisibleCells;
	protected ManagerRepository managerRepository;

	/**
	 * @param aUserType
	 * @param manager
	 * @param aColliName
	 * @param aWindowName
	 * @param somInvisibleCells
	 */
	public ProductionApplyList(final Login login, final IApplyListManager<Produceable> manager, final String aColliName,
			final String aWindowName, final Integer[] somInvisibleCells, final ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator) {
		super(login, manager, vismaFileCreator);
		managerRepository = aManagerRepository;
		colliName = aColliName;
		windowName = aWindowName;
		invisibleCells = somInvisibleCells != null ? somInvisibleCells.clone() : null;
	}

	/**
	 * Setter kolli for produsert element
	 * 
	 * @param orderLine
	 * @throws ProTransException
	 */
	private void doPackage(final OrderLine orderLine, final String aColliName) throws ProTransException {
		String currentColliName = aColliName != null ? aColliName : colliName;
		ColliManager colliManager = managerRepository.getColliManager();
		OrderLineManager orderLineManager = managerRepository.getOrderLineManager();
		Colli colli;
		if (orderLine.getPostShipment() != null) {
			colli = colliManager.findByNameAndPostShipment(currentColliName, orderLine.getPostShipment());
		} else {
			colli = colliManager.findByNameAndOrder(currentColliName, orderLine.getOrder());
		}
		if (colli == null) {
			if (orderLine.getPostShipment() != null) {
				colli = new Colli(null, null, currentColliName, null, null, null, orderLine.getPostShipment(), null,
						Util.getCurrentDate(),"ProductionApplyList");
			} else {
				colli = new Colli(null, orderLine.getOrder(), currentColliName, null, null, null, null, null,
						Util.getCurrentDate(),"ProductionApplyList");
			}
		} else {
			colliManager.lazyLoad(colli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
		}
		colli.addOrderLine(orderLine);
		colliManager.saveColli(colli);

		orderLine.setColli(colli);

		orderLineManager.saveOrderLine(orderLine);
		checkCompleteness(colli, true);

	}

	/**
	 * Sjekker om ordre er komplett
	 * 
	 * @param colli
	 * @param applied
	 * @throws ProTransException
	 */
	protected void checkCompleteness(Colli colli, boolean applied) throws ProTransException {
		if (colli != null) {
			Order order = colli.getOrder();
			if (order != null) {
				OrderManager orderManager = managerRepository.getOrderManager();
				if (applied) {
					orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
							LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
					if (order.isDonePackage()) {
						order.setOrderComplete(Util.getCurrentDate());
						orderManager.saveOrder(order);
					}
				} else {
					order.setOrderComplete(null);
					orderManager.saveOrder(order);
				}
			} else {
				PostShipment postShipment = colli.getPostShipment();
				if (postShipment != null) {
					PostShipmentManager postShipmentManager = managerRepository.getPostShipmentManager();
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
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#
	 *      setApplied(no.ugland.utransprod.gui.model.Applyable, boolean,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(final Produceable object, final boolean applied, final WindowInterface window) {
		if (object != null) {
			handleApply(object, applied, window, colliName);
		}

	}

	protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window,
			final String aColliName) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {

			try {
				if (applied) {
					orderLine.setProduced(object.getProduced());
					setProducableApplied(orderLine, aColliName);

				} else {
					setProducableUnapplied(orderLine);

				}
			} catch (ProTransException e1) {
				Util.showErrorDialog(window, "Feil", e1.getMessage());
				e1.printStackTrace();
			}

			refreshAndSaveOrder(window, orderLine);
			applyListManager.refresh(object);
		}
	}

	protected void refreshAndSaveOrder(final WindowInterface window, final OrderLine orderLine) {
		Order order = orderLine.getOrder();

		try {
			managerRepository.getOrderManager().refreshObject(order);
			order.setStatus(null);
			managerRepository.getOrderManager().saveOrder(order);
			managerRepository.getOrderLineManager().saveOrderLine(orderLine);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	protected void setProducableUnapplied(final OrderLine orderLine) throws ProTransException {
		orderLine.setProduced(null);
		// orderLine.setProductionUnit(null);

		if (colliName != null) {
			checkCompleteness(orderLine.getColli(), false);
			orderLine.setColli(null);
		}
	}

	protected void setProducableApplied(final OrderLine orderLine, final String aColliName) throws ProTransException {
		// orderLine.setProduced(Util.getCurrentDate());
		String currentColliName = aColliName != null ? aColliName : colliName;
		if (currentColliName != null) {
			doPackage(orderLine, currentColliName);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#hasWriteAccess()
	 */
	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), windowName);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getReportEnum()
	 */
	@Override
	public ReportEnum getReportEnum() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getTableModelReport(javax.swing.ListModel,
	 *      org.jdesktop.swingx.JXTable,
	 *      com.jgoodies.binding.list.SelectionInList)
	 */
	@Override
	public TableModel getTableModelReport(final ListModel listModel, final JXTable table,
			final SelectionInList objectSelectionList) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#setInvisibleColumns(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setInvisibleColumns(JXTable table) {
		if (invisibleCells != null && invisibleCells.length != 0) {
			for (int i = 0; i < invisibleCells.length; i++) {
				table.getColumnExt(invisibleCells[i].intValue()).setVisible(false);
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#sortObjectLines(java.util.List)
	 */
	@Override
	protected void sortObjectLines(List<Produceable> lines) {
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public Produceable getApplyObject(final Transportable transportable, final WindowInterface window) {
		List<Produceable> list = applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());

		// return list == null||list.size()==0 ? null : list != null &&
		// list.size() == 1 ? list.get(0) : selectApplyObject(list,window);
		return list != null && list.size() == 1 ? list.get(0) : null;
	}

	public void setStarted(final Produceable object, final boolean started) {
		if (object != null) {

			Produceable currentProduceable = getProduceable(object);
			OrderLine orderLine = managerRepository.getOrderLineManager()
					.findByOrderLineId(currentProduceable.getOrderLineId());
			if (orderLine != null) {
				if (started) {
					orderLine.setActionStarted(Util.getCurrentDate());
					String gjortAv = Util.showInputDialogWithdefaultValue(null, "Gjøres av", "Gjøres av",
							login.getApplicationUser().getFullName());
					orderLine.setDoneBy(gjortAv);
				} else {
					orderLine.setActionStarted(null);
					orderLine.setDoneBy(null);
				}
				managerRepository.getOrderLineManager().saveOrderLine(orderLine);

				// applyListManager.refresh((Produceable)orderLine);
				applyListManager.refresh(currentProduceable);

			}
		}

	}

	public void setRealProductionHours(Produceable object, BigDecimal overstyrtTidsforbruk) {
		if (object != null) {

			Produceable currentProduceable = getProduceable(object);
			OrderLine orderLine = managerRepository.getOrderLineManager()
					.findByOrderLineId(currentProduceable.getOrderLineId());
			if (orderLine != null) {
				orderLine.setRealProductionHours(overstyrtTidsforbruk);
				managerRepository.getOrderLineManager().saveOrderLine(orderLine);

				// applyListManager.refresh((Produceable)orderLine);
				applyListManager.refresh(currentProduceable);

			}
		}

	}

	private Produceable getProduceable(Produceable object) {
		if (object.getOrderLineId() != null) {
			return object;
		} else if (object.getRelatedArticles() != null && object.getRelatedArticles().size() != 0) {
			return (Produceable) object.getRelatedArticles().get(0);
		}
		return null;
	}

	public void setProductionUnit(ProductionUnit productionUnit, Produceable object, WindowInterface window) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {
			orderLine.setProductionUnit(productionUnit);
			refreshAndSaveOrder(window, orderLine);
			applyListManager.refresh(object);

		}
	}

	protected void lagFerdigmelding(Order order, OrderLine orderLine, boolean minus, String produksjonstype) {
		LOGGER.info(String.format("Skal lage ferdigmelding for ordno: %s oglnno: %s", orderLine.getOrdNo(),
				orderLine.getLnNo()));

		List<OrderLine> ordrelinjer = order.getOrderLineList(produksjonstype);

		List<Ordln> ordlnList = new ArrayList<Ordln>();

		for (OrderLine orderLine2 : ordrelinjer) {
			if (orderLine.getOrdNo() != null && orderLine2.getLnNo() != null) {
				ordlnList.add(managerRepository.getOrdlnManager().findByOrdNoAndLnNo(orderLine2.getOrdNo(),
						orderLine2.getLnNo()));
			}
		}

		// List<Ordln> ordrelinjer =
		// managerRepository.getOrdlnManager().findOrdLnByOrdNo(orderLine.getOrdNo());

		Set<Integer> produksjonslinjer = new HashSet<Integer>();
		for (Ordln ordln : ordlnList) {
			if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno() != 0) {
				produksjonslinjer.add(ordln.getPurcno());
			}
		}

		if (produksjonslinjer.isEmpty()) {
			LOGGER.info("Lager ikke ferdigmelding fordi mangler ordln, purcno eller purcno er 0");
		}

		for (Integer purcno : produksjonslinjer) {
			List<String> fillinjer = (VismaFileCreatorImpl.lagFillinjer(minus, purcno, orderLine.getDoneBy(),
					produksjonstype, orderLine.getRealProductionHours()));
			try {
				vismaFileCreator.writeFile(purcno.toString(), ApplicationParamUtil.findParamByName("visma_out_dir"),
						fillinjer, 1);
			} catch (IOException e) {
				throw new RuntimeException("Feilet ved skriving av vismafil", e);
			}
		}

	}
}
