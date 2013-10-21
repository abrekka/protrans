package no.ugland.utransprod.util.rules;

import static no.ugland.utransprod.util.rules.CraningAssert.assertThat;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.test.FastTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Category(FastTests.class)
public class CraningWithoutRuleTest {
	private OrderLine trossOrderLine;
	private OrderLine constructionTypeOrderLine;
	private OrderLine portOrderLine;
	@Mock
	private CraningCostManager craningCostManager;

	@Before
	public void settopp() {
		MockitoAnnotations.initMocks(this);
		when(craningCostManager.findCostByCraningEnum(Craningbasis.STANDARD))
				.thenReturn(BigDecimal.valueOf(2800));
		when(
				craningCostManager
						.findCostByCraningEnum(Craningbasis.STANDARD_PLUSS))
				.thenReturn(BigDecimal.valueOf(3500));
		when(craningCostManager.findCostByCraningEnum(Craningbasis.LONG_HIGH))
				.thenReturn(BigDecimal.valueOf(2000));
		when(
				craningCostManager
						.findCostByCraningEnum(Craningbasis.PORT_SUPPORT))
				.thenReturn(BigDecimal.valueOf(1700));
		when(craningCostManager.findCostByCraningEnum(Craningbasis.ATTIC))
				.thenReturn(BigDecimal.valueOf(0));
		when(
				craningCostManager
						.findCostByCraningEnum(Craningbasis.STANDARD_LONG_HIGH))
				.thenReturn(BigDecimal.valueOf(3800));
		when(
				craningCostManager
						.findCostByCraningEnum(Craningbasis.STANDARD_PORT_SUPPORT))
				.thenReturn(BigDecimal.valueOf(3500));
	}

	@Test
	public void testStandard1() {

		setTrossOrderLine("22°", "680", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", false, "800");
		setPortOrderLine("260x210");

		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD).hasCostValue(2800);

	}

	@Test
	public void testStandard2() {
		setTrossOrderLine("30°", "560", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", false, "800");
		setPortOrderLine("260x210");
		Craning craning = createCraning();
		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD).hasCostValue(2800);
	}

	@Test
	public void testStandard3() {
		setTrossOrderLine("38°", "500", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", false, "800");
		setPortOrderLine("260x210");
		Craning craning = createCraning();
		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD).hasCostValue(2800);
	}

