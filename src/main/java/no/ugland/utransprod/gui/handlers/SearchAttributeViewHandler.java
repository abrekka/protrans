package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JRadioButton;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.dao.hibernate.QuerySettings;
import no.ugland.utransprod.gui.AttributeCriteriaView;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.ArticleTypeTreeNode;
import no.ugland.utransprod.gui.model.ArticleTypeTreeTableModel;
import no.ugland.utransprod.gui.model.OrderLineAttributeCriteria;
import no.ugland.utransprod.gui.model.OrderLineTreeNode;
import no.ugland.utransprod.gui.model.OrderLineTreeTableModel;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.OrderWrapper;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

/**
 * Håndterer artikkelsøk
 * @author atle.brekka
 */
public class SearchAttributeViewHandler implements Closeable {
    /**
     * 
     */
    private final ArrayListModel articleList;

    /**
     * 
     */
    private final SelectionInList articleSelectionList;

    /**
     * 
     */
    private JButton buttonSelect;

    /**
     * 
     */
    private JButton buttonDeselect;

    /**
     * 
     */
    private JXTreeTable treeTableArticles;

    /**
     * 
     */
    private JXTreeTable treeTableChosen;

    /**
     * 
     */
    private OrderLineTreeTableModel<Order, OrderModel> treeTableModelChosen;

    /**
     * 
     */
    private List<OrderLine> criterias = new ArrayList<OrderLine>();

    /**
     * 
     */
    private JButton buttonSearch;

    /**
     * 
     */
    private JDateChooser dateChooserFrom;

    /**
     * 
     */
    private JDateChooser dateChooserTo;

    /**
     * 
     */
    private List<Order> orderLines = new ArrayList<Order>();

    /**
     * 
     */
    private Integer numberOfOrders;

    /**
     * 
     */
    private boolean canceled = false;

    /**
     * 
     */
    QuerySettings querySettings;

    /**
     * 
     */
    private PresentationModel queryPresentationModel;

    /**
     * 
     */
    public SearchAttributeViewHandler() {
        ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
                .getBean("articleTypeManager");
        querySettings = new QuerySettings();
        queryPresentationModel = new PresentationModel(querySettings);

        List<ArticleType> articles = articleTypeManager.findAllTopLevel();
        if (articles != null) {
            lazyLoadArticles(articles);
        } else {
            articles = new ArrayList<ArticleType>();
        }
        ArticleType constructionType = getConstructionType();
        if (constructionType != null) {
            articles.add(constructionType);
        }
        articleList = new ArrayListModel(articles);
        articleSelectionList = new SelectionInList((ListModel) articleList);
    }

    /**
     * @return ordrelinjer
     */
    public List<Order> getOrderLines() {
        return orderLines;
    }

    /**
     * Lager årvelger for fra år
     * @return årvelger
     */
    public JYearChooser getYearChooserFrom() {
        JYearChooser yearChooser = new JYearChooser();
        PropertyConnector connYear = new PropertyConnector(yearChooser, "year",
                queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_YEAR_FROM), "value");

