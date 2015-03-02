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
package watchtower.workflow.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.common.automation.Command;
import watchtower.common.automation.CommandType;
import watchtower.common.automation.Job;
import watchtower.workflow.producer.KafkaProducer;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;

@Path("/v1.0/jobs")
public class AutomationResource {
  private static final Logger logger = LoggerFactory.getLogger(AutomationResource.class);
      
  private KafkaProducer kafkaProducer;
  
  @Inject
  public AutomationResource(KafkaProducer kafkaProducer) {
    this.kafkaProducer = kafkaProducer;
  }
  
  @POST
  @Timed
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response create(@Context UriInfo uriInfo, Job job) {
    logger.info("Received {}", job);
    kafkaProducer.send(new Command(CommandType.CREATE_JOB, job));
    return Response.ok().build();
  }
}