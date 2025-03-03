package com.example.EncurtadorURL.controller;

import com.example.EncurtadorURL.service.QRCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/QRCode/")
public class QRCodeController {

    private final QRCodeService qrCodeService;

    public QRCodeController(QRCodeService qrCodeService) {
        this.qrCodeService = qrCodeService;
    }

    @GetMapping("{id}")
    public ResponseEntity<byte[]> getQRCode(@PathVariable Long id) {
        return qrCodeService.getQRCodeById(id);
    }
}
