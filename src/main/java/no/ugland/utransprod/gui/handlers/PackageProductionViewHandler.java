/*     */ package no.ugland.utransprod.gui.handlers;

/*     */
/*     */ import com.jgoodies.binding.PresentationModel;
/*     */ import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;

/*     */ import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
/*     */ import java.math.BigDecimal;
import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.plaf.FontUIResource;
/*     */ import no.ugland.utransprod.gui.TextTicker;
/*     */ import no.ugland.utransprod.gui.handlers.PackageProductionViewHandler.InfoChangeListener;
/*     */ import no.ugland.utransprod.gui.handlers.PackageProductionViewHandler.InfoHolder;
/*     */ import no.ugland.utransprod.gui.model.NokkelProduksjonVModel;
/*     */ import no.ugland.utransprod.model.NokkelProduksjonV;
/*     */ import no.ugland.utransprod.model.NokkelProduksjonVPK;
/*     */ import no.ugland.utransprod.service.InfoManager;
/*     */ import no.ugland.utransprod.service.NokkelProduksjonVManager;
/*     */ import no.ugland.utransprod.util.ApplicationParamUtil;
/*     */ import no.ugland.utransprod.util.ModelUtil;
/*     */ import no.ugland.utransprod.util.Util;
/*     */ import no.ugland.utransprod.util.YearWeek;

