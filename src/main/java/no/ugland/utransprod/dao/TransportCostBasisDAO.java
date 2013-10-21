package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.TransportCostBasis;

public interface TransportCostBasisDAO extends DAO<TransportCostBasis>{
    List<TransportCostBasis> findById(Integer transportCostBasisId);
    //void lazyLoad(TransportCostBasis transportCostBasis, LazyLoadTransportCostBasisEnum[] enums) ;
    void setInvoiceNr(TransportCostBasis transportCostBasis, String invoiceNr);
}
