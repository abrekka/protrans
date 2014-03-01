package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.CloseListener;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.CancelListener;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.FlushListener;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.HighlighterFactory;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Abstrakt klasse som brukes for de klasser som håndterer vindusvariable
 * 
 * @author atle.brekka
 * @param <T>
 * @param <E>
 *            objekt som ligger i liste
 */
public abstract class AbstractViewHandler<T, E> extends Model implements Updateable, Closeable {
    private static final long serialVersionUID = 1L;

    ViewHandlerExt<T, E> viewHandlerExt;

    private JButton buttonSearch;

    JButton buttonEdit;

    private JButton buttonCancel;

    JButton buttonDelete;

    JLabel labelHeading;

    private FilteredChangeListener filteredChangeListener;

    /**
     * Property for filtrering
     */
    public static final String PROPERTY_FILTERED = "filtered";

    /**
     * Vindustittel
     */
    private String heading;

    /**
     * Seleksjonsliste med alle objekter
     */
    SelectionInList objectSelectionList;

    /**
     * True dersom liste er filtrert
     */
    private boolean filtered = false;

    /**
     * Liste med alle objekter
     */
    final ArrayListModel objectList;

    /**
     * Manager mot gjeldende tabell
     */
    protected OverviewManager<T> overviewManager;

    /**
     * Antall objekter totalt
     */
    int noOfObjects;

    T currentObject;

    /**
     * Holder rede på om modell blir flushet
     */
    boolean flushing = false;

    List<FlushListener> flushListeners = new ArrayList<FlushListener>();

    JXTable table;

    private List<CloseListener> closeListeners = new ArrayList<CloseListener>();

    private TableModel tableModel;

    boolean loaded = false;

    UserType userType;

    private boolean disposeOnClose;

    List<Component> components = new ArrayList<Component>();

    WindowInterface window;

    private JButton buttonExcel;

    /**
     * Konstruktør
     * 
     * @param aHeading
     *            tittel
     * @param managerName
     * @param aUserType
     * @param useDisposeOnClose
     */
    public AbstractViewHandler(final String aHeading, final OverviewManager<T> aOverviewManager, final UserType aUserType,
	    final boolean useDisposeOnClose) {
	this(aHeading, aOverviewManager, false, aUserType, useDisposeOnClose);
    }

    /**
     * @param aHeading
     * @param managerName
     * @param oneObject
     * @param aUserType
     * @param useDisposeOnClose
     */
    public AbstractViewHandler(final String aHeading, final OverviewManager<T> aOverviewManager, final boolean oneObject, final UserType aUserType,
	    final boolean useDisposeOnClose) {
	disposeOnClose = useDisposeOnClose;

	userType = aUserType;
	heading = aHeading;
	overviewManager = aOverviewManager;
	objectList = new ArrayListModel();
	objectSelectionList = new SelectionInList((ListModel) objectList);
    }

