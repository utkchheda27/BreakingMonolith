package org.example.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @GetMapping("/{id}")
    public ResponseEntity<String> getOrders(@PathVariable String id) throws MalformedURLException {
        HttpURLConnection httpURLConnection = null;
        try{
            URL url = new URL("http://localhost:8082/products/" + id);

            /*
            Creates an object of HttpUrlConnection, consider it like
            an envelope or a request in which we specify all the details like URL,
            RequestMethod, RequestType,connectionTimeout details etc
             */
            httpURLConnection = (HttpURLConnection) url.openConnection();

            //Setting httpConnection request method and header
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("Accept", "application/json");

            //max time to establish the TCP connection(ms)
            httpURLConnection.setConnectTimeout(100);
            //max time to wait for server response
            httpURLConnection.setReadTimeout(500);
            httpURLConnection.connect();

            /*
                Below, opens a TCP connection and sends the HTTP request and also reads the response,
                TCP connection is created when we make the first Input/Output request on HttpUrlConnection such as:
                -getInputStream()
                -getResponseCode()
                -connect(etc)

                HttpClient object is a wrapper around the HttpURLConnection object, it provides a higher level API to send HTTP requests and receive HTTP responses.
                So before creating the HttpClient object,
                it first checks with 'keepAliveCache' class,
                if there is already an object present,
                if not it creates one object and also puts into the cache.
                key ->host:port
                value -> httpClient object
             */
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine;
            while((responseLine =in.readLine()) != null){
                response.append(responseLine);
            }
            in.close();
            System.out.println("Response from ProductService: " + response.toString());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        } finally {
            if(httpURLConnection != null){
                /*
                If response is fully read properly,
                the TCP connection i.e HttpClient is returned back to KeepAlive cache else
                TCP connection gets closed.
                 */
                httpURLConnection.disconnect();
            }
        }
        return ResponseEntity.ok("Order fetched with id: " + id);
    }
}
