package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.util.InternalFrameBuilder;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Klasse som håndterer visning av objekter
 * 
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public class OverviewView<T, E> implements Viewer, CloseListener {
	private WindowInterface mainWindow;

	private MouseListener doubleClickHandler;

	JXTable table;

	JButton buttonAdd;

	JButton buttonRemove;

	JButton buttonSearch;

	JButton buttonCancel;

	JButton buttonEdit;

	JLabel labelHeading;

	JButton buttonExcel;

	AbstractViewHandler<T, E> viewHandler;

	private boolean useSearchButton;

	private boolean useNewButton;

	private JScrollPane scrollPaneTable;

	private JComponent component;

	/**
	 * @param handler
	 */
	public OverviewView(final AbstractViewHandler<T, E> handler) {
		this(handler, true);
	}

	/**
	 * @param handler
	 * @param searchButton
	 */
	public OverviewView(final AbstractViewHandler<T, E> handler,
			final boolean searchButton) {
		this(handler, searchButton, true);
	}

	/**
	 * @param handler
	 * @param searchButton
	 * @param newButton
	 */
	public OverviewView(final AbstractViewHandler<T, E> handler,
			final boolean searchButton, final boolean newButton) {
		useSearchButton = searchButton;
		useNewButton = newButton;
		viewHandler = handler;

	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#buildWindow()
	 */
	public final WindowInterface buildWindow() {
		mainWindow = InternalFrameBuilder.buildInternalFrame(viewHandler
				.getTitle(), viewHandler.getWindowSize(), true);

		component = buildPanel(mainWindow);
		mainWindow.add(component, BorderLayout.CENTER);

		return mainWindow;
	}

	/**
	 * Initierer komponenter. Kan ikke være final fordi denne overskrives i
	 * klasser som arver fra denne
	 * 
	 * @param window
	 */
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
		table = viewHandler.getTable(window);
		viewHandler.setColumnWidth(table);
		initEventHandling(window);
		updateActionEnablement();
	}

	/**
	 * Bygger panel Kan ikke være final
	 * 
	 * @param window
	 * @return panel
	 */
	public JComponent buildPanel(final WindowInterface window) {
		window.setName("Overview" + viewHandler.getClassName());
		initComponents(window);
		FormLayout layout = new FormLayout("15dlu,"
				+ viewHandler.getTableWidth() + ":grow,3dlu,p,15dlu",
				"10dlu,p,3dlu,10dlu,p,3dlu,p,3dlu,p,3dlu,p,"
						+ viewHandler.getTableHeight() + ":grow,p,5dlu,p,5dlu");
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		PanelBuilder builder = new PanelBuilder(layout);
		scrollPaneTable = new JScrollPane(table);
		scrollPaneTable.setBorder(Borders.EMPTY_BORDER);
		CellConstraints cc = new CellConstraints();

		builder.add(labelHeading, cc.xy(2, 2));
		builder.add(scrollPaneTable, cc.xywh(2, 4, 1, 9));
		// builder.add(table, cc.xywh(2, 4, 1, 9));

		builder.add(buildButtonPanel(), cc.xywh(4, 5, 1, 10));

		builder.add(ButtonBarFactory
				.buildCenteredBar(buttonExcel, buttonCancel), cc.xyw(2, 15, 3));

		return builder.getPanel();
	}

	/**
	 * Bygger knappepanel Kan ikke være final
	 * 
	 * @return panel
	 */
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
	 * Initierer hendelsehåndtering Kan ikke være final
	 * 
	 * @param window
	 */
	protected void initEventHandling(WindowInterface window) {
		viewHandler.setSelectionEmptyHandler(new SelectionEmptyHandler());
		doubleClickHandler = viewHandler.getDoubleClickHandler(window);
		table.addMouseListener(doubleClickHandler);
		viewHandler.addCloseListener(this);
	}

	/**
	 * Oppdaterer enable/disable av knapper Kan ikke være final
	 */
	protected void updateActionEnablement() {
		boolean hasSelection = viewHandler.objectSelectionListHasSelection();
		buttonEdit.setEnabled(hasSelection);
		if (viewHandler.hasWriteAccess()) {
			buttonRemove.setEnabled(hasSelection);
		}
	}

	/**
	 * Klasse som håndterer valg av objekt
	 * 
	 * @author atle.brekka
	 */
	protected class SelectionEmptyHandler implements PropertyChangeListener {

		/**
		 * @param evt
		 */
		public final void propertyChange(final PropertyChangeEvent evt) {
			updateActionEnablement();
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#getTitle()
	 */
	public final String getTitle() {
		return viewHandler.getTitle();
	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#initWindow()
	 */
	public final void initWindow() {
		viewHandler.init();

	}

	/**
	 * @see no.ugland.utransprod.gui.CloseListener#windowClosed()
	 */
	public void windowClosed() {

	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#cleanUp()
	 */
	public final void cleanUp() {

	}

	/**
	 * @see no.ugland.utransprod.gui.Viewer#useDispose()
	 */
	public final boolean useDispose() {
		return viewHandler.getDisposeOnClose();
	}
}
