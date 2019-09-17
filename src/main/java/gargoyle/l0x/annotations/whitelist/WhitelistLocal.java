package gargoyle.l0x.annotations.whitelist;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Whitelist({WhitelistLocal.IPV4, WhitelistLocal.IPV6})
public @interface WhitelistLocal {
    String IPV4 = "127.0.0.1";
    String IPV6 = "0:0:0:0:0:0:0:1";
}
