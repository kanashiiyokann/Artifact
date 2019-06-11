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
    private UserType type;

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

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


    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                ", type=" + type +
                '}';
    }

    /**
     * 用户类型常量
     */
    public enum UserType {
        /**
         * 管理员
         */
        admin(3, "管理员"),
        /**
         * 普通用户
         */
        common(1, "普通用户");

        private String text;

        UserType(int ordinal, String text) {

            this.text = text;
        }

        public String text() {
            return text;
        }

        private void setOrdinal(int ordinal) {

        }

    }

}
