package gargoyle.l0x.services.alert;

import gargoyle.l0x.data.alert.Alert;
import gargoyle.l0x.data.alert.AlertSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service(AlertsImpl.BEAN_ID)
@RequiredArgsConstructor
public class AlertsImpl implements Alerts {
    public static final String BEAN_ID = "alerts";
    private final AlertSet alerts;

    @Override
    public Set<Alert> getAlerts() {
        return alerts.getAlerts();
    }

    @Override
    public Set<Alert> getAlertsOnce() {
        return alerts.getAlertsOnce();
    }

    @Override
    public void alert(Alert alert) {
        alerts.alert(alert);
    }

}
