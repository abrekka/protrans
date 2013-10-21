/**
 * 
 */
package no.ugland.utransprod.gui.handlers;

import java.util.Set;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.service.DeviationManager;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.PostShipmentManager;
import no.ugland.utransprod.service.enums.LazyLoadDeviationEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import com.jgoodies.binding.list.ArrayListModel;

/**
 * Genererer pakkliste for etterlevering
 * @author atle.brekka
 */
class PacklistPrinter implements Threadable {
    private WindowInterface owner;

    private PostShipment postShipment;

    /**
     * @param aWindow
     * @param aPostShipment
     */
    public PacklistPrinter(final WindowInterface aWindow, final PostShipment aPostShipment) {
        owner = aWindow;
        postShipment = aPostShipment;
    }

    /**
     * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
     */
    public final void doWhenFinished(final Object object) {
        if (object != null) {
            Util.showErrorDialog(owner, "Feil", object.toString());
        }
    }

    /**
     * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
     *      javax.swing.JLabel)
     */
    public final Object doWork(final Object[] params, final JLabel labelInfo) {
        labelInfo.setText("Genererer pakkliste...");
        String errorMsg = null;

        try {
            if (postShipment != null) {
                lazyLoadPostShipment();
                Set<OrderLine> orderLineSet = postShipment.getOrderLines();

                generatePacklistReport(orderLineSet, owner);
            }
        } catch (ProTransException e) {
            e.printStackTrace();
            errorMsg = e.getMessage();
        }

        return errorMsg;

    }

    private void generatePacklistReport(final Set<OrderLine> orderLineSet,
            final WindowInterface window) throws ProTransException {
        if (orderLineSet != null) {
            lazyLoadOrderLines(orderLineSet);
            ArrayListModel orderLines = new ArrayListModel(postShipment
                    .getOrderLines());

            ReportViewer reportViewer = new ReportViewer("Pakkliste");
            reportViewer.generateProtransReportAndShow(new PacklistTableModel(
                    orderLines, orderLines.size(),postShipment), "Pakkliste",
                    ReportEnum.PACKLIST, null, window);
        }
    }

    private void lazyLoadOrderLines(final Set<OrderLine> orderLineSet) {
        OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
                .getBean("orderLineManager");

        for (OrderLine orderLine : orderLineSet) {
            orderLineManager
                    .lazyLoad(
                            orderLine,
                            new LazyLoadOrderLineEnum[] {LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE});
        }
    }

    private void lazyLoadPostShipment() {
        PostShipmentManager postShipmentManager = (PostShipmentManager) ModelUtil
                .getBean("postShipmentManager");
        
        postShipmentManager
                .lazyLoad(
                        postShipment,
                        new LazyLoadPostShipmentEnum[] {LazyLoadPostShipmentEnum.ORDER_LINES});
        
        if(postShipment.getDeviation()!=null){
            DeviationManager deviationManager = (DeviationManager) ModelUtil
            .getBean("deviationManager");
            deviationManager.lazyLoad(postShipment.getDeviation(), new LazyLoadDeviationEnum[]{LazyLoadDeviationEnum.COMMENTS});
        }
    }

    /**
     * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
     */
    public void enableComponents(final boolean enable) {
    }

}