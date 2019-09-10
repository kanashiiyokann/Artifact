package artifact.exception;

import org.springframework.beans.factory.annotation.Value;

public class BusinessException extends Throwable {

    @Value("${environment.mode:online}")
    private static String mode;

    /**
     * @param message            异常的描述信息
     * @param cause              导致此异常发生的父异常
     * @param enableSuppression  异常挂起的参数
     * @param writableStackTrace 是否生成栈追踪信息
     */
    protected BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression,"dev".equals(mode));
    }
}
