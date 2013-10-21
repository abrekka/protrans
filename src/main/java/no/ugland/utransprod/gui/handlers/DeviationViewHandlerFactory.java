package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.Order;

import com.google.inject.assistedinject.Assisted;

public interface DeviationViewHandlerFactory {
	DeviationViewHandler create(Order aOrder, 
			boolean doSeAll, boolean forOrderInfo,
			boolean isForRegisterNew,
			Deviation notDisplayDeviation,
			boolean isDeviationTableEditable);
}
