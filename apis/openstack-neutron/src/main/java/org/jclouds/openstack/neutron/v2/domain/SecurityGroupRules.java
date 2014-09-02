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
package org.jclouds.openstack.neutron.v2.domain;

import com.google.common.collect.ImmutableSet;
import org.jclouds.openstack.v2_0.domain.Link;
import org.jclouds.openstack.v2_0.domain.PaginatedCollection;

import java.beans.ConstructorProperties;

/**
 * A collection of Security Group Rules
 */
public class SecurityGroupRules extends PaginatedCollection<SecurityGroupRule> {
   public static final SecurityGroupRules EMPTY = new SecurityGroupRules(ImmutableSet.<SecurityGroupRule> of(), ImmutableSet.<Link> of());

   @ConstructorProperties({ "security_group_rules", "security_group_rules_links" })
   protected SecurityGroupRules(Iterable<SecurityGroupRule> securityGroupRules, Iterable<Link> securityGroupRulesLinks) {
      super(securityGroupRules, securityGroupRulesLinks);
   }
}
