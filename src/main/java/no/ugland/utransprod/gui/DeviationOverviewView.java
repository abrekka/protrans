package no.ugland.utransprod.gui;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.gui.model.DeviationModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Oversiktsview for avvik
 * 
 * @author atle.brekka
 */
public class DeviationOverviewView extends OverviewView<Deviation, DeviationModel> {
    private JCheckBox checkBoxFilterDone;

    private JCheckBox checkBoxFilterOwn;

    private PreventiveActionViewHandler preventiveActionViewHandler;

    @Inject
    public DeviationOverviewView(

    PreventiveActionViewHandler aPreventiveActionViewHandler, @Assisted DeviationViewHandler deviationViewHandler, @Assisted boolean useSearchButton,
	    @Assisted Order aOrder, @Assisted boolean doSeeAll, @Assisted boolean forOrderInfo, @Assisted boolean isForRegisterNew,
	    @Assisted Deviation notDisplayDeviation, @Assisted boolean isDeviationTableEditable) {
	super(deviationViewHandler, useSearchButton);
	preventiveActionViewHandler = aPreventiveActionViewHandler;
    }

    @Override
    protected void initComponents(WindowInterface window) {
	super.initComponents(window);
	checkBoxFilterDone = ((DeviationViewHandler) viewHandler).getCheckBoxFilterDone();
	checkBoxFilterOwn = ((DeviationViewHandler) viewHandler).getCheckBoxFilterOwn();

    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#buildPanel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public JComponent buildPanel(WindowInterface window) {
	FormLayout layout = new FormLayout("15dlu," + viewHandler.getTableWidth() + ":grow,15dlu", "10dlu,fill:300dlu:grow,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(layout, new
	// FormDebugPanel());
	CellConstraints cc = new CellConstraints();

	window.setName("Overview" + viewHandler.getClassName());
	JTabbedPane tabbedPane = new JTabbedPane();
	tabbedPane.add("Avvik", buildDeviationPanel(window, false));
	JPanel preventiveActionPanel = buildPreventiveActionPanel(window);
	String preventiveActionHeading = getPreventiveActionHeading();
	tabbedPane.add(preventiveActionHeading, preventiveActionPanel);

	builder.add(tabbedPane, cc.xy(2, 2));
	builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel), cc.xy(2, 4));

	return builder.getPanel();
    }

    private String getPreventiveActionHeading() {
	StringBuilder heading = new StringBuilder("Korrigerende tiltak");
	int ownPreventiveActionCount = preventiveActionViewHandler.getRowCount();
	if (ownPreventiveActionCount != 0) {
	    heading.append("(").append(ownPreventiveActionCount).append(")");
	}
	return heading.toString();
    }

    private JPanel buildPreventiveActionPanel(WindowInterface window) {

	PreventiveActionOverviewView preventiveActionOverviewView = new PreventiveActionOverviewView(preventiveActionViewHandler);
	return preventiveActionOverviewView.buildPreventiveActionPanel(window);
    }

    /**
     * Bygger panel for å vise avvik
     * 
     * @param window
     * @param onlyInfo
     * @return panel med avvik
     */
    public JPanel buildDeviationPanel(WindowInterface window, boolean onlyInfo) {
	initComponents(window);
	if (onlyInfo) {
	    return buildDeviationPanelForOrderInfo();
	}
	// FormLayout layout = new FormLayout(viewHandler.getTableWidth() +
	// ":grow,3dlu,p,3dlu,p", "p,3dlu," + viewHandler.getTableHeight()
	// + ":grow,5dlu,p");
	FormLayout layout = new FormLayout(viewHandler.getTableWidth() + ":grow,3dlu,p,3dlu,p", "p,3dlu,fill:p:grow,5dlu,p");
	// PanelBuilder builder = new PanelBuilder(layout, new
	// FormDebugPanel());
	PanelBuilder builder = new PanelBuilder(layout);
	JScrollPane scrollPaneTable = new JScrollPane(table);

	CellConstraints cc = new CellConstraints();
	// scrollPaneTable.setBorder(Borders.EMPTY_BORDER);

	// builder.add(labelHeading, cc.xy(2, 2));
	builder.add(checkBoxFilterOwn, cc.xy(3, 1));
	builder.add(checkBoxFilterDone, cc.xy(5, 1));
	builder.add(scrollPaneTable, cc.xywh(1, 3, 3, 1));

	builder.add(buildButtonPanel(), cc.xywh(5, 3, 1, 1));

	builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel), cc.xyw(1, 5, 4));

	return builder.getPanel();

    }

    /**
     * Bygger panel for å vise evvik for en order i ordrevindu
     * 
     * @return panel
     */
    private JPanel buildDeviationPanelForOrderInfo() {
	FormLayout layout = new FormLayout(viewHandler.getTableWidth() + ":grow,3dlu,p", "p,3dlu," + viewHandler.getTableHeight() + ":grow");
	// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
	PanelBuilder builder = new PanelBuilder(layout);
	JScrollPane scrollPaneTable = new JScrollPane(table);
	CellConstraints cc = new CellConstraints();
	scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
	builder.add(checkBoxFilterDone, cc.xy(1, 1));
	builder.add(scrollPaneTable, cc.xywh(1, 3, 1, 1));
	if (((DeviationViewHandler) viewHandler).useButtons()) {
	    builder.add(buildButtonPanel(), cc.xywh(3, 3, 1, 1));
	}
	return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.OverviewView#updateActionEnablement()
     */
    @Override
    protected void updateActionEnablement() {
	// Deviation deviation = (Deviation)
	// viewHandler.getObjectSelectionList().getSelection();
	Deviation deviation = (Deviation) viewHandler.getTableSelection();
	boolean hasSelection = viewHandler.objectSelectionListHasSelection();
	buttonEdit.setEnabled(hasSelection);
	if (viewHandler.hasWriteAccess()) {
	    if (deviation != null) {

		JobFunction jobFunction = deviation.getDeviationFunction();
		ApplicationUser manager = null;
		ApplicationUser currentUser = ((DeviationViewHandler) viewHandler).getApplicationUser();
		if (jobFunction != null) {
		    manager = jobFunction.getManager();
		}
		if ((currentUser != null && currentUser.equals(manager)) || (currentUser != null && viewHandler.getUserType().isAdministrator())) {
		    buttonRemove.setEnabled(hasSelection);
		} else {
		    buttonRemove.setEnabled(false);
		}

	    }
	}
    }

    public void setComponentEnablement(boolean enable) {
	((DeviationViewHandler) viewHandler).setComponentEnablement(enable);

    }
}
