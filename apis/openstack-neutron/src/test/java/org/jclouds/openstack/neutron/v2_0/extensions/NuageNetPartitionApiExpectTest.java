/**
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 1.1 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-1.1
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.neutron.v2_0.extensions;

import com.google.common.collect.ImmutableSet;
import org.jclouds.http.HttpResponse;
import org.jclouds.openstack.neutron.v2_0.domain.NuageNetPartition;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiExpectTest;
import org.jclouds.openstack.neutron.v2_0.options.DeleteNuageNetPartitionOptions;
import org.jclouds.openstack.neutron.v2_0.parse.ParseNuageNetPartitionTest;
import org.jclouds.rest.AuthorizationException;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of NuageNetPartitionApi
 */
@Test(groups = "unit", testName = "NuageNetPartitionApiExpectTest")
public class NuageNetPartitionApiExpectTest extends BaseNeutronApiExpectTest {

   private static final String ZONE = "region-a.geo-1";

   public void testListReturns2xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_net_partitions.json", APPLICATION_JSON)).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      Set<? extends NuageNetPartition> netPartitions = api.list().toSet();
      assertEquals(netPartitions, listOfNetPartitions());
   }

   public void testListReturns4xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions").build(),
            HttpResponse.builder().statusCode(404).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      assertTrue(api.list().isEmpty());
   }

   public void testGetReturns2xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/net_partition.json", APPLICATION_JSON)).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      NuageNetPartition netPartition = api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      assertEquals(netPartition, new ParseNuageNetPartitionTest().expected());
   }

   public void testGetReturns4xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(404).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      assertNull(api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a"));
   }

   public void testCreateReturns2xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions").method("POST")
                  .payload(payloadFromStringWithContentType("{\"net_partitions\":{\"name\":\"net-partition\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromStringWithContentType("{\"net_partition\":{\"id\":\"12345\",\"name\":\"net-partition\"}}", APPLICATION_JSON)).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      NuageNetPartition netPartition = api.create("net-partition");
      assertEquals(netPartition, NuageNetPartition.builder().id("12345").name("net-partition").build());
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testCreateReturns4xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions").method("POST")
                  .payload(payloadFromStringWithContentType("{\"net_partition\":{\"name\":\"net-partition\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(401).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      api.create("net-partition");
   }

   public void testDeleteReturns2xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions/12345").method("DELETE")
                                          .payload(payloadFromStringWithContentType("{\"cascade_delete\":true}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      assertTrue(api.delete("12345", DeleteNuageNetPartitionOptions.builder().cascadeDelete(true).build()));
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testDeleteReturns4xx() {
      NuageNetPartitionApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/net-partitions/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(403).build())
            .getNuageNetPartitionApiExtensionForZone(ZONE).get();

      api.delete("12345");
   }

   protected Set<NuageNetPartition> listOfNetPartitions() {
      return ImmutableSet.of(
            NuageNetPartition.builder().name("jclouds-port-test").id("16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            NuageNetPartition.builder().name("wibble").id("1a104cf5-cb18-4d35-9407-2fd2646d9d0b").build(),
            NuageNetPartition.builder().name("jclouds-test").id("31083ae2-420d-48b2-ac98-9f7a4fd8dbdc").build(),
            NuageNetPartition.builder().name("jclouds-test").id("49c6d6fa-ff2a-459d-b975-75a8d31c9a89").build(),
            NuageNetPartition.builder().name("wibble").id("5cb3d6f4-62cb-41c9-b964-ba7d9df79e4e").build(),
            NuageNetPartition.builder().name("jclouds-port-test").id("5d51d012-3491-4db7-b1b5-6f254015015d").build(),
            NuageNetPartition.builder().name("wibble").id("5f9cf7dc-22ca-4097-8e49-1cc8b23faf17").build(),
            NuageNetPartition.builder().name("jclouds-test").id("6319ecad-6bff-48b2-9b53-02ede8cb7588").build(),
            NuageNetPartition.builder().name("jclouds-port-test").id("6ba4c788-661f-49ab-9bf8-5f10cbbb2f57").build(),
            NuageNetPartition.builder().name("jclouds-test").id("74ed170b-5069-4353-ab38-9719766dc57e").build(),
            NuageNetPartition.builder().name("wibble").id("b71fcac1-e864-4031-8c5b-edbecd9ece36").build(),
            NuageNetPartition.builder().name("jclouds-port-test").id("c7681895-d84d-4650-9ca0-82c72036b855").build()
      );
   }

}