package no.ugland.utransprod.service.impl;

import java.io.Serializable;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.CuttingDAO;
import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.service.CuttingManager;

public class CuttingManagerImpl extends ManagerImpl<Cutting> implements CuttingManager {

    @Override
    protected Serializable getObjectId(Cutting object) {
        return object.getCuttingId();
    }

    public Cutting findByProId(String aProId) {
        Cutting cutting = new Cutting();
        cutting.setProId(aProId);
        List<Cutting> cuttings = dao.findByExample(cutting);
        return cuttings.size() == 1 ? cuttings.get(0) : null;
    }

    public void removeCutting(Cutting cutting) {
        dao.removeObject(cutting.getCuttingId());

    }

    public void saveCutting(Cutting cutting, final Boolean overwriteExistingCutting) throws ProTransException {
        Cutting existingCutting = findByProId(cutting.getProId());
        if (existingCutting != null && !existingCutting.getOrder().equals(cutting.getOrder())) {
            throw new ProTransException("Kappfil med proid " + cutting.getProId() + " er allerede importert!");
        }
        if (overwriteExistingCutting) {
            checkAndDeleteExisting(cutting);
        }
        dao.saveObject(cutting);

    }

    private void checkAndDeleteExisting(Cutting cutting) {
        Cutting oldCutting = ((CuttingDAO)dao).findByOrder(cutting.getOrder());
        if (oldCutting != null) {
            removeCutting(oldCutting);
        }

    }

}
