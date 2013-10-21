package no.ugland.utransprod.gui.model;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PacklistVComparator;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Håndterer setting av at pakkliuste er klar for ordre
 * 
 * @author atle.brekka
 * 
 */
public class PacklistApplyList extends AbstractApplyList<PacklistV> {

	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public PacklistApplyList(Login login,
			PacklistVManager manager) {
		super(login, manager,null);
	}

	/**
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(PacklistV object, boolean applied,
			WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");

		Order order = orderManager.findByOrderNr(object.getOrderNr());
		if (applied) {
			Date packlistDate = Util.getDate(window);
			order.setPacklistReady(packlistDate);
		} else {
			order.setPacklistReady(null);
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
		return UserUtil.hasWriteAccess(login.getUserType(), "Pakkliste");
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#sortObjectLines(java.util.List)
	 */
	@Override
	protected void sortObjectLines(List<PacklistV> lines) {
		Collections.sort(lines);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public PacklistV getApplyObject(Transportable transportable,WindowInterface window) {
		List<PacklistV> list = applyListManager
				.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		if (list != null && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

    public void setStarted(PacklistV object, boolean started) {
    }

    public boolean shouldGenerateVismaFile() {
        // TODO Auto-generated method stub
        return false;
    }


    
}
