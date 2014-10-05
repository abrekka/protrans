package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;

import javax.swing.JDialog;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

public class VeggProductionApplyList extends ProductionApplyList {

    public VeggProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName, String aWindowName,
	    Integer[] somInvisibleCells, ManagerRepository aManagerRepository) {
	super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository);
    }

    @Override
    protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window, final String aColliName) {
	OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
	OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
	if (orderLine != null) {

	    try {
		if (applied) {
		    orderLine.setProduced(object.getProduced());
		    setProducableApplied(orderLine, aColliName);

		    BigDecimal tidsbruk = orderLine.getRealProductionHours();

		    if (tidsbruk == null) {
			tidsbruk = Tidsforbruk.beregnTidsforbruk(orderLine.getActionStarted(), orderLine.getProduced());
		    }

		    EditPacklistView editPacklistView = new EditPacklistView(login, false, tidsbruk, orderLine.getDoneBy());

		    JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
		    WindowInterface window1 = new JDialogAdapter(dialog);
		    window1.add(editPacklistView.buildPanel(window1));
		    window1.pack();
		    Util.locateOnScreenCenter(window1);
		    window1.setVisible(true);

		    if (!editPacklistView.isCanceled()) {
			orderLine.setRealProductionHours(editPacklistView.getPacklistDuration());
			orderLine.setDoneBy(editPacklistView.getDoneBy());
		    }

		} else {
		    setProducableUnapplied(orderLine);
		    orderLine.setRealProductionHours(null);
		    orderLine.setDoneBy(null);

		}
	    } catch (ProTransException e1) {
		Util.showErrorDialog(window, "Feil", e1.getMessage());
		e1.printStackTrace();
	    }

	    refreshAndSaveOrder(window, orderLine);
	    applyListManager.refresh(object);
	}
    }
}
