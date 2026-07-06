package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/{id}")
    public ResponseEntity<String> getOrders(@PathVariable String id) throws MalformedURLException {
        String url = "http://localhost:8082/products/" + id;
        String response = restTemplate.getForObject(url, String.class);
        return ResponseEntity.ok(response);
    }
}
