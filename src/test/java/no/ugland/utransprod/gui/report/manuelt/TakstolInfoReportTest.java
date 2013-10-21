package no.ugland.utransprod.gui.report.manuelt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.TakstolInfoV;
import no.ugland.utransprod.model.TakstolInfoVPK;
import no.ugland.utransprod.util.report.ReportViewer;

import org.fest.swing.core.BasicRobot;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.Test;

import com.birosoft.liquid.LiquidLookAndFeel;

public class TakstolInfoReportTest  {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	public void testShowReport() throws Exception {
		ReportViewer reportViewer = new ReportViewer("Takstolinfo");
		Collection<TakstolInfoV> heads = new ArrayList<TakstolInfoV>();
		TakstolInfoV takstolInfo = new TakstolInfoV();
		takstolInfo.setTakstolInfoVPK(new TakstolInfoVPK("100", 1));

		takstolInfo.setKundenr(200);
		takstolInfo.setNavn("Navn");
		takstolInfo.setLeveringsadresse("Leveringsadresse");
		takstolInfo.setPostnr("PostNr");
		takstolInfo.setPoststed("Poststed");
		takstolInfo.setHoydeOverHavet(500);
		takstolInfo.setBeregnetFor(" beregnetfor");
		takstolInfo.setSnolast(500);
		takstolInfo.setEgenvekt(1000);
		takstolInfo.setUtstikkType(1);
		takstolInfo.setKode("kode");
		takstolInfo.setAntall(BigDecimal.valueOf(1));
		takstolInfo.setProdno("prodno");
		takstolInfo.setBeskrivelse("beskrivelse");
		takstolInfo.setTakstoltype("takstoltype");
		takstolInfo.setVirkesbredde(BigDecimal.valueOf(5.5));
		takstolInfo.setUtstikkslengde(200);
		takstolInfo.setSvilleklaring(200);
		takstolInfo.setRombreddeAStol(30);
		takstolInfo.setBaeringGulv("baeringgulv");
		takstolInfo.setIsolasjonshoyde("isoloasjonsh");
		takstolInfo.setLoddkutt(BigDecimal.valueOf(12.6));
		takstolInfo.setNedstikk(BigDecimal.valueOf(90));
		takstolInfo.setBeregnetTid(BigDecimal.valueOf(2));
		heads.add(takstolInfo);

		takstolInfo = new TakstolInfoV();
		takstolInfo.setTakstolInfoVPK(new TakstolInfoVPK("100", 1));

		takstolInfo.setKundenr(200);
		takstolInfo.setNavn("Navn");
		takstolInfo.setLeveringsadresse("Leveringsadresse");
		takstolInfo.setPostnr("PostNr");
		takstolInfo.setPoststed("Poststed");
		takstolInfo.setHoydeOverHavet(500);
		takstolInfo.setBeregnetFor(" beregnetfor");
		takstolInfo.setSnolast(500);
		takstolInfo.setEgenvekt(1000);
		takstolInfo.setUtstikkType(1);
		takstolInfo.setKode("kode2");
		takstolInfo.setAntall(BigDecimal.valueOf(11));
		takstolInfo.setProdno("prodno2");
		takstolInfo.setBeskrivelse("beskrivelse2");
		takstolInfo.setTakstoltype("takstoltype2");
		takstolInfo.setVirkesbredde(BigDecimal.valueOf(55.5));
		takstolInfo.setUtstikkslengde(2200);
		takstolInfo.setSvilleklaring(2200);
		takstolInfo.setRombreddeAStol(230);
		takstolInfo.setBaeringGulv("baeringgulv2");
		takstolInfo.setIsolasjonshoyde("isoloasjonsh2");
		takstolInfo.setLoddkutt(BigDecimal.valueOf(122.6));
		takstolInfo.setNedstikk(BigDecimal.valueOf(920));
		takstolInfo.setBeregnetTid(BigDecimal.valueOf(22));
		heads.add(takstolInfo);

		String reportFileName = "Takstolinfo - " + takstolInfo.getOrdernr()
				+ ".pdf";
		reportViewer.generateProtransReportFromBeanAndShow(heads,
				"Takstolinfo", ReportEnum.TAKSTOL_INFO, null, reportFileName,
				null, false);

		DialogFixture dialog = WindowFinder.findDialog(
				ReportEnum.TAKSTOL_INFO.getReportName()).using(
				BasicRobot.robotWithCurrentAwtHierarchy());
		dialog.button("ButtonCancel").click();
	}

}
