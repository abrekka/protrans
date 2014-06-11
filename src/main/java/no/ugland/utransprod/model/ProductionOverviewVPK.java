package no.ugland.utransprod.model;

import java.io.Serializable;

public class ProductionOverviewVPK implements Serializable {
    private Integer orderId;
    private Integer postShipmentId;

    public Integer getOrderId() {
	return orderId;
    }

    public void setOrderId(Integer orderId) {
	this.orderId = orderId;
    }

    public Integer getPostShipmentId() {
	return postShipmentId;
    }

    public void setPostShipmentId(Integer postShipmentId) {
	this.postShipmentId = postShipmentId;
    }
}
