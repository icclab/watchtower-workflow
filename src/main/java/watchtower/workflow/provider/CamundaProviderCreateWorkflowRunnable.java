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
package watchtower.workflow.provider;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import watchtower.common.event.Event;
import watchtower.common.incident.Incident;
import watchtower.workflow.configuration.CamundaProviderConfiguration;
import watchtower.workflow.configuration.ProviderConfiguration;

public class CamundaProviderCreateWorkflowRunnable extends ProviderCreateWorkflowRunnable {
  private static final Logger logger = LoggerFactory.getLogger(CamundaProviderCreateWorkflowRunnable.class);
  
  @Inject
  public CamundaProviderCreateWorkflowRunnable(
      @Assisted ProviderConfiguration providerConfiguration, @Assisted Event event, @Assisted int threadNumber) {
    super(providerConfiguration, event, threadNumber);
  }

  @Override
  public void run() {
    CamundaProviderConfiguration camundaProviderConfiguration = (CamundaProviderConfiguration) providerConfiguration;

    Client client = Client.create();
    WebResource webResource = client.resource(camundaProviderConfiguration.getEndpoint());

    ClientResponse response =
        webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .post(ClientResponse.class, event);

    logger.info("Got response {}", response);
    logger.info("Created {}", response.getEntity(Incident.class));
    
    if (response.getStatus() != 200) {
      logger.error("Failed to send event to Camunda with HTTP error code: " + response.getStatus());
    }
  }
}