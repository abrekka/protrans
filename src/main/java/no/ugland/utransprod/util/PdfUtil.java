package no.ugland.utransprod.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import no.ugland.utransprod.ProTransException;

public class PdfUtil {

	public static String slaaSammenFiler(String kundenavn, String ordrenummer, List<String> filer)
			throws IOException, JRException {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("kundenavn", kundenavn);
		parameters.put("ordrenummer", ordrenummer);
		InputStream iconStream = PdfUtil.class.getClassLoader().getResourceAsStream("images/montering_forside.jpg");
		parameters.put("bakgrunn", iconStream);

		InputStream stream = PdfUtil.class.getClassLoader().getResourceAsStream("reports/montering_forside.jasper");

		if (stream == null) {
			throw new ProTransException("Fant ikke rapport");
		}

		String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");
		JasperPrint jasperPrintReport = JasperFillManager.fillReport(stream, parameters);
		JRPdfSaveContributor pdfSaveContrib = new JRPdfSaveContributor(null, null);
		pdfSaveContrib.save(jasperPrintReport, new File(excelDirectory + "/forside_" + ordrenummer + ".pdf"));

		PDFMergerUtility merger = new PDFMergerUtility();
		merger.addSource(new File(excelDirectory + "/forside_" + ordrenummer + ".pdf"));
		for (String fil : filer) {
			merger.addSource(fil);
		}

		String filnavn = excelDirectory + "/monteringsanvisning_" + ordrenummer + ".pdf";
		merger.setDestinationFileName(filnavn);
		merger.mergeDocuments();
		return filnavn;
	}

	public static void lagForside() throws InvalidPasswordException, IOException {
		URL resource = PdfUtil.class.getClassLoader().getResource("Forside.pdf");
		File forside = new File(PdfUtil.class.getClassLoader().getResource("forside_uten_tekst.pdf").getFile());
		PDDocument document = PDDocument.load(forside);
		PDPage page = document.getPage(0);

		PDPageContentStream pdPageContentStream = new PDPageContentStream(document, page);
		pdPageContentStream.beginText();
		pdPageContentStream.newLineAtOffset(200, 350);
		pdPageContentStream.setFont(PDType1Font.TIMES_ROMAN, 12);

		pdPageContentStream.showText("Her kommer teksat");

		// Ending the content stream
		pdPageContentStream.endText();
		pdPageContentStream.close();

		File test = new File("nyforside.pdf");
		document.save(test);
		document.close();

		// PDFStreamParser parser = new PDFStreamParser(page);
		// parser.parse();
		// List<Object> tokens = parser.getTokens();
		// for (Object obj : tokens) {
		// for (int j = 0; j < tokens.size(); j++) {
		// Object next = tokens.get(j);
		// if (next instanceof Operator) {
		// Operator op = (Operator) next;
		// // Tj and TJ are the two operators that display strings in a
		// // PDF
		// if (op.getName().equals("Tj")) {
		// // Tj takes one operator and that is the string to
		// // display so lets update that operator
		// COSString previous = (COSString) tokens.get(j - 1);
		// String string = previous.getString();
		// string = string.replaceFirst("Kari", "Ola");
		// previous.setValue(string.getBytes());
		// } else if (op.getName().equals("TJ")) {
		// COSArray previous = (COSArray) tokens.get(j - 1);
		// for (int k = 0; k < previous.size(); k++) {
		// Object arrElement = previous.getObject(k);
		// if (arrElement instanceof COSString) {
		// COSString cosString = (COSString) arrElement;
		// String string = cosString.getString();
		// string = StringUtils.replaceOnce(string, "enr: SO-99997", "tull");
		// string = StringUtils.replaceOnce(string, "Kar", "tull");
		// string = StringUtils.replaceOnce(string, "Or", "tull");
		// string = StringUtils.replaceOnce(string, "dr", "tull");
		// string = StringUtils.replaceOnce(string, "i Nor", "tull");
		// string = StringUtils.replaceOnce(string, "mann", "tull");
		// cosString.setValue(string.getBytes());
		// }
		// }
		// }
		// }
		// }
		//
		// }
		// PDStream updatedStream = new PDStream(document);
		// OutputStream out = updatedStream.createOutputStream();
		// ContentStreamWriter tokenWriter = new ContentStreamWriter(out);
		// tokenWriter.writeTokens(tokens);
		// page.setContents(updatedStream);
		// out.close();
		// File test = new File("nyforside.pdf");
		// document.save(test);
		// document.close();
	}
}
