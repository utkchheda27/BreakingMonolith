package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    RestClient restClient;

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrders(@PathVariable String id) throws MalformedURLException {
        String url = "http://localhost:8082/products/" + id;

        //Fluent API used here, each method exposes the next set of methods to call.
        // The retrieve() method is used to perform the HTTP GET request and retrieve the response body as a String.
        String response = restClient
                .get()
                .uri(url)
                .retrieve()
                .body(String.class);

        System.out.println("Response from Product API called from order service: " + response);
        return ResponseEntity.ok(response);
    }
}
