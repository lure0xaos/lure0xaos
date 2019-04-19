package gargoyle.l0x.dto.base;

public interface Dto<ID> {
    Class<ID> getIdClass();

    ID getId();

    String getUid();

    void setUid(String uid);
}
