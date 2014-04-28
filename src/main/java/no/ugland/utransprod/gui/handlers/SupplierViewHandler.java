package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.EmployeeView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditSupplierView;
import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.gui.model.SupplierModel;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.SupplierProductAreaGroup;
import no.ugland.utransprod.model.SupplierType;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.SupplierTypeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer leverandører
 * 
 * @author atle.brekka
 * 
 */
public class SupplierViewHandler extends DefaultAbstractViewHandler<Supplier, SupplierModel> {
    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
	 * 
	 */
    private ArrayListModel supplierTypeList;

    private ManagerRepository managerRepository;
    private ArrayListModel productAreaGroupArray;
    private SelectionInList productAreaGroupSelectionList;

    private JButton buttonDeleteProductAreaGroup;
    private JCheckBox checkBoxShowInactive;

    /**
     * @param userType
     */
    public SupplierViewHandler(Login login, ManagerRepository aManagerRepository) {
	super("Leverandører", aManagerRepository.getSupplierManager(), login.getUserType(), true);
	managerRepository = aManagerRepository;

	productAreaGroupArray = new ArrayListModel();
	productAreaGroupSelectionList = new SelectionInList((ListModel) productAreaGroupArray);

	productAreaGroupSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_EMPTY, new EmptyListenerProductAreaGroup());
    }

    /**
     * Lager tekstfelt for navn
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldSupplierName(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_SUPPLIER_NAME));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldSupplierName");
	return textField;
    }

    /**
     * Lager tekstfelt for beskrivelse
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldSupplierDescription(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_SUPPLIER_DESCRIPTION));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldSupplierDescription");
	return textField;
    }

    /**
     * Lager tekstfelt for telefon
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldPhone(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_PHONE));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldPhone");
	return textField;
    }

    /**
     * Lager tekstfelt for fax
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldFax(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_FAX));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldFax");
	return textField;
    }

    public JTextField getTextFieldSupplierNr(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_SUPPLIER_NR));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldSupplierNr");
	return textField;
    }

    /**
     * Lager tekstfelt for adresse
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldAddress(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_ADDRESS));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldAddress");
	return textField;
    }

    /**
     * Lager tekstfelt for postnummer
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldPostalCode(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_POSTAL_CODE));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldPostalCode");
	return textField;
    }

    /**
     * Lager tekstfelt for poststed
     * 
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldPostOffice(PresentationModel presentationModel) {
	JTextField textField = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(SupplierModel.PROPERTY_POST_OFFICE));
	textField.setEnabled(hasWriteAccess());
	textField.setName("TextFieldPostOffice");
	return textField;
    }

    /**
     * Lager komboboks for leverandørtype
     * 
     * @param presentationModel
     * @return komboboks
     */
    public JComboBox getComboBoxSupplierType(PresentationModel presentationModel) {
	SupplierTypeManager supplierTypeManager = (SupplierTypeManager) ModelUtil.getBean("supplierTypeManager");
	supplierTypeList = new ArrayListModel(supplierTypeManager.findAll());
	JComboBox comboBox = new JComboBox(new ComboBoxAdapter((ListModel) supplierTypeList,
		presentationModel.getBufferedModel(SupplierModel.PROPERTY_SUPPLIER_TYPE)));
	comboBox.setEnabled(hasWriteAccess());
	comboBox.setName("ComboBoxSupplierType");
	return comboBox;
    }

    public JCheckBox getCheckBoxInactive(PresentationModel presentationModel) {
	JCheckBox checkBox = BasicComponentFactory
		.createCheckBox(presentationModel.getBufferedModel(SupplierModel.PROPERTY_INACTIVE_BOOL), "Inaktiv");
	checkBox.setName("CheckBoxInactive");
	return checkBox;
    }

    /**
     * Lager knapp for ansatte
     * 
     * @return knapp
     */
    public JButton getButtonEmployee() {
	return new JButton(new EmployeeAction());
    }

    public JButton getButtonAddProductAreaGroup(PresentationModel presentationModel, WindowInterface window) {
	return new JButton(new AddProductAreaGroupAction(presentationModel, window));
    }

    public JButton getButtonDeleteProductAreaGroup(PresentationModel presentationModel) {
	buttonDeleteProductAreaGroup = new JButton(new DeleteProductAreaGroupAction(presentationModel));
	buttonDeleteProductAreaGroup.setEnabled(false);
	return buttonDeleteProductAreaGroup;
    }

    /**
     * @param object
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(Supplier supplier) {
	if (((SupplierManager) overviewManager).hasOrderCosts(supplier)) {
	    return new CheckObject("Kan ikke slette leverandør som er knyttet opp mot kostnader!", false);
	}
	return null;
    }

    /**
     * @param object
     * @param presentationModel
     * @param window
     * @return feilmelsing
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public CheckObject checkSaveObject(SupplierModel object, PresentationModel presentationModel, WindowInterface window) {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
	return "leverandør";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
	return "Supplier";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public Supplier getNewObject() {
	return new Supplier();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
	return new SupplierTableModel(objectSelectionList);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
	return "150dlu";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
	return "Leverandører";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
	return new Dimension(800, 500);
    }

    /**
     * Legger til ny leverandør
     * 
     * @param window
     * 
     * @return leverandør
     */
    public Supplier insertNewSupplier(WindowInterface window) {
	Supplier supplier = new Supplier();
	openEditView(supplier, false, window);
	return supplier;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
	// navn
	table.getColumnExt(0).setPreferredWidth(170);
	// telefon
	table.getColumnExt(1).setPreferredWidth(70);
	// beskrivelse
	table.getColumnExt(2).setPreferredWidth(100);
	// type
	table.getColumnExt(3).setPreferredWidth(150);
    }

    /**
     * Tabellmodell for leverandør
     * 
     * @author atle.brekka
     * 
     */
    public static final class SupplierTableModel extends AbstractTableAdapter {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	/**
		 * 
		 */
	private static final String[] COLUMNS = { "Navn", "Leverandørnr", "Telefon", "Beskrivelse", "Type", "Inaktiv", "Aktiv" };

	/**
	 * @param listModel
	 */
	public SupplierTableModel(ListModel listModel) {
	    super(listModel, COLUMNS);
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    Supplier supplier = (Supplier) getRow(rowIndex);
	    switch (columnIndex) {
	    case 0:
		return supplier.getSupplierName();
	    case 1:
		return supplier.getSupplierNr();
	    case 2:
		return supplier.getPhone();
	    case 3:
		return supplier.getSupplierDescription();
	    case 4:
		return supplier.getSupplierType();
	    case 5:
		return supplier.getInactive() == null || supplier.getInactive() == 0 ? Boolean.FALSE : Boolean.TRUE;
	    case 6:
		return supplier.getInactive() == null || supplier.getInactive() == 0 ? "nei" : "ja";
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
	    case 1:
	    case 2:
	    case 3:
	    case 6:
		return String.class;
	    case 4:
		return SupplierType.class;
	    case 5:
		return Boolean.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * Ansatte
     * 
     * @author atle.brekka
     * 
     */
    private class EmployeeAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	public EmployeeAction() {
	    super("Ansatte...");

	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    Supplier supplier = (Supplier) objectSelectionList.getElementAt(table.convertRowIndexToModel(objectSelectionList.getSelectionIndex()));
	    EmployeeViewHandler employeeViewHandler = new EmployeeViewHandler(supplier, userType, managerRepository.getEmployeeManager(),
		    managerRepository.getEmployeeTypeManager());
	    EmployeeView employeeView = new EmployeeView(employeeViewHandler);

	    JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN, employeeView.getTitle(), true);
	    WindowInterface window = new JDialogAdapter(dialog);
	    window.add(employeeView.buildPanel(window));

	    window.pack();
	    window.setSize(employeeViewHandler.getWindowSize());

	    Util.locateOnScreenCenter(window);
	    window.setVisible(true);

	}
    }

    private class AddProductAreaGroupAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private PresentationModel presentationModel;
	private WindowInterface window;

	public AddProductAreaGroupAction(PresentationModel aPresentationModel, WindowInterface awindow) {
	    super("Legg til produktområde");
	    presentationModel = aPresentationModel;
	    window = awindow;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    Supplier supplier = ((SupplierModel) presentationModel.getBean()).getObject();

	    List<ProductAreaGroup> groups = Util.getProductAreaGroupList();

	    ProductAreaGroup selectedGroup = (ProductAreaGroup) JOptionPane.showInputDialog(window.getComponent(), "Velg produktområde",
		    "Legg til produktområde", JOptionPane.INFORMATION_MESSAGE, null, groups.toArray(), null);

	    if (selectedGroup != null) {
		if (selectedGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
		    Util.showErrorDialog(window, "Ugyldig valg", "Kan ikke velge alle");
		    return;
		}
		SupplierProductAreaGroup supplierGroup = new SupplierProductAreaGroup(supplier, selectedGroup);
		productAreaGroupArray.add(supplierGroup);
		presentationModel.setBufferedValue(SupplierModel.PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST, productAreaGroupArray);
		((ApplicationUserModel) presentationModel.getBean()).firePropertyChanged();
	    }

	}
    }

    private class DeleteProductAreaGroupAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	private PresentationModel presentationModel;

	public DeleteProductAreaGroupAction(PresentationModel aPresentationModel) {
	    super("Fjern produktområde");
	    presentationModel = aPresentationModel;
	}

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent arg0) {
	    SupplierProductAreaGroup group = (SupplierProductAreaGroup) productAreaGroupSelectionList.getSelection();
	    if (group != null) {
		productAreaGroupArray.remove(group);

		presentationModel.setBufferedValue(SupplierModel.PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST, productAreaGroupArray);
	    }

	}
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(userType, "Leverandører");
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
    protected AbstractEditView<SupplierModel, Supplier> getEditView(AbstractViewHandler<Supplier, SupplierModel> handler, Supplier object,
	    boolean searching) {
	managerRepository.getSupplierManager().lazyLoad(object,
		new LazyLoadEnum[][] { { LazyLoadEnum.SUPPLIER_PRODUCT_AREA_GROUPS, LazyLoadEnum.NONE } });
	return new EditSupplierView(searching, object, this);
    }

    public JList getListProductAreaGroup(PresentationModel presentationModel) {
	productAreaGroupArray.clear();
	productAreaGroupArray.addAll((List<SupplierProductAreaGroup>) presentationModel
		.getBufferedValue(SupplierModel.PROPERTY_SUPPLIER_PRODUCT_AREA_GROUPS_LIST));
	JList list = BasicComponentFactory.createList(productAreaGroupSelectionList);
	return list;
    }

    class EmptyListenerProductAreaGroup implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent arg0) {
	    buttonDeleteProductAreaGroup.setEnabled(productAreaGroupSelectionList.hasSelection());

	}

    }

    public JCheckBox getCheckBoxShowInactive() {
	checkBoxShowInactive = new JCheckBox("Vis inaktive");
	checkBoxShowInactive.setSelected(true);
	checkBoxShowInactive.setName("CheckBoxFilterShowInactive");
	checkBoxShowInactive.addActionListener(new FilterInactiveListener());
	return checkBoxShowInactive;
    }

    private void handleShowInactive() {
	table.clearSelection();
	objectSelectionList.clearSelection();

	if (checkBoxShowInactive.isSelected()) {
	    table.setFilters(null);
	} else {
	    Filter filterApplied = new PatternFilter("nei", Pattern.CASE_INSENSITIVE, 5);
	    FilterPipeline filterPipeline = new FilterPipeline(filterApplied);
	    table.setFilters(filterPipeline);
	}

    }

    class FilterInactiveListener implements ActionListener {

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent arg0) {
	    handleShowInactive();

	}

    }
}
