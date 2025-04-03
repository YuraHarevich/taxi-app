package com.Harevich.driverservice.proxy;

import com.Harevich.driverservice.client.auth.AuthenticationClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
@Profile("!test")
public class AuthProxy {

    private final AuthenticationClient authenticationClient;

    @Around("execution(* com.Harevich.driverservice.service.DriverService.*(..))")
    public Object aroundServiceMethods(ProceedingJoinPoint pjp) throws Throwable {
        String methodName = pjp.getSignature().getName();
        String methodName = pjp.getArgs();
        log.info("AuthProxy.Authentication proxy is called on method: {}", methodName);
        switch (methodName){
            case "create":
                authenticationClient.registrationOfUser();
        }
        try {
            Object result = pjp.proceed();
            return result;
        } catch (Exception e) {
            log.info("AuthProxy.Exception on method: {}, e.message: {}", methodName, e.getMessage());
            throw e;
        }
    }

}
