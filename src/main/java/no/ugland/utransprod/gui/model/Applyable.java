
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Ordln;

public interface Applyable {
   String getOrderNr();

   Boolean isForPostShipment();

   Integer getOrderLineId();

   void setOrdln(Ordln var1);

   Ordln getOrdln();

   String getArticleName();

   Colli getColli();

   void setProduced(Date var1);

   Date getProduced();

   void setColli(Colli var1);

   List<Applyable> getRelatedArticles();

   void setRelatedArticles(List<Applyable> var1);

   boolean isRelatedArticlesComplete();

   Integer getNumberOfItems();

   String getProductionUnitName();

   BigDecimal getRealProductionHours();

   Date getActionStarted();
}
