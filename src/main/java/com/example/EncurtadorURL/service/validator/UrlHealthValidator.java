package com.example.EncurtadorURL.service.validator;

import com.example.EncurtadorURL.model.Link;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

import java.net.URISyntaxException;
import java.net.URLConnection;

public class UrlHealthValidator implements RedirectValidator{
    @Override
    public void validator(Link link) {
        try {
            URI urlObj = new URI(link.getUrlLong());
            URLConnection conn = urlObj.toURL().openConnection();
            if (conn instanceof HttpURLConnection httpConn) {
                httpConn.setRequestMethod("HEAD");
                httpConn.setConnectTimeout(3000);
                int code = httpConn.getResponseCode();

                if (code < 200 || code >= 400) {
                    throw new IOException("URL inválida, código de resposta: " + code);
                }
            }
        } catch (URISyntaxException | IOException ex) {
            // Lança uma nova exceção RuntimeException ou personaliza o erro conforme necessário
            throw new RuntimeException("Falha ao validar a URL: " + ex.getMessage(), ex);
        }
    }
}