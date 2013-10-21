package no.ugland.utransprod.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.OrdchgrHeadVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.io.FileUtils;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.google.inject.name.Named;

public class VismaFileCreatorImpl implements VismaFileCreator {
	private static final String VISMA_OUT_DIR = "visma_out_dir";
	private static Boolean uniqueFileName = true;

	private OrdchgrHeadVManager ordchgrManager;

	@Inject
	public VismaFileCreatorImpl(final OrdchgrHeadVManager aOrdchgrManager,
			@Named(value = "useUniqueFileName") final Boolean useUniqueFileName) {
		ordchgrManager = aOrdchgrManager;
		uniqueFileName = useUniqueFileName;
	}

	public String createVismaFile(List<OrderLine> orderLines)
			throws ProTransException {
		return ordchgrManager != null ? createHeadAndLines(orderLines) : null;
	}

	public String createVismaFileForTransport(Order order)
			throws ProTransException {
		try {

			Integer ordNo = order.getOrderLines().iterator().next().getOrdNo();
			OrdchgrHeadV head = ordchgrManager.getHead(ordNo);
			return createTransportFile(order, head, ApplicationParamUtil
					.findParamByName(VISMA_OUT_DIR));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProTransException(e.getMessage());
		}

	}

	private String createHeadAndLines(List<OrderLine> orderLines)
			throws ProTransException {
		if (orderLines != null && orderLines.size() != 0) {
			OrdchgrHeadV head = ordchgrManager.getHead(orderLines.get(0)
					.getOrdNo());
			List<OrdchgrLineV> lines = head != null ? ordchgrManager.getLines(
					orderLines.get(0).getOrdNo(), getLnNos(orderLines)) : null;
			return head != null ? createFile(head, lines, orderLines.get(0)
					.getOrderNr(), ApplicationParamUtil
					.findParamByName(VISMA_OUT_DIR)) : null;
		}
		return null;
	}

	private List<Integer> getLnNos(List<OrderLine> orderLines) {
		List<Integer> list = new ArrayList<Integer>();
		for (OrderLine orderLine : orderLines) {
			boolean added = orderLine.getLnNo() != null ? list.add(orderLine
					.getLnNo()) : false;
		}
		return list;
	}

	public String createFile(OrdchgrHeadV head, List<OrdchgrLineV> fileLines,
			final String orderNr, String outdir) throws ProTransException {
		try {
			List<String> lines = new ArrayList<String>();
			lines.add(head.getHeadLine(null));
			for (OrdchgrLineV ordchgrLineV : fileLines) {
				lines.add(ordchgrLineV.getLineLine());
			}
			return writeFile(orderNr, outdir, lines);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProTransException(e.getMessage());
		}
	}

	private String writeFile(final String orderNr, String outdir,
			List<String> lines) throws IOException {
		String fileName = orderNr + "_";
		fileName += uniqueFileName ? Util
				.getCurrentDateAsDateTimeStringWithSeconds() : "";
		fileName += ".edi";
		File file = new File(outdir + "/" + fileName);
		FileUtils.writeLines(file, lines);
		return fileName;
	}

	public boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window) {
		boolean ignoreFile = true;
		if (orderLine.getOrdNo() != null
				&& !Util
						.showConfirmDialog(window, "Vismafil",
								"Det er generert fil til visma, vil du allikevel fjerne pakking?")) {
			ignoreFile = false;
		}
		return ignoreFile;
	}

	public String createTransportFile(Order order, OrdchgrHeadV head,
			String outdir) throws IOException {
		if (order == null || head == null) {
			return null;
		}
		String transportDate = null;
		if (order.getTransport() != null) {
			Transport transport = order.getTransport();
			transportDate = transport.getLoadingDate() != null ? Util.DATE_FORMAT_YYYYMMDD
					.format(transport.getLoadingDate())
					: Util.getLastFridayAsString(transport.getTransportYear(),
							transport.getTransportWeek());
		}

		return writeFile(order.getOrderNr(), outdir, Lists.newArrayList(head
				.getHeadLine(transportDate)));
	}

}
