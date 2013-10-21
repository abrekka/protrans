package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCustomerView;
import no.ugland.utransprod.gui.model.CustomerModel;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Hjelpeklasse for visning og bhandling av kunder
 * 
 * @author atle.brekka
 * 
 */
public class CustomersViewHandler extends
DefaultAbstractViewHandler<Customer, CustomerModel> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param userType
	 */
	public CustomersViewHandler(Login login,CustomerManager customerManager) {
		super("Kunder", customerManager, login.getUserType(), false);
	}

	/**
	 * Lager tekstfelt for kundenummer
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldCustomerNr(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createIntegerField(presentationModel
						.getBufferedModel(CustomerModel.PROPERTY_CUSTOMER_NR));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldCustomerNr");
		return textField;
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
						.getBufferedModel(CustomerModel.PROPERTY_FIRST_NAME));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldFirstName");
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
						.getBufferedModel(CustomerModel.PROPERTY_LAST_NAME));
		textField.setEnabled(hasWriteAccess());
		textField.setName("TextFieldLastName");
		return textField;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Customer getNewObject() {
		return new Customer();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new CustomerTableModel(objectSelectionList);
	}

	/**
	 * Tabellmodell for visning av kunder i tabell
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class CustomerTableModel extends AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Kundenr", "Fornavn",
				"Etternavn" };

		/**
		 * @param listModel
		 */
		CustomerTableModel(ListModel listModel) {
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
			Customer customer = (Customer) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return customer.getCustomerNr();
			case 1:
				return customer.getFirstName();
			case 2:
				return customer.getLastName();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * Henter kolonneklasse
		 * 
		 * @param columnIndex
		 * @return kolonneklasse
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Integer.class;
			case 1:
			case 2:
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Kunder";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "140dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(390, 260);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Kundenr
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(65);

		// fornavn
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(80);

		// etternavn
		col = table.getColumnModel().getColumn(2);
		col.setPreferredWidth(80);

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
	public CheckObject checkSaveObject(CustomerModel object,
			PresentationModel presentationModel, WindowInterface window) {
		String errorString = null;
		Customer customer = (object).getObject();
		if (customer.getCustomerId() == null && objectList.contains(customer)) {
			errorString = "Kan ikke lagre kunde med et kundenummer som finnes fra før";
		}
		return new CheckObject(errorString,false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "kunde";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Customer";
	}

	/**
	 * @param object
	 * @return feilstreng dersom feil
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Customer object) {
		String errorString = null;
        overviewManager.lazyLoad(object, new LazyLoadEnum[][]{{LazyLoadEnum.ORDERS,LazyLoadEnum.NONE}});
		if (object.getOrders() != null && object.getOrders().size() != 0) {
			errorString = "Kan ikke slette kunde som har ordre";
		}
		return new CheckObject(errorString,false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Kunde");
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
	protected AbstractEditView<CustomerModel, Customer> getEditView(
			AbstractViewHandler<Customer, CustomerModel> handler,
			Customer object, boolean searching) {
		return new EditCustomerView(this, object, searching);
	}

    

}
