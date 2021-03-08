
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface NokkelProduksjonVManager extends ExcelManager, NokkelVManager<NokkelProduksjonV> {
   NokkelProduksjonV findByWeek(Integer var1, Integer var2);
}
