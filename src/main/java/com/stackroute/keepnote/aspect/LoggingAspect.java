package com.stackroute.keepnote.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/* Annotate this class with @Aspect and @Component */

@Component
@Aspect
public class LoggingAspect {

	/*
	 * Write loggers for each of the methods of controller, any particular method
	 * will have all the four aspectJ annotation
	 * (@Before, @After, @AfterReturning, @AfterThrowing).
	 */

	@Pointcut("execution(* com.stackroute.keepnote.controller.*.*(..))")
	private void logingDetails() {
		// empty method block for point cut
	}

	@Before(value = "logingDetails()")
	public void beforeLog() {
		System.out.println(this.getClass().getEnclosingMethod() + " -- " + LocalDateTime.now());

	}

	@After(value = "logingDetails()")
	public void afterLog() {
		System.out.println(this.getClass().getEnclosingMethod() + " -- " + LocalDateTime.now());
	}

	@AfterThrowing(value = "logingDetails()")
	public void afterThrowLog() {
		System.out.println(this.getClass().getEnclosingMethod() + " -- " + LocalDateTime.now());
	}

	@AfterReturning(value = "logingDetails()")
	public void afterReturnLog() {
		System.out.println(this.getClass().getEnclosingMethod() + " -- " + LocalDateTime.now());
	}
}
