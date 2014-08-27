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
package org.jclouds.openstack.nova.v2_0.features;

import org.jclouds.openstack.nova.v2_0.domain.FixedIp;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.internal.BaseNovaApiLiveTest;
import org.jclouds.openstack.nova.v2_0.options.AttachInterfaceOptions;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 *
 */
@Test(groups = "live", testName = "InterfaceApiLiveTest")
public class InterfaceApiLiveTest extends BaseNovaApiLiveTest {

   @Test(description = "POST /v${apiVersion}/{tenantId}/servers/{server_id}/os-interfaces")
   public void testAttachedInterfaces() throws Exception {
      for (String zoneId : zones) {
         InterfaceApi interfaceApi = api.getInterfaceApiForZone(zoneId).get();
         String netId = "727ce2ef-6983-49c2-a404-e11a92e8fd28";
         AttachInterfaceOptions attachInterfaceOptions = AttachInterfaceOptions.Builder.netId(netId);
         InterfaceAttachment inListenableFuture = interfaceApi.attachInterface("532eff71-c523-4380-aa24-e39695c983a2", attachInterfaceOptions);
         assertNotNull(inListenableFuture);
         assertNotNull(inListenableFuture.getFixedIp());
         assertNotNull(inListenableFuture.getMacAddr());
         assertNotNull(inListenableFuture.getPortId());
         assertNotNull(inListenableFuture.getPortState());
         assertEquals(inListenableFuture.getNetId(), netId);
      }
   }

   @Test(description = "POST /v${apiVersion}/{tenantId}/servers/{server_id}/os-interfaces")
   public void testAttachedInterfacesWithOptions() throws Exception {
      for (String zoneId : zones) {
         InterfaceApi interfaceApi = api.getInterfaceApiForZone(zoneId).get();
         String netId = "727ce2ef-6983-49c2-a404-e11a92e8fd28";
         FixedIp fixedIp = FixedIp.builder().subnetId("cf18362d-f117-4598-b32d-edb316f26c3f").ipAddress("11.40.7.22").build();
         List<FixedIp> fixIpsLis = new LinkedList<FixedIp>();
         fixIpsLis.add(fixedIp);
         AttachInterfaceOptions attachInterfaceOptions = AttachInterfaceOptions.Builder.netId(netId).fixedIps(fixIpsLis);
         InterfaceAttachment inListenableFuture = interfaceApi.attachInterface("532eff71-c523-4380-aa24-e39695c983a2", attachInterfaceOptions);
         assertNotNull(inListenableFuture);
         assertNotNull(inListenableFuture.getMacAddr());
         assertEquals(inListenableFuture.getFixedIp(), fixedIp);
         assertNotNull(inListenableFuture.getPortId());
         assertNotNull(inListenableFuture.getPortState());
         assertEquals(inListenableFuture.getNetId(), netId);
      }
   }

   @Test(description = "DELETE /v${apiVersion}/{tenantId}/servers/{server_id}/os-interface/{port_id}\"")
   public void testDetachIInterfaces() throws Exception {
      for (String zoneId : zones) {
         InterfaceApi interfaceApi = api.getInterfaceApiForZone(zoneId).get();
         interfaceApi.detachInterface("532eff71-c523-4380-aa24-e39695c983a2", "2839d4cd-0f99-4742-98ce-0585605d0222");
      }
   }

}