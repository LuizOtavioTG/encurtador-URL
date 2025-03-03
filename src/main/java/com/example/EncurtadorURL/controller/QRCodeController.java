package com.example.EncurtadorURL.controller;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/QRCode/")
public class QRCodeController {

    private final LinkRepository linkRepository;

    public QRCodeController(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id){
        Optional<Link> linkOptional = linkRepository.findById(id);

        if (linkOptional.isPresent()) {
            Link link = linkOptional.get();
            byte[] qrCodeBytes = link.getUrlQrCode();  // Recupera o QR Code armazenado no banco

            if (qrCodeBytes != null) {
                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
                return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
            } else {
                return ResponseEntity.notFound().build();  // Caso o QR Code não exista
            }
        } else {
            return ResponseEntity.notFound().build();  // Caso o link não exista
        }
    }
}
