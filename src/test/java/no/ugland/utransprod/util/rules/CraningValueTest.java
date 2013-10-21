package no.ugland.utransprod.util.rules;

import static org.fest.assertions.Assertions.assertThat;

import java.math.BigDecimal;

import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.rules.Craning.CraningValue;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class CraningValueTest {

	@Test
	public void skalSjekkeAtVerdiErMindreEnn(){
		assertThat(CraningValue.with(BigDecimal.valueOf(20)).build().isLessThan(25)).isTrue();
	}
	
	@Test
	public void skalSjekkeAtVerdiErStoerreEllerLik(){
		assertThat(CraningValue.with(BigDecimal.valueOf(680)).build().isGreaterOrEqualTo(680)).isTrue();
	}
	
	@Test
	public void skalSjekkeAtVerdierStoerreEnn(){
		assertThat(CraningValue.with(BigDecimal.valueOf(300)).build().isGreaterThan(300)).isFalse();
		assertThat(CraningValue.with(BigDecimal.valueOf(301)).build().isGreaterThan(300)).isTrue();
	}
}
