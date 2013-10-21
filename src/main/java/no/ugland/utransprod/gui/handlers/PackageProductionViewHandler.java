package no.ugland.utransprod.gui.handlers;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.FontUIResource;

import no.ugland.utransprod.gui.TextTicker;
import no.ugland.utransprod.gui.model.NokkelProduksjonVModel;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.service.InfoManager;
import no.ugland.utransprod.service.NokkelProduksjonVManager;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;

/**
 * Hjelpeklasse for visning av standalone vindu med produksjonstall og info
 * 
 * @author atle.brekka
 * 
 */
public class PackageProductionViewHandler {
	/**
	 * 
	 */
	private NokkelProduksjonVManager nokkelProduksjonVManager;

	/**
	 * 
	 */
	private InfoManager infoManager;

	/**
	 * 
	 */
	private PresentationModel presentationModelProduksjon;

	/**
	 * 
	 */
	private PresentationModel presentationModelSumProduksjon;

	/**
	 * 
	 */
	PresentationModel presentationModelInfo;

	/**
	 * 
	 */
	private Integer currentYear;

	/**
	 * 
	 */
	private Integer currentWeek;

	/**
	 * 
	 */
	private Font font;

	/**
	 * 
	 */
	private Font fontInfo;

	/**
	 * 
	 */
	TextTicker textTicker;

	/**
	 * 
	 */
	public PackageProductionViewHandler() {
		String prodFrontParam = ApplicationParamUtil
				.findParamByName("prod_font");
		String infoFrontParam = ApplicationParamUtil
				.findParamByName("info_font");

		font = new FontUIResource("Arial", Font.BOLD, Integer
				.valueOf(prodFrontParam));
		fontInfo = new FontUIResource("Arial", Font.BOLD, Integer
				.valueOf(infoFrontParam));

		currentYear = Util.getCurrentYear();
		currentWeek = Util.getCurrentWeek();
		nokkelProduksjonVManager = (NokkelProduksjonVManager) ModelUtil
				.getBean("nokkelProduksjonVManager");
		infoManager = (InfoManager) ModelUtil.getBean("infoManager");

		refresh();

	}

	/**
	 * Oppdaterer info
	 */
	public void refresh() {
		NokkelProduksjonV nokkelProduksjonV = nokkelProduksjonVManager
				.findByWeek(currentYear, currentWeek);
		if (nokkelProduksjonV == null) {
			nokkelProduksjonV = new NokkelProduksjonV(null, 0, BigDecimal
					.valueOf(0), 0, null, BigDecimal.valueOf(0), BigDecimal
					.valueOf(0), BigDecimal.valueOf(0));
		}
		if (presentationModelProduksjon == null) {
			presentationModelProduksjon = new PresentationModel(
					new NokkelProduksjonVModel(nokkelProduksjonV));
		} else {
			presentationModelProduksjon.setBean(new NokkelProduksjonVModel(
					nokkelProduksjonV));
		}

		NokkelProduksjonV sumNokkelProduksjonV = nokkelProduksjonVManager
				.aggreagateYearWeek(new YearWeek(currentYear, currentWeek),"Garasje villa");
		if (sumNokkelProduksjonV == null) {
			sumNokkelProduksjonV = new NokkelProduksjonV(null, 0, BigDecimal
					.valueOf(0), 0, null, BigDecimal.valueOf(0), BigDecimal
					.valueOf(0), BigDecimal.valueOf(0));
		}
		if (presentationModelSumProduksjon == null) {
			presentationModelSumProduksjon = new PresentationModel(
					new NokkelProduksjonVModel(sumNokkelProduksjonV));
		} else {
			presentationModelSumProduksjon.setBean(new NokkelProduksjonVModel(
					sumNokkelProduksjonV));
		}

		List<String> textInfo = infoManager.findListByDate(Util
				.getCurrentDate());
		if (presentationModelInfo == null) {
			presentationModelInfo = new PresentationModel(new InfoHolder());
		}
		presentationModelInfo.setValue(InfoHolder.PROPERTY_TEXTS, textInfo);

	}

	/**
	 * Lager textticker for informasjon som skal vises i vindu
	 * 
	 * @return textticker
	 */
	@SuppressWarnings("unchecked")
	public TextTicker getTextTicker() {
		presentationModelInfo.addBeanPropertyChangeListener(
				InfoHolder.PROPERTY_TEXTS, new InfoChangeListener());
		textTicker = new TextTicker((List<String>) presentationModelInfo
				.getValue(InfoHolder.PROPERTY_TEXTS), fontInfo);
		return textTicker;
	}

