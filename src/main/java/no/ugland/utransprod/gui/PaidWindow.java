package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.PaidViewHandler;
import no.ugland.utransprod.gui.model.PaidApplyList;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Selvstendig vindu fpr å sette forhåndsbetaling
 * 
 * @author atle.brekka
 * 
 */
public class PaidWindow implements MainWindow, CloseListener {

	private Login login;
	private PaidView paidView;

	@Inject
	public PaidWindow(final PaidViewFactory paidViewFactory) {
		paidView = paidViewFactory.create(false);
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public Component buildMainWindow(SystemReadyListener listener,
			ManagerRepository managerRepository) {
		/*PaidVManager paidVManager = (PaidVManager) ModelUtil
				.getBean("paidVManager");
		PaidViewHandler paidViewHandler = new PaidViewHandler(
				new PaidApplyList(login.getUserType(), paidVManager), login
						.getUserType(), login.getApplicationUser());
		paidViewHandler.addCloseListener(this);
		PaidView paidView = new PaidView(paidViewHandler, false);*/
		JFrame jFrame = new JFrame("Forhåndsbetaling");
		jFrame.setSize(paidView.getWindowSize());
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(paidView.buildPanel(frame));
		Util.locateOnScreenCenter(frame);
		frame.setVisible(true);
		listener.systemReady();
		return jFrame;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#setApplicationUser(no.ugland.utransprod.model.ApplicationUser)
	 */
	public void setLogin(Login aLogin) {
		login = aLogin;
	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public void windowClosed() {
		System.exit(0);

	}

}
