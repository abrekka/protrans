package no.ugland.utransprod.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.junit.Test;

import net.sf.jasperreports.engine.JRException;

public class PdfUtilTest {
	private PdfUtil pdfUtil = new PdfUtil();

	@Test
	public void skalKasteExceptionDersomPdfIkkeFinnes() throws URISyntaxException, IOException, JRException {
		File pdfFil1 = new File(this.getClass().getClassLoader().getResource("faktura.pdf").toURI());
		File pdfFil2 = new File(this.getClass().getClassLoader().getResource("test1.pdf").toURI());
		pdfUtil.slaaSammenFiler("Kundenavn","1234",Arrays.asList("faktura.pdf","test2.pdf"));
	}
	
	@Test
	public void skalSlaaSammenPdf() throws URISyntaxException, IOException, JRException {
		File pdfFil1 = new File(this.getClass().getClassLoader().getResource("faktura.pdf").toURI());
		File pdfFil2 = new File(this.getClass().getClassLoader().getResource("test1.pdf").toURI());
		pdfUtil.slaaSammenFiler("Kundenavn","1234",Arrays.asList("faktura.pdf","test1.pdf"));
	}
	
	@Test
	public void skalLageForside() throws InvalidPasswordException, IOException{
		PdfUtil.lagForside();
	}
}
