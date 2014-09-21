package no.ugland.utransprod.service;

import java.util.Collection;

import no.ugland.utransprod.model.AssemblyV;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

public class AssemblyVManagerTest {
    private AssemblyVManager assemblyVManager;

    @Before
    public void setUp() throws Exception {
	assemblyVManager = (AssemblyVManager) ModelUtil.getBean("assemblyVManager");
    }

    @Test
    public void skalFinneAlleForGittAar() {
	Collection<AssemblyV> monteringer = assemblyVManager.findByYear(2014);
	Assertions.assertThat(monteringer).isNotEmpty();
    }
}
