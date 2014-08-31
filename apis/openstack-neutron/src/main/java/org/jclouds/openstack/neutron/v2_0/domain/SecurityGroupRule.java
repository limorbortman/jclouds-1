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

import java.beans.ConstructorProperties;

/**
 * A Neutron Security Group Rule
 *
 * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/security-groups-ext.html">api doc</a>
 */
public class SecurityGroupRule extends Reference {

   public static enum Protocol {

      TCP, UDP, ICMP, UNRECOGNIZED;

      public String value() {
         return name();
      }

      public static Protocol fromValue(String v) {
         try {
            return valueOf(v.replaceAll("\\(.*", "").toUpperCase());
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static enum Direction {

      EGRESS, INGRESS, UNRECOGNIZED;

      public String value() {
         return name().toLowerCase();
      }

      public static Direction fromValue(String v) {
         try {
            return valueOf(v.replaceAll("\\(.*", "").toUpperCase());
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static enum EtherType {

      IPv4, IPv6, UNRECOGNIZED;

      public String value() {
         return name();
      }

      public static EtherType fromValue(String v) {
         try {
            return valueOf(v.replaceAll("\\(.*", ""));
         } catch (IllegalArgumentException e) {
            return UNRECOGNIZED;
         }
      }
   }

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromSecurityGroupRule(this);
   }

   public static abstract class Builder<T extends Builder<T>> extends Reference.Builder<T> {

      protected String securityGroupId;
      protected String remoteSecurityGroupId;
      protected Direction direction;
      protected Protocol protocol;
      protected Integer portRangeMin;
      protected Integer portRangeMax;
      protected String remoteIpPrefix;
      protected EtherType etherType;

      /**
       * @see SecurityGroupRule#getSecurityGroupId()
       */
      public T securityGroupId(String securityGroupId) {
         this.securityGroupId = securityGroupId;
         return self();
      }

      /**
       * @see SecurityGroupRule#getRemoteSecurityGroupId()
       */
      public T remoteSecurityGroupId(String remoteSecurityGroupId) {
         this.remoteSecurityGroupId = remoteSecurityGroupId;
         return self();
      }

      /**
       * @see SecurityGroupRule#getDirection()
       */
      public T direction(Direction direction) {
         this.direction = direction;
         return self();
      }

      /**
       * @see SecurityGroupRule#getProtocol()
       */
      public T protocol(Protocol protocol) {
         this.protocol = protocol;
         return self();
      }

      /**
       * @see SecurityGroupRule#getPortRangeMin()
       */
      public T portRangeMin(Integer portRangeMin) {
         this.portRangeMin = portRangeMin;
         return self();
      }

      /**
       * @see SecurityGroupRule#getPortRangeMax()
       */
      public T portRangeMax(Integer portRangeMax) {
         this.portRangeMax = portRangeMax;
         return self();
      }

      /**
       * @see SecurityGroupRule#getRemoteIpPrefix()
       */
      public T remoteIpPrefix(String remoteIpPrefix) {
         this.remoteIpPrefix = remoteIpPrefix;
         return self();
      }

      /**
       * @see SecurityGroupRule#getEtherType()
       */
      public T etherType(EtherType etherType) {
         this.etherType = etherType;
         return self();
      }

      public SecurityGroupRule build() {
         return new SecurityGroupRule(id, tenantId, securityGroupId, remoteSecurityGroupId, direction, protocol, portRangeMin, portRangeMax, remoteIpPrefix, etherType);
      }

      public T fromSecurityGroupRule(SecurityGroupRule in) {
         return super.fromReference(in)
               .securityGroupId(in.getSecurityGroupId())
               .remoteSecurityGroupId(in.getRemoteSecurityGroupId())
               .direction(in.getDirection())
               .protocol(in.getProtocol())
               .portRangeMin(in.getPortRangeMin())
               .portRangeMax(in.getPortRangeMax())
               .remoteIpPrefix(in.getRemoteIpPrefix())
               .etherType(in.getEtherType());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String securityGroupId;
   private final String remoteSecurityGroupId;
   private final Direction direction;
   private final Protocol protocol;
   private final Integer portRangeMin;
   private final Integer portRangeMax;
   private final String remoteIpPrefix;
   private final EtherType etherType;

   @ConstructorProperties({
         "id", "tenant_id", "security_group_id", "remote_group_id", "direction", "protocol", "port_range_min",
         "port_range_max", "remote_ip_prefix", "ethertype"
   })
   protected SecurityGroupRule(String id, String tenantId, String securityGroupId, String remoteSecurityGroupId, Direction direction,
                               Protocol protocol, Integer portRangeMin, Integer portRangeMax, String remoteIpPrefix, EtherType etherType) {
      super(id, tenantId);
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
    * @return The security group ID for the security group with which the rule is associated
    */
   public String getSecurityGroupId() {
      return securityGroupId;
   }

   /**
    * @return The remote security group ID
    */
   public String getRemoteSecurityGroupId() {
      return remoteSecurityGroupId;
   }

   /**
    * @return The direction for this rule. Either ingress or egress.
    */
   public Direction getDirection() {
      return direction;
   }

   /**
    * @return The protocol to be allowed.
    */
   public Protocol getProtocol() {
      return protocol;
   }

   /**
    * @return The minimal port
    */
   public Integer getPortRangeMin() {
      return portRangeMin;
   }

   /**
    * @return The maximal port
    */
   public Integer getPortRangeMax() {
      return portRangeMax;
   }

   /**
    * @return The cidr of the IP addresses allowed
    */
   public String getRemoteIpPrefix() {
      return remoteIpPrefix;
   }

   /**
    * @return The ether type. Either IPv4 or IPv6.
    */
   public EtherType getEtherType() {
      return etherType;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), securityGroupId, remoteSecurityGroupId, direction, protocol,
            portRangeMin, portRangeMax, remoteIpPrefix, etherType);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      SecurityGroupRule that = SecurityGroupRule.class.cast(obj);
      return super.equals(obj)
            && Objects.equal(this.securityGroupId, that.securityGroupId)
            && Objects.equal(this.remoteSecurityGroupId, that.remoteSecurityGroupId)
            && Objects.equal(this.direction, that.direction)
            && Objects.equal(this.protocol, that.protocol)
            && Objects.equal(this.portRangeMin, that.portRangeMin)
            && Objects.equal(this.portRangeMax, that.portRangeMax)
            && Objects.equal(this.remoteIpPrefix, that.remoteIpPrefix)
            && Objects.equal(this.etherType, that.etherType);
   }

   protected Objects.ToStringHelper string() {
      return super.string()
            .add("securityGroupId", securityGroupId).add("remoteSecurityGroupId", remoteSecurityGroupId).add("direction", direction)
            .add("protocol", protocol).add("portRangeMin", portRangeMin).add("portRangeMax", portRangeMax)
            .add("remoteIpPrefix", remoteIpPrefix).add("etherType", etherType);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}