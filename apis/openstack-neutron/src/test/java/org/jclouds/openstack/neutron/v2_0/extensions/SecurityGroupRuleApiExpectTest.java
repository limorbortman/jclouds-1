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
import org.jclouds.openstack.neutron.v2_0.domain.Reference;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiExpectTest;
import org.jclouds.openstack.neutron.v2_0.parse.ParseSecurityGroupRuleTest;
import org.jclouds.rest.AuthorizationException;
import org.testng.annotations.Test;

import javax.ws.rs.core.MediaType;
import java.util.Set;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Direction.EGRESS;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Direction.INGRESS;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.EtherType.IPv4;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.EtherType.IPv6;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Protocol.ICMP;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Protocol.TCP;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Protocol.UDP;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;


/**
 * Tests parsing and Guice wiring of SecurityGroupRuleApi
 *
 * @author Nick Livens
 */
@Test(groups = "unit", testName = "SecurityGroupRuleApiExpectTest")
public class SecurityGroupRuleApiExpectTest extends BaseNeutronApiExpectTest {

   private static final String ZONE = "region-a.geo-1";

   public void testListReferencesReturns2xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").addQueryParam("fields", "id", "tenant_id").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_security_group_rules.json", APPLICATION_JSON)).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      Set<? extends Reference> references = api.listReferences().toSet();
      assertEquals(references, listOfReferences());
   }

   public void testListReferencesReturns4xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").addQueryParam("fields", "id", "tenant_id").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      assertTrue(api.listReferences().isEmpty());
   }

   public void testListReturns2xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/list_security_group_rules.json", APPLICATION_JSON)).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      Set<? extends SecurityGroupRule> securityGroups = api.list().toSet();
      assertEquals(securityGroups, listOfSecurityGroupRules());
   }

   public void testListReturns4xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      assertTrue(api.list().isEmpty());
   }

   public void testGetReturns2xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResourceWithContentType("/security_group_rule.json", APPLICATION_JSON)).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      SecurityGroupRule securityGroupRule = api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      assertEquals(securityGroupRule, new ParseSecurityGroupRuleTest().expected());
   }

   public void testGetReturns4xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules/16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            HttpResponse.builder().statusCode(404).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      assertNull(api.get("16dba3bc-f3fa-4775-afdc-237e12c72f6a"));
   }

   public void testCreateReturns2xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").method("POST")
                  .payload(payloadFromStringWithContentType("{\"security_group_rule\":{\"security_group_id\":\"123456789\",\"direction\":\"ingress\",\"protocol\":\"TCP\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromStringWithContentType("{\"security_group_rule\":{\"id\":\"12345\",\"tenant_id\":\"6789\",\"protocol\":\"TCP\",\"security_group_id\":\"123456789\"}}", APPLICATION_JSON)).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      Reference securityGroupRule = api.create("123456789", INGRESS.value(), TCP.value());
      assertEquals(securityGroupRule, Reference.builder().id("12345").tenantId("6789").build());
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testCreateReturns4xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules").method("POST")
                  .payload(payloadFromStringWithContentType("{\"security_group_rule\":{\"security_group_id\":\"123456789\",\"direction\":\"ingress\",\"protocol\":\"TCP\"}}", MediaType.APPLICATION_JSON)).build(),
            HttpResponse.builder().statusCode(401).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      api.create("123456789", INGRESS.value(), TCP.value());
   }

   public void testDeleteReturns2xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(200).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      assertTrue(api.delete("12345"));
   }

   @Test(expectedExceptions = AuthorizationException.class)
   public void testDeleteReturns4xx() {
      SecurityGroupRuleApi api = requestsSendResponses(
            keystoneAuthWithUsernameAndPasswordAndTenantName, responseWithKeystoneAccess,
            authenticatedGET().endpoint(endpoint + "/security-group-rules/12345").method("DELETE").build(),
            HttpResponse.builder().statusCode(403).build())
            .getSecurityGroupRuleApiExtensionForZone(ZONE).get();

      api.delete("12345");
   }

   protected Set<SecurityGroupRule> listOfSecurityGroupRules() {
      return ImmutableSet.of(
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv4).protocol(TCP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("16dba3bc-f3fa-4775-afdc-237e12c72f6a").build(),
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv4).protocol(UDP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("1a104cf5-cb18-4d35-9407-2fd2646d9d0b").build(),
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv4).protocol(ICMP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("31083ae2-420d-48b2-ac98-9f7a4fd8dbdc").build(),
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv6).protocol(TCP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("49c6d6fa-ff2a-459d-b975-75a8d31c9a89").build(),
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv6).protocol(UDP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("5cb3d6f4-62cb-41c9-b964-ba7d9df79e4e").build(),
            SecurityGroupRule.builder().direction(EGRESS).etherType(IPv6).protocol(ICMP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("5d51d012-3491-4db7-b1b5-6f254015015d").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv4).protocol(TCP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("5f9cf7dc-22ca-4097-8e49-1cc8b23faf17").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv4).protocol(UDP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("6319ecad-6bff-48b2-9b53-02ede8cb7588").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv4).protocol(ICMP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("6ba4c788-661f-49ab-9bf8-5f10cbbb2f57").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv6).protocol(TCP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("74ed170b-5069-4353-ab38-9719766dc57e").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv6).protocol(UDP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("b71fcac1-e864-4031-8c5b-edbecd9ece36").build(),
            SecurityGroupRule.builder().direction(INGRESS).etherType(IPv6).protocol(ICMP).securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5").tenantId("1234567890").id("c7681895-d84d-4650-9ca0-82c72036b855").build()
      );
   }

}