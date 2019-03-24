package artifact.modules.item.entity;


import artifact.modules.common.entity.BaseEntity;

public class Item extends BaseEntity{
    private String name;
    private String note;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
