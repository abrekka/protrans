package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.SearchOrderView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.OrderTableModel;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.IncomingOrderManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.jgoodies.binding.PresentationModel;

/**
 * Håndterer ordre til avrop
 * @author atle.brekka
 */
public class IncomingOrderViewHandler extends
        DefaultAbstractViewHandler<Order, OrderModel> implements ViewHandlerExt<Order,OrderModel>{
    private static final long serialVersionUID = 1L;

    private OrderViewHandler orderViewHandler;

    private final PresentationModel presentationModelProductAreaGroup;
    private ManagerRepository managerRepository;

    /**
     * @param aOrderViewHandler
     * @param userType
     */
    @Inject
    public IncomingOrderViewHandler(OrderViewHandlerFactory orderViewHandlerFactory,
            Login login,IncomingOrderManager incomingOrderManager,ManagerRepository aManagerRepository) {
        super("Ordre til avrop", incomingOrderManager, login.getUserType(), true);
        managerRepository=aManagerRepository;
        viewHandlerExt=this;
        orderViewHandler = orderViewHandlerFactory.create(false);
        presentationModelProductAreaGroup = new PresentationModel(
                new ProductAreaGroupModel());
        presentationModelProductAreaGroup
                .addBeanPropertyChangeListener(new ProductAreaGroupChangeListener());
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
        return "IncomingOrder";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
        return new OrderTableModel(objectSelectionList,
                OrderPanelTypeEnum.INCOMING_ORDER);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
        return "250dlu";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
        return "Ordre til avrop";
    }

    
    /**
     * Søk
     */
    private void doSearch() {

        objectSelectionList.clearSelection();

        SearchOrderView searchOrderView = new SearchOrderView(false);
        JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN, "Søk", true);
        WindowInterface dialogWindow = new JDialogAdapter(dialog);
        dialog.add(searchOrderView.buildPanel(dialogWindow));
        dialog.pack();
        Util.locateOnScreenCenter(dialogWindow);
        dialogWindow.setVisible(true);

        String criteria = searchOrderView.getCriteria();
        Boolean useOrderNr = searchOrderView.useOrderNr();

        if (criteria != null) {
            if (useOrderNr) {
                List<Order> orders = ((IncomingOrderManager) overviewManager)
                        .findByOrderNr(criteria);
                objectList.clear();
                if (orders != null) {
                    objectList.addAll(orders);
                }
            }
        }

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
        return UserUtil.hasWriteAccess(userType, "Ordre til avrop");
    }

    /**
     * @param handler
     * @param object
     * @param searching
     * @return view
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
     *      java.lang.Object, boolean)
     */
    @Override
    protected AbstractEditView<OrderModel, Order> getEditView(
            AbstractViewHandler<Order, OrderModel> handler, Order object,
            boolean searching) {
        return null;
    }

    public JComboBox getComboBoxProductAreaGroup() {
        JComboBox comboBox = Util
                .createComboBoxProductAreaGroup(presentationModelProductAreaGroup
                        .getModel(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
        comboBox.setSelectedIndex(0);
        comboBox.setName("ComboBoxProductAreaGroup");
        return comboBox;
    }

    private class ProductAreaGroupChangeListener implements
            PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            ProductAreaGroup group = (ProductAreaGroup) presentationModelProductAreaGroup
                    .getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
            if (group != null
                    && !group.getProductAreaGroupName()
                            .equalsIgnoreCase("Alle")) {
                Filter filter = new PatternFilter(group
                        .getProductAreaGroupName(), Pattern.CASE_INSENSITIVE, 7);
                FilterPipeline filterPipeline = new FilterPipeline(
                        new Filter[] {filter});
                table.setFilters(filterPipeline);

            } else {
                table.setFilters(null);
            }

        }

    }

    @Override
    public void setColumnWidth(JXTable table) {
        // kunde
        table.getColumnExt(0).setPreferredWidth(100);
        // ordrenr
        table.getColumnExt(1).setPreferredWidth(50);
        // adresse
        table.getColumnExt(2).setPreferredWidth(100);
        // postunmmer
        table.getColumnExt(3).setPreferredWidth(100);
        // poststed
        table.getColumnExt(4).setPreferredWidth(100);
        // produmtområde
        table.getColumnExt(7).setPreferredWidth(100);
    }

    @Override
    public CheckObject checkDeleteObject(Order object) {
        return null;
    }

    @Override
    public CheckObject checkSaveObject(OrderModel object, PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    @Override
    public String getAddRemoveString() {
        return null;
    }

    @Override
    public Order getNewObject() {
        return null;
    }

    @Override
    public Dimension getWindowSize() {
        return null;
    }

    public boolean openEditViewExt(Order object, boolean searching, WindowInterface parentWindow,boolean lettvekt) {
        try {
            if (searching) {
                doSearch();
            } else {
                currentObject = object;
                Order orderSelection = (Order) objectSelectionList
                        .getElementAt(table
                                .convertRowIndexToModel(objectSelectionList
                                        .getSelectionIndex()));
                setupOrder(orderSelection);
                
                orderViewHandler.openOrderView(orderSelection, searching, parentWindow,true);
            }
        } catch (ProTransException e) {
            Util.showErrorDialog(parentWindow, "Feil", e.getMessage());
            e.printStackTrace();
        }
        return true;
        
    }
    private void setupOrder(Order order)throws ProTransException{
        ((IncomingOrderManager)overviewManager).setCosts(order);
        ((IncomingOrderManager)overviewManager).setOrderLines(order,managerRepository);
    }

    public boolean saveObjectExt(AbstractModel<Order, OrderModel> objectModel, WindowInterface window) {
        return false;
    }
}
