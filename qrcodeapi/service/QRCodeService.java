package qrcodeapi.service;

import java.awt.image.BufferedImage;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

@Service
public final class QRCodeService {

  public BufferedImage createQRCode(String content, int imageSize, Map<EncodeHintType, ErrorCorrectionLevel> hints) {
    QRCodeWriter writer = new QRCodeWriter();
    try {
      BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, imageSize, imageSize, hints);
      return MatrixToImageWriter.toBufferedImage(matrix);
    } catch (WriterException e) {
      throw new IllegalArgumentException();
    }
  }

}