package memberservice.member.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect // <- AOP
@Component // -> 컴포넌트 스캔을 통해 스프링 빈을 자동 등록
public class TimeTraceAop {

//    @Around("execution(* memberservice.member.service..*(..))")
//    @Around("execution(* memberservice.member.repository..*(..))")
//    @Around("execution(* memberservice.member.controller..*(..))")
    @Around("execution(* memberservice.member..*(..))") // -> AOP targeting : 해당 패키지의 하위 전부를 타케팅
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        System.out.println("START : " + joinPoint.toString());
        try {

            return joinPoint.proceed();

        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END : " + joinPoint.toString() + " " + timeMs + " ms");
        }

    }

}
