/*
 * MIT License
 *
 * Copyright (c) 2020 Airbyte
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.airbyte.server;

import io.airbyte.commons.io.FileTtlManager;
import io.airbyte.config.Configs;
import io.airbyte.config.persistence.ConfigPersistence;
import io.airbyte.config.persistence.ConfigRepository;
import io.airbyte.db.Database;
import io.airbyte.scheduler.client.CachingSynchronousSchedulerClient;
import io.airbyte.scheduler.client.SchedulerJobClient;
import io.airbyte.scheduler.persistence.JobPersistence;
import io.airbyte.server.apis.ConfigurationApi;
import io.temporal.serviceclient.WorkflowServiceStubs;
import java.util.Map;
import org.glassfish.hk2.api.Factory;
import org.slf4j.MDC;

public class ConfigurationApiFactory implements Factory<ConfigurationApi> {

  private static WorkflowServiceStubs temporalService;
  private static ConfigRepository configRepository;
  private static JobPersistence jobPersistence;
  private static ConfigPersistence seed;
  private static SchedulerJobClient schedulerJobClient;
  private static CachingSynchronousSchedulerClient synchronousSchedulerClient;
  private static Configs configs;
  private static FileTtlManager archiveTtlManager;
  private static Map<String, String> mdc;
  private static Database configsDatabase;
  private static Database jobsDatabase;

  public static void setValues(
                               final WorkflowServiceStubs temporalService,
                               final ConfigRepository configRepository,
                               final JobPersistence jobPersistence,
                               final ConfigPersistence seed,
                               final SchedulerJobClient schedulerJobClient,
                               final CachingSynchronousSchedulerClient synchronousSchedulerClient,
                               final Configs configs,
                               final FileTtlManager archiveTtlManager,
                               final Map<String, String> mdc,
                               final Database configsDatabase,
                               final Database jobsDatabase) {
    ConfigurationApiFactory.configRepository = configRepository;
    ConfigurationApiFactory.jobPersistence = jobPersistence;
    ConfigurationApiFactory.seed = seed;
    ConfigurationApiFactory.schedulerJobClient = schedulerJobClient;
    ConfigurationApiFactory.synchronousSchedulerClient = synchronousSchedulerClient;
    ConfigurationApiFactory.configs = configs;
    ConfigurationApiFactory.archiveTtlManager = archiveTtlManager;
    ConfigurationApiFactory.mdc = mdc;
    ConfigurationApiFactory.temporalService = temporalService;
    ConfigurationApiFactory.configsDatabase = configsDatabase;
    ConfigurationApiFactory.jobsDatabase = jobsDatabase;
  }

  @Override
  public ConfigurationApi provide() {
    MDC.setContextMap(ConfigurationApiFactory.mdc);

    return new ConfigurationApi(
        ConfigurationApiFactory.configRepository,
        ConfigurationApiFactory.jobPersistence,
        ConfigurationApiFactory.seed,
        ConfigurationApiFactory.schedulerJobClient,
        ConfigurationApiFactory.synchronousSchedulerClient,
        ConfigurationApiFactory.configs,
        ConfigurationApiFactory.archiveTtlManager,
        ConfigurationApiFactory.temporalService,
        ConfigurationApiFactory.configsDatabase,
        ConfigurationApiFactory.jobsDatabase);
  }

  @Override
  public void dispose(final ConfigurationApi service) {
    /* noop */
  }

}
