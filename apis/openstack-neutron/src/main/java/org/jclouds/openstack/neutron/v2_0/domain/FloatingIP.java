/*
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.jclouds.openstack.neutron.v2_0.domain;

import com.google.common.base.Objects;

import java.beans.ConstructorProperties;

/**
 * A Neutron Floating IP
 *
 */
public class FloatingIP extends Reference {

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromFloatingIP(this);
   }

   public static abstract class Builder<T extends Builder<T>> extends Reference.Builder<T> {
      protected abstract T self();

      protected String routerId;
      protected String floatingNetworkId;
      protected String portId;
      protected String fixedIpAddress;
      protected String floatingIpAddress;

      /**
       * @see FloatingIP#getRouterId()
       */
      public T routerId(String routerId) {
         this.routerId = routerId;
         return self();
      }

      /**
       * @see FloatingIP#getFloatingNetworkId()
       */
      public T floatingNetworkId(String floatingNetworkId) {
         this.floatingNetworkId = floatingNetworkId;
         return self();
      }

      /**
       * @see FloatingIP#getPortId()
       */
      public T portId(String portId) {
         this.portId = portId;
         return self();
      }

      /**
       * @see FloatingIP#getFixedIpAddress()
       */
      public T fixedIpAddress(String fixedIpAddress) {
         this.fixedIpAddress = fixedIpAddress;
         return self();
      }

      /**
       * @see FloatingIP#getFloatingIpAddress()
       */
      public T floatingIpAddress(String floatingIpAddress) {
         this.floatingIpAddress = floatingIpAddress;
         return self();
      }

      public FloatingIP build() {
         return new FloatingIP(id, tenantId, routerId, floatingNetworkId, portId, fixedIpAddress, floatingIpAddress);
      }

      public T fromFloatingIP(FloatingIP in) {
         return super.fromReference(in)
               .floatingNetworkId(in.getFloatingNetworkId())
               .portId(in.getPortId())
               .fixedIpAddress(in.getFixedIpAddress())
               .floatingIpAddress(in.getFloatingIpAddress());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   private final String routerId;
   private final String floatingNetworkId;
   private final String portId;
   private final String fixedIpAddress;
   private final String floatingIpAddress;

   @ConstructorProperties({
         "id", "tenant_id", "router_id", "floating_network_id", "port_id", "fixed_ip_address", "floating_ip_address"
   })
   protected FloatingIP(String id, String tenantId, String routerId, String floatingNetworkId, String portId, String fixedIpAddress, String floatingIpAddress) {
      super(id, tenantId);
      this.routerId = routerId;
      this.floatingNetworkId = floatingNetworkId;
      this.portId = portId;
      this.fixedIpAddress = fixedIpAddress;
      this.floatingIpAddress = floatingIpAddress;
   }

   /**
    * @return the id of the router where the floating IP is associated with
    */
   public String getRouterId() {
      return routerId;
   }

   /**
    * @return the id of the external network where the floating IP is created
    */
   public String getFloatingNetworkId() {
      return floatingNetworkId;
   }

   /**
    * @return the id of the port on an internal network which should be associated to the Floating IP
    */
   public String getPortId() {
      return portId;
   }

   /**
    * @return the IP address on the port which is associated with the floating IP
    */
   public String getFixedIpAddress() {
      return fixedIpAddress;
   }

   /**
    * @return the IP address of the floating IP on the external network
    */
   public String getFloatingIpAddress() {
      return floatingIpAddress;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(super.hashCode(), floatingNetworkId, portId, fixedIpAddress, floatingIpAddress);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      FloatingIP that = FloatingIP.class.cast(obj);
      return super.equals(obj)
            && Objects.equal(this.routerId, that.routerId)
            && Objects.equal(this.floatingNetworkId, that.floatingNetworkId)
            && Objects.equal(this.portId, that.portId)
            && Objects.equal(this.fixedIpAddress, that.fixedIpAddress)
            && Objects.equal(this.floatingIpAddress, that.floatingIpAddress);
   }

   protected Objects.ToStringHelper string() {
      return super.string().add("routerId", routerId).add("floatingNetworkId", floatingNetworkId).add("portId", portId)
            .add("fixedIpAddress", fixedIpAddress).add("floatingIpAddress", floatingIpAddress);
   }

   @Override
   public String toString() {
      return string().toString();
   }

}