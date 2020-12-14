package com.cafex.billing;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.cafex.billing.domain.Bill;
import com.cafex.billing.domain.BillItem;
import com.cafex.billing.domain.Item;
import com.cafex.billing.domain.ItemType;

@Service
@Configuration
@PropertySource("classpath:menu.properties")
public class BillingService implements EnvironmentAware {
	
	@Value("${servicecharge.percentage.food}")
	private double foodServiceCharge;
	
	@Value("${servicecharge.percentage.drink}")
	private double drinkServiceCharge;
	
	
	
	 @Autowired
	 private Environment env;
	
	
	public Bill generateBill(List<Item> items) {
		BigDecimal total = new BigDecimal(0);
		total = total.setScale(2, RoundingMode.HALF_UP);
		List<BillItem> billItems = new ArrayList<BillItem>();
		
		System.out.println("Food Serice charge %: "+foodServiceCharge);
		for (Item item : items) {
			System.out.println(BigDecimal.valueOf(Double.parseDouble(env.getProperty(item.getItemName()))).setScale(2, RoundingMode.HALF_UP).doubleValue());
			BigDecimal itemPrice = new BigDecimal(env.getProperty(item.getItemName())).setScale(2);
			BigDecimal itemTotal = new BigDecimal((itemPrice.doubleValue() * item.getQuantity()) + getServiceCharge(item));
			//itemTotal = new BigDecimal(itemTotal.doubleValue() + checkDiscount(items));
			total =  new BigDecimal( total.doubleValue() + itemTotal.doubleValue() );
			BillItem billItem = new BillItem();
			billItem.setItemName(item.getItemName());
			billItem.setItemState(item.getItemState());
			billItem.setItemType(item.getItemType());
			billItem.setQuantity(item.getQuantity());
			billItem.setItemId(item.getItemId());
			billItem.setItemTotal(itemTotal);
			billItems.add(billItem);
		} 
		total = new BigDecimal( total.doubleValue() - checkDiscount(items));
		Bill billGenerated = new Bill();
		
	
		billGenerated.setItems(billItems);
		total = total.setScale(2, RoundingMode.HALF_UP);
		System.out.println(total);
		billGenerated.setTotal(total.setScale(2, BigDecimal.ROUND_HALF_UP));
		System.out.println("Bill Generated Total: "+billGenerated.getTotal());
		return billGenerated;
	}
	
	private double getServiceCharge(Item item) {
		
		if(item.getItemType().equals(ItemType.food))
			return item.getQuantity() * Double.parseDouble(env.getProperty(item.getItemName())) * foodServiceCharge;
		else
			return item.getQuantity() * Double.parseDouble(env.getProperty(item.getItemName())) * drinkServiceCharge;
	}
	
	private double checkDiscount(List<Item> items) {
		double discount = 0;
		List<String> itemNames = new ArrayList<String>();
		for(Item item: items) {
			itemNames.add(item.getItemName());
		}
		
		for(Item item: items) {
			if(item.getItemName().equalsIgnoreCase("CheeseSandwich"))
				if(itemNames.contains("Cola"))
					discount = 0.1 * item.getQuantity() * Double.parseDouble(env.getProperty(item.getItemName()));
		}
		return discount;
	}

	@Override
	public void setEnvironment(Environment environment) {
		// TODO Auto-generated method stub
		 this.env = environment;
	        
	}
}
