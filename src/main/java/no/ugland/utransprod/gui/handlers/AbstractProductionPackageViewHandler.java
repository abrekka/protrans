package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.CloseListener;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.JFrameAdapter;
import no.ugland.utransprod.gui.JInternalFrameAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.SearchOrderView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.RefreshButton;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Accident;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Abstrakt klasse for produksjons- og pakkevunduer
 * 
 * @author atle.brekka
 * @param <T>
 */
public abstract class AbstractProductionPackageViewHandler<T extends Applyable>
		implements Closeable, Updateable, ProductAreaGroupProvider {
	final ArrayListModel objectList;

	final SelectionInList objectSelectionList;

	private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

	JXTable table;

	private JButton buttonApply;

	JButton buttonUnapply;

	JCheckBox checkBoxFilter;

	ApplyListInterface<T> applyListInterface;

	private String windowTitle;

	private boolean disposeOnClose = true;

	JPopupMenu popupMenu;

	private JMenuItem menuItemDeviation;
	private JMenuItem menuItemAccident;

	private JMenuItem menuItemComment;

	private List<ProductAreaGroup> allProductAreaGroupList;

	PresentationModel productAreaGroupModel;

	private JButton buttonStart;

	private JButton buttonNotStart;

	private TableEnum tableEnum;

	private DeviationViewHandlerFactory deviationViewHandlerFactory;

	protected Login login;
	protected ManagerRepository managerRepository;

	@Inject
	public AbstractProductionPackageViewHandler(final Login aLogin,
			ManagerRepository aManagerRepository,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory,
			@Assisted final ApplyListInterface<T> productionInterface,
			@Assisted final String title, @Assisted final TableEnum aTableEnum) {
		managerRepository = aManagerRepository;
		login = aLogin;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		tableEnum = aTableEnum;
		this.applyListInterface = productionInterface;
		this.windowTitle = title;
		objectList = new ArrayListModel();
		objectSelectionList = new SelectionInList((ListModel) objectList);
		objectSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_INDEX,
				new SelectionListener());

		popupMenu = new JPopupMenu();
		popupMenu.setName("PopupMenuProductionPackage");
		menuItemDeviation = new JMenuItem("Registrer avvik...");
		popupMenu.add(menuItemDeviation);

		menuItemComment = new JMenuItem("Legg til kommentar...");
		popupMenu.add(menuItemComment);

		menuItemAccident = new JMenuItem("Registrer hendelse...");
		menuItemAccident.setName("MenuItemAccident");
		popupMenu.add(menuItemAccident);
		initProductAreaGroup();
	}

	/**
	 * Initierer liste med produktområdegrupper
	 */
	private void initProductAreaGroup() {
		productAreaGroupModel = new PresentationModel(
				new ProductAreaGroupModel(ProductAreaGroup.UNKNOWN));
		productAreaGroupModel
				.addBeanPropertyChangeListener(new FilterPropertyChangeListener());
		allProductAreaGroupList = new ArrayList<ProductAreaGroup>();
		List<ProductAreaGroup> groups = managerRepository
				.getProductAreaGroupManager().findAll();
		if (groups != null) {
			allProductAreaGroupList.addAll(groups);
		}
	}

	/**
	 * Henter tekst som skal stå på tilordningsknapp
	 * 
	 * @return tekst
	 */
	protected abstract String getApplyText();

	/**
	 * Henter tekst som skal stå på knapp som fjerner tilordning
	 * 
	 * @return tekst
	 */
	protected abstract String getUnapplyText();

	protected abstract String getStartText();

	protected abstract String getNotStartText();

	/**
	 * Setter et objekt til tilordnet
	 * 
	 * @param object
	 * @param applied
	 * @param window
	 */
	protected abstract void setApplied(T object, boolean applied,
			WindowInterface window);

	protected abstract void setStarted(T object, boolean started);

	/**
	 * Henter tekst som skal stå på filter
	 * 
	 * @return tekst
	 */
	protected abstract String getCheckBoxText();

	/**
	 * Sjekker om tilordningsknapp skal være enablet
	 * 
	 * @param object
	 * @return true dersom enablet
	 */
	protected abstract boolean getButtonApplyEnabled(T object);

	protected abstract boolean getButtonStartEnabled(T object);

	/**
	 * Kjører lazy lasting dersom nødvendig
	 * 
	 * @param object
	 */
	protected abstract void checkLazyLoad(T object);

	/**
	 * Henter tabellmodell
	 * 
	 * @param window
	 * @return tabellmodell
	 */
	protected abstract TableModel getTableModel(WindowInterface window);

	/**
	 * Henter kolonne som viser tilordning
	 * 
	 * @return kolonne
	 */
	protected abstract Integer getApplyColumn();

	protected abstract Integer getStartColumn();

	/**
	 * Henter kolonne som inneholder produktområdegruppenavn
	 * 
	 * @return kolonne
	 */
	protected abstract int getProductAreaColumn();

	/**
	 * Søker etter ordre
	 * 
	 * @param orderNr
	 * @param customerNr
	 * @param window
	 */
	protected abstract void searchOrder(String orderNr, String customerNr,
			WindowInterface window);

	/**
	 * Henter render for ordrecelle
	 * 
	 * @return render
	 */
	protected abstract TableCellRenderer getOrderCellRenderer();

	/**
	 * Henter render for spesifikasjonscelle
	 * 
	 * @return render
	 */
	protected abstract TableCellRenderer getSpecificationCellRenderer();

	/**
	 * Henter kolonne for ordreinfo
	 * 
	 * @return kolonne
	 */
	protected abstract int getOrderInfoCell();

	/**
	 * Henter kolonne for spesifikasjon
	 * 
	 * @return kolonne
	 */
	protected abstract int getSpecificationCell();

	/**
	 * Henter rapportenum
	 * 
	 * @return rapportenum
	 */
	protected abstract ReportEnum getReportEnum();

	/**
	 * Sjekker om bruker har skriverettigheter
	 * 
	 * @return true dersom bruker har rettigheter
	 */
	protected abstract boolean hasWriteAccess();

	/**
	 * Setter kolonner som ikke skal være synlige
	 */
	protected abstract void setInvisibleCells();

	/**
	 * Finner objekt som skal setter basert på transporterbart objekt
	 * 
	 * @param transportable
	 * @return objekt som skal settes
	 */
	public abstract T getApplyObject(Transportable transportable,
			WindowInterface window);

	public abstract void clearApplyObject();

	/**
	 * Legger til lukkelytter
	 * 
	 * @param listener
	 */
	public final void addCloseListener(final CloseListener listener) {
		closeListeners.add(listener);
	}

	/**
	 * Sier i fra at vindu lukkes
	 */
	private void fireClose() {
		for (CloseListener listener : closeListeners) {
			listener.windowClosed();
		}
	}

	protected void addObjectInfo() {

	}

	/**
	 * Henter seleksjonsliste
	 * 
	 * @return seleksjonsliste
	 */
	protected final SelectionInList getObjectSelectionList() {
		objectList.clear();
		Collection<T> objectLines = applyListInterface.getObjectLines();
		if (objectLines != null) {
			objectList.addAll(objectLines);
		}
		if (table != null) {
			table.scrollRowToVisible(0);
		}
		addObjectInfo();
		return objectSelectionList;
	}

	/**
	 * Lager knapp for tilordning
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonApply(final WindowInterface window) {
		buttonApply = new JButton(new ApplyAction(getApplyText(), true, window));
		buttonApply.setEnabled(false);
		buttonApply.setName("ButtonApply");

		return buttonApply;
	}

	public final JButton getButtonStart() {
		String startText = getStartText();
		if (startText != null) {
			buttonStart = new JButton(new StartAction(startText, true));
			buttonStart.setEnabled(false);
			buttonStart.setName("ButtonStart");
		}
		return buttonStart;
	}

	/**
	 * Lager knapp for å fjerne tilordning
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonUnapply(final WindowInterface window) {
		buttonUnapply = new JButton(new ApplyAction(getUnapplyText(), false,
				window));
		buttonUnapply.setEnabled(false);
		buttonUnapply.setName("ButtonUnapply");
		return buttonUnapply;
	}

	public final JButton getButtonNotStart() {
		String notStartText = getNotStartText();
		if (notStartText != null) {
			buttonNotStart = new JButton(new StartAction(notStartText, false));
			buttonNotStart.setEnabled(false);
			buttonNotStart.setName("ButtonNotStart");
		}
		return buttonNotStart;
	}

	/**
	 * Lager oppdateringsknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonRefresh(final WindowInterface window) {
		return new RefreshButton(this, window);
	}

	/**
	 * Lager avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonCancel(final WindowInterface window) {
		return new CancelButton(window, this, disposeOnClose);
	}

	/**
	 * Lager tabell
	 * 
	 * @param window
	 * @return tabell
	 */
	public final JXTable getTable(final WindowInterface window) {
		menuItemDeviation.addActionListener(new MenuItemListener(window));
		menuItemComment.addActionListener(new MenuItemCommentListener(window));
		menuItemAccident.addActionListener(new AddAccidentAction(window));
		table = new JXTable();

		table.setModel(getTableModel(window));
		table.setName(tableEnum.getTableName());
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setSelectionModel(new SingleListSelectionAdapter(
				objectSelectionList.getSelectionIndexHolder()));
		table.setColumnControlVisible(true);
		table.setColumnControl(new UBColumnControlPopup(table, this));
		table.setSortable(true);

		table.setRowHeight(40);
		table.setShowGrid(true);
		table.setShowVerticalLines(true);
		table.setRolloverEnabled(false);

		table.getColumnModel().getColumn(getOrderInfoCell()).setCellRenderer(
				getOrderCellRenderer());
		int specCell = getSpecificationCell();
		if (specCell != -1) {
			table.getColumnModel().getColumn(specCell).setCellRenderer(
					getSpecificationCellRenderer());
		}
		table.getColumnExt(getProductAreaColumn()).setVisible(false);

		setInvisibleCells();

		Integer startColumn = getStartColumn();

		setFirstHighlighters();

		if (startColumn != null) {
			ColorHighlighter patternStart = new ColorHighlighter(
					new PatternPredicate("[^---]", startColumn),
					ColorEnum.YELLOW.getColor(), Color.BLACK);
			table.addHighlighter(patternStart);
		}

		ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate(
				"[^---]", getApplyColumn()), ColorEnum.GREEN.getColor(),
				Color.BLACK);
		table.addHighlighter(pattern);
		table.addMouseListener(new RightClickListener());
		setAdditionHighlighters();
		handleFilter();
		initColumnWidth();
		return table;
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean canClose(final String actionString,
			final WindowInterface window) {
		savePrefs();
		fireClose();

		return true;
	}

	/**
	 * Setter objekt tilordnet
	 * 
	 * @param applied
	 * @param window
	 */
	final void setApplied(final boolean applied, final WindowInterface window) {
		T object = getSelectedObject();

		setApplied(object, applied, window);
		objectSelectionList.clearSelection();

		initColumnWidth();
		table.updateUI();
	}

	final void setStarted(final boolean started) {
		T object = getSelectedObject();

		setStarted(object, started);
		objectSelectionList.clearSelection();

		initColumnWidth();
		table.updateUI();
	}

	@SuppressWarnings("unchecked")
	public T getSelectedObject() {
		int index = table.convertRowIndexToModel(objectSelectionList
				.getSelectionIndex());

		T object = (T) objectSelectionList.getElementAt(index);
		return object;
	}

	void initColumnWidthExt() {
	}

	/**
	 * Initierer kolonnebredder
	 */
	void initColumnWidth() {
		// Transportdato
		table.getColumnExt(0).setPreferredWidth(100);
		// Ordre
		table.getColumnExt(1).setPreferredWidth(200);

		// Antall
		table.getColumnExt(2).setPreferredWidth(50);

		// Spesifikasjon
		table.getColumnExt(3).setPreferredWidth(400);
		initColumnWidthExt();
	}

	/**
	 * Tilordning
	 * 
	 * @author atle.brekka
	 */
	private class ApplyAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private boolean isApplied = true;

		private WindowInterface window;

		/**
		 * @param buttonText
		 * @param applied
		 * @param aWindow
		 */
		public ApplyAction(final String buttonText, final boolean applied,
				final WindowInterface aWindow) {
			super(buttonText);
			window = aWindow;
			isApplied = applied;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			setApplied(isApplied, window);

		}
	}

	private class StartAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private boolean isStarted = true;

		public StartAction(final String buttonText, final boolean started) {
			super(buttonText);
			isStarted = started;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			setStarted(isStarted);

		}
	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final boolean doDelete(final WindowInterface window) {
		return true;

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final void doNew(final WindowInterface window) {

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final void doRefresh(final WindowInterface window) {
		objectSelectionList.clearSelection();
		getObjectSelectionList();

	}

	/**
	 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
	 */
	public final void doSave(final WindowInterface window) {

	}

	protected void buttonsEnabled(final T object, final boolean hasSelection) {

	}

	/**
	 * Enabler/disabler knapper
	 */
	@SuppressWarnings("unchecked")
	final void enableComponents() {
		buttonApply.setEnabled(false);
		buttonUnapply.setEnabled(false);
		if (buttonStart != null) {
			buttonStart.setEnabled(false);
			buttonNotStart.setEnabled(false);
		}
		boolean hasSelection = false;
		T object = null;
		if (hasWriteAccess()) {
			hasSelection = objectSelectionList.hasSelection();

			if (hasSelection) {
				int selectionIndex = objectSelectionList.getSelectionIndex();
				if (selectionIndex < table.getRowCount()) {
					object = (T) objectSelectionList.getElementAt(table
							.convertRowIndexToModel(selectionIndex));

					if (object != null) {
						boolean applied = getButtonApplyEnabled(object);
						boolean started = getButtonStartEnabled(object);
						buttonApply.setEnabled(applied);
						buttonUnapply.setEnabled(!applied);

						if (buttonStart != null && applied) {
							buttonStart.setEnabled(started);
							buttonNotStart.setEnabled(!started);
						}
					}
				}
			}
		}
		buttonsEnabled(object, hasSelection);
	}

	/**
	 * Lager sjekkboks for filter
	 * 
	 * @param window
	 * @return sjekkboks
	 */
	public final JCheckBox getCheckBoxFilter(final WindowInterface window) {
		checkBoxFilter = new JCheckBox(getCheckBoxText());
		checkBoxFilter.setSelected(true);
		checkBoxFilter.setName("CheckBoxFilter");
		checkBoxFilter.addActionListener(new FilterActionListener());
		return checkBoxFilter;
	}

	/**
	 * Lytter på seleksjon
	 * 
	 * @author atle.brekka
	 */
	class SelectionListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			enableComponents();

		}

	}

	/**
	 * Ta bort filter
	 */
	final void clearFilter() {
		checkBoxFilter.setSelected(true);
		table.setFilters(null);
		table.repaint();
	}

	/**
	 * Håndterer filter
	 * 
	 * @author atle.brekka
	 */
	class FilterActionListener implements ActionListener {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			handleFilter();

		}

	}

	void handleFilterExt() {
	}

	void setAdditionFilters(List<Filter> filters) {

	}

	void setFirstHighlighters() {

	}

	void setAdditionHighlighters() {

	}

	/**
	 * Håndterer filtrering
	 */
	protected void handleFilter() {

		table.clearSelection();
		objectSelectionList.clearSelection();

		ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
		PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(), table
				.getName(), table);
		if (group.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
			group = null;
		}
		List<Filter> filterList = new ArrayList<Filter>();

		if (!checkBoxFilter.isSelected()) {
			Filter filterApplied = new PatternFilter("---",
					Pattern.CASE_INSENSITIVE, getApplyColumn());
			filterList.add(filterApplied);
		}
		if (group != null) {
			PatternFilter filterProductAreaGroup = new PatternFilter(group
					.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE,
					getProductAreaColumn());
			filterList.add(filterProductAreaGroup);
		}
		setAdditionFilters(filterList);
		if (filterList.size() != 0) {
			Filter[] filterArray = new Filter[filterList.size()];
			FilterPipeline filterPipeline = new FilterPipeline(filterList
					.toArray(filterArray));
			table.setFilters(filterPipeline);
		} else {
			table.setFilters(null);
		}
		table.repaint();
		handleFilterExt();
	}

	/**
	 * Henter vindusstørrelse
	 * 
	 * @return størrelse
	 */
	public abstract Dimension getWindowSize();

	/**
	 * Sjekker om bruker er administator
	 * 
	 * @return true derosm administrator
	 */
	public final boolean isUserAdministrator() {
		return Util.convertNumberToBoolean(login.getUserType().getIsAdmin());
	}

	/**
	 * Henter tittel
	 * 
	 * @return tittel
	 */
	public final String getWindowTitle() {
		return windowTitle;
	}

	/**
	 * Henter tabellbredde
	 * 
	 * @return tabellbredde
	 */
	public abstract String getTableWidth();

	/**
	 * Lager søkeknapp
	 * 
	 * @param window
	 * @return kapp
	 */
	public final JButton getButtonSearch(final WindowInterface window) {
		JButton button = new JButton(new SearchAction(window));
		button.setName("ButtonSearch");
		return button;
	}

	/**
	 * Lager knapp for registrering av avvik
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonDeviation(final WindowInterface window) {
		return new JButton(new DeviationAction<T>(deviationViewHandlerFactory));
	}

	public final JButton getButtonAddAccident(final WindowInterface window) {
		JButton button = new JButton(new AddAccidentAction(window));
		button.setName("ButtonAddAccident");
		return button;
	}

	/**
	 * Lager komboboks for produktområdegruppe
	 * 
	 * @return komboboks
	 */
	public final JComboBox getComboBoxProductAreaGroup() {
		return Util.getComboBoxProductAreaGroup(login.getApplicationUser(),
				login.getUserType(), productAreaGroupModel);
	}

	/**
	 * Lager utskriftsknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public final JButton getButtonPrint(final WindowInterface window) {
		return new JButton(new PrintAction(window));
	}

	/**
	 * Søk
	 * 
	 * @author atle.brekka
	 */
	private class SearchAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public SearchAction(final WindowInterface aWindow) {
			super("Søk...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			objectSelectionList.clearSelection();

			SearchOrderView searchOrderView = new SearchOrderView(true);
			JDialog dialog = Util.getDialog(window, "Søk", true);
			WindowInterface dialogWindow = new JDialogAdapter(dialog);
			dialog.add(searchOrderView.buildPanel(dialogWindow));
			dialog.pack();
			Util.locateOnScreenCenter(dialogWindow);
			dialogWindow.setVisible(true);

			String criteria = searchOrderView.getCriteria();
			Boolean useOrderNr = searchOrderView.useOrderNr();
			clearFilter();
			handleFilter();
			if (criteria != null) {
				if (useOrderNr) {
					searchOrder(criteria, null, window);
				} else {
					searchOrder(null, criteria, window);
				}
			}

			Util.setDefaultCursor(window.getComponent());
		}
	}

	private class AddAccidentAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public AddAccidentAction(final WindowInterface aWindow) {
			super("Reg. hendelse...");
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			AccidentViewHandler accidentViewHandler = new AccidentViewHandler(
					login, managerRepository);
			accidentViewHandler.openEditView(new Accident(), false, window);
			Util.setDefaultCursor(window.getComponent());
		}
	}

	/**
	 * Henter tabellmodell for rapport
	 * 
	 * @return tabelmodell
	 */
	abstract TableModel getTableModelReport();

	/**
	 * Utskrift
	 * 
	 * @author atle.brekka
	 */
	private class PrintAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public PrintAction(final WindowInterface aWindow) {
			super("Skriv ut...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			boolean useDialog = false;
			if (window.getComponent() instanceof JDialog) {
				useDialog = true;
			}
			Util.runInThreadWheel(window.getRootPane(), new Printer(window,
					useDialog), null);

		}
	}

	/**
	 * Utskrift
	 * 
	 * @author atle.brekka
	 */
	private class Printer implements Threadable {
		private WindowInterface owner;

		/**
		 * @param aOwner
		 * @param useDialog
		 */
		public Printer(final WindowInterface aOwner, final boolean useDialog) {
			owner = aOwner;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(final Object object) {
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(final Object[] params, final JLabel labelInfo) {
			labelInfo.setText("Genererer utskrift...");
			ReportViewer reportViewer = new ReportViewer(getWindowTitle());
			WindowInterface window;
			if (owner instanceof JDialogAdapter) {
				window = new JDialogAdapter(new JDialog((JDialog) owner
						.getComponent(), getWindowTitle()));
			} else if (owner instanceof JFrameAdapter) {
				window = new JDialogAdapter(new JDialog((JFrame) owner
						.getComponent(), getWindowTitle()));
			} else {
				window = new JInternalFrameAdapter(new JInternalFrame(
						getWindowTitle(), true, true, true, true));
				ProTransMain.PRO_TRANS_MAIN.addInternalFrame(window);
			}

			window.add(reportViewer.buildPanel(window));

			try {
				reportViewer.generateProtransReport(getTableModelReport(),
						getWindowTitle(), getReportEnum(), null);
				window.pack();
				Util.locateOnScreenCenter(window);
				window.setVisible(true);
			} catch (ProTransException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(final boolean enable) {
		}

	}

	/**
	 * Velger objekt dersom flere tilfrdsstilte søk
	 * 
	 * @param window
	 * @param objects
	 * @return valgt objekt
	 */
	@SuppressWarnings("unchecked")
	final T getSearchObject(final WindowInterface window, final List<T> objects) {
		T object = null;
		if (objects != null && objects.size() != 0) {

			if (objects.size() > 1) {
				object = (T) JOptionPane.showInputDialog(window.getComponent(),
						"Velg ordre", "Velg ordre",
						JOptionPane.QUESTION_MESSAGE, null, objects.toArray(),
						null);
			} else {
				object = objects.get(0);
			}

		} else {
			JOptionPane.showMessageDialog(window.getComponent(),
					"Ordre ble ikke funnet");
		}
		return object;
	}

	/**
	 * Init
	 */
	public final void init() {
		getObjectSelectionList();
	}

	/**
	 * Henter ut om vindu skal bruke dispose
	 * 
	 * @return true derosm dispose
	 */
	public final boolean getDisposeOnClose() {
		return disposeOnClose;
	}

	/**
	 * Håndterer popupmeny
	 * 
	 * @author atle.brekka
	 */
	class MenuItemListener implements ActionListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemListener(final WindowInterface aWindow) {
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			Applyable applyable = (Applyable) objectSelectionList
					.getElementAt(table
							.convertRowIndexToModel(objectSelectionList
									.getSelectionIndex()));
			OrderManager orderManager = (OrderManager) ModelUtil
					.getBean("orderManager");
			Order order = orderManager.findByOrderNr(applyable.getOrderNr());
			if (order != null) {
				DeviationViewHandler deviationViewHandler = deviationViewHandlerFactory
						.create(order, true, false, true, null, false);
				deviationViewHandler.registerDeviation(order, window);
			}
		}

	}

	/**
	 * Håndterer innlegging av kommentar
	 * 
	 * @author atle.brekka
	 */
	class MenuItemCommentListener implements ActionListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MenuItemCommentListener(final WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(final ActionEvent arg0) {
			Applyable applyable = (Applyable) objectSelectionList
					.getElementAt(table
							.convertRowIndexToModel(objectSelectionList
									.getSelectionIndex()));

			Order order = managerRepository.getOrderManager().findByOrderNr(
					applyable.getOrderNr());
			if (order != null) {
				OrderComment orderComment = new OrderComment();
				orderComment.setUserName(login.getApplicationUser()
						.getUserName());
				orderComment.setCommentDate(Util.getCurrentDate());
				orderComment.addCommentType(CommentTypeUtil
						.getCommentType("Ordre"));

				CommentViewHandler orderCommentViewHandler = new CommentViewHandler(
						login, managerRepository.getOrderManager());
				EditCommentView editDeviationCommentView = new EditCommentView(
						new OrderCommentModel(orderComment),
						orderCommentViewHandler);

				JDialog dialog = Util.getDialog(window, "Legg til kommentar",
						true);
				dialog.setName("EditDeviationCommentView");
				WindowInterface dialogWindow = new JDialogAdapter(dialog);
				dialogWindow.add(editDeviationCommentView
						.buildPanel(dialogWindow));
				dialog.pack();
				Util.locateOnScreenCenter(dialog);
				dialogWindow.setVisible(true);

				if (!orderCommentViewHandler.isCanceled()) {
					orderComment.setOrder(order);
					managerRepository
							.getOrderManager()
							.lazyLoadOrder(
									order,
									new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COMMENTS });
					order.addOrderComment(orderComment);
					try {
						managerRepository.getOrderManager().saveOrder(order);
					} catch (ProTransException e) {
						Util.showErrorDialog(window, "Feil", e.getMessage());
						e.printStackTrace();
					}
					applyListInterface.refresh((T) applyable);
				}
			}

		}

	}

	/**
	 * Hådterer høyreklikk i tabell
	 * 
	 * @author atle.brekka
	 */
	final class RightClickListener extends MouseAdapter {
		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(final MouseEvent e) {

			if (SwingUtilities.isRightMouseButton(e)) {
				popupMenu.show((JXTable) e.getSource(), e.getX(), e.getY());
			}
		}
	}

	/**
	 * Håndterer filtrering
	 * 
	 * @author atle.brekka
	 */
	class FilterPropertyChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			handleFilter();

		}

	}

	public final String getProductAreaGroupName() {
		return ((ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP))
				.getProductAreaGroupName();
	}

	public final ProductAreaGroup getProductAreaGroup() {
		return (ProductAreaGroup) productAreaGroupModel
				.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	}

	public final void savePrefs() {
		PrefsUtil
				.putUserInvisibleColumns(
						table,
						(ProductAreaGroup) productAreaGroupModel
								.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	}
}
