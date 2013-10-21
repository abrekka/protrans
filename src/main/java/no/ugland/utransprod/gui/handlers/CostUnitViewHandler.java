package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCostUnitView;
import no.ugland.utransprod.gui.model.CostUnitModel;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Hjelpeklasse for visning av kostnadsenheter
 * 
 * @author atle.brekka
 * 
 */
public class CostUnitViewHandler extends
DefaultAbstractViewHandler<CostUnit, CostUnitModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public CostUnitViewHandler(Login login,CostUnitManager costUnitManager) {
		super("Kostnadsenheter", costUnitManager, login.getUserType(), true);
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
						.getBufferedModel(CostUnitModel.PROPERTY_COST_UNIT_NAME));
		textField.setName("CostUnitName");
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
						.getBufferedModel(CostUnitModel.PROPERTY_COST_UNIT_DESCRIPTION));
		textField.setName("Description");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * @param object
	 * @return feil
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(CostUnit object) {
		overviewManager.lazyLoad(object, new LazyLoadEnum[][]{{LazyLoadEnum.ORDER_COSTS,LazyLoadEnum.NONE}});
		if (object.getOrderCosts() != null
				&& object.getOrderCosts().size() != 0) {
			return new CheckObject("Kan ikke slette kostnadsenhet som brukes av ordre",false);
		}
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
	public CheckObject checkSaveObject(CostUnitModel object,
			PresentationModel presentationModel, WindowInterface window) {

		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "kostnadsenhet";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "CostUnit";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public CostUnit getNewObject() {
		return new CostUnit();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new CostUnitTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "140dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Kostnadsenheter";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(420, 260);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Navn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(80);

		// Beskrivelse
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(150);

	}

	/**
	 * Tabellmodell gor kostnadsenheter
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class CostUnitTableModel extends AbstractTableAdapter {

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
		CostUnitTableModel(ListModel listModel) {
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
			CostUnit costUnit = (CostUnit) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return costUnit.getCostUnitName();
			case 1:
				return costUnit.getDescription();
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
		return UserUtil.hasWriteAccess(userType, "Kostnadsenheter");
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
	protected AbstractEditView<CostUnitModel, CostUnit> getEditView(
			AbstractViewHandler<CostUnit, CostUnitModel> handler,
			CostUnit object, boolean searching) {
		return new EditCostUnitView(this, object, searching);
	}

    

}
