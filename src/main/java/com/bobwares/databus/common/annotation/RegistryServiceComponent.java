package com.bobwares.databus.common.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface RegistryServiceComponent {

	/**
	 * Specifies that the bean is a RegistryService Component.
	 */
	String value() default "";
}
