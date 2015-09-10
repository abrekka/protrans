package no.ugland.utransprod.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.VismaFile;
import no.ugland.utransprod.service.FakturagrunnlagVManager;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.io.FileUtils;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimaps;
import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

public class VismaFileCreatorImpl implements VismaFileCreator {
    private static final String VISMA_OUT_DIR = "visma_out_dir";
    private static Boolean uniqueFileName = true;

    private OrdchgrHeadVManager ordchgrManager;
    private FakturagrunnlagVManager fakturagrunnlagVManager;

    @Inject
    public VismaFileCreatorImpl(final OrdchgrHeadVManager aOrdchgrManager, @Named(value = "useUniqueFileName") final Boolean useUniqueFileName,
	    FakturagrunnlagVManager fakturagrunnlagVManager) {
	ordchgrManager = aOrdchgrManager;
	uniqueFileName = useUniqueFileName;
	this.fakturagrunnlagVManager = fakturagrunnlagVManager;
    }

    public String createVismaFile(List<OrderLine> orderLines) throws ProTransException {
	return ordchgrManager != null ? createHeadAndLines(orderLines) : null;
    }

    public String createVismaFileForTransport(Order order) throws ProTransException {
	try {

	    Integer ordNo = hentOrdNo(order);
	    OrdchgrHeadV head = ordchgrManager.getHead(ordNo);
	    return createTransportFile(order, head, ApplicationParamUtil.findParamByName(VISMA_OUT_DIR));
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new ProTransException(e.getMessage());
	}

    }

    private Integer hentOrdNo(Order order) {

	OrderLine orderLineMedOrdNo = Iterables.find(order.getOrderLines(), medOrdNo());
	return orderLineMedOrdNo == null ? null : orderLineMedOrdNo.getOrdNo();
    }

    private Predicate<OrderLine> medOrdNo() {
	return new Predicate<OrderLine>() {

	    public boolean apply(OrderLine orderLine) {
		return orderLine.getOrdNo() != null;
	    }
	};
    }

    private String createHeadAndLines(List<OrderLine> orderLines) throws ProTransException {
	if (orderLines != null && orderLines.size() != 0) {
	    String transportDate = getTransportDate(orderLines.get(0).getOrder().getTransport());
	    OrdchgrHeadV head = ordchgrManager.getHead(orderLines.get(0).getOrdNo());
	    List<OrdchgrLineV> lines = head != null ? ordchgrManager.getLines(orderLines.get(0).getOrdNo(), getLnNos(orderLines)) : null;
	    return head != null ? createFile(head, lines, orderLines.get(0).getOrderNr(), ApplicationParamUtil.findParamByName(VISMA_OUT_DIR),
		    transportDate) : null;
	}
	return null;
    }

    private List<Integer> getLnNos(List<OrderLine> orderLines) {
	List<Integer> list = new ArrayList<Integer>();
	for (OrderLine orderLine : orderLines) {
	    boolean added = orderLine.getLnNo() != null ? list.add(orderLine.getLnNo()) : false;
	}
	return list;
    }

    public String createFile(OrdchgrHeadV head, List<OrdchgrLineV> fileLines, final String orderNr, String outdir, String transportDate)
	    throws ProTransException {
	try {
	    List<String> lines = new ArrayList<String>();
	    lines.add(head.getHeadLine(transportDate));
	    for (OrdchgrLineV ordchgrLineV : fileLines) {
		lines.add(ordchgrLineV.getLineLine());
	    }
	    return writeFile(orderNr, outdir, lines);
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new ProTransException(e.getMessage());
	}
    }

    private String writeFile(final String orderNr, String outdir, List<String> lines) throws IOException {
	try {
	    if (lines == null || lines.isEmpty()) {
		return null;
	    }
	    String fileName = orderNr + "_";
	    fileName += uniqueFileName ? Util.getCurrentDateAsDateTimeStringWithSeconds() : "";
	    fileName += ".edi";
	    File file = new File(outdir + "/" + fileName);
	    FileUtils.writeLines(file, lines);
	    return fileName;
	} catch (Exception e) {
	    e.printStackTrace();
	}
	return null;
    }

