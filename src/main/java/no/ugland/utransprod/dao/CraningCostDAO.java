package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.CraningCost;

public interface CraningCostDAO extends DAO<CraningCost>{
    CraningCost findByRuleId(String ruleId);
}
