package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.gui.model.Transportable;

public abstract class AbstractTransportable extends BaseObject implements Transportable {
    private List<CustTr> custTrs;
    private Ord takstolKjopOrd;

    public final List<CustTr> getCustTrs() {
	return custTrs;
    }

    public final void setCustTrs(final List<CustTr> aCustTrs) {
	custTrs = aCustTrs;

    }

    public Ord getTakstolKjopOrd() {
	return takstolKjopOrd;
    }

    public void setTakstolKjopOrd(Ord ord) {
	this.takstolKjopOrd = ord;
    }

    public boolean isPaid() {
	if (custTrs != null && custTrs.size() > 0) {
	    BigDecimal restAmount = BigDecimal.ZERO;
	    for (CustTr custTr : custTrs) {
		restAmount = restAmount.add(custTr.getRestAmount() != null ? custTr.getRestAmount() : BigDecimal.ZERO);
	    }
	    return restAmount.intValue() == 0 ? true : false;

	}

	if (getPaidDate() != null) {
	    return true;
	}
	return false;
    }
}
