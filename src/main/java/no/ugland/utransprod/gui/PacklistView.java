package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.PacklistViewHandler;
import no.ugland.utransprod.model.PacklistV;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * View for pkklister
 * 
 * @author atle.brekka
 * 
 */
public class PacklistView extends ApplyListView<PacklistV> {
	private JButton buttonEditOrder;

	private JButton buttonExternalOrder;

	private JLabel labelBudget;

	private JLabel labelCountWeek;

	private JLabel labelCountYear;
	private JButton buttonTrossReady;
	private JButton buttonProductionBasis;

	public PacklistView(
			AbstractProductionPackageViewHandler<PacklistV> aViewHandler) {
		super(aViewHandler, false);
	}

	/**
	 * @see no.ugland.utransprod.gui.ApplyListView#initComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initComponents(WindowInterface window) {
		super.initComponents(window);
		buttonEditOrder = ((PacklistViewHandler) viewHandler)
				.getButtonEditOrder(window);
		buttonExternalOrder = ((PacklistViewHandler) viewHandler)
				.getButtonExternalOrder(window);
		buttonProductionBasis = ((PacklistViewHandler) viewHandler)
				.getButtonProductionBasis(window);
		buttonTrossReady = ((PacklistViewHandler) viewHandler)
				.getButtonTrossReady(window);
		labelBudget = ((PacklistViewHandler) viewHandler).getLabelBudget();
		labelCountWeek = ((PacklistViewHandler) viewHandler)
				.getLabelCountWeek();
		labelCountYear = ((PacklistViewHandler) viewHandler)
				.getLabelCountYear();
		comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
	}

	/**
	 * @see no.ugland.utransprod.gui.ApplyListView#buildPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public JComponent buildPanel(WindowInterface window) {
		initComponents(window);

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,p,3dlu,35dlu,3dlu,p,3dlu,20dlu,3dlu,p,3dlu,20dlu,50dlu:grow,3dlu,p,10dlu",
				"10dlu,top:p,3dlu,top:p,top:3dlu,top:p,3dlu,top:p,120dlu:grow,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Produktområde:", cc.xy(2, 2));
		builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
		builder.addLabel("Budsjett:", cc.xy(6, 2));
		builder.add(labelBudget, cc.xy(8, 2));
		builder.addLabel("# denne uke:", cc.xy(10, 2));
		builder.add(labelCountWeek, cc.xy(12, 2));
		builder.addLabel("# dette året:", cc.xy(14, 2));
		builder.add(labelCountYear, cc.xy(16, 2));
		builder.add(checkBoxFilter, cc.xy(19, 4));

		builder.add(buildButtons(), cc.xywh(19, 6, 1, 6));
		builder.add(new JScrollPane(tableAppList), cc.xywh(2, 4, 16, 6));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh,
				buttonCancel), cc.xyw(2, 11, 18));
		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.ApplyListView#buildButtons()
	 */
	@Override
	protected JPanel buildButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonApplied);
		builder.addRelatedGap();
		builder.addGridded(buttonNotApplied);
		builder.addRelatedGap();
		builder.addGridded(buttonSearch);
		builder.addRelatedGap();
		builder.addGridded(buttonEditOrder);
		builder.addRelatedGap();
		builder.addGridded(buttonExternalOrder);
		builder.addRelatedGap();
		builder.addGridded(buttonDeviation);
		builder.addRelatedGap();
		builder.addGridded(buttonTrossReady);
		builder.addRelatedGap();
		builder.addGridded(buttonProductionBasis);

		return builder.getPanel();
	}

}
