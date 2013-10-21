package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.TakstolProductionApplyList;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.TakstolProductionV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

public class TakstolProductionViewHandler extends ProductionViewHandler
		implements OrderNrProvider {

	private final ArrayListModel productionUnitList;
	private final SelectionInList productionUnitSelectionList;
	private JButton buttonShowTakstolInfo;
	private JButton buttonStartetKapping;
	private ShowTakstolInfoActionFactory showTakstolInfoActionFactory;
	private JButton buttonIkkeStartetKapping;
	private JButton buttonFerdigKappet;
	private JButton buttonIkkeFerdigKappet;

	@Inject
	public TakstolProductionViewHandler(
			final TakstolProductionApplyList productionInterface,
			final Login login, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory,
			ShowTakstolInfoActionFactory aShowTakstolInfoActionFactory,
			SetProductionUnitActionFactory setProductionUnitActionFactory) {
		super(productionInterface, "Takstolproduksjon", login, null,
				"produksjon", TableEnum.TABLEPRODUCTIONTAKSTOL,
				managerRepository.getArticleTypeManager().findByName(
						"Takstoler"), managerRepository,
				deviationViewHandlerFactory, setProductionUnitActionFactory);
		showTakstolInfoActionFactory = aShowTakstolInfoActionFactory;
		productionUnitList = new ArrayListModel();
		productionUnitSelectionList = new SelectionInList(
				(ListModel) productionUnitList);
		initProductionUnitList();
	}

	@Override
	protected void setApplied(final Produceable produceable,
			final boolean applied, final WindowInterface window) {
		Util.runInThreadWheel(window.getRootPane(), new SetApplied(applied,produceable,window), null);
//		if (applied) {
//			ProductionUnit productionUnit = setProductionUnit(window,
//					produceable);
//			setProductionUnitForRelatedArticles(produceable, productionUnit,
//					window);
//		}
//		applyListInterface.setApplied(produceable, applied, window);

	}

	private void setProductionUnitForRelatedArticles(Produceable produceable,
			ProductionUnit productionUnit, WindowInterface window) {
		List<Applyable> relatedArticles = produceable.getRelatedArticles();
		if (relatedArticles != null && productionUnit != null) {
			SetProductionUnitAction setProductionUnitAction = setProductionUnitActionFactory
					.create(articleType, null, window);
			for (Applyable applyable : relatedArticles) {
				setProductionUnitAction.setProductionUnitForOrderLine(
						productionUnit, (Produceable) applyable);
			}
		}

	}

	private void initProductionUnitList() {
		List<ProductionUnit> productionUnits = managerRepository
				.getProductionUnitManager().findAll();
		if (productionUnits != null) {
			productionUnitList.addAll(productionUnits);
			productionUnitList.add(0, null);
		}
		productionUnitSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_INDEX,
				new ProductionUnitSelectionListener());
	}

	public final JComboBox getComboBoxProductionUnit() {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(
				productionUnitSelectionList));
		comboBox.setName("ComboBoxProductionUnit");
		return comboBox;
	}

	@Override
	protected boolean getButtonApplyEnabled(Produceable produceable) {
		if (produceable.getProbability() != null
				&& produceable.getProbability() == 90) {
			return false;
		}
		if (produceable != null
				&& (produceable.getProduced() == null || !produceable
						.isRelatedArticlesComplete())) {
			return true;
		}
		return false;
	}

	private class ProductionUnitSelectionListener implements
			PropertyChangeListener {

		public void propertyChange(final PropertyChangeEvent evt) {
			handleFilter();

		}

	}

	final void setAdditionFilters(final List<Filter> filters) {
		ProductionUnit productionUnit = (ProductionUnit) productionUnitSelectionList
				.getSelection();
		if (productionUnit != null) {
			PatternFilter filterProductionUnit = new PatternFilter(
					productionUnit.getProductionUnitName(),
					Pattern.CASE_INSENSITIVE, 8);
			filters.add(filterProductionUnit);
		}
	}

	@Override
	protected TableModel getTableModel(final WindowInterface window) {
		return new TakstolProductionTableModel(getObjectSelectionList());
	}

	@Override
	void setFirstHighlighters() {
		ColorHighlighter bluePattern = new ColorHighlighter(
				new PatternPredicate("[^---]", ProductionColumn.STARTET_KAPPING
						.ordinal()), Color.WHITE, ColorEnum.BLUE.getColor());
		table.addHighlighter(bluePattern);

		ColorHighlighter greenPattern = new ColorHighlighter(
				new PatternPredicate("[^---]", ProductionColumn.KAPPING_FERDIG
						.ordinal()), Color.WHITE, ColorEnum.GREEN.getColor());
		table.addHighlighter(greenPattern);
	}

	@Override
	void setAdditionHighlighters() {
		ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate(
				"[Nei]", ProductionColumn.valueOf("KOMPLETT").ordinal()),
				ColorEnum.YELLOW.getColor(), Color.BLACK);
		table.addHighlighter(pattern);
		ColorHighlighter greyPattern = new ColorHighlighter(
				new PatternPredicate("90", ProductionColumn.valueOf(
						"SANNSYNLIGHET").ordinal()), ColorEnum.GREY.getColor(),
				null);
		table.addHighlighter(greyPattern);

	}

	final class TakstolProductionTableModel extends AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		public TakstolProductionTableModel(final ListModel listModel) {
			super(listModel, ProductionColumn.getColumnNames());
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(final int rowIndex, final int columnIndex) {
			Produceable produceable = (Produceable) getRow(rowIndex);
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_");

			return ProductionColumn.valueOf(columnName).getValue(produceable);
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(final int columnIndex) {
			String columnName = StringUtils.upperCase(
					getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_");
			return ProductionColumn.valueOf(columnName).getColumnClass();
		}

	}

	private enum ProductionColumn {
		TRANSPORT("Transport") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable.getTransportDetails();
			}

			@Override
			public int getColumnWidth() {
				return 100;
			}
		},
		ORDRE("Ordre") {
			@Override
			public Class<?> getColumnClass() {
				return Produceable.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable;
			}

			@Override
			public int getColumnWidth() {
				return 200;
			}
		},
		PROD_DATO("Prod.dato") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return Util.formatDate(produceable.getProductionDate(),
						Util.SHORT_DATE_FORMAT);
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		ANTALL("Antall") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				if (produceable.getNumberOfItems() != null) {
					return decimalFormat.format(produceable.getNumberOfItems());
				}
				return "";
			}

			@Override
			public int getColumnWidth() {
				return 50;
			}
		},
		SPESIFIKASJON("Spesifikasjon") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				if (produceable.getOrdln() != null) {
					return produceable.getOrdln().getDescription();
				}
				return Util.removeNoAttributes(produceable.getAttributeInfo());
			}

			@Override
			public int getColumnWidth() {
				return 400;
			}
		},
		OPPLASTING("Opplasting") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				Date loadingDate = produceable.getLoadingDate();
				if (loadingDate != null) {
					return Util.SHORT_DATE_FORMAT.format(loadingDate);
				}
				return null;
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		PRODUSERT("Produsert") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				if (produceable.getProduced() != null) {
					return Util.SHORT_DATE_FORMAT.format(produceable
							.getProduced());
				}
				return "---";
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		PRODUKTOMRÅDE("Produktområde") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				if (produceable.getProductAreaGroupName() != null) {
					return produceable.getProductAreaGroupName();
				}
				return "";
			}

			@Override
			public int getColumnWidth() {
				return 60;
			}
		},
		PROD_ENHET("Prod.enhet") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable.getProductionUnitName();
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		STARTET("Startet") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				Date started = produceable.getRelatedStartedDate();
				if (started != null) {
					return Util.SHORT_DATE_FORMAT.format(started);
				}
				return "---";
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		ARTIKKEL("Artikkel") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable.getArticleName();
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}
		},
		KOMPLETT("Komplett") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				if (produceable.getProduced() != null) {// hovedartikkel pakket
					return produceable.isRelatedArticlesComplete() ? "Ja"
							: "Nei";// sjekker relaterte artikler
				} else {
					if (produceable.isRelatedArticlesStarted()) {
						return "Nei";
					} else {
						return "Ja";
					}
				}
			}

			@Override
			public int getColumnWidth() {
				return 60;
			}
		},
		SANNSYNLIGHET("Sannsynlighet") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable.getProbability();
			}

			@Override
			public int getColumnWidth() {
				return 50;
			}
		},
		TAKSTOLTEGNER("Takstoltegner") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(Produceable produceable) {
				return produceable.getTrossDrawer();
			}

			@Override
			public int getColumnWidth() {
				return 100;
			}
		},
		STARTET_KAPPING("Startet kapping") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}

			@Override
			public Object getValue(Produceable produceable) {
				TakstolProductionV takstol = (TakstolProductionV) produceable;
				Date started = takstol.getCuttingStarted();
				if (started != null) {
					return Util.SHORT_DATE_FORMAT.format(started);
				}
				return "---";
			}
		},
		KAPPING_FERDIG("Kapping ferdig") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public int getColumnWidth() {
				return 70;
			}

			@Override
			public Object getValue(Produceable produceable) {
				TakstolProductionV takstol = (TakstolProductionV) produceable;
				Date kappingFerdig = takstol.getCuttingDone();
				if (kappingFerdig != null) {
					return Util.SHORT_DATE_FORMAT.format(kappingFerdig);
				}
				return "---";
			}
		};

		private String columnName;
		private static DecimalFormat decimalFormat;
		static {
			decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);
		}

		private ProductionColumn(String aColumnName) {
			columnName = aColumnName;

		}

		public String getColumnName() {
			return columnName;
		}

		public static String[] getColumnNames() {
			ProductionColumn[] productionColumns = ProductionColumn.values();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 0; i < productionColumns.length; i++) {
				columnNameList.add(productionColumns[i].getColumnName());
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		public abstract Object getValue(Produceable produceable);

		public abstract Class<?> getColumnClass();

		public abstract int getColumnWidth();
	}

	public JButton getButtonShowTakstolInfo(WindowInterface window) {
		buttonShowTakstolInfo = new JButton(showTakstolInfoActionFactory
				.create(this, window));
		buttonShowTakstolInfo.setName("ButtonShowTakstolInfo");
		buttonShowTakstolInfo.setEnabled(false);
		return buttonShowTakstolInfo;
	}

	@Override
	protected void buttonsEnabled(final Produceable produceable,
			boolean hasSelection) {
		buttonStartetKapping.setEnabled(false);
		buttonIkkeStartetKapping.setEnabled(false);
		buttonFerdigKappet.setEnabled(false);
		buttonIkkeFerdigKappet.setEnabled(false);
		if (hasSelection) {
			TakstolProductionV takstolProductionV = (TakstolProductionV) produceable;
			if (takstolProductionV.getProbability() != null
					&& takstolProductionV.getProbability() == 100
					&& takstolProductionV.getCuttingStarted() == null
					&& takstolProductionV.getCuttingDone() == null
					&& takstolProductionV.getActionStarted() == null
					&& takstolProductionV.getProduced() == null) {
				buttonStartetKapping.setEnabled(true);
			}
			if (takstolProductionV.getCuttingStarted() != null
					&& takstolProductionV.getActionStarted() == null
					&& takstolProductionV.getProduced() == null
					&& takstolProductionV.getCuttingDone() == null) {
				buttonIkkeStartetKapping.setEnabled(true);
			}
			if (takstolProductionV.getProbability() != null
					&& takstolProductionV.getProbability() == 100
					&& takstolProductionV.getCuttingDone() == null
					&& takstolProductionV.getActionStarted() == null
					&& takstolProductionV.getProduced() == null) {
				buttonFerdigKappet.setEnabled(true);
			}
			if (takstolProductionV.getCuttingDone() != null
					&& takstolProductionV.getProduced() == null
					&& takstolProductionV.getActionStarted() == null) {
				buttonIkkeFerdigKappet.setEnabled(true);
			}
		}
		if (buttonShowTakstolInfo != null) {
			buttonShowTakstolInfo.setEnabled(hasSelection);
		}
	}

	public String getSelectedOrderNr() {
		Produceable produceable = getSelectedObject();
		return produceable.getOrderNr();
	}

	@Override
	void initColumnWidthExt() {
		List<TableColumn> columns = table.getColumns();
		for (TableColumn col : columns) {
			String columnHeader = StringUtils.replace(StringUtils
					.replace(StringUtils.upperCase(String.valueOf(col
							.getHeaderValue())), " ", "_"), ".", "_");
			ProductionColumn productionColumn = ProductionColumn
					.valueOf(columnHeader);
			table.getColumnExt(productionColumn.getColumnName())
					.setPreferredWidth(productionColumn.getColumnWidth());
		}

	}

	public JButton getButtonStartetKapping(WindowInterface window) {
		buttonStartetKapping = new JButton(new StartetKappingAction());
		buttonStartetKapping.setName("ButtonStartetKapping");
		buttonStartetKapping.setEnabled(false);
		return buttonStartetKapping;
	}

	public JButton getButtonIkkeStartetKapping(WindowInterface window) {
		buttonIkkeStartetKapping = new JButton(new IkkeStartetKappingAction());
		buttonIkkeStartetKapping.setName("ButtonIkkeStartetKapping");
		buttonIkkeStartetKapping.setEnabled(false);
		return buttonIkkeStartetKapping;
	}

	public JButton getButtonFerdigKappet(WindowInterface window) {
		buttonFerdigKappet = new JButton(new FerdigKappetAction());
		buttonFerdigKappet.setName("ButtonFerdigKappet");
		buttonFerdigKappet.setEnabled(false);
		return buttonFerdigKappet;
	}

	public JButton getButtonIkkeFerdigKappet(WindowInterface window) {
		buttonIkkeFerdigKappet = new JButton(new IkkeFerdigKappetAction());
		buttonIkkeFerdigKappet.setName("ButtonIkkeFerdigKappet");
		buttonIkkeFerdigKappet.setEnabled(false);
		return buttonIkkeFerdigKappet;
	}

	@SuppressWarnings("serial")
	private class StartetKappingAction extends AbstractAction {

		public StartetKappingAction() {
			super("Startet kapping");
		}

		public void actionPerformed(ActionEvent e) {
			settStartetKapping(Util.getCurrentDate());

		}

	}

	private void settStartetKapping(Date startetKappingDato) {
		Produceable produceable = getSelectedObject();
		((TakstolProductionApplyList) applyListInterface).settStartetKapping(
				produceable, startetKappingDato);
	}

	private void settKappingFerdig(Date kappingFerdigDato) {
		Produceable produceable = getSelectedObject();
		((TakstolProductionApplyList) applyListInterface).settKappingFerdig(
				produceable, kappingFerdigDato);
	}

	@SuppressWarnings("serial")
	private class IkkeStartetKappingAction extends AbstractAction {

		public IkkeStartetKappingAction() {
			super("Ikke startet kapping");
		}

		public void actionPerformed(ActionEvent e) {
			settStartetKapping(null);

		}

	}

	@SuppressWarnings("serial")
	private class FerdigKappetAction extends AbstractAction {

		public FerdigKappetAction() {
			super("Ferdig kappet");
		}

		public void actionPerformed(ActionEvent e) {
			settKappingFerdig(Util.getCurrentDate());

		}

	}

	@SuppressWarnings("serial")
	private class IkkeFerdigKappetAction extends AbstractAction {

		public IkkeFerdigKappetAction() {
			super("Ikke ferdig kappet");
		}

		public void actionPerformed(ActionEvent e) {
			settKappingFerdig(null);

		}

	}
	
	private class SetApplied implements Threadable{
		private boolean applied;
		private Produceable produceable;
		private WindowInterface window;
		public SetApplied(boolean applied,Produceable produceable,WindowInterface window){
			this.applied=applied;
			this.window=window;
			this.produceable=produceable;
		}

		public void enableComponents(boolean enable) {
			// TODO Auto-generated method stub
			
		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			if(applied){
			labelInfo.setText("Setter produsert...");
			}else{
				labelInfo.setText("Setter ikke produsert...");
			}
			if (applied) {
				ProductionUnit productionUnit = setProductionUnit(window,
						produceable);
				setProductionUnitForRelatedArticles(produceable, productionUnit,
						window);
			}
			applyListInterface.setApplied(produceable, applied, window);
			return null;
		}

		public void doWhenFinished(Object object) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
