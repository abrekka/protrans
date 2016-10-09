package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.ListModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.enums.LazyLoadTransportEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.toedter.calendar.JYearChooser;

/**
 * Håndterer oversiktsrapport for transport
 * @author atle.brekka
 */
public class TransportOverviewReportHandler implements Closeable {
    /**
     * 
     */
    PresentationModel presentationModel;

    /**
     * 
     */
    StatusCheckerInterface<Transportable> steinChecker;

    /**
     * 
     */
    private boolean disposeOnClose = true;

    private ReportSetting currentReportSetting;

    /**
     * @param aSteinChecker
     */
    public TransportOverviewReportHandler(
            StatusCheckerInterface<Transportable> aSteinChecker) {
        steinChecker = aSteinChecker;
        currentReportSetting = new ReportSetting(Util.getCurrentYear(), Util
                .getCurrentWeek(), Util.getCurrentWeek());
        presentationModel = new PresentationModel(currentReportSetting);
    }

    /**
     * Lager årvelger
     * @return årvelger
     */
    public JYearChooser getYearChooser() {
        JYearChooser yearChooser = new JYearChooser();
        PropertyConnector.connect(yearChooser, "year", presentationModel
                .getModel(ReportSetting.PROPERTY_YEAR), "value");
        return yearChooser;
    }

