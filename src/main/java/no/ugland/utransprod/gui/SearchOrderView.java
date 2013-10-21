package no.ugland.utransprod.gui;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import no.ugland.utransprod.gui.buttons.CancelButton;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

/**
 * Vindu som brukes for å søke etter ordre
 * 
 * @author atle.brekka
 * 
 */
public class SearchOrderView implements Closeable {
	/**
	 * 
	 */
	private JCheckBox checkBoxOrderNr;

	/**
	 * 
	 */
	JTextField textFieldSearch;

	/**
	 * 
	 */
	private PresentationModel presentationModel;

	/**
	 * 
	 */
	private JButton buttonOk;

	/**
	 * 
	 */
	private boolean useCustomer;

	/**
	 * @param useCustomerSearch
	 */
	public SearchOrderView(boolean useCustomerSearch) {
		useCustomer = useCustomerSearch;
		presentationModel = new PresentationModel(new SearchCriteria());
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		checkBoxOrderNr = BasicComponentFactory.createCheckBox(
				presentationModel
						.getModel(SearchCriteria.PROPERTY_USERORDER_NR),
				"Ordernr");
		textFieldSearch = BasicComponentFactory.createTextField(
				presentationModel.getModel(SearchCriteria.PROPERTY_CRITERIA),
				false);
		textFieldSearch.setName("TextFieldSearch");
		buttonOk = new CancelButton(window, this, false, "Ok",
				IconEnum.ICON_OK, null, true);
		buttonOk.setName("ButtonOk");
		window.getRootPane().setDefaultButton(buttonOk);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				textFieldSearch.requestFocus();
			}
		});
	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public JPanel buildPanel(WindowInterface window) {
		window.setName("SearchOrderView");
		initComponents(window);
		FormLayout layout = new FormLayout("10dlu,p,3dlu,50dlu,10dlu",
				"10dlu,p,3dlu,p,5dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		if (useCustomer) {
			builder.add(checkBoxOrderNr, cc.xy(2, 2));
		}
		builder.addLabel("Søkekriterie:", cc.xy(2, 4));
		builder.add(textFieldSearch, cc.xy(4, 4));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc
				.xyw(2, 6, 3));

		return builder.getPanel();
	}

	/**
	 * Klasse som brukes til å holde på søkekriterier
	 * 
	 * @author atle.brekka
	 * 
	 */
	public class SearchCriteria extends Model {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		public static final String PROPERTY_USERORDER_NR = "useOrderNr";

		/**
		 * 
		 */
		public static final String PROPERTY_CRITERIA = "criteria";

		/**
		 * 
		 */
		private Boolean useOrderNr;

		/**
		 * 
		 */
		private String criteria;

		/**
		 * 
		 */
		public SearchCriteria() {
			useOrderNr = true;

		}

		/**
		 * @return kriterie
		 */
		public String getCriteria() {
			return criteria;
		}

		/**
		 * @param criteria
		 */
		public void setCriteria(String criteria) {
			String oldCrit = getCriteria();
			this.criteria = criteria;
			firePropertyChange(PROPERTY_CRITERIA, oldCrit, criteria);
		}

		/**
		 * @return true dersom det skal brukes ordrenummer
		 */
		public Boolean getUseOrderNr() {
			return useOrderNr;
		}

		/**
		 * @param useOrderNr
		 */
		public void setUseOrderNr(Boolean useOrderNr) {
			Boolean oldUse = getUseOrderNr();
			this.useOrderNr = useOrderNr;
			firePropertyChange(PROPERTY_USERORDER_NR, oldUse, useOrderNr);
		}
	}

	/**
	 * @return true dersom det skal brukes ordrenummer
	 */
	public Boolean useOrderNr() {
		return ((SearchCriteria) presentationModel.getBean()).getUseOrderNr();
	}

	/**
	 * @return kriterie
	 */
	public String getCriteria() {
		return ((SearchCriteria) presentationModel.getBean()).getCriteria();
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {

		return true;
	}
}
