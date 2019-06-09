package com.stackroute.keepnote.aspect;

import java.time.LocalDateTime;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger log=LoggerFactory.getLogger(LoggingAspect.class);
	
	private static final String LOG_LITERAL = "{} -- {}";
	
	@Pointcut(value="execution(* com.stackroute.keepnote.controller.*.*(..))")
	private void logingDetails() {
		//empty method block for point cut
	}
	
	@Before(value="logingDetails")
	public void beforeLog() {
		log.info(LOG_LITERAL,this.getClass().getEnclosingMethod().getName(),LocalDateTime.now());
	}
	
	@After(value="logingDetails")
	public void afterLog() {
		log.info(LOG_LITERAL,this.getClass().getEnclosingMethod().getName(),LocalDateTime.now());
	}
	
	@AfterThrowing(value="logingDetails")
	public void afterThrowLog() {
		log.info(LOG_LITERAL,this.getClass().getEnclosingMethod().getName(),LocalDateTime.now());
	}
	
	@AfterReturning(value="logingDetails")
	public void afterReturnLog() {
		log.info(LOG_LITERAL,this.getClass().getEnclosingMethod().getName(),LocalDateTime.now());
	}
}
