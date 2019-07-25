package gargoyle.l0x.annotations.alert;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface AlertReport {
    AlertReportSuccess success() default @AlertReportSuccess();

    AlertReportFailure failure() default @AlertReportFailure();
}