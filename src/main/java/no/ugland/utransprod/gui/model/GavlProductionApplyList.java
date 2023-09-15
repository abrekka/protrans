package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
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
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class GavlProductionApplyList extends ProductionApplyList {
	private static Logger LOGGER = Logger.getLogger(GavlProductionApplyList.class);

	public GavlProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName,
			String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator,List<String> artikler) {
		super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator,artikler);
	}

//	protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
//		OrderLineManager orderLineManager = this.managerRepository.getOrderLineManager();
//		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
//		if (orderLine != null) {
//			try {
//				Order order = this.managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());
//				this.managerRepository.getOrderManager().lazyLoadOrder(order,
//						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//
//				List<OrderLine> orderLineList = order.getOrderLineList("Gavl");
//
//				OrderLine orderLineGavl = null;
//				int antall = 0;
//				BigDecimal duration = null;
//				String doneBy = null;
//				OrderLine gavl;
//				for (Iterator var13 = orderLineList.iterator(); var13.hasNext(); this.managerRepository
//						.getOrderLineManager().saveOrderLine(gavl)) {
//					gavl = (OrderLine) var13.next();
//					if (gavl.getOrdNo() != null && gavl.getOrdNo() != 0 && gavl.getLnNo() != null) {
//						orderLineGavl = gavl;
//					}
//					++antall;
//					if (applied) {
//						gavl.setProduced(object.getProduced());
//						this.setProducableApplied(gavl, aColliName);
//
//						BigDecimal tidsbruk = gavl.getRealProductionHours();
//
//						if (tidsbruk == null) {
//							tidsbruk = Tidsforbruk.beregnTidsforbruk(gavl.getActionStarted(), gavl.getProduced());
//						}
//
//						if (antall == 1 && window != null) {
//							EditPacklistView editPacklistView = new EditPacklistView(this.login, false, tidsbruk,
//									gavl.getDoneBy());
//
//							JDialog dialog = Util.getDialog(window, "Gavl produsert", true);
//							WindowInterface window1 = new JDialogAdapter(dialog);
//							window1.add(editPacklistView.buildPanel(window1));
//							window1.pack();
//							Util.locateOnScreenCenter(window1);
//							window1.setVisible(true);
//
//							if (!editPacklistView.isCanceled()) {
//								duration = editPacklistView.getPacklistDuration();
//								doneBy = editPacklistView.getDoneBy();
//								gavl.setRealProductionHours(editPacklistView.getPacklistDuration());
//								gavl.setDoneBy(editPacklistView.getDoneBy());
//							}
//						}
//
//						gavl.setRealProductionHours(duration);
//						gavl.setDoneBy(doneBy);
//					} else {
//						this.setProducableUnapplied(gavl);
//						duration = null;
//						doneBy = null;
//						gavl.setRealProductionHours((BigDecimal) null);
//						gavl.setDoneBy((String) null);
//
//					}
//				}
//
//				if (orderLineGavl != null) {
//					orderLineGavl.setRealProductionHours(duration);
//					orderLineGavl.setDoneBy(doneBy);
//					this.lagFerdigmelding(order, orderLineGavl, !applied, "Gavl");
//				} else {
//					LOGGER.info("Lager ikke ferdigmelding fordi ordrelinje mangler ordnno");
//				}
//			} catch (ProTransException var19) {
//				Util.showErrorDialog(window, "Feil", var19.getMessage());
//				var19.printStackTrace();
//			}
//			this.managerRepository.getOrderManager().settStatus(orderLine.getOrder().getOrderId(), (String) null);
//
//			this.applyListManager.refresh(object);
//		}
//	}

	private String lagLinje(Ordln vegg, boolean minus) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("L;$lnNo").append(StringUtils.leftPad("", 71, ";")).append("1;1;$lineStatus;3");

		String lineString = StringUtils.replaceOnce(stringBuilder.toString(), "$lnNo",
				vegg.getLnno() != null ? vegg.getLnno().toString() : "");

		return StringUtils.replaceOnce(lineString, "$lineStatus", (minus ? "-" : "") + vegg.getNoinvoab().toString());
	}
}
