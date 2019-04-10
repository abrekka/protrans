package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JDialog;

import org.apache.commons.lang.StringUtils;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

public class VeggProductionApplyList extends ProductionApplyList {

	public VeggProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName,
			String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator) {
		super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator);
	}

	@Override
	public void setStarted(Produceable object, boolean started) {
		if (object != null) {
			OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
			// OrderLineManager orderLineManager = (OrderLineManager)
			// ModelUtil.getBean("orderLineManager");
			Order order = orderManager.findByOrderNr(object.getOrderNr());
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
			// Produceable currentProduceable = getProduceable(object);
			// OrderLine orderLine =
			// managerRepository.getOrderLineManager().findByOrderLineId(currentProduceable.getOrderLineId());
			if (order != null) {
				List<OrderLine> vegger = order.getOrderLineList("Vegg");
				int antall = 0;
				String gjortAv = "";
				Date startedDate = Util.getCurrentDate();
				for (OrderLine vegg : vegger) {
					antall++;
					if (vegg != null) {
						if (started) {
							vegg.setActionStarted(startedDate);
							if (antall == 1) {
								gjortAv = Util.showInputDialogWithdefaultValue(null, "Gjøres av", "Gjøres av",
										login.getApplicationUser().getFullName());
							}
							vegg.setDoneBy(gjortAv);
						} else {
							vegg.setActionStarted(null);
							vegg.setDoneBy(null);
						}
						managerRepository.getOrderLineManager().saveOrderLine(vegg);

						// applyListManager.refresh((Produceable)orderLine);

					}
				}
				managerRepository.getOrderManager().refreshObject(order);
			}
			applyListManager.refresh(object);
		}
	}

	@Override
	protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window,
			final String aColliName) {
		OrderManager orderManager = managerRepository.getOrderManager();
		Order order = orderManager.findByOrderNr(object.getOrderNr());
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
		if (order != null) {
			List<OrderLine> vegger = order.getOrderLineList("Vegg");

			try {
				int antall = 0;
				OrderLine orderLineVegg = null;
				// Integer ordno = null;
				// Integer lnno = null;
				EditPacklistView editPacklistView = null;
				BigDecimal duration = null;
				String doneBy = null;
				for (OrderLine vegg : vegger) {
					if (vegg.getOrdNo() != null && vegg.getOrdNo() != 0 && vegg.getLnNo() != null) {
						orderLineVegg = vegg;
						// ordno = vegg.getOrdNo();
						// lnno = vegg.getLnNo();
					}
					antall++;
					if (applied) {

						vegg.setProduced(object.getProduced());
						setProducableApplied(vegg, aColliName);

						BigDecimal tidsbruk = vegg.getRealProductionHours();

						if (tidsbruk == null) {
							tidsbruk = Tidsforbruk.beregnTidsforbruk(vegg.getActionStarted(), vegg.getProduced());
						}

						if (antall == 1 && window != null) {
							editPacklistView = new EditPacklistView(login, false, tidsbruk, vegg.getDoneBy());
							JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
							WindowInterface window1 = new JDialogAdapter(dialog);
							window1.add(editPacklistView.buildPanel(window1));
							window1.pack();
							Util.locateOnScreenCenter(window1);
							window1.setVisible(true);
						}

						if (editPacklistView != null && !editPacklistView.isCanceled()) {
							duration = editPacklistView.getPacklistDuration();
							doneBy = editPacklistView.getDoneBy();
							vegg.setRealProductionHours(editPacklistView.getPacklistDuration());
							vegg.setDoneBy(editPacklistView.getDoneBy());
						}
						vegg.setRealProductionHours(duration);
						vegg.setDoneBy(doneBy);
					} else {
						setProducableUnapplied(vegg);
						duration = null;
						doneBy = null;
						vegg.setRealProductionHours(null);
						vegg.setDoneBy(null);

					}
					managerRepository.getOrderLineManager().saveOrderLine(vegg);
				}
				if (orderLineVegg != null) {
					orderLineVegg.setRealProductionHours(duration);
					orderLineVegg.setDoneBy(doneBy);
					lagFerdigmelding(order, orderLineVegg, !applied, "Vegg");
				}
			} catch (ProTransException e1) {
				Util.showErrorDialog(window, "Feil", e1.getMessage());
				e1.printStackTrace();
			}

			managerRepository.getOrderManager().refreshObject(order);
			managerRepository.getOrderManager().settStatus(order.getOrderId(), null);
			// order.setStatus(null);
			// managerRepository.getOrderManager().saveOrder(order);
			applyListManager.refresh(object);
		}
	}

	private void lagFerdigmelding(Integer ordno, Integer lnno, boolean minus) {
		Ordln ordln = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(ordno, lnno);

		if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno().intValue() != 0) {
			List<String> fillinjer = new ArrayList<String>();
			fillinjer.add(String.format(OrdchgrHeadV.HEAD_LINE_TMP,
					ordln.getPurcno() != null ? ordln.getPurcno().toString() : "", ""));
			List<Ordln> vegglinjer = managerRepository.getOrdlnManager().findOrdLnByOrdNo(ordln.getPurcno());
			for (Ordln vegg : vegglinjer) {
				if (vegg.getNoinvoab() != null && vegg.getNoinvoab().intValue() > 0) {
					fillinjer.add(lagLinje(vegg, minus));
				}
			}
			try {
				vismaFileCreator.writeFile(ordln.getPurcno().toString(),
						ApplicationParamUtil.findParamByName("visma_out_dir"), fillinjer, 1);
			} catch (IOException e) {
				throw new RuntimeException("Feilet ved skriving av vismafil", e);
			}
		}

	}

	private String lagLinje(Ordln vegg, boolean minus) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(OrdchgrLineV.HEAD_START)
				.append(StringUtils.leftPad("", OrdchgrLineV.NUMBER_OF_SEMICOLONS, ";")).append(OrdchgrLineV.HEAD_END);
		String lineString = StringUtils.replaceOnce(stringBuilder.toString(), OrdchgrLineV.LN_NO_STRING,
				vegg.getLnno() != null ? vegg.getLnno().toString() : "");
		return StringUtils.replaceOnce(lineString, OrdchgrLineV.LINE_STATUS_STRING,
				(minus ? "-" : "") + vegg.getNoinvoab().toString());
	}

}
