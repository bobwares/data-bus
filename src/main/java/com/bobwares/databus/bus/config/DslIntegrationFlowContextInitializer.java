package com.bobwares.databus.bus.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.integration.config.IntegrationConfigurationInitializer;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.util.Assert;

import java.beans.Introspector;


/**
 * NOTE: This class is used to initialize the IntegrationFlowContext bean.
 * This is normally done in the DslIntegrationConfigurationInitializer, but due to a "bug" it won't happen if the
 * IntegrationFlowBeanPostProcessor is already defined in the bean registry.
 *
 * In order to work around a startup bug involving the IntegrationFlowBeanPostProcessor it has been replaced by
 * the OrderedIntegrationFlowBeanPostProcessor. Since the post processor bean already exist, then the
 * IntegrationFlowContext won't be created by the regular spring code.
 *
 * See DslIntegrationConfigurationInitializer.
 */
public class DslIntegrationFlowContextInitializer implements IntegrationConfigurationInitializer {

	private static final String INTEGRATION_FLOW_CONTEXT_BEAN_NAME =
			Introspector.decapitalize(IntegrationFlowContext.class.getName());

	@Override
	public void initialize(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
		Assert.isInstanceOf(BeanDefinitionRegistry.class, configurableListableBeanFactory,
			"To use Spring Integration Java DSL the 'beanFactory' has to be an instance of " +
			"'BeanDefinitionRegistry'. Consider using 'GenericApplicationContext' implementation."
		);

		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) configurableListableBeanFactory;
		if (!registry.containsBeanDefinition(INTEGRATION_FLOW_CONTEXT_BEAN_NAME)) {
			registry.registerBeanDefinition(INTEGRATION_FLOW_CONTEXT_BEAN_NAME, new RootBeanDefinition(IntegrationFlowContext.class));
		}
	}

}
