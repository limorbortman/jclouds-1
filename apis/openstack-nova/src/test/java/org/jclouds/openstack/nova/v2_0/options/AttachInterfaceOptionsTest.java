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
package org.jclouds.openstack.nova.v2_0.options;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jclouds.http.HttpRequest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.v2_0.domain.FixedIP;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertEquals;

/**
 * Tests behavior of {@code ListOptions}
 */
@Test(groups = "unit")
public class AttachInterfaceOptionsTest {

   Injector injector = Guice.createInjector(new GsonModule());

   @Test
   public void testAddFixIpToRequest() {
      Set<FixedIP> fixIps = new HashSet<FixedIP>();
      FixedIP fixedIp = FixedIP.builder().subnetId("09226399-c417-476c-85bb-3ca176e9823a").ipAddress("12.40.7.6").build();
      fixIps.add(fixedIp);
      AttachInterfaceOptions options = AttachInterfaceOptions.Builder.fixIps(fixIps);
      HttpRequest request = buildRequest(options);
      assertEquals(request.getPayload().getRawContent(), "{\"interfaceAttachment\":{\"fixed_ips\":[{\"subnet_id\":\"09226399-c417-476c-85bb-3ca176e9823a\",\"ip_address\":\"12.40.7.6\"}]}}");
   }

   @Test
   public void testPortIdAndPortStateToRequest() {
      AttachInterfaceOptions options = AttachInterfaceOptions.Builder.portId("2839d4cd-0f99-4742-98ce-0585605d0222").portState("DOWN");
      HttpRequest request = buildRequest(options);
      assertEquals(request.getPayload().getRawContent(), "{\"interfaceAttachment\":{\"port_state\":\"DOWN\",\"port_id\":\"2839d4cd-0f99-4742-98ce-0585605d0222\"}}");

      options = AttachInterfaceOptions.Builder.portState("UP").portId("12345678");
      request = buildRequest(options);
      assertEquals(request.getPayload().getRawContent(), "{\"interfaceAttachment\":{\"port_state\":\"UP\",\"port_id\":\"12345678\"}}");
   }

   @Test
   public void testNetIdToRequest() {
      AttachInterfaceOptions options = AttachInterfaceOptions.Builder.netId("1017d1c5-963b-4ae3-b40f-2e8266287249");
      HttpRequest request = buildRequest(options);
      assertEquals(request.getPayload().getRawContent(), "{\"interfaceAttachment\":{\"net_id\":\"1017d1c5-963b-4ae3-b40f-2e8266287249\"}}");

   }


   private HttpRequest buildRequest(AttachInterfaceOptions options) {
      injector.injectMembers(options);
      HttpRequest request = HttpRequest.builder().method("POST").endpoint("https://az-1.region-a.geo-1.compute.hpcloudsvc.com/v2/3456/servers/a01ea7e8107f48a3b9f04e12b01771b6/os-interface").build();
      options.bindToRequest(request, ImmutableMap.<String, Object>of());
      return request;
   }

}
