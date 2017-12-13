/*
 * Power by www.xiaoi.com
 */
package com.eastrobot.aop;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:eko.z@outlook.com">eko.zhan</a>
 * @date 2017年12月13日 下午4:03:42
 * @version 1.0
 */
@Aspect
@Component
public class IntervalAspect {
	
	private Log logger = LogFactory.getLog(IntervalAspect.class);
	
	@Around("@annotation(com.eastrobot.annotation.Interval)")
	public Object logIntervalCallback(ProceedingJoinPoint joinPoint) throws Throwable{
		
		Calendar cal1 = Calendar.getInstance();
		Object proceed = joinPoint.proceed();
		Calendar cal2 = Calendar.getInstance();
		
		logger.info(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "() during " + (cal2.getTimeInMillis() - cal1.getTimeInMillis()) + " ms");
		
		return proceed;
	}
	
}
