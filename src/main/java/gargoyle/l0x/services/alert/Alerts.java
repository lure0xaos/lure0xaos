package gargoyle.l0x.services.alert;

import gargoyle.l0x.data.alert.Alert;
import gargoyle.l0x.data.alert.AlertType;

import java.util.List;

import static gargoyle.l0x.data.alert.AlertType.*;

public interface Alerts {
    List<Alert> getAlerts();

    List<Alert> getAlertsOnce();

    void alert(Alert alert);

    default void alert(AlertType type, String message) {
        alert(new Alert(type, message));
    }

    default void alert(AlertType type, String message, String description) {
        alert(new Alert(type, message, description));
    }

    default void alert(AlertType type, String message, String description, boolean dismiss) {
        alert(new Alert(type, message, description, dismiss));
    }

    default void alert(AlertType type, String message, String description, boolean dismiss, boolean raw) {
        alert(new Alert(type, message, description, dismiss, raw));
    }

    default void error(String message) {
        alert(new Alert(ERROR, message));
    }

    default void error(String message, String description) {
        alert(new Alert(ERROR, message, description));
    }

    default void error(String message, String description, boolean dismiss) {
        alert(new Alert(ERROR, message, description, dismiss));
    }

    default void error(String message, String description, boolean dismiss, boolean raw) {
        alert(new Alert(ERROR, message, description, dismiss, raw));
    }

    default void warning(String message) {
        alert(new Alert(WARNING, message));
    }

    default void warning(String message, String description) {
        alert(new Alert(WARNING, message, description));
    }

    default void warning(String message, String description, boolean dismiss) {
        alert(new Alert(WARNING, message, description, dismiss));
    }

    default void warning(String message, String description, boolean dismiss, boolean raw) {
        alert(new Alert(WARNING, message, description, dismiss, raw));
    }

    default void info(String message) {
        alert(new Alert(INFO, message));
    }

    default void info(String message, String description) {
        alert(new Alert(INFO, message, description));
    }

    default void info(String message, String description, boolean dismiss) {
        alert(new Alert(INFO, message, description, dismiss));
    }

    default void info(String message, String description, boolean dismiss, boolean raw) {
        alert(new Alert(INFO, message, description, dismiss, raw));
    }

    default void success(String message) {
        alert(new Alert(SUCCESS, message));
    }

    default void success(String message, String description) {
        alert(new Alert(SUCCESS, message, description));
    }

    default void success(String message, String description, boolean dismiss) {
        alert(new Alert(SUCCESS, message, description, dismiss));
    }

    default void success(String message, String description, boolean dismiss, boolean raw) {
        alert(new Alert(SUCCESS, message, description, dismiss, raw));
    }
}
