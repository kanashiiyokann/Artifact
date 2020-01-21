package artifact.modules.user.entity;

import artifact.modules.common.entity.BaseEntity;

public class Log extends BaseEntity {
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
