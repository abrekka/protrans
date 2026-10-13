
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import javax.swing.JLabel;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.util.Periode;

public interface TransportCostManager {
   int COLUMN_ZONE_ADDITION_POSTAL_CODE = 0;
   int COLUMN_ZONE_ADDITION = 2;
   int COLUMN_TRANSPORT_COST_POSTAL_CODE = 0;
   int COLUMN_TRANSPORT_COST_PLACE = 1;
   int COLUMN_TRANSPORT_COST_AREA_CODE = 2;
   int COLUMN_AREA_AREA_CODE = 0;
   int COLUMN_AREA_AREA_NAME = 1;
   int COLUMN_COUNTY_COUNTY_NR = 0;
   int COLUMN_COUNTY_COUNTY_NAME = 1;
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
   int COLUMN_AREA_SNOWLOAD_BASIC_VALUE = 1;
   int COLUMN_AREA_HEIGHT_LIMIT = 2;
   int COLUMN_AREA_DELTA_SNOWLOAD = 3;
   int COLUMN_AREA_SNOWLOAD_MAX = 4;
   int COLUMN_AREA_SNOWLOAD_COMMENT = 5;
   String FILE_HEADER_IMPORT = "Postnummer;Sted;Kommunenr;";
   String FILE_HEADER_ZONE_ADDITION_ASSEMBLY_IMPORT = "Postnummer;Sted;Montørtillegg;";
   String FILE_HEADER_UPDATE = "Fra;Til;Pris;Max tillegg;Påslag;Tillegg:;Beskrivelse;Grunnlag;Til;Tillegg;Enhet;Transportgrunnlag;Max tillegg;";
   String FILE_HEADER_IMPORT_AREA = "Kommunenr;Kommunenavn;";
   String FILE_HEADER_IMPORT_COUNTY = "Fylkesnr;Fylke;";
   String FILE_HEADER_IMPORT_SNOW_LOAD = "Area_code;Snowload_basic_value;Height_limit;delta_snowload;Snowload_max;Comment;";
   String MANAGER_NAME = "transportCostManager";

   void importAllPostalCodes(String var1, boolean var2) throws ProTransException;

   List<TransportCost> findAll();

   void updatePricesFromFile(String var1) throws ProTransException;

   TransportCost findByPostalCode(String var1);

   List<TransportCostBasis> generateTransportCostList(Periode var1) throws ProTransException;

   void setLabelInfo(JLabel var1);

   void importAllAreas(String var1, boolean var2) throws ProTransException;

   void importAllCounties(String var1, boolean var2) throws ProTransException;

   void removeAll();

   void importSnowLoad(String var1) throws ProTransException;

   void importAllZoneAddtionAssembly(String var1);
}
