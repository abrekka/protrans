package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.swing.JDialog;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;
import org.apache.log4j.Logger;

public class FrontProductionApplyList extends ProductionApplyList {
	private static Logger LOGGER = Logger.getLogger(GavlProductionApplyList.class);

	public FrontProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName,
			String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator, List<String> artikler) {
		super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator,artikler);
	}

//	protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
//		OrderManager orderManager = this.managerRepository.getOrderManager();
//		Order order = orderManager.findByOrderNr(object.getOrderNr());
//		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//		if (order != null) {
//			List fronter = order.getOrderLineList("Front");
//
//			try {
//				int antall = 0;
//				OrderLine orderLineFront = null;
//
//				EditPacklistView editPacklistView = null;
//				BigDecimal duration = null;
//				String doneBy = null;
//				OrderLine front;
//				for (Iterator var13 = fronter.iterator(); var13.hasNext(); this.managerRepository.getOrderLineManager()
//						.saveOrderLine(front)) {
//					front = (OrderLine) var13.next();
//					if (front.getOrdNo() != null && front.getOrdNo() != 0 && front.getLnNo() != null) {
//						orderLineFront = front;
//
//					}
//
//					++antall;
//					if (applied) {
//
//						front.setProduced(object.getProduced());
//						this.setProducableApplied(front, aColliName);
//
//						BigDecimal tidsbruk = front.getRealProductionHours();
//
//						if (tidsbruk == null) {
//							tidsbruk = Tidsforbruk.beregnTidsforbruk(front.getActionStarted(), front.getProduced());
//						}
//
//						if (antall == 1 && window != null) {
//							editPacklistView = new EditPacklistView(this.login, false, tidsbruk, front.getDoneBy());
//							JDialog dialog = Util.getDialog(window, "Front produsert", true);
//							WindowInterface window1 = new JDialogAdapter(dialog);
//							window1.add(editPacklistView.buildPanel(window1));
//							window1.pack();
//							Util.locateOnScreenCenter(window1);
//							window1.setVisible(true);
//						}
//
//						if (editPacklistView != null && !editPacklistView.isCanceled()) {
//							duration = editPacklistView.getPacklistDuration();
//							doneBy = editPacklistView.getDoneBy();
//							front.setRealProductionHours(editPacklistView.getPacklistDuration());
//							front.setDoneBy(editPacklistView.getDoneBy());
//						}
//						front.setRealProductionHours(duration);
//						front.setDoneBy(doneBy);
//					} else {
//						this.setProducableUnapplied(front);
//						duration = null;
//						doneBy = null;
//						front.setRealProductionHours((BigDecimal) null);
//						front.setDoneBy((String) null);
//
//					}
//				}
//
//				if (orderLineFront != null) {
//					orderLineFront.setRealProductionHours(duration);
//					orderLineFront.setDoneBy(doneBy);
//					this.lagFerdigmelding(order, orderLineFront, !applied, "Front");
//				}
//			} catch (ProTransException var18) {
//				Util.showErrorDialog(window, "Feil", var18.getMessage());
//				var18.printStackTrace();
//			}
//
//			this.managerRepository.getOrderManager().refreshObject(order);
//			this.managerRepository.getOrderManager().settStatus(order.getOrderId(), (String) null);
//
//			this.applyListManager.refresh(object);
//		}
//	}

//	public void setStarted(Produceable object, boolean started) {
//		if (object != null) {
//			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
//
//			Order order = orderManager.findByOrderNr(object.getOrderNr());
//			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//
//			if (order != null) {
//				List<OrderLine> vegger = order.getOrderLineList("Front");
//				int antall = 0;
//				String gjortAv = "";
//				Date startedDate = Util.getCurrentDate();
//				Iterator var9 = vegger.iterator();
//				while (var9.hasNext()) {
//					OrderLine vegg = (OrderLine) var9.next();
//					++antall;
//					if (vegg != null) {
//						if (started) {
//							vegg.setActionStarted(startedDate);
//							if (antall == 1) {
//								gjortAv = Util.showInputDialogWithdefaultValue((WindowInterface) null, "Gjøres av",
//										"Gjøres av", this.login.getApplicationUser().getFullName());
//							}
//
//							vegg.setDoneBy(gjortAv);
//						} else {
//							vegg.setActionStarted((Date) null);
//							vegg.setDoneBy((String) null);
//						}
//						this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
//
//					}
//				}
//
//				this.managerRepository.getOrderManager().refreshObject(order);
//			}
//			this.applyListManager.refresh(object);
//		}
//	}
}
