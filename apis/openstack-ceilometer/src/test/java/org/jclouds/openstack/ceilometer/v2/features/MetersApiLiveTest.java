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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.jclouds.openstack.ceilometer.v2.domain.Meter;
import org.jclouds.openstack.ceilometer.v2.internal.BaseCeilometerApiLiveTest;
import org.jclouds.openstack.ceilometer.v2.options.QueryOptions;
import org.testng.annotations.Test;

/**
 * Tests parsing and Guice wiring of MetersApi
 */
@Test(groups = "live", testName = "MetersApiLiveTest")
public class MetersApiLiveTest extends BaseCeilometerApiLiveTest {

   public void testListMeters() {
      for (String region : regions) {
         MetersApi metersApi = api.getMetersApi(region);

         List<Meter> meters = metersApi.listMeters();
         assertThat(meters).isNotNull();
         assertThat(meters).isNotEmpty();

         validate(meters);
      }
   }

   public void testListMetersWithQuery() {
      for (String region : regions) {
         MetersApi metersApi = api.getMetersApi(region);

         QueryOptions queryOptions = QueryOptions.Builder.field("resource_id").op(QueryOptions.OP.EQ).value("58a63330-b5cd-4bfb-8f92-af6f95482ac3").type("");
         List<Meter> meters = metersApi.listMeters(queryOptions);

         assertThat(meters).isNotNull();
         assertThat(meters).isNotEmpty();

         validate(meters);
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
