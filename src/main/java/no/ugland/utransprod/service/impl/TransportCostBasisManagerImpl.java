package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.dao.TransportCostBasisDAO;
import no.ugland.utransprod.model.TransportCostBasis;
import no.ugland.utransprod.service.TransportCostBasisManager;

public class TransportCostBasisManagerImpl extends ManagerImpl<TransportCostBasis>implements TransportCostBasisManager {
    public final void saveTransportCostBasisList(
            final List<TransportCostBasis> transportCostBasisList) {
        for (TransportCostBasis basis : transportCostBasisList) {
            dao.saveObject(basis);
        }

    }

    public final void removeTransportCostBasis(
            final TransportCostBasis transportCostBasis) {
        dao.removeObject(transportCostBasis.getTransportCostBasisId());

    }

    public final List<TransportCostBasis> findById(
            final Integer transportCostBasisId) {
        return ((TransportCostBasisDAO)dao).findById(transportCostBasisId);
    }

    public final void saveTransportCostBasis(
            final TransportCostBasis transportCostBasis) {
        dao.saveObject(transportCostBasis);

    }

    public final List<TransportCostBasis> findAll() {
        return dao.getObjects();
    }

    public final List<TransportCostBasis> findByObject(final TransportCostBasis object) {
        return dao.findByExample(object);
    }

    public final void refreshObject(final TransportCostBasis object) {
        dao.refreshObject(object,object.getTransportCostBasisId());
    }

    public final void removeObject(final TransportCostBasis object) {
        removeTransportCostBasis(object);
    }

    public final void saveObject(final TransportCostBasis object) {
        saveTransportCostBasis(object);
    }

    public final void setInvoiceNr(final TransportCostBasis transportCostBasis, final String invoiceNr) {
        transportCostBasis.setInvoiceNr(invoiceNr);
        saveTransportCostBasis(transportCostBasis);
        ((TransportCostBasisDAO)dao).setInvoiceNr(transportCostBasis, invoiceNr);
    }

    /*public void lazyLoad(TransportCostBasis object, Enum[] enums) {
        lazyLoad(object,(LazyLoadTransportCostBasisEnum[]) enums);
        
    }*/

    @Override
    protected Serializable getObjectId(TransportCostBasis object) {
        return object.getTransportCostBasisId();
    }

}
