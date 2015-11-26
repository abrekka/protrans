package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;

public interface VismaFileCreator {

    String createVismaFile(List<OrderLine> orderLines, int teller) throws ProTransException;

    boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window);

    String createVismaFileForTransport(Order order) throws ProTransException;

    void createVismaFileForAssembly(Order order, boolean assemblied, boolean forVisma, int teller);

    void createVismaFileForDelivery(Order order, boolean forVisma, int teller);

    String createVismaFileForProductionWeek(Order order);

}
