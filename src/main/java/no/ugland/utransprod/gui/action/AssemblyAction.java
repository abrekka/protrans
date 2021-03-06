package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.AssemblyPlannerView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AssemblyPlannerViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandlerFactory;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

/**
 * H�ndterer visning av vindu for planlegging av montering
 * 
 * @author atle.brekka
 */
public class AssemblyAction extends AbstractAction {
    /**
	 * 
	 */
    private final MenuBarBuilderInterface menuBarBuilder;
    private OrderViewHandler orderViewHandler;
    private SupplierOrderViewHandlerFactory supplierOrderViewHandlerFactory;
    private Login login;
    private ManagerRepository managerRepository;
    private static final long serialVersionUID = 1L;
    private DeviationViewHandlerFactory deviationViewHandlerFactory;

    @Inject
    public AssemblyAction(MenuBarBuilderInterface aMenuBarBuilder, OrderViewHandlerFactory orderViewHandlerFactory, Login aLogin,
	    ManagerRepository aManagerRepository, SupplierOrderViewHandlerFactory aSupplierOrderViewHandlerFactory,
	    DeviationViewHandlerFactory aDeviationViewHandlerFactory) {
	super("Montering...");
	deviationViewHandlerFactory = aDeviationViewHandlerFactory;
	login = aLogin;
	managerRepository = aManagerRepository;
	supplierOrderViewHandlerFactory = aSupplierOrderViewHandlerFactory;
	orderViewHandler = orderViewHandlerFactory.create(false);
	this.menuBarBuilder = aMenuBarBuilder;
    }

    public void actionPerformed(final ActionEvent arg0) {
	menuBarBuilder.openFrame(new AssemblyPlannerView(new AssemblyPlannerViewHandler(orderViewHandler, login, supplierOrderViewHandlerFactory,
		managerRepository, deviationViewHandlerFactory)));
    }
}