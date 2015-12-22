package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditColliView;
import no.ugland.utransprod.gui.model.ColliListener;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for editering av kollier
 * 
 * @author atle.brekka
 */
public class ColliViewHandler extends AbstractViewHandlerShort<Colli, ColliModel> implements ColliListener {
    private static final long serialVersionUID = 1L;

    private PresentationModel presentationModel;

    private SelectionInList orderLineSelectionList;

    private ArrayListModel orderLineList;

    JCheckBox checkBoxSelection;

    List<ColliListener> colliListeners = new ArrayList<ColliListener>();

    private List<PropertyChangeListener> bufferingListeners = new ArrayList<PropertyChangeListener>();

    Packable packable;

    private ColliOrderLineTableModel tableModel;

    JPopupMenu popupMenuRemoveOrderLine;

    JMenuItem menuItemRemoveOrderLine;

    private ManagerRepository managerRepository;
    private VismaFileCreator vismaFileCreator;

    /**
     * @param aHeading
     * @param aColli
     * @param aPackable
     * @param currentWindow
     * @param userType
     */
    public ColliViewHandler(String aHeading, Colli aColli, Packable aPackable, Login login, ManagerRepository aManagerRepository,
	    WindowInterface currentWindow, VismaFileCreator aVismaFileCreator) {
	super(aHeading, aManagerRepository.getColliManager(), true, login.getUserType(), true);
	vismaFileCreator = aVismaFileCreator;
	managerRepository = aManagerRepository;
	packable = aPackable;

	initColli(aColli);

	popupMenuRemoveOrderLine = new JPopupMenu("Fjern artikkel");
	menuItemRemoveOrderLine = new JMenuItem("Fjern artikkel");
	menuItemRemoveOrderLine.setEnabled(hasWriteAccess());
	popupMenuRemoveOrderLine.add(menuItemRemoveOrderLine);
	menuItemRemoveOrderLine.addActionListener(new MenuItemListenerRemoveOrderLine(currentWindow));

    }

