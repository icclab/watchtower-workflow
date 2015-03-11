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
package watchtower.workflow.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import watchtower.workflow.configuration.KafkaConsumerConfiguration;
import watchtower.workflow.configuration.ProviderConfiguration;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;

public class WatchtowerWorkflowConfiguration extends Configuration {
  @NotNull
  private String name;
  
  @NotNull
  private KafkaConsumerConfiguration kafkaConsumerConfiguration;
  
  @NotNull
  private KafkaProducerConfiguration kafkaProducerConfiguration;
  
  @NotNull
  private String workflowProvider;
  
  @NotNull
  private CamundaProviderConfiguration camundaProviderConfiguration;
  
  @Valid
  @NotNull
  private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();
  
  public String getName() {
    return name;
  }
  
  public KafkaConsumerConfiguration getKafkaConsumerConfiguration() {
    return kafkaConsumerConfiguration;
  }
  
  public KafkaProducerConfiguration getKafkaProducerConfiguration() {
    return kafkaProducerConfiguration;
  }
  
  public String getWorkflowProvider() {
    return workflowProvider;
  }
  
  public ProviderConfiguration getWorkflowProviderConfiguration() {
    if (getWorkflowProvider().equalsIgnoreCase("camunda"))
      return getCamundaProviderConfiguration();
    
    return null;
  }
  
  public CamundaProviderConfiguration getCamundaProviderConfiguration() {
    return camundaProviderConfiguration;
  }
  
  public JerseyClientConfiguration getJerseyClientConfiguration() {
    return jerseyClientConfiguration;
  }
}