package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Ordln;

public interface AssemblyReport {

    public abstract BigDecimal getCraningAddition();

    public abstract BigDecimal getDrivingAddition();

    public abstract Order getOrder();

    public abstract void setOrder(final Order aOrder);

    public abstract List<Ordln> getVismaOrderLines();

    public abstract void setVismaOrderLines(final List<Ordln> someVismaOrderLines);

    public abstract void setCraningAddition(final BigDecimal aCraningAddition);

}