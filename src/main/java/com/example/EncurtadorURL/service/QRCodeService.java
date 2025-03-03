package com.example.EncurtadorURL.service;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QRCodeService {

    private final LinkRepository linkRepository;
    private final LinkService linkService;

    public QRCodeService(LinkRepository linkRepository, LinkService linkService) {
        this.linkRepository = linkRepository;
        this.linkService = linkService;
    }

    public ResponseEntity<byte[]> getQRCodeById(Long id) {
        Optional<Link> linkOptional = linkRepository.findById(id);

        if (linkOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Link link = linkOptional.get();
        linkService.incrementViewsAndSave(link);
        byte[] qrCodeBytes = link.getUrlQrCode();

        if (qrCodeBytes == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/png");
        return new ResponseEntity<>(qrCodeBytes, headers, HttpStatus.OK);
    }
}
