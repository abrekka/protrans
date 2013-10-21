/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import javax.swing.ListModel;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Tabellmodell for pakkliste
 * 
 * @author atle.brekka
 * 
 */
final class PacklistTableModel extends AbstractTableAdapter {
	private static final long serialVersionUID = 1L;

	private Integer articleCount;
    private PostShipment postShipment;

	/**
	 * @param listModel
	 * @param aArticleCount
	 */
	public PacklistTableModel(ListModel listModel, Integer aArticleCount,final PostShipment aPostShipment) {
		super(listModel, new String[] { "address", "first_name", "last_name",
				"postal_code", "post_office", "order_nr", "customer_nr",
				"phone_nr", "construction_type", "number_of_articles",
				"article_name", "article_details","packlist_comment" });
		articleCount = aArticleCount;
        postShipment=aPostShipment;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int row, int column) {
		OrderLine orderLine = (OrderLine) getRow(row);

		Order order = orderLine.getOrder();
        

		switch (column) {
		case 0:
			return order.getDeliveryAddress();
		case 1:
			return order.getCustomer().getFirstName();
		case 2:
			return order.getCustomer().getLastName();
		case 3:
			return order.getPostalCode();
		case 4:
			return order.getPostOffice();
		case 5:
			return order.getOrderNr();
		case 6:
			return order.getCustomer().getCustomerNr();
		case 7:
			return order.getTelephoneNr();
		case 8:
			return "Etterlevering";
		case 9:
			return articleCount;
		case 10:
			return Util.upperFirstLetter(orderLine.getArticleName());
		case 11:
			return orderLine.getDetails();
        case 12:
            return postShipment.getPacklistComments();
		default:
			throw new IllegalStateException("Unknown column");
		}
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		switch (column) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 7:
		case 8:
		case 10:
		case 11:

			return String.class;
		case 6:
		case 9:
			return Integer.class;
		default:
			throw new IllegalStateException("Unknown column");
		}
	}

}