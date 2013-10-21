package no.ugland.utransprod.gui.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import no.ugland.utransprod.gui.MenuBarBuilderInterface;
import no.ugland.utransprod.gui.edit.EditManagerView;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.service.ApplicationParamManager;

import com.google.inject.Inject;

public class HmsManagerAction extends AbstractAction {
	/**
	 * 
	 */
	private final MenuBarBuilderInterface menuBarBuilder;
	private ApplicationParamManager applicationParamManager;
	private static final long serialVersionUID = 1L;

	@Inject
	public HmsManagerAction(MenuBarBuilderInterface aMenuBarBuilder,ApplicationParamManager aApplicationParamManager) {
		super("HMS ansvarlig...");
		applicationParamManager=aApplicationParamManager;
		this.menuBarBuilder = aMenuBarBuilder;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {

		ApplicationParamViewHandler applicationParamViewHandler = new ApplicationParamViewHandler(
				"HMS ansvarlig", applicationParamManager,
				menuBarBuilder.getUserType());
		EditManagerView view = new EditManagerView(false,
				new ApplicationParam(), applicationParamViewHandler,"HMS ansvarlig:",ApplicationParamModel.PROPERTY_HMS_MANAGER,"HMS ansvarlig");
		
		menuBarBuilder.openFrame(view);

	}
}