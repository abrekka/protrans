package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.TakstolProductionViewHandler;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.model.Produceable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.debug.FormDebugPanel;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.CellConstraints.Alignment;
import com.jgoodies.forms.layout.FormLayout;

public class TakstolApplyListView extends ApplyListView<Produceable> {
	private JComboBox comboBoxProductionUnit;
	private JButton buttonShowTakstolInfo;
	private JButton buttonStartetKapping;
	private JButton buttonIkkeStartetKapping;
	private JButton buttonFerdigKappet;
	private JButton buttonIkkeFerdigKappet;
	private JTextField textFieldGreenText;
	private JTextField textFieldBlueText;
	private JTextField textFieldGreen;
	private JTextField textFieldYellow;
	private JTextField textField90;

	public TakstolApplyListView(final TakstolProductionViewHandler viewHandler) {
		super(viewHandler, false);
	}

	@Override
	protected final void initComponents(final WindowInterface window) {
		textFieldGreenText=new JTextField("Kapping ferdig");
		textFieldGreenText.setForeground(ColorEnum.GREEN.getColor());
		textFieldGreenText.setHorizontalAlignment(JTextField.CENTER);
		textFieldBlueText=new JTextField("Kapping startet");
		textFieldBlueText.setForeground(ColorEnum.BLUE.getColor());
		textFieldBlueText.setHorizontalAlignment(JTextField.CENTER);
		textFieldYellow=new JTextField("Startet prod");
		textFieldYellow.setBackground(ColorEnum.YELLOW.getColor());
		textFieldYellow.setHorizontalAlignment(JTextField.CENTER);
		textFieldGreen=new JTextField("Produsert");
		textFieldGreen.setBackground(ColorEnum.GREEN.getColor());
		textFieldGreen.setHorizontalAlignment(JTextField.CENTER);
		textField90=new JTextField("90%");
		textField90.setBackground(ColorEnum.GREY.getColor());
		textField90.setHorizontalAlignment(JTextField.CENTER);
		comboBoxProductionUnit = ((TakstolProductionViewHandler) viewHandler)
				.getComboBoxProductionUnit();
		buttonShowTakstolInfo = ((TakstolProductionViewHandler) viewHandler)
				.getButtonShowTakstolInfo(window);
		buttonStartetKapping = ((TakstolProductionViewHandler) viewHandler)
				.getButtonStartetKapping(window);
		buttonIkkeStartetKapping = ((TakstolProductionViewHandler) viewHandler)
				.getButtonIkkeStartetKapping(window);
		buttonFerdigKappet = ((TakstolProductionViewHandler) viewHandler)
				.getButtonFerdigKappet(window);
		buttonIkkeFerdigKappet = ((TakstolProductionViewHandler) viewHandler)
				.getButtonIkkeFerdigKappet(window);
		super.initComponents(window);
	}

	@Override
	public final JComponent buildPanel(final WindowInterface window) {
		initComponents(window);

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,p,3dlu,40dlu,3dlu,55dlu,55dlu,55dlu,45dlu,35dlu,1dlu:grow,3dlu,p,10dlu,p,3dlu,p",
				"10dlu,top:p,3dlu,top:p,top:3dlu,top:p,3dlu,top:p,120dlu:grow,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
//		 PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(checkBoxFilter, cc.xy(17, 2));
		builder.addLabel("Produktområde:", cc.xy(2, 2));
		builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
		builder.addLabel("Produksjonsenhet:", cc.xy(6, 2));
		builder.add(comboBoxProductionUnit, cc.xy(8, 2));
		builder.add(textFieldBlueText, cc.xy(10, 2));
		builder.add(textFieldGreenText, cc.xy(11, 2));
		builder.add(textFieldYellow, cc.xy(12, 2));
		builder.add(textFieldGreen, cc.xy(13, 2));
		builder.add(textField90, cc.xy(14, 2));
		builder.add(buildButtons(), cc.xywh(17, 4, 1, 6));
		builder.add(new JScrollPane(tableAppList), cc.xywh(2, 4, 14, 6));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh,
				buttonCancel), cc.xyw(2, 11, 17));
		return builder.getPanel();
	}

	protected JPanel buildButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonStartetKapping);
		builder.addRelatedGap();
		builder.addGridded(buttonIkkeStartetKapping);
		builder.addRelatedGap();
		builder.addGridded(buttonFerdigKappet);
		builder.addRelatedGap();
		builder.addGridded(buttonIkkeFerdigKappet);
		builder.addUnrelatedGap();
		builder.addGlue();
		if (buttonStart != null) {
			builder.addGridded(buttonStart);
			builder.addRelatedGap();
			builder.addGridded(buttonNotStart);
			builder.addRelatedGap();
		}
		builder.addGridded(buttonApplied);
		builder.addRelatedGap();
		builder.addGridded(buttonNotApplied);
		builder.addUnrelatedGap();
		builder.addGlue();
		builder.addGridded(buttonSearch);
		builder.addUnrelatedGap();
		builder.addGridded(buttonDeviation);
		builder.addUnrelatedGap();
		builder.addGridded(buttonAddAccident);
		builder.addUnrelatedGap();
		builder.addGridded(buttonShowTakstolInfo);
		return builder.getPanel();
	}

}
