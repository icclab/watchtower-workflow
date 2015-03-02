package watchtower.workflow.provider;

import watchtower.common.event.Event;
import watchtower.workflow.configuration.ProviderConfiguration;

public interface CamundaProviderCreateWorkflowRunnableFactory {
  CamundaProviderCreateWorkflowRunnable create(ProviderConfiguration providerConfiguration, Event event);
}