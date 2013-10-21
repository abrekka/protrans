package no.ugland.utransprod.gui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AttributeChoiceViewHandler;
import no.ugland.utransprod.gui.model.AttributeChoiceModel;
import no.ugland.utransprod.model.AttributeChoice;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class AttributeChoiceOverviewView extends
		OverviewView<AttributeChoice, AttributeChoiceModel> {
	public static final AttributeChoiceOverviewView UNKNOWN = new AttributeChoiceOverviewView() {
	};

	public AttributeChoiceOverviewView() {
		this(null);
	}

	public AttributeChoiceOverviewView(AttributeChoiceViewHandler handler) {
		super(handler, false);
	}

	@Override
	public JComponent buildPanel(WindowInterface window) {
		window.setName("Overview" + viewHandler.getClassName());
		initComponents(window);
		FormLayout layout = new FormLayout("100:grow,3dlu,p",
				"70dlu:grow,3dlu,10dlu,p,3dlu,p,3dlu,p,3dlu,p,"
						+ viewHandler.getTableHeight() + ":grow,p");
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		PanelBuilder builder = new PanelBuilder(layout);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
		CellConstraints cc = new CellConstraints();

		builder.add(scrollPaneTable, cc.xyw(1, 1, 1));

		builder.add(buildButtonPanel(), cc.xywh(3, 1, 1, 1));
		return builder.getPanel();
	}

	public void enableComponents(boolean enable) {
		if (table != null) {
			table.setEnabled(enable);
			buttonAdd.setEnabled(enable);
			this.updateActionEnablement();
		}
	}
}
