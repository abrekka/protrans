package no.ugland.utransprod.gui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;

import no.ugland.utransprod.gui.action.AssemblyAction;
import no.ugland.utransprod.gui.action.CustomerAction;
import no.ugland.utransprod.gui.action.ExternalOrderAction;
import no.ugland.utransprod.gui.action.IncomingOrderAction;
import no.ugland.utransprod.gui.action.OrderAction;
import no.ugland.utransprod.gui.action.PacklistAction;
import no.ugland.utransprod.gui.action.TaksteinAction;
import no.ugland.utransprod.gui.action.TransportAction;
import no.ugland.utransprod.gui.action.TransportCostAction;

import com.google.inject.Inject;

public class GarageMenu extends ProTransMenu {
    private CustomerAction customerAction;
    private OrderAction orderAction;
    private TransportAction transportAction;
    private AssemblyAction assemblyAction;
    // private InvoiceAction invoiceAction;
    private IncomingOrderAction incomingOrderAction;
    private PacklistAction packlistAction;
    private ExternalOrderAction externalOrderAction;
    // private PaidAction paidAction;
    private TaksteinAction taksteinAction;
    private TransportCostAction transportCostAction;

    public GarageMenu(Login aLogin) {
	super(aLogin);
    }

    @Inject
    public GarageMenu(final Login aLogin, final CustomerAction aCustomerAction, final OrderAction aOrderAction,
	    final TransportAction aTransportAction, final AssemblyAction aAssemblyAction,
	    // final InvoiceAction aInvoiceAction,
	    final IncomingOrderAction aIncomingOrderAction, final PacklistAction aPacklistAction, final ExternalOrderAction aExternalOrderAction,
	    // final PaidAction aPaidAction,
	    final TaksteinAction aTaksteinAction, final TransportCostAction aTransportCostAction) {
	super(aLogin);
	transportCostAction = aTransportCostAction;
	taksteinAction = aTaksteinAction;
	// paidAction = aPaidAction;
	externalOrderAction = aExternalOrderAction;
	packlistAction = aPacklistAction;
	incomingOrderAction = aIncomingOrderAction;
	// invoiceAction = aInvoiceAction;
	assemblyAction = aAssemblyAction;
	transportAction = aTransportAction;
	orderAction = aOrderAction;
	customerAction = aCustomerAction;
    }

    public JMenu buildMenu() {
	JMenu menuGarage = addMenu("Ordre", KeyEvent.VK_O);
	// addMenuItem(menuGarage, new CustomerAction(), KeyEvent.VK_K, null,
	addMenuItem(menuGarage, customerAction, KeyEvent.VK_K, null, null, null, "Kunde", false);
	addMenuItem(menuGarage, orderAction, KeyEvent.VK_O, null, null, null, "Ordre", false);
	addMenuItem(menuGarage, transportAction, KeyEvent.VK_T, null, null, null, "Transport", false);
	addMenuItem(menuGarage, assemblyAction, KeyEvent.VK_M, null, null, null, "Montering", false);
	// addMenuItem(menuGarage, invoiceAction, KeyEvent.VK_F, null, null,
	// null,
	// "Fakturering", false);
	addMenuItem(menuGarage, incomingOrderAction, KeyEvent.VK_A, null, null, null, "Ordre til avrop", false);
	addMenuItem(menuGarage, packlistAction, KeyEvent.VK_P, null, null, null, "Pakkliste", false);
	addMenuItem(menuGarage, externalOrderAction, KeyEvent.VK_B, null, null, null, "Bestillinger", false);
	// addMenuItem(menuGarage, paidAction, KeyEvent.VK_R, null, null, null,
	// "Forhåndsbetaling", false);
	addMenuItem(menuGarage, taksteinAction, KeyEvent.VK_S, null, null, null, "Takstein", false);
	addMenuItem(menuGarage, transportCostAction, KeyEvent.VK_N, null, null, null, "Transportkostnad", false);
	return menuGarage;
    }

    public void setOrderAction(OrderAction aOrderAction) {
	orderAction = aOrderAction;

    }

}
