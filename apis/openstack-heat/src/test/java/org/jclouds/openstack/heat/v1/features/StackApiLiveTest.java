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

import org.jclouds.openstack.heat.v1.domain.Stack;
import org.jclouds.openstack.heat.v1.internal.BaseHeatApiLiveTest;
import org.jclouds.openstack.heat.v1.options.CreateStackOptions;
import org.jclouds.openstack.heat.v1.options.Environment;
import org.testng.annotations.Test;

/**
 * Tests parsing and Guice wiring of StackApi
 */
@Test(groups = "live", testName = "StackApiLiveTest")
public class StackApiLiveTest extends BaseHeatApiLiveTest {

   public static final String TEMPLATE_URL = "http://10.5.5.121/Installs/cPaaS/YAML/simple_stack.yaml";
   protected String stackName = System.getProperty("user.name").replace('.', '-').toLowerCase();

   public void testList() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();

         assertThat(stacks).isNotNull();
      }
   }


   public void testGetStackWitnNameAndID() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();
         assertThat(stacks).isNotNull();

         Stack stack = stackApi.get(stacks.get(0).getName(), stacks.get(0).getId());
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo(stacks.get(0).getId());
      }
   }

   public void testGetStack() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);

         List<Stack> stacks = stackApi.list().toList();
         assertThat(stacks).isNotNull();

         Stack stack = stackApi.get(stacks.get(0).getId());
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isEqualTo(stacks.get(0).getId());
      }
   }

   public void testCreateWithTempletUrl() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(getName()).templateUrl(TEMPLATE_URL);
         Stack stack = stackApi.create(createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();

      }
   }

   public void testCreateWithDisableRollback() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(getName()).templateUrl(TEMPLATE_URL).disableRollback(false);
         Stack stack = stackApi.create(createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
         assertThat(stack.isRollbackDisabled()).isFalse();

      }
   }

   public void testCreateWithTemplate() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(getName()).template("heat_template_version: 2013-05-23\n" +
               "\n" +
               "description: >\n" +
               "  HOT template to deploy volume.\n" +
               "\n" +
               "resources:\n" +
               "  cinder_volume:\n" +
               "    type: OS::Cinder::Volume\n" +
               "    properties:\n" +
               "      size: 1");
         Stack stack = stackApi.create(createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
      }
   }

   public void testCreateWithParameters() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         Map<String, String> parameters = new HashMap<String, String>();
         parameters.put("key_name", "myKey");
         parameters.put("image_id", "3b7be1fa-d381-4067-bb81-e835df630564");
         parameters.put("instance_type", "SMALL_1");
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(getName()).template("heat_template_version: 2013-05-23\n" +
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
         Stack stack = stackApi.create(createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();

         Stack stackFromList = stackApi.get(stack.getId());
         assertThat(stackFromList).isNotNull();
         assertThat(stackFromList.getId()).isEqualTo(stack.getId());
         for (String parmName : parameters.keySet()) {
            assertThat(stackFromList.getParameters().containsKey(parmName)).isTrue();
         }

      }
   }

   public void testCreateWithFilesAndEnvironment() {
      for (String region : api.getConfiguredRegions()) {
         StackApi stackApi = api.getStackApi(region);
         Map<String, String> files = new HashMap<String, String>();
         Map<String, String> resourceRegistry = new HashMap<String, String>();
         resourceRegistry.put("ALU::LCP::VolumeB", "LCP-VolumeB.template.yaml");
         Environment environment = Environment.Builder.resourceRegistry(resourceRegistry);
         files.put("LCP-VolumeB.template.yaml", "{\"resources\":{\"ALU-LCP-Block-StorageB\":{\"properties\":{\"description\":\"Used for VM \\/storage partition.\",\"name\":{\"str_replace\":{\"template\":\"$stk-s01c$cardh0\",\"params\":{\"$card\":{\"get_param\":\"cardvB\"},\"$stk\":{\"get_param\":\"deployment_prefix\"}}}},\"size\":{\"get_param\":\"storage_size\"}},\"type\":\"OS::Cinder::Volume\"}},\"heat_template_version\":\"2013-05-23\",\"description\":\"Template to set up volumes for the initial deployment\",\"parameters\":{\"cardvB\":{\"default\":\"00\",\"description\":\"The (2 digit) virtual card number for server attached to volume when on shelf 0\",\"constraints\":[{\"length\":{\"min\":\"2\",\"max\":\"2\"}},{\"allowed_pattern\":\"^[0-9][0-9]$\"}],\"type\":\"string\"},\"deployment_prefix\":{\"description\":\"Name prefix for resources. It should match the system prefix\\nused in the FI worksheet but this is not enforced.\",\"constraints\":[{\"length\":{\"min\":\"5\",\"max\":\"14\"}},{\"allowed_pattern\":\"^[a-zA-Z0-9][a-zA-Z0-9_-]*$\"}],\"type\":\"string\"},\"storage_size\":{\"default\":\"4\",\"description\":\"GBs for block storage\",\"constraints\":[{\"range\":{\"min\":\"4\"}}],\"type\":\"number\"},\"group_index\":{\"default\":[],\"description\":\"List of strings each pointing to one of the other parameters that is steped up for each additional \\nresource of the same type added to the stack (e.g. at growth event)\",\"type\":\"comma_delimited_list\"}},\"outputs\":{\"volumeId\":{\"description\":\"VolumeId\",\"value\":{\"get_resource\":\"ALU-LCP-Block-StorageB\"}}}}");
         CreateStackOptions createStackOptions = CreateStackOptions.Builder.name(getName()).template("heat_template_version: 2013-05-23\n" +
                     "\n" +
                     "description: Simple template to deploy a single compute instance\n" +
                     "\n" +
                     "\n" +
                     "resources:\n" +
                     "    ALU-LCP-OAMB: {\n" +
                     "         properties: {\n" +
                     "             \"cardvB\": \"01\",\n" +
                     "             \"deployment_prefix\": imorTest,\n" +
                     "             \"storage_size\": 4\n" +
                     "                },\n" +
                     "            \"type\": \"ALU::LCP::VolumeB\"\n" +
                     "            }\n"
         ).files(files).environment(environment);

         Stack stack = stackApi.create(createStackOptions);
         assertThat(stack).isNotNull();
         assertThat(stack.getId()).isNotEmpty();
      }
   }

   private String getName() {
      return stackName + "_" + System.currentTimeMillis();
   }

}