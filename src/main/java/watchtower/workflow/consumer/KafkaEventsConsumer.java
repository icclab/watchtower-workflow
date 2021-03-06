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
package watchtower.workflow.consumer;

import kafka.consumer.KafkaStream;
import watchtower.common.event.Event;
import watchtower.workflow.configuration.WatchtowerWorkflowConfiguration;
import watchtower.workflow.provider.Provider;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class KafkaEventsConsumer extends KafkaConsumer<Event> {
  @Inject
  private KafkaEventsConsumerRunnableFactory eventsConsumerRunnableFactory;

  @Inject
  public KafkaEventsConsumer(WatchtowerWorkflowConfiguration configuration, Provider provider) {
    super(configuration, provider);
  }

  @Override
  protected KafkaConsumerRunnable<Event> createRunnable(KafkaStream<byte[], byte[]> stream,
      int threadNumber) {
    return eventsConsumerRunnableFactory.create(stream, threadNumber, provider);
  }

  @Override
  protected String getTopic() {
    return kafkaConfiguration.getEventTopic();
  }

  @Override
  protected String getConsumerId() {
    return kafkaConfiguration.getEventConsumerId();
  }
}