    /**
     * Initierer kolli
     * 
     * @param aColli
     */
    @SuppressWarnings("unchecked")
    public void initColli(Colli aColli) {
	((ColliManager) overviewManager).lazyLoad(aColli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
	presentationModel = new PresentationModel(new ColliModel(aColli));
	orderLineList = new ArrayListModel((List<OrderLine>) presentationModel.getValue(ColliModel.PROPERTY_ORDER_LINES));
	orderLineSelectionList = new SelectionInList((ListModel) orderLineList);
	objectList.add(aColli);
    }

    @SuppressWarnings("unchecked")
    private void refreshOrderLineList() {
	orderLineList.clear();
	orderLineList.addAll((List<OrderLine>) presentationModel.getValue(ColliModel.PROPERTY_ORDER_LINES));

    }

    /**
     * Legger til lytter på kolli
     * 
     * @param listener
     */
    public void addColliSelectionListener(ColliListener listener) {
	colliListeners.add(listener);
    }

    /**
     * Lager label for navn
     * 
     * @return label
     */
    public JLabel getLabelColliName() {
	return BasicComponentFactory.createLabel(presentationModel.getModel(ColliModel.PROPERTY_COLLI_NAME_AND_NUMBER));
    }

    /**
     * Lager tekstfelt for navn
     * 
     * @param aPresentationModel
     * @return teksfelt
     */
    public JTextField getTextFieldColliName(PresentationModel aPresentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(aPresentationModel.getBufferedModel(ColliModel.PROPERTY_COLLI_NAME), false);
	textField.setName("TextFieldColliName");
	return textField;
    }

    /**
     * Lager tabell for ordrelinjer
     * 
     * @param window
     * @return tabell
     */
    @SuppressWarnings("unchecked")
    public JXTable getTableOrderLines(WindowInterface window) {

	table = new JXTable();
	tableModel = new ColliOrderLineTableModel(orderLineSelectionList, orderLineList);
	table.setModel(tableModel);
	table.setSelectionModel(new SingleListSelectionAdapter(orderLineSelectionList.getSelectionIndexHolder()));
	table.setColumnControlVisible(true);
	table.addMouseListener(new TableClickHandler());

	table.packAll();
	return table;
    }

    /**
     * Lager sjekkboks for selektering
     * 
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxSelection() {
	checkBoxSelection = new JCheckBox();
	checkBoxSelection.setEnabled(hasWriteAccess());
	return checkBoxSelection;
    }

    /**
     * Legger til ordrelinje
     * 
     * @param orderLine
     * @param index
     * @throws ProTransException
     */
    public void addOrderLine(OrderLine orderLine, int index) throws ProTransException {
	Colli currentColli = ((ColliModel) presentationModel.getBean()).getObject();

	Set<OrderLine> orderLines = currentColli.getOrderLines();
	if (orderLines == null) {
	    orderLines = new HashSet<OrderLine>();
	}
	orderLine.setColli(currentColli);

	orderLines.add(orderLine);
	currentColli.setOrderLines(orderLines);

	currentColli.setPackageDate(Util.getCurrentDate());
	((ColliManager) overviewManager).saveColli(currentColli);
	managerRepository.getOrderLineManager().saveOrderLine(orderLine);

	Order order = currentColli.getOrder();

	if (order != null) {

	    managerRepository.getOrderLineManager().lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });// ,
																    // LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES
																    // });
	    if (order.isDonePackage()) {
		order.setOrderComplete(Util.getCurrentDate());
		managerRepository.getOrderLineManager().saveOrder(order);
	    }
	} else {
	    PostShipment postShipment = currentColli.getPostShipment();

	    if (postShipment != null) {
		PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
		postShipmentManager.lazyLoad(postShipment, new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES });
		if (postShipment.isDonePackage()) {
		    postShipment.setPostShipmentComplete(Util.getCurrentDate());
		    postShipmentManager.savePostShipment(postShipment);
		}
	    }

	}

	if (tableModel != null) {
	    tableModel.insertRow(index, orderLine);
	}

	((ColliManager) overviewManager).refreshObject(currentColli);
	((ColliManager) overviewManager).lazyLoad(currentColli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });

    }

    /**
     * Tabellmodell for ordrelinjer i kolli
     * 
     * @author atle.brekka
     */
    public class ColliOrderLineTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	private List<OrderLine> orderLineList1;

	/**
	 * @param listModel
	 * @param list
	 */
	public ColliOrderLineTableModel(ListModel listModel, List<OrderLine> list) {
	    super(listModel, new String[] { "Artikkel" });
	    orderLineList1 = list;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    OrderLine orderLine = (OrderLine) getRow(rowIndex);
	    switch (columnIndex) {
	    case 0:
		return orderLine.getArticleName();

	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    switch (columnIndex) {
	    case 0:
		return String.class;

	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

	/**
	 * Legger til ordrelinje
	 * 
	 * @param index
	 * @param orderLine
	 */
	public void insertRow(int index, OrderLine orderLine) {
	    orderLineList1.add(index, orderLine);
	    packable.setColliesDone(1);
	    fireTableDataChanged();
	}

	/**
	 * Legger til ordrelinje
	 * 
	 * @param aOrderLine
	 * @param index
	 * @throws ProTransException
	 */
	public void insertOrderLine(OrderLine aOrderLine, int index) throws ProTransException {
	    addOrderLine(aOrderLine, index);
	}

    }

    /**
     * @param colli
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(Colli colli) {
	((ColliManager) overviewManager).lazyLoad(colli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
	if (colli.getOrderLines() != null && colli.getOrderLines().size() != 0) {
	    return new CheckObject("Kan ikke slette kolli som inneholder artikler", false);
	}
	return null;
    }

    /**
     * Initierer hendelehåndtering
     */
    public void initEventHandling() {
	objectSelectionList.setSelection(((ColliModel) presentationModel.getBean()).getObject());
	checkBoxSelection.addActionListener(new CollCheckBoxiActionListener((ColliModel) presentationModel.getBean()));
	((ColliModel) presentationModel.getBean()).addBufferChangeListener(new ColliBufferingListener(), presentationModel);
    }

    /**
     * Håmdterer valg av sjekkboks
     * 
     * @author atle.brekka
     */
    private final class CollCheckBoxiActionListener implements ActionListener {
	private ColliModel currentColliModel;

	/**
	 * @param colliModel
	 */
	public CollCheckBoxiActionListener(ColliModel colliModel) {
	    currentColliModel = colliModel;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    for (ColliListener listener : colliListeners) {
		listener.colliSelectionChange(checkBoxSelection.isSelected(), currentColliModel);
	    }

	}

    }

    /**
     * Lytter på buffering
     * 
     * @author atle.brekka
     */
    final class ColliBufferingListener implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent arg0) {
	    fireBufferChange();

	}

    }

    /**
     * Forteller at objekt har bufferverdier
     */
    void fireBufferChange() {
	for (PropertyChangeListener listener : bufferingListeners) {
	    listener.propertyChange(new PropertyChangeEvent(presentationModel.getBean(), String.valueOf(presentationModel.isBuffering()),
		    presentationModel.getBean(), String.valueOf(presentationModel.isBuffering())));
	}
    }

    /**
     * Setter valgt kolli
     * 
     * @param selected
     */
    public void setColliSelected(boolean selected) {
	checkBoxSelection.setSelected(selected);
    }

    /**
     * Lytter til meny for å fjerne ordrelinje
     * 
     * @author atle.brekka
     */
    private class MenuItemListenerRemoveOrderLine implements ActionListener {
	private WindowInterface window;

	/**
	 * @param aWindow
	 */
	public MenuItemListenerRemoveOrderLine(WindowInterface aWindow) {
	    window = aWindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent actionEvent) {
	    if (actionEvent.getActionCommand().equalsIgnoreCase(menuItemRemoveOrderLine.getText())) {

		removeOrderLine(window);

	    }
	}

    }

    /**
     * Fjerner ordrelinje
     * 
     * @param window
     */
    void removeOrderLine(WindowInterface window) {
	int index = table.convertRowIndexToModel(orderLineSelectionList.getSelectionIndex());
	if (index != -1) {
	    OrderLine orderLine = (OrderLine) orderLineSelectionList.getElementAt(index);

	    if (ApplicationParamUtil.getNotPackageList().indexOf(orderLine.getArticleName()) < 0) {

		Colli currentColli = ((ColliModel) presentationModel.getBean()).getObject();
		// ((ColliManager) overviewManager).lazyLoad(currentColli, new
		// LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES,
		// LazyLoadEnum.NONE } });
		Set<OrderLine> orderLines = currentColli.getOrderLines();
		if (orderLines == null) {
		    orderLines = new HashSet<OrderLine>();
		}

		boolean success = orderLines.remove(orderLine);
		if (success) {
		    orderLine.setColli(null);
		    // currentColli.setOrderLines(orderLines);

		    if (orderLines.size() == 0) {
			currentColli.setPackageDate(null);
		    }

		    ((ColliManager) overviewManager).saveColli(currentColli);
		    managerRepository.getOrderLineManager().saveOrderLine(orderLine);
		    orderLineList.remove(orderLine);

		    if (orderLine.getOrdNo() != null) {
			orderLine.setOrdln(managerRepository.getOrderLineManager().findOrdlnByOrderLine(orderLine.getOrderLineId()));
			vismaFileCreator.createVismaFile(Lists.newArrayList(orderLine), 1, true);
		    }

		    Order order = currentColli.getOrder();
		    if (order != null) {
			managerRepository.getOrderLineManager().refreshOrder(order);
			order.setOrderComplete(null);
			order.setColliesDone(0);
			try {

			    managerRepository.getOrderLineManager().saveOrder(order);
			} catch (ProTransException e) {
			    Util.showErrorDialog(window, "Feil", e.getMessage());
			    e.printStackTrace();
			}
		    } else {
			PostShipment postShipment = currentColli.getPostShipment();
			if (postShipment != null) {
			    PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil.getBean("postShipmentManager");
			    postShipment.setPostShipmentComplete(null);
			    postShipmentManager.savePostShipment(postShipment);
			}

		    }
		}

		fireOrderLineRemoved(window);
	    } else {
		Util.showMsgDialog(window.getComponent(), "Kan ikke fjerne artikkel", "Denne artikkelen kan ikke fjernes her!");
	    }

	}
    }

    /**
     * Forteller at ordrelinje er fjernet
     * 
     * @param window
     */
    private void fireOrderLineRemoved(WindowInterface window) {
	for (ColliListener listener : colliListeners) {
	    listener.orderLineRemoved(window);
	}
    }

    /**
     * Håndterer museklikk i tabell
     * 
     * @author atle.brekka
     */
    final class TableClickHandler extends MouseAdapter {
	public TableClickHandler() {
	}

	/**
	 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {

	    if (SwingUtilities.isRightMouseButton(e)) {

		popupMenuRemoveOrderLine.show((JXTable) e.getSource(), e.getX(), e.getY());

	    }

	}
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(userType, "Garasjepakke");
    }

    /**
     * @param handler
     * @param object
     * @param searching
     * @return view
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
     *      java.lang.Object, boolean)
     */
    @Override
    protected AbstractEditView<ColliModel, Colli> getEditView(AbstractViewHandler<Colli, ColliModel> handler, Colli object, boolean searching) {
	return new EditColliView(false, (ColliModel) presentationModel.getBean(), this);
    }

    @Override
    public String getClassName() {
	return "Colli";
    }

    public JTextField getTextFieldNumberOfCollies(PresentationModel aPresentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(aPresentationModel.getBufferedModel(ColliModel.PROPERTY_NUMBER_OF_COLLIES),
		false);
	textField.setName("TextFieldNumberOfCollies");
	return textField;
    }

    public void updateColliModel() {
	((ColliModel) presentationModel.getBean()).firePropertyChanged(ColliModel.PROPERTY_COLLI_NAME_AND_NUMBER);

    }

    public void colliSelectionChange(boolean selection, ColliModel colliModel) {
    }

    public void orderLineRemoved(WindowInterface window) {
    }

    public void refreshCollies() {
	refreshColli();

    }

    private void refreshColli() {
	Colli currentColli = ((ColliModel) presentationModel.getBean()).getObject();
	managerRepository.getColliManager().refreshObject(currentColli);
	managerRepository.getColliManager().lazyLoad(currentColli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
	presentationModel.setBean(new ColliModel(currentColli));
	refreshOrderLineList();
    }

    public JLabel getLabelHeightWidthLenght() {
	return BasicComponentFactory.createLabel(presentationModel.getModel(ColliModel.PROPERTY_COLLI_HEIGHT));
    }

    public JTextField getTextFieldHeight(PresentationModel aPresentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(aPresentationModel.getBufferedModel(ColliModel.PROPERTY_HEIGHT), false);
	textField.setName("TextFieldHeight");
	return textField;
    }

    public JTextField getTextFieldLenght(PresentationModel aPresentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(aPresentationModel.getBufferedModel(ColliModel.PROPERTY_LENGHT), false);
	textField.setName("TextFieldLenght");
	return textField;
    }

    public JTextField getTextFieldWidht(PresentationModel aPresentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(aPresentationModel.getBufferedModel(ColliModel.PROPERTY_WIDHT), false);
	textField.setName("TextFieldWidht");
	return textField;
    }

}
