package gargoyle.l0x.entities.base;

import javax.persistence.Transient;

public interface IdEntity<ID> {
    @Transient
    Class<ID> getIdClass();

    ID getId();

    String getUid();

    void setUid(String uid);
}
