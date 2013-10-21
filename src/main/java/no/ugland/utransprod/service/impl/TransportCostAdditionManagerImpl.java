package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.TransportCostAdditionDAO;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.service.ITransportCostAddition;
import no.ugland.utransprod.service.TransportCostAdditionManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

public class TransportCostAdditionManagerImpl implements
        TransportCostAdditionManager {
    private TransportCostAdditionDAO dao;

    public final void setTransportCostAdditionDAO(
            final TransportCostAdditionDAO aDao) {
        this.dao = aDao;
    }

    public final TransportCostAddition findByDescription(final String desc) {
        return dao.findByDescription(desc);
    }

    public final void deleteAll() {
        dao.removeAll();

    }

    public final void saveTransportCostAddition(
            final TransportCostAddition transportCostAddition) {
        dao.saveObject(transportCostAddition);

    }

    public final Map<ITransportCostAddition, BigDecimal> calculateCostAddition(
            final Transportable transportable,
            final TransportCost transportCost, final Periode period)
            throws ProTransException {

        Map<ITransportCostAddition, BigDecimal> additionValues = calculateAllAdditionsWithMaxAdditions(
                transportable, transportCost, period);
        BigDecimal additionCost = getAdditionCost(additionValues.values());
        additionValues.putAll(calculateAllAdditionsWithoutMaxAdditions(
                transportable, transportCost.getCost().add(additionCost),
                period));
        return additionValues;
    }

    private BigDecimal getAdditionCost(final Collection<BigDecimal> costs) {
        BigDecimal totalCost = BigDecimal.valueOf(0);
        for (BigDecimal cost : costs) {
            totalCost = totalCost.add(cost);
        }
        return totalCost;
    }

    private Map<ITransportCostAddition, BigDecimal> calculateAllAdditionsWithoutMaxAdditions(
            final Transportable transportable, final BigDecimal transportCost,
            final Periode period) throws ProTransException {
        List<TransportCostAddition> additions = dao
                .findTransportBasisAdditions(0);
        BigDecimal additionValue = BigDecimal.valueOf(0);
        Map<ITransportCostAddition, BigDecimal> additionMap =
            new Hashtable<ITransportCostAddition, BigDecimal>();

        for (TransportCostAddition addition : additions) {
            ITransportCostAddition additionImpl = TransportCostAdditionImplFacory
                    .getTransportCostAdditionImpl(addition);
            additionValue = calculateTmpAddition(transportable, transportCost,
                    period, additionImpl, false);
            if (additionValue.intValue() != 0) {
                additionMap.put(additionImpl, additionValue);
            }
        }

        return additionMap;
    }

    private Map<ITransportCostAddition, BigDecimal> calculateAllAdditionsWithMaxAdditions(
            final Transportable transportable,
            final TransportCost transportCost, final Periode period)
            throws ProTransException {
        List<TransportCostAddition> additions = dao
                .findTransportBasisAdditions(1);

        Map<ITransportCostAddition, BigDecimal> maxAdditionMap = createMaxAdditionMap(
                additions, transportCost.getCost(), transportable, period,
                false, null);

        if (maxAdditionMap != null) {
            if (transportable.getPostShipment() != null) {
                maxAdditionMap = checkMaxAdditionForPostShipment(transportable,
                        transportCost, period, additions, maxAdditionMap);
            }
            maxAdditionMap = calculateMaxAdditions(transportCost,
                    maxAdditionMap);
        }
        return maxAdditionMap;
    }

    private Map<ITransportCostAddition, BigDecimal> checkMaxAdditionForPostShipment(
            final Transportable transportable,
            final TransportCost transportCost, final Periode period,
            final List<TransportCostAddition> additions,
            final Map<ITransportCostAddition, BigDecimal> aMaxAdditionMap)
            throws ProTransException {
        Map<ITransportCostAddition, BigDecimal> maxAdditionMap = aMaxAdditionMap;
        int maxAllowedAddition = transportCost.getMaxAddition();
        Map<ITransportCostAddition, BigDecimal> maxAdditionMapOrder = createMaxAdditionMap(
                additions, transportCost.getCost(), transportable.getOrder(),
                period, true, maxAdditionMap.keySet());
        if (maxAdditionMapOrder != null) {
            if (maxAdditionMapOrder.size() >= maxAllowedAddition) {
                maxAdditionMap = removeAdditionsSentBefore(transportable,
                        period, maxAdditionMap, maxAdditionMapOrder);

            }
        }
        return maxAdditionMap;
    }

    private Map<ITransportCostAddition, BigDecimal> removeAdditionsSentBefore(
            final Transportable transportable, final Periode period,
            final Map<ITransportCostAddition, BigDecimal> maxAdditionMap,
            final Map<ITransportCostAddition, BigDecimal> maxAdditionMapOrder) {
        int numbersSentBefore = getNumbersSentBefore(maxAdditionMapOrder
                .keySet(), transportable.getOrder(), transportable
                .getTransport());
        Set<ITransportCostAddition> keys = maxAdditionMap.keySet();
        int counter = 0;
        for (ITransportCostAddition key : keys) {
            if (counter < numbersSentBefore) {
                maxAdditionMap.remove(key);
                counter++;
            }
        }
        return maxAdditionMap;
    }

    private int getNumbersSentBefore(final Set<ITransportCostAddition> additions,
            final Order order, final Transport transport) {
        int numbersSentBefore = 0;
        for (ITransportCostAddition addition : additions) {
            OrderLine orderLine = order.getOrderLine(addition.getArticlePath());
            if (orderLine != null && orderLine.getColli() != null
                    && orderLine.getColli().getSent() != null) {
                if (Util.compareTransport(orderLine.getTransport(), transport) < 0) {
                    numbersSentBefore++;
                }
            }
        }
        return numbersSentBefore;
    }

    private Map<ITransportCostAddition, BigDecimal> createMaxAdditionMap(
            final List<TransportCostAddition> additions,
            final BigDecimal transportCost, final Transportable transportable,
            final Periode period, final boolean ignoreSent,
            final Set<ITransportCostAddition> ignoreAddtions)
            throws ProTransException {
        Map<ITransportCostAddition, BigDecimal> maxAdditionMap =
            new Hashtable<ITransportCostAddition, BigDecimal>();
        for (TransportCostAddition addition : additions) {
            ITransportCostAddition additionImpl = TransportCostAdditionImplFacory
                    .getTransportCostAdditionImpl(addition);
            if (ignoreAddtions == null
                    || !ignoreAddtions.contains(additionImpl)) {
                BigDecimal tmpAdditionValue = calculateTmpAddition(
                        transportable, transportCost, period, additionImpl,
                        ignoreSent);
                if (tmpAdditionValue.intValue() != 0) {
                    maxAdditionMap = addAdditionToMaxAdditionMap(
                            maxAdditionMap, tmpAdditionValue, additionImpl);
                }
            }
        }
        return maxAdditionMap;
    }

    private BigDecimal calculateTmpAddition(final Transportable transportable,
            final BigDecimal transportCost, final Periode period,
            final ITransportCostAddition additionImpl, final boolean ignoreSent) {

        BigDecimal tmpAdditionValue = additionImpl.calculateAddition(
                transportCost, transportable, period, ignoreSent);
        return tmpAdditionValue;
    }

    private Map<ITransportCostAddition, BigDecimal> addAdditionToMaxAdditionMap(
            final Map<ITransportCostAddition, BigDecimal> aMaxAdditionMap,
            final BigDecimal tmpAdditionValue, final ITransportCostAddition additionImpl) {
        Map<ITransportCostAddition, BigDecimal> maxAdditionMap=aMaxAdditionMap;
        maxAdditionMap = getMaxAdditionMap(maxAdditionMap);
        maxAdditionMap.put(additionImpl, tmpAdditionValue);
        return maxAdditionMap;
    }

    private Map<ITransportCostAddition, BigDecimal> getMaxAdditionMap(
            final Map<ITransportCostAddition, BigDecimal> aMaxAdditionMap) {
        Map<ITransportCostAddition, BigDecimal> maxAdditionMap=aMaxAdditionMap;
        if (maxAdditionMap == null) {
            maxAdditionMap = new Hashtable<ITransportCostAddition, BigDecimal>();
        }
        return maxAdditionMap;
    }

    private Map<ITransportCostAddition, BigDecimal> calculateMaxAdditions(
            final TransportCost transportCost,
            final Map<ITransportCostAddition, BigDecimal> maxAdditionMap)
            throws ProTransException {
        Integer maxAdditions = transportCost.getMaxAddition();

        if (maxAdditions == null) {
            throw new ProTransException(
                    "Det er ikke definert maks tillegg for postnr "
                            + transportCost.getPostalCode());
        }

        int additionCount = 0;
        Set<ITransportCostAddition> keySet = maxAdditionMap.keySet();
        List<ITransportCostAddition> keySetList = new ArrayList<ITransportCostAddition>(
                keySet);
        for (ITransportCostAddition addition : keySetList) {
            if (additionCount >= maxAdditions) {
                maxAdditionMap.remove(addition);
            }
            additionCount++;
        }
        return maxAdditionMap;
    }

}
