/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.google.inject.Inject;
/*     */ import com.google.inject.assistedinject.Assisted;
/*     */ import javax.swing.table.TableCellRenderer;
/*     */ import javax.swing.table.TableModel;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.model.ApplyListInterface;
/*     */ import no.ugland.utransprod.gui.model.Applyable;
/*     */ import no.ugland.utransprod.gui.model.ReportEnum;
/*     */ import no.ugland.utransprod.service.ManagerRepository;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractProductionPackageViewHandlerShort<T extends Applyable> extends AbstractProductionPackageViewHandler<T> {
/*     */    @Inject
/*     */    public AbstractProductionPackageViewHandlerShort(Login login, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory, @Assisted ApplyListInterface<T> productionInterface, @Assisted String title, @Assisted TableEnum tableEnum) {
/*  32 */       super(login, managerRepository, deviationViewHandlerFactory, productionInterface, title, tableEnum);
/*     */ 
/*  34 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected void setInvisibleCells() {
/*  43 */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected final TableModel getTableModelReport() {
/*  52 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected final TableCellRenderer getSpecificationCellRenderer() {
/*  57 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected final int getSpecificationCell() {
/*  67 */       return -1;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    protected final ReportEnum getReportEnum() {
/*  77 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected void checkLazyLoad(T object) {
/*  82 */    }
/*     */ 
/*     */ 
/*     */    protected final String getNotStartText() {
/*  86 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected final String getStartText() {
/*  91 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected final void setStarted(T object, boolean started) {
/*  96 */    }

protected final void setPause(T object, boolean started) {
/*  96 */    }
/*     */ 
/*     */ 
/*     */    protected final boolean getButtonStartEnabled(T object) {
/* 100 */       return false;
/*     */    }
/*     */ 
/*     */ 
/*     */    protected final Integer getStartColumn() {
/* 105 */       return null;
/*     */    }
/*     */ }