	/**
	 * Lager label for budsjett
	 * 
	 * @return label
	 */
	public JLabel getLabelBudget() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		JLabel label = new JLabel("Budsjett");
		label.setFont(font);
		panel.add(label);
		return label;
	}

	/**
	 * Lager label for virkelig produksjon
	 * 
	 * @return label
	 */
	public JLabel getLabelReal() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEtchedBorder());
		JLabel label = new JLabel("Reelt");
		label.setFont(font);
		panel.add(label);
		return label;
	}

	/**
	 * Lager label for avvik
	 * 
	 * @return label
	 */
	public JLabel getLabelDeviation() {
		JLabel label = new JLabel("Avvik(kr)");
		label.setFont(font);
		return label;
	}

	/**
	 * Lager label for avvik i prosent
	 * 
	 * @return label
	 */
	public JLabel getLabelDeviationProcent() {
		JLabel label = new JLabel("Avvik(%)");
		label.setFont(font);
		return label;
	}

	/**
	 * Lager label for akkumulert
	 * 
	 * @return label
	 */
	public JLabel getLabelSum() {
		JLabel label = new JLabel("Akkumulert:");
		label.setFont(font);
		return label;
	}

	/**
	 * Lager label for uke
	 * 
	 * @return label
	 */
	public JLabel getLabelWeek() {
		JLabel label = new JLabel("Uke " + String.valueOf(currentWeek) + ":");
		label.setFont(font);
		return label;
	}

	/**
	 * Lager label for avvik i kroner
	 * 
	 * @return label
	 */
	public JLabel getLabelDeviationKr() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_DEVIATION_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for avvik i prosent
	 * 
	 * @return label
	 */
	public JLabel getLabelDeviationProc() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_DEVIATION_PROC_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for aggregert avvik i kroner
	 * 
	 * @return label
	 */
	public JLabel getLabelAggregateDeviationKr() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelSumProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_DEVIATION_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for aggregert avvik i prosent
	 * 
	 * @return label
	 */
	public JLabel getLabelAggregateDeviationProc() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelSumProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_DEVIATION_PROC_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for virkelig produksjon for gjeldende uke
	 * 
	 * @return label
	 */
	public JLabel getLabelRealWeek() {

		JLabel label = BasicComponentFactory
				.createLabel(presentationModelProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_PACKAGE_SUM_WEEK_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for aggregert virkelig tall
	 * 
	 * @return label
	 */
	public JLabel getLabelAggregateReal() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelSumProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_PACKAGE_SUM_WEEK_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for ukesbudsjett
	 * 
	 * @return label
	 */
	public JLabel getLabelBudgetWeek() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_VALUE_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Lager label for aggregert budsjett
	 * 
	 * @return label
	 */
	public JLabel getLabelAggregateBudget() {
		JLabel label = BasicComponentFactory
				.createLabel(presentationModelSumProduksjon
						.getModel(NokkelProduksjonVModel.PROPERTY_BUDGET_VALUE_STRING));
		label.setFont(font);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		return label;
	}

	/**
	 * Klasse som holder info som skal vises i vindu
	 * 
	 * @author atle.brekka
	 * 
	 */
	public class InfoHolder extends Model {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public static final String PROPERTY_TEXTS = "texts";

		/**
		 * 
		 */
		private List<String> texts = new ArrayList<String>();

		/**
		 * @return tekst som skal vises
		 */
		public List<String> getTexts() {
			return texts;
		}

		/**
		 * @param newTexts
		 */
		public void setTexts(List<String> newTexts) {
			List<String> oldList = new ArrayList<String>(texts);
			texts.clear();
			texts.addAll(newTexts);
			if (!Util.isListsSame(oldList, texts)) {
				firePropertyChange(PROPERTY_TEXTS, oldList, newTexts);
			}
		}

	}

	/**
	 * Håndterer endring av info som skal vises
	 * 
	 * @author atle.brekka
	 * 
	 */
	class InfoChangeListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		@SuppressWarnings("unchecked")
		public void propertyChange(PropertyChangeEvent arg0) {
			textTicker.setTexts((List<String>) presentationModelInfo
					.getValue(InfoHolder.PROPERTY_TEXTS));

		}

	}
}
