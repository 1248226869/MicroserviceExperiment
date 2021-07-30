package com.cxytiandi.sharding.reactorNettyTailen;

import org.springframework.http.HttpCookie;
import reactor.core.publisher.Flux;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import java.util.List;

/**
 * @Description
 * @Author zhao tailin
 * @Date 2021/7/20
 * @Version 1.0.0
 */
public class Client {
    public static void main(String[] args) {
        HttpClient.create()             // Prepares an HTTP client ready for configuration
                .port(12343)  // Obtains the server's port and provides it as a port to which this
                // client should connect
                .post()               // Specifies that POST method will be used
                .uri("/test/World")   // Specifies the path
                .send(ByteBufFlux.fromString(Flux.just("Hello")))  // Sends the request body
                .responseContent()    // Receives the response body
                .aggregate()
                .asString()
                .log("http-client")
                .block();
    }
}
