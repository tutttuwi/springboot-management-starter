package me.tutttuwi.springboot.management.exception;

import static java.util.Optional.*;
import java.util.Optional;
import org.springframework.validation.Errors;

/**
 * バリデーションエラー.
 */
public class ValidationErrorException extends RuntimeException {

  private static final long serialVersionUID = 5084588189148251787L;

  private Optional<Errors> errors;

  public ValidationErrorException(Errors errors) {
    super();
    this.errors = ofNullable(errors);
  }

  public Optional<Errors> getErrors() {
    return this.errors;
  }
}
