package com.bobwares.databus.bus.config;

import org.springframework.core.PriorityOrdered;
import org.springframework.integration.dsl.config.IntegrationFlowBeanPostProcessor;
import org.springframework.stereotype.Component;


/**
 * NOTE: In order to work around a startup bug involving the IntegrationFlowBeanPostProcessor it has been replaced by
 * this class which adds ordering to the post process so it doesn't stop the processing of other beans.
 * When the problem occurs messages are output to the log stating "[bean-name] is not eligible for getting processed by all BeanPostProcessors"
 * Not sure why ordering fixes this issue. It doesn't seem to matter what the order value is (highest, lowest, or middle).
 *
 * See DslIntegrationConfigurationInitializer.
 */
@Component("org.springframework.integration.dsl.config.IntegrationFlowBeanPostProcessor")
public class OrderedIntegrationFlowBeanPostProcessor extends IntegrationFlowBeanPostProcessor implements PriorityOrdered {

	@Override
	public int getOrder() {
		return 0;
	}

}
