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
package watchtower.workflow.provider.camunda;

import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.client.JerseyClientConfiguration;
import io.dropwizard.setup.Environment;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.common.event.Event;
import watchtower.common.event.EventUtils;
import watchtower.workflow.configuration.CamundaProviderConfiguration;
import watchtower.workflow.configuration.ProviderConfiguration;
import watchtower.workflow.provider.ProviderInstantiateWorkflowRunnable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class CamundaProviderInstantiateWorkflowRunnable extends ProviderInstantiateWorkflowRunnable {
  private static final Logger logger = LoggerFactory
      .getLogger(CamundaProviderInstantiateWorkflowRunnable.class);

  @Inject
  public CamundaProviderInstantiateWorkflowRunnable(
      @Assisted ProviderConfiguration providerConfiguration, @Assisted Environment environment,
      @Assisted Event event, @Assisted int threadNumber) {
    super(providerConfiguration, environment, event, threadNumber);

    logger.info("Starting up workflow runnable");
  }

  @Override
  public void run() {
    CamundaProviderConfiguration camundaProviderConfiguration =
        (CamundaProviderConfiguration) providerConfiguration;

    JerseyClientConfiguration jerseyClientConfiguration =
        camundaProviderConfiguration.getJerseyClientConfiguration();

    jerseyClientConfiguration.setGzipEnabledForRequests(false);

    Client client =
        new JerseyClientBuilder(environment).using(
            camundaProviderConfiguration.getJerseyClientConfiguration()).build(
            "CamundaWorker" + threadNumber);

    WebTarget target = client.target(camundaProviderConfiguration.getEndpoint());

    Response response =
        target.request(MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE)
            .post(Entity.entity(EventUtils.toJson(event), MediaType.APPLICATION_JSON));

    if (response.getStatus() != 200)
      logger.error("Failed to send event to Camunda with HTTP error code: " + response.getStatus());
    else
      logger.info("Incident: {}", response.readEntity(String.class));
  }
}