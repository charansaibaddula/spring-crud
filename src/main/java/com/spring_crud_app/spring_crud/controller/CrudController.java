package com.spring_crud_app.spring_crud.controller;

import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Flux;

// import java.time.Duration;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class CrudController {

    private final ChatClient chatClient;

    public CrudController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping(value = "/ask", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> ask(@RequestParam String message) {
        return chatClient.prompt()
                .user(message)
                .stream()
                .content()
                .bufferUntil(token -> token.contains("\n"))
                .map(tokens -> String.join("", tokens));
    }

}
