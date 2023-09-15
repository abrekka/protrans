package no.ugland.utransprod.gui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer vindu for produksjon
 * 
 * @author atle.brekka
 * @param <T>
 */
public class ApplyListView<T extends Applyable> implements Viewer {
	JXTable tableAppList;

	JButton buttonCancel;

	JButton buttonApplied;
	JButton buttonPause;

	AbstractProductionPackageViewHandler<T> viewHandler;

	JButton buttonRefresh;

	JCheckBox checkBoxFilter;

	JButton buttonNotApplied;

	JButton buttonSearch;

	protected JButton buttonPrint;

	JButton buttonDeviation;

	protected boolean printable;

	// JComboBox comboBoxProductAreaGroup;
	protected JButton buttonStart;
	protected JButton buttonNotStart;
	protected JButton buttonAddAccident;
	protected JButton buttonRealProductionHours;

	/**
	 * @param aViewHandler
	 * @param printButton
	 */
	public ApplyListView(final AbstractProductionPackageViewHandler<T> aViewHandler, final boolean printButton) {
		printable = printButton;
		viewHandler = aViewHandler;
	}

	/**
	 * Initierer komponenter
	 * 
	 * @param window
	 */
	protected void initComponents(final WindowInterface window) {
		window.setName(viewHandler.getWindowTitle());
		checkBoxFilter = viewHandler.getCheckBoxFilter(window);
		tableAppList = viewHandler.getTable(window);
		buttonCancel = viewHandler.getButtonCancel(window);
		buttonApplied = viewHandler.getButtonApply(window);
		buttonPause = viewHandler.getButtonPause(window);
		buttonRefresh = viewHandler.getButtonRefresh(window);

		buttonNotApplied = viewHandler.getButtonUnapply(window);
		buttonRealProductionHours = viewHandler.getButtonRealProductionHours();
		buttonSearch = viewHandler.getButtonSearch(window);
		buttonPrint = viewHandler.getButtonPrint(window);
		buttonDeviation = viewHandler.getButtonDeviation(window);
		// comboBoxProductAreaGroup = viewHandler.getComboBoxProductAreaGroup();
		buttonStart = viewHandler.getButtonStart();
		buttonNotStart = viewHandler.getButtonNotStart();
		buttonAddAccident = viewHandler.getButtonAddAccident(window);
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
		builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh, buttonCancel), cc.xyw(2, 11, 7));
		return builder.getPanel();
	}

	/**
	 * Lager knappepanel
	 * 
	 * @return panel
	 */
	protected JPanel buildButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		if (buttonStart != null) {
			builder.addGridded(buttonStart);
			builder.addRelatedGap();
			builder.addGridded(buttonNotStart);

			builder.addRelatedGap();
		}
		if (viewHandler.isProductionWindow()) {
			builder.addGridded(buttonPause);
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
		if (printable) {
			builder.addRelatedGap();
			builder.addGridded(buttonPrint);
		}
		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#buildWindow()
	 */
	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame(viewHandler.getWindowTitle(),
				viewHandler.getWindowSize(), true);
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
		viewHandler.init();

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
