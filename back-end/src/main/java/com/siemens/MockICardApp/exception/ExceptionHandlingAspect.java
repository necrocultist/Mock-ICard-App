package com.siemens.MockICardApp.exception;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ExceptionHandlingAspect {

    @Pointcut("execution(* com.siemens.MockICardApp.service.EmployeeService.*(..))")
    public void serviceMethods() {}

    @AfterThrowing(pointcut = "serviceMethods()", throwing = "exception")
    public void handleServiceException(Exception exception) {
        // This aspect is optional, and you may choose to remove it
        System.out.println("Exception caught in EmployeeService AOP: " + exception.getMessage());
    }

    @Pointcut("execution(* com.siemens.MockICardApp.service.EmployeeService.deleteEmployee(..)) && args(id)")
    public void deleteEmployeeMethod(String id) {}

    // Remove the existing method for handling deleteEmployeeException

}
