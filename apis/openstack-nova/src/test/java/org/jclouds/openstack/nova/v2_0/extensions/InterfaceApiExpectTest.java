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

import com.google.common.base.Optional;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import org.jclouds.http.HttpRequest;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.nova.v2_0.NovaApi;
import org.jclouds.openstack.nova.v2_0.domain.FixedIP;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.domain.PortState;
import org.jclouds.openstack.nova.v2_0.internal.BaseNovaApiExpectTest;
import org.jclouds.openstack.nova.v2_0.options.AttachInterfaceOptions;
import org.jclouds.openstack.nova.v2_0.parse.ParseInterfaceAttachmentTest;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import java.net.URI;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

@Test(groups = "unit", testName = "AttachInterfaceApiExpectTest")
public class InterfaceApiExpectTest extends BaseNovaApiExpectTest {

   public void testAttachInterfacesList() throws Exception {
      HttpRequest list = HttpRequest.builder().method("GET")
            .endpoint("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/instance-1/os-interface")
            .addHeader("Accept", "application/json").addHeader("X-Auth-Token", authToken).build();

      HttpResponse listResponse = HttpResponse.builder().statusCode(200)
            .payload(payloadFromResource("/attach_interfaces_list.json")).build();

      NovaApi novaApi = requestsSendResponses(keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess, extensionsOfNovaRequest, extensionsOfNovaResponse, list, listResponse);

      FluentIterable<InterfaceAttachment> interfaceAttachments = novaApi.getInterfaceApi("az-1.region-a.geo-1")
            .get().list("instance-1");

      Optional<? extends InterfaceAttachment> interfaceAttachment = interfaceAttachments.first();

      assertTrue(interfaceAttachment.isPresent(), "Couldn't find interface attachment");
      assertEquals(interfaceAttachment.get(), testInterfaceAttachment());
   }

   public void testAttachInterfaceGet() throws Exception {
      HttpRequest list = HttpRequest
            .builder()
            .method("GET")
            .endpoint(
                  "https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/instance-1/os-interface/ce531f90-199f-48c0-816c-13e38010b442")
            .addHeader("Accept", "application/json").addHeader("X-Auth-Token", authToken).build();

      HttpResponse listResponse = HttpResponse.builder().statusCode(200)
            .payload(payloadFromResource("/attach_interface_details.json")).build();

      NovaApi novaApi = requestsSendResponses(keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess, extensionsOfNovaRequest, extensionsOfNovaResponse, list, listResponse);

      InterfaceAttachment interfaceAttachment = novaApi.getInterfaceApi("az-1.region-a.geo-1").get()
            .get("instance-1", "ce531f90-199f-48c0-816c-13e38010b442");

      assertEquals(interfaceAttachment, testInterfaceAttachment());
   }

   public void testAttachInterfaceResponseIs2xx() throws Exception {
      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/a01ea7e8107f48a3b9f04e12b01771b6/os-interface");
      InterfaceApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            extensionsOfNovaRequest,
            extensionsOfNovaResponse,
            authenticatedGET().endpoint(endpoint)
                  .method("POST")
                  .payload(payloadFromStringWithContentType(
                        "{\"interfaceAttachment\":{\"net_id\":\"1017d1c5-963b-4ae3-b40f-2e8266287249\"}}", "application/json")).
                  build(),
            HttpResponse.builder().statusCode(200).message("HTTP/1.1 200").payload(payloadFromResource("/interface_attachment.json")).build()
      ).getInterfaceApi("az-1.region-a.geo-1").get();

