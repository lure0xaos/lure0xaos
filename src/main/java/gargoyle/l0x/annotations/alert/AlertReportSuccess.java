package gargoyle.l0x.annotations.alert;

import gargoyle.l0x.data.alert.AlertType;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AlertReportSuccess {
    AlertType type() default AlertType.SUCCESS;

    String message() default "''";

    String description() default "''";

    boolean dismiss() default false;

    boolean raw() default false;
}