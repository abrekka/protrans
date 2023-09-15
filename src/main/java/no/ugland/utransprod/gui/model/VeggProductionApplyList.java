package no.ugland.utransprod.gui.model;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductionTimeManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;

import net.sourceforge.jtds.jdbc.ProtocolException;

public class VeggProductionApplyList extends ProductionApplyList {
	private static Logger LOGGER = Logger.getLogger(ProductionApplyList.class);
	private ProductionTimeManager productionTimeManager;

	public VeggProductionApplyList(Login login, IApplyListManager<Produceable> manager, String aColliName,
			String aWindowName, Integer[] somInvisibleCells, ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator,List<String> artikler) {
		super(login, manager, aColliName, aWindowName, somInvisibleCells, aManagerRepository, vismaFileCreator,artikler);
		productionTimeManager = (ProductionTimeManager) ModelUtil.getBean(ProductionTimeManager.MANAGER_NAME);
	}

	
//	public void setStarted(Produceable object, boolean started) {
//		if (object != null) {
//			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
//
//			Order order = orderManager.findByOrderNr(object.getOrderNr());
//			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//
//			if (order != null) {
//				boolean skalRegistreresSomOvertid = Util.showConfirmDialog((WindowInterface) null, "Overtid",
//						"Skal denne registreres som overtid?");
//				List<ProductionTime> produksjonstider = productionTimeManager
//						.findByOrderNrAndProductionname(order.getOrderNr(), "Vegg");
//				Date startedDate = Util.getCurrentDate();
//				String gjortAv = this.login.getApplicationUser().getFullName();
//				if (started) {
//					if (produksjonstider.isEmpty()) {
//						List<OrderLine> vegger = order.getOrderLineList("Vegg");
//						for (OrderLine vegg : vegger) {
//							vegg.setActionStarted(startedDate);
//							vegg.setDoneBy(gjortAv);
//							this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
//						}
//					}
//					productionTimeManager.saveProductionTime(new ProductionTime().withOrderNr(order.getOrderNr())
//							.withProductionname("Vegg").withUsername(login.getApplicationUser().getUserName())
//							.withStarted(startedDate).withCreated(Util.getCurrentDate())
//							.withUpdated(Util.getCurrentDate()).withOvertime(skalRegistreresSomOvertid));
//				} else {
//
//					List<ProductionTime> tiderForAndreBrukere = Lists
//							.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {
//
//								public boolean apply(ProductionTime input) {
//									return !input.getUsername()
//											.equalsIgnoreCase(login.getApplicationUser().getUserName());
//								}
//							}));
//					if (tiderForAndreBrukere.isEmpty()) {
//						List<OrderLine> vegger = order.getOrderLineList("Vegg");
//						for (OrderLine vegg : vegger) {
//							vegg.setActionStarted((Date) null);
//							vegg.setDoneBy((String) null);
//							this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
//						}
//
//					}
//					productionTimeManager.deleteAllForUser(order.getOrderNr(), "Vegg",
//							login.getApplicationUser().getUserName());
//				}
//
////				List<OrderLine> vegger = order.getOrderLineList("Vegg");
////				int antall = 0;
////				
////
////				Iterator var9 = vegger.iterator();
////				while (var9.hasNext()) {
////					OrderLine vegg = (OrderLine) var9.next();
////					++antall;
////					if (vegg != null) {
////						if (started) {
////							vegg.setActionStarted(startedDate);
//////							if (antall == 1) {
//////								gjortAv = Util.showInputDialogWithdefaultValue((WindowInterface) null, "Gjøres av",
//////										"Gjøres av", this.login.getApplicationUser().getFullName());
//////							}
////
////							vegg.setDoneBy(gjortAv);
////						} else {
////							vegg.setActionStarted((Date) null);
////							vegg.setDoneBy((String) null);
////						}
////						this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
////
////					}
////				}
//
//				this.managerRepository.getOrderManager().refreshObject(order);
//			}
//			this.applyListManager.refresh(object);
//		}
//	}

//	public void setPause(Produceable object, boolean started) {
//		OrderManager orderManager = this.managerRepository.getOrderManager();
//		Order order = orderManager.findByOrderNr(object.getOrderNr());
//		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//		if (order != null) {
//			List vegger = order.getOrderLineList("Vegg");
//
//			int antall = 0;
//			OrderLine orderLineVegg = null;
//
//			EditPacklistView editPacklistView = null;
//			BigDecimal duration = null;
//			String doneBy = null;
//			OrderLine vegg;
//
//			List<ProductionTime> produksjonstider = productionTimeManager
//					.findByOrderNrAndProductionname(order.getOrderNr(), "Vegg");
//
//			List<ProductionTime> tiderForBruker = Lists
//					.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {
//
//						public boolean apply(ProductionTime input) {
//							return input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName());
//						}
//					}));
//
//			Collections.sort(tiderForBruker, new Comparator<ProductionTime>() {
//
//				public int compare(ProductionTime o1, ProductionTime o2) {
//					return o2.getStarted().compareTo(o1.getStarted());
//				}
//			});
//
//			ProductionTime sisteProduksjon = tiderForBruker.get(0);
//
//			sisteProduksjon.setStopped(new Date());
//			productionTimeManager.saveProductionTime(sisteProduksjon);
//		}
//
//	}

//	protected void handleApply(Produceable object, boolean applied, WindowInterface window, String aColliName) {
//		OrderManager orderManager = this.managerRepository.getOrderManager();
//		Order order = orderManager.findByOrderNr(object.getOrderNr());
//		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
//		if (order != null) {
//			List<OrderLine> vegger = order.getOrderLineList("Vegg");
//
//			try {
//				int antall = 0;
//				OrderLine orderLineVegg = null;
//
//				EditPacklistView editPacklistView = null;
//				BigDecimal duration = null;
//				String doneBy = null;
//
//				List<ProductionTime> produksjonstider = productionTimeManager
//						.findByOrderNrAndProductionname(order.getOrderNr(), "Vegg");
//
//				List<ProductionTime> tiderForBruker = Lists
//						.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {
//
//							public boolean apply(ProductionTime input) {
//								return input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName());
//							}
//						}));
//
//				List<ProductionTime> tiderForAndreBrukereSomIkkeErFerdig = Lists
//						.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {
//
//							public boolean apply(ProductionTime input) {
//								return !input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName())
//										&& input.getStopped() == null;
//							}
//						}));
//
//				Collections.sort(tiderForBruker, new Comparator<ProductionTime>() {
//
//					public int compare(ProductionTime o1, ProductionTime o2) {
//						return o2.getStarted().compareTo(o1.getStarted());
//					}
//				});
//
//				ProductionTime sisteProduksjon = tiderForBruker.get(0);
//
//				BigDecimal totalTidsforbruk = BigDecimal.ZERO;
//				if (applied) {
//					if (!tiderForAndreBrukereSomIkkeErFerdig.isEmpty()) {
//						ArrayList<String> brukere = Lists.newArrayList(Iterables
//								.transform(tiderForAndreBrukereSomIkkeErFerdig, new Function<ProductionTime, String>() {
//
//									public String apply(ProductionTime from) {
//										return from.getUsername();
//									}
//								}));
//						throw new ProTransException(
//								"Kan ikke sette produsert fordi " + String.join(",", brukere) + " ikke er ferdige!");
//					}
//					sisteProduksjon.setStopped(new Date());
//					productionTimeManager.saveProductionTime(sisteProduksjon);
//					for (ProductionTime productionTime : produksjonstider) {
//						totalTidsforbruk = totalTidsforbruk
//								.add(Tidsforbruk.beregnTidsforbruk(productionTime.getStarted(),
//										productionTime.getStopped(), productionTime.getOvertimeBoolean()));
//					}
//
//					for (OrderLine vegg : vegger) {
//						++antall;
//						if (vegg.getOrdNo() != null && vegg.getOrdNo() != 0 && vegg.getLnNo() != null) {
//							orderLineVegg = vegg;
//						}
//						vegg.setProduced(object.getProduced());
//						this.setProducableApplied(vegg, aColliName);
//						if (antall == 1 && window != null) {
//							editPacklistView = new EditPacklistView(this.login, false, totalTidsforbruk,
//									vegg.getDoneBy());
//							JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
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
//							vegg.setRealProductionHours(editPacklistView.getPacklistDuration());
//							vegg.setDoneBy(editPacklistView.getDoneBy());
//						}
//						vegg.setRealProductionHours(duration);
//						vegg.setDoneBy(doneBy);
//						this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
//					}
//
//				} else {
//					sisteProduksjon.setStopped(null);
//					productionTimeManager.saveProductionTime(sisteProduksjon);
//					for (OrderLine vegg : vegger) {
//						this.setProducableUnapplied(vegg);
//						duration = null;
//						doneBy = null;
//						vegg.setRealProductionHours((BigDecimal) null);
//						vegg.setDoneBy((String) null);
//						this.managerRepository.getOrderLineManager().saveOrderLine(vegg);
//					}
//				}
//				if (orderLineVegg != null) {
//					this.lagFerdigmelding(order, orderLineVegg, !applied, "Vegg");
//				}
//
////				for (Iterator var13 = vegger.iterator(); var13.hasNext(); this.managerRepository.getOrderLineManager()
////						.saveOrderLine(vegg)) {
////					vegg = (OrderLine) var13.next();
////					if (vegg.getOrdNo() != null && vegg.getOrdNo() != 0 && vegg.getLnNo() != null) {
////						orderLineVegg = vegg;
////
////					}
////
////					++antall;
////					if (applied) {
////
////						vegg.setProduced(object.getProduced());
////						this.setProducableApplied(vegg, aColliName);
////
//////						BigDecimal tidsbruk = vegg.getRealProductionHours();
//////
//////						if (tidsbruk == null) {
//////							tidsbruk = Tidsforbruk.beregnTidsforbruk(vegg.getActionStarted(), vegg.getProduced());
//////						}
////
////						if (antall == 1 && window != null) {
////							editPacklistView = new EditPacklistView(this.login, false, totalTidsforbruk,
////									vegg.getDoneBy());
////							JDialog dialog = Util.getDialog(window, "Vegg produsert", true);
////							WindowInterface window1 = new JDialogAdapter(dialog);
////							window1.add(editPacklistView.buildPanel(window1));
////							window1.pack();
////							Util.locateOnScreenCenter(window1);
////							window1.setVisible(true);
////						}
////
////						if (editPacklistView != null && !editPacklistView.isCanceled()) {
////							duration = editPacklistView.getPacklistDuration();
////							doneBy = editPacklistView.getDoneBy();
////							vegg.setRealProductionHours(editPacklistView.getPacklistDuration());
////							vegg.setDoneBy(editPacklistView.getDoneBy());
////						}
////						vegg.setRealProductionHours(duration);
////						vegg.setDoneBy(doneBy);
////					} else {
////						this.setProducableUnapplied(vegg);
////						duration = null;
////						doneBy = null;
////						vegg.setRealProductionHours((BigDecimal) null);
////						vegg.setDoneBy((String) null);
////
////					}
////				}
//
////				if (orderLineVegg != null) {
////					orderLineVegg.setRealProductionHours(duration);
////					orderLineVegg.setDoneBy(doneBy);
////					this.lagFerdigmelding(order, orderLineVegg, !applied, "Vegg");
////				}
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

//	@Override
//	protected void lagFerdigmelding(Order order, OrderLine orderLine, boolean minus, String produksjonstype) {
//		LOGGER.info(String.format("Skal lage ferdigmelding vegg for ordno: %s oglnno: %s", orderLine.getOrdNo(),
//				orderLine.getLnNo()));
	protected void lagFerdigmelding(Integer ordno, Integer lnno, boolean minus) {
		Ordln ordln = this.managerRepository.getOrdlnManager().findByOrdNoAndLnNo(ordno, lnno);

		if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno() != 0) {
			List<String> fillinjer = new ArrayList();
			fillinjer.add(String.format(
					"H;;%s;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
					ordln.getPurcno() != null ? ordln.getPurcno().toString() : "", ""));

			List<Ordln> vegglinjer = this.managerRepository.getOrdlnManager().findOrdLnByOrdNo(ordln.getPurcno());
			Iterator var7 = vegglinjer.iterator();
			while (var7.hasNext()) {
				Ordln vegg = (Ordln) var7.next();
				if (vegg.getNoinvoab() != null && vegg.getNoinvoab().intValue() > 0) {
					fillinjer.add(this.lagLinje(vegg, minus));
				}
			}

			try {
				/* 179 */ this.vismaFileCreator.writeFile(ordln.getPurcno().toString(),
						ApplicationParamUtil.findParamByName("visma_out_dir"), fillinjer, 1);
				/*     */
				/* 181 */ } catch (IOException var9) {
				/* 182 */ throw new RuntimeException("Feilet ved skriving av vismafil", var9);
				/*     */ }
			/*     */ }
		/*     */
		/* 186 */ }

	/*     */
	/*     */ private String lagLinje(Ordln vegg, boolean minus) {
		/* 189 */ StringBuilder stringBuilder = new StringBuilder();
		/* 190 */ stringBuilder.append("L;$lnNo").append(StringUtils.leftPad("", 71, ";")).append("1;1;$lineStatus;3");
		/*     */
		/* 192 */ String lineString = StringUtils.replaceOnce(stringBuilder.toString(), "$lnNo",
				vegg.getLnno() != null ? vegg.getLnno().toString() : "");
		/*     */
		/* 194 */ return StringUtils.replaceOnce(lineString, "$lineStatus",
				(minus ? "-" : "") + vegg.getNoinvoab().toString());
		/*     */ }
	/*     */ }
