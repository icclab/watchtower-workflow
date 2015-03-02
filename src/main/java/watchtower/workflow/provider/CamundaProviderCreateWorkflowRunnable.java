package watchtower.workflow.provider;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;

public class CamundaProviderCreateWorkflowRunnable extends ProviderCreateWorkflowRunnable {
  @Inject
  public CamundaProviderCreateWorkflowRunnable(@Assisted ProviderConfiguration providerConfiguration,
      @Assisted Event event) {
    super(providerConfiguration, event);
  }

  @Override
  public void run() {
    
  }
}