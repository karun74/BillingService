package com.cafex.billing.domain;

import java.math.BigDecimal;

public class BillItem extends Item {

	private BigDecimal itemTotal;

	public BigDecimal getItemTotal() {
		return itemTotal;
	}

	public void setItemTotal(BigDecimal itemTotal) {
		this.itemTotal = itemTotal;
	}
}
