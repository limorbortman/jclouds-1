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
import org.jclouds.openstack.neutron.v2.domain.NetPartition;
import org.jclouds.openstack.neutron.v2.domain.NetPartitions;
import org.jclouds.openstack.neutron.v2.fallbacks.EmptyNetPartitionsFallback;
import org.jclouds.openstack.neutron.v2.functions.NetPartitionsToPagedIterable;
import org.jclouds.openstack.neutron.v2.functions.ParseNetPartitions;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

/**
 * Provides synchronous access to NetPartition operations on the OpenStack Neutron API.
 * <p/>
 * An external IP address that is mapped to a port that is attached to an internal network.
 *
 */
@Beta
@Path("/net-partitions")
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
public interface NetPartitionApi {

   /**
    * Returns the list of all net partitions currently defined in Neutron for the current tenant. The list provides the unique
    * identifier of each net partition configured for the tenant
    *
    * @return the list of all net partitions references configured for the tenant.
    */
   @Named("netpartition:list")
   @GET
   @Transform(NetPartitionsToPagedIterable.class)
   @ResponseParser(ParseNetPartitions.class)
   @Fallback(Fallbacks.EmptyPagedIterableOnNotFoundOr404.class)
   PagedIterable<NetPartition> list();

   /**
    * @see <a href="http://docs.openstack.org/api/openstack-network/2.0/content/pagination.html">api doc</a>
    */
   @Named("netpartition:list")
   @GET
   @ResponseParser(ParseNetPartitions.class)
   @Fallback(EmptyNetPartitionsFallback.class)
   NetPartitions list(PaginationOptions options);

   /**
    * Returns a net partitions collection that should contain a single net partition with the id requested.
    *
    * @param id the id of the net partition to return
    * @return Net Partitions collection or empty if not found
    */
   @Named("netpartition:get")
   @GET
   @Path("/{id}")
   @SelectJson("net_partition")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Nullable
   NetPartition get(@PathParam("id") String id);

   /**
    * Create a new net partition
    *
    * @param netPartition Options for creating a net partition
    * @return the newly created net partition
    */
   @Named("netpartition:create")
   @POST
   @SelectJson("net_partition")
   NetPartition create(@WrapWith("net_partition") NetPartition.CreateOptions netPartition);

   /**
    * Deletes the specified net partition
    *
    * @param id the id of the net partition to delete
    * @return true if delete successful, false if not
    */
   @Named("netpartition:delete")
   @DELETE
   @Path("/{id}")
   @Fallback(Fallbacks.FalseOnNotFoundOr404.class)
   boolean delete(@PathParam("id") String id);
}
