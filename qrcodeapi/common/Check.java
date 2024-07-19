package qrcodeapi.common;

import java.util.Map;

import org.springframework.http.MediaType;

import com.google.zxing.EncodeHintType;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public final class Check {

  private static final int MIN_SIZE = 150;
  private static final int MAX_SIZE = 350;

  private Check() {
  }

  public static int size(int size) {
    if (size < MIN_SIZE || size > MAX_SIZE) {
      throw new IllegalArgumentException(String.format("Image size must be between %d and %d pixels", MIN_SIZE, MAX_SIZE));
    }

    return size;
  }

  public static MediaType mediaTypeBy(String type) {
    return switch (type) {
      case "png" -> MediaType.IMAGE_PNG;
      case "jpeg" -> MediaType.IMAGE_JPEG;
      case "gif" -> MediaType.IMAGE_GIF;
      default -> throw new IllegalArgumentException("Only png, jpeg and gif image types are supported");
    };
  }

  public static String content(String content) {
    if (content == null || content.isBlank()) {
      throw new IllegalArgumentException("Contents cannot be null or blank");
    }

    return content;
  }

  public static Map<EncodeHintType, ErrorCorrectionLevel> hintsBy(String correction) {
    if (ErrorCorrectionLevel.L.name().equals(correction)) {
      return Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
    } else if (ErrorCorrectionLevel.M.name().equals(correction)) {
      return Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
    } else if (ErrorCorrectionLevel.Q.name().equals(correction)) {
      return Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
    } else if (ErrorCorrectionLevel.H.name().equals(correction)) {
      return Map.of(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
    } else {
      throw new IllegalArgumentException("Permitted error correction levels are L, M, Q, H");
    }
  }

}