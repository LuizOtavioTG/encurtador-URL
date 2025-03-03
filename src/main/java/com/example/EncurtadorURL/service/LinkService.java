package com.example.EncurtadorURL.service;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;
import com.example.EncurtadorURL.service.validator.RedirectValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LinkService {

    private final LinkRepository linkRepository;
    private final List<RedirectValidator> redirectValidators;

    public LinkService(LinkRepository linkRepository, List<RedirectValidator> redirectValidators) {
        this.linkRepository = linkRepository;
        this.redirectValidators = redirectValidators;
    }

    @Transactional
    public Link createUrl(String originalUrl){
        Link link = new Link(originalUrl, UrlShortenerUtils.generateRandomUrlCode());
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

