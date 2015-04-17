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
package watchtower.workflow.provider.runnable;

import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.common.automation.JobExecution;
import watchtower.workflow.configuration.CamundaProviderConfiguration;
import watchtower.workflow.configuration.ProviderConfiguration;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable extends
    ProviderAttachJobExecutionToWorkflowInstanceRunnable {
  private static final Logger logger = LoggerFactory
      .getLogger(CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable.class);

  @Inject
  public CamundaProviderAttachJobExecutionToWorkflowInstanceRunnable(
      @Assisted ProviderConfiguration providerConfiguration, @Assisted String workflowInstanceId,
      @Assisted JobExecution execution, @Assisted int threadNumber) {
    super(providerConfiguration, workflowInstanceId, execution, threadNumber);
    logger.info("Initializing runnable for job execution");
  }

  @Override
  public void run() {
    CamundaProviderConfiguration camundaProviderConfiguration =
        (CamundaProviderConfiguration) providerConfiguration;

    logger.info("Sending job execution back to camunda at {}",
        (camundaProviderConfiguration.getEndpoint() + "/" + workflowInstanceId));

    Client client = Client.create();
    WebResource webResource =
        client.resource(camundaProviderConfiguration.getEndpoint() + "/" + workflowInstanceId);

    ClientResponse response =
        webResource.type(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
            .post(ClientResponse.class, execution);

    logger.info("Got response {}", response);

    if (response.getStatus() != 200) {
      logger.error("Failed to send job execution to Camunda with HTTP error code: "
          + response.getStatus());
    }
  }
}