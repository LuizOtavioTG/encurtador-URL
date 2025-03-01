package com.example.EncurtadorURL.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;


@Entity
@Table(name = "links")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Link {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String urlLong;
    private String urlShort;
    private String urlQrCode;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updateAt;

    private LocalDateTime expiryAt;
    private Integer views;

    public Link(String urlLong, String urlShort){
        this.urlLong = urlLong;
        this.urlShort = urlShort;
        //TODO: IMPLEMENTAR QR CODE
        this.urlQrCode = "QR CODE INDIPONIVEL NO MOMENTO";
        this.expiryAt = LocalDateTime.now().plusDays(1);
        this.views = 0;
    }
}
