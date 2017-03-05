package no.ugland.utransprod.service;

import java.util.List;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.util.Periode;

public interface TransportCostManager {
    // sonetillegg montering
    int COLUMN_ZONE_ADDITION_POSTAL_CODE = 0;
    int COLUMN_ZONE_ADDITION = 2;
    // postnummer
    int COLUMN_TRANSPORT_COST_POSTAL_CODE = 0;
    int COLUMN_TRANSPORT_COST_PLACE = 1;
    int COLUMN_TRANSPORT_COST_AREA_CODE = 2;
    // kommuner
    int COLUMN_AREA_AREA_CODE = 0;
    int COLUMN_AREA_AREA_NAME = 1;
    // fylker
    int COLUMN_COUNTY_COUNTY_NR = 0;
    int COLUMN_COUNTY_COUNTY_NAME = 1;

    // oppdatering av priser
    int COLUMN_UPDATE_POSTAL_CODE_FROM = 0;

    int COLUMN_UPDATE_POSTAL_CODE_TO = 1;

    int COLUMN_UPDATE_PRICE = 2;

    int COLUMN_UPDATE_MAX_ADDITION = 3;
    int COLUMN_UPDATE_ADDITION = 4;

    int COLUMN_UPDATE_ADDITION_DESCRIPTION = 6;

    int COLUMN_UPDATE_BASIS = 7;
    int COLUMN_UPDATE_BASIS2 = 8;

    int COLUMN_UPDATE_ADDITION_ADDITION = 9;

    int COLUMN_UPDATE_METRIC = 10;

    int COLUMN_UPDATE_TRANSPORT_BASIS = 11;

    int COLUMN_UPDATE_MEMBER_OF_MAX_ADDITIONS = 12;

    // snølast
    int COLUMN_AREA_SNOWLOAD_BASIC_VALUE = 1;
    int COLUMN_AREA_HEIGHT_LIMIT = 2;
    int COLUMN_AREA_DELTA_SNOWLOAD = 3;
    int COLUMN_AREA_SNOWLOAD_MAX = 4;
    int COLUMN_AREA_SNOWLOAD_COMMENT = 5;

    String FILE_HEADER_IMPORT = "Postnummer;Sted;Kommunenr;";
    String FILE_HEADER_ZONE_ADDITION_ASSEMBLY_IMPORT = "Postnummer;Sted;Montørtillegg;";

    String FILE_HEADER_UPDATE = "Fra;Til;Pris;Max tillegg;Påslag;Tillegg:;Beskrivelse;Grunnlag;Til;Tillegg;Enhet;" + "Transportgrunnlag;Max tillegg;";

    static final String FILE_HEADER_IMPORT_AREA = "Kommunenr;Kommunenavn;";
    static final String FILE_HEADER_IMPORT_COUNTY = "Fylkesnr;Fylke;";
    static final String FILE_HEADER_IMPORT_SNOW_LOAD = "Area_code;Snowload_basic_value;Height_limit;delta_snowload;Snowload_max;Comment;";
    static final String MANAGER_NAME = "transportCostManager";

    void importAllPostalCodes(String excelFileName, boolean add) throws ProTransException;

    List<TransportCost> findAll();

    void updatePricesFromFile(String excelFileName) throws ProTransException;

    TransportCost findByPostalCode(String postalCode);

    List<TransportCostBasis> generateTransportCostList(Periode period) throws ProTransException;

    void setLabelInfo(JLabel aLabel);

    void importAllAreas(String excelFileName, boolean add) throws ProTransException;

    void importAllCounties(String excelFileName, boolean add) throws ProTransException;

    void removeAll();

    void importSnowLoad(String excelFileName) throws ProTransException;

    void importAllZoneAddtionAssembly(String excelFileName);
}
