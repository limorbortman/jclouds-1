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

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Named;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A Neutron Security Group
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
public class SecurityGroup {

   private String id;
   @Named("tenant_id")
   private String tenantId;
   private String name;

   private String description;
   @Named("security_group_rules")
   private ImmutableSet<SecurityGroupRule> securityGroupRules;

   @ConstructorProperties({"id", "tenant_id", "name", "description", "security_group_rules"})
   private SecurityGroup(String id, String tenantId, String name, String description, ImmutableSet<SecurityGroupRule> securityGroupRules) {
      this.id = id;
      this.tenantId = tenantId;
      this.name = name;
      this.description = description;
      this.securityGroupRules = securityGroupRules;
   }

   /**
    * Default constructor.
    */
   private SecurityGroup() {}

   /**
    * Copy constructor
    * @param securityGroup
    */
   private SecurityGroup(SecurityGroup securityGroup) {
      this(securityGroup.id,
            securityGroup.tenantId,
            securityGroup.name,
            securityGroup.description,
            securityGroup.securityGroupRules);
   }

   /**
    * @return the id of the security group
    */
   @Nullable
   public String getId() {
      return id;
   }

   /**
    * @return the tenantId of the security group
    */
   @Nullable
   public String getTenantId() {
      return tenantId;
   }

   /**
    * @return the name of the security group
    */
   @Nullable
   public String getName() {
      return name;
   }

   /**
    * @return The description of the security group.
    */
   @Nullable
   public String getDescription() {
      return description;
   }

   /**
    * @return The security group rules which are associated with this security group.
    */
   @Nullable
   public ImmutableSet<SecurityGroupRule> getSecurityGroupRules() {
      return securityGroupRules;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, tenantId, name, description, securityGroupRules);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      SecurityGroup that = SecurityGroup.class.cast(obj);
      return Objects.equal(this.id, that.id)
            && Objects.equal(this.tenantId, that.tenantId)
            && Objects.equal(this.name, that.name)
            && Objects.equal(this.description, that.description)
            && Objects.equal(this.securityGroupRules, that.securityGroupRules);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("tenantId", tenantId)
            .add("name", name)
            .add("description", description)
            .add("securityGroupRules", securityGroupRules)
            .toString();
   }

 /*
    * Methods to get the Create and Update builders follow
    */

   /**
    * @return the Builder for creating a new NetPartition
    */
   public static CreateBuilder createOptions(String name, String description) {
      return new CreateBuilder(name, description);
   }

   private static abstract class Builder<ParameterizedBuilderType> {
      protected SecurityGroup securityGroup;

      /**
       * No-parameters constructor used when updating.
       * */
      private Builder() {
         securityGroup = new SecurityGroup();
      }

      protected abstract ParameterizedBuilderType self();
   }

   /**
    * Create and Update builders (inheriting from Builder)
    */
   public static class CreateBuilder extends Builder<CreateBuilder> {
      /**
       * Supply required properties for creating a Builder
       */
      private CreateBuilder(String name, String description) {
         securityGroup.name = name;
         securityGroup.description = description;
      }

      /**
       * @return a CreateOptions constructed with this Builder.
       */
      public CreateOptions build() {
         return new CreateOptions(securityGroup);
      }

      protected CreateBuilder self() {
         return this;
      }
   }

   /**
    * Create and Update options - extend the domain class, passed to API update and create calls.
    * Essentially the same as the domain class. Ensure validation and safe typing.
    */
   public static class CreateOptions extends SecurityGroup {
      /**
       * Copy constructor
       */
      private CreateOptions(SecurityGroup securityGroup) {
         super(securityGroup);
         checkNotNull(securityGroup.name, "name should not be null");
         checkNotNull(securityGroup.description, "description should not be null");
      }
   }

}
