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
package org.jclouds.openstack.heat.v1.features;

import com.google.common.collect.Iterables;
import org.jclouds.openstack.heat.v1.internal.BaseHeatApiLiveTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests behavior of StackApi
 */
@Test(groups = "live", testName = "StackApiLiveTest", singleThreaded = true)
public class StackApiLiveTest extends BaseHeatApiLiveTest {

   protected StackApi stackApi;
   protected StackApi stackAsyncApi;
   protected String zone;

   @BeforeClass(groups = {"integration", "live"})
   @Override
   public void setup() {
      super.setup();
      zone = Iterables.getLast(api.getConfiguredZones(), "local");
      stackApi = api.getStackApiForZone(zone);
      stackAsyncApi = api.getStackApiForZone(zone);
   }

   @AfterClass(groups = { "integration", "live" })
   @Override
   protected void tearDown() {
      super.tearDown();
   }
}