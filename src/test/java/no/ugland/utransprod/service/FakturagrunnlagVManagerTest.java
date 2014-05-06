package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class FakturagrunnlagVManagerTest {

    @Test
    public void skalHenteBestillingsnrrakt() {
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	Integer bestillingsnrFrakt = fakturagrunnlagVManager.finnBestillingsnrFrakt(15257);
	Assertions.assertThat(bestillingsnrFrakt).isEqualTo(2580);
    }

    @Test
    public void skalHenteFakturagrunnlag() {
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	List<FakturagrunnlagV> fakturagrunnlag = fakturagrunnlagVManager.findFakturagrunnlag(11099);
	Assertions.assertThat(fakturagrunnlag).isNotEmpty();
    }
}
