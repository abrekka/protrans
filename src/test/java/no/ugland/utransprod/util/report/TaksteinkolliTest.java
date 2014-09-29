package no.ugland.utransprod.util.report;

import java.math.BigDecimal;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class TaksteinkolliTest {

    @Test
    public void skalBeregneAntallPaller() {
	Taksteinkolli taksteinkolli = new Taksteinkolli().medBeskrivelse(Taksteinkolli.SKARPNES_DOBBEL_EDEL_SORT).medAntall(
		BigDecimal.valueOf(659.000000));
	Assertions.assertThat(taksteinkolli.beregnAntallPaller()).isEqualTo("3");
    }

    @Test
    public void skalBeregneAntallPakker() {
	Taksteinkolli taksteinkolli = new Taksteinkolli().medBeskrivelse(Taksteinkolli.SKARPNES_DOBBEL_EDEL_SORT).medAntall(
		BigDecimal.valueOf(659.000000));
	Assertions.assertThat(taksteinkolli.beregnAntallPakker()).isEqualTo("20");
    }

    @Test
    public void skalLagePalleinfo() {
	Taksteinkolli taksteinkolli = new Taksteinkolli().medBeskrivelse(Taksteinkolli.SKARPNES_DOBBEL_EDEL_SORT).medAntall(
		BigDecimal.valueOf(444.000000));
	Assertions.assertThat(taksteinkolli.getPalleinfo()).isEqualTo("(2/14)");
    }

    @Test
    public void skalReturnereAntallSomStreng() {
	Taksteinkolli taksteinkolli = new Taksteinkolli().medBeskrivelse(Taksteinkolli.SKARPNES_DOBBEL_EDEL_SORT).medAntall(
		BigDecimal.valueOf(444.000000));
	Assertions.assertThat(taksteinkolli.getAntallstreng()).isEqualTo("444");
    }

}
