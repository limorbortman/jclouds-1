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

package org.jclouds.openstack.neutron.v2_0.domain;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;

import java.beans.ConstructorProperties;
import java.util.Collection;
import java.util.Set;

/**
 * A Neutron Security Group
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
public class SecurityGroup extends ReferenceWithName {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromSecurityGroup(this);
   }

   public static abstract class Builder<T extends Builder<T>> extends ReferenceWithName.Builder<T> {

      protected String description;
      protected Set<SecurityGroupRule> securityGroupRules;

      /**
       * @see SecurityGroup#getDescription()
       */
      public T description(String description) {
         this.description = description;
         return self();
      }

      /**
       * @see SecurityGroup#getSecurityGroupRules()
       */
      public T securityGroupRules(Collection<SecurityGroupRule> securityGroupRules) {
         this.securityGroupRules = ImmutableSet.copyOf(securityGroupRules);
         return self();
      }

      public SecurityGroup build() {
         return new SecurityGroup(id, tenantId, name, description, securityGroupRules);
      }

      public T fromSecurityGroup(SecurityGroup in) {
         return super.fromReferenceWithName(in)
               .description(in.getDescription())
               .securityGroupRules(in.getSecurityGroupRules());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String description;
   private final Set<SecurityGroupRule> securityGroupRules;

   @ConstructorProperties({
         "id", "tenant_id", "name", "description", "security_group_rules"
   })
   protected SecurityGroup(String id, String tenantId, String name, String description, Set<SecurityGroupRule> securityGroupRules) {
      super(id, tenantId, name);
      this.description = description;
      this.securityGroupRules = securityGroupRules;
   }

   /**
    * @return The description of the security group.
    */
   public String getDescription() {
      return description;
   }

   /**
    * @return The security group rules which are associated with this security group.
    */
   public Set<SecurityGroupRule> getSecurityGroupRules() {
      return securityGroupRules;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), description, securityGroupRules);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      SecurityGroup that = SecurityGroup.class.cast(obj);
      return super.equals(obj)
            && Objects.equal(this.description, that.description)
            && Objects.equal(this.securityGroupRules, that.securityGroupRules);
   }

   protected Objects.ToStringHelper string() {
      return super.string().add("description", description).add("securityGroupRules", securityGroupRules);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}