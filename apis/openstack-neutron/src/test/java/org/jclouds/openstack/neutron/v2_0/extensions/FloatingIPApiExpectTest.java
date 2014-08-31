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

package org.jclouds.openstack.neutron.v2_0.extensions;

import com.google.common.collect.ImmutableSet;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.neutron.v2_0.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2_0.domain.Reference;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiExpectTest;
import org.jclouds.openstack.neutron.v2_0.options.CreateFloatingIPOptions;
import org.jclouds.openstack.neutron.v2_0.options.UpdateFloatingIPOptions;
import org.jclouds.openstack.neutron.v2_0.parse.ParseFloatingIPTest;
import org.jclouds.rest.AuthorizationException;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of FloatingIpApi
 *
 */
@Test(groups = "unit", testName = "FloatingIpApiExpectTest")
public class FloatingIPApiExpectTest extends BaseNeutronApiExpectTest {

   private static final String ZONE = "region-a.geo-1";

   public void testListReferencesReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").addQueryParam("fields", "id", "tenant_id").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_floating_ips.json", APPLICATION_JSON)).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      Set<? extends Reference> references = api.listReferences().toSet();
      assertEquals(references, listOfReferences());
   }

   public void testListReferencesReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").addQueryParam("fields", "id", "tenant_id").build(),
            HttpResponse.builder().statusCode(404).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      assertTrue(api.listReferences().isEmpty());
   }

   public void testListReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_floating_ips.json", APPLICATION_JSON)).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      Set<? extends FloatingIP> floatingIps = api.list().toSet();
      assertEquals(floatingIps, listOfFloatingIps());
   }

   public void testListReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").build(),
            HttpResponse.builder().statusCode(404).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      assertTrue(api.list().isEmpty());
   }

   public void testGetReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/floating_ip.json", APPLICATION_JSON)).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      FloatingIP floatingIp = api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      assertEquals(floatingIp, new ParseFloatingIPTest().expected());
   }

   public void testGetReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(404).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      assertNull(api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a"));
   }

   public void testCreateReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").method("POST")
                  .payload(payloadFromStringWithContentType("{\"floatingip\":{\"floating_network_id\":\"12345678\",\"port_id\":\"87654321\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromStringWithContentType("{\"floatingip\":{\"id\":\"12345\",\"tenant_id\":\"6789\"}}", APPLICATION_JSON)).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      Reference floatingIp = api.create("12345678", CreateFloatingIPOptions.builder().portId("87654321").build());
      assertEquals(floatingIp, Reference.builder().id("12345").tenantId("6789").build());
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testCreateReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips").method("POST")
                  .payload(payloadFromStringWithContentType("{\"floatingip\":{\"floating_network_id\":\"12345678\",\"port_id\":\"87654321\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(401).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      api.create("12345678", CreateFloatingIPOptions.builder().portId("87654321").build());
   }

   public void testUpdateReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/12345").method("PUT")
                  .payload(payloadFromStringWithContentType("{\"floatingip\":{\"port_id\":\"12345678\",\"fixed_ip_address\":\"192.168.0.2\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      assertTrue(api.update("12345", UpdateFloatingIPOptions.builder().portId("12345678").fixedIpAddress("192.168.0.2").build()));
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testUpdateReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/12345").method("PUT")
                  .payload(payloadFromStringWithContentType("{\"floatingip\":{\"port_id\":\"12345678\",\"fixed_ip_address\":\"192.168.0.2\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(404).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      api.update("12345", UpdateFloatingIPOptions.builder().portId("12345678").fixedIpAddress("192.168.0.2").build());
   }

   public void testDeleteReturns2xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(200).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      assertTrue(api.delete("12345"));
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testDeleteReturns4xx() {
      FloatingIPApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/floatingips/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(403).build())
            .getFloatingIPExtensionForZone(ZONE).get();

      api.delete("12345");
   }

   protected Set<FloatingIP> listOfFloatingIps() {
      return ImmutableSet.of(
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("1a104cf5-cb18-4d35-9407-2fd2646d9d0b").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("31083ae2-420d-48b2-ac98-9f7a4fd8dbdc").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("49c6d6fa-ff2a-459d-b975-75a8d31c9a89").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("5cb3d6f4-62cb-41c9-b964-ba7d9df79e4e").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("5d51d012-3491-4db7-b1b5-6f254015015d").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("5f9cf7dc-22ca-4097-8e49-1cc8b23faf17").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("6319ecad-6bff-48b2-9b53-02ede8cb7588").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("6ba4c788-661f-49ab-9bf8-5f10cbbb2f57").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("74ed170b-5069-4353-ab38-9719766dc57e").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("b71fcac1-e864-4031-8c5b-edbecd9ece36").build(),
            FloatingIP.builder().portId("1489734678").floatingNetworkId("9876543210").routerId("1357924680").tenantId("1234567890").id("c7681895-d84d-4650-9ca0-82c72036b855").build()
      );
   }
}