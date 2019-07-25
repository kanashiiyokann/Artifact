package artifact.modules.assets.entity;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @ClassName Assets
 * @Description 资产
 * @Author 孙胜腾
 * @Date 2019/2/21 11:00 AM
 * @Version 1.0
 **/
@Document(collection = "assets")
public class Assets {

    /**
     * 资产编码
     */
    private String number;
    /**
     * 资产名称
     */
    private String name;
    /**
     * 资产类别名字
     */
    private String categoryName;
    /**
     * 资产类别ID
     */
    private Long categoryId;
    /**
     * 使用部门ID
     */
    private Long deptId;
    /**
     * 使用部门名字
     */
    private String deptName;
    /**
     * 已被清理
     */
    private Boolean cleared;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public Boolean getCleared() {
        return cleared;
    }

    public void setCleared(Boolean cleared) {
        this.cleared = cleared;
    }
}
