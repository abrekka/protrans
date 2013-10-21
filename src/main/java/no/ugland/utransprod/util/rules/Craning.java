package no.ugland.utransprod.util.rules;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.inject.internal.Lists;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.util.Util;

/**
 * Public metoder kan ikke settes final fordi Drools avrver fra denne klassen
 * 
 * @author atle.brekka
 */
public class Craning {
	private OrderLine trossOrderLine;

	private OrderLine constructionTypeOrderLine;

	private OrderLine portOrderLine;

	private List<Craningbasis> cranings = Lists.newArrayList();

	private CraningValue degrees;

	private CraningValue width;

	private CraningValue length;

	private Boolean longWalls;

	private CraningValue wallHeight;

	private CraningValue brickWallHeight;

	private Boolean portSupport;

	private CraningValue portWidth;

	private Boolean attic;

	private BigDecimal costValue;
	private CraningCostManager craningCostManager;

	private Craning() {
	}

	public Boolean getAttic() throws ProTransException {
		if (attic == null) {
			assignAttic();
		}
		if (attic == null) {
			throw new ProTransException("Mangler egenordre");
		}
		return attic;

	}

	private void assignAttic() {
		if (getTrossOrderLine() != null) {
			OrderLineAttribute attribute = getTrossOrderLine()
					.getAttributeByName("Egenordre");
			if (attribute != null) {
				String attributeValue = attribute.getAttributeValue();
				if (attributeValue != null
						&& attributeValue.equalsIgnoreCase("Kvist")) {
					attic = Boolean.TRUE;
				} else {
					attic = Boolean.FALSE;
				}
			}
		}
	}

	public CraningValue getPortWidth() throws ProTransException {
		if (portWidth == null) {
			assignPortWidth();
		}
		if (portWidth == null) {
			throw new ProTransException("Mangler portbredde");
		}
		return portWidth;
	}

	private void assignPortWidth() {
		if (getPortOrderLine() != null) {
			OrderLineAttribute attribute = getPortOrderLine()
					.getAttributeByName("Portmål");
			if (attribute != null) {
				String attributeValue = attribute.getAttributeValue();
				if (attributeValue != null && attributeValue.indexOf("x") > 0) {
					portWidth = CraningValue.with(BigDecimal.valueOf(Double
							.valueOf(attributeValue.substring(0,
									attributeValue.indexOf("x"))))).build();
				}
			}
		}
	}

	public Boolean getPortSupport() throws ProTransException {
		if (portSupport == null) {
			assignPortSupport();
		}
		if (portSupport == null) {
			throw new ProTransException("Mangler bæring over port");
		}
		return portSupport;

	}

	private void assignPortSupport() {
		if (getConstructionTypeOrderLine() != null) {
			OrderLineAttribute attribute = getConstructionTypeOrderLine()
					.getAttributeByName("Bæring over port");
			if (attribute != null) {

				portSupport = attribute.getAttributeValueBool();
			}
		}
	}

	public CraningValue getBrickWallHeight() throws ProTransException {
		if (brickWallHeight == null) {
			assignBrickWallHeight();
		}
		if (brickWallHeight == null) {
			throw new ProTransException("Mangler murhøyde");
		}
		return brickWallHeight;
	}

	private void assignBrickWallHeight() {
		if (getConstructionTypeOrderLine() != null) {
			OrderLineAttribute attribute = getConstructionTypeOrderLine()
					.getAttributeByName("Murhøyde");
			if (attribute != null) {

				brickWallHeight = CraningValue.with(BigDecimal.valueOf(Double.valueOf(attribute
						.getAttributeNumberValue()))).build();
			}
		}
	}

	public CraningValue getWallHeight() throws ProTransException {
		if (wallHeight == null) {
			assignWallHeight();
		}
		if (wallHeight == null) {
			throw new ProTransException("Mangler vegghøyde");
		}
		return wallHeight;
	}

	private void assignWallHeight() {
		if (getConstructionTypeOrderLine() != null) {
			OrderLineAttribute attribute = getConstructionTypeOrderLine()
					.getAttributeByName("Vegghøyde");
			if (attribute != null) {

				wallHeight = CraningValue.with(Util.convertStringToBigDecimal(attribute
						.getAttributeNumberValue())).build();
			}
		}
	}

	public Boolean getLongWalls() throws ProTransException {
		if (longWalls == null) {
			assignLongWalls();
		}
		if (longWalls == null) {
			throw new ProTransException("Mangler lange vegger");
		}
		return longWalls;

	}

	private void assignLongWalls() {
		if (getConstructionTypeOrderLine() != null) {
			OrderLineAttribute attribute = getConstructionTypeOrderLine()
					.getAttributeByName("Lange vegger");
			if (attribute != null) {

				longWalls = attribute.getAttributeValueBool();
			}
		}
	}

