package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.OrdchgrHeadVDAO;
import no.ugland.utransprod.dao.OrdchgrLineVDAO;
import no.ugland.utransprod.model.OrdchgrHeadV;
import no.ugland.utransprod.model.OrdchgrLineVPK;
import no.ugland.utransprod.model.OrdchgrLineV;
import no.ugland.utransprod.service.OrdchgrHeadVManager;

public class OrdchgrHeadVManagerImpl implements OrdchgrHeadVManager {
    private OrdchgrHeadVDAO ordchgrHeadVDAO;
    private OrdchgrLineVDAO ordchgrLineVDAO;

    public void setOrdchgrHeadVDAO(OrdchgrHeadVDAO aOrdchgrHeadVDAO) {
        ordchgrHeadVDAO = aOrdchgrHeadVDAO;
    }

    public void setOrdchgrLineVDAO(OrdchgrLineVDAO aOrdchgrLineVDAO) {
        ordchgrLineVDAO = aOrdchgrLineVDAO;
    }

    public OrdchgrHeadV getHead(Integer ordNo) {
        return ordNo != null ? ordchgrHeadVDAO.getObject(ordNo) : null;
    }

    public OrdchgrLineV getLine(Integer ordNo, Integer lnNo) {
        return ordchgrLineVDAO.getObject(new OrdchgrLineVPK(ordNo, lnNo));
    }

    public List<OrdchgrLineV> getLines(Integer ordNo, List<Integer> lnNos) {
        return ordchgrLineVDAO.getLines(ordNo, lnNos);
    }

}
