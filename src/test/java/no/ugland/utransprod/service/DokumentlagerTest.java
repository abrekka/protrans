package no.ugland.utransprod.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.Test;

public class DokumentlagerTest {

	@Test
	public void skalLageTempfil() throws IOException {
		String opprinneligFilnavn="Test.pdf";
		String suffix=opprinneligFilnavn.substring(opprinneligFilnavn.lastIndexOf("."));
		String filnavn = opprinneligFilnavn.substring(0, opprinneligFilnavn.lastIndexOf("."));
		Path filpath = Files.createTempFile(filnavn, suffix);
		File pdf = filpath.toFile();
		System.out.println(pdf.getAbsolutePath());
	}
}
