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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import io.dropwizard.lifecycle.Managed;
import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;

public abstract class Provider implements Managed {
  private static final Logger logger = LoggerFactory.getLogger(Provider.class);
  
  protected final ProviderConfiguration providerConfiguration;
  private ExecutorService executorService;
  private int numberOfThreads = 0;
  
  @Inject
  public Provider(@Assisted WatchtowerWorkflowConfiguration configuration) {
    this.providerConfiguration = configuration.getCamundaProviderConfiguration();
  }
  
  public void start() throws Exception {
    executorService = Executors.newFixedThreadPool(providerConfiguration.getNumThreads());
  }
  
  public void stop() throws Exception {
    if (executorService != null) {
      executorService.shutdown();
      
      try {
        if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
          executorService.shutdownNow();
          
          if (!executorService.awaitTermination(60, TimeUnit.SECONDS))
            logger.debug("ExecutorService did not terminate");
        }
      } catch (InterruptedException ie) {
        executorService.shutdownNow();
        
        logger.debug("ExecutorService did not terminate", ie);
      }
    }
  }
  
  public void createWorkflowInstance(Event event) {
    if (executorService == null) {
      logger.error("Provider executor service is null, restarting it");
      try {
        start();
      } catch (Exception e) {
        logger.error("Couldn't restart it: {} ", e);
        
        return;
      }
    }
    
    try {
      executorService.submit(createRunnable(providerConfiguration, event, numberOfThreads++));
    } catch (RejectedExecutionException ree) {
      logger.debug("Failed to submit runnable: {}", ree);
    }
  }
  
  protected abstract ProviderCreateWorkflowRunnable createRunnable(ProviderConfiguration providerConfiguration, Event event,
      int threadNumber);
}