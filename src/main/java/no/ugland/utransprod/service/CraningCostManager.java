package no.ugland.utransprod.service;

import java.math.BigDecimal;

import no.ugland.utransprod.util.rules.Craningbasis;

public interface CraningCostManager {
    String MANAGER_NAME = "craningCostManager";

    BigDecimal findCostByCraningEnum(Craningbasis craningEnum);
}
