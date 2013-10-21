package no.ugland.utransprod.gui.handlers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JTable;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.gui.model.TransportableTreeTableModel;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;

import org.jdesktop.swingx.JXTreeTable;

public class SplitOrderViewHandler implements Closeable{
    private Transportable transportable;
    private boolean canceled=false;
    private TransportableTreeTableModel transportableTreeTableModel;
    

    public SplitOrderViewHandler(Transportable aTransportable) {
        transportable = aTransportable;
        lazyLoadTransportable(transportable);
    }

    public JXTreeTable getTreeTableTransportable() {
        transportableTreeTableModel=new TransportableTreeTableModel(transportable);
        JXTreeTable treeTable = new JXTreeTable(transportableTreeTableModel);
        treeTable.setName("TreeTableTransportable");
        treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        treeTable.getColumnExt(0).setPreferredWidth(200);
        treeTable.getColumnExt(1).setPreferredWidth(50);
        return treeTable;
    }
    public JButton getButtonOk(WindowInterface window){
        JButton button = new CancelButton(window,this,false,"Ok",IconEnum.ICON_OK,null,true);
        button.setName("ButtonOk");
        return button;
    }
    public JButton getButtonCancel(WindowInterface window){
        JButton button = new CancelButton(window,this,true);
        button.setName("ButtonCancel");
        return button;
    }

    private void lazyLoadTransportable(Transportable transportable) {
        if (transportable != null) {
            if (transportable instanceof Order) {
                OrderManager orderManager = (OrderManager) ModelUtil
                        .getBean("orderManager");
                orderManager
                        .lazyLoadOrder(
                                (Order) transportable,
                                new LazyLoadOrderEnum[] {LazyLoadOrderEnum.ORDER_LINES,LazyLoadOrderEnum.COLLIES});
            } else {
                PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
                        .getBean("postShipmentManager");
                postShipmentManager
                        .lazyLoad(
                                (PostShipment) transportable,
                                new LazyLoadPostShipmentEnum[] {LazyLoadPostShipmentEnum.ORDER_LINES,LazyLoadPostShipmentEnum.COLLIES});
            }
        }
    }
    
    public boolean canClose(String actionString, WindowInterface window) {
        if(!actionString.equalsIgnoreCase("Ok")){
            canceled=true;
        }
        return true;
    }
    
    public List<Object> getSplitted(){
        return new ArrayList<Object>(transportableTreeTableModel.getSplitFromOrder());
    }
    
    public boolean isCanceled(){
        return canceled;
    }
}
