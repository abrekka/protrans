package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditProductionUnitView;
import no.ugland.utransprod.gui.model.ProductionUnitModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.ProductionUnitProductAreaGroup;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;

public class ProductionUnitViewHandler extends
		DefaultAbstractViewHandler<ProductionUnit, ProductionUnitModel> {
	private static final long serialVersionUID = 1L;
	private SelectionInList productAreaGroupSelectionList;
	private JButton buttonRemoveProductAreaGroup;

	@Inject
	public ProductionUnitViewHandler(Login login,
			ProductionUnitManager productionUnitManager) {
		super("Produksjonsenhter", productionUnitManager, false, login
				.getUserType(), true);
	}

	public JTextField getTextFieldProductionUnitName(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_NAME));
		textField.setName("TextFieldProductionUnitName");
		return textField;
	}

	public JComboBox getComboBoxArticleType(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util
				.getArticleTypeList(), presentationModel
				.getBufferedModel(ProductionUnitModel.PROPERTY_ARTICLE_TYPE)));
		comboBox.setName("ComboBoxArticleType");
		return comboBox;
	}

	public JList getListProductAreaGroup(PresentationModel presentationModel) {
		productAreaGroupSelectionList = new SelectionInList(
				presentationModel
						.getBufferedModel(ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST));
		productAreaGroupSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptySelectionHandler());
		JList list = BasicComponentFactory
				.createList(productAreaGroupSelectionList);
		list.setName("ListProductAreaGroup");
		return list;
	}

	public JButton getButtonAddProductAreaGroup(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new AddProductAreaGroupAction(window,
				presentationModel));
		button.setName("ButtonAddProductAreaGroup");
		return button;
	}

	public JButton getButtonRemoveProductAreaGroup(
			PresentationModel presentationModel) {
		buttonRemoveProductAreaGroup = new JButton(
				new RemoveProductAreaGroupAction(presentationModel));
		buttonRemoveProductAreaGroup.setName("ButtonRemoveProductAreaGroup");
		buttonRemoveProductAreaGroup.setEnabled(false);
		return buttonRemoveProductAreaGroup;
	}

	@Override
	public CheckObject checkDeleteObject(ProductionUnit object) {
		return null;
	}

	@Override
	public CheckObject checkSaveObject(ProductionUnitModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	@Override
	public String getAddRemoveString() {
		return "produksjonsenhet";
	}

	@Override
	public String getClassName() {
		return "ProductionUnit";
	}

	@Override
	protected AbstractEditView<ProductionUnitModel, ProductionUnit> getEditView(
			AbstractViewHandler<ProductionUnit, ProductionUnitModel> handler,
			ProductionUnit object, boolean searching) {
		overviewManager.lazyLoad(object, new LazyLoadEnum[][] { {
				LazyLoadEnum.PRODUCTION_UNIT_PRODUCT_AREA_GROUPS,
				LazyLoadEnum.NONE } });
		return new EditProductionUnitView(searching, new ProductionUnitModel(
				object), this);
	}

	@Override
	public ProductionUnit getNewObject() {
		return new ProductionUnit();
	}

	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ProductionUnitTableModel(objectSelectionList);
	}

	@Override
	public String getTableWidth() {
		return "100dlu";
	}

	@Override
	public String getTitle() {
		return "Produksjonsenheter";
	}

	@Override
	public Dimension getWindowSize() {
		return new Dimension(400, 300);
	}

	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Produksjonsenhet");
	}

	@Override
	public void setColumnWidth(JXTable table) {

	}

	private class AddProductAreaGroupAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public AddProductAreaGroupAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Legg til produktområde...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			addProductAreaGroup(window, presentationModel);

		}
	}

	private class RemoveProductAreaGroupAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private PresentationModel presentationModel;

		public RemoveProductAreaGroupAction(PresentationModel aPresentationModel) {
			super("Fjern produktområde");
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			removeProductAreaGroup(presentationModel);

		}
	}

	private static final class ProductionUnitTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Navn", "Artikkel" };

		/**
		 * @param listModel
		 */
		ProductionUnitTableModel(ListModel listModel) {
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
			ProductionUnit productionUnit = (ProductionUnit) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return productionUnit.getProductionUnitName();
			case 1:

				return productionUnit.getArticleType();
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
			case 1:
				return ArticleType.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	@SuppressWarnings("unchecked")
	private void addProductAreaGroup(WindowInterface window,
			PresentationModel presentationModel) {
		ProductAreaGroup productAreaGroup = (ProductAreaGroup) Util
				.showOptionsDialogCombo(window, Util.getProductAreaGroupList(),
						"Velg produktområde", true, null);
		if (productAreaGroup != null) {
			ProductionUnitProductAreaGroup group = new ProductionUnitProductAreaGroup();
			group.setProductAreaGroup(productAreaGroup);
			group.setProductionUnit(((ProductionUnitModel) presentationModel
					.getBean()).getObject());

			List<ProductionUnitProductAreaGroup> list = new ArrayList<ProductionUnitProductAreaGroup>(
					(List<ProductionUnitProductAreaGroup>) presentationModel
							.getBufferedValue(ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST));
			list.add(group);
			presentationModel
					.getBufferedModel(
							ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST)
					.setValue(list);
		}
	}

	private class EmptySelectionHandler implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			if (buttonRemoveProductAreaGroup != null) {
				buttonRemoveProductAreaGroup
						.setEnabled(productAreaGroupSelectionList
								.hasSelection());
			}

		}

	}

	@SuppressWarnings("unchecked")
	private void removeProductAreaGroup(PresentationModel presentationModel) {
		ProductionUnitProductAreaGroup group = (ProductionUnitProductAreaGroup) productAreaGroupSelectionList
				.getSelection();
		if (group != null) {
			List<ProductionUnitProductAreaGroup> list = new ArrayList<ProductionUnitProductAreaGroup>(
					(List<ProductionUnitProductAreaGroup>) presentationModel
							.getBufferedValue(ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST));
			list.remove(group);
			presentationModel
					.getBufferedModel(
							ProductionUnitModel.PROPERTY_PRODUCTION_UNIT_PRODUCT_AREA_GROUP_LIST)
					.setValue(list);
		}
	}
}