    /**
     * Lager komboboks for uke fra
     * @return komboboks
     */
    public JComboBox getComboBoxFromWeek() {
        return new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
                presentationModel.getModel(ReportSetting.PROPERTY_FROM_WEEK)));
    }

    /**
     * Lager komboboks for uke til
     * @return komboboks
     */
    public JComboBox getComboBoxToWeek() {
        return new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
                presentationModel.getModel(ReportSetting.PROPERTY_TO_WEEK)));
    }

    public JComboBox getComboBoxProductAreaGroup() {
        return Util.createComboBoxProductAreaGroup(presentationModel
                .getModel(ReportSetting.PROPERTY_PRODUCT_AREA_GROUP));
    }

    /**
     * Lager utskriftknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonPrint(WindowInterface window) {
        JButton button = new JButton(new PrintAction(window));
        button.setIcon(IconEnum.ICON_EXCEL.getIcon());
        return button;
    }

    /**
     * Lager avbrytknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonCancel(WindowInterface window) {
        return new CancelButton(window, this, disposeOnClose);
    }

    /**
     * Henter vindustittel
     * @return tittel
     */
    public String getWindowTitle() {
        return "Transportoversikt";
    }

    /**
     * Henter vindusstørrelse
     * @return vindusstørrelse
     */
    public Dimension getWindowSize() {
        return new Dimension(310, 130);
    }

    /**
     * Holder på rapportsettinger
     * @author atle.brekka
     */
    public class ReportSetting extends Model {
        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        public static final String PROPERTY_YEAR = "year";

        /**
         * 
         */
        public static final String PROPERTY_FROM_WEEK = "fromWeek";

        /**
         * 
         */
        public static final String PROPERTY_TO_WEEK = "toWeek";

        public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

        /**
         * 
         */
        private Integer year;

        /**
         * 
         */
        private Integer fromWeek;

        /**
         * 
         */
        private Integer toWeek;

        private ProductAreaGroup productAreaGroup;

        /**
         * @param year
         * @param fromWeek
         * @param toWeek
         */
        public ReportSetting(Integer year, Integer fromWeek, Integer toWeek) {
            super();
            this.year = year;
            this.fromWeek = fromWeek;
            this.toWeek = toWeek;
        }

        /**
         * @return fra uke
         */
        public Integer getFromWeek() {
            return fromWeek;
        }

        /**
         * @param fromWeek
         */
        public void setFromWeek(Integer fromWeek) {
            Integer oldWeek = getFromWeek();
            this.fromWeek = fromWeek;
            firePropertyChange(PROPERTY_FROM_WEEK, oldWeek, fromWeek);
        }

        /**
         * @return til uke
         */
        public Integer getToWeek() {
            return toWeek;
        }

        /**
         * @param toWeek
         */
        public void setToWeek(Integer toWeek) {
            Integer oldWeek = getToWeek();
            this.toWeek = toWeek;
            firePropertyChange(PROPERTY_TO_WEEK, oldWeek, toWeek);
        }

        /**
         * @return år
         */
        public Integer getYear() {
            return year;
        }

        /**
         * @param year
         */
        public void setYear(Integer year) {
            Integer oldYear = getYear();
            this.year = year;
            firePropertyChange(PROPERTY_YEAR, oldYear, year);
        }

        public ProductAreaGroup getProductAreaGroup() {
            return productAreaGroup;
        }

        public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
            ProductAreaGroup oldAreaGroup = getProductAreaGroup();
            this.productAreaGroup = productAreaGroup;
            firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldAreaGroup, productAreaGroup);
        }

        public boolean isValid() {
            if (productAreaGroup != null) {
                return true;
            }
            return false;
        }

    }

    /**
     * Utskrift
     * @author atle.brekka
     */
    private class PrintAction extends AbstractAction {
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
        public PrintAction(WindowInterface aWindow) {
            super("Rapport");
            window = aWindow;
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent arg0) {
            if (currentReportSetting.isValid()) {
                window.setSize(new Dimension(310, 250));
                Util.runInThreadWheel(window.getRootPane(),new ReportGenerator(window), null);
                
            } else {
                Util.showErrorDialog(window, "Utvalg må settes",
                        "Alle utvalg må settes");
            }

        }
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
        return true;
    }

    /**
     * Utskrift
     * @author atle.brekka
     */
    private class ReportGenerator implements Threadable {
        /**
         * 
         */
        private WindowInterface window;

        /**
         * @param aWindow
         */
        public ReportGenerator(WindowInterface aWindow) {
            window = aWindow;
        }

        /**
         * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
         */
        public void doWhenFinished(Object object) {
            window.setSize(getWindowSize());

        }

        /**
         * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
         *      javax.swing.JLabel)
         */
        public Object doWork(Object[] params, JLabel labelInfo) {
            String errorString = null;
            try {
                if(labelInfo!=null){
                labelInfo.setText("Genererer transportoversikt...");
                }
                List<Transport> transportList = getTransportList();

                Map<Integer, Map<Transport, TransportOverviewTableModel>> weekModels = generateWeekModels(
                        transportList, ((ReportSetting) presentationModel
                                .getBean()).getProductAreaGroup());

                generateExcel(weekModels);
            } catch (ProTransException ex) {
                ex.printStackTrace();
                errorString = ex.getMessage();
            }
            return errorString;
        }

        private Map<Integer, Map<Transport, TransportOverviewTableModel>> generateWeekModels(
                List<Transport> transportList, ProductAreaGroup productAreaGroup) {
            int currentWeek = 0;
            Map<Transport, TransportOverviewTableModel> models = new HashMap<Transport, TransportOverviewTableModel>();
            Map<Integer, Map<Transport, TransportOverviewTableModel>> weekModels = new HashMap<Integer, Map<Transport, TransportOverviewTableModel>>();
            TransportManager transportManager = (TransportManager) ModelUtil
                    .getBean("transportManager");
            
            for (Transport transport : transportList) {
                transportManager
                        .lazyLoadTransport(
                                transport,
                                new LazyLoadTransportEnum[] {LazyLoadTransportEnum.ORDER});

                if (currentWeek == 0) {
                    currentWeek = transport.getTransportWeek();
                }
                
                if (transport.getTransportWeek() != currentWeek) {
                    
                    
                    weekModels.put(currentWeek, models);
                    
                    List<Order> orders = transport.getOrders(productAreaGroup);
                    
                    if (orders != null && orders.size() != 0) {
                        models = new HashMap<Transport, TransportOverviewTableModel>();
                        currentWeek = transport.getTransportWeek();
                        models.put(transport, new TransportOverviewTableModel(
                                new ArrayListModel(orders), window));
                    }
                    
                } else {
                    addTransportToWeek(transport, models, productAreaGroup);
                }


            }
            weekModels.put(currentWeek, models);
            return weekModels;
        }

       

        private void addTransportToWeek(Transport transport,
                Map<Transport, TransportOverviewTableModel> models,
                ProductAreaGroup productAreaGroup) {
            List<Order> orders = transport.getOrders(productAreaGroup);
            if (orders != null && orders.size() != 0) {
                models.put(transport, new TransportOverviewTableModel(
                        new ArrayListModel(orders), window));
            }
        }

       

        private void generateExcel(
                Map<Integer, Map<Transport, TransportOverviewTableModel>> weekModels)
                throws ProTransException {
            String fileName = "transportoversikt_"
                    + Util.getCurrentDateAsDateTimeString() + ".xlsx";
            String excelDirectory = ApplicationParamUtil
                    .findParamByName("excel_path");
            ExcelUtil excelUtil = new ExcelUtil();
            excelUtil.showDataInExcelTransportOverview(excelDirectory,
                    fileName, null, weekModels);
        }

        private List<Transport> getTransportList() {
            TransportManager transportManager = (TransportManager) ModelUtil
                    .getBean("transportManager");
            List<Transport> transportList = transportManager
                    .findBetweenYearAndWeek(((ReportSetting) presentationModel
                            .getBean()).getYear(),
                            ((ReportSetting) presentationModel.getBean())
                                    .getFromWeek(),
                            ((ReportSetting) presentationModel.getBean())
                                    .getToWeek(),
                            new String[] {"transportWeek"});
            return transportList;
        }

        /**
         * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
         */
        public void enableComponents(boolean enable) {
        }

    }

    /**
     * Finner status for ønsket objekt
     * @param checker
     * @param statusMap
     * @param order
     * @param window
     * @return status
     */
    String getStatus(StatusCheckerInterface<Transportable> checker,
            Map<String, String> statusMap, Order order, WindowInterface window) {
        OrderManager orderManager = (OrderManager) ModelUtil
                .getBean("orderManager");
        String status = statusMap.get(checker.getArticleName());
        if (status != null) {
            return status;
        }
        orderManager.lazyLoadTree(order);

        status = checker.getArticleStatus(order);
        statusMap.put(checker.getArticleName(), status);
        order.setStatus(Util.statusMapToString(statusMap));
        try {
            orderManager.saveOrder(order);
        } catch (ProTransException e) {
            Util.showErrorDialog(window, "Feil", e.getMessage());
            e.printStackTrace();
        }
        return status;
    }

    /**
     * Tabellmodell for transportoversikt
     * @author atle.brekka
     */
    public class TransportOverviewTableModel extends AbstractTableAdapter {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private WindowInterface window;

        /**
         * @param listModel
         * @param aWindow
         */
        public TransportOverviewTableModel(ListModel listModel,
                WindowInterface aWindow) {
            super(listModel, new String[] {"Ordre", "Stein"});
            window = aWindow;

        }

        /**
         * @see javax.swing.table.TableModel#getValueAt(int, int)
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            Order order = (Order) getRow(rowIndex);
            Map<String, String> statusMap = Util.createStatusMap(order
                    .getStatus());
            switch (columnIndex) {
            case 0:
                return order.getOrderStringShort();
            case 1:
                return getStatus(steinChecker, statusMap, order, window);
            }
            return null;
        }

        /**
         * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
            case 0:
            case 1:
                return String.class;
            }
            return Object.class;
        }

    }

    /**
     * Sjekker om det skal kjøres dispose
     * @return true dersom dispose
     */
    public boolean getDisposeOnClose() {
        return disposeOnClose;
    }
}
