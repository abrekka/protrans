package no.ugland.utransprod.gui.edit;

import java.util.Map;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler2.ProductionColumn;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ProductionOverviewV;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.SelectionInList;

public class ProductionoverviewFilter extends Model {
    private SelectionInList sort1SelectionList;
    private SelectionInList sort2SelectionList;
    private SelectionInList sort3SelectionList;
    public static final String PACKLIST_READY = "packlistReady";
    public static final String PACKLIST_DONE_BY = "packlistDoneBy";
    public static final String PACKLIST_DURATION = "packlistDuration";
    public static final String ORDERNR = "orderNr";
    public static final String FIRSTNAME = "firstname";
    public static final String LASTNAME = "lastname";
    public static final String POSTAL_CODE = "postalCode";
    public static final String POSTOFFICE = "postoffice";
    public static final String CONSTRUCTION_TYPE_NAME = "constructionTypeName";
    public static final String INFO = "info";
    public static final String PRODUCT_AREA = "productArea";
    public static final String PRODUCTION_WEEK = "productionWeek";
    public static final String TRANSPORT = "transport";
    public static final String ESTIMATED_TIME_WALL = "estimatedTimeWall";
    public static final String TIME_WALL = "timeWall";
    public static final String ESTIMATED_TIME_GAVL = "estimatedTimeGavl";
    public static final String TIME_GAVL = "timeGavl";
    public static final String ESTIMATED_TIME_PACK = "estimatedTimePack";
    public static final String WALL_STATUS = "wallStatus";
    public static final String GULVSPON_STATUS = "gulvsponStatus";
    public static final String TAKSTOL_STATUS = "takstolStatus";
    public static final String GAVL_STATUS = "gavlStatus";
    public static final String PAKK_STATUS = "pakkStatus";
    public static final String TAKSTEIN_STATUS = "taksteinStatus";
    public static final String MONTERING = "montering";
    public static final String EGENPRODUKSJON = "egenproduksjon";
    public static final String KOST = "kost";
    public static final String FRAKT = "frakt";
    public static final String MONTERINGSUM = "monteringsum";
    public static final String TEGNINGER_JATAK = "tegningerJatak";
    public static final String ONSKET_LEVERING = "onsketLevering";
    private String packlistReady;
    private String packlistDoneBy;
    private String packlistDuration;
    private String orderNr;
    private String firstname;
    private String lastname;
    private String postalCode;
    private String postoffice;
    private String constructionTypeName;
    private String info;
    private String productArea;
    private String productionWeek;
    private String transport;
    private String estimatedTimeWall;
    private String timeWall;
    private String estimatedTimeGavl;
    private String timeGavl;
    private String estimatedTimePack;
    private String wallStatus;
    private String gulvsponStatus;
    private String takstolStatus;
    private String gavlStatus;
    private String pakkStatus;
    private String taksteinStatus;
    private String montering;
    private String egenproduksjon;
    private String kost;
    private String frakt;
    private String monteringsum;
    private String tegningerJatak;
    private String onsketLevering;

    public ProductionoverviewFilter() {
	sort1SelectionList = new SelectionInList(ProductionColumn.getVisibleColumns());
	sort2SelectionList = new SelectionInList(ProductionColumn.getVisibleColumns());
	sort3SelectionList = new SelectionInList(ProductionColumn.getVisibleColumns());
    }

    public void setOnsketLevering(String onsketLevering) {
	this.onsketLevering = onsketLevering;
    }

    public String getOnsketLevering() {
	return onsketLevering;
    }

    public void setTegningerJatak(String tegningerJatak) {
	this.tegningerJatak = tegningerJatak;
    }

    public String getTegningerJatak() {
	return tegningerJatak;
    }

    public void setFrakt(String frakt) {
	this.frakt = frakt;
    }

    public void setMonteringsum(String monteringsum) {
	this.monteringsum = monteringsum;
    }

    public String getMonteringsum() {
	return monteringsum;
    }

    public String getFrakt() {
	return frakt;
    }

    public void setKost(String kost) {
	this.kost = kost;
    }

    public String getKost() {
	return kost;
    }

    public void setEgenproduksjon(String egenproduksjon) {
	this.egenproduksjon = egenproduksjon;
    }

    public String getEgenproduksjon() {
	return egenproduksjon;
    }

    public void setMontering(String montering) {
	this.montering = montering;
    }

    public String getMontering() {
	return montering;
    }

    public void setTaksteinStatus(String taksteinStatus) {
	this.taksteinStatus = taksteinStatus;
    }

    public String getTaksteinStatus() {
	return taksteinStatus;
    }

    public void setPakkStatus(String pakkStatus) {
	this.pakkStatus = pakkStatus;
    }

    public String getPakkStatus() {
	return pakkStatus;
    }

    public void setGavlStatus(String gavlStatus) {
	this.gavlStatus = gavlStatus;
    }

    public String getGavlStatus() {
	return gavlStatus;
    }

    public void setTakstolStatus(String takstolStatus) {
	this.takstolStatus = takstolStatus;
    }

    public String getTakstolStatus() {
	return takstolStatus;
    }

