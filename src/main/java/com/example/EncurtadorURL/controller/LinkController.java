package com.example.EncurtadorURL.controller;

import com.example.EncurtadorURL.dto.LinkRequestDTO;
import com.example.EncurtadorURL.dto.LinkResponseDTO;
import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping()
    public ResponseEntity<LinkResponseDTO> createUrl(@RequestBody Map<String, String> request){
        String originalUrl = request.get("originalUrl");
        Link link = linkService.createUrl(originalUrl);

        return  ResponseEntity.status(200).body(new LinkResponseDTO(link));
    }
}
