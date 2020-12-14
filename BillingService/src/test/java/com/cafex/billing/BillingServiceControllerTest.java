package com.cafex.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.cafex.billing.domain.Bill;
import com.cafex.billing.domain.Item;
import com.cafex.billing.domain.ItemState;
import com.cafex.billing.domain.ItemType;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonGeneratorImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes=BillingServiceApplication.class)
public class BillingServiceControllerTest {
	
	
	
	  @Autowired
	BillingService billingService;
	
	  @Autowired
	  BillingServiceController billingController;

	 
	@Test
	void testGenerateBill() {
		List<Item> items = new ArrayList<Item>();
		Bill bill = billingService.generateBill(items);
		assert(bill.getTotal().doubleValue()>=0.0);
	}
	
	@Test
	void testGenerateBillForSingleDrink() {
		Item item = new Item();
		item.setItemName("Coffee");
		item.setItemType(ItemType.drink);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		double total = billingService.generateBill(items).getTotal().doubleValue();
		assert(total > 0.00);
		assertEquals(total, 1.00);
	}
	
	@Test
	void testGenerateBillForSingleFood() {
		Item item = new Item();
		item.setItemName("SteakSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		List<Item> items = new ArrayList<Item>();
		items.add(item);
		double total = billingService.generateBill(items).getTotal().doubleValue();
		assert(total > 0.00);
		assert(billingService.generateBill(items).getTotal().precision()==3);
		assertThat(total > 4.50);
	}
	
	@Test
	void testGenerateBillForMultipleFoods() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setItemName("SteakSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		items.add(item);
		item.setItemName("CheeseSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		
		items.add(item);
		double total = billingService.generateBill(items).getTotal().doubleValue();
		assert(total > 0.00);
		assert(billingService.generateBill(items).getTotal().precision()==3);
		
	}
	
	@Test
	void testDiscountForComboFoodAndDrink() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setItemName("CheeseSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.COLD);
		item.setQuantity(1);
		items.add(item);
		item = new Item();
		item.setItemName("Cola");
		item.setItemType(ItemType.drink);
		item.setItemState(ItemState.COLD);
		item.setQuantity(1);
		items.add(item);
		double total = billingService.generateBill(items).getTotal().doubleValue();
		assert(total > 0.00);
		assertEquals(2.50, total);
	}
	
	@Test
	void testGenerateBillFor() {
		List<Item> items = new ArrayList<Item>();
		Item item = new Item();
		item.setItemName("SteakSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		items.add(item);
		item.setItemName("CheeseSandwich");
		item.setItemType(ItemType.food);
		item.setItemState(ItemState.HOT);
		item.setQuantity(1);
		
		items.add(item);
		ObjectMapper om = new ObjectMapper();
		try {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/students/Student1/courses")
				.accept(MediaType.APPLICATION_JSON).content(om.writeValueAsString(items))
				.contentType(MediaType.APPLICATION_JSON);
		ResponseEntity<Bill> response = billingController.generateBillFor(items);
		

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
