package no.ugland.utransprod.gui;

import no.ugland.utransprod.gui.handlers.DeviationViewHandler2;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;

public interface DeviationOverviewViewFactory {
	DeviationOverviewView2 create(DeviationViewHandler2 deviationViewHandler,boolean useSearchButton,Order aOrder,
			boolean doSeeAll, boolean forOrderInfo,
			boolean isForRegisterNew,
			Deviation notDisplayDeviation,
			boolean isDeviationTableEditable);
}
