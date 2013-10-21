package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.PacklistViewHandler;
import no.ugland.utransprod.gui.model.PacklistApplyList;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.service.AccidentManager;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PacklistVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Frittstående vindu for pakklister
 * 
 * @author atle.brekka
 * 
 */
public class PacklistWindow implements MainWindow, CloseListener {
	private Login login;
	private PacklistViewHandler packlistViewHandler;
	
	@Inject
	public PacklistWindow(PacklistViewHandler aPacklistViewHandler){
		packlistViewHandler=aPacklistViewHandler;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public Component buildMainWindow(SystemReadyListener listener,ManagerRepository managerRepository) {
		/*OrderManager orderManager=(OrderManager)ModelUtil.getBean(OrderManager.MANAGER_NAME);
		ExternalOrderManager externalOrderManager=(ExternalOrderManager)ModelUtil.getBean(ExternalOrderManager.MANAGER_NAME);
		DeviationManager deviationManager=(DeviationManager)ModelUtil.getBean(DeviationManager.MANAGER_NAME);
		AccidentManager accidentManager=(AccidentManager)ModelUtil.getBean(AccidentManager.MANAGER_NAME);
		OrderViewHandler orderViewHandler = new OrderViewHandler(false,login,orderManager);
		PacklistVManager packlistVManager=(PacklistVManager)ModelUtil.getBean("packlistVManager");
		AbstractProductionPackageViewHandler<PacklistV> productionViewHandler = new PacklistViewHandler(
				new PacklistApplyList(login.getUserType(),packlistVManager), orderViewHandler,
				login.getUserType(),login.getApplicationUser(),externalOrderManager,deviationManager,accidentManager);*/

		packlistViewHandler.addCloseListener(this);

		PacklistView packlistView = new PacklistView(packlistViewHandler);

		JFrame jFrame = new JFrame("Pakklister");
		jFrame.setSize(packlistViewHandler.getWindowSize());
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(packlistView.buildPanel(frame));
		Util.locateOnScreenCenter(frame);
		frame.setVisible(true);
		listener.systemReady();
		return jFrame;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
	 */
	public void setLogin(Login aLogin) {
		login=aLogin;
	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public void windowClosed() {
		System.exit(0);

	}

	

}
