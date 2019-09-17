package gargoyle.l0x.annotations.whitelist;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Whitelist {
    String[] value();
}