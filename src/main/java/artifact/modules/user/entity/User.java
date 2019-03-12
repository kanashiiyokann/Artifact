package artifact.modules.user.entity;

import artifact.modules.common.entity.BaseEntity;
import artifact.modules.user.constant.UserState;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "User")
public class User extends BaseEntity implements UserState {
    private String name;
    private String pwd;
    private Integer age;
    private Integer state;

    public Integer getState() {
        return state;
    }
    public void setState(Integer state) {
        this.state = state;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
