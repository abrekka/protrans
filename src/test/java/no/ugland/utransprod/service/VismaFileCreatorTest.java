package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.OrdchgrLineVPK;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.Util;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class VismaFileCreatorTest {

    @Test
    public void skalLageVismaFilForOrdreUtenTransport() throws IOException, ParseException {
	VismaFileCreatorImpl vismaFileCreator = new VismaFileCreatorImpl(null, false);
	Order ordre = new Order();
	OrdchgrHeadV head = new OrdchgrHeadV();
	head.setOrdNo(1);
	String fileName = vismaFileCreator.createTransportFile(ordre, head, "visma");
	assertNotNull(fileName);
	File file = new File("visma/" + fileName);
	assertEquals(true, file.exists());
	List<String> stringLines = FileUtils.readLines(file);
	assertEquals(1, stringLines.size());
	assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4", stringLines.get(0));
    }

    @Test
    public void skalLageVismaFilForOrdreUtenLeveringsdatoOgUtenTransportAarOGUke() throws IOException, ParseException {
	VismaFileCreatorImpl vismaFileCreator = new VismaFileCreatorImpl(null, false);
	Order ordre = new Order();
	Transport transport = new Transport();
	ordre.setTransport(transport);
	OrdchgrHeadV head = new OrdchgrHeadV();
	head.setOrdNo(1);
	String fileName = vismaFileCreator.createTransportFile(ordre, head, "visma");
	assertNotNull(fileName);
	File file = new File("visma/" + fileName);
	assertEquals(true, file.exists());
	List<String> stringLines = FileUtils.readLines(file);
	assertEquals(1, stringLines.size());
	assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4", stringLines.get(0));
    }

    @Test
    public void skalLageVismaFilForLeveringsdato() throws IOException, ParseException {
	VismaFileCreatorImpl vismaFileCreator = new VismaFileCreatorImpl(null, false);
	Order ordre = new Order();
	Transport transport = new Transport();
	transport.setLoadingDate(Util.DATE_FORMAT_YYYYMMDD.parse("20110929"));
	ordre.setTransport(transport);
	OrdchgrHeadV head = new OrdchgrHeadV();
	head.setOrdNo(1);
	String fileName = vismaFileCreator.createTransportFile(ordre, head, "visma");
	assertNotNull(fileName);
	File file = new File("visma/" + fileName);
	assertEquals(true, file.exists());
	List<String> stringLines = FileUtils.readLines(file);
	assertEquals(1, stringLines.size());
	assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;20110929;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4", stringLines.get(0));
    }

    @Test
    public void testWriteFile() throws Exception {
	final OrdchgrHeadV ordchgrHead = new OrdchgrHeadV();
	ordchgrHead.setOrdNo(1);
	final OrdchgrLineV ordchgrLine = new OrdchgrLineV();
	ordchgrLine.setOrdchgrLineVPK(new OrdchgrLineVPK(1, 10));
	ordchgrLine.setLineStatus(20);
	final List<OrdchgrLineV> lines = new ArrayList<OrdchgrLineV>();
	lines.add(ordchgrLine);

	VismaFileCreatorImpl vismaFileCreator = new VismaFileCreatorImpl(null, false);
	String fileName = vismaFileCreator.createFile(ordchgrHead, lines, "1", "visma", null);

	File file = new File("visma/" + fileName);
	assertEquals(true, file.exists());
	List<String> stringLines = FileUtils.readLines(file);
	for (String line : stringLines) {
	    Line.valueOf(line.substring(0, 1)).validate(line);
	}
    }

    private enum Line {
	H {
	    @Override
	    public void validate(String line) {
		assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4", line);

	    }
	},
	L {
	    @Override
	    public void validate(String line) {
		assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;20;3", line);

	    }
	};
	public abstract void validate(String line);
    }
}
