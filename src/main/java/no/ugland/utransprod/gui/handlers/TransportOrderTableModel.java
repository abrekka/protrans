package no.ugland.utransprod.gui.handlers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.TextPaneRendererTransport;
import no.ugland.utransprod.gui.model.TextPaneRendererTransportSent;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

/**
 * Tabellmodell for ordre
 * 
 * @author atle.brekka
 */
public final class TransportOrderTableModel extends AbstractTableAdapter {
	private StatusCheckerInterface<Transportable> gavlChecker;

	private StatusCheckerInterface<Transportable> takstolChecker;

	private StatusCheckerInterface<Transportable> steinChecker;

	private StatusCheckerInterface<Transportable> gulvsponChecker;

	private final ArrayListModel list;

	private static final long serialVersionUID = 1L;
	private ListModel listModel;
	private TransportColumn.ForExcel isForExcel;

	/**
	 * @param listModel
	 * @param aOrderList
	 * @param aGavlChecker
	 * @param aTakstolChecker
	 * @param aSteinChecker
	 * @param aGulvsponChecker
	 */
	TransportOrderTableModel(ListModel aListModel, ArrayListModel aOrderList,
			StatusCheckerInterface<Transportable> aGavlChecker, StatusCheckerInterface<Transportable> aTakstolChecker,
			StatusCheckerInterface<Transportable> aSteinChecker, StatusCheckerInterface<Transportable> aGulvsponChecker,
			TransportColumn.ForExcel forExcel) {
		super(aListModel, TransportColumn.getColumnNames(forExcel));
		isForExcel = forExcel;
		listModel = aListModel;
		list = aOrderList;
		gavlChecker = aGavlChecker;
		takstolChecker = aTakstolChecker;
		steinChecker = aSteinChecker;
		gulvsponChecker = aGulvsponChecker;
	}

	public TransportOrderTableModel clone(TransportColumn.ForExcel forExcel) {
		return new TransportOrderTableModel(listModel, list, gavlChecker, takstolChecker, steinChecker, gulvsponChecker,
				forExcel);
	}

	/**
	 * Henter objekt for gjeldende indeks
	 * 
	 * @param rowIndex
	 * @return objekt
	 */
	public Transportable getTransportable(int rowIndex) {
		return (Transportable) getRow(rowIndex);
	}

