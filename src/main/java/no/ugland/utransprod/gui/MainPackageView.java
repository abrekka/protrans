package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.MainPackageViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av vindu for pakking av garasjepakke
 * 
 * @author atle.brekka
 * 
 */
public class MainPackageView implements Viewer {
    private MainPackageViewHandler viewHandler;

    private JXTable tableOrders;

    private JXTable tablePostShipment;

    private JButton buttonCancel;

    private JXTable tableOrderLines;

    private JPanel panelCollies;

    private JPanel panelColliesMain;

    private JButton buttonRefresh;

    private JCheckBox checkBoxShowPackaged;

    private JButton buttonAddArticle;

    private JCheckBox checkBoxReadyVegg;
    private JCheckBox checkBoxReadyTakstol;
    private JCheckBox checkBoxReadyPakk;

    private JButton buttonRemoveArticle;

    private JButton buttonSearchOrder;

    private JLabel labelDayValue;

    private JLabel labelWeekValue;

    private WindowInterface currentWindow;

    private JLabel labelPackedBy;
    private JLabel labelPackedByTross;
    private JLabel labelPackedByPakk;

    private JLabel labelBudget;

    private JList listComments;

    private JButton buttonAddComment;

    private JComboBox comboBoxProductAreaGroup;
    private JComboBox comboBoxPakketype;

    public MainPackageView(MainPackageViewHandler handler) {
	viewHandler = handler;
    }

    /**
     * Initierere vinduskomponenter
     * 
     * @param window
     */
    private void initComponents(WindowInterface window) {
	comboBoxPakketype = viewHandler.getComboBoxPakketype();
	tableOrders = viewHandler.getTableOrders(window);
	tablePostShipment = viewHandler.getTablePostShipment(window);
	buttonCancel = viewHandler.getButtonCancel(window);
	tableOrderLines = viewHandler.getTableOrderLines(window);
	panelColliesMain = buildColliesMainPanel();

	buttonRefresh = viewHandler.getButtonRefresh(window);
	checkBoxShowPackaged = viewHandler.getCheckBoxShowPackaged();
	buttonAddArticle = viewHandler.getButtonAddArticle(window);
	checkBoxReadyVegg = viewHandler.getCheckBoxReadyVegg(window);
	checkBoxReadyTakstol = viewHandler.getCheckBoxReadyTakstol(window);
	checkBoxReadyPakk = viewHandler.getCheckBoxReadyPakk(window);
	buttonRemoveArticle = viewHandler.getButtonRemoveArticle(window);

	buttonSearchOrder = viewHandler.getButtonSearchOrder(window);

	labelDayValue = viewHandler.getLabelDayValue();
	labelWeekValue = viewHandler.getLabelWeekValue();
	labelBudget = viewHandler.getLabelBudget();

	labelPackedBy = viewHandler.getLabelPackedByWall();
	labelPackedByTross = viewHandler.getLabelPackedByTross();
	labelPackedByPakk = viewHandler.getLabelPackedByPakk();

	viewHandler.initEventHandling(this, window);
	listComments = viewHandler.getListComments();

	buttonAddComment = viewHandler.getButtonAddComment(window);
	comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();

    }

    private JPanel buildColliesMainPanel() {
	FormLayout layout = new FormLayout("fill:p:grow", "p,3dlu,fill:p:grow");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	panelCollies = new JPanel();
	CellConstraints cc = new CellConstraints();
	builder.addLabel("Kollier:", cc.xy(1, 1));
	builder.add(panelCollies, cc.xy(1, 3));

	return builder.getPanel();
    }

    private JPanel buildFilterPanel() {
	FormLayout layout = new FormLayout("70dlu,3dlu,70dlu", "p,3dlu,p");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Produktområde:", cc.xy(1, 1));
	builder.add(comboBoxProductAreaGroup, cc.xy(3, 1));
	builder.add(ButtonBarFactory.buildLeftAlignedBar(buttonSearchOrder), cc.xy(1, 3));
	builder.add(checkBoxShowPackaged, cc.xy(3, 3));

	return builder.getPanel();
    }

    private JPanel buildOrderPanel() {
	FormLayout layout = new FormLayout("200dlu", "p,3dlu,150dlu:grow");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Ordre:", cc.xy(1, 1));
	builder.add(new JScrollPane(tableOrders), cc.xy(1, 3));
	return builder.getPanel();
    }

    private JPanel buildPostShipmentPanel() {
	FormLayout layout = new FormLayout("200dlu", "p,3dlu,150dlu:grow");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Ettersendinger:", cc.xy(1, 1));
	builder.add(new JScrollPane(tablePostShipment), cc.xy(1, 3));

	return builder.getPanel();
    }

