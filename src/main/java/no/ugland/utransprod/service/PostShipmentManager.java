
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.Date;
import java.util.List;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.Periode;

public interface PostShipmentManager extends OverviewManager<PostShipment> {
   String MANAGER_NAME = "postShipmentManager";

   List<PostShipment> findAllWithoutTransport();

   void savePostShipment(PostShipment var1);

   void removePostShipment(PostShipment var1);

   List<PostShipment> findAllNotSent();

   void lazyLoad(PostShipment var1, LazyLoadPostShipmentEnum[] var2);

   void lazyLoadTree(PostShipment var1);

   List<PostShipment> findByOrderNr(String var1);

   List<PostShipment> findByCustomerNr(Integer var1);

   void load(PostShipment var1);

   List<PostShipment> findSentInPeriod(Periode var1, String var2);

   PostShipment loadById(Integer var1);

   void settSentDato(PostShipment var1, Date var2);

   void settLevert(PostShipment var1, Date var2);
}
