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

import org.jclouds.openstack.neutron.v2_0.domain.Reference;
import org.jclouds.openstack.neutron.v2_0.domain.ReferenceWithName;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiLiveTest;
import org.jclouds.openstack.neutron.v2_0.options.CreateSecurityGroupRuleOptions;
import org.testng.annotations.Test;

import java.util.Set;

import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Direction;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.EtherType;
import static org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule.Protocol;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupRuleApi
 *
 */
@Test(groups = "live", testName = "SecurityGroupRuleApiLiveTest", singleThreaded = true)
public class SecurityGroupRuleApiLiveTest extends BaseNeutronApiLiveTest {

   public void testGetAndListSecurityGroupRules() {
      for (String zone : api.getConfiguredZones()) {
         Set<? extends Reference> references = api.getSecurityGroupRuleApiExtensionForZone(zone).get().listReferences().toSet();
         Set<? extends SecurityGroupRule> securityGroupRules = api.getSecurityGroupRuleApiExtensionForZone(zone).get().list().toSet();

         assertNotNull(references);
         assertNotNull(securityGroupRules);
         assertEquals(references.size(), securityGroupRules.size());

         for (SecurityGroupRule securityGroupRule : securityGroupRules) {
            assertNotNull(securityGroupRule.getSecurityGroupId());
            assertTrue(references.contains(Reference.builder().id(securityGroupRule.getId()).tenantId(securityGroupRule.getTenantId()).build()));

            SecurityGroupRule retrievedSecurityGroupRule = api.getSecurityGroupRuleApiExtensionForZone(zone).get().get(securityGroupRule.getId());
            assertEquals(securityGroupRule, retrievedSecurityGroupRule);
         }
      }
   }

   public void testCreateAndDeleteSecurityGroupRule() {
      for (String zone : api.getConfiguredZones()) {
         SecurityGroupApi securityGroupApi = api.getSecurityGroupApiExtensionForZone(zone).get();
         SecurityGroupRuleApi securityGroupRuleApi = api.getSecurityGroupRuleApiExtensionForZone(zone).get();

         ReferenceWithName securityGroup = securityGroupApi.create("JClouds-Live-Sec-Group", "JClouds Live Test created sec group");
         assertNotNull(securityGroup);

         Reference ref = securityGroupRuleApi.create(securityGroup.getId(), Direction.EGRESS.value(), Protocol.TCP.value(),
               CreateSecurityGroupRuleOptions.builder().portRangeMin(100).portRangeMax(300).build());
         assertNotNull(ref);

         SecurityGroupRule securityGroupRule = securityGroupRuleApi.get(ref.getId());
         assertNotNull(securityGroupRule);
         assertEquals(securityGroupRule.getId(), ref.getId());
         assertEquals(securityGroupRule.getDirection(), Direction.EGRESS);
         assertEquals(securityGroupRule.getProtocol(), Protocol.TCP);
         assertEquals(securityGroupRule.getPortRangeMin(), new Integer(100));
         assertEquals(securityGroupRule.getPortRangeMax(), new Integer(300));

         Reference ref2 = securityGroupRuleApi.create(securityGroup.getId(), Direction.INGRESS.value(), Protocol.UDP.value(),
               CreateSecurityGroupRuleOptions.builder().etherType(EtherType.IPv4).remoteIpPrefix("172.16.0.0/24").build());
         assertNotNull(ref2);
         assertNotNull(securityGroupRuleApi.get(ref2.getId()));

         assertTrue(securityGroupRuleApi.delete(ref.getId()));
         assertTrue(securityGroupRuleApi.delete(ref2.getId()));
         assertTrue(securityGroupApi.delete(securityGroup.getId()));
      }
   }

}