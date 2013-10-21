package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.google.inject.Inject;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import no.ugland.utransprod.gui.edit.EditViewable;
import no.ugland.utransprod.gui.handlers.TrossReadyViewHandler;

public class TrossReadyView implements EditViewable{
	private TrossReadyViewHandler viewHandler;
	private JComboBox comboBoxUsers;
	private JButton buttonOk;
	private JButton buttonCancel;
	private JRadioButton radioButtonStart;
	private JRadioButton radioButtonFinish;
	private JTextField textfieldCost;
	
	@Inject
	public TrossReadyView(TrossReadyViewHandler handler){
		viewHandler=handler;
	}
	public JComponent buildPanel(WindowInterface window) {
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,p,3dlu,p,10dlu", "10dlu,p,3dlu,p,3dlu,p,5dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        
        builder.addLabel("Tegner:",cc.xy(2, 2));
        builder.add(comboBoxUsers,cc.xy(4, 2));
        builder.add(radioButtonStart,cc.xy(2, 4));
        builder.add(radioButtonFinish,cc.xy(4, 4));
        builder.addLabel("Takstolkostnad:",cc.xy(2, 6));
        builder.add(textfieldCost,cc.xy(4, 6));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk,buttonCancel),cc.xyw(2, 8,3));
        
        return builder.getPanel();
	}

	public String getDialogName() {
		return "TrossReadyView";
	}

	public String getHeading() {
		return "Takstolprosjektering";
	}

	private void initComponents(WindowInterface window){
		textfieldCost=viewHandler.getTextFieldCost();
		comboBoxUsers=viewHandler.getComboBoxUsers();
		radioButtonStart=viewHandler.getRadioButtonStart();
		radioButtonFinish=viewHandler.getRadioButtonFinsih();
		buttonOk=viewHandler.getButtonOk(window);
        buttonCancel=viewHandler.getButtonCancel(window);
	}
}
