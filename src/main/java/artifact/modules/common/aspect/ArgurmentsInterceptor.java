package artifact.modules.common.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ArgurmentsInterceptor {

    @Around("execution(public * artifact.modules.user.service.impl.UserServiceImpl.* (..))")
    public Object repalceEmptyStringToNull(ProceedingJoinPoint pjp) throws Throwable {
        Object[] args = pjp.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] != null && args[i] instanceof String && String.valueOf(args[i]).trim().length() == 0) {
                args[i] = null;
            }
        }

        return pjp.proceed(args);
    }
}
