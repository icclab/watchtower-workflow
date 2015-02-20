package watchtower.workflow.provider;

import com.google.inject.Inject;

import watchtower.common.event.Event;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;

public class CamundaProvider extends Provider {

  @Inject
  public CamundaProvider(WatchtowerWorkflowConfiguration configuration) {
    super(configuration);
  }

  public void start() throws Exception {

  }

  public void stop() throws Exception {

  }

  @Override
  public void createWorkflowInstance(Event event) {
    
  }
}