	public CraningValue getDegrees() throws ProTransException {
		if (degrees == null) {
			assignDegrees();
		}
		if (degrees == null) {
			throw new ProTransException("Mangler vinkel");
		}
		return degrees;

	}

	private void assignDegrees() {
		if (getTrossOrderLine() != null) {
			OrderLineAttribute attribute = getTrossOrderLine()
					.getAttributeByName("Vinkel");
			if (attribute != null) {

				degrees = CraningValue.with(
						BigDecimal.valueOf(Double.valueOf(attribute
								.getAttributeNumberValue()))).build();
			}
		}
	}

	public CraningValue getWidth() throws ProTransException {
		if (width == null) {
			assignWidth();
		}
		if (width == null) {
			throw new ProTransException("Mangler bredde");
		}
		return width;
	}

	private void assignWidth() {
		if (getTrossOrderLine() != null) {
			OrderLineAttribute attribute = getTrossOrderLine()
					.getAttributeByName("Bredde");
			if (attribute != null) {

				width = CraningValue.with(BigDecimal.valueOf(Double.valueOf(attribute
						.getAttributeNumberValue()))).build();
			}
		}
	}

	public CraningValue getLength() throws ProTransException {
		if (length == null) {
			assignLength();
		}
		if (length == null) {
			throw new ProTransException("Mangler lengde");
		}
		return length;
	}

	private void assignLength() {
		if (getConstructionTypeOrderLine() != null) {
			OrderLineAttribute attribute = getConstructionTypeOrderLine()
					.getAttributeByName("Lengde");
			if (attribute != null) {

				length = CraningValue.with(BigDecimal.valueOf(Double.valueOf(attribute
						.getAttributeNumberValue()))).build();
			}
		}
	}

	public List<Craningbasis> getCranings() {
		return this.cranings;
	}

	public void setCranings(final List<Craningbasis> someCranings) {
		this.cranings = someCranings;
	}

	public OrderLine getTrossOrderLine() {
		return trossOrderLine;
	}

	public OrderLine getConstructionTypeOrderLine() {
		return constructionTypeOrderLine;
	}

	public OrderLine getPortOrderLine() {
		return portOrderLine;
	}

	public int getCraningsSize() {
		return cranings.size();
	}

	public BigDecimal getCostValue() {
		return costValue;
	}

	public void setCostValue(BigDecimal costValue) {
		this.costValue = costValue;
	}

	public static Builder with(CraningCostManager craningCostManager) {
		return new Builder(craningCostManager);
	}

	private void calculateCraningCost() {
		setCraningbasis();

		if (cranings.size() == 1) {
			costValue = craningCostManager.findCostByCraningEnum(cranings
					.get(0));
		}else if(cranings.size()==2&&cranings.contains(Craningbasis.STANDARD)&&cranings.contains(Craningbasis.STANDARD_PLUSS)){
			costValue = craningCostManager.findCostByCraningEnum(Craningbasis.STANDARD_PLUSS);
		}else if(cranings.size()==2&&cranings.contains(Craningbasis.LONG_HIGH)&&cranings.contains(Craningbasis.PORT_SUPPORT)){
			costValue = craningCostManager.findCostByCraningEnum(Craningbasis.LONG_HIGH);
		}

	}

	private void setCraningbasis() {
		for (Craningbasis craningbasis : Craningbasis.values()) {
			if (craningbasis.checkRule(this)) {
				cranings.add(craningbasis);
			}
		}
	}

	public static final class Builder {
		private Craning craning = new Craning();

		private Builder(CraningCostManager craningCostManager) {
			craning.craningCostManager = craningCostManager;
		}

		public Builder trossOrderLine(OrderLine trossOrderLine) {
			craning.trossOrderLine = trossOrderLine;
			return this;
		}

		public Builder constructionTypeOrderLine(
				OrderLine constructionTypeOrderLine) {
			craning.constructionTypeOrderLine = constructionTypeOrderLine;
			return this;
		}

		public Builder portOrderLine(OrderLine portOrderLine) {
			craning.portOrderLine = portOrderLine;
			return this;
		}

		public Craning build() {
			craning.calculateCraningCost();
			return craning;
		}
	}

	public static final class CraningValue {
		private BigDecimal value;

		public static Builder with(BigDecimal value) {
			return new Builder(value);
		}

		public static final class Builder {
			private CraningValue degrees = new CraningValue();

			private Builder(BigDecimal value) {
				degrees.value = value;
			}

			public CraningValue build() {
				return degrees;
			}
		}

		public boolean isLessThan(float testvalue) {
			return value.floatValue()<testvalue;
		}

		public boolean isGreaterOrEqualTo(float testvalue) {
			return value.floatValue()>=testvalue;
		}

		public boolean isGreaterThan(float testvalue) {
			return value.floatValue()>testvalue;
		}

		public BigDecimal getValue() {
			return value;
		}

	}
}
