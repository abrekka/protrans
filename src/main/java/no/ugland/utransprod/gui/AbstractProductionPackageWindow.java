package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Abstrakt klasse for vindu som skal være hovedvindu for pakking og produksjon
 * 
 * @author atle.brekka
 * @param <T>
 * 
 */
public abstract class AbstractProductionPackageWindow<T extends Applyable> implements MainWindow,
		CloseListener {
	
	
	protected Login login;
	
	public AbstractProductionPackageWindow(){
		this(null);
	}

	@Inject
	public AbstractProductionPackageWindow(final Login aLogin){
		login=aLogin;
	}


	/**
	 * Henter artikkelnavn for artikkel som skal pakkes eller produseres
	 * 
	 * @return artikkelnavn
	 */
	protected abstract String getParamArticleName();

	/**
	 * Henter vindustittel
	 * 
	 * @return vindustittel
	 */
	protected abstract String getWindowTitle();

	/**
	 * Henter hjelpeklasse for vindu for spesifikk produksjon eller pakking
	 * 
	 * @return vindushjelpeklasse
	 */
	public abstract AbstractProductionPackageViewHandler<T> getViewHandler();

	/**
	 * Sjekker om knapp for utskrift skal brukes
	 * 
	 * @return true dersom printknapp skal brukes
	 */
	protected abstract boolean usePrintButton();

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public Component buildMainWindow(SystemReadyListener listener,ManagerRepository managerRepository) {
		AbstractProductionPackageViewHandler<T> productionViewHandler = getViewHandler();
		productionViewHandler.addCloseListener(this);
		ApplyListView<T> productionView = new ApplyListView<T>(
				productionViewHandler, usePrintButton());
		JFrame jFrame = new JFrame(getWindowTitle());
		jFrame.setSize(productionViewHandler.getWindowSize());
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(productionView.buildPanel(frame));

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
        //getViewHandler().savePrefs();
		System.exit(0);

	}


}
