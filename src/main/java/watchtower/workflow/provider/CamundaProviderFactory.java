package watchtower.workflow.provider;

import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;

public interface CamundaProviderFactory {
  CamundaProvider create(WatchtowerWorkflowConfiguration configuration);
}
