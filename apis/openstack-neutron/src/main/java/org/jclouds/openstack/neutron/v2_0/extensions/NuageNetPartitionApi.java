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
package org.jclouds.openstack.neutron.v2_0.extensions;

import com.google.common.collect.FluentIterable;
import org.jclouds.Fallbacks;
import org.jclouds.javax.annotation.Nullable;
import org.jclouds.openstack.keystone.v2_0.filters.AuthenticateRequest;
import org.jclouds.openstack.neutron.v2_0.domain.NuageNetPartition;
import org.jclouds.openstack.neutron.v2_0.options.CreateNuageNetPartitionOptions;
import org.jclouds.openstack.neutron.v2_0.options.DeleteNuageNetPartitionOptions;
import org.jclouds.rest.annotations.Fallback;
import org.jclouds.rest.annotations.PayloadParam;
import org.jclouds.rest.annotations.RequestFilters;
import org.jclouds.rest.annotations.SelectJson;
import org.jclouds.rest.annotations.WrapWith;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Provides asynchronous access to Nuage net partition operations on the openstack.neutron.API.
 *
 * @see <a href=
 * "http://docs.openstack.org/api/openstack-network/2.0/content/router_ext_ops_floatingip.html">api doc</a>
 */
@RequestFilters(AuthenticateRequest.class)
@Consumes(MediaType.APPLICATION_JSON)
@Path("/v2.0/net-partitions")
public interface NuageNetPartitionApi {

   /**
    * @see NuageNetPartitionApi#list
    */
   @GET
   @SelectJson("net_partitions")
   @Fallback(Fallbacks.EmptyFluentIterableOnNotFoundOr404.class)
   FluentIterable<? extends NuageNetPartition> list();

   /**
    * @see NuageNetPartitionApi#get
    */
   @GET
   @SelectJson("net_partition")
   @Path("/{id}")
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   NuageNetPartition get(@PathParam("id") String id);

   /**
    * @see NuageNetPartitionApi#create
    */
   @POST
   @SelectJson("net_partition")
   @Produces(MediaType.APPLICATION_JSON)
   @WrapWith("net_partitions")
   NuageNetPartition create(@PayloadParam("name") String name, @Nullable CreateNuageNetPartitionOptions... createNuageNetPartitionOptions);

   /**
    * @see NuageNetPartitionApi#delete
    */
   @DELETE
   @Fallback(Fallbacks.NullOnNotFoundOr404.class)
   @Path("/{id}")
   boolean delete(@PathParam("id") String id, @Nullable DeleteNuageNetPartitionOptions... deleteNuageNetPartitionOptions);
}