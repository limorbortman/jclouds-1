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
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.FloatingIPs;
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
 * Tests FloatingIPApi Guice wiring and parsing
 *
 */
@Test
public class FloatingIPApiMockTest extends BaseNeutronApiMockTest {

   public void testCreateFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(201).setBody(stringFromResource("/floatingip_create_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP.CreateOptions createFloatingIp = FloatingIP.createOptions("1234567890").build();

         FloatingIP floatingIp = api.create(createFloatingIp);

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", "/v2.0/floatingips", "/floatingip_create_request.json");

         /*
          * Check response
          */
         assertNotNull(floatingIp);
         assertEquals(floatingIp.getFloatingNetworkId(), "1234567890");
         assertEquals(floatingIp.getRouterId(), "5d51d012-3491-4db7-b1b5-6f254015015d");
         assertEquals(floatingIp.getPortId(), "b71fcac1-e864-4031-8c5b-edbecd9ece36");
         assertEquals(floatingIp.getFixedIpAddress(), "192.168.0.25");
         assertEquals(floatingIp.getTenantId(), "1234567890");
         assertEquals(floatingIp.getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   @Test(expectedExceptions = ResourceNotFoundException.class)
   public void testCreateFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/floatingip_create_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP.CreateOptions createFloatingIp = FloatingIP.createOptions("1234567890").build();

         FloatingIP floatingIp = api.create(createFloatingIp);
      } finally {
         server.shutdown();
      }
   }

   public void testListSpecificPageFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/floatingip_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIPs floatingIps = api.list(PaginationOptions.Builder.limit(2).marker("abcdefg"));

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips?limit=2&marker=abcdefg");

         /*
          * Check response
          */
         assertNotNull(floatingIps);
         assertEquals(floatingIps.first().get().getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   // These fail tests uncover issues with the fallback annotations.
   public void testListSpecificPageFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/floatingip_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIPs floatingIps = api.list(PaginationOptions.Builder.limit(2).marker("abcdefg"));

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips?limit=2&marker=abcdefg");

         /*
          * Check response
          */
         assertTrue(floatingIps.isEmpty());
      } finally {
         server.shutdown();
      }
   }

   public void testListPagedFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/floatingip_list_response_paged1.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/floatingip_list_response_paged2.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         // Note: Lazy! Have to actually look at the collection.
         List<FloatingIP> floatingIps = api.list().concat().toList();
         assertEquals(floatingIps.size(), 4);
         // look at last element
         assertEquals(floatingIps.get(3).getId(), "1a104cf5-cb18-4d35-9407-2fd2646d9d0b_2");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 3);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips");
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips?marker=16dba3bc-f3fa-4775-afdc-237e12c72f6a");

         /*
          * Check response
          */
         assertNotNull(floatingIps);
         assertEquals(floatingIps.get(0).getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
         assertEquals(floatingIps.get(3).getId(), "1a104cf5-cb18-4d35-9407-2fd2646d9d0b_2");
      } finally {
         server.shutdown();
      }
   }

   public void testListPagedFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404).setBody(stringFromResource("/floatingip_list_response_paged1.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         // Note: Lazy! Have to actually look at the collection.
         List<FloatingIP> floatingIps = api.list().concat().toList();


         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips");

         /*
          * Check response
          */
         assertTrue(floatingIps.isEmpty());
      } finally {
         server.shutdown();
      }
   }

   public void testGetFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/floatingip_get_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP floatingIp = api.get("12345");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips/12345");

         /*
          * Check response
          */
         assertNotNull(floatingIp);
         assertEquals(floatingIp.getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   public void testGetFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP floatingIp = api.get("12345");

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", "/v2.0/floatingips/12345");

         /*
          * Check response
          */
         assertNull(floatingIp);
      } finally {
         server.shutdown();
      }
   }

   public void testUpdateFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(201).setBody(stringFromResource("/floatingip_update_response.json"))));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP.UpdateOptions updateFloatingIp = FloatingIP.updateOptions()
               .portId("b71fcac1-e864-4031-8c5b-edbecd9ece36")
               .fixedIpAddress("192.168.0.25")
               .build();

         FloatingIP floatingIp = api.update("123456", updateFloatingIp);

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "PUT", "/v2.0/floatingips/123456", "/floatingip_update_request.json");

         /*
          * Check response
          */
         assertNotNull(floatingIp);
         assertEquals(floatingIp.getPortId(), "b71fcac1-e864-4031-8c5b-edbecd9ece36");
         assertEquals(floatingIp.getId(), "16dba3bc-f3fa-4775-afdc-237e12c72f6a");
      } finally {
         server.shutdown();
      }
   }

   public void testUpdateFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         FloatingIP.UpdateOptions updateFloatingIp = FloatingIP.updateOptions()
               .portId("b71fcac1-e864-4031-8c5b-edbecd9ece36")
               .fixedIpAddress("192.168.0.25")
               .build();

         FloatingIP floatingIp = api.update("123456", updateFloatingIp);

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "PUT", "/v2.0/floatingips/123456");

         /*
          * Check response
          */
         assertNull(floatingIp);
      } finally {
         server.shutdown();
      }
   }

   public void testDeleteFloatingIp() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(201)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         boolean result = api.delete("123456");

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", "/v2.0/floatingips/123456");

         /*
          * Check response
          */
         assertTrue(result);
      } finally {
         server.shutdown();
      }
   }

   public void testDeleteFloatingIpFail() throws IOException, InterruptedException, URISyntaxException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         NeutronApi neutronApi = api(server.getUrl("/").toString(), "openstack-neutron", overrides);
         FloatingIPApi api = neutronApi.getFloatingIPExtensionApi("RegionOne").get();

         boolean result = api.delete("123456");

         /*
          * Check request
          */
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", "/v2.0/floatingips/123456");

         /*
          * Check response
          */
         assertFalse(result);
      } finally {
         server.shutdown();
      }
   }
}
