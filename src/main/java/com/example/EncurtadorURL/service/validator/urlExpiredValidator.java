package com.example.EncurtadorURL.service.validator;

import com.example.EncurtadorURL.expcetion.ExpiredUrlException;
import com.example.EncurtadorURL.model.Link;

import java.time.LocalDateTime;

public class urlExpiredValidator implements RedirectValidator{
    @Override
    public void validator(Link link) {
        boolean isExpired = link.getExpiryAt().isBefore(LocalDateTime.now());
        if (isExpired) {
            throw new ExpiredUrlException("Expirou o período de uso da URL");
        }
    }
}
