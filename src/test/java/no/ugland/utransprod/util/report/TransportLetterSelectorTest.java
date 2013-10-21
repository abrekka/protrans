package no.ugland.utransprod.util.report;

import junit.framework.TestCase;

public class TransportLetterSelectorTest extends TestCase{
	public void testSelectTransportLetterForGarage(){
		TransportLetter transportLetter = TransportLetterSelector.valueOf("GARASJE").getTransportLetter(null);
		assertEquals(true, transportLetter instanceof GarageTransportLetter);
	}
	
	public void testSelectTransportLetterForTross(){
		TransportLetter transportLetter = TransportLetterSelector.valueOf("TAKSTOL").getTransportLetter(null);
		assertEquals(true, transportLetter instanceof TrossTransportLetter);
	}
}
