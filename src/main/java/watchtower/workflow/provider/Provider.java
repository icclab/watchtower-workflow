package watchtower.workflow.provider;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import io.dropwizard.lifecycle.Managed;
import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;

public abstract class Provider implements Managed {
  protected final ProviderConfiguration providerConfiguration;
  
  @Inject
  public Provider(@Assisted WatchtowerWorkflowConfiguration configuration) {
    this.providerConfiguration = configuration.getCamundaProviderConfiguration();
  }
  
  public abstract void createWorkflowInstance(Event event);
}