    public boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window) {
	boolean ignoreFile = true;
	if (orderLine.getOrdNo() != null
		&& !Util.showConfirmDialog(window, "Vismafil", "Det er generert fil til visma, vil du allikevel fjerne pakking?")) {
	    ignoreFile = false;
	}
	return ignoreFile;
    }

    public String createTransportFile(Order order, OrdchgrHeadV head, String outdir) throws IOException {
	if (order == null || head == null) {
	    return null;
	}
	String transportDate = getTransportDate(order.getTransport());

	return writeFile(order.getOrderNr(), outdir, Lists.newArrayList(head.getHeadLine(transportDate)));
    }

    public String createProductionWeekFile(Order order, OrdchgrHeadV head, String outdir) throws IOException {
	if (order == null || head == null) {
	    return null;
	}

	return writeFile(order.getOrderNr(), outdir, Lists.newArrayList(head.getHeadLineForProductionWeek(order.getProductionWeek())));
    }

    private String getTransportDate(Transport transport) {
	String transportDate = null;
	if (transport != null) {
	    transportDate = transport.getLoadingDate() != null ? Util.DATE_FORMAT_YYYYMMDD.format(transport.getLoadingDate()) : Util
		    .getLastFridayAsString(transport.getTransportYear(), transport.getTransportWeek());
	}
	return transportDate;
    }

    public List<VismaFile> createAssemblyFiles(List<FakturagrunnlagV> fakturagrunnlag, boolean assemblied, Assembly assembly) {

	Iterable<FakturagrunnlagV> monteringsbestillinger = Iterables.filter(fakturagrunnlag, linjerForMonteringMedBestilling());
	ImmutableListMultimap<Integer, FakturagrunnlagV> purcnoMap = Multimaps.index(monteringsbestillinger, purcno());
	List<VismaFile> vismafiler = Lists.newArrayList();
	for (Integer purcno : purcnoMap.keySet()) {
	    ImmutableList<FakturagrunnlagV> grunnlagliste = purcnoMap.get(purcno);
	    List<String> linjer = Lists.newArrayList();

	    String assemblyteamNr = assembly == null ? "" : assembly.getAssemblyTeamNr() == null ? "" : assembly.getAssemblyTeamNr();
	    linjer.add(String.format(OrdchgrHeadV.HEAD_LINE_WITH_SUPPLIER_TMP, purcno, assemblyteamNr, ""));

	    for (FakturagrunnlagV grunnlag : grunnlagliste) {
		linjer.add(OrdchgrLineV.getLinje(grunnlag.getLnPurcno(), assemblied ? grunnlag.getAlloc() : grunnlag.getAlloc() * -1));
	    }
	    vismafiler.add(new VismaFile(linjer));
	}
	return vismafiler;
    }

    private Function<FakturagrunnlagV, Integer> purcno() {
	return new Function<FakturagrunnlagV, Integer>() {

	    public Integer apply(FakturagrunnlagV grunnlag) {
		return grunnlag.getPurcno();
	    }
	};
    }

    private Predicate<FakturagrunnlagV> linjerForMonteringMedBestilling() {
	return new Predicate<FakturagrunnlagV>() {

	    public boolean apply(FakturagrunnlagV grunnlag) {
		return grunnlag.harBestillingsnr()
			&& (grunnlag.erMontering() || grunnlag.erKranbil() || grunnlag.erAvfallsfjerning() || grunnlag.erMonteringsspiker());
	    }
	};
    }

    public List<String> createVismaAssemblyFiles(Order order, List<FakturagrunnlagV> fakturagrunnlag, String outdir, boolean assemblied) {
	List<String> filenames = Lists.newArrayList();
	try {
	    List<VismaFile> assemblyFiles = createAssemblyFiles(fakturagrunnlag, assemblied, order.getAssembly());
	    for (VismaFile vismaFile : assemblyFiles) {
		filenames.add(writeFile(order.getOrderNr(), outdir, vismaFile.getLinjer()));
	    }
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new ProTransException(e.getMessage());
	}

	return filenames;
    }

    public void createVismaFileForAssembly(Order order, boolean assemblied) {
	createVismaAssemblyFiles(order, fakturagrunnlagVManager.findFakturagrunnlag(order.getOrderId()),
		ApplicationParamUtil.findParamByName(VISMA_OUT_DIR), assemblied);

    }

    public VismaFile createDeliveryFile(Transport transport, List<FakturagrunnlagV> fakturagrunnlag, boolean sendt) {
	List<FakturagrunnlagV> fraktbestillinger = Lists.newArrayList(Iterables.filter(fakturagrunnlag, linjerForFraktMedBestilling()));

	if (fraktbestillinger.size() > 1) {
	    throw new ProTransException("Fakturagrunnlag har mer enn 1 fraktbestilling!");
	}

	if (!fraktbestillinger.isEmpty()) {
	    FakturagrunnlagV fakturagrunnlagV = fraktbestillinger.get(0);
	    List<String> linjer = Lists.newArrayList();
	    String levNr = transport.getSupplierNr() == null ? "" : transport.getSupplierNr();
	    String transportDate = getTransportDate(transport);
	    transportDate = transportDate == null ? "0" : transportDate;
	    String transportnavn = transport.getTransportName() == null ? "" : transport.getTransportName();
	    String driver = transport.getDriver() == null ? "" : transport.getDriver();
	    linjer.add(String.format(OrdchgrHeadV.HEAD_LINE_DELIVERY_TMP, fakturagrunnlagV.getPurcno(), levNr, transportDate, transportnavn, driver));
	    linjer.add(OrdchgrLineV.getLinje(fakturagrunnlagV.getLnPurcno(), sendt ? fakturagrunnlagV.getAlloc() : fakturagrunnlagV.getAlloc() * -1));
	    return new VismaFile(linjer);
	}
	return new VismaFile();
    }

    private Predicate<FakturagrunnlagV> linjerForFraktMedBestilling() {
	return new Predicate<FakturagrunnlagV>() {

	    public boolean apply(FakturagrunnlagV grunnlag) {
		return grunnlag.harBestillingsnr() && (grunnlag.erFrakt());
	    }
	};
    }

    public void createVismaFileForDelivery(Order order) {
	try {
	    VismaFile vismaFile = createDeliveryFile(order.getTransport(), fakturagrunnlagVManager.findFakturagrunnlag(order.getOrderId()),
		    order.getSentBool());
	    writeFile(order.getOrderNr(), ApplicationParamUtil.findParamByName(VISMA_OUT_DIR), vismaFile.getLinjer());
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new ProTransException(e.getMessage());
	}

    }

    public String createVismaFileForProductionWeek(Order order) {
	try {

	    Integer ordNo = hentOrdNo(order);
	    OrdchgrHeadV head = ordchgrManager.getHead(ordNo);
	    return createProductionWeekFile(order, head, ApplicationParamUtil.findParamByName(VISMA_OUT_DIR));
	} catch (IOException e) {
	    e.printStackTrace();
	    throw new ProTransException(e.getMessage());
	}

    }
}
