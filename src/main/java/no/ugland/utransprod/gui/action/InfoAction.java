package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.InfoViewHandler;
import no.ugland.utransprod.gui.model.InfoModel;
import no.ugland.utransprod.model.Info;
import no.ugland.utransprod.service.InfoManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Produksjoninformasjon
 * 
 * @author atle.brekka
 */
public class InfoAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private InfoManager infoManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public InfoAction(MenuBarBuilderInterface aMenuBarBuilder,InfoManager aInfoManager) {
		super("Produksjoninformasjon...");
		infoManager=aInfoManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		menuBarBuilder.openFrame(new OverviewView<Info, InfoModel>(new InfoViewHandler(
				menuBarBuilder.getUserType(),infoManager)));

	}
}