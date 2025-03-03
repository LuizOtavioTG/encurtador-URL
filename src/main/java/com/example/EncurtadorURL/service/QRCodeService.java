package com.example.EncurtadorURL.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Hashtable;

@Service
public class QRCodeService {

    public byte[] generateQRCode(String text) {
        byte[] qrCodeImage;

        try {
            // Define as configurações do QR Code
            int width = 300;
            int height = 300;

            Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
            hints.put(EncodeHintType.MARGIN, 1);  // Ajusta a margem do QR code

            // Cria o QR Code com o texto
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hints);

            // Converte a matriz para uma imagem
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bufferedImage.setRGB(x, y, bitMatrix.get(x, y) ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
                }
            }

            // Converte a imagem em um array de bytes para envio
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "PNG", byteArrayOutputStream);
            qrCodeImage = byteArrayOutputStream.toByteArray();

        } catch (WriterException | IOException e) {
            // Tratar exceções adequadamente
            e.printStackTrace();
            // Retorna null ou um valor apropriado em caso de erro
            qrCodeImage = null;
        }

        return qrCodeImage;
    }
}
