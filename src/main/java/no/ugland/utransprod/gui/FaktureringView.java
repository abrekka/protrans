package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import no.ugland.utransprod.gui.handlers.InvoiceViewHandler;
import no.ugland.utransprod.model.FaktureringV;

public class FaktureringView extends ApplyListView<FaktureringV> {
	private JButton buttonExcel;
	protected JButton buttonSendtMail;
	protected JButton buttonIkkeSendtMail;

	public FaktureringView(InvoiceViewHandler invoiceViewHandler) {
		super(invoiceViewHandler, false);
	}

	@Override
	protected void initComponents(WindowInterface window) {
		super.initComponents(window);
		buttonExcel = ((InvoiceViewHandler) viewHandler).getButtonExcel(window);
		buttonSendtMail = ((InvoiceViewHandler) viewHandler).getButtonSendtMail();
		buttonIkkeSendtMail = ((InvoiceViewHandler) viewHandler).getButtonIkkeSendtMail();
	}

	@Override
	public JComponent buildPanel(WindowInterface window) {
		initComponents(window);

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p," + viewHandler.getTableWidth() + ":grow,3dlu,p,10dlu,p,3dlu,p",
				"10dlu,top:p,3dlu,top:p,top:3dlu,top:p,3dlu,top:p,120dlu:grow,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(checkBoxFilter, cc.xy(7, 2));
		// builder.addLabel("Produktområde:", cc.xy(2, 2));
		// builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
		builder.add(buildButtons(), cc.xywh(7, 4, 1, 6));
		builder.add(new JScrollPane(tableAppList), cc.xywh(2, 4, 4, 6));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh, buttonCancel, buttonExcel), cc.xyw(2, 11, 7));
		
		
		return builder.getPanel();
	}

	@Override
	protected JPanel buildButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		if (buttonStart != null) {
			builder.addGridded(buttonStart);
			builder.addRelatedGap();
			builder.addGridded(buttonNotStart);

			builder.addRelatedGap();
		}
		builder.addGridded(buttonApplied);
		builder.addRelatedGap();
		builder.addGridded(buttonNotApplied);
		builder.addRelatedGap();
		builder.addGridded(buttonRealProductionHours);
		builder.addRelatedGap();
		builder.addGridded(buttonSearch);
		builder.addRelatedGap();
		builder.addGridded(buttonDeviation);
		builder.addRelatedGap();
		builder.addGridded(buttonAddAccident);
		builder.addRelatedGap();
		builder.addGridded(buttonSendtMail);
		builder.addRelatedGap();
		builder.addGridded(buttonIkkeSendtMail);
		return builder.getPanel();
	}

}
