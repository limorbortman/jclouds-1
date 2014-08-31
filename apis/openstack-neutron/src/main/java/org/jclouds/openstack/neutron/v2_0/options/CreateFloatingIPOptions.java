/**
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
package org.jclouds.openstack.neutron.v2_0.options;

import com.google.common.collect.ImmutableMap;
import org.jclouds.http.HttpRequest;
import org.jclouds.rest.MapBinder;
import org.jclouds.rest.binders.BindToJsonPayload;

import javax.inject.Inject;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 *
 */
public class CreateFloatingIPOptions implements MapBinder {

   @Inject
   private BindToJsonPayload jsonBinder;

   public static Builder<?> builder() {
      return new ConcreteBuilder();
   }

   public Builder<?> toBuilder() {
      return new ConcreteBuilder().fromCreateFloatingIpOptions(this);
   }

   public static abstract class Builder<T extends Builder<T>> {
      protected abstract T self();

      protected String portId;
      protected String fixedIpAddress;

      /**
       * @see CreateFloatingIPOptions#getPortId()
       */
      public T portId(String portId) {
         this.portId = portId;
         return self();
      }

      /**
       * @see CreateFloatingIPOptions#getFixedIpAddress()
       */
      public T fixedIpAddress(String fixedIpAddress) {
         this.fixedIpAddress = fixedIpAddress;
         return self();
      }

      public CreateFloatingIPOptions build() {
         return new CreateFloatingIPOptions(portId, fixedIpAddress);
      }

      public T fromCreateFloatingIpOptions(CreateFloatingIPOptions options) {
         return this.portId(options.getPortId()).fixedIpAddress(options.getFixedIpAddress());
      }
   }

   private static class ConcreteBuilder extends Builder<ConcreteBuilder> {
      @Override
      protected ConcreteBuilder self() {
         return this;
      }
   }

   protected static class CreateFloatingIpRequest {
      protected String floating_network_id;
      protected String port_id;
      protected String fixed_ip_address;

      protected CreateFloatingIpRequest(String floatingNetworkId) {
         this.floating_network_id = floatingNetworkId;
      }
   }

   private final String portId;
   private final String fixedIpAddress;

   public CreateFloatingIPOptions(String portId, String fixedIpAddress) {
      this.portId = portId;
      this.fixedIpAddress = fixedIpAddress;
   }

   /**
    * @return the id of the port where this floating IP should be assigned too
    */
   public String getPortId() {
      return portId;
   }

   /**
    * @return the IP address on the port which will be associated with the floating IP
    */
   public String getFixedIpAddress() {
      return fixedIpAddress;
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Map<String, Object> postParams) {
      String floatingNetworkId = checkNotNull(postParams.get("floating_network_id"), "floatingNetworkId not present").toString();

      CreateFloatingIpRequest createFloatingIpRequest = new CreateFloatingIpRequest(floatingNetworkId);
      if (this.portId != null)
         createFloatingIpRequest.port_id = this.portId;
      if (this.fixedIpAddress != null)
         createFloatingIpRequest.fixed_ip_address = this.fixedIpAddress;

      return bindToRequest(request, ImmutableMap.of("floatingip", createFloatingIpRequest));
   }

   @Override
   public <R extends HttpRequest> R bindToRequest(R request, Object input) {
      return jsonBinder.bindToRequest(request, input);
   }
}