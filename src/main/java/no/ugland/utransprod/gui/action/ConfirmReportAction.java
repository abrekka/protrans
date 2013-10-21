/**
 *
 */
package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.ConfirmReportView;
import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.handlers.ConfirmReportViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.handlers.OrderViewHandlerFactory;
import no.ugland.utransprod.service.ManagerRepository;

import com.google.inject.Inject;

public class ConfirmReportAction extends AbstractAction {
	/**
	 *
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private OrderViewHandler orderViewHandler;
	private ManagerRepository managerRepository;
	private static final long serialVersionUID = 1L;

	@Inject
	public ConfirmReportAction(MenuBarBuilderInterface aMenuBarBuilder,
			OrderViewHandlerFactory aOrderViewHandlerFactory,
			ManagerRepository managerRepository) {
		super("Avropsrapport...");
		this.managerRepository = managerRepository;
		orderViewHandler = aOrderViewHandlerFactory.create(true);
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
		ConfirmReportViewHandler confirmReportViewHandler = new ConfirmReportViewHandler(
				orderViewHandler, managerRepository);

		menuBarBuilder
				.openFrame(new ConfirmReportView(confirmReportViewHandler));

	}
}