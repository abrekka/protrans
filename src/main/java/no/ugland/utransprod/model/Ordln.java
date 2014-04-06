package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Ordln extends BaseObject {
    private OrdlnPK ordlnPK;

    private String description;

    private BigDecimal discount;

    private BigDecimal costPrice;

    private BigDecimal price;

    private BigDecimal free1;
    private BigDecimal noOrg;
    private Prod prod;
    private BigDecimal lgtU;
    private BigDecimal hgtU;
    private BigDecimal free4;
    private BigDecimal ccstPr;

    private BigDecimal wdtu;

    private BigDecimal lgtu;

    /**
     * Brukes bare til å mellomlagre artikkeltype
     */
    private ArticleType articleType;

    private BigDecimal am;
    private Integer prodTp;
    private Integer purcno;

    public static final Ordln UNKNOWN = new Ordln() {
    };

    public Ordln() {
	super();
    }

    public Ordln(final OrdlnPK aOrdlnPK, final String aDescription, final BigDecimal aDiscount, final BigDecimal aCostPrice, final BigDecimal aPrice,
	    final BigDecimal aFree1, final BigDecimal aNoOrg, final Prod aProd, final BigDecimal aLgtU, final BigDecimal aHgtU,
	    final BigDecimal aFree4) {
	super();
	this.ordlnPK = aOrdlnPK;
	this.description = aDescription;
	this.discount = aDiscount;
	this.costPrice = aCostPrice;
	this.price = aPrice;
	this.free1 = aFree1;
	this.noOrg = aNoOrg;
	this.prod = aProd;
	this.lgtU = aLgtU;
	this.hgtU = aHgtU;
	this.free4 = aFree4;
    }

    public final OrdlnPK getOrdlnPK() {
	return ordlnPK;
    }

    public final void setOrdlnPK(final OrdlnPK aOrdlnPK) {
	this.ordlnPK = aOrdlnPK;
    }

    public Integer getProdTp() {
	return prodTp;
    }

    public void setProdTp(Integer prodTp) {
	this.prodTp = prodTp;
    }

    public final BigDecimal getCostPrice() {
	return costPrice;
    }

    public final void setCostPrice(final BigDecimal aCostPrice) {
	this.costPrice = aCostPrice;
    }

    public final String getDescription() {
	return description;
    }

    public final void setDescription(final String aDescription) {
	this.description = aDescription;
    }

    public final BigDecimal getDiscount() {
	return discount;
    }

    public final void setDiscount(final BigDecimal aDiscount) {
	this.discount = aDiscount;
    }

    public final Integer getLnno() {
	return ordlnPK.getLnno();
    }

    /*
     * public final BigDecimal getNumberOf() { return numberOf; } public final
     * void setNumberOf(final BigDecimal aNumberOf) { this.numberOf = aNumberOf;
     * }
     */

    public final Integer getOrdno() {
	return ordlnPK.getOrdno();
    }

    public final BigDecimal getPrice() {
	return price;
    }

    public final void setPrice(final BigDecimal aPrice) {
	this.price = aPrice;
    }

    /*
     * public final String getProductnr() { return productnr; } public final
     * void setProductnr(final String aProductnr) { this.productnr = aProductnr;
     * }
     */

    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof Ordln)) {
	    return false;
	}
	Ordln castOther = (Ordln) other;
	return new EqualsBuilder().append(ordlnPK, castOther.ordlnPK).isEquals();
    }

    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(ordlnPK).toHashCode();
    }

    @Override
    public final String toString() {
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("ordlnPK", ordlnPK)
		// .append("productnr", productnr)
		.append("description", description).append("discount", discount).append("free1", free1).append("costPrice", costPrice)
		.append("price", price).toString();
    }

    public final BigDecimal getFree1() {
	return free1;
    }

    public final void setFree1(final BigDecimal aFree1) {
	this.free1 = aFree1;
    }

    public BigDecimal getNoOrg() {
	return noOrg;
    }

    public void setNoOrg(BigDecimal noOrg) {
	this.noOrg = noOrg;
    }

    public Prod getProd() {
	return prod;
    }

    public void setProd(Prod prod) {
	this.prod = prod;
    }

    public BigDecimal getLgtU() {
	return lgtU;
    }

    public void setLgtU(BigDecimal lgtU) {
	this.lgtU = lgtU;
    }

    public BigDecimal getHgtU() {
	return hgtU;
    }

    public void setHgtU(BigDecimal hgtU) {
	this.hgtU = hgtU;
    }

    public final BigDecimal getFree4() {
	return free4;
    }

    public final void setFree4(final BigDecimal aFree4) {
	this.free4 = aFree4;
    }

    public BigDecimal getCcstPr() {
	return ccstPr;
    }

    public void setCcstPr(BigDecimal cstPr) {
	ccstPr = cstPr;
    }

    public BigDecimal getWdtu() {
	return wdtu;
    }

    public void setWdtu(BigDecimal aWdtu) {
	wdtu = aWdtu;

    }

    public ArticleType getArticleType() {
	return articleType;
    }

    public void setArticleType(ArticleType articleType) {
	this.articleType = articleType;
    }

    public BigDecimal getAm() {
	return am;
    }

    public void setAm(BigDecimal am) {
	this.am = am;
    }

    public static enum CostLine {
	MONTERING_VILLA("MONTERING VILLA") {
	    @Override
	    public OrderCost addCost(CostTypeManager costTypeManager, CostUnitManager costUnitManager, Order order, BigDecimal costAmount) {
		CostType costType = costTypeManager.findByName("Montering");
		CostUnit costUnit = costUnitManager.findByName("Kunde");
		OrderCost orderCost = order.getOrderCost("Montering", "Kunde");
		orderCost = orderCost != null ? orderCost : new OrderCost();
		orderCost.setCostAmount(costAmount);
		orderCost.setCostType(costType);
		orderCost.setCostUnit(costUnit);
		orderCost.setOrder(order);
		order.setDoAssembly(1);
		return orderCost;

	    }
	},
	KRANBIL("KRANBIL") {
	    @Override
	    public OrderCost addCost(CostTypeManager costTypeManager, CostUnitManager costUnitManager, Order order, BigDecimal costAmount) {
		CostType costType = costTypeManager.findByName("Kraning");
		CostUnit costUnit = costUnitManager.findByName("Kunde");
		OrderCost orderCost = order.getOrderCost("Kraning", "Kunde");
		orderCost = orderCost != null ? orderCost : new OrderCost();
		orderCost.setCostAmount(costAmount);
		orderCost.setCostType(costType);
		orderCost.setCostUnit(costUnit);
		orderCost.setOrder(order);
		return orderCost;
	    }
	},
	AVFALLSFJERNING_MONTERING("AVFALLSFJERNING MONTERING") {
	    @Override
	    public OrderCost addCost(CostTypeManager costTypeManager, CostUnitManager costUnitManager, Order order, BigDecimal costAmount) {
		CostType costType = costTypeManager.findByName("Avfall");
		CostUnit costUnit = costUnitManager.findByName("Kunde");
		OrderCost orderCost = order.getOrderCost("Avfall", "Kunde");
		orderCost = orderCost != null ? orderCost : new OrderCost();
		orderCost.setCostAmount(costAmount);
		orderCost.setCostType(costType);
		orderCost.setCostUnit(costUnit);
		orderCost.setOrder(order);
		return orderCost;
	    }
	};

	private String prodNo;
	private static Map<String, CostLine> costLines = new HashMap<String, CostLine>();

	static {
	    for (CostLine costLine : CostLine.values()) {
		costLines.put(costLine.getProdNo(), costLine);
	    }
	}

	private CostLine(String prodNo) {
	    this.prodNo = prodNo;
	}

	public static Collection<String> getCostProdnoList() {
	    List<String> prodnoList = new ArrayList<String>();
	    for (CostLine costLine : CostLine.values()) {
		prodnoList.add(costLine.getProdNo());
	    }
	    return prodnoList;
	}

	private String getProdNo() {
	    return prodNo;
	}

	public static CostLine getCostLine(String prodNo) {
	    return costLines.get(prodNo.toUpperCase());
	}

	public abstract OrderCost addCost(CostTypeManager costTypeManager, CostUnitManager costUnitManager, Order order, BigDecimal costAmount);
    }

    public Integer getPurcno() {
	return purcno;
    }

    public void setPurcno(Integer purcno) {
	this.purcno = purcno;
    }
}
