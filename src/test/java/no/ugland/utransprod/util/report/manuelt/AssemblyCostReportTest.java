package no.ugland.utransprod.util.report.manuelt;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.report.AssemblyReport;
import no.ugland.utransprod.util.report.AssemblyReportImpl;
import no.ugland.utransprod.util.report.ReportViewer;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
@Category(ManuellTest.class)
public class AssemblyCostReportTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private OrderManager orderManager;
	private OrdlnManager ordlnManager;


	@Before
	public void setUp() throws Exception {
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		ordlnManager = (OrdlnManager) ModelUtil.getBean("ordlnManager");
	}


	@Test
	public void testShowReport() throws Exception {

		Order order = orderManager.findByOrderNr("43860");
		assertNotNull(order);
		orderManager.lazyLoadOrder(order,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS });
		List<Ordln> vismaOrderLines = ordlnManager.findByOrderNr(order
				.getOrderNr());
		assertNotNull(vismaOrderLines);
		assertEquals(3, vismaOrderLines.size());
		CraningCostManager craningCostManager = (CraningCostManager) ModelUtil
				.getBean(CraningCostManager.MANAGER_NAME);

		AssemblyReport assemblyReport = new AssemblyReportImpl(
				craningCostManager, order, vismaOrderLines);

		assertEquals(BigDecimal.valueOf(5000).setScale(2), assemblyReport
				.getCraningAddition().setScale(2));

		ReportViewer reportViewer = new ReportViewer("Montering");
		List<AssemblyReport> orderList = new ArrayList<AssemblyReport>();
		orderList.add(assemblyReport);
		reportViewer.generateProtransReportFromBeanAndShow(orderList,
				"Montering", ReportEnum.ASSEMBLY, null, null, null, false);

		DialogFixture dialog = WindowFinder.findDialog(
				ReportEnum.ASSEMBLY.getReportName()).using(
				BasicRobot.robotWithCurrentAwtHierarchy());
		dialog.button("ButtonCancel").click();
	}

	
}
