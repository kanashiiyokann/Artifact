package artifact.modules.common.entity;

import java.util.Date;
import java.util.Optional;

public class BaseEntity {
    private Long id;
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return Optional.ofNullable(createTime).map(d->(Date)d.clone()).orElse(null);
    }

    public void setCreateTime(Date createTime) {
        this.createTime = (Date)createTime.clone();
    }
}
