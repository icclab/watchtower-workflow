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

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;
import watchtower.workflow.consumer.KafkaEventsConsumer;
import watchtower.workflow.consumer.KafkaJobExecutionConsumer;
import watchtower.workflow.health.KafkaHealthCheck;
import watchtower.workflow.provider.Provider;
import watchtower.workflow.resources.JobsResource;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class WatchtowerWorkflowApplication extends Application<WatchtowerWorkflowConfiguration> {

  public static void main(String[] args) throws Exception {
    new WatchtowerWorkflowApplication().run(args);
  }

  @Override
  public void initialize(Bootstrap<WatchtowerWorkflowConfiguration> bootstrap) {

  }

  @Override
  public void run(WatchtowerWorkflowConfiguration configuration, Environment environment)
      throws Exception {
    Injector injector =
        Guice.createInjector(new WatchtowerWorkflowModule(configuration, environment));

    environment.jersey().register(injector.getInstance(JobsResource.class));

    environment.healthChecks().register("kafka-health-check", KafkaHealthCheck.getInstance());

    final Provider provider = injector.getInstance(Provider.class);

    environment.lifecycle().manage(provider);

    final KafkaEventsConsumer kafkaEventsConsumer = injector.getInstance(KafkaEventsConsumer.class);

    environment.lifecycle().manage(kafkaEventsConsumer);

    final KafkaJobExecutionConsumer kafkaJobExecutionConsumer =
        injector.getInstance(KafkaJobExecutionConsumer.class);

    environment.lifecycle().manage(kafkaJobExecutionConsumer);
  }

  @Override
  public String getName() {
    return "watchtower-workflow";
  }
}