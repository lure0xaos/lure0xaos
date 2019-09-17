package gargoyle.l0x.data.alert;

public enum AlertType {
    ERROR("danger"), WARNING("warning"), INFO("info"), SUCCESS("success");
    private final String cssClass;

    AlertType(String cssClass) {
        this.cssClass = cssClass;
    }

    public String getCssClass() {
        return cssClass;
    }
}
