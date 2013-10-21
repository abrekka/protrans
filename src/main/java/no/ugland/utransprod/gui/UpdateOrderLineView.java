package no.ugland.utransprod.gui;

import java.awt.Font;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.handlers.ConstructionArticleAttributeViewHandler;
import no.ugland.utransprod.gui.model.IArticleAttributeModel;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.IArticleAttribute;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.validators.OrderLineAttributesValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer oppdatering av ordrelinje
 * @author atle.brekka
 */
public class UpdateOrderLineView {
    private Map<Head, Set<OrderLineAttribute>> orderLineAttributes;
    private List<OrderLineAttribute> allAttributes=new ArrayList<OrderLineAttribute>();
    private JButton buttonOk;

    private JLabel labelHeading;

    private ConstructionArticleAttributeViewHandler viewHandler;

    private Collection<OrderLine> orderLines;

    Map<Head, Map<String, JComponent>> components;
    final ValidationResultModel validationResultModel = new DefaultValidationResultModel();

    /**
     * @param someOrderLines
     * @param handler
     * @param heading
     */
    public UpdateOrderLineView(final Collection<OrderLine> someOrderLines,
            final ConstructionArticleAttributeViewHandler handler, final String heading) {
        this.orderLines = someOrderLines;
        viewHandler = handler;
        labelHeading = new JLabel(heading);
    }

    /**
     * Henter ut alle attributte
     * @param orderLines1
     * @param aParentName
     * @return sortert Map med attributte
     */
    private SortedMap<Head, Set<OrderLineAttribute>> getAttributes(
            final Collection<OrderLine> orderLines1, final String aParentName) {
        String parentName = aParentName;
        TreeMap<Head, Set<OrderLineAttribute>> map = new TreeMap<Head, Set<OrderLineAttribute>>();
        if (parentName != null && parentName.length() != 0) {
            parentName = parentName + " - ";
        }
        if (orderLines1 != null && orderLines1.size() != 0) {
            for (OrderLine line : orderLines1) {

                addOrderLineAttributesToMap(parentName, map, line);
            }
        }
        return map;
    }

    private void addOrderLineAttributesToMap(
            final String parentName, final TreeMap<Head, Set<OrderLineAttribute>> map, final OrderLine line) {
        SortedSet<OrderLineAttribute> orderedAttributes;
        map.putAll(getAttributes(line.getOrderLines(), parentName + line.toString()));
        if (line.getOrderLineAttributes() != null) {
            orderedAttributes = OrderModel.getOrderedOrderLineAttributes(line.getOrderLineAttributes());
            Head head = new Head(parentName + line.toString(), line);
            map.put(head, orderedAttributes);
        }
    }

