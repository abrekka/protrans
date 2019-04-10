package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.collect.Iterables;

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
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

public class FrontProductionApplyList extends ProductionApplyList {
	private static Logger LOGGER = Logger.getLogger(GavlProductionApplyList.class);

	public FrontProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName,
			String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator) {
		super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator);
	}

	@Override
	protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window,
			final String aColliName) {
		// Order order =
		// managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());

		OrderLineManager orderLineManager = managerRepository.getOrderLineManager();
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {

			try {
				Order order = managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());
				managerRepository.getOrderManager().lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
				List<OrderLine> orderLineList = order.getOrderLineList("Front");

				OrderLine orderLineFront = null;
				int antall = 0;
				BigDecimal duration = null;
				String doneBy = null;
				for (OrderLine front : orderLineList) {
					if (front.getOrdNo() != null && front.getOrdNo() != 0 && front.getLnNo() != null) {
						orderLineFront = front;
					}
					antall++;
					if (applied) {
						orderLineFront.setProduced(object.getProduced());
						setProducableApplied(orderLineFront, aColliName);

						BigDecimal tidsbruk = orderLineFront.getRealProductionHours();

						if (tidsbruk == null) {
							tidsbruk = Tidsforbruk.beregnTidsforbruk(orderLineFront.getActionStarted(),
									orderLineFront.getProduced());
						}
						orderLineFront.setRealProductionHours(tidsbruk);

						if (antall == 1 && window != null) {
							EditPacklistView editPacklistView = new EditPacklistView(login, false, tidsbruk,
									orderLineFront.getDoneBy());

							JDialog dialog = Util.getDialog(window, "Front produsert", true);
							WindowInterface window1 = new JDialogAdapter(dialog);
							window1.add(editPacklistView.buildPanel(window1));
							window1.pack();
							Util.locateOnScreenCenter(window1);
							window1.setVisible(true);

							if (!editPacklistView.isCanceled()) {
								duration = editPacklistView.getPacklistDuration();
								doneBy = editPacklistView.getDoneBy();
							}
						}
						orderLineFront.setRealProductionHours(duration);
						orderLineFront.setDoneBy(doneBy);

					} else {
						setProducableUnapplied(orderLineFront);
						duration = null;
						doneBy = null;
						orderLineFront.setRealProductionHours(null);
						orderLineFront.setDoneBy(null);

					}
					managerRepository.getOrderLineManager().saveOrderLine(orderLineFront);
				}
				if (orderLineFront.getOrdNo() != null) {

					lagFerdigmelding(order, orderLineFront, !applied, "Front");
				} else {
					LOGGER.info("Lager ikke ferdigmelding fordi ordrelinje mangler ordnno");
				}
			} catch (ProTransException e1) {
				Util.showErrorDialog(window, "Feil", e1.getMessage());
				e1.printStackTrace();
			}
			managerRepository.getOrderManager().settStatus(orderLine.getOrder().getOrderId(), null);
			// refreshAndSaveOrder(window, orderLine);
			applyListManager.refresh(object);
		}
	}

	// protected void lagFerdigmelding(OrderLine orderLine, boolean minus) {
	// LOGGER.info(String.format("Skal lage ferdigmelding for ordno: %s og lnno:
	// %s", orderLine.getOrdNo(),
	// orderLine.getLnNo()));
	// List<Ordln> ordrelinjer =
	// managerRepository.getOrdlnManager().findOrdLnByOrdNo(orderLine.getOrdNo());
	//
	// Set<Integer> produksjonslinjer = new HashSet<Integer>();
	// for (Ordln ordln : ordrelinjer) {
	// if (ordln.getPurcno() != null) {
	// produksjonslinjer.add(ordln.getPurcno());
	// }
	// }
	//
	// if (produksjonslinjer.isEmpty()) {
	// LOGGER.info("Lager ikke ferdigmelding fordi mangler ordln, purcno eller
	// purcno er 0");
	// }
	//
	// for (Integer purcno : produksjonslinjer) {
	// List<String> fillinjer = (VismaFileCreatorImpl.lagFillinjer(minus,
	// purcno, orderLine.getDoneBy(), "Front",
	// orderLine.getRealProductionHours()));
	// try {
	// vismaFileCreator.writeFile(purcno.toString(),
	// ApplicationParamUtil.findParamByName("visma_out_dir"),
	// fillinjer, 1);
	// } catch (IOException e) {
	// throw new RuntimeException("Feilet ved skriving av vismafil", e);
	// }
	// }
	//
	// }

	// private String lagLinje(Ordln vegg, boolean minus) {
	// StringBuilder stringBuilder = new StringBuilder();
	// stringBuilder.append(OrdchgrLineV.HEAD_START)
	// .append(StringUtils.leftPad("", OrdchgrLineV.NUMBER_OF_SEMICOLONS,
	// ";")).append(OrdchgrLineV.HEAD_END);
	// String lineString = StringUtils.replaceOnce(stringBuilder.toString(),
	// OrdchgrLineV.LN_NO_STRING,
	// vegg.getLnno() != null ? vegg.getLnno().toString() : "");
	// return StringUtils.replaceOnce(lineString,
	// OrdchgrLineV.LINE_STATUS_STRING,
	// (minus ? "-" : "") + vegg.getNoinvoab().toString());
	// }
}
