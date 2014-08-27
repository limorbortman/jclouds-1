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

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.jclouds.json.BaseItemParserTest;
import org.jclouds.json.config.GsonModule;
import org.jclouds.openstack.nova.v2_0.config.NovaParserModule;
import org.jclouds.openstack.nova.v2_0.domain.FixedIp;
import org.jclouds.openstack.nova.v2_0.domain.InterfaceAttachment;
import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Test(groups = "unit", testName = "ParseInterfaceAttachmentTest")
public class ParseInterfaceAttachmentTest extends BaseItemParserTest<InterfaceAttachment> {

   @Override
   public String resource() {
      return "/interface_attachment.json";
   }

   @Override
   @SelectJson("interfaceAttachment")
   @Consumes(MediaType.APPLICATION_JSON)
   public InterfaceAttachment expected() {
      return InterfaceAttachment
            .builder()
            .macAddr("fa:16:3e:28:c6:34")
            .netId("1017d1c5-963b-4ae3-b40f-2e8266287249")
            .portId("2839d4cd-0f99-4742-98ce-0585605d0222")
            .fixedIp(FixedIp.
                  builder().
                  ipAddress("12.40.7.6").
                  subnetId("09226399-c417-476c-85bb-3ca176e9823a").
                  build())
            .portState("DOWN")
            .build();
   }

   protected Injector injector() {
      return Guice.createInjector(new NovaParserModule(), new GsonModule());
   }
}