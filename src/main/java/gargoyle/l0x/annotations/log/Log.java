package gargoyle.l0x.annotations.log;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({TYPE, METHOD, CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Log {
}