      assertEquals(api.create("a01ea7e8107f48a3b9f04e12b01771b6", AttachInterfaceOptions.Builder.netId("1017d1c5-963b-4ae3-b40f-2e8266287249")).toString(),
            new ParseInterfaceAttachmentTest().expected().toString());
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testAttachNonExistentServerIdResponseIs4xx() throws Exception {
      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/NonExistentServerId/os-interface");
      InterfaceApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            extensionsOfNovaRequest,
            extensionsOfNovaResponse,
            authenticatedGET().endpoint(endpoint)
                  .method("POST")
                  .payload(payloadFromStringWithContentType(
                        "{\"interfaceAttachment\":{\"net_id\":\"1017d1c5-963b-4ae3-b40f-2e8266287249\"}}", "application/json"))
                  .build(),
            HttpResponse.builder().statusCode(404).build()
      ).getInterfaceApi("az-1.region-a.geo-1").get();

      api.create("NonExistentServerId", AttachInterfaceOptions.Builder.netId("1017d1c5-963b-4ae3-b40f-2e8266287249"));
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testAttachNonExistentNetIdResponseIs4xx() throws Exception {
      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/a01ea7e8107f48a3b9f04e12b01771b6/os-interface");
      InterfaceApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess,
            extensionsOfNovaRequest,
            extensionsOfNovaResponse,
            authenticatedGET().endpoint(endpoint)
                  .method("POST")
                  .payload(payloadFromStringWithContentType(
                        "{\"interfaceAttachment\":{\"net_id\":\"NonExistentNetId\"}}", "application/json"))
                  .build(),
            HttpResponse.builder().statusCode(404).build()
      ).getInterfaceApi("az-1.region-a.geo-1").get();

      api.create("a01ea7e8107f48a3b9f04e12b01771b6", AttachInterfaceOptions.Builder.netId("NonExistentNetId"));
   }

   public void testDetachInterfaceResponseIs202() throws Exception {
      String serverId = "a01ea7e8107f48a3b9f04e12b01771b6";
      String portId = "2839d4cd-0f99-4742-98ce-0585605d0222";

      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/" + serverId + "/os-interface" + "/" + portId);
      HttpRequest detachInterfaceRequest = HttpRequest.builder()
            .method("DELETE")
            .endpoint(endpoint)
            .addHeader("Accept", "application/json")
            .addHeader("X-Auth-Token", authToken)
            .build();

      HttpResponse detachResponse = HttpResponse.builder().statusCode(204).build();

      NovaApi apiWhenServerExists = requestsSendResponses(keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess, extensionsOfNovaRequest, extensionsOfNovaResponse, detachInterfaceRequest, detachResponse);

      apiWhenServerExists.getInterfaceApi("az-1.region-a.geo-1").get().detachInterface(serverId, portId);
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testDetachNonExistentServerIdResponseIs4xx() throws Exception {
      String serverId = "NonExistentServerId";
      String portId = "2839d4cd-0f99-4742-98ce-0585605d0222";

      URI endpoint = URI.create("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/" + serverId + "/os-interface" + "/" + portId);
      HttpRequest detachInterfaceRequest = HttpRequest.builder()
            .method("DELETE")
            .endpoint(endpoint)
            .addHeader("Accept", "application/json")
            .addHeader("X-Auth-Token", authToken)
            .build();

      HttpResponse detachResponse = HttpResponse.builder().statusCode(404).build();

      NovaApi apiWhenServerNotExists = requestsSendResponses(keystoneAuthWithUsernameAndPasswordAndTenantName,
            responseWithKeystoneAccess, extensionsOfNovaRequest, extensionsOfNovaResponse, detachInterfaceRequest, detachResponse);

      apiWhenServerNotExists.getInterfaceApi("az-1.region-a.geo-1").get().detachInterface(serverId, portId);
   }

   private InterfaceAttachment testInterfaceAttachment() {
      return InterfaceAttachment
            .builder()
            .portId("ce531f90-199f-48c0-816c-13e38010b442")
            .networkId("3cb9bc59-5699-4588-a4b1-b87f96708bc6")
            .portState(PortState.ACTIVE)
            .macAddress("fa:16:3e:4c:2c:30")
            .fixedIps(
                  ImmutableSet.of(FixedIP.builder().ipAddress("192.168.1.3")
                        .subnetId("f8a6e8f8-c2ec-497c-9f23-da9616de54ef").build())
            ).build();
   }

}
