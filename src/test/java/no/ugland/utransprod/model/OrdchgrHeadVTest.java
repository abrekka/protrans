package no.ugland.utransprod.model;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class OrdchgrHeadVTest {
	@Test
	public void skalLageOrdreHodeMedProduksjonsuke() {
		OrdchgrHeadV ordchgrHeadV = new OrdchgrHeadV();
		ordchgrHeadV.setOrdNo(234);
		String ordrehode = ordchgrHeadV.getHeadLineForProductionWeek(6);
		Assertions.assertThat(ordrehode).isEqualTo("H;;234;;;;;;;;;;;;;;;;;;;;;;20190201;;;;;;06;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4");
	}
}
