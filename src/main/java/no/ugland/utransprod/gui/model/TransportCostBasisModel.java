package no.ugland.utransprod.gui.model;

import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.TransportCostBasis;

import com.jgoodies.binding.PresentationModel;

public class TransportCostBasisModel extends
        AbstractModel<TransportCostBasis, TransportCostBasisModel> {
    public static final String PROPERTY_ORDER_COSTS = "orderCosts";

    private static final long serialVersionUID = 1L;

    public TransportCostBasisModel(final TransportCostBasis object) {
        super(object);
    }

    public final List<OrderCost> getOrderCosts() {
        if(object.getOrderCosts()!=null){
        return new ArrayList<OrderCost>(object.getOrderCosts());
        }
        return null;
    }

    @Override
    public final void addBufferChangeListener(
            final PropertyChangeListener listener,
            final PresentationModel presentationModel) {
        // TODO Auto-generated method stub

    }

    @Override
    public final TransportCostBasisModel getBufferedObjectModel(
            final PresentationModel presentationModel) {
        // TODO Auto-generated method stub
        return null;
    }

}
