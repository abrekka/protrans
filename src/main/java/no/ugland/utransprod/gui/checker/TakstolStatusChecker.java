package no.ugland.utransprod.gui.checker;

import java.util.List;

import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.TakstolV;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.service.TakstolVManager;
import no.ugland.utransprod.util.Util;

import org.apache.log4j.Logger;

import com.google.inject.Inject;
import com.google.inject.name.Named;

/**
 * Sjekker status for takstol
 * 
 * @author atle.brekka
 */
public class TakstolStatusChecker implements
		StatusCheckerInterface<Transportable> {
	private static final Logger LOGGER = Logger
			.getLogger(TakstolStatusChecker.class);

	private ArticleType checkType;
	private TakstolPackageVManager takstolPackageVManager;
	private TakstolProductionVManager takstolProductionVManager;

	/**
	 * @param checkArticleType
	 */
	@Inject
	public TakstolStatusChecker(
			@Named(value = "takstolArticle") final ArticleType checkArticleType,
			final TakstolPackageVManager aTakstolPackageVManager,
			final TakstolProductionVManager aTakstolProductionVManager) {
		takstolProductionVManager = aTakstolProductionVManager;
		takstolPackageVManager = aTakstolPackageVManager;
		if (checkArticleType == null) {
			LOGGER.error("artikkeltype er null");
		}
		checkType = checkArticleType;
	}

	/**
	 * @param transportable
	 * @return e dersom egenordre, X dersom pakket
	 * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleStatus(java.lang.Object)
	 */
	public final String getArticleStatus(final Transportable transportable) {
		StringBuffer returnBuffer = new StringBuffer();

		List<OrderLine> orderLines = transportable.getOrderLineList(checkType
				.getArticleTypeName());
		boolean articleFound = false;
		for (OrderLine orderLine : orderLines) {
			returnBuffer = articleFound ? returnBuffer.append("/")
					: returnBuffer;
			articleFound = orderLine != null
					&& orderLine.belongTo(transportable) ? checkFoundArticle(
					returnBuffer, orderLine) : false;
		}
		return returnBuffer.toString();
	}

	/**
	 * @see no.ugland.utransprod.gui.checker.StatusCheckerInterface#getArticleName()
	 */
	public String getArticleName() {
		return checkType.getArticleTypeName();
	}

	private boolean checkFoundArticle(StringBuffer returnBuffer,
			final OrderLine orderLine) {
		boolean articleFound = true;
		returnBuffer = addDefaultToStatus(returnBuffer, orderLine);

		returnBuffer = addOrderLineDoneToStatus(returnBuffer, orderLine);
		addNumberOfItemsToStatus(returnBuffer, orderLine);
		addProductionUnitToStatus(returnBuffer, orderLine);

		return articleFound;
	}

	private StringBuffer addDefaultToStatus(StringBuffer returnBuffer,
			final OrderLine orderLine) {
		if (orderLine.hasDefaultSet()) {
			returnBuffer = !orderLine.isDefault() ? returnBuffer.append("e")
					: returnBuffer;
		} else {
			returnBuffer = !orderLine.isDefault()
					|| !orderLine.getAttributeValue("Egenordre")
							.equalsIgnoreCase("Nei") ? returnBuffer.append("e")
					: returnBuffer;
		}
		return returnBuffer;
	}

	private void addProductionUnitToStatus(StringBuffer returnBuffer,
			final OrderLine orderLine) {
		returnBuffer = orderLine.getProductionUnit() != null ? returnBuffer
				.append(orderLine.getProductionUnit().getProductionUnitName())
				: returnBuffer;
	}

	private void addNumberOfItemsToStatus(StringBuffer returnBuffer,
			final OrderLine orderLine) {
		returnBuffer.append(Util.nullIntegerToString(orderLine
				.getNumberOfItems()));
	}

	private StringBuffer addOrderLineDoneToStatus(StringBuffer returnBuffer,
			final OrderLine orderLine) {
		returnBuffer = isOrderLineDone(orderLine) ? returnBuffer.append("X")
				: returnBuffer;
		return returnBuffer;
	}

	private boolean isOrderLineDone(final OrderLine orderLine) {
		return orderLine.isSent() ? true : isOrderLineComplete(orderLine);

	}

	private boolean isOrderLineComplete(OrderLine orderLine) {
		TakstolVManager manager = orderLine.isDefault() ? takstolPackageVManager
				: takstolProductionVManager;
		TakstolV takstol = manager.findByOrderNrAndOrderLineId(orderLine
				.getOrderNr(), orderLine.getOrderLineId());
		return takstol != null ? takstol.isRelatedArticlesComplete()
				&& orderLine.getColli() != null : false;
	}

}
