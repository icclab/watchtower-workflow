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

import io.dropwizard.setup.Environment;
import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;

public abstract class ProviderInstantiateWorkflowRunnable implements Runnable {
  protected final ProviderConfiguration providerConfiguration;
  protected final Environment environment;
  protected final Event event;
  protected final int threadNumber;

  public ProviderInstantiateWorkflowRunnable(ProviderConfiguration providerConfiguration,
      Environment environment, Event event, int threadNumber) {
    this.providerConfiguration = providerConfiguration;
    this.environment = environment;
    this.event = event;
    this.threadNumber = threadNumber;
  }
}