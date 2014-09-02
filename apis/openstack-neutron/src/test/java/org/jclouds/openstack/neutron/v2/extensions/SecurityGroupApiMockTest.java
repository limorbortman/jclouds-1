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

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.jclouds.openstack.neutron.v2.NeutronApi;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroup;
import org.jclouds.openstack.neutron.v2.domain.SecurityGroups;
import org.jclouds.openstack.neutron.v2.internal.BaseNeutronApiMockTest;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.jclouds.rest.ResourceNotFoundException;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

/**
 * Tests parsing and Guice wiring of SecurityGroupApi
 */
@Test
public class SecurityGroupApiMockTest extends BaseNeutronApiMockTest {

   public void testCreateSecurityGroup() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(201).setBody(stringFromResource("/securitygroup_create_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroup.CreateOptions createSecurityGroup = SecurityGroup.createOptions("jclouds-securitygroup", "jclouds-securitygroup-desc").build();

         SecurityGroup securityGroup = api.create(createSecurityGroup);

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", "/v2.0/security-groups", "/securitygroup_create_request.json");

         /*
          * Check response
          */
         assertNotNull(securityGroup);
         assertEquals(securityGroup.getDescription(), "jclouds-securitygroup-desc");
         assertEquals(securityGroup.getName(), "jclouds-securitygroup");
         assertEquals(securityGroup.getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testCreateSecurityGroupFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/securitygroup_create_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroup.CreateOptions createSecurityGroup = SecurityGroup.createOptions("jclouds-securitygroup", "jclouds-securitygroup-desc").build();

         SecurityGroup securityGroup = api.create(createSecurityGroup);
      } finally {
         server.shutdown();
      }
   }

   public void testListSpecificPageSecurityGroup() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/securitygroup_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroups securityGroups = api.list(PaginationOptions.Builder.limit(2).marker("abcdefg"));

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups?limit=2&marker=abcdefg");

         /*
          * Check response
          */
         assertNotNull(securityGroups);
         assertEquals(securityGroups.first().get().getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   // These fail tests uncover issues with the fallback annotations.
   public void testListSpecificPageSecurityGroupFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/securitygroup_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroups securityGroups = api.list(PaginationOptions.Builder.limit(2).marker("abcdefg"));

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups?limit=2&marker=abcdefg");

         /*
          * Check response
          */
         assertTrue(securityGroups.isEmpty());
      } finally {
         server.shutdown();
      }
   }

   public void testListPagedSecurityGroup() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/securitygroup_list_response_paged1.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/securitygroup_list_response_paged2.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         // Note: Lazy! Have to actually look at the collection.
         List<SecurityGroup> securityGroups = api.list().concat().toList();
         assertEquals(securityGroups.size(), 4);
         // look at last element
         assertEquals(securityGroups.get(3).getId(), "1a104cf5-cb18-4d35-9407-2fd2646d9d0b_2");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 3);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups");
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups?marker=16dba3bc-f3fa-4775-afdc-237e12c72f6a");

         /*
          * Check response
          */
         assertNotNull(securityGroups);
         assertEquals(securityGroups.get(0).getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
         assertEquals(securityGroups.get(3).getId(), "1a104cf5-cb18-4d35-9407-2fd2646d9d0b_2");
      } finally {
         server.shutdown();
      }
   }

   public void testListPagedSecurityGroupFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/securitygroup_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         // Note: Lazy! Have to actually look at the collection.
         List<SecurityGroup> securityGroups = api.list().concat().toList();


         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups");

         /*
          * Check response
          */
         assertTrue(securityGroups.isEmpty());
      } finally {
         server.shutdown();
      }
   }

   public void testGetSecurityGroup() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/securitygroup_get_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroup securityGroup = api.get("12345");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups/12345");

         /*
          * Check response
          */
         assertNotNull(securityGroup);
         assertEquals(securityGroup.getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   public void testGetSecurityGroupFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         SecurityGroup securityGroup = api.get("12345");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/security-groups/12345");

         /*
          * Check response
          */
         assertNull(securityGroup);
      } finally {
         server.shutdown();
      }
   }

   public void testDeleteSecurityGroup() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(201)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         boolean result = api.delete("123456");

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", "/v2.0/security-groups/123456");

         /*
          * Check response
          */
         assertTrue(result);
      } finally {
         server.shutdown();
      }
   }

   public void testDeleteSecurityGroupFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         SecurityGroupApi api = neutronApi.getSecurityGroupExtensionApi("RegionOne").get();

         boolean result = api.delete("123456");

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", "/v2.0/security-groups/123456");

         /*
          * Check response
          */
         assertFalse(result);
      } finally {
         server.shutdown();
      }
   }

}
