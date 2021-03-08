
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import com.jgoodies.binding.list.ArrayListModel;
import java.util.List;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;

public interface ICostableModel<T, E> extends ModelInterface<E> {
   String PROPERTY_COSTS = "costList";
   String PROPERTY_ORDER = "order";
   String PROPERTY_DEVIATION = "deviation";
   String PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL = "orderLineArrayListModel";
   String PROPERTY_ARTICLES = "articles";

   ArrayListModel getCostList();

   Order getOrder();

   Deviation getDeviation();

   ArrayListModel getOrderLineArrayListModel();

   List<ArticleType> getArticles();

   String getCustomerFirstName();

   String getCustomerLastName();

   String getDeliveryAddress();

   String getPostalCode();

   String getPostOffice();
}
