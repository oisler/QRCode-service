package qrcodeapi.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import qrcodeapi.service.QRCodeService;

@RestController
public final class QRCodeController {

  private final QRCodeService qrCodeService;

  public QRCodeController(@Autowired QRCodeService qrCodeService) {
    this.qrCodeService = qrCodeService;
  }

  @GetMapping("/api/health")
  public ResponseEntity<HttpStatus> getHealth() {
    return ResponseEntity.ok().build();
  }

  @GetMapping("/api/qrcode?size={}&type={}")
  public ResponseEntity<?> getQrCode(@RequestParam int size, @RequestParam String type) {
    try {
      Check.checkSize(size);
      Check.checkType(type);
      byte[] qrCode = qrCodeService.createQRCode(size);
      return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(qrCode);
    } catch (IllegalArgumentException ex) {
      return ResponseEntity.badRequest().body(ex.getMessage());
    } catch (IOException ex) {
      return ResponseEntity.internalServerError().build();
    }
  }

}

final class Check {

  private Check() {
  }

  static void checkSize(int size) {
    if (size < QRCodeService.MIN_SIZE || size > QRCodeService.MAX_SIZE) {
      String message = String.format("Image size must be between %d and %d pixels", QRCodeService.MIN_SIZE, QRCodeService.MAX_SIZE);
      throw new IllegalArgumentException(message);
    }
  }

  static void checkType(String type) {
    if (!QRCodeService.TYPES.contains(type)) {
      String message = String.format("Only %s, %s and %s image types are supported", QRCodeService.TYPES.toArray());
      throw new IllegalArgumentException(message);
    }
  }

}