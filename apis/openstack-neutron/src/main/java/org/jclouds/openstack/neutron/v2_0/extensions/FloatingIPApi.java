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
package org.jclouds.openstack.neutron.v2_0.extensions;

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.neutron.v2_0.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2_0.domain.Reference;
import org.jclouds.openstack.neutron.v2_0.options.CreateFloatingIPOptions;
import org.jclouds.openstack.neutron.v2_0.options.UpdateFloatingIPOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.QueryParams;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Provides synchronous access to Floating IP operations on the openstack neutron API.
 * <p/>
 * Representing an external IP address mapped to a OpenStack Networking port attached to an internal network
 *
 * @see FloatingIPAsyncApi
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v2.0/floatingips")
public interface FloatingIPApi {

   /**
    * Returns the list of all floating IPs currently defined in Neutron for the current tenant. The list provides the unique
    * identifier of each floating IP configured for the tenant
    *
    * @return the list of all floating IP references configured for the tenant.
    */
   @GET
   @SelectJson("floatingips")
   @QueryParams(keys = {"fields", "fields"}, values = {"id", "tenant_id"})
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends Reference> listReferences();

   /**
    * Returns all routers currently defined in Quantum for the current tenant.
    *
    * @return the list of all routers configured for the tenant
    */
   @GET
   @SelectJson("floatingips")
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends FloatingIP> list();

   /**
    * Returns the specific floating IP.
    *
    * @param id the id of the floating IP to return
    * @return FloatingIP or null if not found
    */
   @GET
   @SelectJson("floatingip")
   @Path("/{id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   FloatingIP get(@PathParam("id") String id);

   /**
    * Create a new floating IP
    *
    * @param floatingNetworkId the id of the external network
    * @param portId            the id of the port on the internal network
    * @param options           optional arguments
    * @return a reference of the newly-created floating IP
    */
   @POST
   @SelectJson("floatingip")
   @Produces(MediaType.APPLICATION_JSON)
   @WrapWith("floatingip")
   Reference create(@PayloadParam("floating_network_id") String floatingNetworkId, CreateFloatingIPOptions... options);

   /**
    * Update a floating IP
    *
    * @param id      the id of the floating IP to update
    * @param options the attributes to update
    * @return true if update successful, false if not
    */
   @PUT
   @Produces(MediaType.APPLICATION_JSON)
   @Path("/{id}")
   @WrapWith("floatingip")
   boolean update(@PathParam("id") String id, UpdateFloatingIPOptions... options);

   /**
    * Deletes the specified floating IP
    *
    * @param id the id of the floating IP to delete
    * @return true if delete successful, false if not
    */
   @DELETE
   @Path("/{id}")
   boolean delete(@PathParam("id") String id);
}