package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Employee;
import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.OrdchgrLineVPK;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.VismaFile;
import no.ugland.utransprod.service.impl.VismaFileCreatorImpl;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.Util;

import org.apache.commons.io.FileUtils;
import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.inject.internal.Lists;

@Category(FastTests.class)
public class VismaFileCreatorTest {
	private VismaFileCreator vismaFileCreator = new VismaFileCreatorImpl(null, false, null);

	@Test
	public void skalLageFerdigmelding() {
		List<String> linjer = VismaFileCreatorImpl.lagFillinjer(false, 55919, "produsert av", "Front",
				BigDecimal.valueOf(10.5));
		Assertions.assertThat(linjer.size()).isEqualTo(1);
		Assertions.assertThat(linjer.get(0)).isEqualTo(
				"H;;55919;;;;;produsert av;;;;;;;;;;;;;;;;;;;;;;;Front;;;;;;;;;;10.5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4");
	}

	@Test
	public void skalLageVismaFilForProduksjonsuke() throws IOException, ParseException {
		Order ordre = new Order();
		ordre.setProductionWeek(6);
		OrdchgrHeadV head = new OrdchgrHeadV();
		head.setOrdNo(1000);
		String fileName = ((VismaFileCreatorImpl) vismaFileCreator).createProductionWeekFile(ordre, head, "visma");
		assertNotNull(fileName);
		File file = new File("visma/" + fileName);
		assertEquals(true, file.exists());
		List<String> stringLines = FileUtils.readLines(file);
		assertEquals(1, stringLines.size());
		assertEquals("H;;1000;;;;;;;;;;;;;;;;;;;;;;;;;;;;06;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
	}

	@Test
	public void skalLageVismafilForTilbakemeldingForTransportUtenPurcno() throws Exception {
		List<FakturagrunnlagV> fakturagrunnlag = Lists
				.newArrayList(new FakturagrunnlagV().medProdno("FRAKT").medPurcno(0).medLnPurcno(0).medAlloc(0));
		Transport transport = new Transport();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300017");
		transport.setSupplier(supplier);
		transport.setLoadingDate(new SimpleDateFormat("yyyyMMdd").parse("20140516"));
		Employee employee = new Employee();
		employee.setFirstName("test");
		employee.setLastName("testesen");
		transport.setEmployee(employee);
		transport.setTransportName("turnavn");
		VismaFile vismafil = ((VismaFileCreatorImpl) vismaFileCreator).createDeliveryFile(transport, fakturagrunnlag,
				true, false);
		List<String> stringLines = vismafil.getLinjer();
		assertEquals(0, stringLines.size());
	}

	@Test
	public void skalLageVismafilForTilbakemeldingForTransport() throws Exception {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1),
				new FakturagrunnlagV().medProdno("FRAKT").medPurcno(2617).medLnPurcno(5).medAlloc(1));
		Transport transport = new Transport();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300017");
		transport.setSupplier(supplier);
		transport.setLoadingDate(new SimpleDateFormat("yyyyMMdd").parse("20140516"));
		Employee employee = new Employee();
		employee.setFirstName("test");
		employee.setLastName("testesen");
		transport.setEmployee(employee);
		transport.setTransportName("turnavn");
		VismaFile vismafil = ((VismaFileCreatorImpl) vismaFileCreator).createDeliveryFile(transport, fakturagrunnlag,
				true, false);
		List<String> stringLines = vismafil.getLinjer();
		assertEquals(2, stringLines.size());
		assertEquals(
				"H;;2617;;;300017;;;;;;;;;;;;;;;;;;;20140516;;;;;turnavn;test testesen;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;5;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
	}

