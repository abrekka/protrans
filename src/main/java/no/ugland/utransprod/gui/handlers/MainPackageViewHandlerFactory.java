package no.ugland.utransprod.gui.handlers;

import java.util.Map;

import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.model.OrderLine;

import com.google.common.collect.Multimap;

public interface MainPackageViewHandlerFactory {
    MainPackageViewHandler create(Multimap<String, String> aColliSetup, Map<String, StatusCheckerInterface<OrderLine>> chekers);
}
