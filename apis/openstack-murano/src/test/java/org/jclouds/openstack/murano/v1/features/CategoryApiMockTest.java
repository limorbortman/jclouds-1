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
import org.jclouds.openstack.murano.v1.domain.Category;
import org.jclouds.openstack.murano.v1.internal.BaseMuranoApiMockTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Tests annotation parsing of {@code CategoryApi}
 */
@Test(groups = "unit", testName = "CategoryApiMockTest")
public class CategoryApiMockTest extends BaseMuranoApiMockTest {
   private static final String CATEGORY_NAME = "CATEGORY_NAME";
   private static final String TEST_CATEGORY_ID = "d341282acead45faa81776d486331d7f";

   public void testCategoryList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/category_list.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         CategoryApi api = muranoApi.getCategoryApi("RegionOne");

         List<Category> categories = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/catalog/categories");

         /*
          * Check response
          */
         assertThat(categories).isNotEmpty();
         assertThat(categories.size()).isEqualTo(2);

      } finally {
         server.shutdown();
      }
   }

   public void testCreateCategory() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_category" + ".json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         CategoryApi api = muranoApi.getCategoryApi("RegionOne");
         Category category = api.create(CATEGORY_NAME);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/v1/catalog/categories");

         /*
          * Check response
          */
         assertThat(category).isNotNull();
         assertThat(category.getId()).isEqualTo(TEST_CATEGORY_ID);

      } finally {
         server.shutdown();
      }
   }

   public void testDeleteCategory() throws IOException, InterruptedException {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200)));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         CategoryApi api = muranoApi.getCategoryApi("RegionOne");

         boolean result = api.delete(TEST_CATEGORY_ID);

         /*
          * Check request
          */
         assertEquals(server.getRequestCount(), 2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "DELETE", BASE_URI + "/v1/catalog/categories/" + TEST_CATEGORY_ID);

         /*
          * Check response
          */
         assertTrue(result);
      } finally {
         server.shutdown();
      }
   }

   public void testGetAutoCategory() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/category_get_response.json"))));

      try {
         MuranoApi muranoApi = api(server.getUrl("/").toString(), "openstack-murano", overrides);
         CategoryApi api = muranoApi.getCategoryApi("RegionOne");

         Category category = api.get(TEST_CATEGORY_ID);
         assertThat(category).isNotNull();

         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/v1/catalog/categories/" + TEST_CATEGORY_ID);
         assertThat(category).isNotNull();

      } finally {
         server.shutdown();
      }
   }

}
