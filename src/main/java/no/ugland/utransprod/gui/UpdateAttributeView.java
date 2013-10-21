package no.ugland.utransprod.gui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.ConstructionArticleAttributeViewHandler;
import no.ugland.utransprod.gui.model.IArticleAttributeModel;
import no.ugland.utransprod.gui.model.IArticleModel;
import no.ugland.utransprod.model.IArticle;
import no.ugland.utransprod.model.IArticleAttribute;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.validators.AttributeSetValidator;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som hånderer visning av attributter som det skal settes verdi på i
 * forbindelse med at en artikkel har blitt valgt
 * 
 * @author atle.brekka
 * 
 */
public class UpdateAttributeView {
	/**
	 * 
	 */
	private Set<IArticleAttribute> attributes;

	/**
	 * 
	 */
	private Map<String, JTextField> textFields;

	/**
	 * 
	 */
	private Map<String, JTextField> textFieldDialogOrders;

	/**
	 * 
	 */
	private Map<String, JCheckBox> checkBoxes;

	/**
	 * 
	 */
	private Map<String, JComboBox> comboBoxes;

	/**
	 * 
	 */
	private JButton buttonOk;

	/**
	 * 
	 */
	private JTextField textFieldNumberOf;

	/**
	 * 
	 */
	private JTextField textFieldOrder;

	/**
	 * 
	 */
	private String articleName;

	/**
	 * 
	 */
	private ConstructionArticleAttributeViewHandler viewHandler;

	/**
	 * 
	 */
	private ValidationResultModel validationResultModel;

	/**
	 * 
	 */
	private List<BeanAdapter> beans = new ArrayList<BeanAdapter>();

	/**
	 * 
	 */
	private IArticle article;

	/**
	 * 
	 */
	private boolean showOrder = true;

	/**
	 * @param aArticle
	 * @param attributes
	 * @param handler
	 */
	public UpdateAttributeView(IArticle aArticle,
			Set<IArticleAttribute> attributes,
			ConstructionArticleAttributeViewHandler handler) {
		this.attributes = attributes;
		viewHandler = handler;
		article = aArticle;

		if (aArticle instanceof OrderLine || aArticle == null) {
			showOrder = false;
		}
	}

