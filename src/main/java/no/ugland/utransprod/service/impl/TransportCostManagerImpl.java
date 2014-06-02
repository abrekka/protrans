package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JLabel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Area;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.County;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.service.AreaManager;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.CountyManager;
import no.ugland.utransprod.service.ITransportCostAddition;
import no.ugland.utransprod.service.TransportCostAdditionManager;
import no.ugland.utransprod.service.TransportCostBasisManager;
import no.ugland.utransprod.service.TransportCostManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelUtil;

public class TransportCostManagerImpl implements TransportCostManager {

    private TransportCostDAO dao;

    private TransportCostAdditionManager transportCostAdditionManager;

    private CostTypeManager costTypeManager;

    private CostUnitManager costUnitManager;

    private TransportCostBasisManager transportCostBasisManager;

    private TransportManager transportManager;

    private static CostType costType;

    private static CostUnit costUnit;

    private String infoText = "Importerer gyldige adresser...";

    private JLabel infoLabel;

    private AreaManager areaManager;

    private CountyManager countyManager;

    public final void setTransportCostDAO(final TransportCostDAO aDao) {
	this.dao = aDao;
    }

    public final void setTransportCostAdditionManager(final TransportCostAdditionManager aTransportCostAdditionManager) {
	transportCostAdditionManager = aTransportCostAdditionManager;
    }

    public final void setTransportManager(final TransportManager aTransportManager) {
	transportManager = aTransportManager;
    }

    public final void setCostTypeManager(final CostTypeManager aCostTypeManager) {
	costTypeManager = aCostTypeManager;
    }

    public final void setCostUnitManager(final CostUnitManager aCostUnitManager) {
	costUnitManager = aCostUnitManager;
    }

    public final void setTransportCostBasisManager(final TransportCostBasisManager aTransportCostBasisManager) {
	transportCostBasisManager = aTransportCostBasisManager;
    }

    public final void setAreaManager(final AreaManager aAreaManager) {
	areaManager = aAreaManager;
    }

    public final void setCountyManager(final CountyManager aCountyManager) {
	countyManager = aCountyManager;
    }

    public final List<TransportCost> findAll() {
	return dao.findAll();
    }

