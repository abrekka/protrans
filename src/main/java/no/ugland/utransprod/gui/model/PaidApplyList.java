/*    */ package no.ugland.utransprod.gui.model;

/*    */
/*    */ import com.google.inject.Inject;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import no.ugland.utransprod.ProTransException;
/*    */ import no.ugland.utransprod.gui.Login;
/*    */ import no.ugland.utransprod.gui.WindowInterface;
/*    */ import no.ugland.utransprod.model.Order;
/*    */ import no.ugland.utransprod.model.PaidV;
/*    */ import no.ugland.utransprod.service.OrderManager;
/*    */ import no.ugland.utransprod.service.PaidVManager;
/*    */ import no.ugland.utransprod.service.VismaFileCreator;
/*    */ import no.ugland.utransprod.util.ModelUtil;
/*    */ import no.ugland.utransprod.util.UserUtil;
/*    */ import no.ugland.utransprod.util.Util;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class PaidApplyList extends AbstractApplyList<PaidV> {
	/*    */ @Inject
	/*    */ public PaidApplyList(Login login, PaidVManager manager) {
		/* 33 */ super(login, manager, (VismaFileCreator) null);
		/* 34 */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public void setApplied(PaidV object, boolean applied, WindowInterface window) {
		/* 44 */ OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		/* 45 */ Order order = orderManager.findByOrderNr(object.getOrderNr());
		/* 46 */ if (applied) {
			/* 47 */ Date invoiceDate = Util.getDate(window);
			/* 48 */ order.setPaidDate(invoiceDate);
			/*    */ } else {
			/* 50 */ order.setPaidDate((Date) null);
			/*    */ }
		/*    */ try {
			/* 53 */ orderManager.saveOrder(order);
			/* 54 */ } catch (ProTransException var7) {
			/* 55 */ Util.showErrorDialog(window, "Feil", var7.getMessage());
			/* 56 */ var7.printStackTrace();
			/*    */ }
		/* 58 */ this.applyListManager.refresh(object);
		/*    */
		/* 60 */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public boolean hasWriteAccess() {
		/* 66 */ return UserUtil.hasWriteAccess(this.login.getUserType(), "Betaling");
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public PaidV getApplyObject(Transportable transportable, WindowInterface window) {
		/* 73 */ List<PaidV> list = this.applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());
		/* 74 */ return list != null && list.size() == 1 ? (PaidV) list.get(0) : null;
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public void setStarted(PaidV object, boolean started) {
		/* 83 */ }

	public void setPause(PaidV object, boolean started) {
		/* 83 */ }

	/*    */
	/*    */
	/*    */
	/*    */ public void setRealProductionHours(PaidV object, BigDecimal overstyrtTidsforbruk) {
		/* 88 */ }
	/*    */ }
