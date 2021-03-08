
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.FakturagrunnlagV;

public interface FakturagrunnlagVManager {
   String MANAGER_NAME = "fakturagrunnlagVManager";

   List<FakturagrunnlagV> findFakturagrunnlag(Integer var1);

   Integer finnBestillingsnrFrakt(Integer var1);
}
