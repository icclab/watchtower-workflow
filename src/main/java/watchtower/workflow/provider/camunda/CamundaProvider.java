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

import io.dropwizard.setup.Environment;
import watchtower.common.automation.JobExecution;
import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;
import watchtower.workflow.provider.Provider;
import watchtower.workflow.provider.ProviderAttachJobExecutionToWorkflowInstanceRunnable;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class CamundaProvider extends Provider {
  @Inject
  private CamundaProviderInstantiateWorkflowRunnableFactory camundaProviderInstantiateWorkflowRunnableFactory;

  @Inject
  private CamundaProviderAttachJobExecutionToWorkflowInstanceRunnableFactory camundaProviderAttachJobExecutionToWorkflowInstanceRunnableFactory;

  @Inject
  public CamundaProvider(WatchtowerWorkflowConfiguration configuration, Environment environment) {
    super(configuration, environment);
  }

  @Override
  protected CamundaProviderInstantiateWorkflowRunnable createInstantiateWorkflowRunnable(
      ProviderConfiguration providerConfiguration, Event event, int threadNumber) {
    return camundaProviderInstantiateWorkflowRunnableFactory.create(providerConfiguration,
        environment, event, threadNumber);
  }

  @Override
  protected ProviderAttachJobExecutionToWorkflowInstanceRunnable attachJobExecutionToWorkflowInstanceRunnable(
      ProviderConfiguration providerConfiguration, String workflowInstanceId,
      JobExecution execution, int threadNumber) {
    return camundaProviderAttachJobExecutionToWorkflowInstanceRunnableFactory.create(
        providerConfiguration, environment, workflowInstanceId, execution, threadNumber);
  }
}