package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;

public interface VismaFileCreator {

    String createVismaFile(List<OrderLine> orderLines) throws ProTransException;

    boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window);

    String createVismaFileForTransport(Order order) throws ProTransException;

    void createVismaFileForAssembly(Order order, boolean assemblied);

    void createVismaFileForDelivery(Order order);

    String createVismaFileForProductionWeek(Order order);

}
