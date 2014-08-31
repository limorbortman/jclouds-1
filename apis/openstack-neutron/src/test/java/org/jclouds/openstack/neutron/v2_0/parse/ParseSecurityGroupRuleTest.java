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

package org.jclouds.openstack.neutron.v2_0.parse;

import org.jclouds.json.BaseItemParserTest;
import org.jclouds.openstack.neutron.v2_0.domain.SecurityGroupRule;
import org.jclouds.rest.annotations.SelectJson;
import org.testng.annotations.Test;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

/**
 *
 */
@Test(groups = "unit", testName = "ParseSecurityRuleGroupTest")
public class ParseSecurityGroupRuleTest extends BaseItemParserTest<SecurityGroupRule> {

   @Override
   public String resource() {
      return "/security_group_rule.json";
   }

   @Override
   @SelectJson("security_group_rule")
   @Consumes(MediaType.APPLICATION_JSON)
   public SecurityGroupRule expected() {
      return SecurityGroupRule.builder()
            .direction(SecurityGroupRule.Direction.EGRESS)
            .etherType(SecurityGroupRule.EtherType.IPv4)
            .protocol(SecurityGroupRule.Protocol.TCP)
            .securityGroupId("85cc3048-abc3-43cc-89b3-377341426ac5")
            .tenantId("e4f50856753b4dc6afee5fa6b9b6c550")
            .id("3c0e45ff-adaf-4124-b083-bf390e5482ff")
            .build();
   }

}