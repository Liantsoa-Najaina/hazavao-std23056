package com.hei.hazavao.service;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class OpenAIService {
  private final WebClient webClient;

  private final String apiKey = System.getenv("API_KEY");

  public OpenAIService(WebClient.Builder builder) {
    this.webClient = builder.baseUrl("https://api.openai.com/v1").build();
  }

  public Mono<String> getMalagasyWordMeaning(String word) {
    Map<String, Object> body =
        Map.of(
            "model",
            "gpt-3.5-turbo",
            "messages",
            List.of(
                Map.of(
                    "role",
                    "system",
                    "content",
                    "You are a helpful assistant that defines Malagasy words."),
                Map.of(
                    "role",
                    "user",
                    "content",
                    "What does the Malagasy word '" + word + "' mean in Malagasy?")),
            "temperature",
            0.7);

    return webClient
        .post()
        .uri("/chat/completions")
        .header("Authorization", "Bearer " + apiKey)
        .header("Content-Type", "application/json")
        .bodyValue(body)
        .retrieve()
        .bodyToMono(Map.class)
        .map(
            json -> {
              List<Map<String, Object>> choices = (List<Map<String, Object>>) json.get("choices");
              Map<String, Object> message = (Map<String, Object>) choices.getFirst().get("message");
              return (String) message.get("content");
            });
  }
}
