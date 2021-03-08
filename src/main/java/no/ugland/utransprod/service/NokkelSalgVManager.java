
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.NokkelSalgV;

public interface NokkelSalgVManager extends NokkelVManager<NokkelSalgV> {
   List<NokkelSalgV> findByYearWeek(Integer var1, Integer var2, String var3);
}
