package no.ugland.utransprod.gui.model;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.FaktureringVManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Håndterer fakturaliste
 * 
 * @author atle.brekka
 * 
 */
public class InvoiceApplyList extends AbstractApplyList<FaktureringV> {

	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public InvoiceApplyList(Login login,
			FaktureringVManager manager) {
		super(login, manager,null);
	}

	/**
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(FaktureringV object, boolean applied,
			WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		Order order = orderManager.findByOrderNr(object.getOrderNr());
		if (applied) {
			Date invoiceDate = Util.getDate(window);
			order.setInvoiceDate(invoiceDate);
		} else {
			order.setInvoiceDate(null);
		}
		try {
			orderManager.saveOrder(order);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
		applyListManager.refresh(object);

	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#hasWriteAccess()
	 */
	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), "Fakturering");
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public FaktureringV getApplyObject(Transportable transportable,WindowInterface window) {
		List<FaktureringV> list = applyListManager
				.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

    public void setStarted(FaktureringV object, boolean started) {
    }

    public boolean shouldGenerateVismaFile() {
        // TODO Auto-generated method stub
        return false;
    }


}
