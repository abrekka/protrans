/*     */ package no.ugland.utransprod.gui.model;

/*     */
/*     */ import com.jgoodies.binding.list.SelectionInList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import javax.swing.ListModel;
/*     */ import javax.swing.table.TableModel;
/*     */ import no.ugland.utransprod.ProTransException;
/*     */ import no.ugland.utransprod.gui.Login;
/*     */ import no.ugland.utransprod.gui.WindowInterface;
/*     */ import no.ugland.utransprod.model.OrderLine;
/*     */ import no.ugland.utransprod.model.ProductAreaGroup;
/*     */ import no.ugland.utransprod.model.ProductionUnit;
/*     */ import no.ugland.utransprod.service.IApplyListManager;
/*     */ import no.ugland.utransprod.service.VismaFileCreator;
/*     */ import org.jdesktop.swingx.JXTable;

/*     */ public abstract class AbstractApplyList<T extends Applyable> implements ApplyListInterface<T> {
	/*     */ protected Login login;
	/*     */ protected IApplyListManager<T> applyListManager;
	/*     */ protected VismaFileCreator vismaFileCreator;

	/*     */
	/*     */ public AbstractApplyList(Login aLogin, IApplyListManager<T> manager, VismaFileCreator aVismaFileCreator) {
		/* 41 */ this.login = aLogin;
		/* 42 */ this.applyListManager = manager;
		/* 43 */ this.vismaFileCreator = aVismaFileCreator;

		/* 45 */ }

	
	public Collection<T> getObjectLines() {
		List<T> lines = this.applyListManager.findAllApplyable();
		this.sortObjectLines(lines);
		return lines;
	}

	/*     */ public List<T> doSearch(String orderNr, String customerNr, ProductAreaGroup productAreaGroup)
			throws ProTransException {
		/* 67 */ if (orderNr != null) {
			/* 68 */ return this.applyListManager.findApplyableByOrderNrAndProductAreaGroup(orderNr,
					(ProductAreaGroup) null);
			/* 69 */ } else if (customerNr != null) {
			/*     */ try {
				/* 71 */ return this.applyListManager.findApplyableByCustomerNrAndProductAreaGroup(
						Integer.valueOf(customerNr), (ProductAreaGroup) null);
				/* 72 */ } catch (NumberFormatException var5) {
				/* 73 */ var5.printStackTrace();
				/* 74 */ throw new ProTransException("Kundenr må være tall");
				/*     */ }
			/*     */ } else {
			/* 77 */ return null;
			/*     */ }
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public ReportEnum getReportEnum() {
		/* 86 */ return null;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public TableModel getTableModelReport(ListModel listModel, JXTable table,
			SelectionInList objectSelectionList) {
		/* 97 */ return null;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void setInvisibleColumns(JXTable table) {
		/* 107 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ protected void sortObjectLines(List<T> lines) {
		/* 116 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void refresh(T object) {
		/* 123 */ this.applyListManager.refresh(object);
		/* 124 */ }

	/*     */
	/*     */ public void setProductionUnit(ProductionUnit productionUnit, T object, WindowInterface window) {
		/* 127 */ }

	/*     */
	/*     */ protected String createVismaFile(List<OrderLine> orderLines) throws ProTransException {
		/* 130 */ return this.vismaFileCreator != null ? this.vismaFileCreator.createVismaFile(orderLines, 1, false)
				: null;
		/*     */ }

	/*     */
	/*     */
	/*     */ protected boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window, boolean isMainArticle) {
		/* 135 */ return !isMainArticle ? true
				: (this.vismaFileCreator != null ? this.vismaFileCreator.ignoreVismaFile(orderLine, window) : true);
		/*     */ }
	/*     */ }
