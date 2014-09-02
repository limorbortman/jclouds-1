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
package org.jclouds.openstack.neutron.v2.domain;

import com.google.common.base.Objects;
import org.jclouds.javax.annotation.Nullable;

import javax.inject.Named;
import java.beans.ConstructorProperties;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * A Neutron Floating IP
 *
 * An external IP address that is mapped to a port that is attached to an internal network
 */
public class FloatingIP {

   private String id;
   @Named("tenant_id")
   private String tenantId;

   @Named("router_id")
   private String routerId;
   @Named("floating_network_id")
   private String floatingNetworkId;
   @Named("port_id")
   private String portId;
   @Named("fixed_ip_address")
   private String fixedIpAddress;
   @Named("floating_ip_address")
   private String floatingIpAddress;

   @ConstructorProperties({
         "id", "tenant_id", "router_id", "floating_network_id", "port_id", "fixed_ip_address", "floating_ip_address"
   })
   private FloatingIP(String id, String tenantId, String routerId, String floatingNetworkId, String portId, String fixedIpAddress, String floatingIpAddress) {
      this.id = id;
      this.tenantId = tenantId;
      this.routerId = routerId;
      this.floatingNetworkId = floatingNetworkId;
      this.portId = portId;
      this.fixedIpAddress = fixedIpAddress;
      this.floatingIpAddress = floatingIpAddress;
   }

   /**
    * Default constructor.
    */
   private FloatingIP() {}

   /**
    * Copy constructor
    * @param floatingIp
    */
   private FloatingIP(FloatingIP floatingIp) {
      this(floatingIp.id,
            floatingIp.tenantId,
            floatingIp.routerId,
            floatingIp.floatingNetworkId,
            floatingIp.portId,
            floatingIp.fixedIpAddress,
            floatingIp.floatingIpAddress);
   }

   /**
    * @return the id of the FloatingIP
    */
   @Nullable
   public String getId() {
      return id;
   }

   /**
    * @return the id of the tenant where this entity is associated with
    */
   @Nullable
   public String getTenantId() {
      return tenantId;
   }

   /**
    * @return the id of the router where the floating IP is associated with
    */
   @Nullable
   public String getRouterId() {
      return routerId;
   }

   /**
    * @return the id of the external network where the floating IP is created
    */
   @Nullable
   public String getFloatingNetworkId() {
      return floatingNetworkId;
   }

   /**
    * @return the id of the port on an internal network which should be associated to the Floating IP
    */
   @Nullable
   public String getPortId() {
      return portId;
   }

   /**
    * @return the IP address on the port which is associated with the floating IP
    */
   @Nullable
   public String getFixedIpAddress() {
      return fixedIpAddress;
   }

   /**
    * @return the IP address of the floating IP on the external network
    */
   @Nullable
   public String getFloatingIpAddress() {
      return floatingIpAddress;
   }

   @Override
   public int hashCode() {
      return Objects.hashCode(id, tenantId, floatingNetworkId, portId, fixedIpAddress, floatingIpAddress);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (obj == null || getClass() != obj.getClass()) return false;
      FloatingIP that = FloatingIP.class.cast(obj);
      return Objects.equal(this.id, that.id)
            && Objects.equal(this.tenantId, that.tenantId)
            && Objects.equal(this.routerId, that.routerId)
            && Objects.equal(this.floatingNetworkId, that.floatingNetworkId)
            && Objects.equal(this.portId, that.portId)
            && Objects.equal(this.fixedIpAddress, that.fixedIpAddress)
            && Objects.equal(this.floatingIpAddress, that.floatingIpAddress);
   }

   @Override
   public String toString() {
      return Objects.toStringHelper(this)
            .add("id", id)
            .add("tenantId", tenantId)
            .add("routerId", routerId)
            .add("floatingNetworkId", floatingNetworkId)
            .add("portId", portId)
            .add("fixedIpAddress", fixedIpAddress)
            .add("floatingIpAddress", floatingIpAddress)
            .toString();
   }

      /*
    * Methods to get the Create and Update builders follow
    */

   /**
    * @return the Builder for creating a new FloatingIP
    */
   public static CreateBuilder createOptions(String floatingNetworkId) {
      return new CreateBuilder(floatingNetworkId);
   }

   /**
    * @return the Builder for updating a FloatingIP
    */
   public static UpdateBuilder updateOptions() {
      return new UpdateBuilder();
   }

   private static abstract class Builder<ParameterizedBuilderType> {
      protected FloatingIP floatingIp;

      /**
       * No-parameters constructor used when updating.
       * */
      private Builder() {
         floatingIp = new FloatingIP();
      }

      protected abstract ParameterizedBuilderType self();

      /**
       * Provide the router ID to the FloatingIP's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.FloatingIP#getRouterId()
       */
      public ParameterizedBuilderType routerId(String routerId) {
         floatingIp.routerId = routerId;
         return self();
      }

      /**
       * Provide the floatingNetworkId to the FloatingIP's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.FloatingIP#getFloatingNetworkId()
       */
      public ParameterizedBuilderType floatingNetworkId(String floatingNetworkId) {
         floatingIp.floatingNetworkId = floatingNetworkId;
         return self();
      }

      /**
       * Provide the portId to the FloatingIP's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.FloatingIP#getPortId()
       */
      public ParameterizedBuilderType portId(String portId) {
         floatingIp.portId = portId;
         return self();
      }

      /**
       * Provide the fixedIpAddress to the FloatingIP's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.FloatingIP#getFixedIpAddress()
       */
      public ParameterizedBuilderType fixedIpAddress(String fixedIpAddress) {
         floatingIp.fixedIpAddress = fixedIpAddress;
         return self();
      }

      /**
       * Provide the floatingIpAddress to the FloatingIP's Builder.
       *
       * @return the Builder.
       * @see  org.jclouds.openstack.neutron.v2.domain.FloatingIP#getFloatingIpAddress()
       */
      public ParameterizedBuilderType floatingIpAddress(String floatingIpAddress) {
         floatingIp.floatingIpAddress = floatingIpAddress;
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
      private CreateBuilder(String floatingNetworkId) {
         floatingIp.floatingNetworkId = floatingNetworkId;
      }

      /**
       * @return a CreateOptions constructed with this Builder.
       */
      public CreateOptions build() {
         return new CreateOptions(floatingIp);
      }

      protected CreateBuilder self() {
         return this;
      }
   }

   /**
    * Create and Update builders (inheriting from Builder)
    */
   public static class UpdateBuilder extends Builder<UpdateBuilder> {
      /**
       * Supply required properties for updating a Builder
       */
      private UpdateBuilder() {
      }

      /**
       * @return a UpdateOptions constructed with this Builder.
       */
      public UpdateOptions build() {
         return new UpdateOptions(floatingIp);
      }

      protected UpdateBuilder self() {
         return this;
      }
   }

   /**
    * Create and Update options - extend the domain class, passed to API update and create calls.
    * Essentially the same as the domain class. Ensure validation and safe typing.
    */
   public static class CreateOptions extends FloatingIP {
      /**
       * Copy constructor
       */
      private CreateOptions(FloatingIP floatingIp) {
         super(floatingIp);
         checkNotNull(floatingIp.floatingNetworkId, "floatingNetworkId should not be null");
      }
   }

   /**
    * Create and Update options - extend the domain class, passed to API update and create calls.
    * Essentially the same as the domain class. Ensure validation and safe typing.
    */
   public static class UpdateOptions extends FloatingIP  {
      /**
       * Copy constructor
       */
      private UpdateOptions(FloatingIP floatingIp) {
         super(floatingIp);
      }
   }
}