        connYear.updateProperty1();
        return yearChooser;
    }

    /**
     * Lager årvelger for til år
     * @return årvelger
     */
    public JYearChooser getYearChooserTo() {
        JYearChooser yearChooser = new JYearChooser();
        PropertyConnector connYear = new PropertyConnector(
                yearChooser,
                "year",
                queryPresentationModel.getModel(QuerySettings.PROPERTY_YEAR_TO),
                "value");

        connYear.updateProperty1();
        return yearChooser;
    }

    /**
     * Lager komboboks for fra uke
     * @return komboboks
     */
    public JComboBox getComboBoxWeeksFrom() {
        return new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
                queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_WEEK_FROM)));
    }

    /**
     * Lager komboboks for uke til
     * @return komboboks
     */
    public JComboBox getComboBoxWeeksTo() {
        return new JComboBox(
                new ComboBoxAdapter(Util.getWeeks(), queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_WEEK_TO)));
    }

    /**
     * @return artikkel
     */
    private ArticleType getConstructionType() {
        ArticleType article = null;
        ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
                .getBean("articleTypeManager");
        List<Attribute> attributes = articleTypeManager
                .findAllConstructionTypeAttributes();
        if (attributes != null && attributes.size() != 0) {
            article = new ArticleType();
            Set<ArticleTypeAttribute> constructionTypeAttributes = new HashSet<ArticleTypeAttribute>();
            article.setArticleTypeName("Garasjetype");
            ArticleTypeAttribute articleTypeAttribute;
            for (Attribute attribute : attributes) {
                articleTypeAttribute = new ArticleTypeAttribute();
                articleTypeAttribute.setArticleType(article);
                articleTypeAttribute.setAttribute(attribute);
                constructionTypeAttributes.add(articleTypeAttribute);
            }
            article.setArticleTypeAttributes(constructionTypeAttributes);
        }
        return article;

    }

    /**
     * Lazy laster artikler
     * @param articles
     */
    private void lazyLoadArticles(List<ArticleType> articles) {
        ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil
                .getBean("articleTypeManager");
        for (ArticleType article : articles) {
            articleTypeManager.lazyLoad(article, new LazyLoadArticleTypeEnum[] {
                    LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE_REF,
                    LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE,
                    LazyLoadArticleTypeEnum.ATTRIBUTE});
        }
    }

    /**
     * Lager tretabell for artikler
     * @return tretabell
     */
    public JXTreeTable getTreeTableArticles() {
        treeTableArticles = new JXTreeTable(new ArticleTypeTreeTableModel(
                articleList));
        treeTableArticles.getSelectionModel().addListSelectionListener(
                new ArticleSelectionListener());
        treeTableArticles.addMouseListener(new DoubleClickHandler());

        treeTableArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        return treeTableArticles;
    }

    /**
     * Stter kolonnebredde for tretabell for valgte attributter
     */
    void initTreeTableChosenColumns() {
        // Artikkel
        TableColumn col = treeTableChosen.getColumnModel().getColumn(0);
        col.setPreferredWidth(130);
    }

    /**
     * Lager tretabell fro valgte attributter
     * @return tretabell
     */
    public JXTreeTable getTreeTableChosen() {
        treeTableModelChosen = new OrderLineTreeTableModel<Order, OrderModel>(
                null);
        treeTableChosen = new JXTreeTable(treeTableModelChosen);
        treeTableChosen.getSelectionModel().addListSelectionListener(
                new ArticleSelectionListener());
        treeTableChosen.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        initTreeTableChosenColumns();
        return treeTableChosen;
    }

    /**
     * Lager knapp for å velge attributt
     * @return knapp
     */
    public JButton getButtonSelect() {
        buttonSelect = new JButton(new SelectAction());
        buttonSelect.setEnabled(false);
        return buttonSelect;
    }

    /**
     * Lager knapp mfor å fjerne attributt
     * @return knapp
     */
    public JButton getButtonDeselect() {
        buttonDeselect = new JButton(new DeselectAction());
        buttonDeselect.setEnabled(false);
        return buttonDeselect;
    }

    /**
     * Lager søkeknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonSearch(WindowInterface window) {
        buttonSearch = new JButton(new SearchAction(window));
        buttonSearch.setIcon(IconEnum.ICON_SEARCH.getIcon());
        return buttonSearch;
    }

    /**
     * Lager raioknapp for fakturadato
     * @return radioknapp
     */
    public JRadioButton getRadioButtonInvoiceDate() {
        JRadioButton radioButtonInvoiceDate = BasicComponentFactory
                .createRadioButton(queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_DATE_ENUM_STRING),
                        "INVOICE_DATE", "Fakturadato");
        return radioButtonInvoiceDate;
    }

    /**
     * Lager radioknapp for ordredato
     * @return radioknapp
     */
    public JRadioButton getRadioButtonOrderDate() {
        return BasicComponentFactory.createRadioButton(queryPresentationModel
                .getModel(QuerySettings.PROPERTY_DATE_ENUM_STRING),
                "ORDER_DATE", "Ordredato");
    }

    /**
     * Lager radioknapp for transportuke
     * @return radioknapp
     */
    public JRadioButton getRadioButtonTransportWeek() {
        return BasicComponentFactory.createRadioButton(queryPresentationModel
                .getModel(QuerySettings.PROPERTY_DATE_ENUM_STRING),
                "TRANSPORT_WEEK", "Transportuke");
    }

    /**
     * Lager radioknapp for "eller"
     * @return radioknapp
     */
    public JRadioButton getRadioButtonOr() {
        JRadioButton radioButtonOr = BasicComponentFactory.createRadioButton(
                queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_OPERAND_ENUM_STRING),
                "OR", "Eller");
        return radioButtonOr;
    }

    /**
     * Lager radioknapp for "og"
     * @return radioknapp
     */
    public JRadioButton getRadioButtonAnd() {
        JRadioButton radioButtonAnd = BasicComponentFactory.createRadioButton(
                queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_OPERAND_ENUM_STRING),
                "AND", "Og");
        return radioButtonAnd;
    }

    /**
     * Lager avbrytknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonCancel(WindowInterface window) {
        return new CancelButton(window, this, false);
    }

    /**
     * Lager renskeknapp
     * @return knapp
     */
    public JButton getButtonClear() {
        JButton buttonClear = new JButton(new ClearAction());
        buttonClear.setIcon(IconEnum.ICON_CLEAR.getIcon());
        return buttonClear;
    }

    /**
     * Lager datovelger for fra dato
     * @return datovelger
     */
    public JDateChooser getDateChooserFrom() {
        dateChooserFrom = new JDateChooser();

        PropertyConnector connDate = new PropertyConnector(dateChooserFrom,
                "date", queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_DATE_FROM), "value");

        connDate.updateProperty1();

        return dateChooserFrom;
    }

    /**
     * Lager datovelger for til dato
     * @return datovelger
     */
    public JDateChooser getDateChooserTo() {
        dateChooserTo = new JDateChooser();
        PropertyConnector connDate = new PropertyConnector(dateChooserTo,
                "date", queryPresentationModel
                        .getModel(QuerySettings.PROPERTY_DATE_TO), "value");

        if (queryPresentationModel.getValue(QuerySettings.PROPERTY_DATE_TO) != null) {
            connDate.updateProperty1();
        }
        return dateChooserTo;
    }

    /**
     * Initierere hendelsehåndtering
     */
    public void initEventHandling() {

        articleSelectionList.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new EmptySelectionListener());

    }

    /**
     * Setter enablement av komponenter
     */
    void setComponentEnablement() {
        TreePath path = treeTableArticles.getPathForRow(treeTableArticles
                .getSelectedRow());
        buttonSelect.setEnabled(false);

        if (path != null) {
            TreeNode selectedNode = (ArticleTypeTreeNode) path
                    .getLastPathComponent();
            if (selectedNode.isLeaf()) {
                buttonSelect.setEnabled(true);
            }
        }

        path = treeTableChosen.getPathForRow(treeTableChosen.getSelectedRow());
        buttonDeselect.setEnabled(false);

        if (path != null) {
            TreeNode selectedNode = (OrderLineTreeNode) path
                    .getLastPathComponent();
            if (selectedNode.isLeaf()) {
                buttonDeselect.setEnabled(true);
            }
        }
    }

    /**
     * Setter referanse til ordrelinjer
     * @param articleType
     * @param orderLineRef
     * @param orderLines
     * @param usedArticles
     * @return ordrelinje
     */
    private OrderLine setOrderLineRefs(ArticleType articleType,
            OrderLine orderLineRef, List<OrderLine> orderLines,
            List<ArticleType> usedArticles) {
        OrderLine orderLine = null;

        if (articleType != null && usedArticles.contains(articleType)) {
            orderLine = new OrderLine();
            orderLine.setArticleType(articleType);
            orderLine.setOrderLineRef(orderLineRef);

            if (orderLines != null && orderLines.contains(orderLine)) {
                orderLine = orderLines.get(orderLines.indexOf(orderLine));
            } else {

                if (orderLineRef != null) {
                    Set<OrderLine> orderLineRefs;
                    if (orderLineRef.getOrderLines() != null) {
                        orderLineRef.getOrderLines().add(orderLine);
                    } else {
                        orderLineRefs = new HashSet<OrderLine>();
                        orderLineRefs.add(orderLine);
                        orderLineRef.setOrderLines(orderLineRefs);
                    }

                }

                if (orderLines != null) {
                    orderLines.add(orderLine);
                }
            }

            Set<ArticleTypeArticleType> articleTypeArticleTypes = articleType
                    .getArticleTypeArticleTypes();

            if (articleTypeArticleTypes != null) {

                for (ArticleTypeArticleType articleTypeArticleType : articleTypeArticleTypes) {
                    orderLine = setOrderLineRefs(articleTypeArticleType
                            .getArticleTypeRef(), orderLine, orderLines,
                            usedArticles);
                }
            }

        }

        if (orderLine != null) {
            return orderLine;
        }
        return orderLineRef;
    }

    /**
     * Henter ordrelinje
     * @param leaf
     * @param orderLines1
     * @param usedArticles
     * @return ordrelinje
     */
    private OrderLine getOrderLine(ArticleTypeTreeNode leaf,
            List<OrderLine> orderLines1, List<ArticleType> usedArticles) {
        ArticleTypeTreeNode parent = leaf.getParent();
        Object object = parent.getObject();
        ArticleType articleType=null;
        if (object instanceof ArticleTypeArticleType) {
            articleType = ((ArticleTypeArticleType) object).getArticleTypeRef();
        } else if(object instanceof ArticleType){
            articleType = (ArticleType) object;
        }
        if (usedArticles == null) {
            usedArticles = new ArrayList<ArticleType>();
        }
        if(articleType!=null){
        usedArticles.add(articleType);
        }

        //if (parent.getParent() == null) {
        if (parent.getParent() != null&&parent.getParent().getObject() instanceof ArrayListModel) {
            return setOrderLineRefs(articleType, null, orderLines1,
                    usedArticles);
        }
        return getOrderLine(parent, orderLines1, usedArticles);
    }

    /**
     * Velg attributt
     */
    @SuppressWarnings("unchecked")
    void doSelect() {
        TreePath path = treeTableArticles.getPathForRow(treeTableArticles
                .getSelectedRow());

        if (path != null) {
            ArticleTypeTreeNode selectedNode = (ArticleTypeTreeNode) path
                    .getLastPathComponent();
            if (selectedNode.isLeaf()) {
                ArticleTypeAttribute attribute = (ArticleTypeAttribute) selectedNode
                        .getObject();
                OrderLineAttributeCriteria orderLineAttribute = new OrderLineAttributeCriteria();
                List<OrderLine> orderLines1 = new ArrayList<OrderLine>();

                Object object = treeTableModelChosen.getRoot();

                if (object != null
                        && ((OrderLineTreeNode) object).getObject() != null) {
                    OrderWrapper<Order, OrderModel> orderWrapper = (OrderWrapper<Order, OrderModel>) ((OrderLineTreeNode) object)
                            .getObject();
                    orderLines1 = orderWrapper.getOrderLines();
                }

                OrderLine orderLine = getOrderLine(selectedNode, orderLines1,
                        null);

                if (criterias.contains(orderLine)) {
                    orderLine = criterias.get(criterias.indexOf(orderLine));

                } else {
                    criterias.add(orderLine);
                }

                Set<OrderLineAttribute> orderLineAttributes;
                if (orderLine.getOrderLineAttributes() != null) {
                    orderLineAttributes = orderLine.getOrderLineAttributes();
                } else {
                    orderLineAttributes = new HashSet<OrderLineAttribute>();
                    orderLine.setOrderLineAttributes(orderLineAttributes);
                }

                orderLineAttribute.setArticleTypeAttribute(attribute);

                if (orderLine.getOrderLineAttributes().contains(
                        orderLineAttribute)) {
                    List<OrderLineAttribute> atts = new ArrayList<OrderLineAttribute>(
                            orderLine.getOrderLineAttributes());
                    orderLineAttribute = (OrderLineAttributeCriteria) atts
                            .get(atts.indexOf(orderLineAttribute));
                } else {
                    orderLineAttributes.add(orderLineAttribute);
                }

                AttributeCriteriaViewHandler attributeCriteriaViewHandler = new AttributeCriteriaViewHandler();
                AttributeCriteriaView attributeCriteriaView = new AttributeCriteriaView(
                        orderLineAttribute, attributeCriteriaViewHandler);
                WindowInterface dialogAttributes = new JDialogAdapter(
                        new JDialog(ProTransMain.PRO_TRANS_MAIN, "Attributter",
                                true));
                dialogAttributes.setName("OrderLineAttributeView");
                dialogAttributes.add(attributeCriteriaView
                        .buildPanel(dialogAttributes));
                dialogAttributes.pack();
                Util.locateOnScreenCenter(dialogAttributes);
                dialogAttributes.setVisible(true);
                dialogAttributes.dispose();

                if (object == null
                        || ((OrderLineTreeNode) object).getObject() == null) {
                    Order order = new Order();
                    order.setOrderLines(new HashSet<OrderLine>(orderLines1));
                    // OrderWrapper orderWrapper = new OrderWrapper(order);
                    OrderWrapper orderWrapper = new OrderWrapper(
                            new OrderModel(order, false, false, false,null,null,false));
                    treeTableModelChosen = new OrderLineTreeTableModel(
                            orderWrapper);
                    treeTableChosen.setTreeTableModel(treeTableModelChosen);
                }
                treeTableModelChosen.fireChanged();

            }
        }
    }

    /**
     * Utfør attributtsøk
     */
    /**
     * 
     */
    void doSearch() {
        orderLines.clear();
        OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
                .getBean("orderLineManager");
        numberOfOrders = orderLineManager.countByDate(querySettings);
        List<Order> orderLinesConstrcutionTypeArticleAttribute = null;
        orderLinesConstrcutionTypeArticleAttribute = orderLineManager
                .findByConstructionTypeArticleAttributes(criterias,
                        querySettings);
        if (orderLinesConstrcutionTypeArticleAttribute != null) {
            orderLines.addAll(orderLinesConstrcutionTypeArticleAttribute);
        }
    }

    /**
     * Velg attributt
     * @author atle.brekka
     */
    private class SelectAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        public SelectAction() {
            super("Velg...");
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            doSelect();
            setComponentEnablement();
            initTreeTableChosenColumns();
        }
    }

    /**
     * Fjern attributt
     */
    @SuppressWarnings("unchecked")
    void doDeselect() {
        TreePath path = treeTableChosen.getPathForRow(treeTableChosen
                .getSelectedRow());

        if (path != null) {
            OrderLineTreeNode selectedNode = (OrderLineTreeNode) path
                    .getLastPathComponent();
            if (selectedNode.isLeaf()) {
                OrderLineAttribute attribute = (OrderLineAttribute) selectedNode
                        .getObject();
                OrderLine orderLine = (OrderLine) selectedNode.getParent()
                        .getObject();

                orderLine.getOrderLineAttributes().remove(attribute);

                if (orderLine.getOrderLineAttributes().size() == 0) {
                    criterias.remove(orderLine);

                    Object object = treeTableModelChosen.getRoot();

                    OrderWrapper<Order, OrderModel> orderWrapper = (OrderWrapper<Order, OrderModel>) ((OrderLineTreeNode) object)
                            .getObject();

                    orderWrapper.getOrderLines().remove(orderLine);

                }
                treeTableModelChosen.fireChanged();
            }
        }
    }

    /**
     * Fjern attributt
     * @author atle.brekka
     */
    private class DeselectAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        public DeselectAction() {
            super("Ta bort");
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            doDeselect();
            setComponentEnablement();
            initTreeTableChosenColumns();

        }
    }

    /**
     * Søk
     * @author atle.brekka
     */
    private class SearchAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private WindowInterface window;

        /**
         * @param aWindow
         */
        public SearchAction(WindowInterface aWindow) {
            super("Søk");
            window = aWindow;
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            String errorString = querySettings.checkSettings();
            if (errorString != null) {
                Util.showErrorDialog(window, "Feil", errorString);
            } else {
                doSearch();
                window.setVisible(false);
            }

        }
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
        canceled = true;
        return true;
    }

    /**
     * Håndterer selektering i tabelltre
     * @author atle.brekka
     */
    class EmptySelectionListener implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent evt) {
            setComponentEnablement();

        }

    }

    /**
     * Håndterer selektering av artikkel
     * @author atle.brekka
     */
    class ArticleSelectionListener implements ListSelectionListener {

        /**
         * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
         */
        public void valueChanged(ListSelectionEvent e) {

            setComponentEnablement();

        }

    }

    /**
     * Håndterer dobbeltklikk
     */
    public void doDoubleClick() {
        doSelect();
        setComponentEnablement();
        initTreeTableChosenColumns();

    }

    /**
     * @return antall ordre
     */
    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    /**
     * @return true dersom dialog er kansellert
     */
    public boolean isCanceled() {
        return canceled;
    }

    /**
     * @return presentasjnsmodell
     */
    public PresentationModel getPresentationModel() {
        return queryPresentationModel;
    }

    /**
     * Rensk vindu
     */
    void doClear() {
        criterias.clear();
        treeTableModelChosen = new OrderLineTreeTableModel<Order, OrderModel>(
                null);
        treeTableChosen.setTreeTableModel(treeTableModelChosen);
    }

    /**
     * Rensk vindu
     * @author atle.brekka
     */
    private class ClearAction extends AbstractAction {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        public ClearAction() {
            super("Rensk");
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            doClear();

        }
    }

    /**
     * @return utvalgskriterier
     */
    public String getFilterString() {
        return querySettings.toString() + " " + criteriasToString();
    }

    /**
     * @return utvalgskriterier som streng
     */
    @SuppressWarnings("unchecked")
    private String criteriasToString() {
        StringBuffer toStringBuffer = new StringBuffer();
        List<OrderLineAttributeCriteria> attributes;
        if (criterias != null) {
            for (OrderLine orderLine : criterias) {
                toStringBuffer.append("[").append(
                        orderLine.getGeneratedArticlePath()).append(";");
                attributes = new ArrayList(orderLine.getOrderLineAttributes());

                for (OrderLineAttributeCriteria attribute : attributes) {
                    toStringBuffer.append("(").append(
                            attribute.getAttributeName()).append(":").append(
                            attribute.getAttributeValueFrom()).append("-")
                            .append(
                                    Util.nullToString(attribute
                                            .getAttributeValueTo()));
                    toStringBuffer.append(")");
                }

                toStringBuffer.append("]");
            }
        }
        return toStringBuffer.toString();
    }

    /**
     * Håndterer dobbeltklikk
     * @author atle.brekka
     */
    class DoubleClickHandler extends MouseAdapter {

        /**
         * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
         */
        @SuppressWarnings("unchecked")
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
                doDoubleClick();
            }
        }
    }
}
