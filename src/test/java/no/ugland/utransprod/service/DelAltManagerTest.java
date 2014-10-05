package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.DelAlt;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class DelAltManagerTest {
    private DelAltManager delAltManager;

    @Before
    public void settOpp() throws Exception {
	delAltManager = (DelAltManager) ModelUtil.getBean(DelAltManager.MANAGER_NAME);
    }

    @Test
    public void skalHenteDelAltForPrdno() {
	List<DelAlt> list = delAltManager.finnForProdno("10541902");
	Assertions.assertThat(list).hasSize(2);
    }
}
