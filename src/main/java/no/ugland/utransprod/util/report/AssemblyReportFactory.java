package no.ugland.utransprod.util.report;

import java.util.List;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Ordln;

public interface AssemblyReportFactory {
    AssemblyReport create(final Order order,
            final List<Ordln> vismaOrderLines);
}