    private JPanel buildStatisticsPanel() {
	FormLayout layout = new FormLayout("50dlu,3dlu,50dlu,3dlu,50dlu,3dlu,70dlu,3dlu,p,3dlu,70dlu,3dlu,p,3dlu,p", "p,p,p");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Dagsverdi:", cc.xy(1, 1));
	builder.add(labelDayValue, cc.xy(1, 2));
	builder.addLabel("Ukesverdi:", cc.xy(3, 1));
	builder.add(labelWeekValue, cc.xy(3, 2));
	builder.addLabel("Budsjett:", cc.xy(5, 1));
	builder.add(labelBudget, cc.xy(5, 2));
	builder.add(checkBoxReadyVegg, cc.xy(7, 1));
	builder.addLabel("Pakket av:", cc.xy(9, 1));
	builder.add(labelPackedBy, cc.xy(11, 1));
	builder.add(checkBoxReadyTakstol, cc.xy(7, 2));
	builder.addLabel("Pakket av:", cc.xy(9, 2));
	builder.add(labelPackedByTross, cc.xy(11, 2));
	builder.add(checkBoxReadyPakk, cc.xy(7, 3));
	builder.addLabel("Pakket av:", cc.xy(9, 3));
	builder.add(labelPackedByPakk, cc.xy(11, 3));
	builder.addLabel("Pakketype:", cc.xy(13, 1));
	builder.add(comboBoxPakketype, cc.xy(15, 1));

	return builder.getPanel();
    }

    private JPanel buildOrderLinePanel() {
	FormLayout layout = new FormLayout("p", "p,3dlu,fill:200dlu:grow");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Artikler:", cc.xy(1, 1));
	builder.add(new JScrollPane(tableOrderLines), cc.xy(1, 3));

	return builder.getPanel();
    }

    private JPanel buildOrderLineAndCommentPanel() {
	FormLayout layout = new FormLayout("p", "fill:p:grow,3dlu,p");
	// FormLayout layout = new FormLayout("fill:p", "fill:p,3dlu,p");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.add(buildOrderLinePanel(), cc.xy(1, 1));
	builder.add(buildCommentsPanel(), cc.xy(1, 3));

	return builder.getPanel();
    }

    /**
     * Bygger vinduspanel
     * 
     * @param window
     * @return panel
     */
    public JPanel buildPanel(WindowInterface window) {
	currentWindow = window;
	initComponents(window);
	FormLayout layout = new FormLayout("10dlu,p,3dlu,300dlu,3dlu,fill:p:grow,10dlu", "10dlu,p,3dlu,fill:p:grow,3dlu,fill:p:grow,3dlu,p,5dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();

	builder.add(buildFilterPanel(), cc.xy(2, 2));
	builder.add(buildOrderPanel(), cc.xy(2, 4));
	builder.add(buildPostShipmentPanel(), cc.xy(2, 6));
	builder.add(buildStatisticsPanel(), cc.xyw(4, 2, 3));
	builder.add(buildOrderLineAndCommentPanel(), cc.xywh(4, 4, 1, 3));

	// builder.add(buildOrderlineFilterPanel(), cc.xy(6, 2));

	builder.add(panelColliesMain, cc.xywh(6, 4, 1, 3));

	builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh, buttonCancel), cc.xyw(2, 8, 5));
	return builder.getPanel();
    }

    private Component buildOrderlineFilterPanel() {
	FormLayout layout = new FormLayout("p,3dlu,p", "p");
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Pakketype:", cc.xy(1, 1));
	builder.add(comboBoxPakketype, cc.xy(3, 1));

	return builder.getPanel();
    }

    /**
     * Bygger kommentarpanel
     * 
     * @return panel
     */
    private JPanel buildCommentsPanel() {
	// FormLayout layout = new FormLayout("fill:p:grow",
	// "p,3dlu,85dlu,3dlu,p");
	FormLayout layout = new FormLayout("280dlu", "p,3dlu,85dlu,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
	// layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Kommentarer:", cc.xy(1, 1));
	builder.add(new JScrollPane(listComments), cc.xy(1, 3));
	builder.add(ButtonBarFactory.buildCenteredBar(buttonAddComment, buttonAddArticle, buttonRemoveArticle), cc.xy(1, 5));
	return builder.getPanel();
    }

    /**
     * Oppdaterer kollipanel
     * 
     * @param clear
     */
    public void updateColliesPanel(boolean clear) {
	panelColliesMain.remove(panelCollies);
	if (clear) {
	    panelCollies = new JPanel();
	} else {
	    panelCollies = viewHandler.getColliListView(currentWindow);
	}
	CellConstraints cc = new CellConstraints();
	panelColliesMain.add(panelCollies, cc.xy(1, 3));
	panelColliesMain.repaint();
	panelColliesMain.validate();

	if (panelColliesMain.getParent() != null) {
	    panelColliesMain.getParent().repaint();
	    panelColliesMain.getParent().validate();
	}

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
     * @see no.ugland.utransprod.gui.Viewer#getTitle()
     */
    public String getTitle() {
	return viewHandler.getWindowTitle();
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#initWindow()
     */
    public void initWindow() {
	viewHandler.doRefresh(null);

    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#cleanUp()
     */
    public void cleanUp() {
    }

    /**
     * @see no.ugland.utransprod.gui.Viewer#useDispose()
     */
    public boolean useDispose() {
	return viewHandler.getDisposeOnClose();
    }

}