    public void setGulvsponStatus(String gulvsponStatus) {
	this.gulvsponStatus = gulvsponStatus;
    }

    public String getGulvsponStatus() {
	return gulvsponStatus;
    }

    public void setWallStatus(String wallStatus) {
	this.wallStatus = wallStatus;
    }

    public String getWallStatus() {
	return wallStatus;
    }

    public void setEstimatedTimePack(String estimatedTimePack) {
	this.estimatedTimePack = estimatedTimePack;
    }

    public String getEstimatedTimePack() {
	return estimatedTimePack;
    }

    public void setTimeGavl(String timeGavl) {
	this.timeGavl = timeGavl;
    }

    public String getTimeGavl() {
	return timeGavl;
    }

    public void setEstimatedTimeGavl(String estimatedTimeGavl) {
	this.estimatedTimeGavl = estimatedTimeGavl;
    }

    public String getEstimatedTimeGavl() {
	return estimatedTimeGavl;
    }

    public void setTimeWall(String timeWall) {
	this.timeWall = timeWall;
    }

    public String getTimeWall() {
	return timeWall;
    }

    public void setEstimatedTimeWall(String estimatedTimeWall) {
	this.estimatedTimeWall = estimatedTimeWall;
    }

    public String getEstimatedTimeWall() {
	return estimatedTimeWall;
    }

    public void setTransport(String transport) {
	this.transport = transport;
    }

    public String getTransport() {
	return transport;
    }

    public void setProductionWeek(String productionWeek) {
	this.productionWeek = productionWeek;
    }

    public String getProductionWeek() {
	return productionWeek;
    }

    public void setProductArea(String productArea) {
	this.productArea = productArea;
    }

    public String getProductArea() {
	return productArea;
    }

    public void setInfo(String info) {
	this.info = info;
    }

    public String getInfo() {
	return info;
    }

    public void setConstructionTypeName(String constructionTypeName) {
	this.constructionTypeName = constructionTypeName;
    }

    public String getConstructionTypeName() {
	return constructionTypeName;
    }

    public void setPostoffice(String postoffice) {
	this.postoffice = postoffice;
    }

    public String getPostoffice() {
	return postoffice;
    }

    public void setPostalCode(String postalCode) {
	this.postalCode = postalCode;
    }

    public String getPostalCode() {
	return postalCode;
    }

    public void setLastname(String lastname) {
	this.lastname = lastname;
    }

    public String getLastname() {
	return lastname;
    }

    public void setFirstname(String firstname) {
	this.firstname = firstname;
    }

    public String getFirstname() {
	return firstname;
    }

    public void setOrderNr(String orderNr) {
	this.orderNr = orderNr;
    }

    public String getOrderNr() {
	return orderNr;
    }

    public void setPacklistDuration(String packlistDuration) {
	this.packlistDuration = packlistDuration;
    }

    public String getPacklistDuration() {
	return packlistDuration;
    }

    public void setPacklistDoneBy(String packlistDoneBy) {
	this.packlistDoneBy = packlistDoneBy;
    }

    public String getPacklistDoneBy() {
	return packlistDoneBy;
    }

    public void setPacklistReady(String packlistReady) {
	this.packlistReady = packlistReady;
    }

    public String getPacklistReady() {
	return packlistReady;
    }

    public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
	    Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
	ProductionColumn sortColumn1 = (ProductionColumn) sort1SelectionList.getSelection();
	ProductionColumn sortColumn2 = (ProductionColumn) sort2SelectionList.getSelection();
	ProductionColumn sortColumn3 = (ProductionColumn) sort3SelectionList.getSelection();
	Map<String, String> statusMap1 = Util.createStatusMap(productionOverviewV1.getStatus());
	Map<String, String> statusMap2 = Util.createStatusMap(productionOverviewV2.getStatus());
	int sortValue = 0;
	if (sortColumn1 != null) {
	    sortValue = sortColumn1.sort(productionOverviewV1, productionOverviewV2, statusMap1, statusMap2, statusCheckers);
	}
	if (sortValue == 0 && sortColumn2 != null) {
	    sortValue = sortColumn2.sort(productionOverviewV1, productionOverviewV2, statusMap1, statusMap2, statusCheckers);
	}
	if (sortValue == 0 && sortColumn3 != null) {
	    sortValue = sortColumn3.sort(productionOverviewV1, productionOverviewV2, statusMap1, statusMap2, statusCheckers);
	}
	return sortValue;
    }

    public boolean filter(ProductionOverviewV productionOverviewV, Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
	boolean filter = true;
	Map<String, String> statusMap = Util.createStatusMap(productionOverviewV.getStatus());
	for (ProductionColumn column : ProductionColumn.getVisibleColumns()) {
	    filter = filter && column.filter(productionOverviewV, this, statusMap, statusCheckers);
	}
	return filter;
    }

    public SelectionInList getSort1SelectionInList() {
	return sort1SelectionList;
    }

    public SelectionInList getSort2SelectionInList() {
	return sort2SelectionList;
    }

    public SelectionInList getSort3SelectionInList() {
	return sort3SelectionList;
    }

}
