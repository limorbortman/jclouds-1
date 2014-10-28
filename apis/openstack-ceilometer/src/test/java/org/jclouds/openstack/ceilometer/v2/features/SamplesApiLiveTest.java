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

import org.jclouds.openstack.ceilometer.v2.domain.Sample;
import org.jclouds.openstack.ceilometer.v2.internal.BaseCeilometerApiLiveTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests parsing and Guice wiring of MetersApi
 */
@Test(groups = "live", testName = "MetersApiLiveTest")
public class SamplesApiLiveTest extends BaseCeilometerApiLiveTest {

   /**
    * use carefully since this test returns all the samples and might stuck ceilometer
    */
   public void testListSamples() {
      for (String region : regions) {
         QueryApi queryApi = api.getQueryApi(region);

         List<Sample> samples = queryApi.listSamples();
         assertThat(samples).isNotNull();
         assertThat(samples).isNotEmpty();

         validate(samples);
      }
   }

   public void testListSamplesWithQuery() {
      for (String region : regions) {
         QueryApi queryApi = api.getQueryApi(region);

         List<Sample> samples = queryApi.listSamples(1);

         validate(samples);
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