    public final void setSelectionEmptyHandler(final PropertyChangeListener handler) {
	if (objectSelectionList != null) {
	    objectSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION, handler);
	}
    }

    public final void setWindowInterface(final WindowInterface aWindow) {
	window = aWindow;
    }

    /**
     * Legger til flushlytter
     * 
     * @param listener
     */
    public final void addFlushListener(final FlushListener listener) {
	flushListeners.add(listener);
    }

    /**
     * Forteller til lyttere at det flushes
     * 
     * @param flush
     */
    private void fireFlushing(final boolean flush) {
	for (FlushListener listener : flushListeners) {
	    listener.flushChanged(flush);
	}
    }

    /**
     * Setter true dersom modell blir flushet
     * 
     * @param isFlushing
     */
    public final void setFlushing(final boolean isFlushing) {
	this.flushing = isFlushing;
	fireFlushing(isFlushing);
	setFlushingExt(isFlushing);
    }

    public void setFlushingExt(final boolean isFlushing) {
    }

    /**
     * Klassenavn
     * 
     * @return klassenavn
     */
    public abstract String getClassName();

    /**
     * Henter streng som skal være på add- og removeknapp
     * 
     * @return streng
     */
    public abstract String getAddRemoveString();

    /**
     * Sjekker om bruker har rettigheter
     * 
     * @return true derosm bruker har rettigheter
     */
    public abstract Boolean hasWriteAccess();

    /**
     * Henter streng som skal være på legg til knapp, dersom denne er null vil
     * streng fra getAddRemoveString bli brukt
     * 
     * @return strenk på legg til knapp
     */
    abstract String getAddString();

    /**
     * Henter view for editering
     * 
     * @param handler
     * @param object
     * @param searching
     * @return editeringsview
     */
    protected abstract AbstractEditView<E, T> getEditView(AbstractViewHandler<T, E> handler, T object, boolean searching);

    /**
     * Åpner editeringsvindu
     * 
     * @param object
     *            objekt som skal editeres
     * @param searching
     *            true dersom det skal søkes
     * @param parentWindow
     */
    protected final boolean openEditView(final T object, final boolean searching, final WindowInterface parentWindow) {
	boolean hasOpenViewExt = false;
	if (viewHandlerExt != null) {
	    hasOpenViewExt = viewHandlerExt.openEditViewExt(object, searching, parentWindow);
	}

	if (!hasOpenViewExt) {
	    AbstractEditView<E, T> view = getEditView(this, object, searching);
	    if (view != null) {
		WindowInterface dialog = new JDialogAdapter(Util.getDialog(parentWindow, getTitle(), true));
		dialog.setName("Edit" + getClassName() + "View");
		dialog.add(view.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		if (searching && !view.isCanceled()) {
		    updateViewList(object, parentWindow);
		}
	    }
	    return !view.isCanceled();
	}
	return true;
    }

    /**
     * Henter nytt objekt
     * 
     * @return objekt
     */
    public abstract T getNewObject();

    /**
     * Henter modell for tabell
     * 
     * @param window1
     * @return tabellmodell
     */
    public abstract TableModel getTableModel(final WindowInterface window1);

    abstract void afterSaveObject(T object, WindowInterface window);

    /**
     * Lagrer objekt
     * 
     * @param objectModel
     * @param window
     */
    public final void saveObject(AbstractModel<T, E> objectModel, WindowInterface window) {
	boolean hasSaveObjectExt = false;
	if (viewHandlerExt != null) {
	    hasSaveObjectExt = viewHandlerExt.saveObjectExt(objectModel, window);
	}
	if (!hasSaveObjectExt) {
	    T object = objectModel.getObject();
	    int index = objectList.indexOf(object);

	    try {
		overviewManager.saveObject(object);
	    } catch (ProTransException e) {
		Util.showErrorDialog(window, "Feil", e.getMessage());
		e.printStackTrace();
	    }

	    if (index < 0) {
		objectList.add(object);
		noOfObjects++;
	    } else {
		objectSelectionList.fireContentsChanged(index, index);
	    }
	    afterSaveObject(object, window);
	}

    }

    /**
     * Henter tittel
     * 
     * @return tittel
     */
    public abstract String getTitle();

    /**
     * Henter vindusstørrelse
     * 
     * @return vindusstørrelse
     */
    public abstract Dimension getWindowSize();

    /**
     * Henter størrelse for tabell
     * 
     * @return tabellstørrelse
     */
    public abstract String getTableWidth();

    /**
     * Henter tabellhøyde
     * 
     * @return tabellhøyde
     */
    public String getTableHeight() {
	return "80dlu";
    }

    /**
     * Setter kolonnestørrelse for tabell
     * 
     * @param table
     *            tabell det skal settes kolonnebredde for
     */
    public abstract void setColumnWidth(JXTable table);

    /**
     * Sjekker om objekt er gyldig
     * 
     * @param object
     * @param presentationModel
     * @param window
     * @return feiltekst dersom objekt ikke er gyldig
     */
    public abstract CheckObject checkSaveObject(E object, PresentationModel presentationModel, WindowInterface window);

    /**
     * Sjekker objekt før det slettes
     * 
     * @param object
     * @return feilstreng dersom er feil
     */
    public abstract CheckObject checkDeleteObject(T object);

    public void beforeClose() {

    }

    /**
     * Henter overskrift
     * 
     * @return oversikrift
     */
    public String getHeading() {
	return heading;
    }

    /**
     * Lager label for overskrift
     * 
     * @return label
     */
    public JLabel getLabelHeading() {
	filteredChangeListener = new FilteredChangeListener();
	addPropertyChangeListener(AbstractViewHandler.PROPERTY_FILTERED, filteredChangeListener);
	labelHeading = new JLabel(getHeading());
	return labelHeading;
    }

    /**
     * Lager tabell
     * 
     * @param window
     * @return tabell
     */
    public JXTable getTable(WindowInterface window) {
	table = new JXTable();
	table.setName("Table" + getClassName());

	initObjects();

	tableModel = getTableModel(window);
	table.setModel(tableModel);

	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setSelectionModel(new SingleListSelectionAdapter(objectSelectionList.getSelectionIndexHolder()));
	// getObjectSelectionList().getSelectionIndexHolder()));
	table.setColumnControlVisible(true);
	table.setSearchable(null);
	table.setRowMargin(0);
	table.addHighlighter(HighlighterFactory.createAlternateStriping());
	// table.setHighlighters(new HighlighterPipeline(new Highlighter[] {new
	// AlternateRowHighlighter()}));
	components.add(table);

	return table;
    }

    public boolean objectSelectionListHasSelection() {
	if (objectSelectionList != null) {
	    return objectSelectionList.hasSelection();
	}
	return false;
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
     */
    @SuppressWarnings("unchecked")
    public boolean doDelete(WindowInterface window) {
	boolean returnValue = true;
	int selectedIndex = getSelectedIndex();

	if (selectedIndex != -1) {
	    T object = (T) objectSelectionList.getElementAt(selectedIndex);
	    CheckObject checkObject = checkDeleteObject(object);
	    String msg = null;
	    if (checkObject != null) {
		msg = checkObject.getMsg();
	    }
	    if (msg == null) {
		doDeleteObject(selectedIndex, object);
	    } else {
		returnValue = handleDeleteCheckObject(window, returnValue, selectedIndex, object, checkObject, msg);

	    }

	}
	return returnValue;
    }

    protected boolean handleDeleteCheckObject(WindowInterface window, boolean returnValue, int selectedIndex, T object, CheckObject checkObject,
	    String msg) {
	if (checkObject.canContinue()) {
	    returnValue = handleCanContinueDelete(window, returnValue, selectedIndex, object, msg);
	} else {
	    returnValue = false;
	    Util.showErrorDialog((Component) null, "Feil", msg);
	}
	return returnValue;
    }

    private boolean handleCanContinueDelete(WindowInterface window, boolean returnValue, int selectedIndex, T object, String msg) {
	boolean doDelete = Util.showConfirmDialog(window.getComponent(), "Slette?", msg + " Vil du slette?");
	if (doDelete) {
	    doDeleteObject(selectedIndex, object);
	} else {
	    returnValue = false;
	}
	return returnValue;
    }

    protected final boolean handleSaveCheckObject(WindowInterface window, CheckObject checkObject) {
	boolean returnValue;
	if (checkObject.canContinue()) {
	    returnValue = handleCanContinueSave(window, checkObject.getMsg());
	} else {
	    returnValue = false;
	    Util.showErrorDialog((Component) null, "Feil", checkObject.getMsg());
	}
	return returnValue;
    }

    protected final boolean handleCanContinueSave(WindowInterface window, String msg) {
	boolean doSave = Util.showConfirmDialog(window.getComponent(), "Lagre?", msg + " Vil du fortsette?");
	return doSave;
    }

    protected void doDeleteObject(int selectedIndex, T object) {
	objectList.remove(selectedIndex);
	overviewManager.removeObject(object);
	noOfObjects--;
    }

    protected int getSelectedIndex() {
	int selectedIndex = objectSelectionList.getSelectionIndex();
	if (table != null) {
	    selectedIndex = table.convertRowIndexToModel(selectedIndex);
	} else {
	    selectedIndex = -1;
	}
	return selectedIndex;
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doSave(WindowInterface window) {

    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doNew(WindowInterface window) {
	currentObject = getNewObject();
	openEditView(currentObject, false, window);
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
	beforeClose();
	fireClose();

	return true;
    }

    /**
     * Rydder opp
     */
    public void cleanUp() {
    }

    /**
     * Henter seleksjonsliste med alle objekter
     * 
     * @param comparator
     * @return seleksjonsliste
     */
    @SuppressWarnings("unchecked")
    public SelectionInList getObjectSelectionList(Comparator<T> comparator) {
	initObjects();
	if (comparator != null) {
	    Collections.sort(objectList, comparator);
	}
	return objectSelectionList;
    }

    /**
     * @return objektliste
     */
    /*
     * public SelectionInList getObjectSelectionList() { return
     * objectSelectionList; }
     */
    public int getObjectSelectionListSize() {
	if (objectSelectionList != null) {
	    return objectSelectionList.getSize();
	}
	return 0;
    }

    public Object getTableSelection() {
	if (objectSelectionList.hasSelection()) {
	    return objectSelectionList.getElementAt(table.convertRowIndexToModel(objectSelectionList.getSelectionIndex()));
	}
	return null;
    }

    /**
     * Henter knapp for å legge til
     * 
     * @param window
     *            vindu som skal vise knapp
     * @return knapp
     */
    public JButton getAddButton(WindowInterface window) {
	String addString;
	JButton button;

	addString = getAddString();
	if (addString != null) {
	    button = new NewButton(getAddRemoveString(), this, window, addString);
	} else {
	    button = new NewButton(getAddRemoveString(), this, window);
	}

	button.setName("Add" + getClassName());
	button.setEnabled(hasWriteAccess());
	components.add(button);
	return button;
    }

    /**
     * Henter sletteknapp
     * 
     * @param window
     *            vindu som skal vise sletteknapp
     * @return knapp
     */
    public JButton getRemoveButton(WindowInterface window) {
	buttonDelete = new DeleteButton(getAddRemoveString(), this, window);
	buttonDelete.setName("Remove" + getClassName());
	buttonDelete.setEnabled(false);
	components.add(buttonDelete);
	return buttonDelete;
    }

    /**
     * Henter søkeknapp
     * 
     * @param window
     * @return knapp
     */
    public JButton getSearchButton(WindowInterface window) {
	buttonSearch = new JButton(new SearchAction(window));
	buttonSearch.setName("Search" + getClassName());
	components.add(buttonSearch);
	return buttonSearch;
    }

    /**
     * Legger på tilleggsinformasjon etter at alle objekter er hentet fra
     * database
     */
    protected void addObjectInfo() {

    }

    /**
     * Henter opp alle objekter fra manager
     */
    protected void initObjects() {
	if (!loaded) {
	    setFiltered(false);
	    objectSelectionList.clearSelection();
	    objectList.clear();
	    List<T> allObjects = overviewManager.findAll();
	    if (allObjects != null) {
		objectList.addAll(allObjects);
	    }
	    noOfObjects = objectList.getSize();
	    if (table != null) {
		table.scrollRowToVisible(0);
	    }
	    addObjectInfo();
	}
    }

    /**
     * Henter avbrytknapp
     * 
     * @param window
     *            vinsu som skal vise avbrytknapp
     * @param cancelListener
     * @return knapp
     */
    public JButton getCancelButton(WindowInterface window, CancelListener cancelListener) {
	return new CancelButton(window, this, cancelListener, true);
    }

    /**
     * Lager avbrytknapp
     * 
     * @param window
     * @return knapp
     */
    public JButton getCancelButton(WindowInterface window) {
	buttonCancel = new CancelButton(window, this, disposeOnClose);
	buttonCancel.setName("ButtonCancel");
	return buttonCancel;
    }

    /**
     * Henter editeringsknapp
     * 
     * @param window
     * @return knapp
     */
    public JButton getEditButton(WindowInterface window) {
	// editAction = new EditAction(window);
	buttonEdit = new JButton(new EditAction(window));
	buttonEdit.setName("Edit" + getClassName());
	return buttonEdit;
    }

    /**
     * Lager excel-knapp
     * 
     * @param window
     * @return knapp
     */
    public JButton getExcelButton(WindowInterface window) {
	buttonExcel = new JButton(new ExcelAction(window));
	buttonExcel.setIcon(IconEnum.ICON_EXCEL.getIcon());
	buttonExcel.setName("ButtonExcel");
	return buttonExcel;
    }

    /**
     * Henter klasse for håndtering av dobelltklikk
     * 
     * @param window
     * @return klasse for dobbeltklikk
     */
    public MouseListener getDoubleClickHandler(WindowInterface window) {
	return new DoubleClickHandler(window);
    }

    /**
     * Oppdaterer liste for objekter som vises i vindu
     * 
     * @param object
     */
    protected void updateViewList(final T object, final WindowInterface aWindow) {
	/*
	 * Util.runInThreadWheel(aWindow.getRootPane(), new Threadable() {
	 * public void enableComponents(boolean enable) { // TODO Auto-generated
	 * method stub } public Object doWork(Object[] params, JLabel labelInfo)
	 * { labelInfo.setText("Oppdaterer vindu...");
	 */
	objectSelectionList.clearSelection();
	objectList.clear();
	List<?> objects = overviewManager.findByObject(object);
	if (objects.size() != noOfObjects) {
	    setFiltered(true);
	} else {
	    setFiltered(false);
	}

	objectList.addAll(objects);
	/*
	 * return null; } public void doWhenFinished(Object object) { // TODO
	 * Auto-generated method stub } }, null);
	 */

    }

    /**
     * Sjekker om liste er filtrert
     * 
     * @return true derosm filtrert
     */
    public boolean isFiltered() {
	return filtered;
    }

    /**
     * Setter at liste er filtrert
     * 
     * @param filtered
     */
    protected void setFiltered(boolean filtered) {
	boolean oldFilter = isFiltered();
	this.filtered = filtered;
	firePropertyChange(PROPERTY_FILTERED, oldFilter, filtered);
    }

    /**
     * Klassesom håndterer søking
     * 
     * @author atle.brekka
     */
    private class SearchAction extends AbstractAction {
	/**
         *
         */
	private static final long serialVersionUID = 1L;

	/**
         *
         */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public SearchAction(WindowInterface aWindow) {
	    super("Søk...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {

	    openEditView(getNewObject(), true, window);

	}
    }

    /**
     * Editering av valgt objekt
     * 
     * @param window
     */
    @SuppressWarnings("unchecked")
    void doEditAction(WindowInterface window) {
	Util.setWaitCursor(window.getComponent());
	int modelRow = objectSelectionList.getSelectionIndex();

	if (table != null) {
	    modelRow = table.convertRowIndexToModel(table.getSelectedRow());
	}
	currentObject = (T) objectSelectionList.getElementAt(modelRow);

	openEditView(currentObject, false, window);
	Util.setDefaultCursor(window.getComponent());
    }

    /**
     * Klassesom håndtrer editering
     * 
     * @author atle.brekka
     */
    private class EditAction extends AbstractAction {
	/**
         *
         */
	private WindowInterface window;

	/**
         *
         */
	private static final long serialVersionUID = 1L;

	/**
	 * @param aWindow
	 */
	public EditAction(WindowInterface aWindow) {
	    super("Editer...");
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    doEditAction(window);

	}
    }

    /**
     * Klassesom håndterer dobbeltklikk
     * 
     * @author atle.brekka
     */
    final class DoubleClickHandler extends MouseAdapter {
	/**
         *
         */
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public DoubleClickHandler(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	    if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
		if (objectSelectionList.getSelection() != null) {
		    doEditAction(window);
		}
	}
    }

    /**
     * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
     */
    public void doRefresh(WindowInterface window) {
    }

    /**
     * Oppdaterer object
     * 
     * @param object
     */
    public void refreshObject(E object) {

    }

    /**
     * Legger til lukkelytter
     * 
     * @param listener
     */
    public void addCloseListener(CloseListener listener) {
	closeListeners.add(listener);
    }

    /**
     * Fjerner lukkelytter
     * 
     * @param listener
     */
    public void removeCloseListener(CloseListener listener) {
	closeListeners.remove(listener);
    }

    /**
     * Sier i fra at vindu blir lukket
     */
    public void fireClose() {
	for (CloseListener listener : closeListeners) {
	    listener.windowClosed();
	}
    }

    /**
     * Initiering
     */
    public void init() {
	initObjects();
    }

    /**
     * Håndterer ensdring av filtrering
     * 
     * @author atle.brekka
     */
    class FilteredChangeListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent evt) {
	    boolean filtered1 = (Boolean) evt.getNewValue();
	    if (filtered1) {
		labelHeading.setIcon(IconEnum.ICON_FILTER.getIcon());
	    } else {
		labelHeading.setIcon(null);
	    }
	}

    }

    /**
     * Henter tabell som skal brukes for generering av excelfil
     * 
     * @return tabell
     */
    protected JXTable getExcelTable() {
	return table;
    }

    /**
     * Sjekker om vindu skal bruke dispose
     * 
     * @return true dersom dispose
     */
    public boolean getDisposeOnClose() {
	return disposeOnClose;
    }

    /**
     * Eksporter til excel
     * 
     * @throws ProTransException
     */
    void exportToExcel(WindowInterface window) throws ProTransException {
	String fileName = getClassName() + "_" + Util.getCurrentDateAsDateTimeString() + ".xls";
	String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

	ExcelUtil.showDataInExcel(excelDirectory, fileName, null, getTitle(), getExcelTable(), null, null, 16, false);
	// ExcelUtil.showDataInExcelInThread(window, fileName, getTitle(),
	// getExcelTable(), null, null, 16, false);
    }

    /**
     * Eksport til excel
     * 
     * @author atle.brekka
     */
    private class ExcelAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private WindowInterface window;

	public ExcelAction(WindowInterface aWindow) {
	    super("Excel");
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent arg0) {
	    Util.runInThreadWheel(window.getRootPane(), new Threadable() {

		public void enableComponents(boolean enable) {

		}

		public Object doWork(Object[] params, JLabel labelInfo) {
		    labelInfo.setText("Genererer excel...");
		    String errorMsg = null;
		    try {
			exportToExcel(window);
		    } catch (ProTransException e) {
			errorMsg = e.getMessage();
		    }
		    return errorMsg;
		}

		public void doWhenFinished(Object object) {
		    if (object != null) {
			Util.showErrorDialog(window, "Feil", object.toString());
		    } else {
			Util.showMsgFrame(window.getComponent(), "Excel generert",
				"Dersom excelfil ikke kom opp ligger den i katalog definert for excel");
		    }

		}

	    }, null);

	}
    }

    /**
     * Oppdaterer tilgang til knapper
     */
    protected void updateButtonEnablement() {
	boolean hasSelection = objectSelectionList.hasSelection();
	buttonDelete.setEnabled(hasSelection);
	buttonEdit.setEnabled(hasSelection);
    }

    /**
     * Henter bukertype
     * 
     * @return brukertype
     */
    public UserType getUserType() {
	return userType;
    }

    /**
     * Henter antall objekter som er lastet
     * 
     * @return antall objekter
     */
    public int getNoOfObjects() {
	return noOfObjects;
    }

}
