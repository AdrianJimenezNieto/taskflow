package com.taskflow.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserRequest {
  
  // Nottations for validation that review the incoming data
  @NotBlank(message = "El nombre de usuario no puede estar vacío.")
  @Size(min = 3, max = 20, message = "El nombre de usuario debe tener entre 3 y 20 caracteres.")
  private String userName;

  @NotBlank(message = "El nombre no puede estar vacío.")
  private String name;

  @NotBlank(message = "El apellido no puede estar vacío.")
  private String lastName;

  @NotBlank(message = "El email no puede estar vacío.")
  @Email(message = "El email no es válido.")
  private String email;

  @NotBlank(message = "La contraseña no puede estar vacía.")
  @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres.")
  private String password;
}
