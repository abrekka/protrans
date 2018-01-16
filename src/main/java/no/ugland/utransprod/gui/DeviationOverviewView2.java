package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler2;
import no.ugland.utransprod.gui.handlers.PreventiveActionViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.InternalFrameBuilder;

/**
 * Oversiktsview for avvik
 * 
 * @author atle.brekka
 */
public class DeviationOverviewView2 implements CloseListener, Viewer {// extends
																		// OverviewView<Deviation,
																		// DeviationModel>
																		// {
	private WindowInterface mainWindow;
	private JComponent component;
	private JCheckBox checkBoxFilterDone;

	private JCheckBox checkBoxFilterOwn;

	private PreventiveActionViewHandler preventiveActionViewHandler;
	private DeviationViewHandler2 viewHandler;
	private boolean useSearchButton;

	private boolean useNewButton;
	JLabel labelHeading;
	JButton buttonAdd;
	JButton buttonRemove;

	JButton buttonSearch;

	JButton buttonCancel;
	JButton buttonEdit;
	JButton buttonExcel;
	JButton buttonRefresh;
	JXTable table;
	private MouseListener doubleClickHandler;
	private ManagerRepository managerRepository;

	@Inject
	public DeviationOverviewView2(

			PreventiveActionViewHandler aPreventiveActionViewHandler,
			@Assisted DeviationViewHandler2 deviationViewHandler, @Assisted boolean useSearchButton,
			@Assisted Order aOrder, @Assisted boolean doSeeAll, @Assisted boolean forOrderInfo,
			@Assisted boolean isForRegisterNew, @Assisted Deviation notDisplayDeviation,
			@Assisted boolean isDeviationTableEditable, ManagerRepository managerRepository) {
		// super(deviationViewHandler, useSearchButton);
		this.managerRepository = managerRepository;
		this.useSearchButton = useSearchButton;
		useNewButton = true;
		viewHandler = deviationViewHandler;
		preventiveActionViewHandler = aPreventiveActionViewHandler;
	}

	protected void initComponents(final WindowInterface window) {
		viewHandler.setWindowInterface(window);
		labelHeading = new JLabel(viewHandler.getHeading());
		labelHeading = viewHandler.getLabelHeading();
		buttonAdd = viewHandler.getAddButton(window);
		buttonRemove = viewHandler.getRemoveButton(window);
		buttonSearch = viewHandler.getSearchButton(window);
		buttonCancel = viewHandler.getCancelButton(window);
		buttonEdit = viewHandler.getEditButton(window);
		buttonExcel = viewHandler.getExcelButton(window);
		buttonRefresh = viewHandler.getRefreshButton(window);
		table = viewHandler.getTable(window);
		viewHandler.setColumnWidth(table);
		initEventHandling(window);
		updateActionEnablement();

		checkBoxFilterDone = viewHandler.getCheckBoxFilterDone();
		checkBoxFilterOwn = viewHandler.getCheckBoxFilterOwn();
	}

	protected void initEventHandling(WindowInterface window) {
		viewHandler.setSelectionEmptyHandler(new SelectionEmptyHandler());
		doubleClickHandler = viewHandler.getDoubleClickHandler(window);
		table.addMouseListener(doubleClickHandler);
		viewHandler.addCloseListener(this);
	}

	// @Override
	// protected void initComponents(WindowInterface window) {
	// super.initComponents(window);
	// checkBoxFilterDone = ((DeviationViewHandler)
	// viewHandler).getCheckBoxFilterDone();
	// checkBoxFilterOwn = ((DeviationViewHandler)
	// viewHandler).getCheckBoxFilterOwn();
	//
	// }