	@Test
	public void skalLageVismafilForTilbakemeldingForTransportMedVismaOrdrenummer() throws Exception {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1)
						.medOrdNo(1678).medLnNo(10),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1)
						.medOrdNo(1678).medLnNo(11),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1).medOrdNo(1678)
						.medLnNo(12),
				new FakturagrunnlagV().medProdno("FRAKT").medPurcno(2617).medLnPurcno(5).medAlloc(1).medOrdNo(1678)
						.medLnNo(9990));
		Transport transport = new Transport();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300017");
		transport.setSupplier(supplier);
		transport.setLoadingDate(new SimpleDateFormat("yyyyMMdd").parse("20140516"));
		Employee employee = new Employee();
		employee.setFirstName("test");
		employee.setLastName("testesen");
		transport.setEmployee(employee);
		transport.setTransportName("turnavn");
		VismaFile vismafil = ((VismaFileCreatorImpl) vismaFileCreator).createDeliveryFile(transport, fakturagrunnlag,
				true, true);
		List<String> stringLines = vismafil.getLinjer();
		assertEquals(2, stringLines.size());
		assertEquals("H;;1678;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;9990;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
	}

	@Test
	public void skalLageGrunnlagForVismaFilForMonteringHvorMonteringErFjernet() {

		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1));
		List<VismaFile> vismafiler = ((VismaFileCreatorImpl) vismaFileCreator).createAssemblyFiles(fakturagrunnlag,
				false, null, false);
		Assertions.assertThat(vismafiler).hasSize(1);
		List<String> stringLines = vismafiler.get(0).getLinjer();
		assertEquals(4, stringLines.size());
		assertEquals("H;;2616;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;-1;3",
				stringLines.get(1));
		assertEquals("L;11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;-1;3",
				stringLines.get(2));
		assertEquals("L;12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;-1;3",
				stringLines.get(3));
	}

	@Test
	public void skalLageVismaFilForMontering() throws Exception {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1));
		Order order = new Order();
		Assembly assembly = new Assembly();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300018");
		assembly.setSupplier(supplier);
		order.setAssembly(assembly);
		List<String> fileNames = ((VismaFileCreatorImpl) vismaFileCreator).createVismaAssemblyFiles(order,
				fakturagrunnlag, "visma", true, false, 1);
		Assertions.assertThat(fileNames).hasSize(1);
		File file = new File("visma/" + fileNames.get(0));
		assertEquals(true, file.exists());
		List<String> stringLines = FileUtils.readLines(file);
		assertEquals(4, stringLines.size());
		assertEquals("H;;2616;;;300018;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
		assertEquals("L;11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(2));
		assertEquals("L;12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(3));
	}

	@Test
	public void skalLage2GrunnlagForVismaFilerForMonteringDersomUliktPurcno() {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2617).medLnPurcno(12).medAlloc(1));
		List<VismaFile> vismafiler = ((VismaFileCreatorImpl) vismaFileCreator).createAssemblyFiles(fakturagrunnlag,
				true, null, false);
		Assertions.assertThat(vismafiler).hasSize(2);
		List<String> stringLines = vismafiler.get(0).getLinjer();
		assertEquals(3, stringLines.size());
		assertEquals("H;;2616;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
		assertEquals("L;11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(2));

		stringLines = vismafiler.get(1).getLinjer();
		assertEquals(2, stringLines.size());
		assertEquals("H;;2617;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
	}

	@Test
	public void skalLageGrunnlagForVismaFilForMontering() {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1));
		Assembly assembly = new Assembly();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300018");
		assembly.setSupplier(supplier);
		List<VismaFile> vismafiler = ((VismaFileCreatorImpl) vismaFileCreator).createAssemblyFiles(fakturagrunnlag,
				true, assembly, false);
		Assertions.assertThat(vismafiler).hasSize(1);
		List<String> stringLines = vismafiler.get(0).getLinjer();
		assertEquals(4, stringLines.size());
		assertEquals("H;;2616;;;300018;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
		assertEquals("L;11;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(2));
		assertEquals("L;12;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(3));
	}

	@Test
	public void skalLageGrunnlagForVismaFilForMonteringForVisma() {
		List<FakturagrunnlagV> fakturagrunnlag = Lists.newArrayList(
				new FakturagrunnlagV().medProdno("Montering villa").medPurcno(2616).medLnPurcno(10).medAlloc(1)
						.medOrdNo(1234).medLnNo(111),
				new FakturagrunnlagV().medProdno("MONTSPIKER").medPurcno(2616).medLnPurcno(11).medAlloc(1)
						.medOrdNo(1234).medLnNo(112),
				new FakturagrunnlagV().medProdno("KRANBIL").medPurcno(2616).medLnPurcno(12).medAlloc(1).medOrdNo(1234)
						.medLnNo(113));
		Assembly assembly = new Assembly();
		Supplier supplier = new Supplier();
		supplier.setSupplierNr("300018");
		assembly.setSupplier(supplier);
		List<VismaFile> vismafiler = ((VismaFileCreatorImpl) vismaFileCreator).createAssemblyFiles(fakturagrunnlag,
				true, assembly, true);
		Assertions.assertThat(vismafiler).hasSize(1);
		List<String> stringLines = vismafiler.get(0).getLinjer();
		assertEquals(4, stringLines.size());
		assertEquals("H;;1234;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
		assertEquals("L;111;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(1));
		assertEquals("L;112;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(2));
		assertEquals("L;113;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;1;3",
				stringLines.get(3));
	}

	@Test
	public void skalLageVismaFilForOrdreUtenTransport() throws IOException, ParseException {
		Order ordre = new Order();
		OrdchgrHeadV head = new OrdchgrHeadV();
		head.setOrdNo(1);
		String fileName = ((VismaFileCreatorImpl) vismaFileCreator).createTransportFile(ordre, head, "visma");
		assertNotNull(fileName);
		File file = new File("visma/" + fileName);
		assertEquals(true, file.exists());
		List<String> stringLines = FileUtils.readLines(file);
		assertEquals(1, stringLines.size());
		assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
	}

	@Test
	public void skalLageVismaFilForOrdreUtenLeveringsdatoOgUtenTransportAarOGUke() throws IOException, ParseException {
		Order ordre = new Order();
		Transport transport = new Transport();
		ordre.setTransport(transport);
		OrdchgrHeadV head = new OrdchgrHeadV();
		head.setOrdNo(1);
		String fileName = ((VismaFileCreatorImpl) vismaFileCreator).createTransportFile(ordre, head, "visma");
		assertNotNull(fileName);
		File file = new File("visma/" + fileName);
		assertEquals(true, file.exists());
		List<String> stringLines = FileUtils.readLines(file);
		assertEquals(1, stringLines.size());
		assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
	}

	@Test
	public void skalLageVismaFilForLeveringsdato() throws IOException, ParseException {
		Order ordre = new Order();
		Transport transport = new Transport();
		transport.setLoadingDate(Util.DATE_FORMAT_YYYYMMDD.parse("20110929"));
		ordre.setTransport(transport);
		OrdchgrHeadV head = new OrdchgrHeadV();
		head.setOrdNo(1);
		String fileName = ((VismaFileCreatorImpl) vismaFileCreator).createTransportFile(ordre, head, "visma");
		assertNotNull(fileName);
		File file = new File("visma/" + fileName);
		assertEquals(true, file.exists());
		List<String> stringLines = FileUtils.readLines(file);
		assertEquals(1, stringLines.size());
		assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;20110929;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
				stringLines.get(0));
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

		String fileName = ((VismaFileCreatorImpl) vismaFileCreator).createFile(ordchgrHead, lines, "1", "visma", null,
				1, false);

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
				assertEquals("H;;1;;;;;;;;;;;;;;;;;;;;;;0;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4",
						line);

			}
		},
		L {
			@Override
			public void validate(String line) {
				assertEquals("L;10;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;1;1;20;3",
						line);

			}
		};
		public abstract void validate(String line);
	}
}
