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

import org.jclouds.openstack.neutron.v2_0.domain.ReferenceWithName;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2_0.internal.BaseNeutronApiLiveTest;
import org.testng.annotations.Test;

import java.util.Set;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupApi
 *
 * @author Nick Livens
 */
@Test(groups = "live", testName = "org.jclouds.openstack.neutron.v2_0.extensions.SecurityGroupApiLiveTest", singleThreaded = true)
public class SecurityGroupApiLiveTest extends BaseNeutronApiLiveTest {

   public void testGetAndListSecurityGroups() {
      for (String zone : api.getConfiguredZones()) {
         Set<? extends ReferenceWithName> references = api.getSecurityGroupApiExtensionForZone(zone).get().listReferences().toSet();
         Set<? extends SecurityGroup> securityGroups = api.getSecurityGroupApiExtensionForZone(zone).get().list().toSet();

         assertNotNull(references);
         assertNotNull(securityGroups);
         assertEquals(references.size(), securityGroups.size());

         for (SecurityGroup securityGroup : securityGroups) {
            assertNotNull(securityGroup.getName());
            assertTrue(references.contains(ReferenceWithName.builder().id(securityGroup.getId()).tenantId(securityGroup.getTenantId()).name(securityGroup.getName()).build()));

            SecurityGroup retrievedSecurityGroup = api.getSecurityGroupApiExtensionForZone(zone).get().get(securityGroup.getId());
            assertEquals(securityGroup, retrievedSecurityGroup);
         }
      }
   }

   public void testCreateAndDeleteSecurityGroup() {
      for (String zone : api.getConfiguredZones()) {
         SecurityGroupApi securityGroupApi = api.getSecurityGroupApiExtensionForZone(zone).get();

         ReferenceWithName ref = securityGroupApi.create("JClouds-Live-Sec-Group", "JClouds Live Test created sec group");
         assertNotNull(ref);

         SecurityGroup securityGroup = securityGroupApi.get(ref.getId());

         assertEquals(securityGroup.getId(), ref.getId());
         assertEquals(securityGroup.getName(), "JClouds-Live-Sec-Group");
         assertEquals(securityGroup.getDescription(), "JClouds Live Test created sec group");

         ReferenceWithName ref2 = securityGroupApi.create("JClouds-Live-Sec-Group-2", "JClouds 2nd Live Test created sec group");
         assertNotNull(ref2);

         assertTrue(securityGroupApi.delete(ref.getId()));
         assertTrue(securityGroupApi.delete(ref2.getId()));
      }
   }

}