	/**
	 * Initierer vinduskomponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		DecimalFormat decimalFormat = new DecimalFormat();
		decimalFormat.setDecimalSeparatorAlwaysShown(false);
		decimalFormat.setParseIntegerOnly(true);
		if (article != null) {
			articleName = article.getArticleName();

			//BeanAdapter beanAdapterArticle = new BeanAdapter(article);
			BeanAdapter beanAdapterArticle = new BeanAdapter(new IArticleModel(article));
			textFieldNumberOf = BasicComponentFactory.createFormattedTextField(
					//beanAdapterArticle.getValueModel("numberOfItemsLong"),
					beanAdapterArticle.getValueModel(IArticleModel.PROPERTY_NUMBER_OF_ITEMS_LONG),
					decimalFormat);

			textFieldOrder = BasicComponentFactory
					.createIntegerField(beanAdapterArticle
							.getValueModel("dialogOrder"));
		}
		if (attributes != null && attributes.size() != 0) {
			textFields = new LinkedHashMap<String, JTextField>();
			textFieldDialogOrders = new LinkedHashMap<String, JTextField>();
			checkBoxes = new LinkedHashMap<String, JCheckBox>();
			comboBoxes = new LinkedHashMap<String, JComboBox>();

			// går gjennom alle attributter og lager et tekstfelt, checkboks
			// eller comboboks for hver
			for (IArticleAttribute attribute : attributes) {
				BeanAdapter beanAdapter = new BeanAdapter(new IArticleAttributeModel(attribute), true);
				beans.add(beanAdapter);

				if (attribute.isYesNo()) {
					checkBoxes.put(attribute.getAttributeName(),
							BasicComponentFactory.createCheckBox(beanAdapter
									//.getValueModel("attributeValueBool"), ""));
									.getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE_BOOL), ""));
					//beanAdapter.setValue("attributeValueBool", Boolean.FALSE);
					beanAdapter.setValue(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE_BOOL, Boolean.FALSE);
				} else if (attribute.getChoices() != null
						&& attribute.getChoices().size() != 0) {
					comboBoxes.put(attribute.getAttributeName(),
							new JComboBox(new ComboBoxAdapter(attribute
									.getChoices(), beanAdapter
									//.getValueModel("attributeValue"))));
									.getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE))));
				} else {

					textFields.put(attribute.getAttributeName(),
							BasicComponentFactory.createTextField(beanAdapter
									//.getValueModel("attributeValue")));
									.getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE)));

				}
					textFieldDialogOrders
							.put(
									attribute.getAttributeName(),
									BasicComponentFactory
											.createIntegerField(beanAdapter
													//.getValueModel("dialogOrderAttribute")));
													.getValueModel(IArticleAttributeModel.PROPERTY_DIALOG_ORDER_ATTRIBUTE)));

			}

		}
		buttonOk = viewHandler.getButtonOk(window,null);
		validationResultModel = viewHandler.getValidationResultModel();
	}

	/**
	 * Bygger panel for visning av attributter
	 * 
	 * @param window
	 * @return panel
	 */
	public JComponent buildPanel(WindowInterface window) {
		initComponents(window);
		initComponentAnnotations();
		initEventHandling();
		FormLayout layout = new FormLayout(
				"10dlu,p,p,100dlu,3dlu,p,p,20dlu,10dlu", "");
		DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setLeadingColumnOffset(1);
		builder.appendRow(new RowSpec("top:10dlu"));
		builder.nextLine();
		
		String metric = article.getMetric();
		if(metric!=null){
			metric="("+metric+")";
		}else{
			metric="";
		}

		if (article != null) {
			builder.append(articleName + " - antall:"+metric, textFieldNumberOf);
			if (showOrder) {
				builder.append("Rekkefølge:", textFieldOrder);
			}
			builder.nextLine();
		}
		if (textFields != null) {
			Set<String> labels = textFields.keySet();
			for (String label : labels) {
				builder.append(label, textFields.get(label));
				if (showOrder) {
					builder.append("Rekkefølge:", textFieldDialogOrders
							.get(label));
				}
				builder.nextLine();
			}
			labels = checkBoxes.keySet();

			for (String label : labels) {
				builder.append(label, checkBoxes.get(label));
				if (showOrder) {
					builder.append("Rekkefølge:", textFieldDialogOrders
							.get(label));
				}
				builder.nextLine();
			}

			labels = comboBoxes.keySet();

			for (String label : labels) {
				builder.append(label, comboBoxes.get(label));
				if (showOrder) {
					builder.append("Rekkefølge:", textFieldDialogOrders
							.get(label));
				}
				builder.nextLine();
			}
		}

		builder.append(ButtonBarFactory.buildCenteredBar(buttonOk), 8);
		builder.appendRow(new RowSpec("top:5dlu"));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Oppdaterer feilrapportering
	 */
	@SuppressWarnings("cast")
	void updateValidationResult() {
		ValidationResult result = new AttributeSetValidator(
				(Set<IArticleAttribute>) attributes).validate();
		validationResultModel.setResult(result);
		if (validationResultModel.hasErrors()) {
			buttonOk.setEnabled(false);
		} else {
			buttonOk.setEnabled(true);
		}
	}

	/**
	 * Initierere hendelsehåndtering
	 */
	private void initEventHandling() {
		PropertyChangeListener handler = new ValidationUpdateHandler();
		for (BeanAdapter bean : beans) {
			//bean.getValueModel("attributeValue")
			bean.getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE)
					.addValueChangeListener(handler);
		}
		updateValidationResult();

	}

	/**
	 * Initierere feilrapportering for komponenter
	 */
	protected void initComponentAnnotations() {
		if (textFields != null) {
			Set<String> labels = textFields.keySet();
			int count = 1;
			for (String label : labels) {
				ValidationComponentUtils.setMandatory(textFields.get(label),
						true);
				ValidationComponentUtils.setMessageKey(textFields.get(label),
						"Artikkel." + label);
				count++;
			}
		}
		if (comboBoxes != null) {
			Set<String> labels = comboBoxes.keySet();
			int count = 1;
			for (String label : labels) {
				ValidationComponentUtils.setMandatory(comboBoxes.get(label),
						true);
				ValidationComponentUtils.setMessageKey(comboBoxes.get(label),
						"Artikkel." + label);
				count++;
			}
		}
	}

	/**
	 * Klasse som håndterer feilrapportering
	 * 
	 * @author atle.brekka
	 * 
	 */
	final class ValidationUpdateHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			updateValidationResult();
		}

	}

}
