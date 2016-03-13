package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.google.inject.Inject;

import no.ugland.utransprod.gui.ApplyListView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.DeviationViewHandlerFactory;
import no.ugland.utransprod.gui.handlers.IgarasjenPackageViewHandler;
import no.ugland.utransprod.gui.model.IgarasjenPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;

public class IgarasjenPackageAction extends AbstractAction {
	private final MenuBarBuilderInterface menuBarBuilder;
	private static final long serialVersionUID = 1L;

	private IgarasjenPackageApplyList igarasjenPackageApplyList;
	private Login login;
	private ManagerRepository managerRepository;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	@Inject
	public IgarasjenPackageAction(MenuBarBuilderInterface aMenuBarBuilder,
			IgarasjenPackageApplyList aIgarasjenPackageApplyList, Login aLogin,
			ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory) {
		super("Igarasjen...");
		managerRepository = aManagerRepository;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		login = aLogin;
		igarasjenPackageApplyList = aIgarasjenPackageApplyList;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	public void actionPerformed(final ActionEvent arg0) {
		AbstractProductionPackageViewHandler<PackableListItem> packageViewHandler = new IgarasjenPackageViewHandler(
				igarasjenPackageApplyList, login, "Igarasjen",
				managerRepository, deviationViewHandlerFactory);

		menuBarBuilder.openFrame(new ApplyListView<PackableListItem>(
				packageViewHandler, true));

	}
}