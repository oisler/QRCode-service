package qrcodeapi.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

@Service
public final class QRCodeService {

  public static final int MIN_SIZE = 150;
  public static final int MAX_SIZE = 350;
  public static final Set<String> TYPES = Set.of("png", "jpg", "gif");
  private static final int ZERO = 0;
  private static final String PNG = "png";

  public byte[] createQRCode(int size) throws IOException {
    BufferedImage image = createImage(size);
    return transformToByteArray(image);
  }

  private static BufferedImage createImage(int size) {
    BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

    Graphics2D graphics = image.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.fillRect(ZERO, ZERO, MIN_SIZE, MIN_SIZE);

    return image;
  }

  private static byte[] transformToByteArray(BufferedImage image) throws IOException {
    try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
      ImageIO.write(image, PNG, byteArrayOutputStream);
      return byteArrayOutputStream.toByteArray();
    }
  }

}