    /**
     * Initierer vinduskomponenter
     * @param window
     */
    private void initComponents(final WindowInterface window) {
        buttonOk = viewHandler.getButtonOk(window,validationResultModel);

        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        decimalFormat.setParseIntegerOnly(true);
        if (orderLines != null && orderLines.size() != 0) {
            Font font = labelHeading.getFont();
            font = font.deriveFont(Font.BOLD);
            labelHeading.setFont(font);
            orderLineAttributes = getAttributes(orderLines, "");
            
            

            Set<Head> headings = orderLineAttributes.keySet();
            Set<OrderLineAttribute> attributes;
            components = new LinkedHashMap<Head, Map<String, JComponent>>();
            Map<String, JComponent> comps;

            BeanAdapter beanAdapterAtt;
            for (Head head : headings) {
                attributes = orderLineAttributes.get(head);
                allAttributes.addAll(attributes);
                JComponent component;
                if (attributes != null && attributes.size() != 0) {
                    comps = new LinkedHashMap<String, JComponent>();

                    if (!head.getNameString().equalsIgnoreCase("Garasjetype")) {
                        beanAdapterAtt = new BeanAdapter(new IArticleAttributeModel(attributes.iterator()
                                .next()));

                        component = BasicComponentFactory.createFormattedTextField(beanAdapterAtt
                                .getValueModel(IArticleAttributeModel.PROPERTY_NUMBER_OF_ITEMS_LONG),
                                decimalFormat);
                        component.setName("TextFieldAntall");
                        comps.put("Antall", component);
                    }

                    AttributeChangeListener attributeChangeListener = new AttributeChangeListener();
                    for (IArticleAttribute att : attributes) {
                        beanAdapterAtt = new BeanAdapter(new IArticleAttributeModel(att));
                        beanAdapterAtt.addBeanPropertyChangeListener(attributeChangeListener);

                        if (att.isYesNo()) {
                            component = BasicComponentFactory.createCheckBox(beanAdapterAtt
                                    .getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE_BOOL), "");
                            component.setName("CheckBox" + att.getAttributeName());
                            comps.put(att.getAttributeName(), component);
                        } else if (att.getChoices() != null && att.getChoices().size() != 0) {
                            component = new JComboBox(new ComboBoxAdapter(att.getChoices(), beanAdapterAtt
                                    .getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE)));
                            component.setName("ComboBox" + att.getAttributeName());
                            comps.put(att.getAttributeName(), component);
                        } else {
                            component = createTextField(att, beanAdapterAtt);
                            component.setName("TextField" + att.getAttributeName());
                            comps.put(att.getAttributeName(), component);
                        }
                        ValidationComponentUtils.setMandatory(component, true);
                        ValidationComponentUtils.setMessageKey(component,
                                "Attributt."+att.getAttributeName());
                    }
                    String nameString = head.getNameString();
                    Object object =components.get(nameString);;
                    if(object!=null){
                    	nameString+=1;
                    }
                    components.put(head, comps);
                }
            }

        }
    }

    private JComponent createTextField(IArticleAttribute att, BeanAdapter beanAdapterAtt) {
        AttributeDataType dataType = att.getAttributeDataType() != null ? AttributeDataType
                .valueOf(StringUtils.upperCase(att.getAttributeDataType())) : AttributeDataType.TEKST;
        return dataType.createTexField(beanAdapterAtt
                .getValueModel(IArticleAttributeModel.PROPERTY_ATTRIBUTE_VALUE));
        /*
         * return BasicComponentFactory .createTextField(beanAdapterAtt
         * .getValueModel(IArticleAttributeModel. PROPERTY_ATTRIBUTE_VALUE));
         */
    }

    /**
     * Bygger panel
     * @param window
     * @return panel
     */
    public final JComponent buildPanel(final WindowInterface window) {
        window.setName("UpdateOrderLineView");
        initComponents(window);
        FormLayout layout = new FormLayout("10dlu,310dlu:grow,10dlu",
                "10dlu,p,3dlu,min(400dlu;p),3dlu,p,3dlu");
        PanelBuilder builder = new PanelBuilder(layout);
        CellConstraints cc = new CellConstraints();
        builder.add(labelHeading, cc.xy(2, 2));
        builder.add(new JScrollPane(buildAttributePanel(window)), cc.xy(2, 4));
        builder.add(ButtonBarFactory.buildCenteredBar(buttonOk), cc.xyw(2, 6, 2));
        //return builder.getPanel();
        return new IconFeedbackPanel(validationResultModel, builder.getPanel());
    }

    /**
     * Bygger panel for visning av attributter
     * @param window
     * @return panel
     */
    private JComponent buildAttributePanel(final WindowInterface window) {
        FormLayout layout = new FormLayout("10dlu,p:grow,10dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setLeadingColumnOffset(1);
        builder.appendRow(new RowSpec("top:10dlu"));
        builder.nextLine();

        if (components != null) {
            Set<Head> heads = components.keySet();

            for (Head head : heads) {
                builder.append(buildComponentPanel(head));
                builder.nextLine();
            }
        }

        builder.appendRow(new RowSpec("top:5dlu"));
        return builder.getPanel();
    }

    /**
     * Bygger koponentpanel
     * @param label
     * @return panel
     */
    private JPanel buildComponentPanel(final Head head) {
        FormLayout layout = new FormLayout("10dlu,p,3dlu,max(90;p),5dlu,p,3dlu,p,10dlu", "");
        DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        // DefaultFormBuilder builder = new DefaultFormBuilder(layout,new
        // FormDebugPanel());
        builder.setBorder(BorderFactory.createBevelBorder(1));

        Map<String, JComponent> comps;
        Set<String> attLabels;
        builder.setLeadingColumnOffset(1);
        builder.appendRow(new RowSpec("top:5dlu"));
        builder.nextLine();
        JLabel aLabel = new JLabel(head.getNameString());
        builder.append(aLabel, 8);
        builder.nextLine();
        comps = components.get(head);
        if (comps != null) {
            attLabels = comps.keySet();

            for (String attLabel : attLabels) {
                builder.append(attLabel, comps.get(attLabel));
                if (attLabel.equalsIgnoreCase("Antall")) {
                    builder.nextLine();
                }
            }
        }
        builder.appendRow(new RowSpec("top:5dlu"));
        builder.nextLine();

        return builder.getPanel();
    }

    /**
     * Oppdaterer feilrapporttering
     */
    @SuppressWarnings("cast")
    void updateValidationResult() {
        Validator validator = new OrderLineAttributesValidator(allAttributes);
        if (validator != null) {
            ValidationResult result = validator.validate();
            validationResultModel.setResult(result);
        }
    }

    /**
     * Klasse som håndterer feilrapportering
     * @author atle.brekka
     */
    final class ValidationUpdateHandler implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(final PropertyChangeEvent evt) {
            updateValidationResult();
        }

    }

    /**
     * Klasse som holder navn og ordrelinje for visning
     * @author atle.brekka
     */
    private class Head implements Comparable<Head> {
        private String nameString;

        private OrderLine orderLine;

        /**
         * @param name
         * @param line
         */
        public Head(final String name, final OrderLine line) {
            nameString = name;
            orderLine = line;

        }
        public void setNameString(String aNameString){
        	nameString=aNameString;
        }

        /**
         * @return navn
         */
        public String getNameString() {
            return nameString;
        }

        /**
         * @return ordrelinje
         */
        public OrderLine getOrderLine() {
            return orderLine;
        }

        /**
         * @see java.lang.Object#equals(java.lang.Object)
         */
        @Override
        public boolean equals(final Object other) {
            if (!(other instanceof Head)) {
                return false;
            }
            Head castOther = (Head) other;
            return new EqualsBuilder().append(nameString, castOther.nameString).append(orderLine,
                    castOther.orderLine).append(orderLine.getDialogOrder(),
                    castOther.orderLine.getDialogOrder()).isEquals();
        }

        /**
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return new HashCodeBuilder().append(nameString).append(orderLine).append(
                    orderLine.getDialogOrder()).toHashCode();
        }

        /**
         * @param other
         * @return -1,0 eller 1
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(final Head other) {
            Integer order1 = Util.nullToInteger1000(orderLine.getDialogOrder());
            Integer order2 = Util.nullToInteger1000(other.orderLine.getDialogOrder());
            int returnValue;

            if (orderLine.getDialogOrder() == null && other.orderLine.getDialogOrder() == null) {
                returnValue = new CompareToBuilder().append(nameString, other.nameString).toComparison();
            } else {
                returnValue = new CompareToBuilder().append(order1, order2).toComparison();
            }

            if (returnValue == 0 && !equals(other)) {
                returnValue = new CompareToBuilder().append(nameString, other.nameString).toComparison();
            }

            return returnValue;
        }

    }

    /**
     * Håndterer endringer av attributter
     * @author atle.brekka
     */
    class AttributeChangeListener implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(final PropertyChangeEvent event) {
            IArticleAttribute attribute = ((IArticleAttributeModel) event.getSource()).getObject();
            OrderLine orderLine = attribute.getOrderLine();
            if (orderLine != null) {
                orderLine.setHasArticle(null);
                orderLine.setAttributeInfo(null);
                orderLine.removeAllOrderLineAttributeInfo();
                orderLine.setIsDefault(null);

                updateIfTakstein(attribute);
            }
            updateValidationResult();
        }

        private void updateIfTakstein(final IArticleAttribute attribute) {
            if (attribute.getAttributeName().equalsIgnoreCase("Taksteintype")) {
                Map<String, JComponent> comps = components.get("Takstein");
                JCheckBox checkBox = null;
                if (comps != null) {
                    checkBox = (JCheckBox) comps.get("Sendes fra GG");
                }
                if (attribute.getAttributeValue().toLowerCase().indexOf("skarpnes") >= 0
                        || attribute.getAttributeValue().toLowerCase().indexOf("shingel") >= 0) {
                    if (checkBox != null) {
                        checkBox.setSelected(true);
                    }
                } else {
                    if (checkBox != null) {
                        checkBox.setSelected(false);
                    }
                }
            }
        }

    }
    
}
