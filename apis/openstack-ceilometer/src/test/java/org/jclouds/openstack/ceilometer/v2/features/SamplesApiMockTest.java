/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jclouds.openstack.ceilometer.v2.features;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.jclouds.openstack.ceilometer.v2.CeilometerApi;
import org.jclouds.openstack.ceilometer.v2.domain.Sample;
import org.jclouds.openstack.ceilometer.v2.internal.BaseCeilometerApiMockTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests annotation parsing of {@code MetersApi}
 */
@Test(groups = "unit", testName = "ResourceApiMockTest")
public class SamplesApiMockTest extends BaseCeilometerApiMockTest{

   public void testListSamples() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/samples_list_response.json"))));

      try {
         CeilometerApi ceilometerApi = api(server.getUrl("/").toString(), "openstack-ceilometer", overrides);
         QueryApi queryApi = ceilometerApi.getQueryApi("RegionOne");

         List<Sample> samples = queryApi.listSamples();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/query/samples");

         /*
          * Check response
          */
         assertThat(samples).isNotEmpty();
         assertThat(samples.size()).isEqualTo(10);

         validate(samples);

      } finally {
         server.shutdown();
      }
   }

   public void testListSamplesWithOptions() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/samples_list_response.json"))));

      try {
         CeilometerApi ceilometerApi = api(server.getUrl("/").toString(), "openstack-ceilometer", overrides);
         QueryApi queryApi = ceilometerApi.getQueryApi("RegionOne");

         List<Sample> samples = queryApi.listSamples(10);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/query/samples");

         /*
          * Check response
          */
         assertThat(samples).isNotEmpty();
         assertThat(samples.size()).isEqualTo(10);

         validate(samples);

      } finally {
         server.shutdown();
      }
   }

   private void validate(List<Sample> samples) {
      for (Sample sample : samples) {
         assertThat(sample.getId()).isNotEmpty();
         assertThat(sample.getMetadata()).isNotEmpty();
         assertThat(sample.getMeter()).isNotEmpty();
         assertThat(sample.getProjectId()).isNotEmpty();
         assertThat(sample.getRecordedAt()).isNotEmpty();
         assertThat(sample.getResourceId()).isNotEmpty();
         assertThat(sample.getSource()).isNotEmpty();
         assertThat(sample.getTimestamp()).isNotEmpty();
         assertThat(sample.getType()).isNotEmpty();
         assertThat(sample.getUnit()).isNotEmpty();
         assertThat(sample.getUserId()).isNotEmpty();
         assertThat(sample.getVolume()).isNotEmpty();
      }
   }
}
