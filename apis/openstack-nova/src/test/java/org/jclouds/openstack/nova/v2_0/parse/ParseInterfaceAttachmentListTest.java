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
package org.jclouds.openstack.nova.v2_0.parse;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jclouds.json.BaseItemParserTest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.v2_0.config.NovaParserModule;
import org.jclouds.openstack.nova.v2_0.domain.FixedIP;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.openstack.nova.v2_0.domain.PortState;
import org.jclouds.openstack.nova.v2_0.functions.internal.ParseInterfaceAttachments;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Test(groups = "unit", testName = "ParseInterfaceAttachmentListTest")
public class ParseInterfaceAttachmentListTest extends BaseItemParserTest<FluentIterable<InterfaceAttachment>> {

   @Override
   public String resource() {
      return "/interface_attachment_list.json";
   }

   @Override
   @ResponseParser(ParseInterfaceAttachments.class)
   @SelectJson("interfaceAttachments")
   @Consumes(MediaType.APPLICATION_JSON)
   public FluentIterable<InterfaceAttachment> expected() {
      FixedIP forNet1 = FixedIP.builder().ipAddress("192.168.1.3").subnetId("f8a6e8f8-c2ec-497c-9f23-da9616de54ef").build();
      FixedIP forNet2 = FixedIP.builder().ipAddress("10.70.20.30").subnetId("f8a6e8f8-c2ec-497c-9f23-d67439f").build();
      return FluentIterable.from(ImmutableSet.of(
                  InterfaceAttachment
                        .builder()
                        .macAddress("fa:16:3e:4c:2c:30")
                        .networkId("3cb9bc59-5699-4588-a4b1-b87f96708bc6")
                        .portId("ce531f90-199f-48c0-816c-13e38010b442")
                        .portState(PortState.ACTIVE)
                        .fixedIps(ImmutableSet.<FixedIP>of(forNet1))
                        .build(),
                  InterfaceAttachment
                        .builder()
                        .macAddress("fa:16:3e:4c:2c:32")
                        .networkId("3cb9bc59-5699-4588-a4b1-t58khgms56409")
                        .portId("ce531f90-199f-48c0-816c-098753dy6lml")
                        .portState(PortState.ACTIVE)
                        .fixedIps(ImmutableSet.<FixedIP>of(forNet2))
                        .build()
            )
      );

   }

   protected Injector injector() {
      return Guice.createInjector(new NovaParserModule(), new GsonModule());
   }

}
