package com.example.retailer.pacts.provider;

import au.com.dius.pact.provider.junit.Consumer;
import au.com.dius.pact.provider.junit.Provider;
import au.com.dius.pact.provider.junit.State;
import au.com.dius.pact.provider.junit.loader.PactBroker;
import au.com.dius.pact.provider.junit.target.Target;
import au.com.dius.pact.provider.junit.target.TestTarget;
import au.com.dius.pact.provider.spring.SpringRestPactRunner;
import au.com.dius.pact.provider.spring.target.SpringBootHttpTarget;
import com.example.retailer.RetailerApplication;
import com.example.retailer.core.Item;
import com.example.retailer.core.RetailerService;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
/*
The purpose of this test is to verify that your provider (the “retailer” service) adheres to the defined contract
when interacting with the consumer (the “product” service).
*/
@RunWith(SpringRestPactRunner.class)
@SpringBootTest(classes = RetailerApplication.class, webEnvironment = RANDOM_PORT, properties = "server.port=80")
@Provider("retailer")
@Consumer("product")
@PactBroker(host = "localhost", port = "9292") // Specifies the Pact broker location (where contracts are stored and retrieved).
@ActiveProfiles("test")
public class ProductPactTests {

    @TestTarget
    public Target target = new SpringBootHttpTarget();

    @State("Get item details") // this annotation defines the state (or interaction) for the contract test.
                              // In this case, it simulates the provider responding to a request for item details with a predefined item (“Apple iPad” priced at 200.0).
    public void testBuyerOneContract() {
        Item item = new Item("Apple", "iPad", 200.0);

        RetailerService mock = Mockito.mock(RetailerService.class);

        Mockito.when(mock.getItemDetails("SomeId"))
                .thenReturn(item);
    }
}
