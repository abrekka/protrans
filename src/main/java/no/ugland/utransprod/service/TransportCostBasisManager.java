package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.TransportCostBasis;

public interface TransportCostBasisManager extends OverviewManager<TransportCostBasis>{
    String MANAGER_NAME = "transportCostBasisManager";
	void saveTransportCostBasisList(List<TransportCostBasis> transportCostBasisList);
    void removeTransportCostBasis(TransportCostBasis transportCostBasis);
    List<TransportCostBasis> findById(Integer transportCostBasisId);
    //void lazyLoad(TransportCostBasis transportCostBasis,LazyLoadTransportCostBasisEnum[] enums);
    void saveTransportCostBasis(TransportCostBasis transportCostBasis);
    void setInvoiceNr(TransportCostBasis transportCostBasis,String invoiceNr);
}
