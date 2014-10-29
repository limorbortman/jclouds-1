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
import org.jclouds.openstack.ceilometer.v2.domain.Meter;
import org.jclouds.openstack.ceilometer.v2.internal.BaseCeilometerApiMockTest;
import org.jclouds.openstack.ceilometer.v2.options.QueryOptions;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests annotation parsing of {@code MeterApi}
 */
@Test(groups = "unit", testName = "ResourceApiMockTest")
public class MeterApiMockTest extends BaseCeilometerApiMockTest{

   public void testListMeters() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/meters_list_response.json"))));

      try {
         CeilometerApi ceilometerApi = api(server.getUrl("/").toString(), "openstack-ceilometer", overrides);
         MeterApi meterApi = ceilometerApi.getMeterApi("RegionOne");

         List<Meter> meters = meterApi.listMeters();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/meters");

         /*
          * Check response
          */
         assertThat(meters).isNotEmpty();
         assertThat(meters.size()).isEqualTo(650);

         validate(meters);

      } finally {
         server.shutdown();
      }
   }


   public void testListMetersWithQuery() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/meters_list_response.json"))));

      try {
         CeilometerApi ceilometerApi = api(server.getUrl("/").toString(), "openstack-ceilometer", overrides);
         MeterApi meterApi = ceilometerApi.getMeterApi("RegionOne");

         QueryOptions queryOptions = QueryOptions.Builder.field("resource_id").op(QueryOptions.OP.EQ).value("58a63330-b5cd-4bfb-8f92-af6f95482ac3")
               .field("user_id").op(QueryOptions.OP.EQ).value("50d315884a92431a9251ae0824a0312f");
         List<Meter> meters = meterApi.listMeters(queryOptions);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/meters?q.field=resource_id&q.op=eq&q.value=58a63330-b5cd-4bfb-8f92-af6f95482ac3" +
               "&q.field=user_id&q.value=50d315884a92431a9251ae0824a0312f");

         /*
          * Check response
          */
         assertThat(meters).isNotEmpty();
         assertThat(meters.size()).isEqualTo(650);

         validate(meters);

      } finally {
         server.shutdown();
      }
   }

   private void validate(List<Meter> meters) {
      for (Meter meter : meters) {
         assertThat(meter.getMeterId()).isNotEmpty();
         assertThat(meter.getName()).isNotEmpty();
         assertThat(meter.getProjectId()).isNotEmpty();
         assertThat(meter.getResourceId()).isNotEmpty();
         assertThat(meter.getSource()).isNotEmpty();
         assertThat(meter.getType()).isNotEmpty();
         assertThat(meter.getUnit()).isNotEmpty();
         assertThat(meter.getUserId()).isNotEmpty();
      }
   }
}
