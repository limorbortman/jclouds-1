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
package org.jclouds.openstack.murano.v1.features;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import org.jclouds.openstack.murano.v1.MuranoApi;
import org.jclouds.openstack.murano.v1.internal.BaseMuranoApiMockTest;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.jclouds.openstack.murano.v1.domain.MuranoPackage;
import org.jclouds.openstack.murano.v1.options.CreatePackageOptions;


/**
 * Tests annotation parsing of {@code MuranoPackageApi}
 */
@Test(groups = "unit", testName = "MuranoPackageApiMockTest")
public class MuranoPackageApiMockTest extends BaseMuranoApiMockTest {

   private static final String TEST_PACKAGE_ID = "5b94281f57634f2aba61236d4622f92f";
   private static final String PACKAGE_NO_TAGS = "/package_no_tags.zip";

   public void testPackageList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/package_list.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         MuranoPackageApi api = muranoApi.getPackageApi("RegionOne");
         List<MuranoPackage> packages = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/catalog/packages");

         /*
          * Check response
          */
         assertThat(packages).isNotEmpty();
         assertThat(packages.size()).isEqualTo(2);

      } finally {
         server.shutdown();
      }
   }

   public void testCreatePackage() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_package" + ".json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         MuranoPackageApi api = muranoApi.getPackageApi("RegionOne");
         String path = getClass().getResource(PACKAGE_NO_TAGS).getFile();
         File file = new File(path);
         CreatePackageOptions createPackageOptions = CreatePackageOptions.Builder.enabled(true);
         MuranoPackage muranoPackage = api.create(createPackageOptions, file);

         /*
          * Check request
          */

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/catalog/packages");

         /*
          *Check response
          */
         assertThat(muranoPackage).isNotNull();
         assertThat(muranoPackage.getId()).isEqualTo(TEST_PACKAGE_ID);

      } finally {
         server.shutdown();
      }
   }

   public void testDeletePackage() throws IOException, InterruptedException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200)));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         MuranoPackageApi api = muranoApi.getPackageApi("RegionOne");

         boolean result = api.delete(TEST_PACKAGE_ID);


         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", BASE_URI + "/v1/catalog/packages/" + TEST_PACKAGE_ID);

         /*
          * Check response
          */

         assertTrue(result);
      } finally {
         server.shutdown();
      }
   }

   public void testGetAutoPackage() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/package_get_response.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         MuranoPackageApi api = muranoApi.getPackageApi("RegionOne");

         MuranoPackage muranoPackage = api.get(TEST_PACKAGE_ID);
         assertThat(muranoPackage).isNotNull();

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/catalog/packages/" + TEST_PACKAGE_ID);
         assertThat(muranoPackage).isNotNull();

      } finally {
         server.shutdown();
      }
   }
}

