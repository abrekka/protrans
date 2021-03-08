/*     */ package no.ugland.utransprod.gui.handlers;
/*     */ 
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import java.awt.Dimension;
/*     */ import javax.swing.table.TableModel;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.model.UserType;
/*     */ import no.ugland.utransprod.service.OverviewManager;
/*     */ import org.jdesktop.swingx.JXTable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractViewHandlerShort<T, E> extends AbstractViewHandler<T, E> {
/*     */    public AbstractViewHandlerShort(String aHeading, OverviewManager<T> aOverviewManager, boolean oneObject, UserType aUserType, boolean useDisposeOnClose) {
/*  35 */       super(aHeading, aOverviewManager, oneObject, aUserType, useDisposeOnClose);
/*  36 */    }
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
/*     */    public final CheckObject checkSaveObject(E object, PresentationModel presentationModel, WindowInterface window1) {
/*  53 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final T getNewObject() {
/*  63 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */    public final TableModel getTableModel(WindowInterface window1) {
/*  68 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getTableWidth() {
/*  76 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final String getTitle() {
/*  84 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final Dimension getWindowSize() {
/*  92 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    public final void setColumnWidth(JXTable table) {
/* 100 */    }
/*     */ 
/*     */ 
/*     */    String getAddString() {
/* 104 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getAddRemoveString() {
/* 110 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */    public String getClassName() {
/* 116 */       return null;
/*     */    }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */    void afterSaveObject(T object, WindowInterface window) {
/* 123 */    }
/*     */ }
