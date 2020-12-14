package com.cafex.billing;



import java.util.List;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cafex.billing.domain.Bill;
import com.cafex.billing.domain.Item;

@RestController
public class BillingServiceController {

	@Autowired
	BillingService billingService;
	
	@PostMapping(path = "/billTotal", consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Bill> generateBillFor(@RequestBody List<Item> items) {
		Bill bill = billingService.generateBill(items);
		return new ResponseEntity<Bill>( bill , HttpStatus.CREATED);
	}
	
}
