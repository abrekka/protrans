package no.ugland.utransprod.gui;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.PreventiveActionModel;
import no.ugland.utransprod.model.PreventiveAction;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class PreventiveActionOverviewView extends OverviewView<PreventiveAction, PreventiveActionModel> {

    private JCheckBox checkBoxFilterDone;

    private JCheckBox checkBoxFilterOwn;

    public PreventiveActionOverviewView(PreventiveActionViewHandler handler) {
	super(handler);
    }

    public JPanel buildPreventiveActionPanel(WindowInterface window) {
	initComponents(window);
	FormLayout layout = new FormLayout(viewHandler.getTableWidth() + ":grow,3dlu,p,3dlu,p", "p,3dlu,p:grow,5dlu,p:grow");
	// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
	PanelBuilder builder = new PanelBuilder(layout);
	JScrollPane scrollPaneTable = new JScrollPane(table);

	CellConstraints cc = new CellConstraints();
	scrollPaneTable.setBorder(Borders.EMPTY_BORDER);

	builder.add(checkBoxFilterOwn, cc.xy(3, 1));
	builder.add(checkBoxFilterDone, cc.xy(5, 1));
	builder.add(scrollPaneTable, cc.xywh(1, 3, 1, 1));

	builder.add(buildButtonPanel(), cc.xywh(3, 3, 3, 1));

	builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel), cc.xyw(1, 5, 4));

	return builder.getPanel();

    }

    @Override
    protected void initComponents(WindowInterface window) {
	super.initComponents(window);
	checkBoxFilterDone = ((PreventiveActionViewHandler) viewHandler).getCheckBoxFilterDone();
	checkBoxFilterOwn = ((PreventiveActionViewHandler) viewHandler).getCheckBoxFilterOwn();

    }

}
