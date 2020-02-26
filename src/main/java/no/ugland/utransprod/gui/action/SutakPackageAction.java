package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.google.inject.Inject;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.GulvsponPackageViewHandler;
import no.ugland.utransprod.gui.handlers.SutakPackageViewHandler;
import no.ugland.utransprod.gui.model.GulvsponPackageApplyList;
import no.ugland.utransprod.gui.model.SutakPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;

public class SutakPackageAction extends AbstractAction {
	/**
	 *
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private SutakPackageApplyList sutakPackageApplyList;
	private Login login;
	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	@Inject
	public SutakPackageAction(MenuBarBuilderInterface aMenuBarBuilder,
			SutakPackageApplyList aSutakPackageApplyList, Login aLogin,
			ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory) {
		super("Sutak...");
		managerRepository = aManagerRepository;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		login = aLogin;
		sutakPackageApplyList = aSutakPackageApplyList;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * �pner kundevindu
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		AbstractProductionPackageViewHandler<PackableListItem> packageViewHandler = new SutakPackageViewHandler(
				sutakPackageApplyList, login, "Sutaksplater",
				managerRepository, deviationViewHandlerFactory);

		menuBarBuilder.openFrame(new ApplyListView<PackableListItem>(
				packageViewHandler, true));

	}
}