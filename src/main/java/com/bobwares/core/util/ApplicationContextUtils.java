package com.bobwares.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;


@Component
public class ApplicationContextUtils implements ApplicationContextAware, ApplicationListener<ContextRefreshedEvent> {
	static final Logger logger = LoggerFactory.getLogger(ApplicationContextUtils.class);

    private static volatile boolean refreshed;
	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		setContext(applicationContext);
	}

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        setRefreshed(true);
    }

	public static ApplicationContext getApplicationContext() {
		return refreshed == true ? applicationContext : null;
	}

	@SuppressWarnings("unchecked")
	public static <E> E getBean(String name) {
		if (applicationContext != null) {
			try {
				Object bean = applicationContext.getBean(name);
				if (bean != null) return (E)bean;
			}
			catch (BeansException e) {
				logger.debug("Could not find bean in context {}", name, e);
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <E> E getBean(String name, Class<E> type) {
		Object bean = getBean(name);
		return bean != null && type.isInstance(bean)
			? (E)bean
			: null
		;
	}

	private static void setContext(ApplicationContext context) {
		applicationContext = context;
	}

	private static void setRefreshed(boolean isRefreshed) {
		refreshed = isRefreshed;
	}

}