/*     */
/*     */ public class PackageProductionViewHandler {
	/*     */ private NokkelProduksjonVManager nokkelProduksjonVManager;
	/*     */ private InfoManager infoManager;
	/*     */ private PresentationModel presentationModelProduksjon;
	/*     */ private PresentationModel presentationModelSumProduksjon;
	/*     */ PresentationModel presentationModelInfo;
	/*     */ private Integer currentYear;
	/*     */ private Integer currentWeek;
	/*     */ private Font font;
	/*     */ private Font fontInfo;
	/*     */ TextTicker textTicker;

	/*     */
	/*     */ public PackageProductionViewHandler() {
		/* 91 */ String prodFrontParam = ApplicationParamUtil.findParamByName("prod_font");
		/*     */
		/* 93 */ String infoFrontParam = ApplicationParamUtil.findParamByName("info_font");
		/*     */
		/*     */
		/* 96 */ this.font = new FontUIResource("Arial", 1, Integer.valueOf(prodFrontParam));
		/*     */
		/* 98 */ this.fontInfo = new FontUIResource("Arial", 1, Integer.valueOf(infoFrontParam));
		/*     */
		/*     */
		/* 101 */ this.currentYear = Util.getCurrentYear();
		/* 102 */ this.currentWeek = Util.getCurrentWeek();
		/* 103 */ this.nokkelProduksjonVManager = (NokkelProduksjonVManager) ModelUtil
				.getBean("nokkelProduksjonVManager");
		/*     */
		/* 105 */ this.infoManager = (InfoManager) ModelUtil.getBean("infoManager");
		/*     */
		/* 107 */ this.refresh();
		/*     */
		/* 109 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public void refresh() {
		/* 115 */ NokkelProduksjonV nokkelProduksjonV = this.nokkelProduksjonVManager.findByWeek(this.currentYear,
				this.currentWeek);
		/*     */
		/* 117 */ if (nokkelProduksjonV == null) {
			/* 118 */ nokkelProduksjonV = new NokkelProduksjonV((NokkelProduksjonVPK) null, 0, BigDecimal.valueOf(0L),
					0, (BigDecimal) null, BigDecimal.valueOf(0L), BigDecimal.valueOf(0L), BigDecimal.valueOf(0L));
			/*     */
			/*     */ }
		/*     */
		/* 122 */ if (this.presentationModelProduksjon == null) {
			/* 123 */ this.presentationModelProduksjon = new PresentationModel(
					new NokkelProduksjonVModel(nokkelProduksjonV));
			/*     */
			/*     */ } else {
			/* 126 */ this.presentationModelProduksjon.setBean(new NokkelProduksjonVModel(nokkelProduksjonV));
			/*     */
			/*     */ }
		/*     */
		/* 130 */ NokkelProduksjonV sumNokkelProduksjonV = (NokkelProduksjonV) this.nokkelProduksjonVManager
				.aggreagateYearWeek(new YearWeek(this.currentYear, this.currentWeek), "Garasje villa");
		/*     */
		/* 132 */ if (sumNokkelProduksjonV == null) {
			/* 133 */ sumNokkelProduksjonV = new NokkelProduksjonV((NokkelProduksjonVPK) null, 0,
					BigDecimal.valueOf(0L), 0, (BigDecimal) null, BigDecimal.valueOf(0L), BigDecimal.valueOf(0L),
					BigDecimal.valueOf(0L));
			/*     */
			/*     */ }
		/*     */
		/* 137 */ if (this.presentationModelSumProduksjon == null) {
			/* 138 */ this.presentationModelSumProduksjon = new PresentationModel(
					new NokkelProduksjonVModel(sumNokkelProduksjonV));
			/*     */
			/*     */ } else {
			/* 141 */ this.presentationModelSumProduksjon.setBean(new NokkelProduksjonVModel(sumNokkelProduksjonV));
			/*     */
			/*     */ }
		/*     */
		/* 145 */ List<String> textInfo = this.infoManager.findListByDate(Util.getCurrentDate());
		/*     */
		/* 147 */ if (this.presentationModelInfo == null) {
			/* 148 */ this.presentationModelInfo = new PresentationModel(new InfoHolder());
			/*     */ }
		/* 150 */ this.presentationModelInfo.setValue("texts", textInfo);
		/*     */
		/* 152 */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public TextTicker getTextTicker() {
		/* 161 */ this.presentationModelInfo.addBeanPropertyChangeListener("texts", new InfoChangeListener());
		/*     */
		/* 163 */ this.textTicker = new TextTicker((List) this.presentationModelInfo.getValue("texts"), this.fontInfo);
		/*     */
		/* 165 */ return this.textTicker;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelBudget() {
		/* 174 */ JPanel panel = new JPanel();
		/* 175 */ panel.setBorder(BorderFactory.createEtchedBorder());
		/* 176 */ JLabel label = new JLabel("Budsjett");
		/* 177 */ label.setFont(this.font);
		/* 178 */ panel.add(label);
		/* 179 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelReal() {
		/* 188 */ JPanel panel = new JPanel();
		/* 189 */ panel.setBorder(BorderFactory.createEtchedBorder());
		/* 190 */ JLabel label = new JLabel("Reelt");
		/* 191 */ label.setFont(this.font);
		/* 192 */ panel.add(label);
		/* 193 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelDeviation() {
		/* 202 */ JLabel label = new JLabel("Avvik(kr)");
		/* 203 */ label.setFont(this.font);
		/* 204 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelDeviationProcent() {
		/* 213 */ JLabel label = new JLabel("Avvik(%)");
		/* 214 */ label.setFont(this.font);
		/* 215 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelSum() {
		/* 224 */ JLabel label = new JLabel("Akkumulert:");
		/* 225 */ label.setFont(this.font);
		/* 226 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelWeek() {
		/* 235 */ JLabel label = new JLabel("Uke " + String.valueOf(this.currentWeek) + ":");
		/* 236 */ label.setFont(this.font);
		/* 237 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelDeviationKr() {
		/* 246 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelProduksjon.getModel("budgetDeviationString"));
		/*     */
		/*     */
		/* 249 */ label.setFont(this.font);
		/* 250 */ label.setHorizontalAlignment(4);
		/* 251 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelDeviationProc() {
		/* 260 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelProduksjon.getModel("budgetDeviationProcString"));
		/*     */
		/*     */
		/* 263 */ label.setFont(this.font);
		/* 264 */ label.setHorizontalAlignment(4);
		/* 265 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelAggregateDeviationKr() {
		/* 274 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelSumProduksjon.getModel("budgetDeviationString"));
		/*     */
		/*     */
		/* 277 */ label.setFont(this.font);
		/* 278 */ label.setHorizontalAlignment(4);
		/* 279 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelAggregateDeviationProc() {
		/* 288 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelSumProduksjon.getModel("budgetDeviationProcString"));
		/*     */
		/*     */
		/* 291 */ label.setFont(this.font);
		/* 292 */ label.setHorizontalAlignment(4);
		/* 293 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelRealWeek() {
		/* 303 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelProduksjon.getModel("packageSumWeekString"));
		/*     */
		/*     */
		/* 306 */ label.setFont(this.font);
		/* 307 */ label.setHorizontalAlignment(4);
		/* 308 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelAggregateReal() {
		/* 317 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelSumProduksjon.getModel("packageSumWeekString"));
		/*     */
		/*     */
		/* 320 */ label.setFont(this.font);
		/* 321 */ label.setHorizontalAlignment(4);
		/* 322 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelBudgetWeek() {
		/* 331 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelProduksjon.getModel("budgetValueString"));
		/*     */
		/*     */
		/* 334 */ label.setFont(this.font);
		/* 335 */ label.setHorizontalAlignment(4);
		/* 336 */ return label;
		/*     */ }

	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */
	/*     */ public JLabel getLabelAggregateBudget() {
		/* 345 */ JLabel label = BasicComponentFactory
				.createLabel(this.presentationModelSumProduksjon.getModel("budgetValueString"));
		/*     */
		/*     */
		/* 348 */ label.setFont(this.font);
		/* 349 */ label.setHorizontalAlignment(4);
		/* 350 */ return label;
		/*     */ }
	/*     */

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
			textTicker.setTexts((List<String>) presentationModelInfo.getValue(InfoHolder.PROPERTY_TEXTS));

		}

	}
}
