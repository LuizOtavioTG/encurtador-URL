package com.example.EncurtadorURL.service;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;
import com.example.EncurtadorURL.service.validator.RedirectValidator;
import com.example.EncurtadorURL.utils.QRCodeGenerator;
import com.example.EncurtadorURL.utils.UrlShortenerUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final QRCodeGenerator qrCodeGenerator;
    private final List<RedirectValidator> redirectValidators;

    public LinkService(LinkRepository linkRepository, QRCodeGenerator qrCodeGenerator, List<RedirectValidator> redirectValidators) {
        this.linkRepository = linkRepository;
        this.qrCodeGenerator = qrCodeGenerator;
        this.redirectValidators = redirectValidators;
    }

    @Transactional
    public Link createLink(String originalUrl) {
        byte[] qrCodeImage = qrCodeGenerator.generateQRCode(originalUrl);
        if (qrCodeImage == null) {
            throw new IllegalArgumentException("Erro ao gerar o QR code");
        }

        Link link = new Link(originalUrl, UrlShortenerUtils.generateRandomUrlCode(), qrCodeImage);
        linkRepository.save(link);
        return link;
    }

    public ResponseEntity<Void> redirectUrl(String urlShort) {
        Link link = linkRepository.findByUrlShort(urlShort)
                .orElseThrow(() -> new NoSuchElementException("URL not found"));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        incrementViewsAndSave(link);
        validateUrl(link);

        return ResponseEntity
                .status(HttpStatus.PERMANENT_REDIRECT)
                .headers(headers)
                .header("Location", link.getUrlLong())
                .build();
    }

    @Transactional
    public void incrementViewsAndSave(Link link) {
        link.incrementViews();
        linkRepository.save(link);
    }

    public void validateUrl(Link link) {
        redirectValidators.forEach(v -> v.validator(link));
    }
}

