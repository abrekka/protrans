package no.ugland.utransprod.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.junit.Test;

public class PdfUtilTest {
	private PdfUtil pdfUtil = new PdfUtil();

	@Test
	public void skalSlaaSammenPdf() throws URISyntaxException, IOException {
		File pdfFil1 = new File(this.getClass().getClassLoader().getResource("faktura.pdf").toURI());
		File pdfFil2 = new File(this.getClass().getClassLoader().getResource("test1.pdf").toURI());
		pdfUtil.slaaSammenFiler(Arrays.asList(pdfFil1,pdfFil2));
	}
}
