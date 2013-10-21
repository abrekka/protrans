package no.ugland.utransprod.gui;

import java.awt.Component;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import no.ugland.utransprod.gui.handlers.InvoiceViewHandler;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;

/**
 * Klasse som håndetere visning av frittstående vindu for fakturerbare ordre
 * 
 * @author atle.brekka
 * 
 */
public class InvoiceWindow implements MainWindow, CloseListener {

	private Login login;
	private InvoiceViewHandler invoiceViewHandler;
	@Inject
	public InvoiceWindow(InvoiceViewHandler aInvoiceViewHandler){
		invoiceViewHandler=aInvoiceViewHandler;
	}

	/**
	 * @see no.ugland.utransprod.gui.MainWindow#buildMainWindow(no.ugland.utransprod.gui.SystemReadyListener)
	 */
	public Component buildMainWindow(SystemReadyListener listener,ManagerRepository managerRepository) {
		/*FaktureringVManager faktureringVManager = (FaktureringVManager) ModelUtil
				.getBean("faktureringVManager");
		AccidentManager accidentManager=(AccidentManager)ModelUtil.getBean(AccidentManager.MANAGER_NAME);
		DeviationManager deviationManager=(DeviationManager)ModelUtil.getBean(DeviationManager.MANAGER_NAME);
		ArticleTypeManager articleTypeManager=(ArticleTypeManager)ModelUtil.getBean(ArticleTypeManager.MANAGER_NAME);
		AttributeChoiceManager attributeChoiceManager=(AttributeChoiceManager)ModelUtil.getBean(AttributeChoiceManager.MANAGER_NAME);
		AttributeManager attributeManager=(AttributeManager)ModelUtil.getBean(AttributeManager.MANAGER_NAME);
		PreventiveActionManager preventiveActionManager=(PreventiveActionManager)ModelUtil.getBean(PreventiveActionManager.MANAGER_NAME);
		ExternalOrderManager externalOrderManager=(ExternalOrderManager)ModelUtil.getBean(ExternalOrderManager.MANAGER_NAME);
		OrderManager orderManager=(OrderManager)ModelUtil.getBean(OrderManager.MANAGER_NAME);*/
		/*InvoiceViewHandler invoiceViewHandler = new InvoiceViewHandler(
				new InvoiceApplyList(login.getUserType(), faktureringVManager),
				login.getUserType(), login.getApplicationUser(),accidentManager,deviationManager,
				preventiveActionManager,articleTypeManager,attributeManager,
				externalOrderManager,
				orderManager,attributeChoiceManager);*/
		invoiceViewHandler.addCloseListener(this);
		ApplyListView<FaktureringV> invoiceView = new ApplyListView<FaktureringV>(
				invoiceViewHandler, false);
		JFrame jFrame = new JFrame("Fakturering");
		jFrame.setSize(invoiceViewHandler.getWindowSize());
		jFrame.setIconImage(IconEnum.ICON_UGLAND_BIG.getIcon().getImage());
		jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		WindowInterface frame = new JFrameAdapter(jFrame);

		frame.add(invoiceView.buildPanel(frame));
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
		login=aLogin;
		
	}

}
