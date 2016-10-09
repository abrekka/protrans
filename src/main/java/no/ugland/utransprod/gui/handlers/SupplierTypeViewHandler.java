package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditSupplierTypeView;
import no.ugland.utransprod.gui.model.SupplierTypeModel;
import no.ugland.utransprod.model.SupplierType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.SupplierTypeManager;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Håndterer leveradørtyper
 * 
 * @author atle.brekka
 * 
 */
public class SupplierTypeViewHandler extends
DefaultAbstractViewHandler<SupplierType, SupplierTypeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public SupplierTypeViewHandler(UserType userType,SupplierTypeManager supplierTypeManager) {
		super("Leverandørtyper", supplierTypeManager, userType, true);
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
						.getBufferedModel(SupplierTypeModel.PROPERTY_SUPPLIER_TYPE_NAME));
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
						.getBufferedModel(SupplierTypeModel.PROPERTY_DESCRIPTION));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * @param object
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(SupplierType object) {
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
	public CheckObject checkSaveObject(SupplierTypeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "leverandørtype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "SupplierType";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public SupplierType getNewObject() {
		return new SupplierType();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new SupplierTypeTableModel(objectSelectionList);
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
		return "Leverandørtyper";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 300);
	}

	/**
	 * Lager ny leverandørtype
	 * 
	 * @param window
	 * 
	 * @return leverandørtype
	 */
	public SupplierType insertNewSupplierType(WindowInterface window) {
		SupplierType supplierType = new SupplierType();
		openEditView(supplierType, false, window,false);
		return supplierType;
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
	 * Tabellmodell for leverandørtyper
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class SupplierTypeTableModel extends
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
		public SupplierTypeTableModel(ListModel listModel) {
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
			SupplierType supplierType = (SupplierType) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return supplierType.getSupplierTypeName();
			case 1:
				return supplierType.getDescription();
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
		return UserUtil.hasWriteAccess(userType, "Leverandørtyper");
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
	protected AbstractEditView<SupplierTypeModel, SupplierType> getEditView(
			AbstractViewHandler<SupplierType, SupplierTypeModel> handler,
			SupplierType object, boolean searching) {
		return new EditSupplierTypeView(searching, object, this);
	}

}
