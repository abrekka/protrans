package no.ugland.utransprod.service;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.handlers.TransportViewHandler;

public class Dokumentlager {
	private static final Logger LOGGER = Logger.getLogger(Dokumentlager.class);

	public static void aapneDokument(String orderId, String documentType) {
		FileService filService;
		try {
			filService = new FileService(new URL("http://iglapp01/KeyforceFileService/FileService.svc?wsdl"));

			IFileService iFileService = filService.getBasicHttpBindingIFileService();
			FileResponse response = iFileService.getDocumentDirect(orderId, documentType, true);
			LOGGER.info("Feilmelding: " + response.getErrorMessage().getValue());
			LOGGER.info("Hentet dokument med navn: " + response.getFilename().getValue());
			LOGGER.info("Hentet dokument med lengde: " + response.getLength());

			String opprinneligFilnavn = response.getFilename().getValue();
			String suffix=opprinneligFilnavn.substring(opprinneligFilnavn.lastIndexOf("."));
			String filnavn = opprinneligFilnavn.substring(0, opprinneligFilnavn.lastIndexOf("."));

			Path filpath = Files.createTempFile(filnavn, suffix);
			LOGGER.info("Oppretet filpath: " + filpath.getFileName());
			File pdf = filpath.toFile();
			LOGGER.info("Oppretet fil: " + pdf.getAbsolutePath());
//			LOGGER.info("FileContents: " + response.getFileContents().getValue() == null ? "er tom" : "er ikke tom");
//			LOGGER.info("Innhold: "+new String(response.getFileContents().getValue()));
			FileUtils.writeByteArrayToFile(pdf, response.getFileContents().getValue());
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(pdf);
			}
		} catch (MalformedURLException e) {
			throw new ProTransException("Feiler ved henting av dokument", e);
		} catch (IOException e) {
			throw new ProTransException("Feiler ved henting av dokument", e);
		}
	}
}
