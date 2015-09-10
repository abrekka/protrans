package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.TakstolInfoV;

public interface TakstolInfoVManager {

    String MANAGER_NAME = "takstolInfoVManager";

    List<TakstolInfoV> findByOrderNr(String orderNr);

    List<Object[]> summerArbeidsinnsats(String fraAaarUke, String tilAarUke, TransportConstraintEnum transportConstraintEnum, String productArea);

    List<Object[]> getBeregnetTidForOrdre(String fraAarUke, String tilAarUke, TransportConstraintEnum transportConstraintEnum, String productArea);

}
