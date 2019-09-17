package gargoyle.l0x.model.base;

public interface Model<ID> {
    Class<ID> getIdClass();

    ID getId();

    String getUid();

    void setUid(String uid);
}
