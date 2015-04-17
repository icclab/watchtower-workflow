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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import watchtower.common.automation.JobExecution;
import watchtower.common.automation.JobExecutionUtils;
import watchtower.workflow.provider.Provider;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public class KafkaJobExecutionConsumerRunnable extends KafkaConsumerRunnable<JobExecution> {
  private static final Logger logger = LoggerFactory
      .getLogger(KafkaJobExecutionConsumerRunnable.class);

  @Inject
  public KafkaJobExecutionConsumerRunnable(@Assisted KafkaStream<byte[], byte[]> stream,
      @Assisted int threadNumber, Provider provider) {
    super(stream, threadNumber, provider);
  }

  @Override
  protected void consumeMessage(byte[] key, byte[] message) {
    try {
      final JobExecution execution = JobExecutionUtils.fromJson(message);

      logger.info("Received {}", execution);

      provider.attachJobExecutionToWorkflowInstance(new String(key), execution);
    } catch (Exception e) {
      logger.error("Failed to deserialize JSON message: " + message, e);
    }
  }
}