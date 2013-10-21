package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;

import com.google.inject.assistedinject.Assisted;

public interface DeviationOverviewViewFactory {
	DeviationOverviewView create(DeviationViewHandler deviationViewHandler,boolean useSearchButton,Order aOrder,
			boolean doSeeAll, boolean forOrderInfo,
			boolean isForRegisterNew,
			Deviation notDisplayDeviation,
			boolean isDeviationTableEditable);
}
