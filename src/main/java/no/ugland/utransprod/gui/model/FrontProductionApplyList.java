package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JDialog;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.VismaFileCreator;
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
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {

			try {
				if (applied) {
					orderLine.setProduced(object.getProduced());
					setProducableApplied(orderLine, aColliName);

					BigDecimal tidsbruk = orderLine.getRealProductionHours();

					if (tidsbruk == null) {
						tidsbruk = Tidsforbruk.beregnTidsforbruk(orderLine.getActionStarted(), orderLine.getProduced());
					}

					EditPacklistView editPacklistView = new EditPacklistView(login, false, tidsbruk,
							orderLine.getDoneBy());

					JDialog dialog = Util.getDialog(window, "Front produsert", true);
					WindowInterface window1 = new JDialogAdapter(dialog);
					window1.add(editPacklistView.buildPanel(window1));
					window1.pack();
					Util.locateOnScreenCenter(window1);
					window1.setVisible(true);

					if (!editPacklistView.isCanceled()) {
						orderLine.setRealProductionHours(editPacklistView.getPacklistDuration());
						orderLine.setDoneBy(editPacklistView.getDoneBy());
					}

				} else {
					setProducableUnapplied(orderLine);
					orderLine.setRealProductionHours(null);
					orderLine.setDoneBy(null);

				}
				if (orderLine.getOrdNo() != null) {
					lagFerdigmelding(orderLine.getOrdNo(), orderLine.getLnNo(), !applied);
				} else {
					LOGGER.info("Lager ikke ferdigmelding fordi ordrelinje mangler ordnno");
				}
			} catch (ProTransException e1) {
				Util.showErrorDialog(window, "Feil", e1.getMessage());
				e1.printStackTrace();
			}

			refreshAndSaveOrder(window, orderLine);
			applyListManager.refresh(object);
		}
	}

	private void lagFerdigmelding(Integer ordno, Integer lnno, boolean minus) {
		LOGGER.info(String.format("Skal lage ferdigmelding for ordno: %s og lnno: %s", ordno, lnno));
		Ordln ordln = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(ordno, lnno);

		LOGGER.info(String.format("Purcno har verdien %s", ordln == null ? "Mangler ordln" : ordln.getPurcno()));
		if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno().intValue() != 0) {
			List<String> fillinjer = new ArrayList<String>();
			fillinjer.add(String.format(OrdchgrHeadV.HEAD_LINE_TMP,
					ordln.getPurcno() != null ? ordln.getPurcno().toString() : "", ""));
			List<Ordln> frontlinjer = managerRepository.getOrdlnManager().findOrdLnByOrdNo(ordln.getPurcno());
			LOGGER.info(String.format("Antall frontlinjer er %s", frontlinjer.size()));
			for (Ordln gavl : frontlinjer) {
				if (gavl.getNoinvoab() != null && gavl.getNoinvoab().intValue() > 0) {
					fillinjer.add(lagLinje(gavl, minus));
				}
			}
			try {
				vismaFileCreator.writeFile(ordln.getPurcno().toString(),
						ApplicationParamUtil.findParamByName("visma_out_dir"), fillinjer, 1);
			} catch (IOException e) {
				throw new RuntimeException("Feilet ved skriving av vismafil", e);
			}
		} else {
			LOGGER.info("Lager ikke ferdigmelding fordi mangler ordln, purcno eller purcno er 0");
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
