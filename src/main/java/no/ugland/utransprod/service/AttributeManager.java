
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.Attribute;

public interface AttributeManager extends OverviewManager<Attribute> {
   String MANAGER_NAME = "attributeManager";

   void saveAttribute(Attribute var1);

   void removeAttribute(Attribute var1);

   Attribute findByName(String var1);

   List<Attribute> findAll();
}
