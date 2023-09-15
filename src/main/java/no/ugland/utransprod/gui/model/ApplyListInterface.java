
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import com.jgoodies.binding.list.SelectionInList;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.table.TableModel;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionUnit;
import org.jdesktop.swingx.JXTable;

public interface ApplyListInterface<T extends Applyable> {
   Collection<T> getObjectLines();

   void setApplied(T var1, boolean var2, WindowInterface var3);
   void setPause(T var1, boolean var2);

   boolean hasWriteAccess();

   ReportEnum getReportEnum();

   TableModel getTableModelReport(ListModel var1, JXTable var2, SelectionInList var3);

   void setInvisibleColumns(JXTable var1);

   void refresh(T var1);

   T getApplyObject(Transportable var1, WindowInterface var2);

   void setStarted(T var1, boolean var2);

   void setRealProductionHours(T var1, BigDecimal var2);

   void setProductionUnit(ProductionUnit var1, T var2, WindowInterface var3);

   List<T> doSearch(String var1, String var2, ProductAreaGroup var3) throws ProTransException;
}
