package com.example.EncurtadorURL.dto;

import com.example.EncurtadorURL.model.Link;

import java.time.LocalDateTime;

public record LinkResponseDTO (String urlLong, String urlShort, String urlQrCode, LocalDateTime expiryAt){
    public LinkResponseDTO(Link link) {
        this(link.getUrlLong(),
                "http://localhost:8080/r/" + link.getUrlShort(),
                "http://localhost:8080/QRCode/" + link.getId(),
                link.getExpiryAt());
    }
}

