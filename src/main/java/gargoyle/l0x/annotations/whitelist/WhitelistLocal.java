package gargoyle.l0x.annotations.whitelist;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Whitelist({"127.0.0.1", "0:0:0:0:0:0:0:1"})
public @interface WhitelistLocal {
}