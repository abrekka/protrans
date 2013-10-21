package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.util.YearWeek;

import com.google.inject.assistedinject.Assisted;

public interface SupplierOrderViewHandlerFactory {
    SupplierOrderViewHandler create(Supplier aSupplier,YearWeek aYearWeek);
}
