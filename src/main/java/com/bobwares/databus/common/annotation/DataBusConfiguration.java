package com.bobwares.databus.common.annotation;

import java.lang.annotation.*;


@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataBusConfiguration {

	/**
	 * Specifies the databus configuration file to use.
	 */
	String value() default "";
}
