package artifact.modules.common.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MethodInterceptor {

//    @Around("execution(* *.* (..))")
//    public void NotNullCheck(ProceedingJoinPoint pjp) throws Throwable {
//
//        Object[] args = pjp.getArgs();
//        Signature signature = pjp.getSignature();
//        System.out.println(signature);
//        pjp.proceed(args);
//    }
}
