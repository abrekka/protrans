package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.edit.EditDeviationView;
import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Klasse som håndterer visning av frittstående vindu for avvik
 * 
 * @author atle.brekka
 * 
 */
public class DeviationWindow implements MainWindow, CloseListener {
	private Login login;
	private PreventiveActionViewHandler preventiveActionViewHandler;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private DeviationOverviewViewFactory deviationOverviewViewFactory;
	private boolean brukOrdrelinjelinjer;

	@Inject
	public DeviationWindow(PreventiveActionViewHandler aPreventiveActionViewHandler,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			DeviationOverviewViewFactory aDeviationOverviewViewFactory, boolean brukOrdrelinjelinjer) {
		preventiveActionViewHandler = aPreventiveActionViewHandler;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		deviationOverviewViewFactory = aDeviationOverviewViewFactory;
		this.brukOrdrelinjelinjer = brukOrdrelinjelinjer;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public Component buildMainWindow(SystemReadyListener listener, ManagerRepository managerRepository) {
		DeviationViewHandler deviationViewHandler = new DeviationViewHandler(login, managerRepository,
				preventiveActionViewHandler,
				// deviationOverviewViewFactory,
				null, false, false, true, null, true, brukOrdrelinjelinjer);
		deviationViewHandler.addCloseListener(this);
		EditDeviationView deviationView = new EditDeviationView(false, new DeviationModel(new Deviation(), false),
				deviationViewHandler, false, true, brukOrdrelinjelinjer);
		JFrame jFrame = new JFrame("Registrere avvik");
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(deviationView.buildPanel(frame));
		frame.pack();
		Util.locateOnScreenCenter(frame);
		frame.setVisible(true);
		listener.systemReady();
		return jFrame;
	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public void windowClosed() {
		System.exit(0);

	}

	public void setLogin(Login aLogin) {
		login = aLogin;

	}

}