	public enum TransportColumn {
		ORDREINFO("Ordreinfo", ForExcel.EXCEL) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getTransportReportString();
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
			}
		},
		LEVERINGSADRESSE("Leveringsadresse", ForExcel.EXCEL) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getOrder().getDeliveryAddress();
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
			}
		},
		TRANSPORT("Transport", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getTransport();
			}

			@Override
			public Class<?> getColumnClass() {
				return Transport.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(100);

			}
		},
		ORDRE("Ordre", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable;
			}

			@Override
			public Class<?> getColumnClass() {
				return Transportable.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
				table.getColumnModel().getColumn(table.getColumnExt(getColumnName()).getModelIndex())
						.setCellRenderer(new TextPaneRendererTransport());

			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(220);

			}
		},
		// GAVL("Gavl", ForExcel.TABLE) {
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		// return statusMap.get(gavlChecker.getArticleName());
		// }
		//
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);
		//
		// }
		// },
		MONT("Mont", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				if (transportable.getDoAssembly() != null && transportable.getDoAssembly() == 1) {
					return transportable.getAssembly() == null ? "M"
							: "M" + transportable.getAssembly().getAssemblyWeek();
				}
				return "";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		// TAKSTOL("Takstol", ForExcel.TABLE) {
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		// return statusMap.get(takstolChecker.getArticleName());
		// }
		//
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
		//
		// }
		// },
		STEIN("Stein", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {

				if (!Hibernate.isInitialized(transportable.getOrderLines())) {
					if (Order.class.isInstance(transportable)) {
						OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
						orderManager.lazyLoadOrder(transportable.getOrder(),
								new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS,
										LazyLoadOrderEnum.COLLIES });
					} else {
						PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
								.getBean(PostShipmentManager.MANAGER_NAME);
						postShipmentManager.lazyLoad(transportable.getPostShipment(), new LazyLoadPostShipmentEnum[] {
								LazyLoadPostShipmentEnum.ORDER_LINES, LazyLoadPostShipmentEnum.COLLIES });
					}
				}

				OrderLine takstein = transportable.getOrderLine("Takstein");
				if (takstein != null) {
					String info = takstein.getAttributeInfo();
					if (info != null) {
						info = info.substring(info.indexOf("Taksteintype:") + 13, info.indexOf("Taksteintype:") + 16);
					}
					return info;
				}
				return null;

				// String steinStatus =
				// statusMap.get(steinChecker.getArticleName());
				// return steinStatus == null ? "MANGLER" : steinStatus;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		GULVSPON("Gulvspon", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {

				// if (!Hibernate.isInitialized(transportable.getOrderLines()))
				// {
				// if (Order.class.isInstance(transportable)) {
				// OrderManager orderManager = (OrderManager)
				// ModelUtil.getBean("orderManager");
				// orderManager.lazyLoadOrder(transportable.getOrder(),
				// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
				// } else {
				// PostShipmentManager postShipmentManager =
				// (PostShipmentManager) ModelUtil
				// .getBean("orderManager");
				// postShipmentManager.lazyLoad(transportable.getPostShipment(),
				// new LazyLoadPostShipmentEnum[] {
				// LazyLoadPostShipmentEnum.ORDER_LINES });
				// }
				// }

				OrderLine gulvspon = transportable.getOrderLine("Gulvspon");
				if (gulvspon != null) {
					String info = gulvspon.getAttributeInfo();
					if (info != null && info.contains("Har gulvspon:Ja")) {
						return "V" + (gulvspon.getNumberOfItems() != null ? gulvspon.getNumberOfItems() : "");

					}
				}
				return null;
				// String gulvsponStatus =
				// statusMap.get(gulvsponChecker.getArticleName());
				// return gulvsponStatus == null ? "MANGLER" : gulvsponStatus;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(65);

			}
		},
		IKKE_OPPLASTET("Ikke opplastet", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				// if(transportable.getOrderNr().equals("145844")){
				// System.out.println("test");
				// }
				return transportable.getTransport() != null && transportable.getTransport().getSent() != null
						&& !transportable.getSentBool() ? "!" : "";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		KOMPLETT("Komplett", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				try {
					return transportable.getOrderComplete() != null ? "Ja" : "Nei";
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		KLAR("Klar", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getOrderReady() != null ? "Ja" : "Nei";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		KLLI("Klli", ForExcel.EXCEL) {
			@SuppressWarnings("unchecked")
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				// OverviewManager<Object> manager = (OverviewManager<Object>)
				// ModelUtil
				// .getBean(transportable.getManagerName());
				// manager.lazyLoad(transportable,
				// new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES,
				// LazyLoadEnum.NONE },
				// { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE },
				// { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });

				// OrderManager orderManager = (OrderManager)
				// ModelUtil.getBean(OrderManager.MANAGER_NAME);
				// if (!Hibernate.isInitialized(transportable.getCollies())) {
				// orderManager.lazyLoadOrder(transportable.getOrder(), new
				// LazyLoadOrderEnum[] {
				// LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
				// LazyLoadOrderEnum.COMMENTS });

				// manager.lazyLoad(transportable,
				// new LazyLoadEnum[][] { { LazyLoadEnum.COLLIES,
				// LazyLoadEnum.NONE },
				// { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE },
				// { LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });
				// }
				return transportable.getCollies() != null ? transportable.getCollies().size() : null;
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		HENSYN("Hensyn", ForExcel.EXCEL) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				StringBuilder builder = new StringBuilder();
				if (transportable.getPostShipment() != null) {
					builder.append("Ettersending ");
				}
				if (transportable.getSpecialConcern() != null) {
					builder.append(transportable.getSpecialConcern());
				}
				return builder.toString();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		},
		OPPLASTET("Opplastet", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable;
			}

			@Override
			public Class<?> getColumnClass() {
				return Transportable.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
				table.getColumnModel().getColumn(table.getColumnExt(getColumnName()).getModelIndex())
						.setCellRenderer(new TextPaneRendererTransportSent());

			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(45);

			}
		},

		// REST("Rest", ForExcel.TABLE) {
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		// return transportable;
		// }
		//
		// @Override
		// public Class<?> getColumnClass() {
		// return Transportable.class;
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// table.getColumnModel().getColumn(table.getColumnExt(getColumnName()).getModelIndex()).setCellRenderer(new
		// TextPaneRendererCustTr());
		//
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
		//
		// }
		// },
		// GHØYDE("GHøyde", ForExcel.TABLE) {
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		// return transportable.getGarageColliHeight();
		// }
		//
		// @Override
		// public Class<?> getColumnClass() {
		// return BigDecimal.class;
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(55);
		//
		// }
		// },
		PRODUKTOMRÅDE("Produktområde", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getProductAreaGroup() != null
						? transportable.getProductAreaGroup().getProductAreaGroupName() : "";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);

			}
		},
		// THØYDE("THøyde", ForExcel.TABLE) {
		// @Override
		// public Class<?> getColumnClass() {
		// return Integer.class;
		// }
		//
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		// return transportable.getMaxTrossHeight();
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(55);
		//
		// }
		// },
		KOMMENTAR("Kommentar", ForExcel.EXCEL) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getComment();
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);

			}
		},
		SANNSYNLIGHET("Sannsynlighet", ForExcel.TABLE) {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {

				return transportable.getProbability();
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
			}
		},
		// TAKSTOLTEGNER("Takstoltegner", ForExcel.TABLE) {
		// @Override
		// public Class<?> getColumnClass() {
		// return String.class;
		// }
		//
		// @Override
		// public Object getValue(Transportable transportable, Map<String,
		// String> statusMap, StatusCheckerInterface<Transportable> gavlChecker,
		// StatusCheckerInterface<Transportable> takstolChecker,
		// StatusCheckerInterface<Transportable> steinChecker,
		// StatusCheckerInterface<Transportable> gulvsponChecker) {
		//
		// return transportable.getTrossDrawer();
		// }
		//
		// @Override
		// public void setCellRenderer(JXTable table) {
		// }
		//
		// @Override
		// public void setPrefferedWidth(JXTable table) {
		// table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(100);
		// }
		// },
		TLF("Tlf", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getOrder().getTelephoneNr();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {

			}

			@Override
			public void setPrefferedWidth(JXTable table) {

			}
		},
		PROD_UKE("Prod uke", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getOrder().getProductionWeek();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {

			}

			@Override
			public void setPrefferedWidth(JXTable table) {

			}
		},
		OPPLASTET_FILTER("opplastet filter", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				return transportable.getSentBool() ? "Ja" : "Nei";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
				// TODO Auto-generated method stub

			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				// TODO Auto-generated method stub

			}
		},
		IGAR("IGar", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				// if (!Hibernate.isInitialized(transportable.getOrderLines()))
				// {
				// if (Order.class.isInstance(transportable)) {
				// OrderManager orderManager = (OrderManager)
				// ModelUtil.getBean("orderManager");
				// orderManager.lazyLoadOrder(transportable.getOrder(),
				// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });
				// } else {
				// PostShipmentManager postShipmentManager =
				// (PostShipmentManager) ModelUtil
				// .getBean("orderManager");
				// postShipmentManager.lazyLoad(transportable.getPostShipment(),
				// new LazyLoadPostShipmentEnum[] {
				// LazyLoadPostShipmentEnum.ORDER_LINES });
				// }
				// }

				OrderLine igar = transportable.getOrderLine("iGarasjen innredning");
				if (igar != null) {
					if (igar.getHasArticle() != null && igar.getHasArticle() == 1) {
						return "V" + (igar.getNumberOfItems() != null ? igar.getNumberOfItems() : "")
								+ (igar.getColli() != null ? "X" : "");

					}
				}
				return null;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(45);

			}
		},
		LEVERT("Levert", ForExcel.BOTH) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {

				return transportable.getLevertBool() ? "Ja" : "Nei";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(45);

			}
		},
		IKKE_LEVERT("Ikke levert", ForExcel.TABLE) {
			@Override
			public Object getValue(Transportable transportable, Map<String, String> statusMap,
					StatusCheckerInterface<Transportable> gavlChecker,
					StatusCheckerInterface<Transportable> takstolChecker,
					StatusCheckerInterface<Transportable> steinChecker,
					StatusCheckerInterface<Transportable> gulvsponChecker) {
				// if(transportable.getOrderNr().equals("145844")){
				// System.out.println("test");
				// }
				return transportable.getTransport() != null && transportable.getTransport().getSent() != null
						&& !transportable.getLevertBool() ? "!" : "";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public void setCellRenderer(JXTable table) {
			}

			@Override
			public void setPrefferedWidth(JXTable table) {
				table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(40);

			}
		};
		private String columnName;
		private ForExcel isForExcel;

		private TransportColumn(String aColumnName, ForExcel useForExcel) {
			columnName = aColumnName;
			isForExcel = useForExcel;
		}

		public String getColumnName() {
			return columnName;
		}

		public abstract Object getValue(Transportable transportable, Map<String, String> statusMap,
				StatusCheckerInterface<Transportable> gavlChecker, StatusCheckerInterface<Transportable> takstolChecker,
				StatusCheckerInterface<Transportable> steinChecker,
				StatusCheckerInterface<Transportable> gulvsponChecker);

		public abstract Class<?> getColumnClass();

		public abstract void setCellRenderer(JXTable table);

		public abstract void setPrefferedWidth(JXTable table);

		public static String[] getColumnNames(ForExcel forExcel) {
			TransportColumn[] transportColumns = TransportColumn.values();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 0; i < transportColumns.length; i++) {
				@SuppressWarnings("unused")
				boolean added = transportColumns[i].getForExcel().isEqual(forExcel)
						? columnNameList.add(transportColumns[i].getColumnName()) : false;
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		public static List<TransportColumn> getTableColumns() {
			TransportColumn[] transportColumns = TransportColumn.values();
			List<TransportColumn> columns = new ArrayList<TransportColumn>();
			for (int i = 0; i < transportColumns.length; i++) {
				@SuppressWarnings("unused")
				boolean added = transportColumns[i].getForExcel().isEqual(ForExcel.TABLE)
						? columns.add(transportColumns[i]) : false;

			}
			return columns;
		}

		public ForExcel getForExcel() {
			return isForExcel;
		}

		public enum ForExcel {
			EXCEL, TABLE, BOTH;

			public boolean isEqual(ForExcel forExcel) {
				return this == forExcel || this == BOTH;
			}

		}
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
		Transportable transportable = (Transportable) getRow(rowIndex);

		if (TransportColumn.ForExcel.EXCEL.equals(isForExcel)) {
			if (!Hibernate.isInitialized(transportable.getOrderLines())
					|| !Hibernate.isInitialized(transportable.getCollies())
					|| !Hibernate.isInitialized(transportable.getOrderComments())) {
				if (transportable instanceof PostShipment) {
					PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
							.getBean("postShipmentManager");
					postShipmentManager
							.lazyLoad((PostShipment) transportable,
									new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.ORDER_LINES,
											LazyLoadPostShipmentEnum.COLLIES,
											LazyLoadPostShipmentEnum.ORDER_COMMENTS });
				} else {
					OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");

					orderManager.lazyLoadOrder((Order) transportable, new LazyLoadOrderEnum[] {
							LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.COMMENTS });

				}
			}
		}

		Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());

		String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
		return TransportColumn.valueOf(columnName).getValue(transportable, statusMap, gavlChecker, takstolChecker,
				steinChecker, gulvsponChecker);

	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
		return TransportColumn.valueOf(columnName).getColumnClass();
	}

	/**
	 * Setter inn rad med nytt objekt
	 * 
	 * @param index
	 * @param transportable
	 */
	public void insertRow(int index, Transportable transportable) {
		list.add(index, transportable);
	}

	/**
	 * Fjerner rad fra tabell
	 * 
	 * @param index
	 */
	public void removeRow(int index) {
		if (index < list.size()) {
			list.remove(index);
		}
	}

}