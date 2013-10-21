package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProcentDoneManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Standalone vindu for produksjonsoversikt
 * 
 * @author atle.brekka
 */
public class ProductionOverviewWindow implements MainWindow, CloseListener {
	// private UserType applicationUserType;

	// private ApplicationUser applicationUser;
	private Login login;
	private ProductionOverviewViewHandler productionOverviewViewHandler;

	@Inject
	public ProductionOverviewWindow(
			final ProductionOverviewViewHandler aProductionOverviewViewHandler) {
		productionOverviewViewHandler = aProductionOverviewViewHandler;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public final Component buildMainWindow(final SystemReadyListener listener,
			ManagerRepository managerRepository) {
		/*
		 * OrderManager
		 * orderManager=(OrderManager)ModelUtil.getBean(OrderManager
		 * .MANAGER_NAME); DeviationManager
		 * deviationManager=(DeviationManager)ModelUtil
		 * .getBean(DeviationManager.MANAGER_NAME); ProcentDoneManager
		 * procentDoneManager
		 * =(ProcentDoneManager)ModelUtil.getBean(ProcentDoneManager
		 * .MANAGER_NAME); OrderViewHandler orderViewHandler = new
		 * OrderViewHandler(true,login,orderManager);
		 * 
		 * ProductionOverviewViewHandler productionOverviewViewHandler = new
		 * ProductionOverviewViewHandler(vismaFileCreator, orderViewHandler,
		 * login,orderManager,deviationManager,procentDoneManager);
		 */
		productionOverviewViewHandler.addCloseListener(this);
		ProductionOverviewView productionOverviewView = new ProductionOverviewView(
				productionOverviewViewHandler);
		JFrame jFrame = new JFrame("Produksjonsoversikt");
		jFrame.setSize(productionOverviewViewHandler.getWindowSize());
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(productionOverviewView.buildPanel(frame));
		Util.locateOnScreenCenter(frame);
		frame.setVisible(true);
		listener.systemReady();
		return jFrame;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
	 */
	public final void setLogin(final Login aLogin) {
		login = aLogin;

	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#setUserType(no.ugland.utransprod.model.UserType)
	 */
	public final void setUserType(final UserType currentUserType) {
		// this.applicationUserType = currentUserType;

	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public final void windowClosed() {
		System.exit(0);

	}

}
