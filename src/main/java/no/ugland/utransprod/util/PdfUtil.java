package no.ugland.utransprod.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.multipdf.PDFMergerUtility;

public class PdfUtil {

	public void slaaSammenFiler(List<File> filer) throws IOException {
		PDFMergerUtility merger = new PDFMergerUtility();

		for (File fil : filer) {
			merger.addSource(fil);
		}
		merger.setDestinationFileName("test.pdf");
		merger.mergeDocuments();
	}

}
