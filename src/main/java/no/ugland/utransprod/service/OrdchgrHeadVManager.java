package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineV;

public interface OrdchgrHeadVManager {

    String MANAGER_NAME = "ordchgrHeadVManager";

    OrdchgrHeadV getHead(Integer ordNo);

    OrdchgrLineV getLine(Integer ordNo, Integer lnNo);

    List<OrdchgrLineV> getLines(Integer ordNo, List<Integer> lnNos);

}
