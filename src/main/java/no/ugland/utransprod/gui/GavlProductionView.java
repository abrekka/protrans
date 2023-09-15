package no.ugland.utransprod.gui;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.GavlProductionViewHandler;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.model.Produceable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class GavlProductionView extends ApplyListView<Produceable> {
	private JCheckBox checkBoxFilterStandard;
	private JCheckBox checkBoxFilterOwn;
	private JTextField textFieldGreen;
	private JTextField textFieldYellow;
	private JTextField textFieldOwn;

	public GavlProductionView(AbstractProductionPackageViewHandler<Produceable> aViewHandler) {
		super(aViewHandler, false);
	}

	/**
	 * Bygger panel for komponenter
	 * 
	 * @param window
	 * @return panel
	 */
	public JComponent buildPanel(final WindowInterface window) {
		initComponents(window);

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,40dlu,3dlu,40dlu,3dlu,40dlu," + viewHandler.getTableWidth()
						+ ":grow,3dlu,p,10dlu,p,3dlu,p,10dlu",
				"10dlu,top:p,3dlu,top:p,top:3dlu,top:p,3dlu,top:p,3dlu,top:p,3dlu,top:p,120dlu:grow,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(checkBoxFilter, cc.xy(13, 2));
		builder.add(checkBoxFilterStandard, cc.xy(13, 4));
		builder.add(checkBoxFilterOwn, cc.xy(13, 6));
		// builder.addLabel("Produktområde:", cc.xy(2, 2));
		// builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
		builder.add(textFieldGreen, cc.xy(6, 2));
		builder.add(textFieldYellow, cc.xy(8, 2));
		builder.add(textFieldOwn, cc.xy(10, 2));
		builder.add(buildButtons(), cc.xywh(13, 8, 1, 6));

		tableAppList.getColumnExt(GavlProductionViewHandler.GavlColumn.STANDARD.getColumnName()).setVisible(false);
		builder.add(new JScrollPane(tableAppList), cc.xywh(2, 4, 10, 10));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh, buttonCancel), cc.xyw(2, 15, 13));
		return builder.getPanel();
	}

	protected void initComponents(final WindowInterface window) {
		checkBoxFilterStandard = ((GavlProductionViewHandler) viewHandler).getCheckBoxFilterStandard(window);
		checkBoxFilterOwn = ((GavlProductionViewHandler) viewHandler).getCheckBoxFilterOwn(window);
		window.setName(viewHandler.getWindowTitle());
		checkBoxFilter = viewHandler.getCheckBoxFilter(window);
		tableAppList = viewHandler.getTable(window);

		buttonCancel = viewHandler.getButtonCancel(window);
		buttonApplied = viewHandler.getButtonApply(window);
		buttonRefresh = viewHandler.getButtonRefresh(window);

		buttonNotApplied = viewHandler.getButtonUnapply(window);
		buttonSearch = viewHandler.getButtonSearch(window);
		buttonPrint = viewHandler.getButtonPrint(window);
		buttonDeviation = viewHandler.getButtonDeviation(window);
		// comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
		buttonStart = viewHandler.getButtonStart();
		buttonPause = viewHandler.getButtonPause(window);
		buttonNotStart = viewHandler.getButtonNotStart();
		buttonRealProductionHours = viewHandler.getButtonRealProductionHours();
		buttonAddAccident = viewHandler.getButtonAddAccident(window);

		textFieldGreen = new JTextField("Produsert");
		textFieldGreen.setBackground(ColorEnum.GREEN.getColor());
		textFieldGreen.setHorizontalAlignment(JTextField.CENTER);
		textFieldYellow = new JTextField("Startet");
		textFieldYellow.setBackground(ColorEnum.YELLOW.getColor());
		textFieldYellow.setHorizontalAlignment(JTextField.CENTER);
		textFieldOwn = new JTextField("Egenordre");
		textFieldOwn.setForeground(ColorEnum.RED.getColor());
		textFieldOwn.setHorizontalAlignment(JTextField.CENTER);
	}

}
