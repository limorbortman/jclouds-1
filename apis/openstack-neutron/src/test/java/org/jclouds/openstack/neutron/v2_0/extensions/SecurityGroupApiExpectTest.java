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
import org.jclouds.openstack.neutron.v2_0.domain.ReferenceWithName;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiExpectTest;
import org.jclouds.openstack.neutron.v2_0.parse.ParseSecurityGroupTest;
import org.jclouds.rest.AuthorizationException;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupApi
 *
 * @author Nick Livens
 */
@Test(groups = "unit", testName = "org.jclouds.openstack.neutron.v2_0.extensions.SecurityGroupApiExpectTest")
public class SecurityGroupApiExpectTest extends BaseNeutronApiExpectTest {

   private static final String ZONE = "region-a.geo-1";

   public void testListReferencesReturns2xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").addQueryParam("fields", "id", "tenant_id", "name").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_security_groups.json", APPLICATION_JSON)).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      Set<? extends ReferenceWithName> references = api.listReferences().toSet();
      assertEquals(references, listOfReferencesWithNames());
   }

   public void testListReferencesReturns4xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").addQueryParam("fields", "id", "tenant_id", "name").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      assertTrue(api.listReferences().isEmpty());
   }

   public void testListReturns2xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_security_groups.json", APPLICATION_JSON)).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      Set<? extends SecurityGroup> securityGroups = api.list().toSet();
      assertEquals(securityGroups, listOfSecurityGroups());
   }

   public void testListReturns4xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      assertTrue(api.list().isEmpty());
   }

   public void testGetReturns2xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/security_group.json", APPLICATION_JSON)).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      SecurityGroup securityGroup = api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      assertEquals(securityGroup, new ParseSecurityGroupTest().expected());
   }

   public void testGetReturns4xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      assertNull(api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a"));
   }

   public void testCreateReturns2xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").method("POST")
                  .payload(payloadFromStringWithContentType("{\"security_group\":{\"name\":\"sec-group\",\"description\":\"sec-group-description\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromStringWithContentType("{\"security_group\":{\"id\":\"12345\",\"tenant_id\":\"6789\",\"name\":\"sec-group\",\"description\":\"sec-group-description\"}}", APPLICATION_JSON)).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      ReferenceWithName securityGroup = api.create("sec-group", "sec-group-description");
      assertEquals(securityGroup, ReferenceWithName.builder().id("12345").tenantId("6789").name("sec-group").build());
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testCreateReturns4xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups").method("POST")
                  .payload(payloadFromStringWithContentType("{\"security_group\":{\"name\":\"sec-group\",\"description\":\"sec-group-description\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(401).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      api.create("sec-group", "sec-group-description");
   }

   public void testDeleteReturns2xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(200).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      assertTrue(api.delete("12345"));
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testDeleteReturns4xx() {
      SecurityGroupApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-groups/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(403).build())
            .getSecurityGroupApiExtensionForZone(ZONE).get();

      api.delete("12345");
   }

   protected Set<SecurityGroup> listOfSecurityGroups() {
      return ImmutableSet.of(
            SecurityGroup.builder().description("security-group").name("jclouds-port-test").tenantId("1234567890").id("16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            SecurityGroup.builder().description("security-group").name("wibble").tenantId("1234567890").id("1a104cf5-cb18-4d35-9407-2fd2646d9d0b").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-test").tenantId("1234567890").id("31083ae2-420d-48b2-ac98-9f7a4fd8dbdc").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-test").tenantId("1234567890").id("49c6d6fa-ff2a-459d-b975-75a8d31c9a89").build(),
            SecurityGroup.builder().description("security-group").name("wibble").tenantId("1234567890").id("5cb3d6f4-62cb-41c9-b964-ba7d9df79e4e").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-port-test").tenantId("1234567890").id("5d51d012-3491-4db7-b1b5-6f254015015d").build(),
            SecurityGroup.builder().description("security-group").name("wibble").tenantId("1234567890").id("5f9cf7dc-22ca-4097-8e49-1cc8b23faf17").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-test").tenantId("1234567890").id("6319ecad-6bff-48b2-9b53-02ede8cb7588").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-port-test").tenantId("1234567890").id("6ba4c788-661f-49ab-9bf8-5f10cbbb2f57").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-test").tenantId("1234567890").id("74ed170b-5069-4353-ab38-9719766dc57e").build(),
            SecurityGroup.builder().description("security-group").name("wibble").tenantId("1234567890").id("b71fcac1-e864-4031-8c5b-edbecd9ece36").build(),
            SecurityGroup.builder().description("security-group").name("jclouds-port-test").tenantId("1234567890").id("c7681895-d84d-4650-9ca0-82c72036b855").build()
      );
   }

}