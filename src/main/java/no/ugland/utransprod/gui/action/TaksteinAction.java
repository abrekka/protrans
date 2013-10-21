package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.TaksteinSkarpnesViewHandler;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.TaksteinSkarpnesVManager;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Håndterer menyvalg Takstein...
 * 
 * @author atle.brekka
 */
public class TaksteinAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private TaksteinSkarpnesVManager taksteinSkarpnesVManager;
	private Login login;
	private ManagerRepository managerRepository;
	private static final long serialVersionUID = 1L;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private SetProductionUnitActionFactory setProductionUnitActionFactory;
	private ArticleType articleTypeTakstein;

	/**
	 * @param aApplicationUser
	 * @param menuBarBuilderImpl
	 *            TODO
	 */
	@Inject
	public TaksteinAction(MenuBarBuilderInterface aMenuBarBuilder,
			final TaksteinSkarpnesVManager aTaksteinSkarpnesVManager,
			Login aLogin, ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,SetProductionUnitActionFactory aSetProductionUnitActionFactory,@Named("taksteinArticle")ArticleType articleType) {
		super("Takstein...");
		articleTypeTakstein=articleType;
		setProductionUnitActionFactory=aSetProductionUnitActionFactory;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		login = aLogin;
		managerRepository = aManagerRepository;
		taksteinSkarpnesVManager = aTaksteinSkarpnesVManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner taksteinvindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		AbstractProductionPackageViewHandler<Produceable> productionViewHandler = new TaksteinSkarpnesViewHandler(
				new ProductionApplyList(login, taksteinSkarpnesVManager,
						"Takstein", "Takstein", new Integer[] { 2 },
						managerRepository), login, managerRepository,
				deviationViewHandlerFactory,setProductionUnitActionFactory,articleTypeTakstein);

		menuBarBuilder.openFrame(new ApplyListView<Produceable>(
				productionViewHandler, false));

	}
}