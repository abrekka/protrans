package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.handlers.JobFunctionViewHandler;
import no.ugland.utransprod.gui.model.JobFunctionModel;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.service.JobFunctionManager;

import com.google.inject.Inject;

/**
 * Håndterer menyvalg Funksjon...
 * 
 * @author atle.brekka
 */
public class JobFunctionAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private JobFunctionManager jobFunctionManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public JobFunctionAction(MenuBarBuilderInterface aMenuBarBuilder,JobFunctionManager aJobFunctionManager) {
		super("Funksjon...");
		jobFunctionManager=aJobFunctionManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		menuBarBuilder.openFrame(new OverviewView<JobFunction, JobFunctionModel>(
				new JobFunctionViewHandler(menuBarBuilder.getUserType(),jobFunctionManager)));

	}
}