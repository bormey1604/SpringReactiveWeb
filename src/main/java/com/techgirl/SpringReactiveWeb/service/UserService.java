package com.techgirl.SpringReactiveWeb.service;

import com.techgirl.SpringReactiveWeb.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class UserService {

    private final WebClient webClient;

    public UserService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/api/v1").build();
    }

    public Mono<User> getUserById(Long id) {
        return webClient.get()
                .uri("/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .timeout(Duration.ofMillis(10000));
    }

    public Flux<User> getAllUsers() {
        return webClient.get()
                .uri("/users")
                .retrieve()
                .bodyToFlux(User.class)
                .timeout(Duration.ofMillis(10000));
    }

    public Mono<User> createUser(User userRequest) {
        return webClient.post()
                .uri("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(userRequest)
                .retrieve()
                .onStatus(HttpStatus.CONFLICT::equals, clientResponse ->
                        Mono.error(new RuntimeException("User already exists")))
                .bodyToMono(User.class)
                .timeout(Duration.ofMillis(10000));
    }



}

