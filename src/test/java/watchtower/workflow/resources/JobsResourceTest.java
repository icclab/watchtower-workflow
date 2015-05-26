package watchtower.workflow.resources;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import io.dropwizard.testing.junit.ResourceTestRule;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import watchtower.common.automation.Job;
import watchtower.common.automation.JobUtils;
import watchtower.workflow.producer.KafkaProducer;

@RunWith(MockitoJUnitRunner.class)
public class JobsResourceTest {
  public static KafkaProducer kafkaProducer = mock(KafkaProducer.class);

  @ClassRule
  public static final ResourceTestRule resources = ResourceTestRule.builder()
      .addResource(new JobsResource(kafkaProducer)).build();

  @Rule
  public ExpectedException exception = ExpectedException.none();

  @Test
  public void shouldCreate() {
    Job job = new Job();

    Response response =
        resources.client().target("/v1.0/jobs").request()
            .post(Entity.entity(JobUtils.toJson(job), MediaType.APPLICATION_JSON));

    assertEquals(200, response.getStatus());
  }

  @Test
  public void shouldNotCreate() {
    exception.expect(ProcessingException.class);

    resources.client().target("/v1.0/jobs").request(MediaType.APPLICATION_JSON_TYPE)
        .post(Entity.entity("StringWhichCausesProcessingException", MediaType.APPLICATION_JSON));
  }
}