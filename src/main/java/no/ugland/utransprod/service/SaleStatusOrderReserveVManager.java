package no.ugland.utransprod.service;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;

public interface SaleStatusOrderReserveVManager {

	public static final String MANAGER_NAME = "saleStatusOrderReserveVManager";

	SaleStatusOrderReserveV findByProductArea(ProductArea productArea);

}
