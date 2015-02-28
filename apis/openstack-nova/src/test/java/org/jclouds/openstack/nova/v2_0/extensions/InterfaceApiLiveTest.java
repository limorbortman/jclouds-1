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
package org.jclouds.openstack.nova.v2_0.extensions;

import com.google.common.collect.FluentIterable;
import org.jclouds.collect.IterableWithMarker;
import org.jclouds.collect.PagedIterable;
import org.jclouds.openstack.nova.v2_0.domain.FixedIP;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.internal.BaseNovaApiLiveTest;
import org.jclouds.openstack.nova.v2_0.options.AttachInterfaceOptions;
import org.jclouds.openstack.v2_0.domain.Resource;
import org.testng.annotations.Test;

import java.util.LinkedList;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 *
 */
@Test(groups = "live", testName = "InterfaceApiLiveTest")
public class InterfaceApiLiveTest extends BaseNovaApiLiveTest {

   @Test(description = "POST /v${apiVersion}/{tenantId}/servers/{server_id}/os-interfaces")
   public void testAttachedInterfaces() throws Exception {
      for (String regionId : regions) {
         InterfaceApi interfaceApi = api.getInterfaceApi(regionId).get();
         PagedIterable<Resource> servers = api.getServerApi(regionId).list();
         for (IterableWithMarker<Resource> iterableWithMarker : servers.toList()) {
            for (Resource server : iterableWithMarker) {
               FluentIterable<InterfaceAttachment> intrafaceList = interfaceApi.list(server.getId());
               if (!intrafaceList.isEmpty()) {
                  String netId = intrafaceList.get(0).getNetworkId();
                  AttachInterfaceOptions attachInterfaceOptions = AttachInterfaceOptions.Builder.netId(netId);
                  InterfaceAttachment inListenableFuture = interfaceApi.create(server.getId(), attachInterfaceOptions);
                  assertNotNull(inListenableFuture);
                  assertNotNull(inListenableFuture.getFixedIps());
                  assertNotNull(inListenableFuture.getMacAddress());
                  assertNotNull(inListenableFuture.getPortId());
                  assertNotNull(inListenableFuture.getPortState());
                  assertEquals(inListenableFuture.getNetworkId(), netId);
               }
            }
         }
      }
   }

   @Test(description = "GET /v${apiVersion}/{tenantId}/servers/{server_id}/os-interfaces")
   public void testListInterfaces() throws Exception {
      for (String regionId : regions) {
         InterfaceApi interfaceApi = api.getInterfaceApi(regionId).get();
         PagedIterable<Resource> servers = api.getServerApi(regionId).list();
         for (IterableWithMarker<Resource> iterableWithMarker : servers.toList()) {
            for (Resource server : iterableWithMarker) {
               FluentIterable<InterfaceAttachment> interfaceList = interfaceApi.list(server.getId());
               if (!interfaceList.isEmpty()) {
                  int oldNumOfInterface = interfaceList.size();
                  String netId = interfaceList.get(0).getNetworkId();
                  AttachInterfaceOptions attachInterfaceOptions = AttachInterfaceOptions.Builder.netId(netId);
                  InterfaceAttachment inListenableFuture = interfaceApi.create(server.getId(), attachInterfaceOptions);
                  assertNotNull(inListenableFuture);
                  interfaceList = interfaceApi.list(server.getId());
                  assertNotNull(interfaceList);
                  assertTrue(interfaceList.size() == oldNumOfInterface + 1);
                  for (InterfaceAttachment interfaceAttachment : interfaceList) {
                     assertNotNull(interfaceAttachment.getMacAddress());
                     assertNotNull(interfaceAttachment.getNetworkId());
                     assertNotNull(interfaceAttachment.getPortId());
                     assertNotNull(interfaceAttachment.getPortState());
                     assertNotNull(interfaceAttachment.getFixedIps());
                     assertFalse(interfaceAttachment.getFixedIps().isEmpty());
                  }
               }
            }
         }
      }
   }

   @Test(description = "POST /v${apiVersion}/{tenantId}/servers/{server_id}/os-interfaces")
   public void testAttachedInterfacesWithOptions() throws Exception {
      for (String regionId : regions) {
         InterfaceApi interfaceApi = api.getInterfaceApi(regionId).get();
         PagedIterable<Resource> servers = api.getServerApi(regionId).list();
         for (IterableWithMarker<Resource> iterableWithMarker : servers.toList()) {
            for (Resource server : iterableWithMarker) {
               FluentIterable<InterfaceAttachment> intrafaceList = interfaceApi.list(server.getId());
               if (!intrafaceList.isEmpty()) {
                  InterfaceAttachment interfaceAttachment = intrafaceList.get(0);
                  String netId = interfaceAttachment.getNetworkId();
                  FixedIP fixedIp = interfaceAttachment.getFixedIps().iterator().next();
                  String newIp = generateNewIp(fixedIp);
                  fixedIp = FixedIP.builder().subnetId(fixedIp.getSubnetId()).ipAddress(newIp).build();
                  List<FixedIP> fixIpsLis = new LinkedList<FixedIP>();
                  fixIpsLis.add(fixedIp);
                  AttachInterfaceOptions attachInterfaceOptions = AttachInterfaceOptions.Builder.netId(netId).fixedIps(fixIpsLis);
                  InterfaceAttachment inListenableFuture = interfaceApi.create(server.getId(), attachInterfaceOptions);
                  assertNotNull(inListenableFuture);
                  assertNotNull(inListenableFuture.getMacAddress());
                  FixedIP ActualFixedIp = inListenableFuture.getFixedIps().iterator().next();
                  assertEquals(ActualFixedIp.getSubnetId(), fixedIp.getSubnetId());
                  assertEquals(ActualFixedIp.getIpAddress(), fixedIp.getIpAddress());
                  assertNotNull(inListenableFuture.getPortId());
                  assertNotNull(inListenableFuture.getPortState());
                  assertEquals(inListenableFuture.getNetworkId(), netId);
               }

            }
         }
      }
   }


   @Test(description = "DELETE /v${apiVersion}/{tenantId}/servers/{server_id}/os-interface/{port_id}\"")
   public void testDetachIInterfaces() throws Exception {
      for (String regionId : regions) {
         InterfaceApi interfaceApi = api.getInterfaceApi(regionId).get();
         PagedIterable<Resource> servers = api.getServerApi(regionId).list();
         for (IterableWithMarker<Resource> iterableWithMarker : servers.toList()) {
            for (Resource server : iterableWithMarker) {
               FluentIterable<InterfaceAttachment> intrafaceList = interfaceApi.list(server.getId());
               if (!intrafaceList.isEmpty()) {
                  interfaceApi.detachInterface(server.getId(), intrafaceList.get(0).getPortId());
               }
            }
         }
      }
   }


   private String generateNewIp(FixedIP fixedIp) {
      String[] ipAsArry = fixedIp.getIpAddress().split("\\.");
      int newIpInInt = (Integer.parseInt(ipAsArry[0]) << 24 | Integer.parseInt(ipAsArry[2]) << 8
            | Integer.parseInt(ipAsArry[1]) << 16 | Integer.parseInt(ipAsArry[3])) + 1;
      if ((byte) newIpInInt == -1) newIpInInt++;
      return String.format("%d.%d.%d.%d", newIpInInt >>> 24 & 0xFF, newIpInInt >> 16 & 0xFF, newIpInInt >> 8 & 0xFF, newIpInInt & 0xFF);
   }

}
