package no.ugland.utransprod.gui.handlers;

import java.util.Arrays;
import java.util.List;

public enum TableEnum {
    //Transportdialog
    TABLEORDERS("TableOrders", new String[] {"Produktomr�de","Sannsynlighet"}), 
    //Transportdialog
    TABLEPOSTSHIPMENTS("TablePostShipments", new String[] {"Produktomr�de"}), 
    //Transportdialog
    TABLETRANSPORTORDERS("TableTransportOrders", new String[] {"Transport","Ikke sendt", "Komplett", "Klar", "Produktomr�de","Sannsynlighet"}),
  //Transportdialog
    TABLETRANSPORTORDERSLIST("TableTransportOrdersList", new String[] {"Ikke sendt", "Komplett", "Klar", "Produktomr�de","Sannsynlighet"}),
    //Produksjon gavl
    TABLEPRODUCTIONGAVL("TableProductionGavl", new String[] {"Produktomr�de"}),
    //Produksjon takstol
    TABLEPRODUCTIONTAKSTOL("TableProductionTakstol", new String[] {"Produktomr�de","Sannsynlighet"}),
    //Produksjon front
    TABLEPRODUCTIONFRONT("TableProductionFront", new String[] {"Produktomr�de"}),
    //Fakturering
    TABLEINVOICE("TableInvoice", new String[] {"Produktomr�de"}),
    //Pakkliste
    TABLEPACKLIST("TablePacklist",new String[] {"Produktomr�de"}),
    //Pakking gulvspon
    TABLEPACKAGEGULVSPON("TablePackageGulvspon",new String[] {"Produktomr�de"}),
    //Forh�ndsbetaling
    TABLEPAID("TablePaid", new String[] {"Produktomr�de"}),
    //Takstein
    TABLETAKSTEIN("TableTakstein", new String[] {"Produktomr�de"}),
    //Produksjon vegg
    TABLEPRODUCTIONVEGG("TableProductionVegg", new String[] {"Produktomr�de"}),
    //Pakking stakstol
    TABLEPACKAGETAKSTOL("TablePackageTakstol", new String[] {"Produktomr�de","Artikkel","Komplett","Sannsynlighet"}),
    //Produksjonsoversikt
    TABLEPRODUCTIONOVERVIEW("TableProductionOverview", new String[] {"Komplett", "Klar", "Produktomr�de"});
    private List<String> invisibleColumns;

    private String tableName;

    private TableEnum(final String aTableName,
            final String[] invisibleColumnArray) {
        tableName = aTableName;
        invisibleColumns = Arrays.asList(invisibleColumnArray);
    }

    public List<String> getInvisibleColumns() {
        return invisibleColumns;
    }

    public String getTableName() {
        return tableName;
    }

    /*public static List<Integer> getInvisibleColumns(final String aTableName) {
        if (aTableName.equalsIgnoreCase(TABLE_ORDERS.getTableName())) {
            return TABLE_ORDERS.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_POST_SHIPMENTS
                .getTableName())) {
            return TABLE_POST_SHIPMENTS.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_TRANSPORT_ORDERS
                .getTableName())) {
            return TABLE_TRANSPORT_ORDERS.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PRODUCTION_GAVL
                .getTableName())) {
            return TABLE_PRODUCTION_GAVL.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PRODUCTION_TAKSTOL
                .getTableName())) {
            return TABLE_PRODUCTION_TAKSTOL.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PRODUCTION_FRONT
                .getTableName())) {
            return TABLE_PRODUCTION_FRONT.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_INVOICE.getTableName())) {
            return TABLE_INVOICE.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PACKLIST.getTableName())) {
            return TABLE_PACKLIST.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PACKAGE_GULVSPON
                .getTableName())) {
            return TABLE_PACKAGE_GULVSPON.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PAID.getTableName())) {
            return TABLE_PAID.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_TAKSTEIN.getTableName())) {
            return TABLE_TAKSTEIN.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PRODUCTION_VEGG
                .getTableName())) {
            return TABLE_PRODUCTION_VEGG.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PACKAGE_TAKSTOL
                .getTableName())) {
            return TABLE_PACKAGE_TAKSTOL.getInvisibleColumns();
        } else if (aTableName.equalsIgnoreCase(TABLE_PRODUCTION_OVERVIEW
                .getTableName())) {
            return TABLE_PRODUCTION_OVERVIEW.getInvisibleColumns();
        }

        return null;
    }*/
}
