package me.tutttuwi.springboot.management.annotaion;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;

/**
 * リマインドパスワードチェック用アノテーション.
 *
 * @author Tomo
 *
 */
@Documented
@Constraint(validatedBy = {CheckForgotPasswordRequirementValidator.class})
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@ReportAsSingleViolation
public @interface CheckForgotPasswordRequirement {

  /**
   * default mechod {@code message}.
   *
   * @return
   */
  String message() default "{validation.CheckForgotPasswordRequirement.message}";

  /**
   * default mechod {@code groups}.
   *
   * @return
   */
  Class<?>[] groups() default {};

  /**
   * default mechod {@code payload}.
   *
   * @return
   */
  Class<? extends Payload>[] payload() default {};

  /**
   * interface {@code List}.
   *
   * @author Tomo
   *
   */
  @Target({TYPE, ANNOTATION_TYPE})
  @Retention(RUNTIME)
  @Documented
  public @interface List {
    /**
     * mechod {@code value}.
     *
     * @return
     */
    CheckForgotPasswordRequirement[] value();
  }

  /**
   * mechod {@code userIdProperty}.
   *
   * @return
   */
  String userIdProperty();

  /**
   * mechod {@code emailProperty}.
   *
   * @return
   */
  String emailProperty();

  /**
   * mechod {@code correlationErrorProperty}.
   *
   * @return
   */
  String correlationErrorProperty();

}
