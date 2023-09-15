package no.ugland.utransprod.gui.model;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditPacklistView;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.ProductionTimeManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jdesktop.swingx.JXTable;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer produksjon
 * 
 * @author atle.brekka
 */

public class ProductionApplyList extends AbstractApplyList<Produceable> {
	private static Logger LOGGER = Logger.getLogger(ProductionApplyList.class);
	private ProductionTimeManager productionTimeManager;
	private String colliName;

	private String windowname;
	private List<String> artikler;

	private Integer[] invisibleCells;
	protected ManagerRepository managerRepository;

	/**
	 * @param aUserType
	 * @param manager
	 * @param aColliName
	 * @param aWindowName
	 * @param somInvisibleCells
	 */
	public ProductionApplyList(final Login login, final IApplyListManager<Produceable> manager, final String aColliName,
			final String aWindowName, final Integer[] somInvisibleCells, final ManagerRepository aManagerRepository,
			VismaFileCreator vismaFileCreator, List<String> artikler) {
		super(login, manager, vismaFileCreator);
		managerRepository = aManagerRepository;
		colliName = aColliName;
		windowname = aWindowName;
		this.artikler = artikler;
		invisibleCells = somInvisibleCells != null ? somInvisibleCells.clone() : null;
		productionTimeManager = (ProductionTimeManager) ModelUtil.getBean(ProductionTimeManager.MANAGER_NAME);
	}

