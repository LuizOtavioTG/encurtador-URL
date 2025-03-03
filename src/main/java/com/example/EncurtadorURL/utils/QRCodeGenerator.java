package com.example.EncurtadorURL.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

@Component
public class QRCodeGenerator {
    private static final Logger logger = LoggerFactory.getLogger(QRCodeGenerator.class);
    public byte[] generateQRCode(String logUrl) {
        byte[] qrCodeImage;


        try {
            int width = 300;
            int height = 300;
            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(logUrl, BarcodeFormat.QR_CODE, width, height, hints);

            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
            qrCodeImage = byteArrayOutputStream.toByteArray();

        } catch (WriterException | IOException e) {
            logger.error("Erro ao gerar QR Code", e);
            qrCodeImage = null;
        }
        return qrCodeImage;
    }
}
