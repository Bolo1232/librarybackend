package libraryManagement.librarybackend.controller;

import libraryManagement.librarybackend.dto.PasswordResetRequest;
import libraryManagement.librarybackend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password-reset")
public class PasswordResetController {

    @Autowired
    private UserService userService;

    @PostMapping("/request")
    public ResponseEntity<String> requestPasswordReset(@RequestBody String email) {
        if (userService.emailExists(email)) {
            userService.sendPasswordResetToken(email);
            return ResponseEntity.ok("Password reset email sent");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest request) {
        boolean success = userService.resetPassword(request.getToken(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Password reset successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired token");
        }
    }
}
