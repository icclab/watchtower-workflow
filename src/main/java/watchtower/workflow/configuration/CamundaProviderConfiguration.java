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

import io.dropwizard.client.JerseyClientConfiguration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class CamundaProviderConfiguration extends ProviderConfiguration {
  @NotEmpty
  private String endpoint;

  private JerseyClientConfiguration jerseyClientConfiguration = new JerseyClientConfiguration();

  @Valid
  @NotNull
  public String getEndpoint() {
    return endpoint;
  }

  public JerseyClientConfiguration getJerseyClientConfiguration() {
    return jerseyClientConfiguration;
  }

  @Override
  public String getProviderName() {
    return "camunda";
  }
}