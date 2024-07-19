package qrcodeapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import qrcodeapi.common.Check;
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

  @GetMapping("/api/qrcode")
  public ResponseEntity<?> getQrCode(
      @RequestParam String contents,
      @RequestParam(required = false, defaultValue = "250") int size,
      @RequestParam(required = false, defaultValue = "L") String correction,
      @RequestParam(required = false, defaultValue = "png") String type) {
    try {
      var qrCode = qrCodeService.createQRCode(Check.content(contents), Check.size(size), Check.hintsBy(correction));
      return ResponseEntity.ok().contentType(Check.mediaTypeBy(type)).body(qrCode);
    } catch (IllegalArgumentException exception) {
      return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(new ApiErrorDto(exception.getMessage()));
    }
  }

}

record ApiErrorDto(String error) {
}