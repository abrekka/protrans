package no.ugland.utransprod.model;

import java.math.BigDecimal;

public class SaleStatusOrderReserveV {

	public static final SaleStatusOrderReserveV UNKNOWN = new SaleStatusOrderReserveV();
	private BigDecimal orderReserve;
	private Integer deptno;

	public BigDecimal getOrderReserve() {
		return orderReserve!=null?orderReserve:BigDecimal.ZERO;
	}

	public void setOrderReserve(BigDecimal orderReserve) {
		this.orderReserve = orderReserve;
	}

	public Integer getDeptno() {
		return deptno;
	}

	public void setDeptno(Integer deptno) {
		this.deptno = deptno;
	}

}
