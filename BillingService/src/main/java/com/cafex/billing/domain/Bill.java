package com.cafex.billing.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import com.cafex.billing.MoneyDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Bill implements Serializable{

	private List<BillItem> items ;
	 @JsonDeserialize(using=MoneyDeserializer.class)
	private BigDecimal total;
	public List<BillItem> getItems() {
		return items;
	}
	public void setItems(List<BillItem> items) {
		this.items = items;
	}
	public BigDecimal getTotal() {
		return total.setScale(2);
	}
	public void setTotal(BigDecimal total) {
		this.total = total.setScale(2);
	}
}