    public final void importAllPostalCodes(final String excelFileName, final boolean add) throws ProTransException {

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_IMPORT);
	if (!add) {
	    deleteAllPostalCodes();
	}
	importOrUpdateTransportCosts(new TransportCostImport(), excelUtil);

    }

    public final TransportCost findByPostalCode(final String postalCode) {
	return dao.findByPostalCode(postalCode);
    }

    public final void updatePricesFromFile(final String excelFileName) throws ProTransException {
	infoText = "Oppdaterer priser ...";
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_UPDATE);
	setAllPostalCodesInvalid();
	importOrUpdateTransportCosts(new TransportCostUpdate(), excelUtil);
	importTransportCostAdditions(excelUtil);
    }

    public final List<TransportCostBasis> generateTransportCostList(final Periode period) throws ProTransException {
	List<Transportable> transportableList = getTransportableList(period);

	Map<Supplier, List<Transportable>> supplierList;
	List<TransportCostBasis> transportCostBasisList = null;

	if (transportableList != null && transportableList.size() != 0) {
	    supplierList = createSupplierList(transportableList);
	    transportCostBasisList = createTransportCostList(supplierList, period);
	    transportCostBasisManager.saveTransportCostBasisList(transportCostBasisList);
	}
	return transportCostBasisList;

    }

    private List<TransportCostBasis> removeEmptyTransportCostBasis(final List<TransportCostBasis> transportCostBasisList) {
	List<TransportCostBasis> returnList = new ArrayList<TransportCostBasis>();
	if (transportCostBasisList != null) {
	    for (TransportCostBasis basis : transportCostBasisList) {
		if (basis.getOrderCosts() == null || basis.getOrderCosts().size() == 0) {
		    transportCostBasisManager.removeObject(basis);
		} else {
		    returnList.add(basis);
		}
	    }
	}
	return returnList;
    }

    private List<Transportable> getTransportableList(final Periode periode) throws ProTransException {
	List<Transportable> transportableList = new ArrayList<Transportable>();
	List<Transport> transports = transportManager.findInPeriode(periode, "Garasje");
	if (transports != null) {
	    for (Transport transport : transports) {
		if (transport.getSent() == null) {
		    throw new ProTransException("Transport " + transport + " er ikke satt til sendt");
		}
		transportableList.addAll(getTransportables(transport));
	    }
	}
	return transportableList;
    }

    private List<Transportable> getTransportables(final Transport transport) throws ProTransException {
	List<Transportable> transportables = transport.getTransportables();
	List<Transportable> returnTransportables = new ArrayList<Transportable>();
	if (transportables != null) {
	    for (Transportable transportable : transportables) {
		if (transportable.getSent() == null) {
		    throw new ProTransException("Ordre " + transportable + " er ikke sendt");
		}
		if (transportable.getProductAreaGroup().getProductAreaGroupName().equalsIgnoreCase("Garasje")) {
		    returnTransportables.add(transportable);
		}
	    }
	}
	return returnTransportables;
    }

    private void importTransportCostAdditions(final ExcelUtil excelUtil) {
	deleteAllTransportCostAdditions();
	importAdditions(excelUtil);
    }

    private void importAdditions(final ExcelUtil excelUtil) {
	int row = 1;
	String desc = excelUtil.readCell(row, COLUMN_UPDATE_ADDITION_DESCRIPTION, null);
	while (desc != null) {
	    importAddition(row, excelUtil);
	    row++;
	    desc = excelUtil.readCell(row, COLUMN_UPDATE_ADDITION_DESCRIPTION, null);
	}
    }

    private void importAddition(final int row, final ExcelUtil excelUtil) {
	TransportCostAddition transportCostAddition = new TransportCostAddition();
	transportCostAddition.setDescription(excelUtil.readCell(row, COLUMN_UPDATE_ADDITION_DESCRIPTION, null));
	transportCostAddition.setBasis(excelUtil.readCell(row, COLUMN_UPDATE_BASIS, null));
	transportCostAddition.setAddition(excelUtil.readCellAsBigDecimal(row, COLUMN_UPDATE_ADDITION_ADDITION));
	transportCostAddition.setMetric(excelUtil.readCell(row, COLUMN_UPDATE_METRIC, null));
	transportCostAddition.setTransportBasis(excelUtil.readCellAsInteger(row, COLUMN_UPDATE_TRANSPORT_BASIS));
	transportCostAddition.setMemberOfMaxAdditions(excelUtil.readCellAsInteger(row, COLUMN_UPDATE_MEMBER_OF_MAX_ADDITIONS));
	transportCostAdditionManager.saveTransportCostAddition(transportCostAddition);
    }

    private void deleteAllTransportCostAdditions() {
	transportCostAdditionManager.deleteAll();
    }

    private void setAllPostalCodesInvalid() {
	dao.setAllPostalCodesInvalid();
    }

    private void deleteAllPostalCodes() {
	dao.removeAll();
    }

    private void importOrUpdateTransportCosts(final TransportCostUpdateable updateable, final ExcelUtil excelUtil) throws ProTransException {
	int row = 1;
	String postalCode = excelUtil.readCell(row, 0, "%1$04.0f");

	while (postalCode != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    updateable.updateTransportCost(row, excelUtil);
	    row++;
	    postalCode = excelUtil.readCell(row, 0, "%1$04.0f");
	}
    }

    private void importZoneAdditionAssembly(final TransportCostUpdateable updateable, final ExcelUtil excelUtil) throws ProTransException {
	infoText = "Oppdaterer sonetillegg for montering ...";
	int row = 1;
	String postalCode = excelUtil.readCell(row, 0, "%1$04.0f");

	while (postalCode != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    updateable.updateTransportCost(row, excelUtil);
	    row++;
	    postalCode = excelUtil.readCell(row, 0, "%1$04.0f");
	}
    }

    final void importTransportCost(final int row, final ExcelUtil excelUtil) throws ProTransException {
	TransportCost transportCost = new TransportCost();
	transportCost.setPostalCode(excelUtil.readCell(row, COLUMN_TRANSPORT_COST_POSTAL_CODE, "%1$04.0f"));
	transportCost.setPlace(excelUtil.readCell(row, COLUMN_TRANSPORT_COST_PLACE, null));
	String areaCode = excelUtil.readCell(row, COLUMN_TRANSPORT_COST_AREA_CODE, "%1$04.0f");

	Area area = areaManager.load(areaCode);
	if (area == null) {
	    throw new ProTransException("Kommunenr " + areaCode + " finnes ikke!");
	}
	transportCost.setArea(area);

	transportCost.setValid(1);
	dao.saveObject(transportCost);

    }

    final void importZoneAdditionAssembly(final int row, final ExcelUtil excelUtil) throws ProTransException {
	String postalCode = excelUtil.readCell(row, COLUMN_ZONE_ADDITION_POSTAL_CODE, "%1$04.0f");
	TransportCost transportCost = dao.findByPostalCode(postalCode);

	if (transportCost != null) {
	    Integer zoneAddition = excelUtil.readCellAsInteger(row, COLUMN_ZONE_ADDITION);
	    if (zoneAddition != null) {
		transportCost.setZoneAdditionAssembly(zoneAddition);
		transportCost.setValid(1);
		dao.saveObject(transportCost);
	    }
	}

    }

    final void updatePriceForTransportCosts(final int row, final ExcelUtil excelUtil) {
	String postalCodeFrom = excelUtil.readCell(row, COLUMN_UPDATE_POSTAL_CODE_FROM, "%1$04.0f");
	String postalCodeTo = excelUtil.readCell(row, COLUMN_UPDATE_POSTAL_CODE_TO, "%1$04.0f");
	BigDecimal price = excelUtil.readCellAsBigDecimal(row, COLUMN_UPDATE_PRICE);
	BigDecimal addition = excelUtil.readCellAsBigDecimal(row, COLUMN_UPDATE_ADDITION);
	Integer maxAddition = excelUtil.readCellAsInteger(row, COLUMN_UPDATE_MAX_ADDITION);

	dao.updatePriceForPostalCodes(postalCodeFrom, postalCodeTo, price, maxAddition, addition);
    }

    private interface TransportCostUpdateable {
	void updateTransportCost(final int row, final ExcelUtil excelUtil) throws ProTransException;
    }

    class TransportCostImport implements TransportCostUpdateable {

	public void updateTransportCost(final int row, final ExcelUtil excelUtil) throws ProTransException {
	    importTransportCost(row, excelUtil);

	}

    }

    class ZoneAdditionAssemblyImport implements TransportCostUpdateable {

	public void updateTransportCost(final int row, final ExcelUtil excelUtil) throws ProTransException {
	    importZoneAdditionAssembly(row, excelUtil);

	}

    }

    class TransportCostUpdate implements TransportCostUpdateable {

	public void updateTransportCost(final int row, final ExcelUtil excelUtil) {
	    updatePriceForTransportCosts(row, excelUtil);

	}

    }

    private List<TransportCostBasis> createTransportCostList(final Map<Supplier, List<Transportable>> supplierList, final Periode period)
	    throws ProTransException {
	List<TransportCostBasis> transportCostBasisList = new ArrayList<TransportCostBasis>();

	Set<Supplier> suppliers = supplierList.keySet();

	for (Supplier supplier : suppliers) {
	    transportCostBasisList.add(createTransportCostForOrders(supplierList.get(supplier), supplier, period));
	}

	transportCostBasisList = removeEmptyTransportCostBasis(transportCostBasisList);
	return transportCostBasisList;

    }

    private TransportCostBasis createTransportCostForOrders(final List<Transportable> transportables, final Supplier supplier, final Periode period)
	    throws ProTransException {
	TransportCostBasis transportCostBasis = new TransportCostBasis(period);
	try {

	    transportCostBasis.setSupplier(supplier);
	    transportCostBasisManager.saveTransportCostBasis(transportCostBasis);

	    for (Transportable transportable : transportables) {
		if (transportable.getSent() == null) {
		    throw new ProTransException("Ordre " + transportable + " er ikke satt til sendt");
		}
		checkTransportableAndCalculateCost(supplier, period, transportCostBasis, transportable);
	    }

	    return transportCostBasis;
	} catch (ProTransException e) {
	    e.printStackTrace();
	    removeFailedTransportCostBasis(transportCostBasis);
	    throw e;
	}
    }

    private void checkTransportableAndCalculateCost(final Supplier supplier, final Periode period, final TransportCostBasis transportCostBasis,
	    final Transportable transportable) throws ProTransException {
	if (!transportable.hasTransportCostBasis()) {
	    OrderCost orderCost = calculateTransportCostForTransportable(transportable, supplier, period);
	    if (orderCost != null) {
		transportCostBasis.addOrderCost(orderCost);
	    }
	}
    }

    private void removeFailedTransportCostBasis(final TransportCostBasis transportCostBasis) {
	if (transportCostBasis.getTransportCostBasisId() != null) {
	    if (transportCostBasis.getOrderCosts() != null) {
		Set<OrderCost> orderCosts = transportCostBasis.getOrderCosts();
		for (OrderCost orderCost : orderCosts) {
		    orderCost.getOrder().removeOrderCost(orderCost);
		}
	    }
	    transportCostBasisManager.removeTransportCostBasis(transportCostBasis);
	}
    }

    private OrderCost calculateTransportCostForTransportable(final Transportable transportable, final Supplier supplier, final Periode period)
	    throws ProTransException {

	TransportCost transportCost = null;

	transportCost = findByPostalCode(transportable.getOrder().getPostalCode());
	BigDecimal cost = BigDecimal.valueOf(0);
	String orderCostComment = null;
	if (transportCost == null) {
	    orderCostComment = "Har ikke gyldig postnummer";
	} else if (transportable.getPostShipment() == null) {
	    cost = transportCost.getCost();

	}
	Map<ITransportCostAddition, BigDecimal> additionalCost = null;
	if (transportCost != null) {
	    additionalCost = transportCostAdditionManager.calculateCostAddition(transportable, transportCost, period);
	}

	return getOrderCost(transportable, cost, additionalCost, supplier, orderCostComment);

    }

    private OrderCost getOrderCost(final Transportable transportable, final BigDecimal basisCost,
	    final Map<ITransportCostAddition, BigDecimal> additionalCost, final Supplier supplier, final String orderCostComment)
	    throws ProTransException {
	OrderCost orderCost = null;
	StringBuilder costDetails = new StringBuilder();
	BigDecimal additionValue = BigDecimal.valueOf(0);

	if (additionalCost != null) {
	    additionValue = generateCostDetailsAndGetAdditionValue(basisCost, additionalCost, costDetails, additionValue);
	}

	String comment = "";
	if (orderCostComment != null) {
	    comment = orderCostComment;
	} else if (costDetails.length() > 0) {
	    comment = costDetails.substring(0, costDetails.length() - 1);
	}

	if (transportable.getPostShipment() != null) {
	    comment = "Etterlevering:" + comment;
	}

	BigDecimal cost = basisCost.add(additionValue);

	Order order = transportable.getOrder();
	orderCost = new OrderCost();
	orderCost.setComment(comment);

	orderCost.setCostAmount(cost);
	orderCost.setCostType(getCostType());
	orderCost.setCostUnit(getCostUnit());
	orderCost.setSupplier(supplier);
	orderCost.setPostShipment(transportable.getPostShipment());
	order.addOrderCost(orderCost);
	return orderCost;
    }

    private BigDecimal generateCostDetailsAndGetAdditionValue(final BigDecimal basisCost,
	    final Map<ITransportCostAddition, BigDecimal> additionalCost, final StringBuilder costDetails, final BigDecimal aAdditionValue) {
	BigDecimal additionValue = aAdditionValue;
	if (basisCost.longValue() != 0) {
	    costDetails.append("Grunnpris:").append(basisCost).append(",");
	}
	if (additionalCost != null) {
	    Set<ITransportCostAddition> keySet = additionalCost.keySet();
	    for (ITransportCostAddition addition : keySet) {
		BigDecimal value = additionalCost.get(addition);
		costDetails.append(addition.getInfo()).append(":").append(value.setScale(2)).append(",");
		additionValue = additionValue.add(value);
	    }
	}
	return additionValue;
    }

    private CostType getCostType() throws ProTransException {
	if (costType == null) {
	    costType = costTypeManager.findByName("Fraktkost");
	    if (costType == null) {
		throw new ProTransException("Kan ikke finne kostnadstype Fraktkost");
	    }
	}
	return costType;
    }

    private CostUnit getCostUnit() throws ProTransException {
	if (costUnit == null) {
	    costUnit = costUnitManager.findByName("Intern");
	    if (costUnit == null) {
		throw new ProTransException("Kan ikke finne kostnadsenhet Intern");
	    }
	}
	return costUnit;
    }

    private Map<Supplier, List<Transportable>> createSupplierList(final List<Transportable> transportables) throws ProTransException {
	Map<Supplier, List<Transportable>> supplierList = new Hashtable<Supplier, List<Transportable>>();
	for (Transportable transportable : transportables) {
	    Supplier supplier = getSupplierForTransportable(transportable);
	    addOrderToSupplierList(supplierList, transportable, supplier);
	}
	return supplierList;
    }

    private void addOrderToSupplierList(final Map<Supplier, List<Transportable>> supplierList, final Transportable transportable,
	    final Supplier supplier) {
	List<Transportable> transportableList = supplierList.get(supplier);
	if (transportableList == null) {
	    transportableList = new ArrayList<Transportable>();
	}
	transportableList.add(transportable);
	supplierList.put(supplier, transportableList);
    }

    private Supplier getSupplierForTransportable(final Transportable transportable) throws ProTransException {
	Supplier supplier = transportable.getTransport().getSupplier();
	if (supplier == null) {
	    throw new ProTransException("Order " + transportable + " mangler transportør");
	}
	return supplier;
    }

    public final void setLabelInfo(final JLabel aLabel) {
	infoLabel = aLabel;

    }

    public final void importAllAreas(final String excelFileName, final boolean add) throws ProTransException {

	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_IMPORT_AREA);
	if (!add) {
	    deleteAllAreas();
	}
	importAreas(excelUtil);

    }

    private void importAreas(final ExcelUtil excelUtil) throws ProTransException {
	int row = 1;
	String areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");

	while (areaCode != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    importArea(row, excelUtil);
	    row++;
	    areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");
	}
    }

    private void importArea(final int row, final ExcelUtil excelUtil) throws ProTransException {
	Area area = new Area();
	String areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");
	County county;
	if (areaCode != null && areaCode.length() > 2) {
	    String countyNr = areaCode.substring(0, 2);
	    county = countyManager.load(countyNr);
	    if (county == null) {
		throw new ProTransException("Fylkesnr " + countyNr + " finnes ikke");
	    }
	    area.setCounty(county);
	} else {
	    throw new ProTransException("Feil kommunenr " + areaCode);
	}
	area.setAreaCode(areaCode);
	area.setAreaName(excelUtil.readCell(row, COLUMN_AREA_AREA_NAME, null));
	areaManager.saveArea(area);
    }

    private void deleteAllAreas() {
	areaManager.removeAll();
    }

    public final void importAllCounties(final String excelFileName, final boolean add) throws ProTransException {
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_IMPORT_COUNTY);
	if (!add) {
	    deleteAllCounties();
	}
	importCounties(excelUtil);

    }

    private void deleteAllCounties() {
	countyManager.removeAll();
    }

    private void importCounties(final ExcelUtil excelUtil) {
	int row = 1;
	String countyNr = excelUtil.readCell(row, COLUMN_COUNTY_COUNTY_NR, "%1$04.0f");

	while (countyNr != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    importCounty(row, excelUtil);
	    row++;
	    countyNr = excelUtil.readCell(row, COLUMN_COUNTY_COUNTY_NR, "%1$04.0f");
	}
    }

    private void importCounty(final int row, final ExcelUtil excelUtil) {
	County county = new County();

	county.setCountyNr(excelUtil.readCell(row, COLUMN_COUNTY_COUNTY_NR, "%1$04.0f"));
	county.setCountyName(excelUtil.readCell(row, COLUMN_COUNTY_COUNTY_NAME, null));
	countyManager.saveCounty(county);
    }

    public final void removeAll() {
	dao.removeAll();

    }

    public void importSnowLoad(String excelFileName) throws ProTransException {
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_IMPORT_SNOW_LOAD);

	importSnowload(excelUtil);

    }

    private void importSnowload(ExcelUtil excelUtil) throws ProTransException {
	int row = 1;
	String areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");

	while (areaCode != null) {
	    if (infoLabel != null) {
		infoLabel.setText(infoText + row);
	    }
	    importSnowLoad(row, excelUtil);
	    row++;
	    areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");
	}

    }

    private void importSnowLoad(int row, ExcelUtil excelUtil) throws ProTransException {
	Area area = null;
	String areaCode = excelUtil.readCell(row, COLUMN_AREA_AREA_CODE, "%1$04.0f");
	County county;
	if (areaCode != null && areaCode.length() > 2) {
	    area = areaManager.findByAreaCode(areaCode);
	    if (area == null) {
		area = new Area();
		String countyNr = areaCode.substring(0, 2);
		county = countyManager.load(countyNr);
		if (county == null) {
		    throw new ProTransException("Fylkesnr " + countyNr + " finnes ikke");
		}
		area.setCounty(county);
		area.setAreaCode(areaCode);
	    }

	    area.setSnowloadBasicValue(excelUtil.readCellAsBigDecimal(row, COLUMN_AREA_SNOWLOAD_BASIC_VALUE));
	    area.setHeightLimit(excelUtil.readCellAsInteger(row, COLUMN_AREA_HEIGHT_LIMIT));
	    area.setDeltaSnowload(excelUtil.readCellAsBigDecimal(row, COLUMN_AREA_DELTA_SNOWLOAD));
	    area.setSnowloadMax(excelUtil.readCellAsBigDecimal(row, COLUMN_AREA_SNOWLOAD_MAX));
	    area.setSnowloadComment(excelUtil.readCell(row, COLUMN_AREA_SNOWLOAD_COMMENT, null));
	} else {
	    throw new ProTransException("Feil kommunenr " + areaCode);
	}

	areaManager.saveArea(area);

    }

    public static Builder med() {
	return new Builder();
    }

    public static class Builder {
	private TransportCostManagerImpl transportCostManagerImpl = new TransportCostManagerImpl();

	private Builder() {
	}

	public Builder transportManager(TransportManager transportManager) {
	    transportCostManagerImpl.transportManager = transportManager;
	    return this;
	}

	public TransportCostManager build() {
	    return transportCostManagerImpl;
	}

	public Builder transportCostBasisManager(TransportCostBasisManager transportCostBasisManager) {
	    transportCostManagerImpl.transportCostBasisManager = transportCostBasisManager;
	    return this;
	}

	public Builder transportCostDAO(TransportCostDAO transportCostDAO) {
	    transportCostManagerImpl.dao = transportCostDAO;
	    return this;
	}

	public Builder costTypeManager(CostTypeManager costTypeManager) {
	    transportCostManagerImpl.costTypeManager = costTypeManager;
	    return this;
	}

	public Builder costUnitManager(CostUnitManager costUnitManager) {
	    transportCostManagerImpl.costUnitManager = costUnitManager;
	    return this;
	}

	public Builder transportCostAdditionManager(TransportCostAdditionManager transportCostAdditionManager) {
	    transportCostManagerImpl.transportCostAdditionManager = transportCostAdditionManager;
	    return this;
	}
    }

    public void importAllZoneAddtionAssembly(String excelFileName) {
	ExcelUtil excelUtil = new ExcelUtil();
	excelUtil.openExcelFileForRead(excelFileName);
	ExcelUtil.checkFileFormat(excelUtil, FILE_HEADER_ZONE_ADDITION_ASSEMBLY_IMPORT);
	importZoneAdditionAssembly(new ZoneAdditionAssemblyImport(), excelUtil);

    }
}
