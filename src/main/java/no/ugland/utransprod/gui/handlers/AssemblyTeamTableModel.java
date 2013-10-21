/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import java.util.List;

import javax.swing.ListModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Lists;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.list.ArrayListModel;

/**
 * Tabellmodell for montering
 * 
 * @author atle.brekka
 * 
 */
public final class AssemblyTeamTableModel extends AbstractTableAdapter {

	/**
	 * 
	 */
	private final ArrayListModel list;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private WindowInterface window;

	/**
	 * 
	 */
	//private static final String[] COLUMNS = { "Sent", "Montert", "Ordre","Planlagt", "Opprinnelig", "Overtid", "Sendt mangler","Montert" };

	/**
	 * @param listModel
	 * @param aAssemblyList
	 * @param aWindow
	 */
	AssemblyTeamTableModel(ListModel listModel, ArrayListModel aAssemblyList,
			WindowInterface aWindow) {
		//super(listModel, COLUMNS);
		super(listModel, AssemblyTeamColumn.getColumnNames());
		window = aWindow;
		list = aAssemblyList;

	}

	/**
	 * Henter ordre for gitt rad
	 * 
	 * @param rowIndex
	 * @return ordre
	 */
	public Order getOrder(int rowIndex) {
		return ((Assembly) getRow(rowIndex)).getOrder();
	}

	/**
	 * Setter caching for manglende kollier
	 * 
	 * @param order
	 * @param window
	 */
	private void setHasMissingCollies(Order order, WindowInterface window) {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean("orderManager");
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
				LazyLoadOrderEnum.ORDER_LINES,
				LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });

		List<OrderLine> orderLines = order.getMissingCollies();
		if (orderLines != null && orderLines.size() != 0) {
			order.setHasMissingCollies(1);
		} else {
			order.setHasMissingCollies(0);
		}
		try {
			orderManager.saveOrder(order);
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
			e.printStackTrace();
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
		Assembly assembly = (Assembly) getRow(rowIndex);
		String columnName = StringUtils.upperCase(getColumnName(columnIndex))
		.replaceAll(" ", "_");
		
		Order order = assembly.getOrder();
		Deviation deviation = assembly.getDeviation();

		if (order != null && order.getSent() != null
				&& order.getHasMissingCollies() == null) {
			setHasMissingCollies(order, window);
		}
		
		return AssemblyTeamColumn.valueOf(columnName).getValue(assembly,order,deviation);
		
		
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int column) {
		String columnName = StringUtils.upperCase(getColumnName(column))
		.replaceAll(" ", "_");
		return AssemblyTeamColumn.valueOf(columnName).getColumnClass();
		
	}

	/**
	 * Setter inn rad
	 * 
	 * @param index
	 * @param assembly
	 */
	public void insertRow(int index, Assembly assembly) {
		list.add(index, assembly);
		fireTableDataChanged();
	}

	/**
	 * Fjerner rad
	 * 
	 * @param index
	 */
	public void removeRow(int index) {
		if (index < list.size()) {
			list.remove(index);
			fireTableDataChanged();
		}
	}
	
	enum AssemblyTeamColumn{
		SENT("Sent") {
			@Override
			public Object getValue(Assembly assembly,Order order,Deviation deviation) {
				if (order != null) {
					if (assembly.getOrder().getSent() != null) {
						return true;

					}
				} else if (deviation != null) {
					if (deviation.getPostShipment() != null) {
						if (deviation.getPostShipment().getSent() != null) {
							return true;
						}
					} else {
						return true;
					}
				}
				return false;
			}

			@Override
			public Class<?> getColumnClass() {
				return Boolean.class;
			}
		},
				MONTERT("Montert") {
					@Override
					public Object getValue(Assembly assembly, Order order,
							Deviation deviation) {
						return assembly.getAssembliedBool();
					}

					@Override
					public Class<?> getColumnClass() {
						return Boolean.class;
					}
				},
				ORDRE("Ordre") {
					@Override
					public Object getValue(Assembly assembly, Order order,
							Deviation deviation) {
						if (order != null) {
							return assembly.getOrder();
						}
						return deviation;
					}

					@Override
					public Class<?> getColumnClass() {
						return Transportable.class;
					}
				},
			PLANLAGT("Planlagt") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					return assembly.getPlanned();
				}

				@Override
				public Class<?> getColumnClass() {
					return String.class;
				}
			},
			OPPRINNELIG("Opprinnelig") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					return assembly.getFirstPlanned();
				}

				@Override
				public Class<?> getColumnClass() {
					return String.class;
				}
			},
			OVERTID("Overtid") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					if (assembly.getAssembliedDate() == null) {
						Integer yearWeek = Integer.valueOf(String.valueOf(Util
								.getCurrentYear())
								+ String.format("%02d", Util.getCurrentWeek()));
						Integer assemblyYearWeek = Integer.valueOf(String
								.valueOf(assembly.getAssemblyYear())
								+ String.format("%02d", assembly.getAssemblyWeek()));

						if (assemblyYearWeek < yearWeek) {
							return 1;
						}
					}
					return 0;
				}

				@Override
				public Class<?> getColumnClass() {
					return Integer.class;
				}
			},
			SENDT_MANGLER("Sendt mangler") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					if (order != null && order.getSent() != null) {
						return order.getHasMissingCollies();
					}
					return 0;
				}

				@Override
				public Class<?> getColumnClass() {
					return Integer.class;
				}
			},
			MONT("Mont") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					 if(assembly.getAssembliedDate()!=null){
			                return "Ja";
			            }
			            return "Nei";
				}

				@Override
				public Class<?> getColumnClass() {
					return String.class;
				}
			},
			PRODUKTOMRÅDE("Produktområde") {
				@Override
				public Object getValue(Assembly assembly, Order order,
						Deviation deviation) {
					if(order!=null){
						return order.getProductAreaGroup();
					}
					if(deviation!=null){
						return deviation.getProductAreaGroup();
					}
					 return null;
				}

				@Override
				public Class<?> getColumnClass() {
					return ProductAreaGroup.class;
				}
			};
		
		private static final List<String> COLUMN_NAMES=Lists.newArrayList();
		
		static{
			for(AssemblyTeamColumn col:AssemblyTeamColumn.values()){
				COLUMN_NAMES.add(col.getColumnName());
			}
		}
		
		private String columnName;

		private AssemblyTeamColumn(String aColumnName){
			columnName=aColumnName;
		}
		
		public abstract Class<?> getColumnClass();

		public abstract Object getValue(Assembly assembly,Order order,Deviation deviation);

		public String getColumnName(){
			return columnName;
		}
		
		public static String[] getColumnNames(){
			String[] columnNames=new String[COLUMN_NAMES.size()];
			return  COLUMN_NAMES.toArray(columnNames);
		}
	}

}