package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.AttributeChoiceOverviewView;
import no.ugland.utransprod.gui.AttributeDataType;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditAttributeView;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Hjelpeklasse for visning og editering av attributter
 * 
 * @author atle.brekka
 * 
 */
public class AttributeViewHandler extends
		AbstractViewHandler<Attribute, AttributeModel> {
	private static final long serialVersionUID = 1L;

	private NewButton buttonAdd;

	private DeleteButton buttonRemove;

	JButton buttonRemoveChoice;

	JXList listAttributes;
	AttributeChoiceOverviewView attributeChoiceOverviewView = AttributeChoiceOverviewView.UNKNOWN;

	private ManagerRepository managerRepository;
	private Login login;

	/**
	 * @param userType
	 */
	@Inject
	public AttributeViewHandler(Login aLogin,
			ManagerRepository aManagerRepository) {

		super("Attributter", aManagerRepository.getAttributeManager(), aLogin
				.getUserType(), true);
		managerRepository = aManagerRepository;
		login = aLogin;
	}

	/**
	 * Lager tekstfelt for attributtnavn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeModel.PROPERTY_NAME));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldName");
		return textField;
	}

	public JTextField getTextFieldProdCatNo(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeModel.PROPERTY_PROD_CAT_NO));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldProdCatNo");
		return textField;
	}

	public JTextField getTextFieldProdCatNo2(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(AttributeModel.PROPERTY_PROD_CAT_NO_2));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldProdCatNo2");
		return textField;
	}

	public JXList getListAttributes(boolean singleSelection) {
		listAttributes = new JXList(
				getObjectSelectionList(new ListComparator()));
		listAttributes.setName("ListAttributes");

		listAttributes.addMouseListener(getListDoubleclickHandler(window));
		if (singleSelection) {
			listAttributes
					.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		objectSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new EmptySelectionHandler());
		listAttributes.getSelectionModel().addListSelectionListener(
				new AttributeListListener());
		return listAttributes;
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
						.getBufferedModel(AttributeModel.PROPERTY_DESCRIPTION));
		textField.setName("TextFieldDescription");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager komboboks for om attributt har boolskverdi eller ikke
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JCheckBox getCheckBoxYesNo(PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory.createCheckBox(
				presentationModel
						.getBufferedModel(AttributeModel.PROPERTY_YES_NO),
				"Ja/Nei");
		checkBox.setEnabled(hasWriteAccess());
		return checkBox;
	}

	/**
	 * Lager sjekkboks for spesialt hensyn
	 * 
	 * @param presentationModel
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxSpecialConcern(
			PresentationModel presentationModel) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel
								.getBufferedModel(AttributeModel.PROPERTY_SPECIAL_CONCERN),
						"Spesielt hensyn");
		checkBox.setEnabled(hasWriteAccess());
		return checkBox;
	}

	public JComponent buildAttributeChoicePanel(
			PresentationModel presentationModel, WindowInterface window) {
		attributeChoiceOverviewView = new AttributeChoiceOverviewView(
				new AttributeChoiceViewHandler(login, managerRepository,
						(AttributeModel) presentationModel.getBean()));
		return attributeChoiceOverviewView.buildPanel(window);
	}

	/**
	 * Henter handler for dobbeltklikk i liste oer attributter
	 * 
	 * @param window
	 * @return muselytter
	 */
	public MouseListener getListDoubleclickHandler(WindowInterface window) {
		return new ListDoubleClickHandler(window);
	}

	/**
	 * Henter ok-knapp
	 * 
	 * @param aWindow
	 * @return knapp
	 */
	public JButton getOkButton(WindowInterface aWindow) {
		JButton buttonOk = new CancelButton(aWindow, this, false, "Ok",
				IconEnum.ICON_OK, null, true);
		buttonOk.setName("CancelAttribute");
		return buttonOk;
	}

	/**
	 * Henter knapp for å legge til attributt
	 * 
	 * @param aWindow
	 * @return knapp
	 */
	public JButton getAddAttributeButton(WindowInterface aWindow) {
		buttonAdd = new NewButton("attributt", this, aWindow);
		buttonAdd.setName("AddAttribute");
		buttonAdd.setEnabled(hasWriteAccess());
		return buttonAdd;
	}

	/**
	 * Henter knapp for å fjerne attributt
	 * 
	 * @param aWindow
	 * @return knapp
	 */
	public JButton getRemoveAttributeButton(WindowInterface aWindow) {
		buttonRemove = new DeleteButton("attributt", this, aWindow);
		buttonRemove.setName("RemoveAttribute");
		buttonRemove.setEnabled(hasWriteAccess());
		return buttonRemove;
	}

	/**
	 * @param object
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Attribute object) {
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
	public CheckObject checkSaveObject(AttributeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "attributt";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Attribute";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Attribute getNewObject() {
		return new Attribute();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new AttributeTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "200dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Attributter";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 300);
	}

	@Override
	void afterSaveObject(Attribute object, WindowInterface window) {
		enableChoices(!Util.convertNumberToBoolean(object.getYesNo()));
		if (window == null
				|| !Util
						.showConfirmDialog(
								window.getComponent(),
								"Oppdatere order?",
								"Dersom spesielt hensyn er oppdatert bør alle ordre oppdateres. Ønsker du å gjøre det?")) {
			return;
		}

		Util.runInThreadWheel(window.getRootPane(), new UpdateOrderConcern(
				window), null);

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
	}

	/**
	 * Håndterer dobbeltklikk i liste over attributter
	 * 
	 * @author atle.brekka
	 * 
	 */
	final class ListDoubleClickHandler extends MouseAdapter {
		/**
		 *
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ListDoubleClickHandler(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)

				window.dispose();

		}
	}

	/**
	 * Tabellmodell for attributter
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class AttributeTableModel extends AbstractTableAdapter {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		/**
		 *
		 */
		private static final String[] COLUMNS = { "Navn", "Beskrivelse",
				"Ja/Nei", "Spesielt hensyn" };

		/**
		 * @param listModel
		 */
		public AttributeTableModel(ListModel listModel) {
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
			Attribute attribute = (Attribute) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return attribute.getName();
			case 1:
				return attribute.getDescription();
			case 2:
				return Util.convertNumberToBoolean(attribute.getYesNo());
			case 3:
				return Util.convertNumberToBoolean(attribute
						.getSpecialConcern());
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
			case 3:
				return Boolean.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Håndterer oppdtering av spesielt hensyn
	 * 
	 * @author atle.brekka
	 * 
	 */
	class UpdateOrderConcern implements Threadable {
		/**
		 *
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public UpdateOrderConcern(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			labelInfo.setText("Oppdaterer ordre...");
			OrderManager orderManager = (OrderManager) ModelUtil
					.getBean("orderManager");
			Set<Order> orders = orderManager.findNotSent();
			int counter = 1;
			if (orders != null) {
				for (Order order : orders) {
					labelInfo.setText("Oppdaterer ordre..." + counter);
					orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
							LazyLoadOrderEnum.ORDER_LINES,
							LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
					order
							.setSpecialConcern(order
									.getOrderLineSpecialConcerns());
					try {
						orderManager.saveOrder(order);
					} catch (ProTransException e) {
						Util.showErrorDialog(window, "Feil", e.getMessage());
						e.printStackTrace();
					}
					counter++;
				}
			}
			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Attributter");
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
	protected AbstractEditView<AttributeModel, Attribute> getEditView(
			AbstractViewHandler<Attribute, AttributeModel> handler,
			Attribute object, boolean searching) {
		return new EditAttributeView(this, object, searching);
	}

	/**
	 * Klasse for sammenlikning av attributter i liste (sortering)
	 * 
	 * @author atle.brekka
	 * 
	 */
	static class ListComparator implements Comparator<Attribute>, Serializable {

		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
		 * @param att1
		 * @param att2
		 * @return mindre enn, lik, større enn
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(Attribute att1, Attribute att2) {
			return att1.getName().compareTo(att2.getName());
		}

	}

	/**
	 * Setter knapper enablet/disablet
	 */
	void updateEnablement() {
		boolean hasSelction = objectSelectionList.hasSelection();
		if (hasSelction) {
			buttonEdit.setEnabled(true);
			buttonRemove.setEnabled(true);
		} else {
			buttonEdit.setEnabled(false);
			buttonRemove.setEnabled(false);
		}
	}

	/**
	 * Klasse som oppdaterer knapper når det velges attributt
	 * 
	 * @author atle.brekka
	 * 
	 */
	class EmptySelectionHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			updateEnablement();

		}

	}

	/**
	 * Klasse som håndterer oppdatering av selsksjonsliste ved valg av
	 * sttributt(er)
	 * 
	 * @author atle.brekka
	 * 
	 */
	class AttributeListListener implements ListSelectionListener {

		/**
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged(ListSelectionEvent arg0) {
			updateSelection();

		}

	}

	/**
	 * Oppdaterer seleksjonsliste
	 */
	void updateSelection() {
		if (listAttributes.getSelectedIndices().length != 1) {
			objectSelectionList.clearSelection();
		} else {
			objectSelectionList.setSelection(listAttributes.getSelectedValue());
		}
	}

	@Override
	String getAddString() {
		return null;
	}

	public void enableChoices(boolean enable) {
		attributeChoiceOverviewView.enableComponents(enable);
	}

	public JComboBox getComboBoxDataType(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						AttributeDataType.getStringValues(),
						presentationModel
								.getBufferedModel(AttributeModel.PROPERTY_ATTRIBUTE_DATA_TYPE)));
		comboBox.setName("ComboBoxDataType");
		return comboBox;
	}

}
