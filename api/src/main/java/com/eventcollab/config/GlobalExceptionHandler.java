package com.eventcollab.config;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.eventcollab.global.dto.ApiResponse;
import com.eventcollab.global.exception.NotAuthorizedToAccessResourceException;
import com.eventcollab.global.exception.ResourceNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiResponse> handleNotFound(ResourceNotFoundException ex) {
    ApiResponse response = ApiResponse.builder()
                            .success(false)
                            .message(ex.getMessage())
                            .build();
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
  }

  public ResponseEntity<ApiResponse> handleNotAuthorizedToAccessResource(NotAuthorizedToAccessResourceException ex) {
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new ApiResponse(false, ex.getMessage()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ApiResponse> handleIllegalState(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ApiResponse(false, ex.getMessage()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ApiResponse> handleValidation(MethodArgumentNotValidException ex) {
    String message = ex.getBindingResult().getFieldErrors().stream()
                      .map(fe -> fe.getField() + " " + fe.getDefaultMessage())
                      .collect(Collectors.joining(", "));
    return ResponseEntity.badRequest().body(new ApiResponse(false, message));
  }

  public ResponseEntity<ApiResponse> handleOther(Exception ex) {
    ex.printStackTrace();
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(false, "Server Error"));
  }
}
