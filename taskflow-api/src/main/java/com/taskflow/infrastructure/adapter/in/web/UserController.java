package com.taskflow.infrastructure.adapter.in.web;

import com.taskflow.infrastructure.adapter.out.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController // Marks this class as a REST controller
@RequestMapping("/api/v1/users") // Base URL for user-related endpoints
@RequiredArgsConstructor
public class UserController {
  // Testing jwt validation token system
  @GetMapping("/me")
  public ResponseEntity<String> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
    if (userDetails == null){
      return new ResponseEntity<>("No hay usuario authenticado", HttpStatus.UNAUTHORIZED);
    }

    // Returns the email of the authenticated user
    return ResponseEntity.ok("Hola, tu email es: " + userDetails.getUsername());
  }
}
