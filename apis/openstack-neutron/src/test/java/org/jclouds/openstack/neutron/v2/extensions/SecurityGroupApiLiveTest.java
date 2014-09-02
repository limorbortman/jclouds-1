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

import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.internal.BaseNeutronApiLiveTest;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupApi
 */
@Test(groups = "live", testName = "SecurityGroupApiLiveTest")
public class SecurityGroupApiLiveTest extends BaseNeutronApiLiveTest {

   public void testCreateListAndDelete() {
      for (String zone : api.getConfiguredRegions()) {
         SecurityGroupApi securityGroupApi = api.getSecurityGroupExtensionApi(zone).get();

         SecurityGroup securityGroup = securityGroupApi.create(SecurityGroup.createOptions("jclouds-live-sec-group", "jclouds live test created sec group").build());
         assertNotNull(securityGroup);

         SecurityGroup retrievedSecurityGroup = securityGroupApi.get(securityGroup.getId());

         assertEquals(retrievedSecurityGroup.getId(), securityGroup.getId());
         assertEquals(retrievedSecurityGroup.getName(), "jclouds-live-sec-group");
         assertEquals(retrievedSecurityGroup.getDescription(), "jclouds live test created sec group");

         List<SecurityGroup> securityGroups = securityGroupApi.list().concat().toList();
         boolean found = false;
         for (SecurityGroup listSecurityGroup : securityGroups) {
            if (listSecurityGroup.getId().equals(retrievedSecurityGroup.getId())) {
               found = true;
               break;
            }
         }

         assertTrue(found);
         assertTrue(securityGroupApi.delete(securityGroup.getId()));
         assertFalse(securityGroupApi.delete("unknownId"));
      }
   }

}
