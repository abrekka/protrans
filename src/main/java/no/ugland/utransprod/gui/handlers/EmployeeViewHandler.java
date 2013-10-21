package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditEmployeeView;
import no.ugland.utransprod.gui.handlers.SupplierViewHandler.FilterInactiveListener;
import no.ugland.utransprod.gui.model.EmployeeModel;
import no.ugland.utransprod.gui.model.EmployeeTypeModel;
import no.ugland.utransprod.gui.model.SupplierModel;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.EmployeeType;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.EmployeeManager;
import no.ugland.utransprod.service.EmployeeTypeManager;
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

/**
 * Håndterer ansatte
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeViewHandler extends
DefaultAbstractViewHandler<Employee, EmployeeModel> {

	private static final long serialVersionUID = 1L;

	private ArrayListModel employeeTypeList;

	private Supplier supplier;

	private EmployeeTypeManager employeeTypeManager;
	private JCheckBox checkBoxShowInactive;

	/**
	 * @param aSupplier
	 * @param userType
	 */
	public EmployeeViewHandler(Supplier aSupplier, UserType userType,EmployeeManager employeeManager,EmployeeTypeManager aEmployeeTypeManager) {
		super("Ansatte", employeeManager, userType, true);
		employeeTypeManager=aEmployeeTypeManager;
		//employeeTypeManager=aEmployeeTypeManager;
		supplier = aSupplier;
		initObjects();

		employeeTypeList = new ArrayListModel();
		refreshEmployeeList();
	}

	/**
	 * Lager tekstfelt for fornavn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFirstName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(EmployeeModel.PROPERTY_FIRST_NAME));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for etternavn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldLastName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(EmployeeModel.PROPERTY_LAST_NAME));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for telefon
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldPhone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(SupplierModel.PROPERTY_PHONE));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager komboboks for ansattyper
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxEmployeeType(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						(ListModel) employeeTypeList,
						presentationModel
								.getBufferedModel(EmployeeModel.PROPERTY_EMPLOYEE_TYPE)));
		comboBox.setEnabled(hasWriteAccess());
		return comboBox;
	}

	/**
	 * Lager knapp for å legge til ansattyper
	 * 
	 * @return knapp
	 */
	public JButton getButtonEmployeeType() {
		return new JButton(new EmployeeTypeAction());
	}

	/**
	 * @param object
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Employee object) {
		return null;
	}

	/**
	 * @param object
	 * @param presentationModel
	 * @param window
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public CheckObject checkSaveObject(EmployeeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "ansatt";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Employee";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Employee getNewObject() {
		return new Employee(null, null, null, null, null, supplier);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new EmployeeTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "250dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableHeight()
	 */
	@Override
	public String getTableHeight() {
		return "50dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Ansatte - " + supplier.getSupplierName();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(630, 300);
	}

	/**
	 * Setter inn ny ansatt
	 * 
	 * @param window
	 * 
	 * @return ansatt
	 */
	public Employee insertNewEmployee(WindowInterface window) {
		Employee employee = new Employee();
		openEditView(employee, false, window);
		return employee;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(150);

	}

	/**
	 * Tbellmodell for ansatte
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class EmployeeTableModel extends AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Fornavn", "Etternavn",
				"Telefon", "Type","Inaktiv","Aktiv" };

		/**
		 * @param listModel
		 */
		public EmployeeTableModel(ListModel listModel) {
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
			Employee employee = (Employee) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return employee.getFirstName();
			case 1:
				return employee.getLastName();
			case 2:
				return employee.getPhone();
			case 3:
				return employee.getEmployeeType();
			case 4:
				return employee.getInactive()==null||employee.getInactive()==0?Boolean.FALSE:Boolean.TRUE;
			case 5:
				return employee.getInactive()==null||employee.getInactive()==0?"Ja":"Nei";
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
			case 5:
				return String.class;
			case 3:
				return EmployeeType.class;
			case 4:
				return Boolean.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Oppdaterer ansattliste
	 */
	void refreshEmployeeList() {
		employeeTypeList.clear();
		//EmployeeTypeManager employeeTypeManager = (EmployeeTypeManager) ModelUtil.getBean("employeeTypeManager");
		employeeTypeList.addAll(employeeTypeManager.findAll());
	}

	/**
	 * Åpner dialog for ansattyper
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class EmployeeTypeAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		

		/**
		 * 
		 */
		public EmployeeTypeAction() {
			super("Typer...");
			
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			EmployeeTypeViewHandler employeeTypeViewHandler = new EmployeeTypeViewHandler(
					userType,employeeTypeManager);
			OverviewView<EmployeeType, EmployeeTypeModel> employeeTypeView = new OverviewView<EmployeeType, EmployeeTypeModel>(
					employeeTypeViewHandler);

			JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN,
					employeeTypeView.getTitle(), true);
			WindowInterface window = new JDialogAdapter(dialog);
			window.add(employeeTypeView.buildPanel(window));

			window.pack();
			window.setSize(employeeTypeViewHandler.getWindowSize());

			Util.locateOnScreenCenter(window);
			window.setVisible(true);
			refreshEmployeeList();

		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@Override
	protected void initObjects() {
		setFiltered(false);
		objectSelectionList.clearSelection();
		objectList.clear();
		List<Employee> allObjects = ((EmployeeManager) overviewManager)
				.findBySupplier(supplier);
		if (allObjects != null) {
			objectList.addAll(allObjects);
		}
		noOfObjects = objectList.getSize();
		if (table != null) {
			table.scrollRowToVisible(0);
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
	protected AbstractEditView<EmployeeModel, Employee> getEditView(
			AbstractViewHandler<Employee, EmployeeModel> handler,
			Employee object, boolean searching) {
		return new EditEmployeeView(searching, object, this);
	}

	public JCheckBox getCheckBoxInactive(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(EmployeeModel.PROPERTY_INACTIVE_BOOL),
						"Inaktiv");
		checkBox.setName("CheckBoxInactive");
		return checkBox;
	}

	public JCheckBox getCheckBoxShowInactive() {
		checkBoxShowInactive = new JCheckBox("Vis inaktive");
		checkBoxShowInactive.setSelected(true);
		checkBoxShowInactive.setName("CheckBoxFilterShowInactive");
		checkBoxShowInactive.addActionListener(new FilterInactiveListener());
		return checkBoxShowInactive;
	}

	class FilterInactiveListener implements ActionListener {

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			handleShowInactive();

		}

		

	}
	private void handleShowInactive() {
		table.clearSelection();
		objectSelectionList.clearSelection();
		
		if(checkBoxShowInactive.isSelected()){
			table.setFilters(null);
		}else{
			Filter filterApplied = new PatternFilter("ja",
					Pattern.CASE_INSENSITIVE, 5);
			FilterPipeline filterPipeline = new FilterPipeline(filterApplied);
			table.setFilters(filterPipeline);
		}
		
	}
    
}
