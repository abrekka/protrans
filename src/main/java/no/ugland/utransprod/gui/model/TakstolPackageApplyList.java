package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ArticlePackageViewFactory;
import no.ugland.utransprod.gui.ArticleProductionPackageView;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ArticlePackageViewHandler.PackProduction;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.GulvsponPackageV;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.TakstolPackageV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer setting av at takstoler er ferdig pakket
 * 
 * @author atle.brekka
 */
public class TakstolPackageApplyList extends PackageApplyList {
	private ArticleProductionPackageView articlePackageView;
	private String mainArticleName;

	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public TakstolPackageApplyList(final TakstolPackageVManager manager,
			final VismaFileCreator vismaFileCreator, final Login aLogin,
			@Named(value = "takstol_article") final String aMainArticleName,
			@Named("takstolArticle") ArticleType articleTypeTakstol,
			final ArticlePackageViewFactory aArticlePackageViewFactory,
			ManagerRepository aManagerRepository) {
		super(aLogin, manager, "Takstol", "Standard takstol",
				ReportEnum.TAKSTOL, vismaFileCreator, aManagerRepository);
		articlePackageView = aArticlePackageViewFactory.create(
				articleTypeTakstol, this, "Takstol");
		mainArticleName = aMainArticleName;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getTableModelReport(javax.swing.ListModel,
	 *      org.jdesktop.swingx.JXTable,
	 *      com.jgoodies.binding.list.SelectionInList)
	 */
	@Override
	public TableModel getTableModelReport(ListModel listModel, JXTable table,
			SelectionInList objectSelectionList) {
		return new TakstolPackageTableModelReport(objectSelectionList,table,objectSelectionList);
	}

	private static enum TakstolPackageColumn {
		TRANSPORT {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				return takstolPackageV.getTransportDetails();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		ORDER {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				StringBuffer buffer = new StringBuffer(takstolPackageV
						.getCustomerDetails());

				buffer.append(" - ").append(takstolPackageV.getOrderNr())
						.append(", ").append(takstolPackageV.getAddress())
						.append(" ,").append(
								takstolPackageV.getConstructionTypeName())
						.append(",").append(takstolPackageV.getInfo());

				return buffer.toString();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		NUMBER_OF_ITEMS {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				return takstolPackageV.getNumberOfItems();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		},
		SPECIFICATION {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				String spesifikasjon="";
				if (takstolPackageV.getOrdln() != null&&takstolPackageV.getOrdln().getDescription()!=null) {
					spesifikasjon=takstolPackageV.getOrdln().getDescription()+" ";
				}
				return spesifikasjon + Util.removeNoAttributes(takstolPackageV
						.getAttributeInfo(),"Papp","Grunnet");
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		LOADING_DATE {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				if (takstolPackageV.getTransportDetails() != null) {
					Date loadingDate = takstolPackageV.getLoadingDate();
					if (loadingDate != null) {
						return Util.SHORT_DATE_FORMAT.format(loadingDate);
					}
					return null;
				}
				return null;
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		STARTED {
			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				if (takstolPackageV.getActionStarted() != null) {
					return "X";
				}
				return "";
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		PACKED {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(TakstolPackageV takstolPackageV) {
				if (takstolPackageV.getColli() != null) {
					return "X";
				}
				return "";
			}
		};

		public static String[] getColumnNames() {

			List<String> columnNameList = new ArrayList<String>();
			for (TakstolPackageColumn column : TakstolPackageColumn.values()) {
				columnNameList.add(column.name());
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		public abstract Object getValue(TakstolPackageV takstolPackageV);

		public abstract Class<?> getColumnClass();
	}

	@SuppressWarnings("serial")
	private final class TakstolPackageTableModelReport extends
	AbstractTableModel {


		private JXTable table;

		private SelectionInList objectSelectionList;

		public TakstolPackageTableModelReport(final ListModel listModel,
                final JXTable aTable, final SelectionInList aSelectionInList) {
			table = aTable;
            objectSelectionList = aSelectionInList;
//			super(listModel, TakstolPackageColumn.getColumnNames());
		}
		
		public int getColumnCount() {
            return TakstolPackageColumn.getColumnNames().length;
        }
		
		public int getRowCount() {
            return table.getRowCount();
        }
		public String getColumnName(final int colIndex) {
            return TakstolPackageColumn.getColumnNames()[colIndex];
        }

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			TakstolPackageV takstolPackageV = (TakstolPackageV) objectSelectionList
                    .getElementAt(table.convertRowIndexToModel(rowIndex));
			
//			TakstolPackageV takstolPackageV = (TakstolPackageV) getRow(rowIndex);
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_");

			return TakstolPackageColumn.valueOf(columnName).getValue(
					takstolPackageV);
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_");
			return TakstolPackageColumn.valueOf(columnName).getColumnClass();
		}

	}

	public void setApplied(PackableListItem object, boolean applied,
			WindowInterface window, List<Applyable> relatedArticles) {
		try {
			if (object != null) {
				List<OrderLine> orderLines = new ArrayList<OrderLine>();
				List<Applyable> objects = new ArrayList<Applyable>();
				object.setColli(applied ? Colli.DEFAULT_TAKSTOL : null);
				if (object.getOrderLineId() != null) {
					objects.add(object);
				}

				if (relatedArticles != null && relatedArticles.size() != 0) {
					objects.addAll(relatedArticles);
				}
				for (Applyable item : objects) {
					item.setColli(Colli.UNKNOWN);
				}
				applyRelatedArticles(applied, window, objects, orderLines);

				if (applied) {

					articlePackageView
							.setArticles(objects, PackProduction.PACK);
					Util.showEditViewable(articlePackageView, window);
				}

				applyRelatedArticles(applied, window, objects, orderLines);
				
				if (orderLines.size() != 0) {
					createVismaFile(orderLines);
				}

				OrderLine orderLine = managerRepository.getOrderLineManager()
						.findByOrderLineId(object.getOrderLineId());
				checkCompleteness(orderLine, applied);
				refreshObjects(object);
			}
		} catch (ProTransException e1) {
			Util.showErrorDialog(window, "Feil", e1.getMessage());
			e1.printStackTrace();
		}

	}

	private void applyRelatedArticles(boolean applied, WindowInterface window,
			List<Applyable> relatedArticles, List<OrderLine> orderLines) {
		if (relatedArticles != null) {
			for (Applyable item : relatedArticles) {
				item.setColli(applied ? item.getColli() : null);
				String aColliName = Util.getColliName(item.getArticleName());

				OrderLine orderLine = applyObject((PackableListItem) item, item
						.getColli() != null, window, aColliName, false);
				boolean added = item.getColli() != null && orderLine != null ? orderLines
						.add(orderLine)
						: false;

			}
		}
	}

	private OrderLine applyObject(PackableListItem object, boolean applied,
			WindowInterface window, final String aColliName,
			final boolean isMainArticle) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
				.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object
				.getOrderLineId());
		OrderLine appliedOrderLine = null;
		if (orderLine != null) {

			appliedOrderLine = handleApply(applied, window, orderLineManager,
					orderLine, aColliName, isMainArticle);
			checkAndSavePostShipment(orderLine);

			saveOrder(window, orderLineManager, orderLine);
			refreshObjects(object);
		}
		return appliedOrderLine;
	}

	private void refreshObjects(PackableListItem object) {
		if (object.getOrderLineId() != null) {
			applyListManager.refresh(object);
		}
		List<Applyable> relatedObjects = object.getRelatedArticles();
		if (relatedObjects != null) {
			for (Applyable applyable : relatedObjects) {
				applyListManager.refresh((PackableListItem) applyable);
			}
		}

	}

	public PackableListItem getApplyObject(final Transportable transportable,
			final WindowInterface window) {
		List<PackableListItem> list = ((TakstolPackageVManager) applyListManager)
				.findApplyableByOrderNrAndArticleName(transportable.getOrder()
						.getOrderNr(), mainArticleName);

		return list == null || list.size() == 0 ? null : list != null
				&& list.size() == 1 ? list.get(0) : selectApplyObject(list,
				window);
	}

	private PackableListItem selectApplyObject(
			final List<PackableListItem> list, final WindowInterface window) {
		return (PackableListItem) Util.showOptionsDialogCombo(window, list,
				"Velg artikkel", true, null);
	}

}
