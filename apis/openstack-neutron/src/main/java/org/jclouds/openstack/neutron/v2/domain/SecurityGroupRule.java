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
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Named;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A Neutron Security Group Rule
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
public class SecurityGroupRule {

   private String id;
   @Named("tenant_id")
   private String tenantId;

   @Named("security_group_id")
   private String securityGroupId;
   @Named("remote_group_id")
   private String remoteSecurityGroupId;
   private Direction direction;
   private Protocol protocol;
   @Named("port_range_min")
   private Integer portRangeMin;
   @Named("port_range_max")
   private Integer portRangeMax;
   @Named("remote_ip_prefix")
   private String remoteIpPrefix;
   @Named("ethertype")
   private EtherType etherType;

   @ConstructorProperties({
         "id", "tenant_id", "security_group_id", "remote_group_id", "direction", "protocol", "port_range_min",
         "port_range_max", "remote_ip_prefix", "ethertype"
   })
   private SecurityGroupRule(String id, String tenantId, String securityGroupId, String remoteSecurityGroupId, Direction direction,
                               Protocol protocol, Integer portRangeMin, Integer portRangeMax, String remoteIpPrefix, EtherType etherType) {
      this.id = id;
      this.tenantId = tenantId;
      this.securityGroupId = securityGroupId;
      this.remoteSecurityGroupId = remoteSecurityGroupId;
      this.direction = direction;
      this.protocol = protocol;
      this.portRangeMin = portRangeMin;
      this.portRangeMax = portRangeMax;
      this.remoteIpPrefix = remoteIpPrefix;
      this.etherType = etherType;
   }

   /**
    * Default constructor.
    */
   private SecurityGroupRule() {}

   /**
    * Copy constructor
    * @param securityGroupRule
    */
   private SecurityGroupRule(SecurityGroupRule securityGroupRule) {
      this(securityGroupRule.id,
            securityGroupRule.tenantId,
            securityGroupRule.securityGroupId,
            securityGroupRule.remoteSecurityGroupId,
            securityGroupRule.direction,
            securityGroupRule.protocol,
            securityGroupRule.portRangeMin,
            securityGroupRule.portRangeMax,
            securityGroupRule.remoteIpPrefix,
            securityGroupRule.etherType);
   }

   /**
    * @return the id of the security group rule
    */
   @Nullable
   public String getId() {
      return id;
   }

   /**
    * @return the tenantId of the security group rule
    */
   @Nullable
   public String getTenantId() {
      return tenantId;
   }

   /**
    * @return The security group ID for the security group with which the rule is associated
    */
   @Nullable
   public String getSecurityGroupId() {
      return securityGroupId;
   }

   /**
    * @return The remote security group ID
    */
   @Nullable
   public String getRemoteSecurityGroupId() {
      return remoteSecurityGroupId;
   }

   /**
    * @return The direction for this rule. Either ingress or egress.
    */
   @Nullable
   public Direction getDirection() {
      return direction;
   }

   /**
    * @return The protocol to be allowed.
    */
   @Nullable
   public Protocol getProtocol() {
      return protocol;
   }

   /**
    * @return The minimal port
    */
   @Nullable
   public Integer getPortRangeMin() {
      return portRangeMin;
   }

   /**
    * @return The maximal port
    */
   @Nullable
   public Integer getPortRangeMax() {
      return portRangeMax;
   }

   /**
    * @return The cidr of the IP addresses allowed
    */
   @Nullable
   public String getRemoteIpPrefix() {
      return remoteIpPrefix;
   }

