package artifact.common;

import io.swagger.annotations.ApiModel;

@ApiModel("返回对象")
public class RestResponse<T> {
    private T data;
    private int code;
    private String message;
    //
    protected static int SUCCESS=1;
    protected static int FAILED=0;

    public RestResponse(int code,T data,String message){
        this.code=code;
        this.data=data;
        this.message=message;
    }
    private RestResponse(){}

    public static  <T> RestResponse<T> success(T data){
        RestResponse<T> rr=new RestResponse<>();
        rr.data=data;
        rr.code=SUCCESS;

        return rr;
    }

    public static  <T> RestResponse<T> failed(String  message){
        RestResponse<T> rr=new RestResponse<>();
        rr.message=message;
        rr.code=FAILED;

        return rr;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
