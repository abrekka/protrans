package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCostTypeView;
import no.ugland.utransprod.gui.model.CostTypeModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Hjelpeklasse for visning av kostnadstype
 * 
 * @author atle.brekka
 * 
 */
public class CostTypeViewHandler extends
DefaultAbstractViewHandler<CostType, CostTypeModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public CostTypeViewHandler(Login login,CostTypeManager costTypeManager) {
		super("Kostnadstyper", costTypeManager, login.getUserType(), true);
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
						.getBufferedModel(CostTypeModel.PROPERTY_COST_TYPE_NAME));
		textField.setName("CostTypeName");
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
						.getBufferedModel(CostTypeModel.PROPERTY_COST_TYPE_DESCRIPTION));
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
	public CheckObject checkDeleteObject(CostType object) {
		if (object.getOrderCosts() != null
				&& object.getOrderCosts().size() != 0) {
			return new CheckObject("Kan ikke slette kostnadstype som brukes av ordre",false);
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
	public CheckObject checkSaveObject(CostTypeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "kostnadstype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "CostType";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public CostType getNewObject() {
		return new CostType();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new CostTypeTableModel(objectSelectionList);
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
		return "Kostnadstyper";
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
		if(table.getColumnCount()>0){
		// Navn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(80);

		// Beskrivelse
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(150);
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Kostnadstyper");
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
	protected AbstractEditView<CostTypeModel, CostType> getEditView(
			AbstractViewHandler<CostType, CostTypeModel> handler,
			CostType object, boolean searching) {
		return new EditCostTypeView(this, object, searching);
	}

    

}