   /**
    * @return The ether type. Either IPv4 or IPv6.
    */
   @Nullable
   public EtherType getEtherType() {
      return etherType;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, tenantId, securityGroupId, remoteSecurityGroupId, direction,
            protocol, portRangeMin, portRangeMax, remoteIpPrefix, etherType);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      SecurityGroupRule that = SecurityGroupRule.class.cast(obj);
      return Objects.equal(this.id, that.id) &&
            Objects.equal(this.tenantId, that.tenantId) &&
            Objects.equal(this.securityGroupId, that.securityGroupId) &&
            Objects.equal(this.remoteSecurityGroupId, that.remoteSecurityGroupId) &&
            Objects.equal(this.direction, that.direction) &&
            Objects.equal(this.protocol, that.protocol) &&
            Objects.equal(this.portRangeMin, that.portRangeMin) &&
            Objects.equal(this.portRangeMax, that.portRangeMax) &&
            Objects.equal(this.remoteIpPrefix, that.remoteIpPrefix) &&
            Objects.equal(this.etherType, that.etherType);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("tenantId", tenantId)
            .add("securityGroupId", securityGroupId)
            .add("remoteSecurityGroupId", remoteSecurityGroupId)
            .add("direction", direction)
            .add("protocol", protocol)
            .add("portRangeMin", portRangeMin)
            .add("portRangeMax", portRangeMax)
            .add("remoteIpPrefix", remoteIpPrefix)
            .add("etherType", etherType)
            .toString();
   }

/*
    * Methods to get the Create and Update builders follow
    */

   /**
    * @return the Builder for creating a new NetPartition
    */
   public static CreateBuilder createOptions(String securityGroupId, Direction direction) {
      return new CreateBuilder(securityGroupId, direction);
   }

   private static abstract class Builder<ParameterizedBuilderType> {
      protected SecurityGroupRule securityGroupRule;

      /**
       * No-parameters constructor used when updating.
       * */
      private Builder() {
         securityGroupRule = new SecurityGroupRule();
      }

      protected abstract ParameterizedBuilderType self();

      /**
       * Provide the remoteSecurityGroupId to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getRemoteSecurityGroupId()
       */
      public ParameterizedBuilderType remoteSecurityGroupId(String remoteSecurityGroupId) {
         securityGroupRule.remoteSecurityGroupId = remoteSecurityGroupId;
         return self();
      }

      /**
       * Provide the protocol to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getProtocol()
       */
      public ParameterizedBuilderType protocol(Protocol protocol) {
         securityGroupRule.protocol = protocol;
         return self();
      }

      /**
       * Provide the portRangeMin to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getPortRangeMin()
       */
      public ParameterizedBuilderType portRangeMin(Integer portRangeMin) {
         securityGroupRule.portRangeMin = portRangeMin;
         return self();
      }

      /**
       * Provide the portRangeMax to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getPortRangeMax()
       */
      public ParameterizedBuilderType portRangeMax(Integer portRangeMax) {
         securityGroupRule.portRangeMax = portRangeMax;
         return self();
      }

      /**
       * Provide the remoteIpPrefix to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getRemoteIpPrefix()
       */
      public ParameterizedBuilderType portRangeMin(String remoteIpPrefix) {
         securityGroupRule.remoteIpPrefix = remoteIpPrefix;
         return self();
      }

      /**
       * Provide the etherType to the SecurityGroupRule's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.SecurityGroupRule#getEtherType()
       */
      public ParameterizedBuilderType etherType(EtherType etherType) {
         securityGroupRule.etherType = etherType;
         return self();
      }
   }

   /**
    * Create and Update builders (inheriting from Builder)
    */
   public static class CreateBuilder extends Builder<CreateBuilder> {
      /**
       * Supply required properties for creating a Builder
       */
      private CreateBuilder(String securityGroupId, Direction direction) {
         securityGroupRule.securityGroupId = securityGroupId;
         securityGroupRule.direction = direction;
      }

      /**
       * @return a CreateOptions constructed with this Builder.
       */
      public CreateOptions build() {
         return new CreateOptions(securityGroupRule);
      }

      protected CreateBuilder self() {
         return this;
      }
   }

   /**
    * Create and Update options - extend the domain class, passed to API update and create calls.
    * Essentially the same as the domain class. Ensure validation and safe typing.
    */
   public static class CreateOptions extends SecurityGroupRule {
      /**
       * Copy constructor
       */
      private CreateOptions(SecurityGroupRule securityGroupRule) {
         super(securityGroupRule);
         checkNotNull(securityGroupRule.securityGroupId, "securityGroupId should not be null");
         checkNotNull(securityGroupRule.direction, "direction should not be null");
      }
   }

}
