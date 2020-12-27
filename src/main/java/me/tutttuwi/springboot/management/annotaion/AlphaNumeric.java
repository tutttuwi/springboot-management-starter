package me.tutttuwi.springboot.management.annotaion;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

/**
 * 英数字チェック用アノテーション.
 * 
 * @author Tomo
 *
 */
@Documented
@Constraint(validatedBy = {})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@ReportAsSingleViolation
@Pattern(regexp = "[a-zA-Z0-9]*")
public @interface AlphaNumeric {

  /**
   * default mechod {@code message}.
   *
   * @return
   */
  String message() default "{validation.AlphaNumeric.message}";

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
  @Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
  @Retention(RUNTIME)
  @Documented
  public @interface List {
    /**
     * mechod {@code value}.
     *
     * @return
     */
    AlphaNumeric[] value();
  }

}
