package no.ugland.utransprod.gui.handlers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.TakstolPackageApplyList;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

public class TakstolPackageViewHandler extends PackageViewHandler {

	public TakstolPackageViewHandler(TakstolPackageApplyList packageInterface, final Login login, TableEnum tableEnum,
			final String aMainArticleName, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super(login, managerRepository, deviationViewHandlerFactory, packageInterface, "Pakking standard takstol",
				tableEnum, aMainArticleName);

	}

	protected void setApplied(PackableListItem packable, boolean applied, WindowInterface window) {
		((TakstolPackageApplyList) applyListInterface).setApplied(packable, applied, window,
				packable.getRelatedArticles());
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setAdditionHighlighters()
	 */
	void setAdditionHighlighters() {
		ColorHighlighter pattern = new ColorHighlighter(
				new PatternPredicate("[Nei]", PackageColumn.valueOf("KOMPLETT").ordinal()), ColorEnum.YELLOW.getColor(),
				null);
		table.addHighlighter(pattern);
		ColorHighlighter greyPattern = new ColorHighlighter(
				new PatternPredicate("90", PackageColumn.valueOf("SANNSYNLIGHET").ordinal()), ColorEnum.GREY.getColor(),
				null);
		table.addHighlighter(greyPattern);
		ColorHighlighter greenPattern = new ColorHighlighter(
				new PatternPredicate("0", PackageColumn.valueOf("REST").ordinal()), null, ColorEnum.GREEN.getColor());
		table.addHighlighter(greenPattern);

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.PackageViewHandler#getButtonApplyEnabled(no.ugland.utransprod.model.PackableListItem)
	 */
	@Override
	protected boolean getButtonApplyEnabled(PackableListItem packable) {
		if (packable != null && (packable.getColli() == null || !packable.isRelatedArticlesComplete())
				&& (packable.getProbability() == null || packable.getProbability() != 90)) {
			return true;
		}
		return false;
	}

	@Override
	protected TableModel getTableModel(WindowInterface window) {
		return new TakstolPackageTableModel(getObjectSelectionList());
	}

	/**
	 * Legger til filter for kun å vise hovedartiklene
	 * 
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setAdditionFilters(java.util.List)
	 */
	void setAdditionFilters(List<Filter> filters) {
		Filter filterArticle = new PatternFilter(mainArticleName, Pattern.CASE_INSENSITIVE,
				PackageColumn.valueOf("ARTIKKEL").ordinal());
		filters.add(filterArticle);
	}

	final class TakstolPackageTableModel extends AbstractTableAdapter {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public TakstolPackageTableModel(ListModel listModel) {
			super(listModel, PackageColumn.getColumnNames());
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			PackableListItem packable = (PackableListItem) getRow(rowIndex);
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
			return PackageColumn.valueOf(columnName).getValue(packable);
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
			return PackageColumn.valueOf(columnName).getColumnClass();
		}

	}

	public enum PackageColumn {
		TRANSPORT("Transport") {
			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getTransportDetails();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		ORDRE("Ordre") {
			@Override
			public Object getValue(PackableListItem packable) {
				return packable;
			}

			@Override
			public Class<?> getColumnClass() {
				return Packable.class;
			}
		},
		ANTALL("Antall") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getNumberOfItems();
			}
		},
		SPESIFIKASJON("Spesifikasjon") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				String spesifikasjon = "";
				if (packable.getOrdln() != null && packable.getOrdln().getDescription() != null) {
					spesifikasjon = packable.getOrdln().getDescription() + " ";
				}
				return spesifikasjon + Util.removeNoAttributes(packable.getAttributeInfo(), "Papp", "Grunnet");

			}
		},
		OPPLASTING("Opplasting") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				if (packable.getTransportDetails() != null) {
					Date loadingDate = packable.getLoadingDate();
					if (loadingDate != null) {
						return Util.SHORT_DATE_FORMAT.format(loadingDate);
					}
					return null;
				}
				return null;
			}
		},
		PAKKET("Pakket") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				if (packable.getColli() != null) {
					return packable.getColli().toString();
				}
				return "---";
			}
		},
		PRODUKTOMRÅDE("Produktområde") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				if (packable.getProductAreaGroupName() != null) {
					return packable.getProductAreaGroupName();
				}
				return "";
			}
		},
		STARTET("Startet") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				if (packable.getActionStarted() != null) {
					return Util.SHORT_DATE_FORMAT.format(packable.getActionStarted());
				}
				return "---";
			}
		},
		ARTIKKEL("Artikkel") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getArticleName();
			}
		},
		KOMPLETT("Komplett") {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				// return packable.isRelatedArticlesComplete() ? "Ja" : "Nei";
				if (packable.getColli() != null) {// hovedartikkel pakket
					return packable.isRelatedArticlesComplete() ? "Ja" : "Nei";// sjekker
					// relaterte
					// artikler
				} else {
					if (packable.isRelatedArticlesStarted()) {
						return "Nei";
					} else {
						return "Ja";
					}
				}

				// return
				// packable.isRelatedArticlesStarted()&&packable.isRelatedArticlesComplete()
				// ? "Ja" : "Nei";
			}
		},
		SANNSYNLIGHET("Sannsynlighet") {
			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}

			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getProbability();
			}
		},
		REST("Rest") {
			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getColli() != null ? -1 : packable.getRest() == null ? 0 : packable.getRest();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		},
		PRODUKSJONSUKE("Produksjonsuke") {
			@Override
			public Object getValue(PackableListItem packable) {
				return packable.getProductionWeek();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		};
		private String columnName;

		private PackageColumn(String aColumnName) {
			columnName = aColumnName;
		}

		public String getColumnName() {
			return columnName;
		}

		public static String[] getColumnNames() {
			PackageColumn[] packageColumns = PackageColumn.values();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 0; i < packageColumns.length; i++) {
				columnNameList.add(packageColumns[i].getColumnName());
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		public abstract Object getValue(PackableListItem packable);

		public abstract Class<?> getColumnClass();
	}

}
