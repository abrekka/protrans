package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditFunctionCategoryView;
import no.ugland.utransprod.gui.model.FunctionCategoryModel;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.FunctionCategoryManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;

/**
 * Håndterer funksjonskategorier
 * 
 * @author atle.brekka
 * 
 */
public class FunctionCategoryViewHandler extends
DefaultAbstractViewHandler<FunctionCategory, FunctionCategoryModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public FunctionCategoryViewHandler(UserType userType,FunctionCategoryManager functionCategoryManager) {
		super("Kategori", functionCategoryManager, userType, true);
	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(FunctionCategoryModel.PROPERTY_FUNCTION_CATEGORY_NAME));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for beskrivelse
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDescription(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(FunctionCategoryModel.PROPERTY_FUNCTION_CATEGORY_DESCRIPTION));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager komboboks for funksjon
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxFunction(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						getJobFunctionList(),
						presentationModel
								.getBufferedModel(FunctionCategoryModel.PROPERTY_JOB_FUNCTION)));
		comboBox.setEnabled(hasWriteAccess());
		return comboBox;
	}

	/**
	 * @param object
	 * @return feilmelsing
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(FunctionCategory object) {
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
	public CheckObject checkSaveObject(FunctionCategoryModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "kategori";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "FunctionCategory";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public FunctionCategory getNewObject() {
		return new FunctionCategory();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new FunctionCategoryTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "220dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Kategori";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(550, 270);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Navn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(100);

		// Beskrivelse
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(100);

		// Funksjon
		col = table.getColumnModel().getColumn(2);
		col.setPreferredWidth(150);

	}

	/**
	 * Henter ur funksjonsliste
	 * 
	 * @return funksjonsliste
	 */
	public List<JobFunction> getJobFunctionList() {
		JobFunctionManager jobFunctionManager = (JobFunctionManager) ModelUtil
				.getBean("jobFunctionManager");
		return jobFunctionManager.findAll();
	}

	/**
	 * Tabellmodell for kategorier
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class FunctionCategoryTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Navn", "Beskrivelse",
				"Funksjon" };

		/**
		 * @param listModel
		 */
		FunctionCategoryTableModel(ListModel listModel) {
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
			FunctionCategory functionCategory = (FunctionCategory) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return functionCategory.getFunctionCategoryName();
			case 1:
				return functionCategory.getFunctionCategoryDescription();
			case 2:
				return functionCategory.getJobFunction();
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
			case 2:
				return JobFunction.class;
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
		return UserUtil.hasWriteAccess(userType, "Kategori");
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
	protected AbstractEditView<FunctionCategoryModel, FunctionCategory> getEditView(
			AbstractViewHandler<FunctionCategory, FunctionCategoryModel> handler,
			FunctionCategory object, boolean searching) {

		return new EditFunctionCategoryView(searching,
				new FunctionCategoryModel(object), this);
	}

}
