package artifact.modules.common.entity;

public enum LogType {
    CREATE_ACCOUNT("01","创建账套","创建账套，账期%s。") ;
    private String code;
    private String template;
    private String type;

    LogType(String code, String type, String template) {
        this.code = code;
        this.template = template;
        this.type = type;
    }

    public String getTemplate() {
        return template;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
