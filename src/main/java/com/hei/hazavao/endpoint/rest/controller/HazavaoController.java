package com.hei.hazavao.endpoint.rest.controller;

import com.hei.hazavao.service.OpenAIService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
public class HazavaoController {

  private final OpenAIService openAIService;

  @GetMapping("/hazavao")
  public Mono<String> defineWord(@RequestParam String teny) {
    return openAIService.getMalagasyWordMeaning(teny);
  }
}
