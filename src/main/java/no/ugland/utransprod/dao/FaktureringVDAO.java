package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Interface for DAO mot view FAKTURERING_V
 * 
 * @author atle.brekka
 * 
 */
public interface FaktureringVDAO extends DAO<FaktureringV> {
    /**
     * Finner alle til fakturering
     * 
     * @return ordre
     */
    List<FaktureringV> findAll();

    /**
     * Finner basert på ordre nummer
     * 
     * @param orderNr
     * @return ordre
     */
    List<FaktureringV> findByOrderNr(String orderNr);

    /**
     * Finner basert på kundenummer
     * 
     * @param customerNr
     * @return ordre
     */
    List<FaktureringV> findByCustomerNr(Integer customerNr);

    /**
     * Oppdaterer
     * 
     * @param faktureringV
     */
    void refresh(FaktureringV faktureringV);

    /**
     * Finner alle basert på parametre
     * 
     * @param params
     * @return ordre
     */
    List<FaktureringV> findByParams(ExcelReportSetting params);

    List<FaktureringV> findByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup);

    List<FaktureringV> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);
}
