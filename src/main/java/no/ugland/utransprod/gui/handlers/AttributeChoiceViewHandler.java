package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditAttributeChoiceView;
import no.ugland.utransprod.gui.model.AttributeChoiceModel;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.validation.ValidationResultModel;

public class AttributeChoiceViewHandler extends
		AbstractViewHandler<AttributeChoice, AttributeChoiceModel> {
	private static final long serialVersionUID = 1L;
	private AttributeModel attributeModel;
	private ManagerRepository managerRepository;

	@Inject
	public AttributeChoiceViewHandler(Login aLogin,
			ManagerRepository aManagerRepository,
			@Assisted final AttributeModel aAttributeModel) {
		super("Attributtvalg", aManagerRepository.getAttributeChoiceManager(),
				false, aLogin.getUserType(), true);
		this.managerRepository = aManagerRepository;
		attributeModel = aAttributeModel;
	}

	public JTextField getTextFieldChoice(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeChoiceModel.PROPERTY_CHOICE_VALUE));
		textField.setName("TextFieldChoiceValue");
		return textField;
	}

	public JTextField getTextFieldProdCatNo(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeChoiceModel.PROPERTY_PROD_CAT_NO));
		textField.setName("TextFieldProdCatNo");
		return textField;
	}

	public JTextField getTextFieldProdCatNo2(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeChoiceModel.PROPERTY_PROD_CAT_NO_2));
		textField.setName("TextFieldProdCatNo2");
		return textField;
	}

	public JTextArea getTextAreaComment(PresentationModel presentationModel) {
		JTextArea textArea = BasicComponentFactory
				.createTextArea(presentationModel
						.getBufferedModel(AttributeChoiceModel.PROPERTY_COMMENT));
		textArea.setName("TextAreaComment");
		return textArea;
	}

	public JButton getButtonOk(ValidationResultModel aValidationResultModel,
			PresentationModel aPresentationModel, WindowInterface aWindow) {
		JButton button = new JButton(new OkAction(aValidationResultModel,
				aPresentationModel, aWindow));
		button.setName("ButtonOk");
		button.setIcon(IconEnum.ICON_OK.getIcon());
		return button;
	}

	@Override
	public CheckObject checkDeleteObject(AttributeChoice object) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<AttributeChoiceModel, AttributeChoice> getEditView(
			AbstractViewHandler<AttributeChoice, AttributeChoiceModel> handler,
			AttributeChoice object, boolean searching) {
		object.setAttribute(attributeModel.getObject());
		return new EditAttributeChoiceView(searching, object, this);
	}

	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Attributter");
	}

	@Override
	void afterSaveObject(AttributeChoice object, WindowInterface window) {
		// TODO Auto-generated method stub

	}

	@Override
	public CheckObject checkSaveObject(AttributeChoiceModel object,
			PresentationModel presentationModel, WindowInterface window) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAddRemoveString() {
		return "valg";
	}

	@Override
	String getAddString() {
		return "Nytt";
	}

	@Override
	public String getClassName() {
		return "AttributeChoice";
	}

	@Override
	public AttributeChoice getNewObject() {
		return new AttributeChoice();
	}

	@Override
	public TableModel getTableModel(WindowInterface window1) {
		return new AttributeChoiceTableModel(objectSelectionList);
	}

	@Override
	public String getTableWidth() {
		return "100dlu";
	}

	@Override
	public String getTitle() {
		return "Valg";
	}

	@Override
	public Dimension getWindowSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColumnWidth(JXTable table) {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void initObjects() {
		if (!loaded) {
			setFiltered(false);
			objectSelectionList.clearSelection();
			objectList.clear();

			List<AttributeChoice> choices = attributeModel.getChoiceList();

			if (choices != null) {
				objectList.addAll(choices);
			}
			noOfObjects = objectList.getSize();

			if (table != null) {
				table.scrollRowToVisible(0);
			}
		}
	}

	public static final class AttributeChoiceTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Valg", "ProdCatNo",
				"ProdCatNo2" };

		/**
		 * @param listModel
		 */
		public AttributeChoiceTableModel(ListModel listModel) {
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
			AttributeChoice attributeChoice = (AttributeChoice) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return attributeChoice.getChoiceValue();
			case 1:
				return attributeChoice.getProdCatNo();
			case 2:
				return attributeChoice.getProdCatNo2();
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
			case 2:
				return Integer.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	private class OkAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private ValidationResultModel validationResultModel;
		private PresentationModel presentationModel;
		private WindowInterface window;

		public OkAction(ValidationResultModel aValidationResultModel,
				PresentationModel aPresentationModel, WindowInterface aWindow) {
			super("Ok");
			validationResultModel = aValidationResultModel;
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			if (validationResultModel.hasErrors()) {
				Util.showErrorDialog(window, "Rett feil",
						"Rett alle feil før lagring!");
			} else {
				AttributeChoiceModel model = (AttributeChoiceModel) presentationModel
						.getBean();
				AttributeChoiceModel bufferedModel = model
						.getBufferedObjectModel(presentationModel);

				CheckObject checkObject = checkSaveObject(bufferedModel,
						presentationModel, window);

				String msg = null;
				boolean canSave = false;
				if (checkObject != null) {
					msg = checkObject.getMsg();
				}
				if (msg == null || msg.length() == 0) {
					canSave = true;
				} else {
					canSave = handleCheckObject(window, checkObject, msg);

				}

				if (canSave) {
					presentationModel.triggerCommit();
					bufferedModel.viewToModel();
					attributeModel.getObject()
							.addAttributeChoice(
									((AttributeChoiceModel) presentationModel
											.getBean()).getObject());
					managerRepository.getAttributeManager().saveAttribute(
							attributeModel.getObject());
					saveObject(
							(AttributeChoiceModel) presentationModel.getBean(),
							window);
				}
			}
			window.dispose();
		}
	}

	public boolean doDelete(WindowInterface window) {
		boolean returnValue = true;
		int selectedIndex = getSelectedIndex();

		if (selectedIndex != -1) {
			AttributeChoice attributeChoice = (AttributeChoice) objectSelectionList
					.getElementAt(selectedIndex);
			CheckObject checkObject = checkDeleteObject(attributeChoice);
			String msg = null;
			if (checkObject != null) {
				msg = checkObject.getMsg();
			}

			if (msg == null) {
				AttributeManager attributeManager = (AttributeManager) ModelUtil
						.getBean(AttributeManager.MANAGER_NAME);
				attributeModel.getObject().removeAttributeChoice(
						attributeChoice);
				attributeManager.saveAttribute(attributeModel.getObject());
				objectList.remove(selectedIndex);
				noOfObjects--;
			} else {
				returnValue = handleDeleteCheckObject(window, returnValue,
						selectedIndex, attributeChoice, checkObject, msg);

			}

		}
		return returnValue;
	}

	private boolean handleCheckObject(WindowInterface window,
			CheckObject checkObject, String msg) {
		boolean returnValue;
		if (checkObject.canContinue()) {
			returnValue = handleCanContinue(window, msg);
		} else {
			returnValue = false;
			Util.showErrorDialog((Component) null, "Feil", msg);
		}
		return returnValue;
	}

	private boolean handleCanContinue(WindowInterface window, String msg) {
		boolean doSave = Util.showConfirmDialog(window.getComponent(),
				"Slette?", msg + " Vil du slette?");
		return doSave;
	}
}
