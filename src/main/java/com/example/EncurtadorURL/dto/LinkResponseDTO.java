package com.example.EncurtadorURL.dto;

import com.example.EncurtadorURL.model.Link;

public record LinkResponseDTO (String link, String urlQrCode){
    public LinkResponseDTO(Link link) {
        this(link.getUrlShort(), link.getUrlQrCode());
    }
}

