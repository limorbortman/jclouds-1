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
package org.jclouds.openstack.neutron.v2.extensions;

import org.jclouds.openstack.neutron.v2.domain.Direction;
import org.jclouds.openstack.neutron.v2.domain.Protocol;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule;
import org.jclouds.openstack.neutron.v2.internal.BaseNeutronApiLiveTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupRuleApi
 */
@Test(groups = "live", testName = "SecurityGroupRuleApiLiveTest")
public class SecurityGroupRuleApiLiveTest extends BaseNeutronApiLiveTest {

   public void testCreateListAndDeleteSecurityGroupRule() {
      for (String zone : api.getConfiguredRegions()) {
         SecurityGroupApi securityGroupApi = api.getSecurityGroupExtensionApi(zone).get();
         SecurityGroupRuleApi securityGroupRuleApi = api.getSecurityGroupRuleExtensionApi(zone).get();

         SecurityGroup securityGroup = securityGroupApi.create(SecurityGroup.createOptions("jclouds-live-sec-group", "jclouds-live-sec-group-desc").build());
         assertNotNull(securityGroup);

         SecurityGroupRule.CreateOptions createSecurityGroupRule = SecurityGroupRule.createOptions(securityGroup.getId(), Direction.EGRESS)
               .protocol(Protocol.TCP).portRangeMin(100).portRangeMax(300).build();
         SecurityGroupRule securityGroupRule = securityGroupRuleApi.create(createSecurityGroupRule);
         assertNotNull(securityGroupRule);

         SecurityGroupRule retrievedSecurityGroupRule = securityGroupRuleApi.get(securityGroupRule.getId());
         assertNotNull(retrievedSecurityGroupRule);
         assertEquals(retrievedSecurityGroupRule.getId(), securityGroupRule.getId());
         assertEquals(retrievedSecurityGroupRule.getDirection(), Direction.EGRESS);
         assertEquals(retrievedSecurityGroupRule.getProtocol(), Protocol.TCP);
         assertEquals(retrievedSecurityGroupRule.getPortRangeMin(), new Integer(100));
         assertEquals(retrievedSecurityGroupRule.getPortRangeMax(), new Integer(300));

         List<SecurityGroupRule> securityGroupRules = securityGroupRuleApi.list().concat().toList();
         boolean found = false;
         for (SecurityGroupRule listSecurityGroupRule : securityGroupRules) {
            if (listSecurityGroupRule.getId().equals(securityGroupRule.getId())) {
               found = true;
               break;
            }
         }

         assertTrue(found) ;
         assertTrue(securityGroupRuleApi.delete(securityGroupRule.getId()));
         assertTrue(securityGroupApi.delete(securityGroup.getId()));
         assertFalse(securityGroupRuleApi.delete("unknownId"));
      }
   }

}
