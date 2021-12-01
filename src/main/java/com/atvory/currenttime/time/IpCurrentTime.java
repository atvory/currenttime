package com.atvory.currenttime.time;

import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

public class IpCurrentTime {
    
    public String getTime(){

        WebClient wb = WebClient.builder()
                        .baseUrl("http://worldtimeapi.org/api")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, 
                            org.springframework.http.MediaType.APPLICATION_JSON_VALUE)
                        .build();
                        
        Mono<TimeResponse> request = wb.get()
                        .uri("/ip")
                        .retrieve()
                        .bodyToMono(TimeResponse.class);

        return request.block().getUtcDateTime();
        
    }
}
