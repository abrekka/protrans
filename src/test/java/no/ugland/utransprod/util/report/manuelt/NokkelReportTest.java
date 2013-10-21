package no.ugland.utransprod.util.report.manuelt;

import static junit.framework.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.model.OrderReserveV;
import no.ugland.utransprod.util.report.NokkelReport;

import org.junit.Test;
/**
 * @author atle.brekka
 *
 */
public class NokkelReportTest {

	@Test
	public void getNokkelTallSalgWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,52,"Garasje villa");
		
		List<NokkelSalgV>  salg = nokkelReport.getNokkelTallSalgWeekN3();
		assertNotNull(salg);
	}
	
	@Test
	public void getNokkelTallDriftProsjekteringWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<NokkelDriftProsjekteringV>  drift = nokkelReport.getNokkelTallDriftProsjekteringWeekN3();
		assertNotNull(drift);
	}
	
	@Test
	public void getNokkelTallTransportWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<NokkelTransportV>  drift = nokkelReport.getNokkelTallTransportWeekN3();
		assertNotNull(drift);
	}
	
	@Test
	public void getNokkelTallMonteringWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<NokkelMonteringV>  drift = nokkelReport.getNokkelTallMonteringWeekN3();
		assertNotNull(drift);
	}
	@Test
	public void getNokkelTallOkonomiWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<NokkelOkonomiV>  okonomi = nokkelReport.getNokkelTallOkonomiWeekN3();
		assertNotNull(okonomi);
	}
	
	@Test
	public void getNokkelTallProduksjonWeekN3(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<NokkelProduksjonV>  produksjon = nokkelReport.getNokkelTallProduksjonWeekN3();
		assertNotNull(produksjon);
	}
	
	@Test
	public void getOrderReserve(){
		NokkelReport nokkelReport = new NokkelReport(2008,4,"Garasje villa");
		
		List<OrderReserveV>  reserve = nokkelReport.getOrderReserve();
		assertNotNull(reserve);
	}
}
