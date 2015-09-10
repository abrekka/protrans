package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.TakstolInfoV;

public interface TakstolInfoVDAO extends DAO<TakstolInfoV> {

    List<TakstolInfoV> findByOrderNr(String orderNr);

    List<Object[]> summerArbeidsinnsats(String fraAarUke, String tilAarUke, TransportConstraintEnum transportConstraintEnum, String productArea);

    List<Object[]> getBeregnetTidForOrdre(String fraAarUke, String tilAarUke, TransportConstraintEnum transportConstraintEnum, String productArea);

}
