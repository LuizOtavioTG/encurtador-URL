package com.example.EncurtadorURL.service;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;
import com.example.EncurtadorURL.service.validator.RedirectValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final QRCodeService qrCodeService;
    private final List<RedirectValidator> redirectValidators;

    public LinkService(LinkRepository linkRepository, QRCodeService qrCodeService, List<RedirectValidator> redirectValidators) {
        this.linkRepository = linkRepository;
        this.qrCodeService = qrCodeService;
        this.redirectValidators = redirectValidators;
    }

    @Transactional
    public Link createLink(String originalUrl){

        byte[] qrCodeImage = qrCodeService.generateQRCode(originalUrl);
        if (qrCodeImage == null) {
            throw new IllegalArgumentException("Erro ao gerar o QR code");
        }
        System.out.println(qrCodeImage.getClass());

        Link link = new Link(originalUrl, UrlShortenerUtils.generateRandomUrlCode(), qrCodeImage);
        System.out.println("qrCodeImage content: " + Arrays.toString(qrCodeImage));
        System.out.println(qrCodeImage.getClass());
        linkRepository.save(link);
        return link;
    }

    public Link getOriginalUrl(String urlShort) {
        Optional<Link> url = linkRepository.findByUrlShort(urlShort);
        return url.orElseThrow(() -> new NoSuchElementException("URL not found"));
    }

    @Transactional
    public void incrementViewsAndSave (Link link){
        link.incrementViews();
        linkRepository.save(link);
    }
    public void validateUrl (Link link) {
        redirectValidators.forEach(v -> validateUrl(link));
    }


    public static class UrlShortenerUtils {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final SecureRandom random = new SecureRandom();

        public static String generateRandomUrlCode() {
            int length = random.nextInt(6) + 5;
            StringBuilder stringBuilder = new StringBuilder(length);

            for (int i = 0; i < length; i++) {
                int index = random.nextInt(CHARACTERS.length());
                stringBuilder.append(CHARACTERS.charAt(index));
            }
            return (stringBuilder.toString());
        }
    }
}