	/**
	 * @see no.ugland.utransprod.gui.OverviewView#buildPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	// @Override
	public JComponent buildPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("15dlu," + viewHandler.getTableWidth() + ":grow,15dlu",
				"10dlu,fill:300dlu:grow,3dlu,p");
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

		PreventiveActionOverviewView preventiveActionOverviewView = new PreventiveActionOverviewView(
				preventiveActionViewHandler);
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
		FormLayout layout = new FormLayout(viewHandler.getTableWidth() + ":grow,3dlu,p,3dlu,p",
				"p,3dlu,fill:p:grow,5dlu,p");
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

		builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel,buttonRefresh), cc.xyw(1, 5, 4));

		return builder.getPanel();

	}

	protected JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		if (useNewButton) {
			builder.addGridded(buttonAdd);
			builder.addRelatedGap();
		}
		builder.addGridded(buttonEdit);
		builder.addRelatedGap();
		builder.addGridded(buttonRemove);
		if (useSearchButton) {
			builder.addRelatedGap();
			builder.addGridded(buttonSearch);
		}

		return builder.getPanel();
	}

	/**
	 * Bygger panel for å vise evvik for en order i ordrevindu
	 * 
	 * @return panel
	 */
	private JPanel buildDeviationPanelForOrderInfo() {
		FormLayout layout = new FormLayout(viewHandler.getTableWidth() + ":grow,3dlu,p",
				"p,3dlu," + viewHandler.getTableHeight() + ":grow");
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		PanelBuilder builder = new PanelBuilder(layout);
		JScrollPane scrollPaneTable = new JScrollPane(table);
		CellConstraints cc = new CellConstraints();
		scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
		builder.add(checkBoxFilterDone, cc.xy(1, 1));
		builder.add(scrollPaneTable, cc.xywh(1, 3, 1, 1));
		if (viewHandler.useButtons()) {
			builder.add(buildButtonPanel(), cc.xywh(3, 3, 1, 1));
		}
		return builder.getPanel();
	}

	/**
	 * @see no.ugland.utransprod.gui.OverviewView#updateActionEnablement()
	 */
	// @Override
	protected void updateActionEnablement() {
		// Deviation deviation = (Deviation)
		// viewHandler.getObjectSelectionList().getSelection();
		DeviationV deviationV = (DeviationV) viewHandler.getTableSelection();
		if (deviationV != null) {
			Deviation deviation = managerRepository.getDeviationManager().findById(deviationV.getDeviationId());
			boolean hasSelection = viewHandler.objectSelectionListHasSelection();
			buttonEdit.setEnabled(hasSelection);
			if (viewHandler.hasWriteAccess()) {
				if (deviation != null) {

					JobFunction jobFunction = deviation.getDeviationFunction();
					ApplicationUser manager = null;
					ApplicationUser currentUser = viewHandler.getApplicationUser();
					if (jobFunction != null) {
						manager = jobFunction.getManager();
					}
					if ((currentUser != null && currentUser.equals(manager))
							|| (currentUser != null && viewHandler.getUserType().isAdministrator())) {
						buttonRemove.setEnabled(hasSelection);
					} else {
						buttonRemove.setEnabled(false);
					}

				}
			}
		}
	}

	public void setComponentEnablement(boolean enable) {
		viewHandler.setComponentEnablement(enable);

	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public void windowClosed() {

	}

	protected class SelectionEmptyHandler implements PropertyChangeListener {

		/**
		 * @param evt
		 */
		public final void propertyChange(final PropertyChangeEvent evt) {
			updateActionEnablement();
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#cleanUp()
	 */
	public final void cleanUp() {

	}

	public final void initWindow() {
		viewHandler.init();

	}

	public final boolean useDispose() {
		return viewHandler.getDisposeOnClose();
	}

	public final String getTitle() {
		return viewHandler.getTitle();
	}

	public final WindowInterface buildWindow() {
		mainWindow = InternalFrameBuilder.buildInternalFrame(viewHandler.getTitle(), viewHandler.getWindowSize(), true);

		component = buildPanel(mainWindow);
		mainWindow.add(component, BorderLayout.CENTER);

		return mainWindow;
	}
}
