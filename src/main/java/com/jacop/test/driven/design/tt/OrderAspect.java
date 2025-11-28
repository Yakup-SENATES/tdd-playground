package com.jacop.test.driven.design.tt;

import com.jacop.test.driven.design.practise.newGen.model.PlaceOrderRequest;
import com.jacop.test.driven.design.practise.newGen.service.stock.StockContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class OrderAspect {
    private final StockContext stockContext;

    public OrderAspect(StockContext stockContext) {
        this.stockContext = stockContext;
    }

    //@Pointcut("execution(public * * (..)) && @within(org.springframework.stereotype.Service)")
    @Pointcut("execution(* com.jacop.test.driven.design.practise.newGen.OrderService.placeOrder(..))")
    public void serviceMethods() {}


    //@Before("execution(public * * (..)) && @within(org.springframework.stereotype.Service)")
    @Before("serviceMethods()")
    public void checkStock(JoinPoint joinPoint) {
        PlaceOrderRequest placeOrderRequest = PlaceOrderRequest.builder().build();
        String methodName = joinPoint.getSignature().toShortString();
        Object[] args = joinPoint.getArgs();
        log.info("[LOG] Calling method: {}", methodName);
        if (args.length > 0) {
            System.out.println("[LOG] Arguments: ");
            for (Object arg : args) {
                if (arg instanceof PlaceOrderRequest p) {
                    placeOrderRequest = p;
                }
                System.out.println(" -> " + arg);
            }
        }
        String stockStatus = stockContext.checkStock(placeOrderRequest.getProductType());
        log.info("[LOG] Stock Status: {}", stockStatus);
    }

    @Around(value = "serviceMethods()")
    public Object measureExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        long start = System.nanoTime();
        Object proceed = proceedingJoinPoint.proceed();
        long end = System.nanoTime();
        double seconds = (end - start) / 1_000_000_000.0;
        log.info("[PERF] Method {} executed in {} seconds", proceedingJoinPoint.getSignature().toShortString(), seconds);
        return proceed;
    }

}
