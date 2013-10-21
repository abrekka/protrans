package no.ugland.utransprod.gui.model;

import java.util.Hashtable;
import java.util.Map;

import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderOverviewView;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.Viewer;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.CustomersViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.util.ModelUtil;

import com.google.inject.Inject;

/**
 * Dialogfactory
 * 
 * @author atle.brekka
 * 
 */
public class WindowFactoryImpl implements WindowFactory {

	/**
	 * 
	 */
	private final Map<WindowEnum, WindowInterface> windows = new Hashtable<WindowEnum, WindowInterface>();

	/**
	 * 
	 */
	private final Map<WindowEnum, Viewer> viewers = new Hashtable<WindowEnum, Viewer>();
	private OrderViewHandlerFactory orderViewHandlerFactory;
	
	@Inject
	public WindowFactoryImpl(OrderViewHandlerFactory aOrderViewHandlerFactory){
		orderViewHandlerFactory=aOrderViewHandlerFactory;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.WindowFactory#getWindow(no.ugland.utransprod.gui.model.WindowEnum,
	 *      no.ugland.utransprod.model.UserType,
	 *      no.ugland.utransprod.model.ApplicationUser)
	 */
	public WindowInterface getWindow(WindowEnum windowEnum, Login login) {
		WindowInterface window = null;
		Viewer viewer = null;
		switch (windowEnum) {
		case CUSTOMER:
			window = windows.get(windowEnum);
			if (window == null) {
				CustomerManager customerManager=(CustomerManager)ModelUtil.getBean(CustomerManager.MANAGER_NAME);
				viewer = new OverviewView<Customer, CustomerModel>(
						new CustomersViewHandler(login,customerManager));
				viewers.put(windowEnum, viewer);
			}
			break;
		case ORDER:
			window = windows.get(windowEnum);
			if (window == null) {
				viewer = new OrderOverviewView(orderViewHandlerFactory.create(false));
				viewers.put(windowEnum, viewer);
			}
			break;
		default:
			throw new ProTransRuntimeException(
					"Det er ikke definert enum for vindu");
		}
		if (viewer != null) {
			window = viewer.buildWindow();
			windows.put(windowEnum, window);
		} else {
			viewer = viewers.get(windowEnum);
			viewer.initWindow();
		}
		return window;
	}

}
