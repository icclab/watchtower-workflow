/*
 * Copyright 2015 Zurich University of Applied Sciences
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package watchtower.workflow;

import io.dropwizard.setup.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;
import watchtower.workflow.consumer.KafkaEventsConsumer;
import watchtower.workflow.consumer.KafkaEventsConsumerFactory;
import watchtower.workflow.consumer.KafkaEventsConsumerRunnable;
import watchtower.workflow.consumer.KafkaEventsConsumerRunnableFactory;
import watchtower.workflow.consumer.KafkaJobExecutionConsumer;
import watchtower.workflow.consumer.KafkaJobExecutionConsumerFactory;
import watchtower.workflow.consumer.KafkaJobExecutionConsumerRunnable;
import watchtower.workflow.consumer.KafkaJobExecutionConsumerRunnableFactory;
import watchtower.workflow.producer.KafkaProducer;
import watchtower.workflow.producer.KafkaProducerFactory;
import watchtower.workflow.provider.CamundaProvider;
import watchtower.workflow.provider.CamundaProviderFactory;
import watchtower.workflow.provider.Provider;
import watchtower.workflow.provider.runnable.CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable;
import watchtower.workflow.provider.runnable.CamundaProviderAttachJobExecutionToWorkflowInstanceRunnableFactory;
import watchtower.workflow.provider.runnable.CamundaProviderInstantiateWorkflowRunnable;
import watchtower.workflow.provider.runnable.CamundaProviderInstantiateWorkflowRunnableFactory;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;

public class WatchtowerWorkflowModule extends AbstractModule {
  private static final Logger logger = LoggerFactory.getLogger(WatchtowerWorkflowModule.class);

  private final WatchtowerWorkflowConfiguration configuration;
  private final Environment environment;

  public WatchtowerWorkflowModule(WatchtowerWorkflowConfiguration configuration,
      Environment environment) {
    this.configuration = configuration;
    this.environment = environment;
  }

  @Override
  protected void configure() {
    bind(WatchtowerWorkflowConfiguration.class).toInstance(configuration);
    bind(Environment.class).toInstance(environment);

    install(new FactoryModuleBuilder().implement(KafkaProducer.class, KafkaProducer.class).build(
        KafkaProducerFactory.class));

    install(new FactoryModuleBuilder().implement(KafkaEventsConsumerRunnable.class,
        KafkaEventsConsumerRunnable.class).build(KafkaEventsConsumerRunnableFactory.class));

    install(new FactoryModuleBuilder().implement(KafkaEventsConsumer.class,
        KafkaEventsConsumer.class).build(KafkaEventsConsumerFactory.class));

    install(new FactoryModuleBuilder().implement(KafkaJobExecutionConsumerRunnable.class,
        KafkaJobExecutionConsumerRunnable.class).build(
        KafkaJobExecutionConsumerRunnableFactory.class));

    install(new FactoryModuleBuilder().implement(KafkaJobExecutionConsumer.class,
        KafkaJobExecutionConsumer.class).build(KafkaJobExecutionConsumerFactory.class));

    if (configuration.getWorkflowProvider().equalsIgnoreCase("camunda")) {
      install(new FactoryModuleBuilder().implement(
          CamundaProviderInstantiateWorkflowRunnable.class,
          CamundaProviderInstantiateWorkflowRunnable.class).build(
          CamundaProviderInstantiateWorkflowRunnableFactory.class));
      install(new FactoryModuleBuilder().implement(
          CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable.class,
          CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable.class).build(
          CamundaProviderAttachJobExecutionToWorkflowInstanceRunnableFactory.class));
      install(new FactoryModuleBuilder().implement(CamundaProvider.class, CamundaProvider.class)
          .build(CamundaProviderFactory.class));

      bind(Provider.class).to(CamundaProvider.class);
    } else {
      logger
          .debug("Encountered unknown workflow provider: {}", configuration.getWorkflowProvider());

      System.exit(1);
    }
  }
}