	/**
	 * Setter kolli for produsert element
	 * 
	 * @param orderLine
	 * @throws ProTransException
	 */
	private void doPackage(final OrderLine orderLine, final String aColliName) throws ProTransException {
		String currentColliName = aColliName != null ? aColliName : colliName;
		ColliManager colliManager = managerRepository.getColliManager();
		OrderLineManager orderLineManager = managerRepository.getOrderLineManager();
		Colli colli;
		if (orderLine.getPostShipment() != null) {
			colli = colliManager.findByNameAndPostShipment(currentColliName, orderLine.getPostShipment());
		} else {
			colli = colliManager.findByNameAndOrder(currentColliName, orderLine.getOrder());
		}
		if (colli == null) {
			if (orderLine.getPostShipment() != null) {
				colli = new Colli(null, null, currentColliName, null, null, null, orderLine.getPostShipment(), null,
						Util.getCurrentDate(), "ProductionApplyList");
			} else {
				colli = new Colli(null, orderLine.getOrder(), currentColliName, null, null, null, null, null,
						Util.getCurrentDate(), "ProductionApplyList");
			}
		} else {
			colliManager.lazyLoad(colli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
		}
		colli.addOrderLine(orderLine);
		colliManager.saveColli(colli);

		orderLine.setColli(colli);

		orderLineManager.saveOrderLine(orderLine);
		checkCompleteness(colli, true);

	}

	/**
	 * Sjekker om ordre er komplett
	 * 
	 * @param colli
	 * @param applied
	 * @throws ProTransException
	 */
	protected void checkCompleteness(Colli colli, boolean applied) throws ProTransException {
		if (colli != null) {
			Order order = colli.getOrder();
			if (order != null) {
				OrderManager orderManager = managerRepository.getOrderManager();
				if (applied) {
					orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
							LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });
					if (order.isDonePackage()) {
						order.setOrderComplete(Util.getCurrentDate());
						orderManager.saveOrder(order);
					}
				} else {
					order.setOrderComplete(null);
					orderManager.saveOrder(order);
				}
			} else {
				PostShipment postShipment = colli.getPostShipment();
				if (postShipment != null) {
					PostShipmentManager postShipmentManager = managerRepository.getPostShipmentManager();
					if (applied) {
						postShipmentManager.lazyLoad(postShipment,
								new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES });
						if (postShipment.isDonePackage()) {
							postShipment.setPostShipmentComplete(Util.getCurrentDate());
							postShipmentManager.savePostShipment(postShipment);
						}
					} else {
						postShipment.setPostShipmentComplete(null);
						postShipmentManager.savePostShipment(postShipment);
					}
				}
			}
		}
	}

	/**
	 * @param object
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#
	 *      setApplied(no.ugland.utransprod.gui.model.Applyable, boolean,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public void setApplied(final Produceable object, final boolean applied, final WindowInterface window) {
		if (object != null) {
			handleApply(object, applied, window, colliName);
		}

	}

	protected void handleApply(final Produceable object, final boolean applied, final WindowInterface window,
			final String aColliName) {
		OrderManager orderManager = this.managerRepository.getOrderManager();
		Order order = orderManager.findByOrderNr(object.getOrderNr());
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
		LOGGER.info("Skal sette ferdig for ordre " + order.getOrderNr());
		if (order != null) {
			List<OrderLine> produksjonslinjer = order.getOrderLineList(artikler);

			try {
				int antall = 0;
				OrderLine orderLine = null;

				EditPacklistView editPacklistView = null;
				BigDecimal duration = null;
				String doneBy = null;

				List<ProductionTime> produksjonstider = productionTimeManager
						.findByOrderNrAndProductionname(order.getOrderNr(), windowname);

				for (ProductionTime productionTime : produksjonstider) {
					LOGGER.info(productionTime.toString());
				}

				List<ProductionTime> tiderForBruker = Lists
						.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {

							public boolean apply(ProductionTime input) {
								return input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName());
							}
						}));

				List<ProductionTime> tiderForAndreBrukereSomIkkeErFerdig = Lists
						.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {

							public boolean apply(ProductionTime input) {
								return !input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName())
										&& input.getStopped() == null;
							}
						}));
				LOGGER.info("Fant produksjonstider for andre som ikke er ferdig for ordre: " + order.getOrderNr());
				for (ProductionTime productionTime : tiderForAndreBrukereSomIkkeErFerdig) {
					LOGGER.info(productionTime.toString());
				}

				Collections.sort(tiderForBruker, new Comparator<ProductionTime>() {

					public int compare(ProductionTime o1, ProductionTime o2) {
						return o2.getStarted().compareTo(o1.getStarted());
					}
				});

				ProductionTime sisteProduksjon = tiderForBruker.isEmpty() ? null : tiderForBruker.get(0);

				BigDecimal totalTidsforbruk = BigDecimal.ZERO;
				if (applied) {
					if (!tiderForAndreBrukereSomIkkeErFerdig.isEmpty()) {
						LOGGER.info("Det finnes ordretider som ikke er ferdig for ordre: " + order.getOrderNr());
						ArrayList<String> brukere = Lists.newArrayList(Iterables
								.transform(tiderForAndreBrukereSomIkkeErFerdig, new Function<ProductionTime, String>() {

									public String apply(ProductionTime from) {
										return from.getUsername();
									}
								}));
						throw new ProTransException("Kan ikke sette produsert fordi " + StringUtils.join(brukere, ",")
								+ " ikke er ferdig!");
					}

					if (sisteProduksjon != null) {
						sisteProduksjon.setStopped(new Date());
						productionTimeManager.saveProductionTime(sisteProduksjon);
					}
					for (ProductionTime productionTime : produksjonstider) {
						totalTidsforbruk = totalTidsforbruk
								.add(Tidsforbruk.beregnTidsforbruk(productionTime.getStarted(),
										productionTime.getStopped(), productionTime.getOvertimeBoolean()));
					}

					for (OrderLine produksjonslinje : produksjonslinjer) {
						++antall;
						if (produksjonslinje.getOrdNo() != null && produksjonslinje.getOrdNo() != 0
								&& produksjonslinje.getLnNo() != null) {
							orderLine = produksjonslinje;
						}
						produksjonslinje.setProduced(object.getProduced());
						this.setProducableApplied(produksjonslinje, aColliName);
						if (antall == 1 && window != null) {
							editPacklistView = new EditPacklistView(this.login, false, totalTidsforbruk,
									produksjonslinje.getDoneBy());
							JDialog dialog = Util.getDialog(window, aColliName + " produsert", true);
							WindowInterface window1 = new JDialogAdapter(dialog);
							window1.add(editPacklistView.buildPanel(window1));
							window1.pack();
							Util.locateOnScreenCenter(window1);
							window1.setVisible(true);
						}

						if (editPacklistView != null && !editPacklistView.isCanceled()) {
							duration = editPacklistView.getPacklistDuration();
							doneBy = editPacklistView.getDoneBy();
							produksjonslinje.setRealProductionHours(editPacklistView.getPacklistDuration());
							produksjonslinje.setDoneBy(editPacklistView.getDoneBy());
						}
						produksjonslinje.setRealProductionHours(duration);
						produksjonslinje.setDoneBy(doneBy);
						this.managerRepository.getOrderLineManager().saveOrderLine(produksjonslinje);
					}

				} else {
					if (sisteProduksjon != null) {
						sisteProduksjon.setStopped(null);
						productionTimeManager.saveProductionTime(sisteProduksjon);
					}
					for (OrderLine produksjonslinje : produksjonslinjer) {
						this.setProducableUnapplied(produksjonslinje);
						duration = null;
						doneBy = null;
						produksjonslinje.setRealProductionHours((BigDecimal) null);
						produksjonslinje.setDoneBy((String) null);
						this.managerRepository.getOrderLineManager().saveOrderLine(produksjonslinje);
					}
				}
				if (orderLine != null) {
					this.lagFerdigmelding(order, orderLine, !applied, artikler);
				}

			} catch (ProTransException var18) {
				Util.showErrorDialog(window, "Feil", var18.getMessage());
				var18.printStackTrace();
			}

			this.managerRepository.getOrderManager().refreshObject(order);
			this.managerRepository.getOrderManager().settStatus(order.getOrderId(), (String) null);

			this.applyListManager.refresh(object);
		}
	}

	protected void refreshAndSaveOrder(final WindowInterface window, final OrderLine orderLine) {
		Order order = orderLine.getOrder();

		try {
			managerRepository.getOrderManager().refreshObject(order);
			order.setStatus(null);
			managerRepository.getOrderManager().saveOrder(order);
			managerRepository.getOrderLineManager().saveOrderLine(orderLine);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
		}
	}

	protected void setProducableUnapplied(final OrderLine orderLine) throws ProTransException {
		orderLine.setProduced(null);
		// orderLine.setProductionUnit(null);

		if (colliName != null) {
			checkCompleteness(orderLine.getColli(), false);
			orderLine.setColli(null);
		}
	}

	protected void setProducableApplied(final OrderLine orderLine, final String aColliName) throws ProTransException {
		// orderLine.setProduced(Util.getCurrentDate());
		String currentColliName = aColliName != null ? aColliName : colliName;
		if (currentColliName != null) {
			doPackage(orderLine, currentColliName);
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#hasWriteAccess()
	 */
	public boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), windowname);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getReportEnum()
	 */
	@Override
	public ReportEnum getReportEnum() {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getTableModelReport(javax.swing.ListModel,
	 *      org.jdesktop.swingx.JXTable, com.jgoodies.binding.list.SelectionInList)
	 */
	@Override
	public TableModel getTableModelReport(final ListModel listModel, final JXTable table,
			final SelectionInList objectSelectionList) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#setInvisibleColumns(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setInvisibleColumns(JXTable table) {
		if (invisibleCells != null && invisibleCells.length != 0) {
			for (int i = 0; i < invisibleCells.length; i++) {
				table.getColumnExt(invisibleCells[i].intValue()).setVisible(false);
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#sortObjectLines(java.util.List)
	 */
	@Override
	protected void sortObjectLines(List<Produceable> lines) {
	}

	/**
	 * @see no.ugland.utransprod.gui.model.ApplyListInterface#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	public Produceable getApplyObject(final Transportable transportable, final WindowInterface window) {
		List<Produceable> list = applyListManager.findApplyableByOrderNr(transportable.getOrder().getOrderNr());

		// return list == null||list.size()==0 ? null : list != null &&
		// list.size() == 1 ? list.get(0) : selectApplyObject(list,window);
		return list != null && list.size() == 1 ? list.get(0) : null;
	}

	public void setStarted(final Produceable object, final boolean started) {
		if (object != null) {
			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");

			Order order = orderManager.findByOrderNr(object.getOrderNr());
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });

			if (order != null) {
				boolean skalRegistreresSomOvertid = false;
				if (!Tidsforbruk.erInnenforArbeidstid(Util.getCurrentDate())) {
					skalRegistreresSomOvertid = Util.showConfirmDialog((WindowInterface) null, "Utenfor arbeidstid",
							"Skal denne registreres som overtid?");
				}
				List<ProductionTime> produksjonstider = productionTimeManager
						.findByOrderNrAndProductionname(order.getOrderNr(), windowname);
				Date startedDate = Util.getCurrentDate();
				String gjortAv = this.login.getApplicationUser().getFullName();
				if (started) {
					if (produksjonstider.isEmpty()) {
						List<OrderLine> produksjonslinjer = order.getOrderLineList(artikler);
						for (OrderLine produksjonslinje : produksjonslinjer) {
							produksjonslinje.setActionStarted(startedDate);
							produksjonslinje.setDoneBy(gjortAv);
							this.managerRepository.getOrderLineManager().saveOrderLine(produksjonslinje);
						}
					}
					productionTimeManager.saveProductionTime(new ProductionTime().withOrderNr(order.getOrderNr())
							.withProductionname(windowname).withUsername(login.getApplicationUser().getUserName())
							.withStarted(startedDate).withCreated(Util.getCurrentDate())
							.withUpdated(Util.getCurrentDate()).withOvertime(skalRegistreresSomOvertid));
				} else {

					List<ProductionTime> tiderForAndreBrukere = Lists
							.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {

								public boolean apply(ProductionTime input) {
									return !input.getUsername()
											.equalsIgnoreCase(login.getApplicationUser().getUserName());
								}
							}));
					if (tiderForAndreBrukere.isEmpty()) {
						List<OrderLine> produksjonslinjer = order.getOrderLineList(artikler);
						for (OrderLine produksjonslinje : produksjonslinjer) {
							produksjonslinje.setActionStarted((Date) null);
							produksjonslinje.setDoneBy((String) null);
							this.managerRepository.getOrderLineManager().saveOrderLine(produksjonslinje);
						}

					}
					productionTimeManager.deleteAllForUser(order.getOrderNr(), windowname,
							login.getApplicationUser().getUserName());
				}

				this.managerRepository.getOrderManager().refreshObject(order);
			}
			this.applyListManager.refresh(object);
		}

	}

	public void setPause(final Produceable object, final boolean started) {
		OrderManager orderManager = this.managerRepository.getOrderManager();
		Order order = orderManager.findByOrderNr(object.getOrderNr());
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
		if (order != null) {

			List<ProductionTime> produksjonstider = productionTimeManager
					.findByOrderNrAndProductionname(order.getOrderNr(), windowname);

			List<ProductionTime> tiderForBruker = Lists
					.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName());
						}
					}));

			Collections.sort(tiderForBruker, new Comparator<ProductionTime>() {

				public int compare(ProductionTime o1, ProductionTime o2) {
					return o2.getStarted().compareTo(o1.getStarted());
				}
			});

			ProductionTime sisteProduksjon = tiderForBruker.get(0);

			sisteProduksjon.setStopped(new Date());
			productionTimeManager.saveProductionTime(sisteProduksjon);
		}

	}

	public void setRealProductionHours(Produceable object, BigDecimal overstyrtTidsforbruk) {
		if (object != null) {
			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
			Order order = orderManager.findByOrderNr(object.getOrderNr());
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });

			List<OrderLine> ordrelinjer = order.getOrderLineList(artikler);

			for (OrderLine orderLine : ordrelinjer) {
				orderLine.setRealProductionHours(overstyrtTidsforbruk);
				managerRepository.getOrderLineManager().saveOrderLine(orderLine);

				applyListManager.refresh(object);
			}
		}
	}

	private Produceable getProduceable(Produceable object) {
		if (object.getOrderLineId() != null) {
			return object;
		} else if (object.getRelatedArticles() != null && object.getRelatedArticles().size() != 0) {
			return (Produceable) object.getRelatedArticles().get(0);
		}
		return null;
	}

	public void setProductionUnit(ProductionUnit productionUnit, Produceable object, WindowInterface window) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {
			orderLine.setProductionUnit(productionUnit);
			refreshAndSaveOrder(window, orderLine);
			applyListManager.refresh(object);

		}
	}

	protected void lagFerdigmelding(Order order, OrderLine orderLine, boolean minus, List<String> produksjonstyper) {
		LOGGER.info(String.format("Skal lage ferdigmelding for ordno: %s oglnno: %s", orderLine.getOrdNo(),
				orderLine.getLnNo()));

		List<OrderLine> ordrelinjer = order.getOrderLineList(produksjonstyper);

		List<Ordln> ordlnList = new ArrayList<Ordln>();

		for (OrderLine orderLine2 : ordrelinjer) {
			LOGGER.info("Finner ordnl for " + orderLine.getOrdNo() + " og " + orderLine2.getLnNo());
			if (orderLine.getOrdNo() != null && orderLine2.getLnNo() != null) {
				LOGGER.info("Finner ordnl for " + orderLine.getOrdNo() + " og " + orderLine2.getLnNo());
				Ordln ordln = managerRepository.getOrdlnManager().findByOrdNoAndLnNo(orderLine2.getOrdNo(),
						orderLine2.getLnNo());
				if (ordln != null) {
					ordlnList.add(ordln);
				} else {
					LOGGER.info("Fant ikke ordnl for " + orderLine.getOrdNo() + " og " + orderLine2.getLnNo());
				}
			}
		}
		LOGGER.info("ordlnList har antall: " + ordlnList.size());

		// List<Ordln> ordrelinjer =
		// managerRepository.getOrdlnManager().findOrdLnByOrdNo(orderLine.getOrdNo());

		Set<Integer> produksjonslinjer = new HashSet<Integer>();
		for (Ordln ordln : ordlnList) {
			LOGGER.info("Ordln purcno: " + ordln.getPurcno());
			if (ordln != null && ordln.getPurcno() != null && ordln.getPurcno() != 0) {
				produksjonslinjer.add(ordln.getPurcno());
			}
		}

		if (produksjonslinjer.isEmpty()) {
			LOGGER.info("Lager ikke ferdigmelding fordi mangler ordln, purcno eller purcno er 0");
		}

		for (Integer purcno : produksjonslinjer) {
			List<String> fillinjer = (VismaFileCreatorImpl.lagFillinjer(minus, purcno, orderLine.getDoneBy(),
					produksjonstyper.get(0), orderLine.getRealProductionHours()));
			try {
				vismaFileCreator.writeFile(purcno.toString(), ApplicationParamUtil.findParamByName("visma_out_dir"),
						fillinjer, 1);
			} catch (IOException e) {
				throw new RuntimeException("Feilet ved skriving av vismafil", e);
			}
		}

	}
}
