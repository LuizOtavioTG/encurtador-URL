package com.example.EncurtadorURL.Service;

import com.example.EncurtadorURL.model.Link;
import com.example.EncurtadorURL.repository.LinkRepository;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LinkService {

    private LinkRepository linkRepository;

    public LinkService(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }


    public Link shortenUrl(String originalUrl){
        Link link = new Link(originalUrl, UrlShortenerUtils.generateRandomUrlCode());
        linkRepository.save(link);
        return link;
    }

    public Link getOriginalUrl(String urlShort) throws Exception {
        Optional<Link> url = linkRepository.findByUrlLong(urlShort);
        return url.orElseThrow(() -> new NoSuchElementException("URL not found"));
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
            return stringBuilder.toString();
        }
    }
}

