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
package org.jclouds.openstack.heat.v1.features;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jclouds.openstack.heat.v1.HeatApi;
import org.jclouds.openstack.heat.v1.domain.Stack;
import org.jclouds.openstack.heat.v1.internal.BaseHeatApiMockTest;
import org.jclouds.openstack.heat.v1.options.CreateStackOptions;
import org.testng.annotations.Test;

import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

/**
 * Tests annotation parsing of {@code StackApi}
 */
@Test(groups = "unit", testName = "StackApiMockTest")
public class StackApiMockTest extends BaseHeatApiMockTest {

   public static final String TESTSTACK = "teststack";

   public void testList() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(
            new MockResponse().setResponseCode(200).setBody(stringFromResource("/stack_list_response.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         List<Stack> stacks = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stacks).isNotEmpty();
         assertThat(stacks.size()).isEqualTo(1);

      } finally {
         server.shutdown();
      }
   }

   public void testListIsEmpty() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(404)));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         List<Stack> stacks = api.list().toList();

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "GET", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stacks).isEmpty();
      } finally {
         server.shutdown();
      }
   }

   public void testCreateWithTemplate() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_stack.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(TESTSTACK).template("\"template\": {\n" +
               "        \"heat_template_version\": \"2013-05-23\",\n" +
               "        \"description\": \"Simple template to test heat commands\",\n" +
               "        \"parameters\": {\n" +
               "            \"flavor\": {\n" +
               "                \"default\": \"m1.tiny\",\n" +
               "                \"type\": \"string\"\n" +
               "            }\n" +
               "        },\n" +
               "        \"resources\": {\n" +
               "            \"hello_world\": {\n" +
               "                \"type\":\"OS::Nova::Server\",\n" +
               "                \"properties\": {\n" +
               "                    \"key_name\": \"heat_key\",\n" +
               "                    \"flavor\": {\n" +
               "                        \"get_param\": \"flavor\"\n" +
               "                    },\n" +
               "                    \"image\": \"40be8d1a-3eb9-40de-8abd-43237517384f\",\n" +
               "                    \"user_data\": \"#!/bin/bash -xv\\necho \\\"hello world\\\" &gt; /root/hello-world.txt\\n\"\n" +
               "                }\n" +
               "            }\n" +
               "        }\n" +
               "    }");
         Stack stack = api.create(createStackOptions);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo("3095aefc-09fb-4bc7-b1f0-f21a304e864c");

      } finally {
         server.shutdown();
      }
   }

   public void testCreateWithParameters() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_stack.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         Map<String, String> parameters = new HashMap<String, String>();
         parameters.put("key_name", "myKey");
         parameters.put("image_id", "3b7be1fa-d381-4067-bb81-e835df630564");
         parameters.put("instance_type", "SMALL_1");
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(TESTSTACK).template("heat_template_version: 2013-05-23\n" +
               "\n" +
               "description: Simple template to deploy a single compute instance\n" +
               "\n" +
               "parameters:\n" +
               "  key_name:\n" +
               "    type: string\n" +
               "    label: Key Name\n" +
               "    description: Name of key-pair to be used for compute instance\n" +
               "  image_id:\n" +
               "    type: string\n" +
               "    label: Image ID\n" +
               "    description: Image to be used for compute instance\n" +
               "  instance_type:\n" +
               "    type: string\n" +
               "    label: Instance Type\n" +
               "    description: Type of instance (flavor) to be used\n" +
               "\n" +
               "resources:\n" +
               "  my_instance:\n" +
               "    type: OS::Nova::Server\n" +
               "    properties:\n" +
               "      key_name: { get_param: key_name }\n" +
               "      image: { get_param: image_id }\n" +
               "      flavor: { get_param: instance_type }").parameters(parameters);
         Stack stack = api.create(createStackOptions);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo("3095aefc-09fb-4bc7-b1f0-f21a304e864c");

      } finally {
         server.shutdown();
      }
   }

   public void testCreateWithTemplateUrl() throws Exception {
      MockWebServer server = mockOpenStackServer();
      server.enqueue(addCommonHeaders(new MockResponse().setBody(stringFromResource("/access.json"))));
      server.enqueue(addCommonHeaders(new MockResponse().setResponseCode(200).setBody(stringFromResource("/create_stack.json"))));

      try {
         HeatApi heatApi = api(server.getUrl("/").toString(), "openstack-heat", overrides);
         StackApi api = heatApi.getStackApi("RegionOne");

         Map<String, String> parameters = new HashMap<String, String>();
         parameters.put("key_name", "myKey");
         parameters.put("image_id", "3b7be1fa-d381-4067-bb81-e835df630564");
         parameters.put("instance_type", "SMALL_1");
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(TESTSTACK).templateUrl("http://10.5.5.121/Installs/cPaaS/YAML/simple_stack.yaml");
         Stack stack = api.create(createStackOptions);

         /*
          * Check request
          */
         assertThat(server.getRequestCount()).isEqualTo(2);
         assertAuthentication(server);
         assertRequest(server.takeRequest(), "POST", BASE_URI + "/stacks");

         /*
          * Check response
          */
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo("3095aefc-09fb-4bc7-b1f0-f21a304e864c");

      } finally {
         server.shutdown();
      }
   }


}
