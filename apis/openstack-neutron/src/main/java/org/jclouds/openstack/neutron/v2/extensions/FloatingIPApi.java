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
package org.jclouds.openstack.neutron.v2.extensions;

import com.google.common.annotations.Beta;
import org.jclouds.Fallbacks;
import org.jclouds.collect.PagedIterable;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.neutron.v2.domain.FloatingIP;
import org.jclouds.openstack.neutron.v2.domain.FloatingIPs;
import org.jclouds.openstack.neutron.v2.fallbacks.EmptyFloatingIPsFallback;
import org.jclouds.openstack.neutron.v2.functions.FloatingIPsToPagedIterable;
import org.jclouds.openstack.neutron.v2.functions.ParseFloatingIPs;
import org.jclouds.openstack.v2_0.options.PaginationOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.ResponseParser;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.Transform;
import org.jclouds.rest.annotations.WrapWith;

import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * Provides synchronous access to FloatingIP operations on the OpenStack Neutron API.
 * <p/>
 * An external IP address that is mapped to a port that is attached to an internal network.
 *
 */
@Beta
@Path("/v2.0/floatingips")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface FloatingIPApi {

   /**
    * Returns the list of all floating IPs currently defined in Neutron for the current tenant. The list provides the unique
    * identifier of each floating IP configured for the tenant
    *
    * @return the list of all floating IP references configured for the tenant.
    */
   @Named("floatingip:list")
   @GET
   @Transform(FloatingIPsToPagedIterable.class)
   @ResponseParser(ParseFloatingIPs.class)
   @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<FloatingIP> list();

   /**
    * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/pagination.html">api doc</a>
    */
   @Named("floatingip:list")
   @GET
   @ResponseParser(ParseFloatingIPs.class)
   @Fallback(EmptyFloatingIPsFallback.class)
   FloatingIPs list(PaginationOptions options);

   /**
    * Returns a Floating IPs collection that should contain a single floating IP with the id requested.
    *
    * @param id the id of the floating IP to return
    * @return Floating IPs collection or empty if not found
    */
   @Named("floatingip:get")
   @GET
   @Path("/{id}")
   @SelectJson("floatingip")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Nullable
   FloatingIP get(@PathParam("id") String id);

   /**
    * Create a new floating IP
    *
    * @param floatingIp Options for creating a floating IP
    * @return the newly created floating IP
    */
   @Named("floatingip:create")
   @POST
   @SelectJson("floatingip")
   FloatingIP create(@WrapWith("floatingip") FloatingIP.CreateOptions floatingIp);

   /**
    * Update a floating IP
    *
    * @param id the id of the floating IP to update
    * @param floatingIp Contains only the attributes to update
    * @return The modified floating IP
    */
   @Named("floatingip:update")
   @PUT
   @Path("/{id}")
   @SelectJson("floatingip")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Nullable
   FloatingIP update(@PathParam("id") String id, @WrapWith("floatingip") FloatingIP.UpdateOptions floatingIp);

   /**
    * Deletes the specified floating IP
    *
    * @param id the id of the floating IP to delete
    * @return true if delete successful, false if not
    */
   @Named("floatingip:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);
}
