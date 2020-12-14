package com.cafex.billing.domain;

import java.io.Serializable;
import java.util.Random;

public class Item implements Serializable{
	
	private int itemId = new Random().nextInt();
	private String itemName;
	private ItemType itemType;
	private ItemState itemState;
	private int quantity; 
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	

	
	public int getItemId() {
		return itemId;
	}
	
	
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public ItemState getItemState() {
		return itemState;
	}
	public void setItemState(ItemState itemState) {
		this.itemState = itemState;
	}
	
	
	

}
