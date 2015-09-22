package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler2;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Viser produksjonsoversikt
 * 
 * @author atle.brekka
 */
public class ProductionOverviewView2 implements Viewer {
    private ProductionOverviewViewHandler2 viewHandler;

    private JXTable productionTable;

    private JButton buttonClose;

    // private JComboBox comboBoxProductAreaGroup;

    private JButton buttonRefresh;

    // private JButton buttonSearch;
    private JButton buttonFilter;

    private JCheckBox checkBoxFilter;
    // private JButton buttonShowTakstolInfo;
    private JButton buttonExcel;
    private JLabel labelAntallGarasjer;
    private JLabel labelSumTidVegg;
    private JLabel labelSumTidGavl;
    private JLabel labelSumTidPakk;

    @Inject
    public ProductionOverviewView2(ProductionOverviewViewHandler2 viewHandler) {
	this.viewHandler = viewHandler;
    }

    private void initComponents(WindowInterface window) {
	buttonExcel = viewHandler.getButtonExcel(window);
	productionTable = viewHandler.getTable(window);
	buttonClose = viewHandler.getCancelButton(window);
	checkBoxFilter = viewHandler.getCheckBoxFilter();

	buttonRefresh = viewHandler.getButtonRefresh(window);
	// buttonSearch = viewHandler.getButtonSearch(window);
	buttonFilter = viewHandler.getButtonFilter(window);
	// comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
	// buttonShowTakstolInfo = viewHandler.getButtonShowTakstolInfo(window);
	labelAntallGarasjer = viewHandler.getLabelAntallGarasjer();
	labelSumTidVegg = viewHandler.getLabelSumTidVegg();
	labelSumTidGavl = viewHandler.getLabelSumTidGavl();
	labelSumTidPakk = viewHandler.getLabelSumTidPakk();
    }

    /**
     * Bygger panel
     * 
     * @param window
     * @return panel
     */
    public Component buildPanel(WindowInterface window) {
	initComponents(window);
	FormLayout layout = new FormLayout("10dlu,p,3dlu,p,3dlu,p,3dlu,p,30dlu,p,3dlu,p,30dlu,p,3dlu,p,30dlu,p,3dlu,p:grow,10dlu",
		"10dlu,p,3dlu,fill:300dlu:grow,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();
	JScrollPane scrollPane = new JScrollPane(productionTable);
	scrollPane.setName("ScrollPaneTable");
	// builder.addLabel("Produktområde:", cc.xy(2, 2));
	// builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
	// builder.add(buttonSearch, cc.xy(6, 2));
	builder.add(checkBoxFilter, cc.xy(2, 2));
	builder.add(buttonFilter, cc.xy(4, 2));
	builder.addLabel("Antall garasjer:", cc.xy(6, 2));
	builder.add(labelAntallGarasjer, cc.xy(8, 2));
	builder.addLabel("Sum estimert tid vegg:", cc.xy(10, 2));
	builder.add(labelSumTidVegg, cc.xy(12, 2));
	builder.addLabel("Sum estimert tid gavl:", cc.xy(14, 2));
	builder.add(labelSumTidGavl, cc.xy(16, 2));
	builder.addLabel("Sum estimert tid pakk:", cc.xy(18, 2));
	builder.add(labelSumTidPakk, cc.xy(20, 2));
	builder.add(scrollPane, cc.xyw(2, 4, 20));
	builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel, buttonRefresh, buttonClose), cc.xyw(2, 6, 20));

	return builder.getPanel();
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#buildWindow()
     */
    public WindowInterface buildWindow() {
	WindowInterface window = InternalFrameBuilder.buildInternalFrame(viewHandler.getWindowTitle(), viewHandler.getWindowSize(), true);
	window.add(buildPanel(window), BorderLayout.CENTER);

	return window;
    }

    /**
     * Gjør ingenting
     * 
     * @see no.ugland.utransprod.gui.Viewer#cleanUp()
     */
    public void cleanUp() {
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#getTitle()
     */
    public String getTitle() {
	return viewHandler.getWindowTitle();
    }

    /**
     * Gjør ingenting
     * 
     * @see no.ugland.utransprod.gui.Viewer#initWindow()
     */
    public void initWindow() {
    }

    /**
     * Returnerer true
     * 
     * @see no.ugland.utransprod.gui.Viewer#useDispose()
     */
    public boolean useDispose() {
	return true;
    }

}
