package gargoyle.l0x.services.alert;

import gargoyle.l0x.data.alert.Alert;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.context.annotation.ScopedProxyMode.TARGET_CLASS;
import static org.springframework.web.context.WebApplicationContext.SCOPE_SESSION;

@Service(AlertsImpl.BEAN_ID)
@Scope(scopeName = SCOPE_SESSION, proxyMode = TARGET_CLASS)
public class AlertsImpl implements Alerts, Externalizable {
    public static final String BEAN_ID = "alerts";
    private static final long serialVersionUID = -9030112091233091069L;
    private transient List<Alert> alerts;

    public AlertsImpl() {
        alerts = new LinkedList<>();
    }

    @Override
    public List<Alert> getAlerts() {
        return Collections.unmodifiableList(alerts);
    }

    @Override
    public List<Alert> getAlertsOnce() {
        List<Alert> copy = List.copyOf(alerts);
        alerts.clear();
        return copy;
    }

    @Override
    public void alert(Alert alert) {
        alerts.add(alert);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(alerts.toArray(new Alert[0]));
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        alerts = new LinkedList<>((Arrays.asList((Alert[]) in.readObject())));
    }
}
