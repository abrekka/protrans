package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

public class VeggProductionApplyList extends ProductionApplyList {

    public VeggProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName, String aWindowName,
	    Integer[] somInvisibleCells, ManagerRepository aManagerRepository) {
	super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository);
    }

    @Override
    public void setStarted(Produceable object, boolean started) {
	if (object != null) {
	    OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	    // OrderLineManager orderLineManager = (OrderLineManager)
	    // ModelUtil.getBean("orderLineManager");
	    Order order = orderManager.findByOrderNr(object.getOrderNr());
	    orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
	    // Produceable currentProduceable = getProduceable(object);
	    // OrderLine orderLine =
	    // managerRepository.getOrderLineManager().findByOrderLineId(currentProduceable.getOrderLineId());
	    if (order != null) {
		List<OrderLine> vegger = order.getOrderLineList("Vegg");
		int antall = 0;
		String gjortAv = "";
		Date startedDate = Util.getCurrentDate();
		for (OrderLine vegg : vegger) {
		    antall++;
		    if (vegg != null) {
			if (started) {
			    vegg.setActionStarted(startedDate);
			    if (antall == 1) {
				gjortAv = Util.showInputDialogWithdefaultValue(null, "Gjøres av", "Gjøres av", login.getApplicationUser()
					.getFullName());
			    }
			    vegg.setDoneBy(gjortAv);
			} else {
			    vegg.setActionStarted(null);
			    vegg.setDoneBy(null);
			}
			managerRepository.getOrderLineManager().saveOrderLine(vegg);

			// applyListManager.refresh((Produceable)orderLine);

		    }
		}
		managerRepository.getOrderManager().refreshObject(order);
	    }
	    applyListManager.refresh(object);
	}
    }

    @Override
    protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window, final String aColliName) {
	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	// OrderLineManager orderLineManager = (OrderLineManager)
	// ModelUtil.getBean("orderLineManager");
	Order order = orderManager.findByOrderNr(object.getOrderNr());
	orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
	// OrderLine orderLine =
	// orderLineManager.findByOrderLineId(object.getOrderLineId());
	// if (orderLine != null) {
	if (order != null) {
	    List<OrderLine> vegger = order.getOrderLineList("Vegg");
	    try {
		int antall = 0;
		EditPacklistView editPacklistView = null;
		for (OrderLine vegg : vegger) {
		    antall++;
		    if (applied) {

			vegg.setProduced(object.getProduced());
			setProducableApplied(vegg, aColliName);

			BigDecimal tidsbruk = vegg.getRealProductionHours();

			if (tidsbruk == null) {
			    tidsbruk = Tidsforbruk.beregnTidsforbruk(vegg.getActionStarted(), vegg.getProduced());
			}

			if (antall == 1) {
			    editPacklistView = new EditPacklistView(login, false, tidsbruk, vegg.getDoneBy());
			    JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
			    WindowInterface window1 = new JDialogAdapter(dialog);
			    window1.add(editPacklistView.buildPanel(window1));
			    window1.pack();
			    Util.locateOnScreenCenter(window1);
			    window1.setVisible(true);
			}

			if (editPacklistView != null && !editPacklistView.isCanceled()) {
			    vegg.setRealProductionHours(editPacklistView.getPacklistDuration());
			    vegg.setDoneBy(editPacklistView.getDoneBy());
			}
		    } else {
			setProducableUnapplied(vegg);
			vegg.setRealProductionHours(null);
			vegg.setDoneBy(null);

		    }
		    managerRepository.getOrderLineManager().saveOrderLine(vegg);
		}
	    } catch (ProTransException e1) {
		Util.showErrorDialog(window, "Feil", e1.getMessage());
		e1.printStackTrace();
	    }

	    managerRepository.getOrderManager().refreshObject(order);
	    order.setStatus(null);
	    managerRepository.getOrderManager().saveOrder(order);
	    applyListManager.refresh(object);
	}
    }

}
