package gargoyle.l0x.data.alert;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Component
@Scope(scopeName = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class AlertSet implements Serializable {
    private static final long serialVersionUID = -7835760432254577170L;
    private final Set<Alert> alerts = new LinkedHashSet<>(1);

    public Set<Alert> getAlerts() {
        return Collections.unmodifiableSet(alerts);
    }

    public Set<Alert> getAlertsOnce() {
        Set<Alert> copy = new LinkedHashSet<>(alerts);
        alerts.clear();
        return copy;
    }

    public void alert(Alert alert) {
        alerts.add(alert);
    }
}
