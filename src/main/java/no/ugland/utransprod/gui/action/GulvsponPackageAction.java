package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.GulvsponPackageViewHandler;
import no.ugland.utransprod.gui.model.GulvsponPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Gulvspon...
 * 
 * @author atle.brekka
 */
public class GulvsponPackageAction extends AbstractAction {
	/**
	 *
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private GulvsponPackageApplyList gulvsponPackageApplyList;
	private Login login;
	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	@Inject
	public GulvsponPackageAction(MenuBarBuilderInterface aMenuBarBuilder,
			GulvsponPackageApplyList aGulvsponPackageApplyList, Login aLogin,
			ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory) {
		super("Gulvspon...");
		managerRepository = aManagerRepository;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		login = aLogin;
		gulvsponPackageApplyList = aGulvsponPackageApplyList;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * Åpner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		AbstractProductionPackageViewHandler<PackableListItem> packageViewHandler = new GulvsponPackageViewHandler(
				gulvsponPackageApplyList, login, ApplicationParamUtil
						.findParamByName("gulvspon_attributt"),
				managerRepository, deviationViewHandlerFactory);

		menuBarBuilder.openFrame(new ApplyListView<PackableListItem>(
				packageViewHandler, true));

	}
}