package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;

import no.ugland.utransprod.dao.CraningCostDAO;
import no.ugland.utransprod.model.CraningCost;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.util.rules.Craningbasis;

public class CraningCostManagerImpl implements CraningCostManager {
    private CraningCostDAO dao;

    /**
     * Setter dao-klasse.
     * @param aDao
     */
    public void setCraningCostDAO(final CraningCostDAO aDao) {
        this.dao = aDao;
    }

    public BigDecimal findCostByCraningEnum(final Craningbasis craningEnum) {
        CraningCost craningCost = dao.findByRuleId(craningEnum.getRuleId());
        if (craningCost != null) {
            return craningCost.getCostValue();
        }
        return null;
    }

}
