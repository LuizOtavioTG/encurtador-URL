package com.example.EncurtadorURL.controller;

import com.example.EncurtadorURL.dto.LinkResponseDTO;
import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.service.LinkService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    @PostMapping()
    public ResponseEntity<LinkResponseDTO> createUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("originalUrl");
        Link link = linkService.createLink(originalUrl);
        return ResponseEntity.status(HttpStatus.CREATED).body(new LinkResponseDTO(link));
    }

    @GetMapping("r/{urlShort}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String urlShort) {
        Link link = linkService.getOriginalUrl(urlShort);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        linkService.incrementViewsAndSave(link);
        linkService.validateUrl(link);

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .headers(headers)
                .header("Location", link.getUrlLong())
                .build();
    }
}