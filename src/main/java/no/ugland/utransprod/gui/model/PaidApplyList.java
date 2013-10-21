package no.ugland.utransprod.gui.model;

import java.util.Date;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Lister for forhåndsbetaling
 * 
 * @author atle.brekka
 * 
 */
public class PaidApplyList extends AbstractApplyList<PaidV> {
	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public PaidApplyList(Login login,PaidVManager manager) {
		super(login,manager,null);
	}

	/**
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(PaidV object, boolean applied, WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		Order order = orderManager.findByOrderNr(object.getOrderNr());
		if (applied) {
			Date invoiceDate = Util.getDate(window);
			order.setPaidDate(invoiceDate);
		} else {
			order.setPaidDate(null);
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
		return UserUtil.hasWriteAccess(login.getUserType(), "Betaling");
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public PaidV getApplyObject(Transportable transportable,WindowInterface window) {
		List<PaidV> list = applyListManager
				.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

    

    public void setStarted(PaidV object, boolean started) {
        // TODO Auto-generated method stub
        
    }



}
