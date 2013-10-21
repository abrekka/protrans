package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditEmployeeTypeView;
import no.ugland.utransprod.gui.model.EmployeeTypeModel;
import no.ugland.utransprod.model.EmployeeType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.EmployeeTypeManager;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Håndterer editring av ansatttyper
 * 
 * @author atle.brekka
 * 
 */
public class EmployeeTypeViewHandler extends
DefaultAbstractViewHandler<EmployeeType, EmployeeTypeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public EmployeeTypeViewHandler(UserType userType,EmployeeTypeManager employeeTypeManager) {
		super("Ansatttyper", employeeTypeManager, userType, true);
	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldTypeName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(EmployeeTypeModel.PROPERTY_EMPLOYEE_TYPE_NAME));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for beskrivelse
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldTypeDescription(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(EmployeeTypeModel.PROPERTY_EMPLOYEE_TYPE_DESCRIPTION));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * @param object
	 * @return feilmelsing
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(EmployeeType object) {
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
	public CheckObject checkSaveObject(EmployeeTypeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "ansatttype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "EmployeeType";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public EmployeeType getNewObject() {
		return new EmployeeType();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new EmployeeTypeTableModel(objectSelectionList);
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
		return "Ansatttyper";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 300);
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
	 * Tabellmodell for ansattyper
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class EmployeeTypeTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Navn", "Beskrivelse" };

		/**
		 * @param listModel
		 */
		public EmployeeTypeTableModel(ListModel listModel) {
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
			EmployeeType employeeType = (EmployeeType) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return employeeType.getEmployeeTypeName();
			case 1:
				return employeeType.getEmployeeTypeDescription();
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
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
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
	protected AbstractEditView<EmployeeTypeModel, EmployeeType> getEditView(
			AbstractViewHandler<EmployeeType, EmployeeTypeModel> handler,
			EmployeeType object, boolean searching) {
		return new EditEmployeeTypeView(searching, object, this);
	}

    

}