	@Test
	public void testStandard3Pluss() {
		setTrossOrderLine("38°", "500", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", false, "860");
		setPortOrderLine("260x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(2)
				.containsCraningbasis(Craningbasis.STANDARD_PLUSS)
				.hasCostValue(3500);
	}

	@Test
	public void testLongWalls() {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(true, "200", "40", false, "800");
		setPortOrderLine("260x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.LONG_HIGH)
				.hasCostValue(2000);
	}

	@Test
	public void testHighWalls() {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(false, "230", "40", false, "800");
		setPortOrderLine("260x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.LONG_HIGH)
				.hasCostValue(2000);
	}

	@Test
	public void testHighBrickWall() {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(false, "200", "50", false, "800");
		setPortOrderLine("260x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.LONG_HIGH)
				.hasCostValue(2000);

	}

	@Test
	public void testPortSupport() {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", true, "800");
		setPortOrderLine("310x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.PORT_SUPPORT)
				.hasCostValue(1700);

	}

	@Test
	public void testAttic() {
		setTrossOrderLine("22°", "500", "Kvist");
		setConstructionTypeOrderLine(false, "200", "40", false, "800");
		setPortOrderLine("210x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.ATTIC).hasCostValue(0);

	}

	@Test
	public void testStandardPortSupportLongWalls() {
		setTrossOrderLine("22°", "680", "Nei");
		setConstructionTypeOrderLine(true, "200", "40", true, "800");
		setPortOrderLine("310x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD_LONG_HIGH)
				.hasCostValue(3800);

	}

	@Test
	public void testStandardHighWalls() {
		setTrossOrderLine("22°", "680", "Nei");
		setConstructionTypeOrderLine(false, "230", "40", false, "800");
		setPortOrderLine("210x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD_LONG_HIGH)
				.hasCostValue(3800);

	}

	@Test
	public void testStandardHighBrickWall() {
		setTrossOrderLine("22°", "680", "Nei");
		setConstructionTypeOrderLine(false, "200", "50", false, "800");
		setPortOrderLine("210x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD_LONG_HIGH)
				.hasCostValue(3800);
	}

	@Test
	public void testStandardPortSupport() {
		setTrossOrderLine("22°", "680", "Nei");
		setConstructionTypeOrderLine(false, "200", "40", true, "800");
		setPortOrderLine("310x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD_PORT_SUPPORT)
				.hasCostValue(3500);

	}

	@Test
	public void testStandardAttic() {
		setTrossOrderLine("22°", "680", "Kvist");
		setConstructionTypeOrderLine(false, "200", "40", false, "800");
		setPortOrderLine("210x210");
		Craning craning = createCraning();

		assertThat(craning).hasCraningbasisSize(1)
				.containsCraningbasis(Craningbasis.STANDARD_LONG_HIGH)
				.hasCostValue(3800);

	}

	@Test
	public void testHighWallsPortSupport() {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(false, "230", "40", true, "800");
		setPortOrderLine("310x210");
		Craning craning = createCraning();

		assertThat(craning)
				.hasCraningbasisSize(2)
				.containsCraningbasis(Craningbasis.LONG_HIGH,
						Craningbasis.PORT_SUPPORT).hasCostValue(2000);

	}

	@Test
	public void testGetBrickWallHeight() throws Exception {
		setTrossOrderLine("22°", "500", "Nei");
		setConstructionTypeOrderLine(false, "230", "", true, "800");
		setPortOrderLine("310x210");
		Craning craning = createCraning();
		assertThat(craning).hasBrickWallHeight(BigDecimal.ZERO);
	}

	private Craning createCraning() {
		Craning craning = Craning.with(craningCostManager)
				.trossOrderLine(trossOrderLine)
				.constructionTypeOrderLine(constructionTypeOrderLine)
				.portOrderLine(portOrderLine).build();
		return craning;
	}

	private void setTrossOrderLine(String degree, String width, String ownOrder) {
		trossOrderLine = new OrderLine();
		OrderLineAttribute attribute = new OrderLineAttribute();

		attribute.setAttributeValue(degree);
		attribute.setOrderLineAttributeName("Vinkel");
		attribute.setOrderLine(trossOrderLine);
		trossOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValue(width);
		attribute.setOrderLineAttributeName("Bredde");
		attribute.setOrderLine(trossOrderLine);
		trossOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValue(ownOrder);
		attribute.setOrderLineAttributeName("Egenordre");
		attribute.setOrderLine(trossOrderLine);
		trossOrderLine.addAttribute(attribute);

	}

	private void setConstructionTypeOrderLine(Boolean longWalls,
			String wallHeight, String brickWallHight, Boolean portSupport,
			String length) {
		constructionTypeOrderLine = new OrderLine();
		OrderLineAttribute attribute = new OrderLineAttribute();

		attribute.setAttributeValueBool(longWalls);
		attribute.setOrderLineAttributeName("Lange vegger");
		attribute.setOrderLine(constructionTypeOrderLine);
		constructionTypeOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValue(wallHeight);
		attribute.setOrderLineAttributeName("Vegghøyde");
		attribute.setOrderLine(constructionTypeOrderLine);
		constructionTypeOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValue(brickWallHight);
		attribute.setOrderLineAttributeName("Murhøyde");
		attribute.setOrderLine(constructionTypeOrderLine);
		constructionTypeOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValueBool(portSupport);
		attribute.setOrderLineAttributeName("Bæring over port");
		attribute.setOrderLine(constructionTypeOrderLine);
		constructionTypeOrderLine.addAttribute(attribute);

		attribute = new OrderLineAttribute();

		attribute.setAttributeValue(length);
		attribute.setOrderLineAttributeName("Lengde");
		attribute.setOrderLine(constructionTypeOrderLine);
		constructionTypeOrderLine.addAttribute(attribute);
	}

	private void setPortOrderLine(String portMeasure) {
		portOrderLine = new OrderLine();
		OrderLineAttribute attribute = new OrderLineAttribute();

		attribute.setAttributeValue(portMeasure);
		attribute.setOrderLineAttributeName("PortMål");
		attribute.setOrderLine(portOrderLine);
		portOrderLine.addAttribute(attribute);
	}
}
