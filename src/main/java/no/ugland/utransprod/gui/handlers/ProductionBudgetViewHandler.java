package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditProductionBudgetView;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.toedter.calendar.JYearChooser;

/**
 * Håndterer produksjonsbudsjett
 * 
 * @author atle.brekka
 * 
 */
public class ProductionBudgetViewHandler extends
DefaultAbstractViewHandler<Budget, ProductionBudgetModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private List<ProductArea> productAreaList;

	/**
	 * @param aUserType
	 * @param useDisposeOnClose
	 */
	public ProductionBudgetViewHandler(Login aLogin,
			boolean useDisposeOnClose,BudgetManager productionBudgetManager) {
		super("Produksjonbudsjett", productionBudgetManager, aLogin.getUserType(),
				useDisposeOnClose);

		ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
				.getBean("productAreaManager");
		productAreaList = new ArrayList<ProductArea>(productAreaManager
				.findAll());
	}

	/**
	 * Lag årvelger
	 * 
	 * @param presentationModel
	 * @return årvelger
	 */
	public JYearChooser getYearChooser(PresentationModel presentationModel) {
		JYearChooser yearChooser = new JYearChooser();
		PropertyConnector conn = new PropertyConnector(
				yearChooser,
				"year",
				presentationModel
						.getBufferedModel(ProductionBudgetModel.PROPERTY_BUDGET_YEAR),
				"value");
		if (presentationModel
				.getBufferedValue(ProductionBudgetModel.PROPERTY_BUDGET_YEAR) != null) {
			conn.updateProperty1();
		} else {
			conn.updateProperty2();
		}
		yearChooser.setName("YearChooser");
		return yearChooser;
	}

	/**
	 * Lag komboboks for uke
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxWeek(PresentationModel presentationModel) {
		return new JComboBox(
				new ComboBoxAdapter(
						Util.getWeeks(),
						presentationModel
								.getBufferedModel(ProductionBudgetModel.PROPERTY_BUDGET_WEEK)));

	}

	/**
	 * Lager komboboks med produktområder
	 * 
	 * @param presentationModel
	 * @return kombobkos
	 */
	public JComboBox getComboBoxProductArea(PresentationModel presentationModel) {
		return new JComboBox(
				new ComboBoxAdapter(
						productAreaList,
						presentationModel
								.getBufferedModel(ProductionBudgetModel.PROPERTY_PRODUCT_AREA)));

	}

	/**
	 * Lager tekstfelt for budsjett
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldBudget(PresentationModel presentationModel) {
		return BasicComponentFactory.createTextField(presentationModel
				.getBufferedModel(ProductionBudgetModel.PROPERTY_BUDGET_VALUE),
				false);
	}

	/**
	 * @param object
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Budget object) {
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
	public CheckObject checkSaveObject(ProductionBudgetModel object,
			PresentationModel presentationModel, WindowInterface window) {
		String errorMsg = null;
		List<Budget> budgets = ((BudgetManager) overviewManager)
				.findByYearAndWeek(object.getBudgetYear(), object
						.getBudgetWeek(), object.getObject().getBudgetId(),
						object.getProductArea(),BudgetType.PRODUCTION);
		if (budgets != null && budgets.size() != 0) {
			errorMsg = "Finnes allerede et budsjett for denne uka.";
		}

		return new CheckObject(errorMsg,false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "budsjettuke";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "ProductionBudget";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Budget getNewObject() {
		return new Budget();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ProductionBudgetTableModel(objectSelectionList);
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
		return "Produksjonsbudsjett";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 300);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Produksjonsbudsjett");
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
	}

	/**
	 * Tabellmodell for budsjett
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class ProductionBudgetTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "År", "Uke", "Budsjett",
				"Produktområde" };

		/**
		 * @param listModel
		 */
		public ProductionBudgetTableModel(ListModel listModel) {
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
			Budget productionBudget = (Budget) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return productionBudget.getBudgetYear();
			case 1:
				return productionBudget.getBudgetWeek();
			case 2:
				return productionBudget.getBudgetValue();
			case 3:
				return productionBudget.getProductArea();
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
				return Integer.class;
			case 2:
				return BigDecimal.class;
			case 3:
				return ProductArea.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

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
	protected AbstractEditView<ProductionBudgetModel, Budget> getEditView(
			AbstractViewHandler<Budget, ProductionBudgetModel> handler,
			Budget object, boolean searching) {
		return new EditProductionBudgetView(searching, object, this);
	}

}
