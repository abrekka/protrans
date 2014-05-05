package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Test;

public class FakturagrunnlagVManagerTest {

    @Test
    public void skalHenteFakturagrunnlag() {
	FakturagrunnlagVManager fakturagrunnlagVManager = (FakturagrunnlagVManager) ModelUtil.getBean(FakturagrunnlagVManager.MANAGER_NAME);
	List<FakturagrunnlagV> fakturagrunnlag = fakturagrunnlagVManager.findFakturagrunnlag(11099);
	Assertions.assertThat(fakturagrunnlag).isNotEmpty();
    }
}
