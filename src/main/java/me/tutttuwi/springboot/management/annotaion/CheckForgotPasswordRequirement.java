package me.tutttuwi.springboot.management.annotaion;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

@Documented
@Constraint(validatedBy = { CheckForgotPasswordRequirementValidator.class })
@Target({ TYPE, ANNOTATION_TYPE })
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface CheckForgotPasswordRequirement {

  String message() default "{validation.CheckForgotPasswordRequirement.message}";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  @Target({ TYPE, ANNOTATION_TYPE })
  @Retention(RUNTIME)
  @Documented
  public @interface List {
    CheckForgotPasswordRequirement[] value();
  }

  String userIdProperty();

  String emailProperty();

  String correlationErrorProperty();

}
